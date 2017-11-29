package billy.rpg.resource.map;

/**
 * save the map into file
 *
 * @author liulei
 * @since 2017-05-06 13:30
 */
public class MapSaverContext {
    private MapSaver mapSaver = new MapBinSaver();

    private static MapSaverContext mapSaverContext = new MapSaverContext();
    private MapSaverContext () {}

    public static MapSaverContext getInstance() {
        return mapSaverContext;
    }
    public MapSaver getDefultMapSaver() {
        return mapSaver;
    }

    public MapSaver getMapSaver() {
        return mapSaver;
    }

    public void setMapSaver(MapSaver mapSaver) {
        this.mapSaver = mapSaver;
    }
}
