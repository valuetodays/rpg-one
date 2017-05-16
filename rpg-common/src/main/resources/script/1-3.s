attr 1 3
scenename 'test map 1-3'
showText '欢迎来到故人居'
return


Hello:
showText '你好，你现在在故人居里。'
return

goleft:
loadmap 1 2 17 7
return

talk 255 goleft
trigger 0 0 goleft
trigger 4 4 Hello
