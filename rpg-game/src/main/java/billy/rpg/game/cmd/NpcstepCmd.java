package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.resource.item.ScriptItem;

import java.util.List;

/**
 * 面向
 * @author liulei@bshf360.com
 * @since 2017-11-28 14:58
 */
public class NpcstepCmd extends CmdBase {
    private int npcid; // 0 = player
    private int faceto;
    private int step;


    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        npcid = Integer.parseInt(arguments.get(0));
        faceto = Integer.parseInt(arguments.get(1));
        step = Integer.parseInt(arguments.get(2));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        if (npcid == 0) {
            ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
            HeroCharacter hero = active.getHero();
            hero.setCurFrame(step);
            hero.setDirection(faceto);
            logger.debug("change face and step:" + faceto + "," + step);
        } else {
            List<NPCCharacter> npcs = GameFrame.getInstance().getGameContainer().getActiveScriptItem().getNpcs();
            for (NPCCharacter npcCharacter : npcs) {
                if (npcid == npcCharacter.getNumber()) {
                    npcCharacter.setCurFrame(step);
                    npcCharacter.setDirection(faceto);
                }
            }
        }
        return 0;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public int getArgumentSize() {
        return 3;
    }

}
