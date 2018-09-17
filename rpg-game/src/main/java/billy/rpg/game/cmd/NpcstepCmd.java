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

    public NpcstepCmd(int npcid, int faceto, int step) {
        super("npcstep");
        this.npcid = npcid;
        this.faceto = faceto;
        this.step = step;
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

}
