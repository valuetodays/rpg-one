package com.billy.scriptParser.item;

import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.loader.image.IImageLoader;
import com.rupeng.game.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileImageItem  implements IImageLoader, IItem {
    private Map<String, Image> tiles;
    
    
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
        String imgPath = GameUtils.mapPath("tiles") + "/";
        
        try {
            Map<String, Image> tileMap = new HashMap<>();
            File file = new File(imgPath);
            String[] list = file.list();
            for (String f : list) {
                FileInputStream fileInputStream = new FileInputStream(imgPath + f);
                Image img = ImageIO.read(fileInputStream);
                fileInputStream.close();
                tileMap.put(f, img);
            }
            tiles = Collections.unmodifiableMap(tileMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }
    
    public Map<String, Image> getTiles() {
        return tiles;
    }
    public Image getTile(String name) {
        return tiles.get(name);
    }

    
}
