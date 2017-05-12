attr 1 2
scenename 'test map 1-2'
showText '欢迎进入地图二'
return
 

Hello:
showText '你好，你现在在地图二里。'
return

goleft:
loadmap 1 1 6 7
return

goright:
loadmap 1 3 1 0
return

trigger 0 0 goleft
trigger 2 2 Hello
trigger 8 8 goright
