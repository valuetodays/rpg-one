package com.billy.scriptParser.bean;

import com.billy.constants.MapConstant;
import org.apache.log4j.Logger;

import java.util.List;

// TODO use a full png as map ?
public class MapDataLoaderBean extends DataLoaderBean {
    private static final Logger LOG = Logger.getLogger(MapDataLoaderBean.class);
    // TODO tileId is hard code now
    private String tileId = "001-Grassland01"; // tile image id
    private String mapId;  // map id
    private String name;   // map name
    private int height;
    private int width;
    private int[][] layer1;
    private int[][] layer2;
    private int[][] layer3;
    private int[][] flag;
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int[][] getFlag() {
        return flag;
    }
    public void setFlag(int[][] flag) {
        this.flag = flag;
    }
    public String getMapId() {
        return mapId;
    }
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }
    public int[][] getLayer1() {
        return layer1;
    }
    public void setLayer1(int[][] layer1) {
        this.layer1 = layer1;
    }
    public int[][] getLayer2() {
        return layer2;
    }
    public void setLayer2(int[][] layer2) {
        this.layer2 = layer2;
    }
    public int[][] getLayer3() {
        return layer3;
    }
    public void setLayer3(int[][] layer3) {
        this.layer3 = layer3;
    }
    public String getTileId() {
        return tileId;
    }
    public void setTileId(String tileId) {
        this.tileId = tileId;
    }
    public void parse(String filename, List<String> mapData) {
        parseName(filename);
        parseData(mapData);
        
        LOG.debug(this.height + "/" + this.width);
    }
    
    private void parseData(List<String> mapData) {

        for (int i = 0; i < mapData.size(); i++) {
            String line = mapData.get(i).trim();
            if (line.startsWith(MapConstant.MAP_COMMENT)) {
                continue;
            }
//            System.out.println(line);
            if (line.startsWith(MapConstant.MAP_HEIGHT)) {
                String height = line.substring(MapConstant.MAP_HEIGHT.length());
                height = height.trim();
                this.height = Integer.valueOf(height);
            } else if (line.startsWith(MapConstant.MAP_WIDTH)) {
                String width = line.substring(MapConstant.MAP_WIDTH.length());
                width = width.trim();
                this.width = Integer.valueOf(width);
            } else {
                if (line.equals(MapConstant.MAP_LAYER1_START)) { // layer1
                    int n = 0; 
                    line = mapData.get(++i);
                    LOG.debug("layer1 start");
                    layer1 = new int[height][width];
                    while (!line.equals(MapConstant.MAP_LAYER1_END)) {
                        LOG.debug(line);
                        String[] split = line.split(" ");
                        for (int m = 0; m < split.length; m++) {
                            String s = split[m];
                            layer1[n][m] = Integer.valueOf(s);
                        }
                        line = mapData.get(++i);
                        n++;
                    }
                    LOG.debug("layer1 end");
                } else if (line.equals(MapConstant.MAP_LAYER2_START)) { // layer2
                    int n = 0; 
                    line = mapData.get(++i);
                    LOG.debug("layer2 start");
                    layer2 = new int[height][width];
                    while (!line.equals(MapConstant.MAP_LAYER2_END)) {
                        LOG.debug(line);
                        String[] split = line.split(" ");
                        for (int m = 0; m < split.length; m++) {
                            String s = split[m];
                            layer2[n][m] = Integer.valueOf(s);
                        }
                        line = mapData.get(++i);
                        n++;
                    }
                    LOG.debug("layer2 end");
                } else if (line.equals(MapConstant.MAP_FLAG_START)) { // flag
                    LOG.debug("flag start");
                    line = mapData.get(++i);
                    int n = 0; 
                    flag = new int[height][width];
                    while (!line.equals(MapConstant.MAP_FLAG_END)) {
                       LOG.debug(line);
                       String[] split = line.split(" ");
                       for (int m = 0; m < split.length; m++) {
                           String s = split[m];
                           flag[n][m] = Integer.valueOf(s);
                       }
                       line = mapData.get(++i);
                       n++;
                    } // end of while
                    LOG.debug("flag end");
                    
                }
            }
        } // end of for
        
    }

    private void parseName(String filename) {
        String mapName = filename.substring(0, filename.length() - MapConstant.MAP_EXT.length());
        this.name = mapName;
    }

    public void initMapId(String filename) {
        parseName(filename);
    }
}
