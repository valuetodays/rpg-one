package com.billy.scriptParser.container;

import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.bean.MapDataLoaderBean;
import com.billy.scriptParser.cmd.CmdBase;
import com.billy.scriptParser.item.*;
import com.billy.scriptParser.item.skill.Skill2ImageItems;
import com.billy.scriptParser.loader.IContainerLoader;
import com.billy.scriptParser.loader.data.MapDataLoader;
import com.billy.scriptParser.loader.data.ScriptDataLoader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game Container
 *   containes many game components.
 *   
 * singleton 
 *
 * @author liulei-frx
 * 
 * @since 2016-12-02 10:38:59
 */
public class GameContainer implements IContainer, IContainerLoader {
    private static final Logger LOG = Logger.getLogger(GameContainer.class);
    private BgImageItem bgImageItem;  // bgImage
    private TileImageItem tileItem;  // tile
    private NpcImageItem npcItem;  // npc
    private RoleImageItem roleItem; // role 
    private GameAboutImageItem gameAboutItem;
    private List<ScriptItem> fileItemList; // scripts
    private ScriptItem activeScriptItem; // active script
    private List<MapDataLoaderBean> mapList; // maps
    private MapDataLoaderBean activeMap; // active map
    private Skill2ImageItems skill2ImageItems;
    
    // make private 
    private GameContainer () {    }
    // the instance 
    private static GameContainer instance = new GameContainer();
    // loaded or not 
    private static boolean loaded = false;
    
    /**
     * load all resources
     * 
     * <ul>
     *      <li>bgImage</li>
     *      <li>tile</li>
     *      <li>role</li>
     *      <li>script</li>
     *      <li>map</li>
     *      <li></li>
     * </ul>
     * 
     * @return un-important
     */
    public List<LoaderBean> load() {
        if (loaded) {
            return null;
        }
        
        bgImageItem = new BgImageItem();
        tileItem = new TileImageItem();
        npcItem = new NpcImageItem();
        roleItem = new RoleImageItem();
        gameAboutItem = new GameAboutImageItem();
        skill2ImageItems = new Skill2ImageItems();
        try {
            bgImageItem.load();
            tileItem.load();
            npcItem.load();
            roleItem.load();
            gameAboutItem.load();
            skill2ImageItems.load();
            new Thread(skill2ImageItems).start();
            loadMapData();
            loadScriptData();

            startChapter(1, 1, "1-2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
        return null;
    }
    public void startChapter(int m, int n, String pos) {
        // init the active map, but it is called in {@link GameContainer#changeActiveScriptItemTo(int, int, String)})
        changeActiveMapItemTo(m, n); 

        // initialize the entry script-item
        changeActiveScriptItemTo(m, n, pos);
        
//        executePrimary();
    }
    
    
    private void loadScriptData() throws Exception {
        ScriptDataLoader sl = new ScriptDataLoader();
        List<LoaderBean> slLoad = sl.load();
        List<ScriptItem> scriptItemList = new ArrayList<>();
        for (LoaderBean lb : slLoad) {
            ScriptItem si = (ScriptItem) lb;
            List<CmdBase> cmdList = si.cmdList;
            LOG.info(cmdList);
            scriptItemList.add(si);
        }
        fileItemList = Collections.unmodifiableList(scriptItemList);
    }


    private void loadMapData() throws Exception {
        MapDataLoader ml = new MapDataLoader();
        List<LoaderBean> mlLoad = ml.load();
        List<MapDataLoaderBean> mapListTemp = new ArrayList<>();
        for (LoaderBean lb : mlLoad) {
            mapListTemp.add((MapDataLoaderBean) lb);
        }
        mapList = Collections.unmodifiableList(mapListTemp);
    }


    //
    public void changeActiveScriptItemTo(int m, int n, String pos) {
        if (fileItemList == null) {
            throw new RuntimeException("fileItemList is null");
        }
        
        ScriptItem fi = null;
        for (ScriptItem f : fileItemList) {
            if((m + "-" + n).equals(f.getFileId())){
                fi = f;
                break;
            }
        }
        
        if (fi == null) {
            throw new RuntimeException("error when loadmap.");
        }

        changeActiveMapItemTo(m, n);
        
        String[] position = pos.split("-");
        activeScriptItem = fi;
        activeScriptItem.reenter();
        activeScriptItem.getMm().setHeight(getActiveMap().getHeight());
        activeScriptItem.getMm().setWidth(getActiveMap().getWidth());
        activeScriptItem.getMm().initPos(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
//        executePrimary();
    }
    public void executePrimary() {
        activeScriptItem.executePrimarySection();
    }
    
    public void changeActiveMapItemTo(int m, int n) {
        if (mapList == null) {
            return ;
        }
        for (MapDataLoaderBean map : mapList) {
            if (("" + m + "-" + n).equals(map.getMapId())) {
                activeMap = map;
            }
        }
   }
    
    
    public BgImageItem getBgImageItem() {
        return bgImageItem;
    }
    public TileImageItem getTileItem() {
        return tileItem;
    }
    public RoleImageItem getRoleItem() {
        return roleItem;
    }
    public NpcImageItem getNpcItem() {
        return npcItem;
    }
    public static GameContainer getInstance() {
        return instance;
    }
    public List<ScriptItem> getFileItemList() {
        return fileItemList;
    }
    public ScriptItem getActiveFileItem() {
        return activeScriptItem;
    }
    public void setActiveFileItem(ScriptItem activeFileItem) {
        this.activeScriptItem = activeFileItem;
    }
    public List<MapDataLoaderBean> getMapList() {
        return mapList;
    }
    public MapDataLoaderBean getActiveMap() {
        return activeMap;
    }
    public GameAboutImageItem getGameAboutItem() {
        return gameAboutItem;
    }
    public Skill2ImageItems getSkill2ImageItems() {
        return skill2ImageItems;
    }
}
