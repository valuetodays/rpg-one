package com.billy.rpg.game.scriptParser.bean;

import com.billy.rpg.game.constants.MapConstant;
import org.apache.log4j.Logger;

import java.util.List;

// TODO use a full png as map ?
public class MapDataLoaderBean extends DataLoaderBean {
    private static final Logger LOG = Logger.getLogger(MapDataLoaderBean.class);

    private String tileId; // tile image id
    private String mapId;  // map id
    private String name;   // map name
    private int height;
    private int width;
    private int[][] layer1;
    private int[][] layer2;
    private int[][] layer3;
    private int[][] walk;
    private int[][] event;
    
    
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
    public int[][] getWalk() {
        return walk;
    }
    public void setWalk(int[][] walk) {
        this.walk = walk;
    }
    public String getMapId() {
        return mapId;
    }
    public void setMapId(String mapId) {
        this.mapId = mapId;
    }
    public int[][] getLayer1() {
//        String s = "";
//        for (int i = 0; i < layer1.length; i++) {
//            for (int j = 0; j < layer1[i].length; j++) {
//                s += "# " + layer1[i][j];
//            }
//            s += "\n";
//        }
//        System.out.println(s);
        return layer1;
    }
    public void setLayer1(int[][] layer1) {
        this.layer1 = layer1;
    }
    public int[][] getLayer2() {
//        String s = "";
//        for (int i = 0; i < layer2.length; i++) {
//            for (int j = 0; j < layer3[i].length; j++) {
//                s += "," + layer2[i][j];
//            }
//            s += "\n";
//        }
//        System.out.println(s);
        return layer2;
    }
    public void setLayer2(int[][] layer2) {
        this.layer2 = layer2;
    }
    public int[][] getLayer3() {
//        String s = "";
//        for (int i = 0; i < layer3.length; i++) {
//            for (int j = 0; j < layer3[i].length; j++) {
//                s += "$ " + layer3[i][j];
//            }
//            s += "\n";
//        }
//        System.out.println(s);
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
                } else if (line.equals(MapConstant.MAP_FLAG_START)) { // walk
                    LOG.debug("walk start");
                    line = mapData.get(++i);
                    int n = 0; 
                    walk = new int[height][width];
                    while (!line.equals(MapConstant.MAP_FLAG_END)) {
                       LOG.debug(line);
                       String[] split = line.split(" ");
                       for (int m = 0; m < split.length; m++) {
                           String s = split[m];
                           walk[n][m] = Integer.valueOf(s);
                       }
                       line = mapData.get(++i);
                       n++;
                    } // end of while
                    LOG.debug("walk end");
                    
                }
            }
        } // end of for
        
    }

    private void parseName(String filename) {
        final String mapName = filename.substring(0, filename.length() - MapConstant.MAP_EXT.length());
        this.name = mapName;
    }

    public int[][] getEvent() {
        return event;
    }

    public void setEvent(int[][] event) {
        this.event = event;
    }

    public void initMapId(String filename) {
        parseName(filename);
    }
}
