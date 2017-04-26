package com.billy.scriptParser.item.skill;

import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.item.IItem;
import com.billy.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * TODO(这里用一句话描述这个类的作用)
 *
 * @author liulei
 * @date 2017-04-25 15:21
 */
public class Skill2ImageItems implements IImageLoader, IItem, Runnable {
    private static final Logger LOG = Logger.getLogger(Skill2ImageItems.class);
    private Map<Integer, Image> images;
    private boolean loaded = false;
    private Integer currentFrame = 0;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        images = Collections.unmodifiableMap(imageMap);

        loaded = true;
    }

    public Image getCurrentImage() {
        return images.get(currentFrame);
    }

    public static void main(String[] args) {
        Skill2ImageItems skill2ImageItems = new Skill2ImageItems();
        try {
            skill2ImageItems.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(skill2ImageItems).start();
        int i = 0;
        i++;

    }


    @Override
    public void run() {
        while(true) {
            LOG.debug("currentFrame=" + currentFrame);
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
