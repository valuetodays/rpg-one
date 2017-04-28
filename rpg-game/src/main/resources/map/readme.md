### about *.map

the basic usage about *.map file

#### direction 

to show the game scene
1. the scope is a png image such as sea/sky/mountain..., 
2. layer1 is shown [grass, ground...]
3. the role, npc, box, transfer, etc...
4. layer2 [tree, etc...] 


#### structure

flag  (1~99)

- 1~2    1 can walk through 2 cannot
- 3~39   : reserved 
- 40~100       : scene id, even(40, 42, 44...) can walk through, odd(41, 43, 45...) not

FLAG_START ... FLAG_END  flag that can walk 
TILE_START  ... TILE_END  tiles
LAYER_1_START  ...  LAYER_1_END  layer1
LAYER_2_START  ...  LAYER_2_END  layer2



