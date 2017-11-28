attr 1 1
scenename '新手村'
monsters


createnpc 9001 8 5 14 1
createnpc 0 8 10 15 2

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ showgut中可以使用`br`来换行
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
showgut '引：`br`    天地玄黄，宇宙洪荒  自古道魔不两立，世虽以道为正，道魔之争却从未休止。究其因，何以道正而魔始未灭，属正邪者，心也！正所谓：`br`    道非道，魔非魔，善恶在人心！`br`江湖中有耳朵的人，绝无一人没有听见过“无机道长”这人的名字，江湖中有眼睛的人，也绝无一人不想瞧瞧“无机道长”绝世风采和他的绝代神功。只因为任何人都知道，世上绝没有一个少女能抵挡“无机道长”的微微一笑，也绝没有一个英雄能抵挡“无机道长”的轻轻一剑！`br`任何人都相信，“无机道长”的剑非但能在百万军中取主帅之首级，也能将一根头发分成两根，而他的笑，却可令少女的心碎。`br`三月廿八晚，无机和当时危害人间的大魔头——赤血天魔依约在三清山的伏魔洞前进行生死决斗。决斗后，无机负伤而归，赤血天魔不知所终。决斗结果无人知晓。`br`二十年后……'


@@@@@@@@@@@@@@@@@@@@@@@@@@
@ showText headImg-id location[1左2右] text
@@@@@@@@@@@@@@@@@@@@@@@@@@

showText 1 1 '小蝴蝶，不要跑……'
@@@@@@@@@@@@@@@@@@@@@
@ NPCSTEP id faceto step
@@@@@@@@@@@@@@@@@@@@@
npcstep 0 3 1
@animation 3 50 100
showText 1 2 '小蝴蝶，不要跑……'
npcstep 0 1 1
showtext 1 1 '小蝴蝶，快出来……'
npcstep 0 2 1
showtext 1 1 '小蝴蝶，不要跑……'
npcstep 0 0 1
showtext 1 1 '小蝴蝶……，哪去了？？'
npcstep 0 3 1

createnpc 2 0 2 1 1
npcstep 2 3 1
npcstep 0 0 1

showtext 2 1 '师兄，原来你在这啊，师父找不到你，正在无机阁大发雷霆呢，你快点到无机阁见师傅吧。'
showtext 1 2 '好，你先走，我就去。'
move 2 0
move 2 0
move 2 0
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 0
move 2 2
move 2 2
move 2 3
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2
move 2 2

move 2 2
DELETENPC 2
return



