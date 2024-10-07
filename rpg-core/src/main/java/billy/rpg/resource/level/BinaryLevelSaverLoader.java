package billy.rpg.resource.level;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei.liu
 * @since 2018-11-20 18:19:06
 */
public class BinaryLevelSaverLoader implements LevelSaverLoader {
    private static final Logger LOG = Logger.getLogger(BinaryLevelSaverLoader.class);
    private static final String LVL_MAGIC = ToolsConstant.MAGIC_LVL;
    private static final String CHARSET = ToolsConstant.CHARSET;

    @Override
    public void save(String filepath, LevelMetaData levelMetaData) {
        int number = levelMetaData.getNumber();
        int maxLevel = levelMetaData.getMaxLevel();
        List<LevelData> levelDataList = levelMetaData.getLevelDataList();
        File file = new File(filepath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(LVL_MAGIC.getBytes(CHARSET));
            LOG.debug("LVL_MAGIC `" + LVL_MAGIC + "` written as " + CHARSET);
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

        LOG.debug("saved file `{"+filepath+"}`.");
    }

    @Override
    public LevelMetaData load(String filepath) throws IOException {
        File file = new File(filepath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        LevelMetaData levelMetaData = new LevelMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bLvlMagic = new byte[LVL_MAGIC.getBytes(CHARSET).length];
            dis.read(bLvlMagic, 0 , bLvlMagic.length);
            String lvlMagicUtf8 = new String(bLvlMagic, CHARSET);
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
        LOG.debug("loaded level file `{"+filepath+"}`.");
        return levelMetaData;
    }
}
