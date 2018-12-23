attr 1 2

createtransfer 201 0 6
createtransfer 202 22 8
createtransfer 202 23 8

createtransfer 204 9 13
createtransfer 204 10 13
createtransfer 205 35 13
createtransfer 205 36 13
createtransfer 206 15 25
createtransfer 207 15 28
createtransfer 208 15 31
createtransfer 209 30 25
createtransfer 210 30 28
createtransfer 211 30 31
createtransfer 212 22 45
createtransfer 212 23 45


createnpc 12 12 5 2 2
createnpc 13 13 9 2 1
createnpc 14 19 19 2 1
createnpc 15 20 30 2 1

createsceneObject 401 19 39 "san_qing_gong_left.png"
createsceneObject 402 21 39 "san_qing_gong_middle.png"
createsceneObject 402 25 39 "san_qing_gong_right.png"

scenename '三清宫'
if 3 无操作
if 2 找师兄
return

找师兄:
say 1 '柳清风' LEFT '取剑那么麻烦的事，我还是先找大师兄问问吧'
set 3
return

下山:
@if 1 见师傅
say 1 '柳清风' RIGHT '终于可以下山了。'
messagebox '游戏结束，恭喜通关~'
animation 3 100 100
animation 12 200 10
return

见师傅:
say 1 '柳清风' RIGHT '还是先去见师傅吧。'
return

去百草地:
if 31 要下山
loadmap 1 1 18 12 4 0
return

要下山:
say 1 '柳清风' RIGHT '还是下山除妖要紧。'
return

游人1:
say 1 '游人1' LEFT 'left,据说`y`无机道人`/y`是道仙转世，今日一见，果然一副仙像。'
say 1 '游人1' RIGHT 'right,据说`y`无机道人`/y`是道仙转世，今日一见，果然一副仙像。'
return

老王:
@@ 不是全数字的变量是脚本内变量
if wang100 老王感谢
say 3 '老王' LEFT '我在三清宫散步，发现钱袋丢了，你能帮我找到吗？有报酬的哦~'
say 1 '柳清风' LEFT "好的好的。"
set wang100
return

老王感谢:
say 3 '老王' LEFT '谢谢你啊，我找了好久没找到，这是100文钱。'
addmoney 100
say 3 '老王' LEFT '（小声嘀咕：钱袋价值百两白银呢，只花了100文钱就找到了，哈哈哈……）'
say 1 '柳清风' LEFT "我怎么感觉不太对劲"
unset wang100
return


药房孙师弟:
@故意加个空格
say 3 '孙师弟' LEFT '师兄，来点儿什么 药药药'
buy 1 2 3 4 5 6
return

无机阁:
loadmap 1 100 10 13 4 9
return

三清宫药房:
loadmap 1 102 10 13 0 0
return

三清宫丹房:
loadmap 1 103 10 11 0 5
return

师傅居:
loadmap 1 104 14 7 2 3
return

大师兄居:
loadmap 1 105 14 7 2 3
return

清风居:
loadmap 1 106 14 7 2 3
return

三清宫厨房:
loadmap 1 101 5 7 0 2
return

普通弟子居1:
loadmap 1 107 4 7 0 1
return

普通弟子居2:
loadmap 1 108 4 7 0 1
return

游人_宝藏:
if 100001 三清坟场宝藏支线
say 3 '游人' LEFT '听说三清坟场藏有宝藏，可从没有人发现过。'
set 100001
return

三清坟场宝藏支线:
say 3 '游人' LEFT '听说三清坟场藏有宝藏，可从没有人发现过。我应该再去看看。'
return

trigger 201 去百草地
trigger 202 无机阁
trigger 204 三清宫药房
trigger 205 三清宫丹房
trigger 206 师傅居
trigger 207 大师兄居
trigger 208 清风居
trigger 209 三清宫厨房
trigger 210 普通弟子居1
trigger 211 普通弟子居2
trigger 212 下山


trigger 12 游人1
trigger 13 老王
trigger 14 药房孙师弟
trigger 15 游人_宝藏
