package billy.rpg.game.item;

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
        String baseDirectory = "/map/tmx/";
        // TODO 此时在java开发环境是能取到rpg-common目录下的tiles的目录，
        // 但是，当rpg-common被处理成jar的话运行就不一定能正常了。
        URL resource = this.getClass().getResource(baseDirectory);

        try {
            Map<String, Image> tileMap = new HashMap<>();
            File file = new File(resource.getPath());
            File[] list = file.listFiles(pathname -> pathname.getName().endsWith(".png"));
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到tile数据");
            }
            for (File f : list) {
//                InputStream fileInputStream = this.getClass().getResourceAsStream("/tiles/" + f.getName());
                InputStream fileInputStream = this.getClass().getResourceAsStream(baseDirectory + f.getName());
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
    
    public Image getTile(String tileId) {
        Image tileImage = tiles.get(tileId);
        if (tileImage == null) {
            throw new RuntimeException("error tileId; " + tileId);
        }

        return tileImage;
    }

}
