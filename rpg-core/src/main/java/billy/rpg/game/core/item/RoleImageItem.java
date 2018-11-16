package billy.rpg.game.core.item;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class RoleImageItem {
    private Map<Integer, Image> roleFullImageMap = new HashMap<>();
    private Map<Integer, Image> roleBattleImageMap = new HashMap<>();

    public void load() throws Exception {
        loadRole();
    }

    private boolean loaded = false;

    private void loadRole() {
        if (loaded) {
            return;
        }

        try {
            // 加载3个角色的四方向行走图 开始
            String basePath = "/Images/character/npc/";
            URL resource = getClass().getResource(basePath);
            for (int i = 1; i < 4; i++) {
                String s = resource.getPath() + "full_0" + i + ".png";
                BufferedImage roleImage = ImageIO.read(new File(s));
                roleFullImageMap.put(i, roleImage);

                InputStream is = this.getClass().getResourceAsStream("/Images/battle_"+i+".png");
                BufferedImage roleBattle = ImageIO.read(is);
                roleBattleImageMap.put(i, roleBattle);
                IOUtils.closeQuietly(is);
            }
            // 加载3个角色的四方向行走图 结束


        } catch (Exception e) {
            e.printStackTrace();
        }

        loaded = true;
    }


    public Image getRoleFullImageOf(int heroId) {
        Image image = roleFullImageMap.get(heroId);
        if (image == null) {
            throw new RuntimeException("error when load roleFullImageOf: " + heroId);
        }
        return image;
    }

    public Image getRoleBattleOf(int heroId) {
        Image image = roleBattleImageMap.get(heroId);
        if (image == null) {
            throw new RuntimeException("error when load roleBattleImageOf: " + heroId);
        }
        return image;
    }

}
