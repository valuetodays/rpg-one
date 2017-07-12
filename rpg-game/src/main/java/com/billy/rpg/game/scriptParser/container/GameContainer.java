package com.billy.rpg.game.scriptParser.container;

import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.character.BoxCharacter;
import com.billy.rpg.game.character.HeroCharacter;
import com.billy.rpg.game.character.NPCCharacter;
import com.billy.rpg.game.character.TransferCharacter;
import com.billy.rpg.game.character.npc.CommonNPCCharacter;
import com.billy.rpg.game.constants.CharacterConstant;
import com.billy.rpg.game.screen.BaseScreen;
import com.billy.rpg.game.screen.MapScreen;
import com.billy.rpg.game.scriptParser.bean.AnimationDataLoaderBean;
import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.bean.MapDataLoaderBean;
import com.billy.rpg.game.scriptParser.cmd.CmdBase;
import com.billy.rpg.game.scriptParser.item.*;
import com.billy.rpg.game.scriptParser.item.skill.TransferImageItem;
import com.billy.rpg.game.scriptParser.loader.data.AnimationDataLoader;
import com.billy.rpg.game.scriptParser.loader.data.MapDataLoader;
import com.billy.rpg.game.scriptParser.loader.data.ScriptDataLoader;
import com.billy.rpg.resource.box.BoxImageLoader;
import com.billy.rpg.resource.monster.MonsterDataLoader;
import com.billy.rpg.resource.npc.NPCImageLoader;
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
public class GameContainer {
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
    private BoxImageLoader boxImageLoader;
    private BattleImageItem battleImageItem;
    private List<AnimationDataLoaderBean> animationList; // animation list;

    // TODO loader不应作为类属性，它的返回值应是类属性，loader本应作为一个方法的局部变量
    private MonsterDataLoader monsterDataLoader;


    
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
        boxImageLoader = new BoxImageLoader();
        battleImageItem = new BattleImageItem();
        monsterDataLoader = new MonsterDataLoader();
        try {
            bgImageItem.load();
            tileItem.load();
            npcItem.load();
            roleItem.load();
            gameAboutItem.load();
            transferImageItem.load();
            npcImageLoader.loadNpcs();
            boxImageLoader.loadBoxes();
            battleImageItem.load();
            monsterDataLoader.load();

            loadMapData();
            loadScriptData();
            loadAnimationData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
        return null;
    }

    public void startChapter(int m, int n, String pos) {
        // TODO 添加过渡场景
        GameFrame.getInstance().changeScreen(8);
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

    private void loadAnimationData() throws Exception {
        AnimationDataLoader adl = new AnimationDataLoader();
        List<LoaderBean> mlLoad = adl.load();
        List<AnimationDataLoaderBean> aniListTemp = new ArrayList<>();
        for (LoaderBean lb : mlLoad) {
            aniListTemp.add((AnimationDataLoaderBean) lb);
        }
        animationList = Collections.unmodifiableList(aniListTemp);
    }

    public AnimationDataLoaderBean getAnimationOf(int number) {
        for ( AnimationDataLoaderBean adlb : animationList) {
            if (adlb.getNumber() == number) {
                return adlb;
            }
        }
        return null;
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

        // TODO 之前已经加载了，为什么又加载了一次
//        changeActiveMapItemTo(m, n);

        // 第一次加载时没有主角的方向
        int oldDirection = CharacterConstant.DIRECTION_DOWN;
        if (activeScriptItem != null) {
            HeroCharacter hero = activeScriptItem.getHero();
            if (hero != null) {
                oldDirection = hero.getDirection();
            }
        }
        String[] position = pos.split("-");
        activeScriptItem = fi;
        activeScriptItem.reenter();
        activeScriptItem.getHero().setHeight(getActiveMap().getHeight());
        activeScriptItem.getHero().setWidth(getActiveMap().getWidth());
        activeScriptItem.getHero().initPos(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
        activeScriptItem.getHero().setDirection(oldDirection);
        activeScriptItem.initNpcs();
        // 添加 npc start
        int[][] npcLayer = getActiveMap().getNpcLayer();
        for (int i = 0; i < getActiveMap().getWidth(); i++) {
            for (int j = 0; j < getActiveMap().getHeight(); j++) {
                if (npcLayer[i][j] != -1) {
                    NPCCharacter npc = new CommonNPCCharacter();
                    npc.setHeight(getActiveMap().getHeight());
                    npc.setWidth(getActiveMap().getWidth());
                    npc.initPos(i, j);
                    npc.setTileNum(npcLayer[i][j]);
                    activeScriptItem.getNpcs().add(npc);
                }
            }
        }
        // 添加 npc end

        // 添加 transfer/box start
        activeScriptItem.initTransfers();
        activeScriptItem.initBoxes();
        int[][] eventLayer = getActiveMap().getEvent();
        for (int i = 0; i < getActiveMap().getWidth(); i++) {
            for (int j = 0; j < getActiveMap().getHeight(); j++) {
                int tileNum = eventLayer[i][j];
                if (tileNum <= 0xff && tileNum >= 0xef) { // transfer
                    TransferCharacter transfer = new TransferCharacter();
                    transfer.initPos(i, j);
                    activeScriptItem.getTransfers().add(transfer);
                } else if (tileNum == 0xee) { // open-box
                    BoxCharacter box = new BoxCharacter(tileNum);
                    box.initPos(i, j);
                    activeScriptItem.getBoxes().add(box);
                } else if (tileNum == 0xed) { // closed-box
                    BoxCharacter box = new BoxCharacter(tileNum);
                    box.initPos(i, j);
                    activeScriptItem.getBoxes().add(box);
                }

            }
        }
        // 添加 transfer/box end

//        executePrimary();
    }
    public void executePrimary() {
        if (activeScriptItem != null) {
            BaseScreen curScreen = GameFrame.getInstance().getCurScreen();
            if (curScreen instanceof MapScreen) {
                activeScriptItem.executePrimarySection();
            }
        }
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

    public BoxImageLoader getBoxImageLoader() {
        return boxImageLoader;
    }

    public BattleImageItem getBattleImageItem() {
        return battleImageItem;
    }

    public MonsterDataLoader getMonsterDataLoader() {
        return monsterDataLoader;
    }
}
