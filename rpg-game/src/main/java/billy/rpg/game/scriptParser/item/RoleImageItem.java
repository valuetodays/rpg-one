package billy.rpg.game.scriptParser.item;

import billy.rpg.game.scriptParser.bean.LoaderBean;
import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

public class RoleImageItem implements IItem {
    private Image roleFull1;

    public List<LoaderBean> load() throws Exception {
        loadRole();   // TODO change to loadImage(); ??
        return null;
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
