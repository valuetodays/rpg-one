package billy.rpg.game.cmd;

import billy.rpg.game.GameFrame;
import billy.rpg.game.character.HeroCharacter;
import billy.rpg.game.character.NPCCharacter;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.constants.CharacterConstant;
import billy.rpg.game.resource.item.ScriptItem;

import java.util.List;

/**
 * 面向
 * @author liulei@bshf360.com
 * @since 2017-11-28 14:58
 */
public class NpcstepCmd extends CmdBase {
    private int npcId; // 0 = player
    private CharacterConstant.PositionEnum faceTo;
    private int step;


    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        npcId = Integer.parseInt(arguments.get(0));
        faceTo = CharacterConstant.PositionEnum.valueOf(arguments.get(1));
        step = Integer.parseInt(arguments.get(2));
    }

    @Override
    public int execute(CmdProcessor cmdProcessor) {
        if (npcId == 0) {
            ScriptItem active = GameFrame.getInstance().getGameContainer().getActiveScriptItem();
            HeroCharacter hero = active.getHero();
            hero.setCurFrame(step);
            hero.setDirection(faceTo);
            logger.debug("change face and step:" + faceTo + "," + step);
        } else {
            List<NPCCharacter> npcs = GameFrame.getInstance().getGameContainer().getActiveScriptItem().getNpcs();
            for (NPCCharacter npcCharacter : npcs) {
                if (npcId == npcCharacter.getNumber()) {
                    npcCharacter.setCurFrame(step);
                    npcCharacter.setDirection(faceTo);
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
