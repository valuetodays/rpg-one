attr 1 1
scenename '百草地'
monsters

@loadmap 1 2 24 2 10
@createnpc 9001 8 5 14 1
@createnpc 0 8 10 15 2

createnpc 14 8 10 15 1


increasegoods 1
increasegoods 2
increasegoods 3
increasegoods 4
increasegoods 5
increasegoods 6
increasegoods 7
increasegoods 8
increasegoods 9
increasegoods 10
increasegoods 11
increasegoods 12
increasegoods 13
increasegoods 14
increasegoods 15
increasegoods 16
increasegoods 17

set 1

if 1 见师傅
increasemoney 20000
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ showgut中可以使用`br`来换行
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@showgut '引：`br`    天地玄黄，宇宙洪荒  自古道魔不两立，世虽以道为正，道魔之争却从未休止。究其因，何以道正而魔始未灭，属正邪者，心也！正所谓：`br`
@道非道，魔非魔，善恶在人心！`br
@`江湖中有耳朵的人，绝无一人没有听见过“无机道长”这人的名字，江湖中有眼睛的人，也绝无一人不想瞧瞧“无机道长”绝世风采和他的绝代神功。只因为任何人都知道，世上绝没有一个少女能抵挡“无机道长”的微微一笑，也绝没有一个英雄能抵挡“无机道长”的轻轻一剑！`br`任何人都相信，“无机道长”的剑非但能在百万军中取主帅之首级，也能将一根头发分成两根，而他的笑，却可令少女的心碎。`br`三月廿八晚，无机和当时危害人间的大魔头——赤血天魔依约在三清山的伏魔洞前进行生死决斗。决斗后，无机负伤而归，赤血天魔不知所终。决斗结果无人知晓。`br`二十年后……'

@@@@@@@@@@@@@@@@@@@@@@@@@@
@ say headImg-id name location[LEFT, RIGHT] text
@@@@@@@@@@@@@@@@@@@@@@@@@@

playbgm
say 1 '柳清风' LEFT '小蝴蝶，不要跑……'
@@@@@@@@@@@@@@@@@@@@@
@ NPCSTEP id faceto step
@@@@@@@@@@@@@@@@@@@@@
npcstep 0 3 1
@animation 3 50 100
say 1 '柳清风' RIGHT '小蝴蝶，不要跑……'
npcstep 0 1 1
say 1 '柳清风' LEFT '小蝴蝶，快出来……'
npcstep 0 2 1
say 1 '柳清风' LEFT '小蝴蝶，不要跑……'
npcstep 0 0 1
say 1 '柳清风' LEFT '小蝴蝶……，哪去了？？'
npcstep 0 3 1

createnpc 2 29 8 2 1
messagebox '师弟出现'
move 2 1
move 2 1
move 2 1
move 2 1
move 2 1
move 2 1
move 2 1
move 2 1
move 2 3
move 2 3
move 2 3
move 2 3
move 2 2
move 2 2
move 2 3
move 2 3

npcstep 2 2 1
npcstep 0 1 1

say 2 '师弟' LEFT '师兄，原来你在这啊，师父找不到你，正在无机阁大发雷霆呢，你快点到无机阁见师傅吧。'
say 1 '柳清风' RIGHT '好，你先走，我就去。'
move 2 0
move 2 0
move 2 1
move 2 1
move 2 0
move 2 0
move 2 0
move 2 0
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
DELETENPC 2

@设置事件，见师傅
set 1
return

见师傅:
return


三清宫:
loadmap 1 2 6 6 0 0
return

先见师傅吧:
say 1 '柳清风' LEFT '还是先去见师父再来玩吧。'
return

百草地西:
@if 1 先见师傅吧
loadmap 1 3 14 9 12 0
return


玉兰草:
if 已得玉兰草 玉兰草ed
messagebox '得到玉兰草'
set 已得玉兰草
return

玉兰草ed:
return

钨龙剑:
if 已得钨龙剑 钨龙剑ed
messagebox '得到钨龙剑，这样就可以把木剑卖了'
set 已得钨龙剑
return

钨龙剑ed:
return

testForAll:
increasegoods 1
increasemoney 20000
say 2 '师弟' LEFT '师兄，原来你在这啊，师父找不到你，正在无机阁大发雷霆呢，你快点到无机阁见师傅吧。'
buy 1 2 3 4 5
return

trigger 1 三清宫
trigger 2 百草地西

trigger 101 玉兰草
trigger 102 钨龙剑

trigger 14 testForAll
