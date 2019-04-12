attr 1 8
scenename '伏魔洞'
createtransfer 201 16 31

if 2 八个灯洞
return

@         idToUse x y image type[1 no walk]
八个灯洞:
createnpc 31 16 10 21 1

createnpc 21 10 9 23 1
createnpc 22 10 13 23 1
createnpc 23 11 14 23 1
@createnpc 28 11 8 23 1
goto 检查灯洞
return

检查灯洞:
console '检查灯洞中。。。。'
if 21 灯洞2是否扫荡
goto 有门
return

灯洞2是否扫荡:
if 22 灯洞3是否扫荡
goto 有门
return

灯洞3是否扫荡:
console '最后一个灯洞了。。'
if 23 开门
console '23灯洞？'
goto 有门
return

有门:
@ 门左
createnpc 29 16 14 21 1
@ 门右
createnpc 30 17 14 21 1
return
开门:
console 'deletenpc 29/30'
deletenpc 29
deletenpc 30
return


灯洞1:
say 21 '灯洞1' LEFT '小子，要打架吗？'
set 21
goto 检查灯洞
return

灯洞2:
say 21 '灯洞2' LEFT '小子，要打架吗？'
set 22
goto 检查灯洞
return

灯洞3:
say 21 '灯洞3' LEFT '小子，要打架吗？'
set 23
goto 检查灯洞
return


伏魔洞口:
loadmap 1 7 6 5 0 0
return

门左:
return

门右:
return

神剑守护者:
if 30 已取剑
if 23 要取剑
say 80 '神剑守护者' LEFT '剑乃百刃之君，但伏魔宝剑却不是。'
return

已取剑:
say 80 '神剑守护者' LEFT '该来的，始终都会来的。你好自为之。'
return

要取剑:
say 80 '神剑守护者' LEFT '果然不一般，竟然打败了八位灯洞守卫，也罢，伏魔宝剑就交给你吧。'
say 80 '神剑守护者' LEFT '该来的，始终都会来的。'
say 1 '柳清风' RIGHT 什么意思？'
@事件：取得伏魔剑
set 30
return

trigger 201 伏魔洞口
trigger 21 灯洞1
trigger 22 灯洞2
trigger 23 灯洞3

trigger 29 门左
trigger 30 门右

trigger 31 神剑守护者
