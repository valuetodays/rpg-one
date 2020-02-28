package billy.rpg.game.core.item;

import billy.rpg.common.util.AssetsUtil;
import java.awt.Image;
import org.apache.log4j.Logger;

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

        bgImage1 = AssetsUtil.getResourceAsImage("/assets/Images/bgImage1.png");
        LOG.debug("bgImage1 is " + (bgImage1 == null));
        
        loaded = true;
    }

    public Image getBgImage1() {
        return bgImage1;
    }
    
}
