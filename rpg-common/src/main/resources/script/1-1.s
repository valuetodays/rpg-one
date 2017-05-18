@attr已不再需要后两个参数，因为现在地图文件(*.map)中已经保存了地图的宽高 2017-05-12
@attr 1 1 15 15
attr 1 1
scenename '新手村'
@showText 'common english charactor'
@showText 'haha,含有中文字符哦亲'
@@@@@messagebox 与showText 不要同时出现， [at 20161223]
@messagebox '欢迎进入小小地图一'
return

haha:
if say0 say0ed
showText '私は有恩报恩，有仇报仇！'
set say0
return

say0ed:
return
 
@set a1
@if a1 Hello
@showText 'showed hello'
@return

Hello:
if a2 Helloed
createnpc 10 5 3
showText '哈喽'
set a2
return

Helloed:
showText '给你说过哈喽了啊'
return

goright:
loadmap 1 2 1 0
return


roshan:
@showText '我是`y`张三`/y`，命令你快去打`r`大龙`/r`！你快去啊，真是不听话，害我说那么多话，真可恶！什么？！你说`b`我废话多`/b`？你再说，再说一次试试？'
showText '我是`y`天大地大我最大张三`/y`，十分、一百分`r`、`/r`一千分、一万分、十万分、百万分郑重地命令你这小厮快去打`r`大龙`/r`！你快去啊，真是不听话，害我说那么多话，真可恶！什么？！你说`b`我废话多`/b`？你再说，再说一次试试？'
@animation arg1 arg2
@          arg1 : which animation
@          arg2 : repeat or not, 1 repeat, 0 not
@animation 1 0
return

@ 肉山，大龙
talk 255 goright
talk 2 roshan

trigger 1 1 Hello
@trigger 14 14 goright
