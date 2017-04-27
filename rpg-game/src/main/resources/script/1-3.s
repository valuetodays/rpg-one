attr 1 3 5 5
scenename 'test map 1-3'
showText '欢迎来到故人居'
return


Hello:
showText '你好，你现在在故人居里。'
return

goleft:
loadmap 1 2 7 8
return

trigger 0 0 goleft
trigger 4 4 Hello
