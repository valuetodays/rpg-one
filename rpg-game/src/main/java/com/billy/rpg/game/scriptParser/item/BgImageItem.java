package com.billy.rpg.game.scriptParser.item;

import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * load background images, there are many background images
 *
 * @author liulei-frx
 * 
 * @since 2016-12-7 10:57:08
 */
public class BgImageItem  implements IImageLoader, IItem {
    private static final Logger LOG = Logger.getLogger(BgImageItem.class);

    private Image bgImage1;
    
    @Override
    public List<LoaderBean> load() throws Exception {
        loadBgImage();   
        return null;
    }
   

    private boolean loaded = false;
    
    private void loadBgImage() {
        if (loaded) {
            return ;
        }
        LOG.debug("load background images");
        String imgPath = GameUtils.mapPath("Images/") + "/";

        try {
            bgImage1 = ImageIO.read(new FileInputStream(imgPath + "bgImage1.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }

    public Image getBgImage1() {
        return bgImage1;
    }
    
}
