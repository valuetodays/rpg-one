package billy.rpg.game.scriptParser.display;

import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.container.GameContainer;
import billy.rpg.resource.map.MapMetaData;

/**
 *
 * show map by console
 *  
 * @author liulei-frx
 * 
 * @since 2016-12-02 9:55:17
 */
public class ConsoleDisplay implements IDisplay {

    @Override
    public void display() {
        
        printActiveMapC();
        /*
          // only print role
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(posX == j && posY == i){
                    System.out.print("|* ");
                } else {
                    System.out.print("|  ");
                }
            }
            System.out.println("|");
        }*/
    }
    

    /**
     * print map data and role
     */
    private void printActiveMapC() {
        HeroCharacter mm = GameContainer.getInstance().getActiveFileItem().getHero();
        int posX = mm.getPosX();
        int posY = mm.getPosY();

        MapMetaData activeMap = GameContainer.getInstance().getActiveMap();
        int[][] data = activeMap.getNpcLayer();
        for (int i = 0; i < data.length; i++) {
            int lineLen = data[i].length;
            for (int j = 0; j < lineLen; j++) {
                if(posX == j && posY == i){
                    System.out.print("| " + data[i][j] + "(*)");
                } else {
                    System.out.print("| " + data[i][j] + "   ");
                }
            }
            System.out.println("|");
            }
    }

    
}
