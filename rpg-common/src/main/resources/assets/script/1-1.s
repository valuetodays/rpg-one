attr 1 1
scenename '百草地'
@ enemies 51
battleImage "001-Grassland01.jpg"

@ 地图上的trigger共有如下几种：
@  传送门
@  npc
@  透明宝箱
@  宝箱

createtransfer 201 0 4
createtransfer 201 0 3
createtransfer 202 23 11
createtransfer 202 23 12

createchest 301 9 3 yes
createnpc 105 8 3 15 1
createnpc 106 7 3 15 1
createnpc 107 6 3 15 1

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@  用于测试
createnpc 14 8 10 15 1
set 30
set 1
set 2
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@ 测试，直接去打护剑神
@ loadmap 1 8 7 11 9 17

if 1 见师傅
addmoney 20000
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ showgut中可以使用`br`来换行
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
showgut '引：`br`    天地玄黄，宇宙洪荒  自古道魔不两立，世虽以道为正，道魔之争却从未休止。究其因，何以道正而魔始未灭，属正邪者，心也！正所谓：`br`\
道非道，魔非魔，善恶在人心！`br` \
江湖中有耳朵的人，绝无一人没有听见过“无机道长”这人的名字，江湖中有眼睛的人，也绝无一人不想瞧瞧“无机道长”绝世风采和他的绝代神功。\
只因为任何人都知道，世上绝没有一个少女能抵挡“无机道长”的微微一笑，也绝没有一个英雄能抵挡“无机道长”的轻轻一剑！`br`\
任何人都相信，“无机道长”的剑非但能在百万军中取主帅之首级，也能将一根头发分成两根，而他的笑，却可令少女的心碎。`br`\
三月廿八晚，无机和当时危害人间的大魔头——赤血天魔依约在三清山的伏魔洞前进行生死决斗。\
决斗后，无机负伤而归，赤血天魔不知所终。决斗结果无人知晓。`br`\
二十年后……'

@        测试装备 开始
@addgoods 2002
@equip 1 2002
@addgoods 3001
@equip 1 3001
@addgoods 4001
@equip 1 4001
@        测试装备 结束

playbgm
say 1 '柳清风' LEFT '小蝴蝶，不要跑……'
npcstep 0 UP 1
@animation 3 50 100
say 1 '柳清风' RIGHT '小蝴蝶，不要跑……'
npcstep 0 LEFT 1
say 1 '柳清风' LEFT '小蝴蝶，快出来……'
npcstep 0 RIGHT 1
say 1 '柳清风' LEFT '小蝴蝶，不要跑……'
npcstep 0 DOWN 1
say 1 '柳清风' LEFT '小蝴蝶……，哪去了？？'
npcstep 0 UP 1

createnpc 2 29 8 2 1
messagebox '师弟出现'
move 2 LEFT
move 2 LEFT
move 2 LEFT
move 2 LEFT
move 2 LEFT
move 2 LEFT
move 2 LEFT
move 2 LEFT
move 2 UP
move 2 UP
move 2 UP
move 2 UP
move 2 RIGHT
move 2 RIGHT
move 2 UP
move 2 UP

npcstep 2 RIGHT 1
npcstep 0 LEFT 1

say 2 '师弟' LEFT '师兄，原来你在这啊，师父找不到你，正在无机阁大发雷霆呢，你快点到无机阁见师傅吧。'
say 1 '柳清风' RIGHT '好，你先走，我就去。'
move 2 DOWN
move 2 DOWN
move 2 LEFT
move 2 LEFT
move 2 DOWN
move 2 DOWN
move 2 DOWN
move 2 DOWN
move 2 RIGHT
move 2 RIGHT
move 2 RIGHT
move 2 RIGHT
move 2 RIGHT
move 2 RIGHT
move 2 RIGHT
move 2 RIGHT
DELETENPC 2

@设置事件，见师傅
set 1
return

见师傅:
return


三清宫:
loadmap 1 2 1 6 0 0
return

先见师傅吧:
say 1 '柳清风' LEFT '还是先去见师父再来玩吧。'
return

百草地西:
if 1 先见师傅吧
loadmap 1 3 18 10 12 0
return


玉兰草:
if 已得玉兰草 已打开宝箱
messagebox '得到玉兰草'
set 已得玉兰草
return

钨龙剑:
if 3 已打开宝箱
@ TODO achievement '钨龙剑成就！'
set 3
return

测试战斗1个怪:
fight "battle_image.png" 51
return

测试战斗:
fight "battle_image.png" 51 51 51
return

测试截屏:
screenshot "仅供测试"
return

等于100:
say 2 '师弟' NONE '师兄，我知道了，m的值等于${m}，厉害吧，我还知道`y`你的国家`/y`是${user.country}。'
return

testForAll:
say 2 '师弟' NONE '师兄，看！我能变天！哈哈哈'
setweather rain
setvar n 100
addvar n 1
subvar n 1
copyvar m n
ifvar m 100 等于100
say 2 '师弟' NONE '师兄，看我表情行事。'
emotion 14 1
iflevel 1 20 没有30级
ifEquip 1 2002 没有木剑
ifMoney 3000 没有三千铜钱
addgoods 1
addmoney 20000
say 2 '师弟' NONE '师兄，原来你在这啊，师父找不到你，正在无机阁大发雷霆呢，你快点到无机阁见师傅吧。'
buy 1 2 3 4 5
return

没有30级:
say 2 '师弟' NONE '师兄，你到30级来找我，我告诉你一个秘密。'
return

没有木剑:
say 2 '三清弟子' NONE '只有佩带`y`木剑`/y`，方可证明是我三清宫弟子。'
return

没有三千铜钱:
say 2 '师弟' NONE '师兄，你这太寒酸了吧，连3000铜钱都没有。'
return

测试宝箱:
emotion 0 5
if 11001 已打开宝箱
@ 得到铁剑
addgoods 2002
openchest 301
set 11001
return

trigger 101 玉兰草
trigger 102 钨龙剑
trigger 105 测试战斗
trigger 106 测试战斗1个怪
trigger 107 测试截屏

trigger 14 testForAll

trigger 201 百草地西
trigger 202 三清宫

trigger 301 测试宝箱