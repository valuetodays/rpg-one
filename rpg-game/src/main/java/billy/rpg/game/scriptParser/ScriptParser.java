package billy.rpg.game.scriptParser;

import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.scriptParser.display.ConsoleDisplay;
import billy.rpg.game.scriptParser.item.ScriptItem;
import org.apache.log4j.Logger;

import java.util.Scanner;

/**
 * main processer
 *
 * @author t-liulei03
 */
public class ScriptParser {

    private static final Logger LOG = Logger.getLogger(ScriptParser.class);

    private ScriptParser() { }

    /**
     * main program 
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        GameContainer.getInstance().load();
        LOG.info("resources files were read just now.");
 
        //- print global variable 
//       GlobalVirtualTables.printGlobalVariablesListString();

//        fileItem.executeTrigger("Hello");
        main0(4);

        return ;
    }

    public static void main0(final int dir) {
        ConsoleDisplay display = new ConsoleDisplay();
        int input = dir;
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            ScriptItem active = GameContainer.getInstance().getActiveFileItem();
//  TODO           active.initWidthAndHeight(active.getHeight(), active.getWidth());
            active.checkTrigger();
            HeroCharacter mm = active.getHero();
            display.display();
            
            System.out.print("[2/4/6/8]> ");
            input = scan.nextInt();
            
            switch (input) {
                case 2:
                    mm.increaseY();
                    break;
                case 4:
                    mm.decreaseX();
                    break;
                case 6:
                    mm.increaseX();
                    break;
                case 8:
                    mm.decreaseY();
                    break;
                default:
                    System.out.println("Thanks for your playing. Bye bye~");
                    scan.close();
                    System.exit(0);
                    break;
            }  // end of switch()

        }
        
        
    }




}
