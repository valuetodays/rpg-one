package billy.rpg.game.item;

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
        final String headPath = "/Images/character/head/";
        URL resource = this.getClass().getResource(headPath);
        
        try {
            File file = new File(resource.getPath());
            File[] list = file.listFiles();
            if (ArrayUtils.isEmpty(list)) {
                throw new RuntimeException("没有找到head数据");
            }
            Map<String, Image> npcMap = new HashMap<>();
            for (File f : list) {
                InputStream fileInputStream = this.getClass().getResourceAsStream(headPath + f.getName());
                Image img = ImageIO.read(fileInputStream);
                IOUtils.closeQuietly(fileInputStream);
                npcMap.put(f.getName(), img);
            }
                heads = Collections.unmodifiableMap(npcMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
