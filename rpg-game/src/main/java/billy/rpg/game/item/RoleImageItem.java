package billy.rpg.game.item;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

public class RoleImageItem {
    private Image roleFull1; // TODO what fuck the name
    private Image roleBattle1;

    public void load() throws Exception {
        loadRole();
    }

    private boolean loaded = false;

    private void loadRole() {
        if (loaded) {
            return;
        }

        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/role_full_1.png");
            roleFull1 = ImageIO.read(is);
            IOUtils.closeQuietly(is);

            is = this.getClass().getResourceAsStream("/Images/battle_1.png");
            roleBattle1 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loaded = true;
    }


    public Image getRoleFull1() {
        return roleFull1;
    }

    public Image getRoleBattleOf(int heroId) {
        if (heroId == 1) {
            return roleBattle1;
        }
        throw new RuntimeException("暂只有一个主角。");
    }

}
