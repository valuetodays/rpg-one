package billy.rpg.merger;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.RandomAccessFile;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-01 10:20
 */
public class LibLoader {
    protected static final Logger LOG = Logger.getLogger(LibLoader.class);
    private RandomAccessFile raf;

    public LibLoader(String libPath) throws Exception {
        raf = new RandomAccessFile(libPath, "r");
        load();
        IOUtils.closeQuietly(raf);
    }


    private int resourceTypeCount;
    private short[] resourceCountArr;
    private int resourceCounts;
    private Map<Integer, String> resourceIdTypeIndexMap = new LinkedHashMap<>();
    private Map<String, Integer> typeIndexResourceIdMap = new LinkedHashMap<>();
    private int[] lengthTable;
    private int[] jumpTable;

    private void load() throws Exception {
        resourceTypeCount = readInt();
        resourceCountArr = new short[resourceTypeCount];
        for (int i = 0; i < resourceTypeCount; i++) {
            resourceCountArr[i] = readShort();
            resourceCounts += resourceCountArr[i];
        }

        lengthTable = new int[resourceCounts];
        for (int i = 0; i < lengthTable.length; i++) {
            lengthTable[i] = readInt();
        }
        jumpTable = new int[resourceCounts];
        for (int i = 0; i < jumpTable.length; i++) {
            jumpTable[i] = readInt();
        }

        // get all type & index
        for (int i = 0; i < resourceCounts; i++) {
            int type = 0;
            int index = 0;
            int preIndex = 0;
            int nextIndex = 0;
            for (int j = 0; j < resourceCountArr.length; j++) {
                nextIndex += resourceCountArr[j];
                if (i >= preIndex && i < nextIndex) {
                    type = j;
                    index = i - preIndex;
                    break;
                }
                preIndex += resourceCountArr[j];
            }
            LOG.debug("resourceCount" + i + "  <<type=" + type + " & index=" + index + ">>");
            resourceIdTypeIndexMap.put(i, type + "," + index);
            typeIndexResourceIdMap.put(type + "," + index, i);
        }

        int offset = 4 + resourceTypeCount * 2 + resourceCounts * 8;
        Set<Map.Entry<Integer, String>> resourceIdTypeIndexEntries = resourceIdTypeIndexMap.entrySet();
        for (Map.Entry<Integer, String> resourceIdTypeIndexEntry : resourceIdTypeIndexEntries) {
            Integer key = resourceIdTypeIndexEntry.getKey();
            String value = resourceIdTypeIndexEntry.getValue();
            int length = lengthTable[key];
            int jump = jumpTable[key];
            byte[] fileContent = new byte[length];
            raf.seek(offset + jump);
//            long filePointer = raf.getFilePointer();
//            long jump_ = filePointer - offset;
//            LOG.debug("length=" + length + ", jump=" + jump + ", jump_" + jump_);
            raf.read(fileContent);
            LOG.debug("fileContent: " + bytes2HexString(fileContent));
        }

        debugOutput();
    }

    private static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            r += byte2HexString(b[i]);
        }

        return r;
    }
    private static String byte2HexString(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }

        return hex.toUpperCase();
    }
    private void debugOutput() {
        LOG.debug("resourceTypeCount=" + resourceTypeCount);
        for (int i = 0; i < resourceCountArr.length; i++) {
            LOG.debug("resourceCounts["+i+"]=" + resourceCountArr[i]);
        }

        LOG.debug("resourceIdTypeIndexMap=" + resourceIdTypeIndexMap);
        LOG.debug("typeIndexResourceIdMap=" + typeIndexResourceIdMap);
    }


    /**
     * get int (4 bytes)
     */
    int readInt() throws Exception {
        int wTmp = readShort();
        wTmp |= readShort() << 16;

        return wTmp;
    }

    /**
     * get int/short (2 bytes)
     */
    short readShort() throws Exception {
        int low = raf.read();
        int high = raf.read();
        return (short)(low | (high << 8));
    }
}
