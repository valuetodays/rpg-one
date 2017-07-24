package billy.rpg.game.item;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

/**
 * load background images, there are many background images
 *
 * @author liulei-frx
 * 
 * @since 2016-12-7 10:57:08
 */
public class BgImageItem {
    private static final Logger LOG = Logger.getLogger(BgImageItem.class);

    private Image bgImage1;
    
    public void load() throws Exception {
        loadBgImage();   
    }
   

    private boolean loaded = false;
    
    private void loadBgImage() {
        if (loaded) {
            return ;
        }
        LOG.debug("load background images");

        try {
            InputStream is = this.getClass().getResourceAsStream("/Images/bgImage1.png");

            bgImage1 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("bgImage1 is " + (bgImage1 == null));
        
        loaded = true;
    }

    public Image getBgImage1() {
        return bgImage1;
    }
    
}
