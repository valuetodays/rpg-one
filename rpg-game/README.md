## rpg-game

#### desc

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
    [TODO] to add head pic in dialog
    
#### 2017-07-07 13:10
    show animation 

#### 2017-07-20 13:20
    meet monster and begin battle. 
    make monster's attack action
    ESC in chooseMonsterScreen and optionScreen
    [TODO] 造成伤害时要显示血量减少
    [普通攻击时，攻击者应移动至目标处，再进行攻击，应有动画]
    对话要能有先后
    [TODO add command]指定发生战斗的命令
    可供选择的选择框
    
#### 2017-07-24 11:30
    [TODO] virtualtable要分情况
    - 全局有效 or 本script文件有效
    - 布尔型变量 or 整数型变量 （布尔型变量用于事件判断，整数可用于ifcmpt/decrease/increase等）
    [TODO] set/unset, use boolean[1024] not map in virtualtable，但是只能使用数字不能使用字符串了，得重新考虑
    [TODO] showtext 要提供选择头像在左/右/无
    
#### 2017-08-22 17:17
    完成滚屏，且处理当地图宽20高15时hero去不了右，下，右下这三个区域的问题
    add npcId argument to the command `createnpc`, and 0 means no event to this npc
    [TODO] 对同一地图中多个宝箱的操作
    BaseCharacter中的height与width已删除
    将talk命令与trigger命令合并成trigger命令，map的坐标事件改由事件触发
    处理地图编辑器中添加npc时的npcId问题，npc层的一个整数的前16位代表npcId,后16位代表tileNum.
  
#### 2017-08-28 17:37
    修复战斗中玩家将多个妖怪中的第一个妖怪kill后，战斗停止的情况
    修正了战斗中攻击者会向被攻击者移动的情况
    
#### 2017-08-28 17:39
    调低了051号小怪的攻击力和和生命值。这样一下遇到三个小怪也不会被一波带走了，同样，玩家一级时也不能打死051号小怪了
    [TODO]在角色编辑器中添加一个技能触发率，越大表示使用技能的可能性越大
    [TODO]战斗中使用技能攻击 & 技能列表显示 & 技能链
    [TODO]唯一被动 张飞攻击必暴击，刘备有一定机率使受到的伤害降为1