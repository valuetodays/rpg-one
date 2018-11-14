package test.billy.rpg.merger;

import billy.rpg.mapeditor.MapTransfer;
import billy.rpg.resource.map.BinaryMapSaverLoader;
import billy.rpg.resource.map.MapMetaData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-22 15:33
 */
public class MapTransferTest {

    @Test
    public void testTransfer() throws IOException, TemplateException {
        String basePath = ("D:\\tmp\\fmj\\map");
        File[] files = new File(basePath).listFiles(pathname -> pathname.getName().endsWith(".map"));
        for (File file : files) {
            String ori = basePath + "/" + file.getName();
            String dst = basePath + "/" + file.getName() + "j";
            MapTransfer.transferAsMapFile(ori, dst);
            String dstTmx = basePath + "/";
            try {
                MapMetaData mapMetaData = new BinaryMapSaverLoader().load(dst);
                writeAsTmxFile(mapMetaData, dstTmx);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
    private void writeAsTmxFile(MapMetaData mapMetaData, String dstTmxDirectory) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_21);
        cfg.setClassForTemplateLoading(getClass(), "/freemarkertemplates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        FileWriter fileWriter = new FileWriter(dstTmxDirectory + mapMetaData.getMapId().replace(".mapj", ".tmx"));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        Map<String, Object> context = new HashMap<>();
        context.put("map", mapMetaData);
        convertArrayDataToString(context, mapMetaData);
        Template template = cfg.getTemplate("/tmx.ftl");
        template.process(context, bufferedWriter);
        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();
    }

    private void convertArrayDataToString(Map<String, Object> context, MapMetaData mapMetaData) {
        int width = mapMetaData.getWidth();
        int height = mapMetaData.getHeight();

        String bgLayerStr = "";
        // show map data
        int[][] bgLayer = mapMetaData.getBgLayer();
        for (int xk = 0; xk < height; xk++) {
            for (int y = 0; y < width; y++) {
                if (y == width-1 && xk == height-1) {
                    bgLayerStr += (bgLayer[y][xk] + 1 + "");
                } else {
                    bgLayerStr += (bgLayer[y][xk] + 1 + ", ");
                }
            }
            bgLayerStr += "\n";
        }
        context.put("bgLayerStr", bgLayerStr);
        // show walk data
        String walkLayerStr = "";
        int[][] walk = mapMetaData.getWalk();
        for (int xk = 0; xk < height; xk++) {
            for (int y = 0; y < width; y++) {
                int val = walk[y][xk];
                if (val == 1) { // 可行走
                    val = 2; // 可行走
                } else {
                    val = 0; // 不可行走
                }
                if ( y == width -1 && xk == height-1) {
                    walkLayerStr += (val);
                } else {
                    walkLayerStr += (val + ", ");
                }
            }
            walkLayerStr += "\n";
        }
        context.put("walkLayerStr", walkLayerStr);
    }

}
