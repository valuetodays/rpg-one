attr 1 2

createtransfer 201 0 6
createtransfer 202 22 8

createnpc 11 8 3 2 1
createnpc 12 12 5 2 1
createnpc 13 13 9 2 1
createnpc 14 19 19 2 1
scenename '三清宫'
return

下山:
say 1 '柳清风' RIGHT '终于可以下山了。'
messagebox '游戏结束，恭喜通关~'
animation 3 100 100
animation 12 200 10
return

去百草地:
if 31 要下山
loadmap 1 1 18 12 4 0
return

要下山:
say 1 '柳清风' RIGHT '还是下山除妖要紧。'
return

师傅:
if 31 回去收拾下山吧
if 30 取回伏魔剑
if 2 去取剑
if 1 见师傅
say 3 '无机道人' LEFT "无机阁在三清宫的北部。"
return

回去收拾下山吧:
say 3 '无机道人' LEFT "你回去收拾，下山去吧。记住，除魔卫道，是你的本分，要以天下苍生为念。"
say 1 '柳清风' RIGHT "是，师父。"
return

见师傅:
say 1 '柳清风' LEFT "弟子拜见师父，不知师父急招弟子，有何吩咐？"
say 3 '无机道人' LEFT "哼&$#!一天到晚就知道玩，上次比武大会拿了个倒数第二，现在还不好好练功，就会给我丢脸。"
say 1 '柳清风' LEFT "师父，我不是在玩，我是在练一种我自创的功夫，我打给你看……"
say 3 '无机道人' LEFT "好了好了，听好，为师近观天象，发现山下被一股妖气弥绕，且有渐浓之势，本想你下山去看看。"
say 1 '柳清风' LEFT "好啊，我这就起程。"
say 3 '无机道人' LEFT "听好，看你这样子，下山准又要给我丢脸。你去后山伏魔洞，取出伏魔剑，方可下山。"
say 3 '无机道人' LEFT "要取伏魔剑，先得过三关，第一，打开存放宝剑的机关门；第二，打败护剑兽；第三，躲过落石。也该磨练磨练了，去吧。"
say 1 '柳清风' LEFT "请问师父，过这三关要用什么方法？"
say 3 '无机道人' LEFT "这就要靠你自己了。"
say 1 '柳清风' LEFT "是，师父。"
set 2
return

取回伏魔剑:
say 1 '柳清风' RIGHT "师父，弟子已取回宝剑。"
say 3 '无机道人' LEFT "呵呵，取到了是在我预料之中的！怎么样，没事吧。"
say 1 '柳清风' RIGHT "我拔出宝剑时地动山摇的，好象出了大事了，出山洞时还被石头砸伤了手。"
say 3 '无机道人' LEFT "哦，快去包扎一下吧。把剑放在我这里吧。"
@usegoods 14 12 没剑
messagebox "伏魔剑交给了无机子"
say 3 '无机道人' LEFT "你回去收拾，下山去吧。记住，除魔卫道，是你的本分，要以天下苍生为念。"
say 1 '柳清风' RIGHT "是，师父。"
@事件：下山除蛇妖
set 31
return

没剑:
messagebox "没有宝剑"
return

去取剑:
say 3 '无机道人' LEFT "去吧，取了剑回来见我。"
say 1 '柳清风' LEFT "是，师父。"
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

trigger 201 去百草地
trigger 202 无机阁

trigger 1 下山
trigger 11 师傅
trigger 12 游人1
trigger 13 老王



trigger 14 药房孙师弟
