@attr已不再需要后两个参数，因为现在地图文件(*.map)中已经保存了地图的宽高 2017-05-12
@attr 1 1 15 15
attr 1 1
scenename '新手村'
monsters 51
@monsters
@showText 'common english charactor'
@showText 'haha,含有中文字符哦亲'
@messagebox '欢迎进入小小地图一'
@ will use createbox
createnpc 9001 8 5 14 1
createnpc 0 8 10 15 2
return

say0ed:
return


npc1new:
showtext 1510 '好巧，又见到你了。'
showtext 1510 '这些送给你吧。'
increasegoods 1
increasegoods 2
increasegoods 3
increasegoods 4
increasegoods 5
increasegoods 6
increasegoods 7
increasegoods 8
increasegoods 9
increasegoods 10
increasegoods 11
increasegoods 12
increasegoods 13
increasegoods 14
increasegoods 15
increasegoods 16
increasegoods 17
return

npc1:
if npc1ed npc1new
showtext 1510 '啊！你怎么沦落至此地？'
showtext 1510 '算了，这是100金币。给你吧。'
inscreasemoney 100
showtext 1510 '不对，还是给你80金币吧。'
descreasemoney 20
showtext 1675 '十分感谢，定会相报。'
set npc1ed
return


@set a1
@if a1 Hello
@showText 'showed hello'
@return

hello:
if a2 helloed
@         x  y n type
@ createnpc 9 5 2 1
showText 1675 '哈喽'
set a2
return

helloed:
@showText 1675 '给你说过哈喽了啊'
@ choice title choice1 choice2 choice3 label1 label2 label3
choice '我们说过话了吗' '说过了' '没有说过' '记不得了' aaa bbb ccc
@showText 1675 '哈哈，你还没做选择呢'
return

aaa:
showText 1675 '果然说过话啊，我就在说怎么看你那么`r`脸熟`/r`。'
decreasegoods 1 1
showText 1675 '谢谢你的止血草'
return

bbb:
showText 1675 '我看也是，我根本`y`没有见过你`/y`。'
return

ccc:
showText 1675 '神经病吧你。'
return


npc2:
showtext 1675 '我是从`y`地图编辑器`/y`中出现的。'
return

goright:
@animation 100 0 0
loadmap 1 2 1 0
return

@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@ box1这个tag说明showText命令是阻塞式的，当执行该命令时，下一句命令不会执行
@ 而animation命令没有完成时也不会执行下面的语句
@@@@@@@@@@@@@@@@@@@@@@@@@@@@
box1:
showText 1675 '这是一个宝箱，可是`y`打不开`/y`，好气啊！'
showText 1675 '第二个窗口对话。'
@播放动画 参数是要播放的动画编号 屏幕posx 屏幕posy
animation 3 50 100
animation 11 50 100
@@if say0 say0ed
showText 1675 '私は有恩报恩，有仇报仇！'
animation 12 50 100
@@set say0
return

roshan:
showText 1675 '我是`y`大BOSS`/y`，我怕谁！`r`毛宁`/r`我都不怕！说过多少次了，我不怕毛宁不怕不怕，我小毛要吊打毛宁！'
@showText 1675 '我是`y`张三`/y`，命令你快去打`r`大龙`/r`！你快去啊，真是不听话，害我说那么多话，真可恶！什么？！你说`b`我废话多`/b`？你再说，再说一次试试？'
@showText 1675 '我是`y`天大地大我最大张三`/y`，十分、一百分`r`、`/r`一千分、一万分、十万分、百万分郑重地命令你这小厮快去打`r`大龙`/r`！你快去啊，真是不听话，害我说那么多话，真可恶！什么？！你说`b
`我废话多`/b`？你再说，再说一次试试？'
@showText 1675 '第二段对话开始了'
@showText 1675 '第三段对话也来了'
return



trigger 255 goright
@ 肉山，大龙
trigger 2 roshan
trigger 237 box1
trigger 555 hello
trigger 9001 npc1
trigger 9901 npc2

