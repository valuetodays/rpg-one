package billy.rpg.resource.role;

import billy.rpg.common.constant.ToolsConstant;
import billy.rpg.common.util.ImageUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-12 10:55
 */
public class RoleLoader {
    private static final Logger LOG = Logger.getLogger(RoleLoader.class);
    private static final String ROLE_MAGIC = ToolsConstant.MAGIC_ROL;
    private static final String CHARSET = ToolsConstant.CHARSET;


    public static RoleMetaData load(String roleFilePath) {
        RoleMetaData rmd = new RoleMetaData();
        File file = new File(roleFilePath);
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bRoleMagic = new byte[ROLE_MAGIC.getBytes(CHARSET).length];
            dis.read(bRoleMagic);
            String aniMagicUtf8 = new String(bRoleMagic, CHARSET);
            LOG.debug("role magic `"+aniMagicUtf8+"` read");
            int number = dis.readInt();
            LOG.debug("role number is " + number);
            rmd.setNumber(number);
            int type = dis.readInt();
            LOG.debug("role type is " + type);
            rmd.setType(type);
            int roleNameByteLength = dis.readInt();
            byte[] roleNameBytes = new byte[roleNameByteLength];
            dis.read(roleNameBytes);
            String roleName = new String(roleNameBytes, CHARSET);
            LOG.debug("role name is " + roleName);
            rmd.setName(roleName);
            int imageLength = dis.readInt();
            byte[] imageBytes = new byte[imageLength];
            dis.read(imageBytes);
            byte[] realImageBytes = ImageUtil.reverseBytes(imageBytes);
            ByteArrayInputStream in = new ByteArrayInputStream(realImageBytes);
            BufferedImage bufferedImage = ImageIO.read(in);
            IOUtils.closeQuietly(in);
            LOG.debug("role image is " + realImageBytes.length + " bytes");
            rmd.setImage(bufferedImage);
            int hp = dis.readInt();
            LOG.debug("role hp is " + hp);
            rmd.setHp(hp);
            rmd.setMaxHp(hp);
            int mp = dis.readInt();
            LOG.debug("role mp is " + mp);
            rmd.setMp(mp);
            rmd.setMaxMp(mp);
            int speed = dis.readInt();
            LOG.debug("role speed is " + speed);
            rmd.setSpeed(speed);
            int attack = dis.readInt();
            LOG.debug("role attack is " + attack);
            rmd.setAttack(attack);
            int defend = dis.readInt();
            LOG.debug("role defend is " + defend);
            rmd.setDefend(defend);
            int exp = dis.readInt();
            LOG.debug("role exp is " + exp);
            rmd.setExp(exp);
            int money = dis.readInt();
            LOG.debug("role money is " + money);
            rmd.setMoney(money);
            int skillIdsBytesLen = dis.readInt();
            byte[] skillIdsBytes = new byte[skillIdsBytesLen];
            dis.read(skillIdsBytes);
            String skillIds = new String(skillIdsBytes, CHARSET);
            LOG.debug("skillIds is " + skillIds);
            rmd.setSkillIds(skillIds);
            int levelChain = dis.readInt();
            LOG.debug("role levelChain is " + levelChain);
            rmd.setLevelChain(levelChain);
        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        LOG.debug("loaded role file ["+roleFilePath+"].");
        return rmd;
    }
}
