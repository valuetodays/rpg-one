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



talk 10 girl
talk 11 girl
talk 12 girl
talk 13 girl
talk 255 goleft
talk 254 goright
@trigger 8 8 goright
