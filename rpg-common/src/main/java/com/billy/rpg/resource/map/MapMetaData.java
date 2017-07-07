package com.billy.rpg.resource.map;

import java.util.List;

/**
 * <p>map data to save</p>
 *
 * <p>map file data structure</p>
 *
 * <ol>
 * <li>10 bytes map header</li>
 * <li>4 bytes tile id length (in byte)</li>
 * <li>N bytes tile id</li>
 * <li>4 bytes map name length (in byte)</li>
 * <li>N bytes map name</li>
 * <li>4 bytes height</li>
 * <li>4 bytes width</li>
 * <li>4 bytes layers size</li>
 * <li>N bytes width*height*4 4-layers map data</li>
 * </ol>
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2017-05-12 22:10
 */
public class MapMetaData {
    private String mapId; // mapId is map's name, such like 1-1 in 1-1.map, when save map, this field is null
    private String tileId; // tile name
    private String mapName; // map name
    private List<int[][]> layers; // map data
    private int height;
    private int width;

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public List<int[][]> getLayers() {
        return layers;
    }

    public void setLayers(List<int[][]> layers) {
        this.layers = layers;
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

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        if (mapId != null) {
            if (mapId.endsWith(".map")) {
                mapId = mapId.substring(0, mapId.length()-".map".length());
            }
        }
        this.mapId = mapId;
    }


}
