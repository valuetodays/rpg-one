package billy.rpg.game.scriptParser.item;

import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

public class RoleImageItem {
    private Image roleFull1; // TODO what fuck the name

    public void load() throws Exception {
        loadRole();   // TODO change to loadImage(); ??
    }

    private boolean loaded = false;

    private void loadRole() {
        if (loaded) {
            return;
        }
        String imgPath = GameUtils.mapPath("Images/") + "/";

        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/role_full_1.png");
            roleFull1 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loaded = true;
    }

    public Image getRoleFull1() {
        return roleFull1;
    }


}
