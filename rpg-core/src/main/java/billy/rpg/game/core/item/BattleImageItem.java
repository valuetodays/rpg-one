package billy.rpg.game.core.item;

import billy.rpg.game.core.util.CoreUtil;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BattleImageItem {
    public static final String DEFAULT_BATTLE = "default_battle.jpg";
    private Map<String, Image> battleImages;
    
    public void load() throws Exception {
        loadImage();
    }
    
    private boolean loaded = false;
    /**
     * load battle-images
     */
    private void loadImage() {
        if (loaded) {
            return ;
        }
        String resourcePath = CoreUtil.getResourcePath("/battle/");

        try {
            Map<String, Image> tileMap = new HashMap<>();
            File file = new File(resourcePath);
            File[] list = file.listFiles();
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到battle数据");
            }
            for (File f : list) {
                Image img = ImageIO.read(f);
                tileMap.put(f.getName(), img);
            }
            battleImages = Collections.unmodifiableMap(tileMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }

    public Image getBattleImage(String name) {
        return battleImages.get(name);
    }

}
