package billy.rpg.game.core.item;

import billy.rpg.common.util.AssetsUtil;
import java.awt.Image;
import java.io.File;
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
            String basePath = "/assets/Images/character/npc/";
            File baseDirectory = new File(AssetsUtil.getResourcePath(basePath));
            for (int i = 1; i < 4; i++) {
                String fullImagePath = basePath + "/full_0" + i + ".png";
                roleFullImageMap.put(i, AssetsUtil.getResourceAsImage(fullImagePath));
                roleBattleImageMap.put(i, AssetsUtil.getResourceAsImage("/assets/Images/battle_" + i + ".png"));
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
