package com.billy.rpg.game.scriptParser.item.skill;

import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.item.IItem;
import com.billy.rpg.game.scriptParser.loader.image.IImageLoader;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.screen.MapScreen;
import com.rupeng.game.GameUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author liulei
 * @since 2017-04-25 15:21
 */
public class Skill2ImageItem implements IImageLoader, IItem, Runnable {
    private static final Logger LOG = Logger.getLogger(Skill2ImageItem.class);
    private Map<Integer, Image> images;
    private boolean loaded = false;
    private Integer currentFrame = 0;
    private boolean enable;

    @Override
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

        Map<Integer, Image> imageMap = new HashMap<>();
        try {
            for (int i = 0; i < 4; i++) {
                Image image = ImageIO.read(new FileInputStream(imgPath + i + ".png"));
                imageMap.put(i, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        images = Collections.unmodifiableMap(imageMap);

        loaded = true;
    }

    public Image getCurrentImage() {
        return images.get(currentFrame);
    }



    public void disable() {
        enable = true;
    }

    public void enable() {
        enable = true;
    }

    @Override
    public void run() {
        while(enable) {
            if (GameFrame.getInstance().getCurScreen() instanceof MapScreen) {
//                LOG.debug("currentFrame=" + currentFrame);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentFrame++;
                if (currentFrame > 3) {
                    currentFrame = 0;
                }
            }
        }

    }
}
