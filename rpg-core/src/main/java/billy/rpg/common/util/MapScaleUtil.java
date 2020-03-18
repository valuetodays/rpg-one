package billy.rpg.common.util;

/**
 * 地图放大工具类
 */
public class MapScaleUtil {

    private MapScaleUtil() {

    }

    /**
     * 放大地图
     * @param originalMap 原始地图
     * @param scale 放大倍数（必须大于1）
     */
    public static int[][] scaleMap(int[][] originalMap, final int scale) {
        if (scale <= 1) {
            throw new RuntimeException("scale should be bigger than 1");
        }
        return scaleMap(originalMap, scale, scale);
    }

    /**
     * 放大地图
     * @param originalMap 原始地图
     * @param scaleX 横向放大倍数（必须大于1）
     * @param scaleY 纵向放大倍数（必须大于1）
     */
    public static int[][] scaleMap(int[][] originalMap, final int scaleX, final int scaleY) {
        if (scaleX <= 1) {
            throw new RuntimeException("scaleX should be bigger than 1");
        }
        if (scaleY <= 1) {
            throw new RuntimeException("scaleY should be bigger than 1");
        }

        int[][] map = new int[originalMap.length*scaleY][originalMap[0].length*scaleX];
        for (int i = 0; i < originalMap.length; i++) {
            for (int j = 0; j < originalMap[0].length; j++) {
                int value = originalMap[i][j];

                for (int k = 0; k < scaleX; k++) {
                    for (int l = 0; l < scaleY; l++) {
                        map[i*scaleY + l][j*scaleX + k] = value;
                    }
                }
            }
        }
        return map;
    }
}
