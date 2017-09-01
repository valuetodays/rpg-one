package billy.rpg.merger;

import billy.rpg.common.util.ByteHexStringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * resource(s)  -->  *.lib
 *
 * @author liulei@bshf360.com
 * @since 2017-09-01 10:20
 */
public class Splitter {
    private static final Logger LOG = Logger.getLogger(Splitter.class);
    private RandomAccessFile raf;

    public Splitter(String libPath) throws Exception {
        raf = new RandomAccessFile(libPath, "r");
        load();
    }


    private int resourceTypeCount;
    private short[] resourceCountArr;
    private int resourceCounts;
    private Map<Integer, String> resourceIdTypeIndexMap = new LinkedHashMap<>();
    private Map<String, Integer> typeIndexResourceIdMap = new LinkedHashMap<>();
    private int[] numberTable;
    private int[] lengthTable;
    private int[] jumpTable;

    private int offset;

    private void load() throws Exception {
        resourceTypeCount = readInt();
        resourceCountArr = new short[resourceTypeCount];
        for (int i = 0; i < resourceTypeCount; i++) {
            resourceCountArr[i] = readShort();
            resourceCounts += resourceCountArr[i];
        }

        numberTable = new int[resourceCounts];
        for (int i = 0; i < numberTable.length; i++) {
            numberTable[i] = readInt();
        }
        lengthTable = new int[resourceCounts];
        for (int i = 0; i < lengthTable.length; i++) {
            lengthTable[i] = readInt();
        }
        jumpTable = new int[resourceCounts];
        for (int i = 0; i < jumpTable.length; i++) {
            jumpTable[i] = readInt();
        }

        // getResource all type & index
        for (int i = 0; i < resourceCounts; i++) {
            int type = 0;
            int preIndex = 0;
            int nextIndex = 0;
            for (int j = 0; j < resourceCountArr.length; j++) {
                nextIndex += resourceCountArr[j];
                if (i >= preIndex && i < nextIndex) {
                    type = j;
                    break;
                }
                preIndex += resourceCountArr[j];
            }
            LOG.debug("resourceCount" + i + "  <<type=" + type + " & number=" + numberTable[i] + ">>");
            resourceIdTypeIndexMap.put(i, type + "," + numberTable[i]); // 不要index, 要文件的number
            typeIndexResourceIdMap.put(type + "," + numberTable[i], i);
        }

        offset = 4 + resourceTypeCount * 2 + resourceCounts * 12;
        Set<Map.Entry<Integer, String>> resourceIdTypeIndexEntries = resourceIdTypeIndexMap.entrySet();
        for (Map.Entry<Integer, String> resourceIdTypeIndexEntry : resourceIdTypeIndexEntries) {
            Integer key = resourceIdTypeIndexEntry.getKey();
            String value = resourceIdTypeIndexEntry.getValue();
            int length = lengthTable[key];
            int jump = jumpTable[key];
            byte[] fileContent = new byte[length];
            raf.seek(offset + jump);
            raf.read(fileContent);
            LOG.debug("fileContent: " + ByteHexStringUtil.bytes2HexString(fileContent));
        }

        debugOutput();
    }

    /**
     * get resource
     *
     * @param type type
     * @param number number
     * @return the file's byte string
     */
    public String getResource(int type, int number) throws Exception {
        int realType = type - 1;
        Integer integer = typeIndexResourceIdMap.get(realType + "," + number);
        if (null == integer) {
            throw new RuntimeException("err: unknown type/number " + type + "/" + number);
        }
        LOG.debug(integer);

        int length = lengthTable[integer];
        int jump = jumpTable[integer];
        byte[] fileContent = new byte[length];
        raf.seek(offset + jump);
        raf.read(fileContent);
        return ByteHexStringUtil.bytes2HexString(fileContent);
    }

    private void debugOutput() {
        LOG.debug("resourceTypeCount=" + resourceTypeCount);
        for (int i = 0; i < resourceCountArr.length; i++) {
            LOG.debug("resourceCounts["+i+"]=" + resourceCountArr[i]);
        }

        LOG.debug("resourceIdTypeIndexMap=" + resourceIdTypeIndexMap);
        LOG.debug("typeIndexResourceIdMap=" + typeIndexResourceIdMap);
    }

    public void close() {
        IOUtils.closeQuietly(raf);
    }


    /**
     * getResource int (4 bytes)
     */
    private int readInt() throws Exception {
        int wTmp = readShort();
        wTmp |= readShort() << 16;

        return wTmp;
    }

    /**
     * getResource short (2 bytes)
     */
    private short readShort() throws Exception {
        int low = raf.read();
        int high = raf.read();
        return (short)(low | (high << 8));
    }
}
