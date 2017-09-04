package billy.rpg.common.util;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-01 14:50
 */
public class ByteHexStringUtil {


    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            r += byte2HexString(b[i]);
        }

        return r;
    }

    public static String byte2HexString(byte b) {
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
