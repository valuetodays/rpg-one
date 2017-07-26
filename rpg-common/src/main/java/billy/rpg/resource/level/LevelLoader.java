package billy.rpg.resource.level;

import billy.rpg.common.constant.LevelEditorConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-26 10:07
 */
public class LevelLoader {
    private static final Logger LOG = Logger.getLogger(LevelLoader.class);
    private static final String LVL_MAGIC = LevelEditorConstant.LVL_MAGIC;


    /**
     * 从指定文件加载升级文件
     * @param lvlFilePath 升级文件
     */
    public static LevelMetaData load(String lvlFilePath) {
        File file = new File(lvlFilePath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        LevelMetaData levelMetaData = new LevelMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bLvlMagic = new byte[LVL_MAGIC.getBytes("utf-8").length];
            dis.read(bLvlMagic, 0 , bLvlMagic.length);
            String lvlMagicUtf8 = new String(bLvlMagic, "utf-8");
            LOG.debug("ani magic `"+lvlMagicUtf8+"` read");
            levelMetaData.setNumber(dis.readInt());
            int maxLevel = dis.readInt();
            levelMetaData.setMaxLevel(maxLevel);
            List<LevelData> levelDataList = new ArrayList<>();
            for (int i = 0; i < maxLevel; i++) {
                LevelData levelData = new LevelData(dis.readInt());
                levelData.setMaxHp(dis.readInt());
                levelData.setMaxMp(dis.readInt());
                levelData.setAttack(dis.readInt());
                levelData.setDefend(dis.readInt());
                levelData.setExp(dis.readInt());
                levelDataList.add(levelData);
            }
            levelMetaData.setLevelDataList(levelDataList);
        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }
        LOG.debug("loaded ani file `{"+lvlFilePath+"}`.");
        return levelMetaData;
    }

}
