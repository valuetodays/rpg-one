package com.billy.rpg.game.scriptParser.item.skill;

import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.screen.MapScreen;
import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.item.IItem;
import com.billy.rpg.game.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * transfer
 * @author liulei
 * @since 2017-05-16 20:43 update class name to TransferImageItem
 * @since 2017-04-25 15:21
 */
public class TransferImageItem implements IImageLoader, IItem, Runnable {
    private static final Logger LOG = Logger.getLogger(TransferImageItem.class);
    private Map<Integer, Image> images;
    private boolean loaded = false;
    private Integer currentFrame = 0;
    private boolean enable = true;

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
        URL resource = this.getClass().getResource("/Images/transfer/");
        File file = new File(resource.getPath());
        File[] list = file.listFiles();
        if (ArrayUtils.isEmpty(list)) {
            throw new RuntimeException("没有找到Transfer数据");
        }

        Map<Integer, Image> imageMap = new HashMap<>();
        try {
            for (int i = 0; i < list.length; i++) {
                InputStream is = this.getClass().getResourceAsStream("/Images/transfer/" + list[i].getName());
                Image image = ImageIO.read(is);
                IOUtils.closeQuietly(is);
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
        if (enable) {
            enable = false;
        }
    }

    public void enable() {
        if (!enable) {
            enable = true;
        }
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
