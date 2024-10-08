package billy.rpg.game.core.item;

import billy.rpg.common.util.AssetsUtil;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TileImageItem {
    private Map<String, Image> tiles;

    public void load() throws Exception {
        loadTileImage();
    }
    
    private boolean loaded = false;
    /**
     * load tile-images of map
     */
    private void loadTileImage() {
        if (loaded) {
            return ;
        }
        String baseDirectory = "/assets/map/tmx/";
        // TODO 此时在java开发环境是能取到rpg-common目录下的tiles的目录，
        // 但是，当rpg-common被处理成jar的话运行就不一定能正常了。
        String resourcePath = AssetsUtil.getResourcePath(baseDirectory);

        try {
            Map<String, Image> tileMap = new HashMap<>();
            File file = new File(resourcePath);
            File[] list = file.listFiles(pathname -> pathname.getName().endsWith(".png"));
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到tile数据");
            }
            for (File f : list) {
                Image img = ImageIO.read(f);
                tileMap.put(f.getName(), img);
            }
            tiles = Collections.unmodifiableMap(tileMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        loaded = true;
    }
    
    public Image getTile(String tileId) {
        Image tileImage = tiles.get(tileId);
        if (tileImage == null) {
            throw new RuntimeException("error tileId: " + tileId);
        }

        return tileImage;
    }

}
