package billy.rpg.resource.skill;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;

public class BinarySaverLoader implements SkillSaverLoader {
    private final Logger logger = Logger.getLogger(getClass());
    private static final String SKL_MAGIC = ToolsConstant.MAGIC_SKL;
    private static final String CHARSET = ToolsConstant.CHARSET;
    @Override
    public void save(String filepath, SkillMetaData skillMetaData) throws IOException {
        File file = new File(filepath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(SKL_MAGIC.getBytes(CHARSET));
            logger.debug("SKL_MAGIC `" + SKL_MAGIC + "` written as " + CHARSET);

            dos.writeInt(skillMetaData.getNumber());
            String name = skillMetaData.getName();
            byte[] nameBytes = name.getBytes(CHARSET);
            dos.writeInt(nameBytes.length);
            dos.write(nameBytes);
            dos.writeInt(skillMetaData.getType());
            dos.writeInt(skillMetaData.getBaseDamage());
            dos.writeInt(skillMetaData.getConsume());
            dos.writeInt(skillMetaData.getTargetType());
            dos.writeInt(skillMetaData.getAnimationId());
            String desc = skillMetaData.getDesc();
            byte[] descBytes = desc.getBytes(CHARSET);
            dos.writeInt(descBytes.length);
            dos.write(descBytes);

        } catch (IOException e) {
            logger.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
        logger.debug("saved file `{"+filepath+"}`.");
    }

    @Override
    public SkillMetaData load(String filepath) throws IOException {
        File file = new File(filepath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        SkillMetaData smd = new SkillMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] sklMagic = new byte[SKL_MAGIC.getBytes(CHARSET).length];
            dis.read(sklMagic, 0 , sklMagic.length);
            String sklMagicUtf8 = new String(sklMagic, CHARSET);
            logger.debug("skl magic `"+sklMagicUtf8+"` read");

            smd.setNumber(dis.readInt());
            int nameBytesLen = dis.readInt();
            byte[] nameBytes = new byte[nameBytesLen];
            dis.read(nameBytes);
            String nameUtf8 = new String(nameBytes, CHARSET);
            smd.setName(nameUtf8);
            smd.setType(dis.readInt());
            smd.setBaseDamage(dis.readInt());
            smd.setConsume(dis.readInt());
            smd.setTargetType(dis.readInt());
            smd.setAnimationId(dis.readInt());
            int descBytesLen = dis.readInt();
            byte[] descBytes = new byte[descBytesLen];
            dis.read(descBytes);
            String descUtf8 = new String(descBytes, CHARSET);
            smd.setDesc(descUtf8);

        } catch (IOException e) {
            logger.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        logger.debug("loaded file `{"+filepath+"}`.");
        return smd;
    }
}
