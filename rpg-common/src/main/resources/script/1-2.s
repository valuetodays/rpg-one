attr 1 2

createnpc 11 8 3 2 1

createnpc 12 12 5 2 1
scenename '三清宫'
return


toWest:
loadmap 1 1 19 8 10 1
return

toSouth:
showtext 1 1 '终于可以下山了。'
return

师傅:
if 2 取剑
if 1 见师傅
showtext 3 1 "无机阁在三清宫的中心。"
return

见师傅:
showtext 1 1 "弟子拜见师父，不知师父急招弟子，有何吩咐？"
showtext 3 1 "哼&$#!一天到晚就知道玩，上次比武大会拿了个倒数第二，现在还不好好练功，就会给我丢脸。"
showtext 1 1 "师父，我不是在玩，我是在练一种我自创的功夫，我打给你看……"
showtext 3 1 "好了好了，听好，为师近观天象，发现山下被一股妖气弥绕，且有渐浓之势，本想你下山去看看。"
showtext 1 1 "好啊，我这就起程。"
showtext 3 1 "听好，看你这样子，下山准又要给我丢脸。你去后山伏魔洞，取出伏魔剑，方可下山。"
showtext 3 1 "要取伏魔剑，先得过三关，第一，打开存放宝剑的机关门；第二，打败护剑兽；第三，躲过落石。也该磨练磨练了，去吧。"
showtext 1 1 "请问师父，过这三关要用什么方法？"
showtext 3 1 "这就要靠你自己了。"
showtext 1 1 "是，师父。"
set 2
return

取剑:
showtext 3 1 "去吧，取了剑回来见我。"
showtext 1 1 "是，师父。"
return

游人1:
showtext 1 1 '据说`y`无机道人`/y`是道仙转世，今日一见，果然一副仙像。'
return

trigger 1 toWest
trigger 2 toSouth
trigger 11 师傅
trigger 12 游人1
