package billy.rpg.resource.skill;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * @author liulei@bshf360.com
 * @since 2017-08-30 18:22
 */
public class SkillLoader {
    private static final Logger LOG = Logger.getLogger(SkillLoader.class);
    private static final String SKL_MAGIC = ToolsConstant.MAGIC_SKL;
    private static final String CHARSET = ToolsConstant.CHARSET;

    /**
     * load skl from specified file
     *
     *
     * @param sklFilepath skl filepath
     */
    public static SkillMetaData load(String sklFilepath) {
        File file = new File(sklFilepath);
        FileInputStream fis = null;
        DataInputStream dis = null;
        SkillMetaData smd = new SkillMetaData();

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] sklMagic = new byte[SKL_MAGIC.getBytes(CHARSET).length];
            dis.read(sklMagic, 0 , sklMagic.length);
            String sklMagicUtf8 = new String(sklMagic, CHARSET);
            LOG.debug("skl magic `"+sklMagicUtf8+"` read");

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
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        LOG.debug("loaded file `{"+sklFilepath+"}`.");
        return smd;
    }
}
