package billy.rpg.game.scriptParser.item;

import com.rupeng.game.GameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NpcImageItem {
    private Map<String, Image> npcs;
    
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
        String imgPath = GameUtils.mapPath("Images/character/npc") + "/";
        // TODO 此时在java开发环境是能取到rpg-common目录下的tiles的目录，
        // 但是，当rpg-common被处理成jar的话运行就不一定能正常了。
        URL resource = this.getClass().getResource("/tiles/");
        
        try {
            Map<String, Image> npcMap = new HashMap<>();
            File file = new File(resource.getPath());
            File[] list = file.listFiles();
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到npc数据");
            }
            for (File f : list) {
                InputStream fileInputStream = this.getClass().getResourceAsStream("/tiles/" + f.getName());
                Image img = ImageIO.read(fileInputStream);
                IOUtils.closeQuietly(fileInputStream);
                npcMap.put(f.getName(), img);
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
