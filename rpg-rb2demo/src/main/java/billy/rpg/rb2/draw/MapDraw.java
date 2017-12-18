package billy.rpg.rb2.draw;

import billy.rpg.rb2.GameReourceHolder;
import billy.rpg.rb2.RB2Cavas;
import billy.rpg.rb2.RB2Panel;
import billy.rpg.rb2.graphics.GraphicsHolder;
import billy.rpg.rb2.graphics.PanoramasGroup;
import billy.rpg.rb2.graphics.Player;
import billy.rpg.rb2.util.KeyUtil;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 15:17
 */
public class MapDraw extends BaseDraw {
    private static final Logger LOG = Logger.getLogger(MapDraw.class);

    private int speed = 32;
    private int offsetX = 0;
    private int offsetY = 960;

    @Override
    public void onKeyUp(int keyCode) {

    }

    @Override
    public void onKeyDown(int keyCode) {

//        PanoramasGroup panoramasGroup = currentPanoramasGroup();
//        int offsetX = panoramasGroup.getOffsetX();
//        int offsetY = panoramasGroup.getOffsetY();
//        if (KeyUtil.isLeft(keyCode)) {
//            offsetX -= speed;
//        } else if (KeyUtil.isRight(keyCode)) {
//            offsetX += speed;
//        } else if (KeyUtil.isDown(keyCode)) {
//            offsetY += speed;
//        } else if (KeyUtil.isUp(keyCode)) {
//            offsetY -= speed;
//        }
//
//        offsetX = Math.max(offsetX, 0);
//        offsetX = Math.min(offsetX, currentPanoramasWidth() - RB2Panel.PANEL_WIDTH - 1);
//        offsetY = Math.max(offsetY, 0);
//        offsetY = Math.min(offsetY, currentPanoramasHeight() - RB2Panel.PANEL_HEIGHT - 1);
//        LOG.debug("offsetX/offsetY: " + offsetX + "/" + offsetY);
//        panoramasGroup.setOffsetX(offsetX);
//        panoramasGroup.setOffsetY(offsetY);

        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        PanoramasGroup currentPanoramasGroup = graphicsHolder.getPanoramasHolder().getCurrentPanoramasGroup();
        Player player = graphicsHolder.getCharacterHolder().getPlayer();
        if (KeyUtil.isUp(keyCode)) {
            player.toUp();
        } else if (KeyUtil.isDown(keyCode)) {
            player.toDown();
        } else if (KeyUtil.isLeft(keyCode)) {
            player.toLeft();
            offsetX -= speed;
            offsetX = Math.max(offsetX, 0);
        } else if (KeyUtil.isRight(keyCode)) {
            player.toRight();
            offsetX += speed;
            int width = currentPanoramasGroup.getPanoramas().getWidth();
            offsetX = Math.min(offsetX, width - RB2Panel.PANEL_WIDTH - 1);
        }
    }

    /**
     * 绘制顺序是远景 全景 角色 近景
     * @param g g
     */
    private void drawPanoramas(Graphics g) {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        Player player = graphicsHolder.getCharacterHolder().getPlayer();
        PanoramasGroup panoramasGroup = currentPanoramasGroup();
//        int mapWidth = panoramasGroup.getPanoramas().getWidth();
//        offsetX = player.getX() - RB2Panel.PANEL_WIDTH / 2;
//        if (offsetX < 0) {
//            offsetX = 0;
//        } else if (offsetX > mapWidth - RB2Panel.PANEL_WIDTH) {
//            offsetX = mapWidth - RB2Panel.PANEL_WIDTH;
//        }
//        int mapHeight = panoramasGroup.getPanoramas().getHeight();
//        int offsetY = player.getY() - RB2Panel.PANEL_HEIGHT / 2;
//        if (offsetY < 0) {
//            offsetY = 0;
//        } else if (offsetY > mapHeight - RB2Panel.PANEL_HEIGHT) {
//            offsetY = mapHeight - RB2Panel.PANEL_HEIGHT;
//        }
        LOG.debug("offsetX/offsetY: " + offsetX + "/" + offsetY);
        panoramasGroup.draw(g, offsetX, offsetY);
        player.draw(g, offsetX, offsetY);
        panoramasGroup.draw2(g, offsetX, offsetY);
    }

    private PanoramasGroup currentPanoramasGroup() {
        String panoramasName = currentPanoramasName();
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        return graphicsHolder.getPanoramasHolder().getPanoramasGroup(panoramasName);
    }

    private String currentPanoramasName() {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        return graphicsHolder.getPanoramasHolder().getCurrentName();
    }
    private int currentPanoramasWidth() {
        String panoramasName = currentPanoramasName();
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        return graphicsHolder.getPanoramasHolder().getPanoramasGroup(panoramasName).getPanoramas().getWidth();
    }

    private int currentPanoramasHeight() {
        String panoramasName = currentPanoramasName();
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        return graphicsHolder.getPanoramasHolder().getPanoramasGroup(panoramasName).getPanoramas().getHeight();
    }

    @Override
    public void draw(RB2Cavas cavas) {
        BufferedImage paint = new BufferedImage(
                RB2Panel.PANEL_WIDTH,
                RB2Panel.PANEL_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
//        g.setColor(Color.black);
//        g.drawString("sfsdf", 20, 20);
        drawPanoramas(g);

        g.dispose();
        cavas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void update() {

    }
}
