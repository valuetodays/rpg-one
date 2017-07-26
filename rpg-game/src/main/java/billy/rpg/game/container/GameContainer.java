package billy.rpg.game.container;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.BoxCharacter;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.character.TransferCharacter;
import billy.rpg.game.character.npc.CommonNPCCharacter;
import billy.rpg.game.constants.CharacterConstant;
import billy.rpg.game.loader.*;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.screen.MapScreen;
import billy.rpg.game.item.*;
import billy.rpg.resource.animation.AnimationMetaData;
import billy.rpg.resource.box.BoxImageLoader;
import billy.rpg.resource.level.LevelMetaData;
import billy.rpg.resource.map.MapMetaData;
import billy.rpg.resource.npc.NPCImageLoader;
import billy.rpg.resource.role.RoleMetaData;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private Map<String, MapMetaData> mapCollections; // maps
    private MapMetaData activeMap; // active map
    private NPCImageLoader npcImageLoader;
    private BoxImageLoader boxImageLoader;
    private BattleImageItem battleImageItem;
    private HeadImageItem headImageItem;
    // animation list, each *.ani is an instance of AnimationDataLoaderBean
    private Map<Integer, AnimationMetaData> animationMap;

    private Map<Integer, RoleMetaData> heroRoleMap;
    private Map<Integer, RoleMetaData> monsterRoleMap;
    private Map<Integer, LevelMetaData> levelMetaDataMap;



    // make private 
    private GameContainer () { }
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
     */
    public void load() {
        if (loaded) {
            return ;
        }
        
        bgImageItem = new BgImageItem();
        tileItem = new TileImageItem();
        npcItem = new NpcImageItem();
        roleItem = new RoleImageItem();
        gameAboutItem = new GameAboutImageItem();
        npcImageLoader = new NPCImageLoader();
        boxImageLoader = new BoxImageLoader();
        battleImageItem = new BattleImageItem();
        headImageItem = new HeadImageItem();

        try {
            bgImageItem.load();
            tileItem.load();
            //npcItem.load();
            roleItem.load();
            gameAboutItem.load();
            npcImageLoader.loadNpcs();
            boxImageLoader.loadBoxes();
            battleImageItem.load();
            headImageItem.load();

            loadMapData();
            loadScriptData();
            loadAnimationData();
            loadRoleData();
            loadLevelData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }

    private void loadLevelData() {
        LevelDataLoader ldl = new LevelDataLoader();
        ldl.load();
        levelMetaDataMap = ldl.getLevleMap();
    }

    private void loadRoleData() {
        RoleDataLoader roleDataLoader = new RoleDataLoader();
        roleDataLoader.load();
        heroRoleMap = roleDataLoader.getHeroList();
        monsterRoleMap = roleDataLoader.getMonsterList();
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
        List<ScriptItem> slLoad = sl.load();

        this.scriptItemList = Collections.unmodifiableList(slLoad);
    }


    private void loadMapData() throws Exception {
        MapDataLoader ml = new MapDataLoader();
        ml.load();
        mapCollections = ml.getMapCollections();
    }

    private void loadAnimationData() throws Exception {
        AnimationDataLoader adl = new AnimationDataLoader();
        adl.load();
        animationMap = adl.getAnimationMap();
    }

    /**
     * get specified animation
     * @param number number
     */
    public AnimationMetaData getAnimationOf(int number) {
        return animationMap.get(number);
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
                    npc.setNumber(activeScriptItem.getNpcs().size() + 1001);
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
        activeMap = mapCollections.get(m + "-" + n);
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

    public MapMetaData getActiveMap() {
        return activeMap;
    }

    public GameAboutImageItem getGameAboutItem() {
        return gameAboutItem;
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

    /**
     * get hero role by number
     * @param number number
     */
    public RoleMetaData getHeroRoleOf(int number) {
        return heroRoleMap.get(number);
    }

    /**
     * get monster role by number
     * @param number number
     */
    public RoleMetaData getMonsterRoleOf(int number) {
        return monsterRoleMap.get(number);
    }

    public Image getHeadImageItemOf(int number) {
        return headImageItem.getHead(number);
    }

    public LevelMetaData getLevelMetaDataOf(int number) {
        return levelMetaDataMap.get(number);
    }
}
