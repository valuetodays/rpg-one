package billy.rpg.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;


public class MapScaleUtilTest {
    private final static Random RANDOM = new Random(System.currentTimeMillis());

    @Test
    public void testScaleMap_error() {
        int[][] map = new int[][] {
                {1,2},
                {1,2},
        };
        String exMsg = "";
        try {
            MapScaleUtil.scaleMap(map, 0);
        } catch (RuntimeException e) {
            exMsg = e.getMessage();
        }
        Assert.assertTrue(exMsg.startsWith("scale should be bigger than 1"));
    }

    @Test
    public void testScaleMap_with_random() {
        for (int count = 0; count < 100; count++) {
            int width = RANDOM.nextInt(70) + 30;
            int height = RANDOM.nextInt(50) + 20;
            int[][] map = new int[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    map[i][j] = RANDOM.nextInt(150)+10;
                }
            }
            for (int scale = 2; scale < 10; scale++) {
                testScale(map, scale);
                testScale(map, scale, scale + 1);
            }
        }

    }

    private void testScale(int[][] map, int scale) {
        testScale(map, scale, scale);
    }
    private void testScale(int[][] map, int scaleX, int scaleY) {
        int[][] scaledMap = MapScaleUtil.scaleMap(map, scaleX, scaleY);
        {
            int height = scaledMap.length;
            int width = scaledMap[0].length;
            Assert.assertEquals(height, map.length * scaleY);
            Assert.assertEquals(width, map[0].length * scaleX);
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int originValue = map[i][j];

                for (int k = 0; k < scaleX; k++) {
                    for (int l = 0; l < scaleY; l++) {
                        Assert.assertEquals(scaledMap[i*scaleY + l][j*scaleX + k], originValue);
                    }
                }
            }
        }
    }

}