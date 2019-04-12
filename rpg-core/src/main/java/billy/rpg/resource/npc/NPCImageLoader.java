package billy.rpg.resource.npc;

import billy.rpg.common.util.AssetsUtil;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author liulei
 * @since 2017-05-16 19:11
 */
public class NPCImageLoader {
    private NPCMetaData npcMetaData;

    public NPCMetaData loadNpcs() {
        npcMetaData = new NPCMetaData();
        java.util.List<Image> littleImages = new ArrayList<>();
        java.util.List<Image> fullImages = new ArrayList<>();
        java.util.List<String> littleNames = new ArrayList<>();
        java.util.List<String> fullNames = new ArrayList<>();
        //

        String resourcePath = AssetsUtil.getResourcePath("/assets/Images/character/npc/");
        try {
            File file = new File(resourcePath);
            // little
            File[] listLittle = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().startsWith("little_")) {
                        return true;
                    }
                    return false;
                }
            });
            if (ArrayUtils.isEmpty(listLittle)) {
                throw new RuntimeException("没有找到npc_little数据");
            }

            for (File f : listLittle) {
                Image img = ImageIO.read(f);
                littleImages.add(img);
                littleNames.add(f.getName());
            }

            // full
            File[] listFull = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().startsWith("full_")) {
                        return true;
                    }
                    return false;
                }
            });
            if (ArrayUtils.isEmpty(listFull)) {
                throw new RuntimeException("没有找到npc_full数据");
            }

            for (File f : listFull) {
                Image img = ImageIO.read(f);
                fullImages.add(img);
                fullNames.add(f.getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
            npcMetaData = null;
        }

        if (npcMetaData == null) {
            throw new RuntimeException("加载npc出错");
        }

        npcMetaData.setLittleImages(littleImages);
        npcMetaData.setFullImages(fullImages);
        npcMetaData.setLittleNames(littleNames);
        npcMetaData.setFullNames(fullNames);
        return npcMetaData;
    }

    public int getLittleNpcNum(String npcName) {
        return Integer.parseInt(npcName.substring("little_".length(), npcName.lastIndexOf(".")));
    }
    public int getFullNpcNum(String npcName) {
        return Integer.parseInt(npcName.substring("full_".length(), npcName.lastIndexOf(".")));
    }

    public String getLittleNpcName(int npcNum) {
        return String.format("little_%02d.png", npcNum);
    }
    public String getFullNpcName(int npcNum) {
        return String.format("full_%02d.png", npcNum);
    }

    public BufferedImage getLittleImageOf(int npcNum) {
        String npcName = this.getLittleNpcName(npcNum);
        int inx = -1;
        for (int i = 0; i < npcMetaData.getLittleNames().size(); i++) {
            if (npcName.equals(npcMetaData.getLittleNames().get(i))) {
                inx = i;
                break;
            }
        }
        if (inx == -1) {
            throw new RuntimeException("npc little数据不对");
        }
        return (BufferedImage)npcMetaData.getLittleImages().get(inx);
    }
    public BufferedImage getFullImageOf(int npcNum) {
        String npcName = this.getFullNpcName(npcNum);
        int inx = -1;
        for (int i = 0; i < npcMetaData.getFullNames().size(); i++) {
            if (npcName.equals(npcMetaData.getFullNames().get(i))) {
                inx = i;
                break;
            }
        }
        if (inx == -1) {
            throw new RuntimeException("npc little数据不对: " + npcNum);
        }
        return (BufferedImage)npcMetaData.getFullImages().get(inx);
    }

    public NPCMetaData getNpcMetaData() {
        return npcMetaData;
    }
}
