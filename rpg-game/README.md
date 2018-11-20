## rpg-game

#### desc
`rpg-game` 是一个使用纯Java语言编写的一个游戏，辅以apache工具包。请运行billy.rpg.game.GameFrame查看效果。
`rpg-game` is a game with pure java & some utils of apache. It contains many as follows:
- image (png) read and show
- map editor, role editor, animation editor
- show map(two-dimensional array), ground-layer, npc-layer, tree-layer, event-layer, scroll-screen
- deal with battle, attack/beating[TODO] animation(common attack/beating & magic attack/beating), hp 
decrease/increase, etc;


#### 2015-09-16

execute the method `com.billy.rpg.game.scriptParser.ScriptParser#main()` to show ...

#### 2016-05-11 
add `bbk a rpg` development tools in `docs/`


#### 2016-11-30
ILoader 
    IResourceLoader  IMapResourceLoader IScriptResourceLoader 
    IItemLoader

#### 2016-12-07 tag1.0
    Now game is runnable by ui( and console), with script/map/tile/role to controller movement of player(a tree stock).
    next is to set unwalkable tile, show dialog, etc....
    
    execute the method `com.billyrupeng.Main.main(String[])` to show by ui
    

#### 2016-12-08 tag1.1
    show unwalkable tile, bgLayer and npcLayer of map, npc.
    
    
     
#### 2017-01-09 16:35
    show dialog with colorful text

     
#### 2017-05-18 18:29
    add head pic in dialog
    
#### 2017-07-07 13:10
    show animation 

#### 2017-07-20 13:20
    meet monster and begin battle. 
    make monster's attack action
    ESC in chooseMonsterScreen and optionScreen
    造成伤害时要显示血量减少
    [普通攻击时，攻击者应移动至目标处，再进行攻击，应有动画]
    对话要能有先后
    指定发生战斗的命令
    可供选择的选择框 choicecmd
    
#### 2017-07-24 11:30
    virtualtable要分情况 全局有效 or 本script文件有效
    [TODO]布尔型变量 or 整数型变量 （布尔型变量用于事件判断，整数可用于ifcmpt/decrease/increase等）
    [TODO] set/unset, use boolean[1024] not map in virtualtable，但是只能使用数字不能使用字符串了，得重新考虑
    showtext/say 要提供选择头像在左/右/无
    
#### 2017-08-22 17:17
    完成滚屏，且处理当地图宽20高15时hero去不了右，下，右下这三个象限的问题
    add npcId argument to the command `createnpc`, and 0 means no event to this npc
    对同一地图中多个宝箱的操作
    BaseCharacter中的height与width已删除
    将talk命令与trigger命令合并成trigger命令，map的坐标事件改由事件触发
    处理地图编辑器中添加npc时的npcId问题，npc层的一个整数的前16位代表npcId,后16位代表tileNum.
  
#### 2017-08-28 17:37
    修复战斗中玩家将多个妖怪中的第一个妖怪kill后，战斗停止的情况
    修正了战斗中攻击者会向被攻击者移动的情况
    
#### 2017-08-29 17:39
    调低了051号小怪的攻击力和和生命值。这样一下遇到三个小怪也不会被一波带走了，同样，玩家一级时也不能秒死051号小怪了
    [TODO]在角色编辑器中添加一个技能触发率，越大表示使用技能的可能性越大
    [TODO]战斗中使用技能攻击 & 技能列表显示 & 技能链
    [TODO]唯一被动 张飞攻击必暴击，刘备有一定机率使受到的伤害降为1
    
#### 2017-08-28 17:39
    [TODO]角色编辑器中添加技能列表，妖怪级别不会升

### 2017-09-05 11:07
    increasemoney decreasemoney命令完成
    increasegoods decreasegoods命令
    GoodsScreen
    [TODO]命令testmoney
    
### 2017-11-28 14:50
    BaseCharacter:
    curFrame;  // 步数 0右，1停止，2左
    direction; // 方向 0下，1左，2右，3上
    add command: move, npcstep, deletenpc
   
### 2017-12-21 13:50
    set的参数中，全是数字就表示是全局变量，其它的全是局部变量，局部变量只在script内有效
    
    
#### 2018-01-13 18:16
    from https://www.cnblogs.com/mq0036/p/4026057.html
    
    
#### 2018-09-27
    use jline to parse command,  not if-elseif-elseif-... but File#listFiles()
    
#### 2018-09-28
    ShowText -> SayCmd
    脚本内容过长时（如say/showgut）以\来接续下一行
    extract dialogTextFormatter
    add createtransferCmd
    show goods list
    show shop ui and buy goods


#### 2018-09-29
    IncreaseGoodsCmd -> AddGoodsCmd;  DecreaseGoodsCmd -> UseGoodsCmd
    IncreaseMoneyCmd -> AddMoneyCmd;DecreaseMoneyCmd -> UseMoneyCmd
    NPCSTEP/move use LEFT/RIGHT/UP/DOWN not 1/2/3/4
    add parser/processor package 
    [TODO] existnpc npcId

#### 2018-10-03
    骨骼系统，栩栩如生：角色图像运用Spine 2D骨骼技巧呈现，随风飘逸的发丝、栩栩如生的动态彷佛角色跃然于屏幕之上，活灵活现
    command : equip xx[roleId] yy[goodsId]
    已可装备武器，计算伤害时，将武器的效果附带上
    
#### 2018-10-16
    将*.map的二进制文件转换成json文件，方便使用
    使用Tiled来创建地图 （见2018-11-14）
    
#### 2018-11-03
    设置 -> 系统 -> 显示装备
    use  SwingUtilities.invokeLater to start game
    user GameFrame.createAndShowGUI()
#### 2018-11-05
    可在行走图中吃药
    [TODO]战斗中使用药物?
#### 2018-11-08
    装备鞋子功能添加
    图像放大与缩小
    TODO 图像旋转
    文本工具类，用于显示“攻击力：100(+23)”的中+23是彩色的情况，用于个人属性查看
    https://www.java.com/en/java_in_action/alice.jsp

#### 2018-11-10
    完成 100(+23)的中+23是彩色的情况，用于个人属性查看
    显示神龙护体特效
    添加rmvx sword4 特技，但使用ps切片保存后的图片背景不透明了
    
#### 2018-11-12
    使用程序分割rmvx的图片
    json格式的动画文件的扩展名为.jani
    [TODO] 使用渐变矩形画hp/mp/exp属性
    神龙护体特效的背景色有问题
    HeroCharacter和MonsterCharacter【是一个】Fighable！
#### 2018-11-14
    https://github.com/bjorn/tiled/tree/master/util/java/libtiled-java的bug：地图文件在/map目录下，图块在/tile目录下，tsx文件也在/tile目录下，一个map文件关联到../tile/1.tsx，但这个map在读取tsx关联和png文件里，却是从/map目录取而非tsx的相对目录。
    使用Tiled来创建地图，但是地图层只保留两层：bgLayer和walkLayer；同时原有的MapEditor已无效了。^\_^|||
    使用Freemarker转换bbk a rpg地图数据 -> 原地图数据 -> TMX
    地图只用于显示地图和行走层，不用于控制传送门和npc和宝箱，因为使用了TMX
#### 2018-11-15
    测试acp命令   git add . && git commit -m $1 && git push
    Q键切换大地图上的玩家人设， 接收不了Tab按键消息

#### 2018-11-16
    提取出rpg-core，供rpg-android和rpg-game使用
    整理rpg-core的依赖，N行代码被改动！！！（看来刚开始的设计不是很好）

#### 2018-11-19
    离开地图后应清空传送门、npc、宝箱
    添加创建宝箱命令CreateChestCmd、打开宝箱命令OpenChestCmd
    添加BuiltinScriptItem、builtin.s、部分重复标签以便复用
    显示场景物品如三清宫
    [TODO] 切换场景再回来后发现宝箱还是关闭着的，但是已经不能再打开了
   
#### 2018-11-20
    the f**king gradle: work differently with the same version idea-community(v2018.2.5), the same gradle version(v4.10.2), and the same OS(Windows7), go back to use maven instead.
    使用ScreenCodeEnum
    完善并删除部分TODO
    add BattleImageCmd [从/battle/目录下取资源，默认为deafult_battle.jpg]
    解决了一个很早就就出现但久未解决的bug：在大地图上显示say/messagebox命令后，窗口会一闪而过，试着把MessageBoxScreen#onKeyUp()的内容移到了MessageBoxScreen.onKeyDown()中，问题解决了。感觉框架上缺少一个消费按键消息的功能。
    [TODO]资源文件的定时扫描加载?
    普通攻击时取攻击者和目标的矩形的中心，攻击即是让两个中心重合
    人物战斗图只有一帧，攻击时该帧向目标发起攻击 [TODO 玩家或怪物被攻击时，有格挡帧]
    修正：怪物攻击玩家时，怪物的贴图应该在玩家上面



