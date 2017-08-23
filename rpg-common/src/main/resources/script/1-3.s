attr 1 3
scenename '故人居'
return


hello:
showText 1000 '你好，你现在在故人居里。'
return

goleft:
loadmap 1 2 17 7
return

trigger 255 goleft
trigger 10 hello
trigger 11 hello
trigger 12 hello

