attr 1 105

createtransfer 201 17 10
scenename '大师兄居'
if 3 有大师兄
if 4 有大师兄
return

有大师兄:
createnpc 105 8 3 15 1
return

三清宫:
loadmap 1 2 10 7 6 21
return

大师兄:
if 4 叮嘱取剑
if 3 如何取剑
return

如何取剑:
say 1 '柳清风' RIGHT '大师兄，师傅让我去取伏魔剑。据说伏魔洞中有`y`八位护剑兽`/y`，还有一位`y`护剑神`/y`，我怕取不出伏魔剑，所以……'
emotion 105 5
say 2 '司空明' LEFT '清风，当年你我同时被师傅收留，而今多久了？'
say 1 '柳清风' RIGHT '大师兄，想来也有十年了。'
say 2 '司空明' LEFT '是啊，十年了，我还是无法回忆起关于`r`司空家`/r`的任何事情，包括你我的身世……'
say 2 '司空明' LEFT '这次师傅让你取剑，实为下山前的历练。'
say 2 '司空明' LEFT '你太过随性，也该去江湖上经历一番了，更应该搜寻自己的身世。'
say 1 '柳清风' RIGHT '大师兄，知道了，我知道了。我这就去取剑。'
say 2 '司空明' LEFT '其实师傅也给我分派了任务，我要往大漠走一遭。'
say 2 '司空明' LEFT '此去大漠，多则三月，少则一月。你要保重。'
say 1 '柳清风' RIGHT '大师兄也要保重，不知大师兄何时出发？'
say 2 '司空明' LEFT '后天就走。'
say 1 '柳清风' RIGHT '（我要速速取回伏魔剑，然后给大师兄送行）'
say 1 '柳清风' RIGHT '大师兄，我去取剑了。'
set 4
return

叮嘱取剑:
say 2 '司空明' LEFT '清风师弟，相信你自己。'
return

trigger 201 三清宫
trigger 105 大师兄
