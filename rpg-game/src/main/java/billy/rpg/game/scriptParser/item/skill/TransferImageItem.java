package billy.rpg.game.scriptParser.item.skill;

import billy.rpg.game.scriptParser.bean.LoaderBean;
import billy.rpg.game.scriptParser.item.IItem;
import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;
import java.util.List;

/**
 * transfer
 * @author liulei
 * @since 2017-05-16 20:43 update class name to TransferImageItem
 * @since 2017-04-25 15:21
 */
public class TransferImageItem implements IItem {
    private static final Logger LOG = Logger.getLogger(TransferImageItem.class);
    private Image image;
    private boolean loaded = false;

    public List<LoaderBean> load() throws Exception {
        load0();
        return null;
    }

    private void load0() {
        if (loaded) {
            return ;
        }
        LOG.debug("load skill-2 images");
        String imgPath = GameUtils.mapPath("Sprites/skill/2/") + "/";

        try {
            InputStream is1 = this.getClass().getResourceAsStream("/Images/transfer.png");
            image = ImageIO.read(is1);
            IOUtils.closeQuietly(is1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loaded = true;
    }

    public Image getImage() {
        return image;
    }



}
