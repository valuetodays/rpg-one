package billy.rpg.resource.level;

import billy.rpg.common.constant.LevelEditorConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-26 09:53
 */
public class LevelSaver {
    private static final Logger LOG = Logger.getLogger(LevelSaver.class);
    private static final String LVL_MAGIC = LevelEditorConstant.LVL_MAGIC;

    /**
     * 保存升级数据成文件
     * @param lvlFilePath 目标文件路径
     * @param levelMetaData 待保存的数据
     */
    public static void save(String lvlFilePath, LevelMetaData levelMetaData) {
        int number = levelMetaData.getNumber();
        int maxLevel = levelMetaData.getMaxLevel();
        List<LevelData> levelDataList = levelMetaData.getLevelDataList();
        File file = new File(lvlFilePath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(LVL_MAGIC.getBytes("utf-8"));
            LOG.debug("LVL_MAGIC `" + LVL_MAGIC + "` written as utf-8");
            dos.writeInt(number);
            LOG.debug("number written with " + number);
            dos.writeInt(maxLevel);
            LOG.debug("maxLevel written with " + maxLevel);
            for (int i = 0; i < maxLevel; i++) {
                LevelData levelData = levelDataList.get(i);
                dos.writeInt(levelData.getLevel());
                dos.writeInt(levelData.getMaxHp());
                dos.writeInt(levelData.getMaxMp());
                dos.writeInt(levelData.getAttack());
                dos.writeInt(levelData.getDefend());
                dos.writeInt(levelData.getExp());
                LOG.debug("level "+(i+1)+" written.");
            }
        } catch (IOException e) {
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }

        LOG.debug("saved file `{"+lvlFilePath+"}`.");
    }
}
