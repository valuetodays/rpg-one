attr 1 3
scenename '故人居'
return


hello:
showText 1000 '你好，你现在在故人居里。'
return

goleft:
loadmap 1 2 17 7
return

talk 255 goleft
talk 10 hello
talk 11 hello
talk 12 hello
trigger 0 0 goleft

