package billy.rpg.rb2.graphics;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 11:52
 */
public class PanoramasHolder {
    private Logger LOG = Logger.getLogger(PanoramasHolder.class);
    private Map<String, PanoramasGroup> panoramasGroupMap = new HashMap<>();

    private final static String basePath = "/Graphics/Panoramas";
    private List<String> panoramaNames = new ArrayList<>();
    private String currentName = "beginning"; // 当前活动地图

    public PanoramasHolder() {
        loadPanoramaNames();
    }

    public void init() {
        for (String name : panoramaNames) {
            initWithName(name);
        }
    }

    private void loadPanoramaNames() {
        File file = new File(this.getClass().getClassLoader().getResource("").getPath() + basePath);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.toLowerCase().endsWith(".png")
                        && !name.contains("-")) {
                    return true;
                }
                return false;
            }
        });
        if (files == null) {
            throw new RuntimeException("no Panoramas!");
        }
        for (File f : files) {
            String name = f.getName();
            // TODO .Png .PNG .pNG .pnG?
            String substring = name.replace(".png", "");
            LOG.debug(" parsing panorama: " + substring);
            panoramaNames.add(substring);
        }
    }

    public void initWithName(String name) {
        try {
            BufferedImage beginning;
            BufferedImage beginning2;
            BufferedImage beginning3;
            BufferedImage beginning4;
            InputStream is = this.getClass().getResourceAsStream(basePath + "/"+name+".png");
            beginning = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream(basePath + "/"+name+"-2.png");
            beginning2 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream(basePath + "/"+name+"-3.png");
            beginning3 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            is = this.getClass().getResourceAsStream(basePath + "/"+name+"-4.png");
            beginning4 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
            PanoramasGroup panoramasGroup = new PanoramasGroup();
            panoramasGroup.setPanoramas(beginning);
            panoramasGroup.setPanoramas2(beginning2);
            panoramasGroup.setPanoramas3(beginning3);
            panoramasGroup.setPanoramas4(beginning4);
            panoramasGroupMap.put(name, panoramasGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void changeTo(String name) {
        if (!panoramaNames.contains(name)) {
            throw new RuntimeException("unkown panorama: " + name);
        }
        currentName = name;
    }

    public PanoramasGroup getPanoramasGroup(String panoramas) {
        return panoramasGroupMap.get(panoramas);
    }
    public PanoramasGroup getCurrentPanoramasGroup() {
        return panoramasGroupMap.get(getCurrentName());
    }

    public String getCurrentName() {
        return currentName;
    }

}
