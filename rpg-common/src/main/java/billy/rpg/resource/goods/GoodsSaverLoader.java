package billy.rpg.resource.goods;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-04 17:54
 */
public class GoodsSaverLoader {
    private static final Logger LOG = Logger.getLogger(GoodsSaverLoader.class);
    private static final String GDS_MAGIC = ToolsConstant.MAGIC_GDS;
    private static final String CHARSET = ToolsConstant.CHARSET;

    /**
     * save goods to file
     * @param filepath filepath
     * @param goodsMetaData data
     */
    public static void save(String filepath, GoodsMetaData goodsMetaData) {
        File file = new File(filepath);
        FileOutputStream fos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
            dos.write(GDS_MAGIC.getBytes(CHARSET));
            LOG.debug("ROLE_MAGIC `" + GDS_MAGIC + "` written as " + CHARSET);
            int number = goodsMetaData.getNumber();
            dos.writeInt(number);
            LOG.debug("number written with " + number);
            int type = goodsMetaData.getType();
            dos.writeInt(type);
            LOG.debug("type written with " + type);
            String name = goodsMetaData.getName();
            byte[] nameBytes = name.getBytes(CHARSET);
            dos.writeInt(nameBytes.length);
            dos.write(nameBytes);
            LOG.debug("name written with " + name + " in " + CHARSET);
            int buy = goodsMetaData.getBuy();
            dos.writeInt(buy);
            LOG.debug("buy written with " + buy);
            int sell = goodsMetaData.getSell();
            dos.writeInt(sell);
            LOG.debug("sell written with " + sell);
            int imageId = goodsMetaData.getImageId();
            dos.writeInt(imageId);
            LOG.debug("imageId written with " + imageId);
            int event = goodsMetaData.getEvent();
            dos.writeInt(event);
            LOG.debug("event written with " + event);
            int animationId = goodsMetaData.getAnimationId();
            dos.writeInt(animationId);
            LOG.debug("animationId written with " + animationId);
            int hp = goodsMetaData.getHp();
            dos.writeInt(hp);
            LOG.debug("hp written with " + hp);
            int mp = goodsMetaData.getMp();
            dos.writeInt(mp);
            LOG.debug("mp written with " + mp);
            int attack = goodsMetaData.getAttack();
            dos.writeInt(attack);
            LOG.debug("attack written with " + attack);
            int defend = goodsMetaData.getDefend();
            dos.writeInt(defend);
            LOG.debug("defend written with " + defend);
            String desc = goodsMetaData.getDesc();
            byte[] descBytes = desc.getBytes(CHARSET);
            dos.writeInt(descBytes.length);
            dos.write(descBytes);
        } catch (IOException e) {
            LOG.debug("IO exception:" + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dos);
            IOUtils.closeQuietly(fos);
        }
        LOG.debug("saved file `{"+filepath+"}`.");
    }


    /**
     *
     * @param filepath filepath
     * @return MetaData
     */
    public static GoodsMetaData load(String filepath) {
        GoodsMetaData gmd = new GoodsMetaData();
        File file = new File(filepath);
        FileInputStream fis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            byte[] bRoleMagic = new byte[GDS_MAGIC.getBytes(CHARSET).length];
            dis.read(bRoleMagic);
            String aniMagicUtf8 = new String(bRoleMagic, CHARSET);
            LOG.debug("goods magic `"+aniMagicUtf8+"` read");
            int number = dis.readInt();
            LOG.debug("goods number is " + number);
            gmd.setNumber(number);
            int type = dis.readInt();
            LOG.debug("goods type is " + type);
            gmd.setType(type);
            int nameByteLength = dis.readInt();
            byte[] nameBytes = new byte[nameByteLength];
            dis.read(nameBytes);
            String name = new String(nameBytes, CHARSET);
            LOG.debug("goods name is " + name);
            gmd.setName(name);
            int buy = dis.readInt();
            LOG.debug("goods buy " + buy);
            gmd.setBuy(buy);
            int sell = dis.readInt();
            LOG.debug("goods sell " + sell);
            gmd.setSell(sell);
            int imageId = dis.readInt();
            LOG.debug("goods imageId " + imageId);
            gmd.setImageId(imageId);
            int event = dis.readInt();
            LOG.debug("goods event " + event);
            gmd.setEvent(event);
            int animationId = dis.readInt();
            LOG.debug("goods animationId " + animationId);
            gmd.setAnimationId(animationId);
            int hp = dis.readInt();
            LOG.debug("goods hp " + hp);
            gmd.setHp(hp);
            int mp = dis.readInt();
            LOG.debug("goods mp is " + mp);
            gmd.setMp(mp);
            int attack = dis.readInt();
            LOG.debug("goods attack is " + attack);
            gmd.setAttack(attack);
            int defend = dis.readInt();
            LOG.debug("goods defend is " + defend);
            gmd.setDefend(defend);

            int descByteLength = dis.readInt();
            byte[] descBytes = new byte[descByteLength];
            dis.read(descBytes);
            String desc = new String(descBytes, CHARSET);
            LOG.debug("goods desc is " + desc);
            gmd.setDesc(desc);
        } catch (IOException e) {
            LOG.debug("IO exception: " + e.getMessage(), e);
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }

        LOG.debug("loaded role file ["+filepath+"].");
        return gmd;
    }
}
