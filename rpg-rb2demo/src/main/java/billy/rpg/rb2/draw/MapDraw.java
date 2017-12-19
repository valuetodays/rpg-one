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
        int mapWidth = currentPanoramasGroup.getPanoramas().getWidth();
        int mapHeight = currentPanoramasGroup.getPanoramas().getHeight();
        Player player = graphicsHolder.getCharacterHolder().getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();
        ;
        if (KeyUtil.isUp(keyCode)) {
            player.toUp();
        } else if (KeyUtil.isDown(keyCode)) {
            player.toDown();
        } else if (KeyUtil.isLeft(keyCode)) {

            if (mapWidth <= RB2Panel.PANEL_WIDTH) {
                int nextPlayerX = playerX - speed;
                player.toLeft(offsetX, offsetY);
            } else {
                if (offsetX >= mapWidth - RB2Panel.PANEL_WIDTH) {
                    if (playerX > RB2Panel.PANEL_WIDTH/2) {
                        int nextPlayerX = playerX - speed;
                        player.toLeft(offsetX, offsetY);
                    } else {
                        int nextPlayerX = playerX - speed;
                        offsetX -= speed;
                        offsetX = Math.max(offsetX, 0);
                        player.increaseCurrentFrame();
                    }
                } else if (offsetX > 0) {
                    int nextPlayerX = playerX - speed;
                    offsetX -= speed;
                    offsetX = Math.max(offsetX, 0);
                    player.increaseCurrentFrame();
                } else if (offsetX <= 0) {
                    if (playerX <= RB2Panel.PANEL_WIDTH / 2) {
                        int nextPlayerX = playerX - speed;
                        player.toLeft(offsetX, offsetY);
                    } else {
                        int nextPlayerX = playerX - speed;
                        offsetX -= speed;
                        offsetX = Math.max(offsetX, 0);
                        player.increaseCurrentFrame();
                    }
                }
            }
        } else if (KeyUtil.isRight(keyCode)) {
            if (mapWidth < RB2Panel.PANEL_WIDTH) {
                player.toRight(offsetX, offsetY);
            } else {
                if (offsetX <= 0) {
                    if (playerX < RB2Panel.PANEL_WIDTH/2) {
                        player.toRight(offsetX, offsetY);
                    } else {
                        offsetX += speed;
                        offsetX = Math.min(offsetX, mapWidth - RB2Panel.PANEL_WIDTH);
                        player.increaseCurrentFrame();
                    }
                } else if (offsetX < mapWidth - RB2Panel.PANEL_WIDTH) {
                    offsetX += speed;
                    offsetX = Math.min(offsetX, mapWidth - RB2Panel.PANEL_WIDTH);
                    player.increaseCurrentFrame();
                } else if (offsetX >= mapWidth - RB2Panel.PANEL_WIDTH) {
                    if (playerX >= RB2Panel.PANEL_WIDTH/2) {
                        player.toRight(offsetX, offsetY);
                    } else {
                        offsetX += speed;
                        offsetX = Math.min(offsetX, mapWidth - RB2Panel.PANEL_WIDTH);
                        player.increaseCurrentFrame();
                    }
                }
            }
        }
    }

    /**
     * 绘制顺序是远景 全景 角色 近景
     * @param g g
     */
    private void drawPanoramas(Graphics g) {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        Player player = graphicsHolder.getCharacterHolder().getPlayer();
        PanoramasGroup panoramasGroup = graphicsHolder.getPanoramasHolder().getCurrentPanoramasGroup();
        LOG.debug("offsetX/offsetY: " + offsetX + "/" + offsetY);
        panoramasGroup.draw(g, offsetX, offsetY);
        player.draw(g, offsetX, offsetY);
        panoramasGroup.draw2(g, offsetX, offsetY);
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
