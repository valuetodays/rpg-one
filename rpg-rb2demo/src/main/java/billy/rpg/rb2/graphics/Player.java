package billy.rpg.rb2.graphics;

import billy.rpg.rb2.Constants;
import billy.rpg.rb2.GameReourceHolder;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 14:12
 */
public class Player {
    private static final Logger LOG = Logger.getLogger(Player.class);
    private final static String basePath = "/Graphics/Characters";

    private static final int FRAME_ATTENTION = 5; // 立正

    private BufferedImage biaoshi14;
    public static final int WIDTH = 192;
    public static final int HEIGHT = 192;
    private int x = 10, y = 120;
    private int sx, sy;
    private int currentFrame = FRAME_ATTENTION; // [0, 7]
    private static final int MAX_FRAME = 8;
    private int direction = Constants.DIRECTION_RIGHT;

    public void init() {
        try {
            InputStream is = this.getClass().getResourceAsStream(basePath + "/biaoshi14.png");
            biaoshi14 = ImageIO.read(is);
            IOUtils.closeQuietly(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getBiaoshi14() {
        return biaoshi14;
    }

    public void toLeft(int offsetX, int offsetY) {
        direction = Constants.DIRECTION_LEFT;
        x -= WIDTH/6;
        x = Math.max(x, 0 - WIDTH/6*3);
        increaseCurrentFrame();
    }

    public void increaseCurrentFrame() {
        currentFrame++;
        currentFrame = currentFrame % MAX_FRAME;
    }

    public void toRight(int offsetX, int offsetY) {
        direction = Constants.DIRECTION_RIGHT;
        x += WIDTH/6;
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        PanoramasGroup currentPanoramasGroup = graphicsHolder.getPanoramasHolder().getCurrentPanoramasGroup();
        int mapWidth = currentPanoramasGroup.getPanoramas().getWidth();
        x = Math.min(x, mapWidth - offsetX - WIDTH/6*3);
        increaseCurrentFrame();
    }

    public void toUp() {
        //y -= HEIGHT;
        //y = Math.max(y, 0);
    }
    public void toDown() {
        //y += HEIGHT;
       //y = Math.min(y, biaoshi14.getHeight()/HEIGHT-1);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void reload(int x, int y) {
        this.x = (x);
        this.y = (y);
    }

    public int getSx() {
        return x;
    }

    public int getSy() {
        return y;
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        //LOG.debug("x/y: " + getX() + "/" + getY());
        //LOG.debug(offsetX + getX() + ", " + (offsetY + getY()));
//        g.drawImage(getBiaoshi14(), offsetX + getX(),
//                offsetY + getY(),
//                offsetX + getX() + Player.WIDTH,
//                offsetY + getY() + Player.HEIGHT,
//                getCurrentFrame() * Player.WIDTH, getDirection() * Player.HEIGHT,
//                getCurrentFrame() * Player.WIDTH + Player.WIDTH,
//                getDirection() * Player.HEIGHT + Player.HEIGHT,
//                null);
        g.setColor(Color.YELLOW);
        g.drawRect(getX(), getY(), Player.WIDTH, Player.HEIGHT);
        g.drawImage(getBiaoshi14(), getX(), getY(),
                getX() + Player.WIDTH,
                getY() + Player.HEIGHT,
                getCurrentFrame() * Player.WIDTH, getDirection() * Player.HEIGHT,
                getCurrentFrame() * Player.WIDTH + Player.WIDTH, getDirection() * Player.HEIGHT + Player.HEIGHT,
                null);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * 使人物处于立正状态
     */
    public void attention() {
        this.currentFrame = FRAME_ATTENTION;
    }
}
