package billy.rpg.merger;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.ByteHexStringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

/**
 *
 *
 * resource(s)  -->  *.lib
 *
 * @author liulei@bshf360.com
 * @since 2017-08-31 14:53
 */
public class Merger {
    protected static final Logger LOG = Logger.getLogger(Merger.class);
    private final Map<String, Integer> extMap;
    private Map<Integer, Integer> resourceTypeCount = new LinkedHashMap<>();
    private Map<Integer, TreeMap<Integer, String>> resourceSetMap = new LinkedHashMap<>();
    private long[] lengthTable;
    private String lengthTableBytesStr = "";
    private int[] numberTable;
    private String numberTableBytesStr = "";
    private long[] jumpTable;
    private String jumpTableBytesStr = "";
    private String resourceFileBytesStr = "";
    private String resourceTypeCountBytesStr = "";
    private String resourceCountBytesStr = "";
    private static final String[] extArr = {".rol", ".skl"};


    public Merger() {
        Map<String, Integer> m = new HashMap<>();
        for (int i = 0; i < extArr.length; i++) {
            m.put(extArr[i], i);
            resourceTypeCount.put(i, 0); // 置空资源数量
            resourceSetMap.put(i, new TreeMap<>());
        }
        extMap = Collections.unmodifiableMap(m);
    }

    /**
     * 写入规则为
     * <ol>
     *     <li>4bytes     - resourceTypeCountSize</li>
     *     <li>2bytes * N - resourceCount of every type</li>
     *     <li>4bytes * N - numberTable</li>
     *     <li>4bytes * N - lengthTable</li>
     *     <li>4bytes * N - jumpTable</li>
     *     <li>Nbytes     - resourceFileBytesStr</li>
     * </ol>
     * @param targetLibPath targetLibPath
     * @param srcFilePathList srcFilePathList
     * @throws Exception e
     */
    public void merge(String targetLibPath, List<String> srcFilePathList) throws Exception {
        for (String srcFilePath : srcFilePathList) {
            String ext = srcFilePath.substring(srcFilePath.lastIndexOf("."));
            LOG.debug("ext=" + ext);
            Integer index = extMap.get(ext);
            if (index == null) {
                throw new RuntimeException("unknown extension: " + ext);
            }
            resourceTypeCount.put(index, resourceTypeCount.get(index) + 1);
            int number = getNumberOfResource(index, srcFilePath); // 从文件的前8字节中读出id
            LOG.debug("number=" + number);
            resourceSetMap.get(index).put(number, srcFilePath);
        }


        int resourcesCount = resourceTypeCount.values().stream().mapToInt(x -> x).sum();
        LOG.debug("resourcesCount=" + resourcesCount);
        lengthTable = new long[resourcesCount];
        numberTable = new int[resourcesCount];
        jumpTable = new long[resourcesCount];

        Set<Map.Entry<Integer, TreeMap<Integer, String>>> resourceSetMapEntries = resourceSetMap.entrySet();

        {
            // 这段代码加{}是为了防止tmpCount被乱用
            int tmpCount = 0;
            long fileLen = 0L;
            for (Map.Entry<Integer, TreeMap<Integer, String>> entry : resourceSetMapEntries) {
                Integer key = entry.getKey();
                TreeMap<Integer, String> value = entry.getValue();
                Set<Map.Entry<Integer, String>> filePathSet = value.entrySet();
                for (Map.Entry<Integer, String> fileNumberAndPath : filePathSet) {
                    Integer number = fileNumberAndPath.getKey();
                    numberTable[tmpCount] = number;
                    String filePath = fileNumberAndPath.getValue();
                    File f = new File(filePath);
                    lengthTable[tmpCount] = f.length();

                    byte[] bytes = FileUtils.readFileToByteArray(new File(filePath));
                    resourceFileBytesStr += ByteHexStringUtil.bytes2HexString(bytes);
                    jumpTable[tmpCount] = fileLen;
                    fileLen += bytes.length;

                    tmpCount++;
                }
            }
        }

        final int resourceTypeCountSize = resourceTypeCount.size();
        resourceTypeCountBytesStr = ByteHexStringUtil.int2HexString(resourceTypeCountSize); // 1

        Set<Map.Entry<Integer, Integer>> resourceTypeCountEntries = resourceTypeCount.entrySet();
        for (Map.Entry<Integer, Integer> entry : resourceTypeCountEntries) {
            Integer value = entry.getValue();
            resourceCountBytesStr += ByteHexStringUtil.short2HexString(value.shortValue()); // 2 n bytes,
        }

        LOG.debug("resourceTypeCount: " + resourceTypeCount);
        LOG.debug("resourceSetMap: " + resourceSetMap);

        for (int n = 0; n < numberTable.length; n++) {
            LOG.debug("numberTable[" + n + "]=" + numberTable[n]);
            numberTableBytesStr += ByteHexStringUtil.int2HexString(numberTable[n]);
        }

        for (int n = 0; n < lengthTable.length; n++) {
            LOG.debug("lengthTable[" + n + "]=" + lengthTable[n]);
            lengthTableBytesStr += ByteHexStringUtil.int2HexString((int) lengthTable[n]);
        }

        for (int n = 0; n < jumpTable.length; n++) {
            LOG.debug("jumpTable[" + n + "]=" + jumpTable[n]);
            jumpTableBytesStr += ByteHexStringUtil.int2HexString((int) jumpTable[n]);
        }

        writeFile(targetLibPath);
    }

    private void writeFile(String targetLibPath) throws Exception {
        FileOutputStream fos = new FileOutputStream(targetLibPath);
        writeHexString(fos, resourceTypeCountBytesStr);
        writeHexString(fos, resourceCountBytesStr);
        writeHexString(fos, numberTableBytesStr);
        writeHexString(fos, lengthTableBytesStr);
        writeHexString(fos, jumpTableBytesStr);
        writeHexString(fos, resourceFileBytesStr);
        IOUtils.closeQuietly(fos);
    }

    private static void writeHexString(FileOutputStream fos, String hex) throws Exception {
        if (StringUtils.isEmpty(hex)) {
            throw new RuntimeException("illegal hex string");
        }
        if (hex.length()%2 != 0) {
            throw new RuntimeException("illegal hex string");
        }
        for (int i = 0; i < hex.length()/2; i++) {
            String hexValue = hex.substring(i*2, i*2+2);
            Integer integer = Integer.valueOf(hexValue, 16);
            fos.write(integer);
        }
    }

    private int getNumberOfResource(Integer index, String filePath) throws Exception {
        byte[] first8bytes = new byte[8];
        FileInputStream fis = new FileInputStream(filePath);
        IOUtils.read(fis, first8bytes);
        IOUtils.closeQuietly(fis);

        byte[] fileType = new byte[4];
        System.arraycopy(first8bytes, 0, fileType, 0, fileType.length);

        if (!Arrays.equals(extArr[index].getBytes(ToolsConstant.CHARSET), fileType)) {
           throw new RuntimeException("illegal fileType of "+extArr[index]+" : " + filePath);
        }

        byte[] fileNumber = new byte[4];
        System.arraycopy(first8bytes, 4, fileNumber, 0, fileType.length);

        int n = fileNumber[3] | (fileNumber[2] << 8) | (fileNumber[1] << 16) | (fileNumber[0] << 24);
        return n;
    }



}
