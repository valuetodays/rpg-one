package test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * TODO(这里用一句话描述这个类的作用)
 *
 * @author liulei
 * @since 2017-05-06 15:38
 */
public class MapLoaderTest {
    private static final String MAP_HEADER = "map header";

    @Test
    public void testGetData() throws UnsupportedEncodingException {
//        byte[] bytes = MAP_HEADER.getBytes("utf-8");
//        int length = bytes.length;
//        byte[] bMapHead = new byte[length];
//        System.out.println(bMapHead.length);


        String mapName = "百草地";
        byte[] mapNameBytes = mapName.getBytes("utf-8");
        System.out.println("len: " + mapNameBytes.length);
        for (byte mapNameByte : mapNameBytes) {
            System.out.println(Integer.toHexString(Byte.valueOf(mapNameByte)));
        }

    }
}
