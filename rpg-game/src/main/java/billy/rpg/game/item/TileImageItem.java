package billy.rpg.game.item;

import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TileImageItem {
    private Map<String, Image> tiles;

    public void load() throws Exception {
        loadTile(); // TODO change to loadImage(); ??
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
        // TODO 此时在java开发环境是能取到rpg-common目录下的tiles的目录，
        // 但是，当rpg-common被处理成jar的话运行就不一定能正常了。
        URL resource = this.getClass().getResource("/tiles/");

        try {
            Map<String, Image> tileMap = new HashMap<>();
            File file = new File(resource.getPath());
            File[] list = file.listFiles();
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到tile数据");
            }
            for (File f : list) {
                InputStream fileInputStream = this.getClass().getResourceAsStream("/tiles/" + f.getName());
                Image img = ImageIO.read(fileInputStream);
                IOUtils.closeQuietly(fileInputStream);
                tileMap.put(f.getName(), img);
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
