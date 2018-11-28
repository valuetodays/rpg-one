package billy.rpg.game.core.item;

import billy.rpg.common.util.AssetsUtil;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HeadImageItem {
    private Map<String, Image> heads;
    
    public void load() throws Exception {
        loadHead();
    }
    
    private boolean loaded = false;
    /**
     * load head file
     */
    private void loadHead() {
        if (loaded) {
            return ;
        }
        final String headPath = "/assets/Images/character/head/";
        String resourcePath = AssetsUtil.getResourcePath(headPath);

        try {
            File file = new File(resourcePath);
            File[] list = file.listFiles();
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到head数据");
            }
            Map<String, Image> npcMap = new HashMap<>();
            for (File f : list) {
                Image img = ImageIO.read(f);
                npcMap.put(f.getName(), img);
            }
                heads = Collections.unmodifiableMap(npcMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }
    
    public Map<String, Image> getHeads() {
        return heads;
    }
    public Image getHead(int number) {
        return heads.get(number + ".png");
    }

    
}
