package com.billy.rpg.game.scriptParser.container;

import com.billy.jee.rpg.common.NPCImageLoader;
import com.billy.rpg.game.character.Hero;
import com.billy.rpg.game.character.TransferCharacter;
import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.bean.MapDataLoaderBean;
import com.billy.rpg.game.scriptParser.cmd.CmdBase;
import com.billy.rpg.game.scriptParser.item.*;
import com.billy.rpg.game.scriptParser.item.skill.TransferImageItem;
import com.billy.rpg.game.scriptParser.loader.IContainerLoader;
import com.billy.rpg.game.scriptParser.loader.data.MapDataLoader;
import com.billy.rpg.game.scriptParser.loader.data.ScriptDataLoader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Game Container
 *   contains many game components.
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
    private List<ScriptItem> scriptItemList; // scripts
    private ScriptItem activeScriptItem; // active script
    private List<MapDataLoaderBean> mapList; // maps
    private MapDataLoaderBean activeMap; // active map
    private TransferImageItem transferImageItem;
    private NPCImageLoader npcImageLoader;


    
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
    public synchronized List<LoaderBean> load() {
        if (loaded) {
            return null;
        }
        
        bgImageItem = new BgImageItem();
        tileItem = new TileImageItem();
        npcItem = new NpcImageItem();
        roleItem = new RoleImageItem();
        gameAboutItem = new GameAboutImageItem();
        transferImageItem = new TransferImageItem();
        npcImageLoader = new NPCImageLoader();
        try {
            bgImageItem.load();
            tileItem.load();
            npcItem.load();
            roleItem.load();
            gameAboutItem.load();
            transferImageItem.load();
            npcImageLoader.loadNpcs();
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
        this.scriptItemList = Collections.unmodifiableList(scriptItemList);
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
        if (scriptItemList == null) {
            throw new RuntimeException("scriptItemList is null");
        }
        
        ScriptItem fi = null;
        for (ScriptItem f : scriptItemList) {
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
        activeScriptItem.getHero().setHeight(getActiveMap().getHeight());
        activeScriptItem.getHero().setWidth(getActiveMap().getWidth());
        activeScriptItem.getHero().initPos(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
        activeScriptItem.initNpcs();
        // 添加npc start
        int[][] npcLayer = getActiveMap().getNpcLayer();
        for (int i = 0; i < getActiveMap().getWidth(); i++) {
            for (int j = 0; j < getActiveMap().getHeight(); j++) {
                if (npcLayer[i][j] != -1) {
                    Hero npc = new Hero();
                    npc.setHeight(getActiveMap().getHeight());
                    npc.setWidth(getActiveMap().getWidth());
                    npc.initPos(i, j);
                    npc.setTileNum(npcLayer[i][j]);
                    activeScriptItem.getNpcs().add(npc);
                }
            }
        }
        // 添加npc end

        // 添加transfer start
        activeScriptItem.initTransfers();
        int[][] eventLayer = getActiveMap().getEvent();
        for (int i = 0; i < getActiveMap().getWidth(); i++) {
            for (int j = 0; j < getActiveMap().getHeight(); j++) {
                int tileNum = eventLayer[i][j];
                if (tileNum <= 0xff && tileNum >= 0xef) { // transfer
                    TransferCharacter transfer = new TransferCharacter();
                    transfer.initPos(i, j);
                    activeScriptItem.getTransfers().add(transfer);
                }
            }
        }
        // 添加transfer end

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
    public List<ScriptItem> getScriptItemList() {
        return scriptItemList;
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
    public TransferImageItem getTransferImageItem() {
        return transferImageItem;
    }
    public NPCImageLoader getNpcImageLoader() {
        return npcImageLoader;
    }
}
