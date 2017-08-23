attr 1 2
scenename '地图二'
return
 

girl:
showText 1000 '你好，你现在在`r`河流之地`/r`。什么？你问我这里为什么没有河流？我怎么知道？！'
return

goleft:
loadmap 1 1 18 14
return

goright:
loadmap 1 3 1 0
return



trigger 10 girl
trigger 11 girl
trigger 12 girl
trigger 13 girl
trigger 255 goleft
trigger 254 goright
