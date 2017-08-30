package billy.rpg.resource.skill;

import billy.rpg.common.constant.SkillEditorConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author liulei@bshf360.com
 * @since 2017-08-30 17:49
 */
public class SkillSaver {
    private static final Logger LOG = Logger.getLogger(SkillSaver.class);
    private static final String SKL_MAGIC = SkillEditorConstant.MAGIC;
    /**
     * save skl to specified file
     *
     *
     * @param sklFilepath skl filepath
     * @param skillMetaData data to save
     */
    public static void save(String sklFilepath, SkillMetaData skillMetaData) {
        File file = new File(sklFilepath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(SKL_MAGIC.getBytes("utf-8"));
            LOG.debug("SKL_MAGIC `" + SKL_MAGIC + "` written as utf-8");

            dos.writeInt(skillMetaData.getNumber());
            String name = skillMetaData.getName();
            byte[] nameBytes = name.getBytes("utf-8");
            dos.writeInt(nameBytes.length);
            dos.write(nameBytes);
            dos.writeInt(skillMetaData.getType());
            dos.writeInt(skillMetaData.getBaseDamage());
            dos.writeInt(skillMetaData.getConsume());
            dos.writeInt(skillMetaData.getTargetType());
            dos.writeInt(skillMetaData.getAnimationId());
            String desc = skillMetaData.getDesc();
            byte[] descBytes = desc.getBytes("utf-8");
            dos.writeInt(descBytes.length);
            dos.write(descBytes);

        } catch (IOException e) {
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
        LOG.debug("saved file `{"+sklFilepath+"}`.");
    }
}
