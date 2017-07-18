package billy.rpg.game.scriptParser.bean;

import billy.rpg.game.constants.MapConstant;
import org.apache.log4j.Logger;

// TODO use a full png as map ?
@Deprecated
public class MapDataLoaderBean {
    private static final Logger LOG = Logger.getLogger(MapDataLoaderBean.class);

    private String tileId; // tile image id
    private String mapId;  // map id
    private String name;   // map name
    private int height;
    private int width;
    private int[][] bgLayer;
    private int[][] npcLayer;
    private int[][] fgLayer;
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
    public int[][] getBgLayer() {
//        String s = "";
//        for (int i = 0; i < bgLayer.length; i++) {
//            for (int j = 0; j < bgLayer[i].length; j++) {
//                s += "# " + bgLayer[i][j];
//            }
//            s += "\n";
//        }
//        System.out.println(s);
        return bgLayer;
    }
    public void setBgLayer(int[][] bgLayer) {
        this.bgLayer = bgLayer;
    }
    public int[][] getNpcLayer() {
//        String s = "";
//        for (int i = 0; i < npcLayer.length; i++) {
//            for (int j = 0; j < fgLayer[i].length; j++) {
//                s += "," + npcLayer[i][j];
//            }
//            s += "\n";
//        }
//        System.out.println(s);
        return npcLayer;
    }
    public void setNpcLayer(int[][] npcLayer) {
        this.npcLayer = npcLayer;
    }
    public int[][] getFgLayer() {
//        String s = "";
//        for (int i = 0; i < fgLayer.length; i++) {
//            for (int j = 0; j < fgLayer[i].length; j++) {
//                s += "$ " + fgLayer[i][j];
//            }
//            s += "\n";
//        }
//        System.out.println(s);
        return fgLayer;
    }
    public void setFgLayer(int[][] fgLayer) {
        this.fgLayer = fgLayer;
    }
    public String getTileId() {
        return tileId;
    }
    public void setTileId(String tileId) {
        this.tileId = tileId;
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


}
