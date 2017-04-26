package com.billy.scriptParser.item;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;

public class NpcImageItem implements IImageLoader, IItem {
    private Map<String, Image> npcs;
    
    
    @Override
    public List<LoaderBean> load() throws Exception {
        loadTile(); // TODO change to loadImage(); ??
        return null;
    }
    
    private boolean loaded = false;
    /**
     * load tile-images of map
     */
    private void loadTile() {
        if (loaded) {
            return ;
        }
        String imgPath = GameUtils.mapPath("Images/npc") + "/";
        
        try {
            Map<String, Image> npcMap = new HashMap<>();
            File file = new File(imgPath);
            String[] list = file.list();
            for (String f : list) {
                FileInputStream fileInputStream = new FileInputStream(imgPath + f);
                Image img = ImageIO.read(fileInputStream);
                fileInputStream.close();
                npcMap.put(f, img);
                npcs = Collections.unmodifiableMap(npcMap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }
    
    public Map<String, Image> getNpcs() {
        return npcs;
    }
    public Image getNpc(String name) {
        return npcs.get(name + ".png");
    }

    
}
