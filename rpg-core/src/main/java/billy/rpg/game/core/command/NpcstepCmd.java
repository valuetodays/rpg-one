package billy.rpg.game.core.command;

import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.character.walkable.npc.NPCWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.constants.WalkableConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;

import java.util.List;

/**
 * 面向
 * @author liulei@bshf360.com
 * @since 2017-11-28 14:58
 */
public class NpcstepCmd extends CmdBase {
    private int npcId; // 0 = player
    private WalkableConstant.PositionEnum faceTo;
    private int step;


    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        npcId = Integer.parseInt(arguments.get(0));
        faceTo = WalkableConstant.PositionEnum.valueOf(arguments.get(1));
        step = Integer.parseInt(arguments.get(2));
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        if (npcId == 0) {
            ScriptItem active = gameContainer.getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();
            hero.setCurFrame(step);
            hero.setDirection(faceTo);
            logger.debug("change face and step:" + faceTo + "," + step);
        } else {
            List<NPCWalkableCharacter> npcs = gameContainer.getActiveScriptItem().getNpcs();
            for (NPCWalkableCharacter npcCharacter : npcs) {
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
