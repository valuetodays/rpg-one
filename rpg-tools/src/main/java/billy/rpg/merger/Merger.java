package billy.rpg.merger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-31 14:53
 */
public class Merger {
    protected static final Logger LOG = Logger.getLogger(Merger.class);
    private final Map<String, Integer> extMap;
    private Map<Integer, Integer> resourceTypeCount = new HashMap<>();
    private Map<Integer, Set<String>> resourceSetMap = new HashMap<>();
    private long[] jumpTable;
    private String buffer = "";


    public Merger() {
        final String[] extArr = {".role", ".skl"};
        Map<String, Integer> m = new HashMap<>();
        for (int i = 0; i < extArr.length; i++) {
            m.put(extArr[i], i);
            resourceTypeCount.put(i, 0); // 置空资源数量
            resourceSetMap.put(i, new HashSet<>());
        }
        extMap = Collections.unmodifiableMap(m);
    }

    public void merge(String targetLibPath, List<String> srcFilePathList) throws Exception {
        for (String srcFilePath : srcFilePathList) {
            String ext = srcFilePath.substring(srcFilePath.lastIndexOf("."));
            LOG.debug(ext);
            Integer index = extMap.get(ext);
            if (index == null) {
                throw new RuntimeException("unknown extension: " + ext);
            }
            resourceTypeCount.put(index, resourceTypeCount.get(index) + 1);
            resourceSetMap.get(index).add(srcFilePath); // TODO 要取文件的前10字节进行比较
        }

        int resourcesCount = resourceTypeCount.values().stream().mapToInt(x -> x).sum();
        LOG.debug("resourcesCount=" + resourcesCount);
        jumpTable = new long[resourcesCount];

        final Set<Map.Entry<Integer, Set<String>>> resourceSetMapEntries = resourceSetMap.entrySet();
        for (Map.Entry<Integer, Set<String>> entry : resourceSetMapEntries) {
            Integer key = entry.getKey();
            Set<String> value = entry.getValue();
        }

        {
            // 这段代码加{}是为了防止resourceCountTmp被乱用
            int resourceCountTmp = 0;
            for (Map.Entry<Integer, Set<String>> entry : resourceSetMapEntries) {
                Set<String> filePathSet = entry.getValue();
                for (String filePath : filePathSet) {
                    File f = new File(filePath);
                    jumpTable[resourceCountTmp++] = f.length();
                }
            }
        }

        LOG.debug("resourceTypeCount: " + resourceTypeCount);
        LOG.debug("resourceSetMap: " + resourceSetMap);
        for (int n = 0; n < jumpTable.length; n++) {
            LOG.debug(" " + n + "=" + jumpTable[n]);
        }

        final int resourceTypeCountSize = resourceTypeCount.size();
        buffer += int2HexString(resourceTypeCountSize); // 1 resourceTypeCountSize (4bytes)
        appendLine();

        Set<Map.Entry<Integer, Integer>> resourceTypeCountEntries = resourceTypeCount.entrySet();
        for (Map.Entry<Integer, Integer> entry : resourceTypeCountEntries) {
            Integer value = entry.getValue();
            buffer += short2HexString(value.shortValue()); // 2 n bytes,
        }
        appendLine();

        appendLine();

        //int sectionSize = (resourceTypeCountSize * 6);
        //buffer += int2HexString(sectionSize);

        for (Map.Entry<Integer, Set<String>> entry : resourceSetMapEntries) {
            Integer key = entry.getKey();
            Set<String> filePathSet = entry.getValue();
            for (String filePath : filePathSet) {
                ; //
                byte[] bytes = FileUtils.readFileToByteArray(new File(filePath));
                //buffer += int2HexString(bytes.length);
//                //buffer += bytes2HexString(bytes);
                appendLine();
            }

        }


        LOG.debug("buffer=\n[" + buffer + "\n]");
        writeFile();
    }

    private void writeFile() throws Exception {
        FileOutputStream fos = new FileOutputStream("z:/merge/dat.lib");
        writeHexString(fos, buffer);
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

    private void appendLine() {
        buffer += "";
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

    public static String int2HexString(int n) {
        return short2HexString((short) n) + short2HexString((short) (n >> 16));
    }
    public static String short2HexString(short b) {
        return byte2HexString((byte) (b)) + byte2HexString((byte) (b >> 8));
    }

}
