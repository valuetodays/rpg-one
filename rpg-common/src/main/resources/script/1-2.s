attr 1 2
scenename '地图二'
return
 

Hello:
showText '你好，你现在在地图二里。'
return

goleft:
loadmap 1 1 18 14
return

goright:
loadmap 1 3 1 0
return

talk 255 goleft
trigger 0 0 goleft
trigger 2 2 Hello
trigger 8 8 goright
talk 254 goright
