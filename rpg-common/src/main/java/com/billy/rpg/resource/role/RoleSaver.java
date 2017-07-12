package com.billy.rpg.resource.role;

import com.billy.rpg.common.constant.AnimationEditorConstant;
import com.billy.rpg.common.constant.RoleEditorConstant;
import com.billy.rpg.common.util.ImageUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-12 10:55
 */
public class RoleSaver {
    private static final Logger LOG = Logger.getLogger(RoleSaver.class);
    private static final String ROLE_MAGIC = RoleEditorConstant.ROLE_MAGIC;



    /**
     * save role to specified file
     *
     * @param roleFilePath role filepath
     * @param roleMetaData data to save
     */
    public static void save(String roleFilePath, RoleMetaData roleMetaData) {
        File file = new File(roleFilePath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(ROLE_MAGIC.getBytes("utf-8"));
            LOG.debug("ROLE_MAGIC `" + ROLE_MAGIC + "` written as utf-8");
            dos.writeInt(AnimationEditorConstant.VERSION);
            LOG.debug("version written with " + RoleEditorConstant.VERSION);
            int type = roleMetaData.getType();
            dos.writeInt(type);
            LOG.debug("type written with " + type);
            int number = roleMetaData.getNumber();
            dos.writeInt(number);
            LOG.debug("number written with " + number);
            String name = roleMetaData.getName();
            byte[] bytes = name.getBytes("utf-8");
            dos.writeInt(bytes.length);
            dos.write(bytes);
            LOG.debug("name written with " + name + " in utf-8");

            BufferedImage image = roleMetaData.getImage();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", out);
            byte[] bytesOfImage = out.toByteArray();
            IOUtils.closeQuietly(out);
            dos.writeInt(bytesOfImage.length);
            byte[] br = ImageUtil.reverseBytes(bytesOfImage);
            dos.write(br);
            LOG.debug("image written with " + bytesOfImage.length+ " bytes.");

            int hp = roleMetaData.getHp();
            dos.writeInt(hp);
            LOG.debug("hp written with " + hp);
            int maxHp = roleMetaData.getMaxHp();
            dos.writeInt(maxHp);
            LOG.debug("maxHp written with " + maxHp);
            int mp = roleMetaData.getMp();
            dos.writeInt(mp);
            LOG.debug("mp written with " + mp);
            int maxMp = roleMetaData.getMaxMp();
            dos.writeInt(maxMp);
            LOG.debug("maxMp written with " + maxMp);
            int speed = roleMetaData.getSpeed();
            dos.writeInt(speed);
            LOG.debug("speed written with " + speed);
            int attack = roleMetaData.getAttack();
            dos.writeInt(attack);
            LOG.debug("attack written with " + attack);
            int defend = roleMetaData.getDefend();
            dos.writeInt(defend);
            LOG.debug("defend written with " + defend);
            int exp = roleMetaData.getExp();
            dos.writeInt(exp);
            LOG.debug("exp written with " + exp);
            int levelChain = roleMetaData.getLevelChain();
            dos.writeInt(levelChain);
            LOG.debug("levelChain written with " + levelChain);
        } catch (IOException e) {
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
        LOG.debug("saved file `{"+roleFilePath+"}`.");

    }
}
