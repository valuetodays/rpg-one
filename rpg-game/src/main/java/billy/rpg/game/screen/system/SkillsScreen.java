package billy.rpg.game.screen.system;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameData;
import billy.rpg.game.GameFrame;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.screen.BaseScreen;
import billy.rpg.game.util.KeyUtil;
import billy.rpg.resource.skill.SkillMetaData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-10-08 15:49:55
 */
public class SkillsScreen extends BaseScreen {
    private SystemUIScreen systemScreen;
    // TODO 待分页
    private int skillIndex = 0; // 一屏显示10条技能，0~9
    private static int MAX_SHOW = 10;
    private int roleId = 0;

    public SkillsScreen(SystemUIScreen systemScreen) {
        this.systemScreen = systemScreen;
    }

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        GameData gameData = GameFrame.getInstance().getGameData();
        g.setFont(new Font("楷体", Font.BOLD, 18));
        g.setColor(Color.YELLOW);

        List<SkillMetaData> skills = GameFrame.getInstance().getGameData().getSkillsOf(roleId);

        if (CollectionUtils.isNotEmpty(skills)) {
            int end = Math.min(MAX_SHOW, skills.size());
            for (int i = 0; i < end; i++) {
                SkillMetaData skillMetaData = skills.get(i);
                g.drawString(skillMetaData.getName() + "(耗mp："+skillMetaData.getConsume()+")",
                        180,70 + i * 25);
            }

            g.drawRect(175, 50 + skillIndex *25, 290, 25); // 当前选中物品
            g.drawRect(470, 50, 120, 400);

            List<String> formattedDesc = formatDesc(skills.get(skillIndex).getDesc());
            for (int i = 0; i < formattedDesc.size(); i++) {
                String s = formattedDesc.get(i);
                g.drawString(s, 473, 70 + i * 25);
            }
        } else {
            g.drawString("暂无技能", 175, 50);
        }

        gameCanvas.drawBitmap(paint, 0, 0);
    }
    private List<String> formatDesc(String desc) {
        int LENGTH = 6;
        if (StringUtils.isEmpty(desc)) {
            return Collections.emptyList();
        }
        if (desc.length() <= LENGTH) {
            return Collections.singletonList(desc);
        }
        List<String> target = new ArrayList<>();
        while (true) {
            if (desc.length() > LENGTH) {
                target.add(desc.substring(0, 5));
                desc = desc.substring(5);
            } else {
                target.add(desc);
                break;
            }
        }
        return target;
    }

    @Override
    public void onKeyDown(int key) {
        if (KeyUtil.isUp(key) && skillIndex > 0) {
            skillIndex--;
        } else if (KeyUtil.isDown(key)) {
            List<SkillMetaData> skills = GameFrame.getInstance().getGameData().getSkillsOf(roleId);
            if (skillIndex < skills.size() - 1) {
                skillIndex++;
            }
        }
    }

    @Override
    public boolean isPopup() {
        return !super.isPopup();
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            systemScreen.pop();
        }
    }
}
