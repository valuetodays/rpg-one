package billy.rpg.game.scriptParser.item;

import billy.rpg.game.scriptParser.bean.LoaderBean;
import billy.rpg.game.scriptParser.loader.image.IImageLoader;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleImageItem implements IImageLoader, IItem {
    private Map<String, Image> battleImages;
    
    
    @Override
    public List<LoaderBean> load() throws Exception {
        loadImage();
        return null;
    }
    
    private boolean loaded = false;
    /**
     * load battle-images
     */
    private void loadImage() {
        if (loaded) {
            return ;
        }
        URL resource = this.getClass().getResource("/battle/");

        try {
            Map<String, Image> tileMap = new HashMap<>();
            File file = new File(resource.getPath());
            File[] list = file.listFiles();
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到battle数据");
            }
            for (File f : list) {
                InputStream fileInputStream = this.getClass().getResourceAsStream("/battle/" + f.getName());
                Image img = ImageIO.read(fileInputStream);
                IOUtils.closeQuietly(fileInputStream);
                tileMap.put(f.getName(), img);
            }
            battleImages = Collections.unmodifiableMap(tileMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }

    public Map<String, Image> getBattleImages() {
        return battleImages;
    }

    public Image getBattleImage(String name) {
        return battleImages.get(name);
    }

}
