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
    private int offsetY = 0;

    @Override
    public void onKeyUp(int keyCode) {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        Player player = graphicsHolder.getCharacterHolder().getPlayer();
        player.attention();
    }

    @Override
    public void onKeyDown(int keyCode) {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        PanoramasGroup currentPanoramasGroup = graphicsHolder.getPanoramasHolder().getCurrentPanoramasGroup();
        int mapWidth = currentPanoramasGroup.getPanoramas().getWidth();
        int mapHeight = currentPanoramasGroup.getPanoramas().getHeight();
        BufferedImage panoramasWalk = currentPanoramasGroup.getPanoramasWalk();
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
                if (inMapX(offsetX + nextPlayerX)) {
                    int rgb = panoramasWalk.getRGB(offsetX + nextPlayerX, offsetY + playerY);
                    if (Color.WHITE.getRGB() == rgb) {
                        player.toLeft(offsetX, offsetY);
                    }
                }
            } else {
                if (offsetX >= mapWidth - RB2Panel.PANEL_WIDTH) {
                    if (playerX > RB2Panel.PANEL_WIDTH/2) {
                        int nextPlayerX = playerX - speed;
                        if (inMapX(offsetX+nextPlayerX)) {
                            int rgb = panoramasWalk.getRGB(offsetX+nextPlayerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                player.toLeft(offsetX, offsetY);
                            }
                        }
                    } else {
                        int nextOffsetX = offsetX - speed;
                        if (inMapX(nextOffsetX+playerX)) {
                            int rgb = panoramasWalk.getRGB(nextOffsetX+playerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                offsetX = nextOffsetX;
                                offsetX = Math.max(offsetX, 0);
                                player.increaseCurrentFrame();
                            }
                        }
                    }
                } else if (offsetX > 0) {
                    int nextOffsetX = offsetX - speed;
                    if (inMapX(nextOffsetX + playerX)) {
                        int rgb = panoramasWalk.getRGB(nextOffsetX + playerX, offsetY + playerY);
                        if (Color.WHITE.getRGB() == rgb) {
                            offsetX = nextOffsetX;
                            offsetX = Math.max(offsetX, 0);
                            player.increaseCurrentFrame();
                        }
                    }
                } else if (offsetX <= 0) {
                    if (playerX <= RB2Panel.PANEL_WIDTH / 2) {
                        int nextPlayerX = playerX - speed;
                        LOG.debug("x=" + (offsetX+nextPlayerX) + ", y=" + playerY);
                        if (inMapX(offsetX + nextPlayerX)) {
                            int rgb = panoramasWalk.getRGB(offsetX + nextPlayerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                player.toLeft(offsetX, offsetY);
                            }
                        }
                    } else {
                        int nextOffsetX = offsetX - speed;
                        if (inMapX(nextOffsetX + playerX)) {
                            int rgb = panoramasWalk.getRGB(nextOffsetX + playerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                offsetX = nextOffsetX;
                                offsetX = Math.max(offsetX, 0);
                                player.increaseCurrentFrame();
                            }
                        }
                    }
                }
            }
        } else if (KeyUtil.isRight(keyCode)) {
            if (mapWidth < RB2Panel.PANEL_WIDTH) {
                int nextPlayerX = playerX + speed;
                if (inMapX(offsetX + nextPlayerX)) {
                    int rgb = panoramasWalk.getRGB(offsetX + nextPlayerX, offsetY + playerY);
                    if (Color.WHITE.getRGB() == rgb) {
                        player.toRight(offsetX, offsetY);
                    }
                }
            } else {
                if (offsetX <= 0) {
                    if (playerX < RB2Panel.PANEL_WIDTH/2) {
                        int nextPlayerX = playerX + speed;
                        if (inMapX(offsetX + nextPlayerX)) {
                            int rgb = panoramasWalk.getRGB(offsetX + nextPlayerX, offsetY + playerY);
                            Color c = new Color(rgb);
                            if (Color.WHITE.getRGB() == rgb) {
                                player.toRight(offsetX, offsetY);
                            }
                        }
                    } else {
                        int nextOffsetX = offsetX + speed;
                        if (inMapX(nextOffsetX + playerX)) {
                            int rgb = panoramasWalk.getRGB(nextOffsetX + playerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                offsetX = nextOffsetX;
                                offsetX = Math.min(offsetX, mapWidth - RB2Panel.PANEL_WIDTH);
                                player.increaseCurrentFrame();
                            }
                        }
                    }
                } else if (offsetX < mapWidth - RB2Panel.PANEL_WIDTH) {
                    int nextOffsetX = offsetX + speed;
                    if (inMapX(nextOffsetX + playerX)) {
                        int rgb = panoramasWalk.getRGB(nextOffsetX + playerX, offsetY + playerY);
                        if (Color.WHITE.getRGB() == rgb) {
                            offsetX = nextOffsetX;
                            offsetX = Math.min(offsetX, mapWidth - RB2Panel.PANEL_WIDTH);
                            player.increaseCurrentFrame();
                        }
                    }
                } else if (offsetX >= mapWidth - RB2Panel.PANEL_WIDTH) {
                    if (playerX >= RB2Panel.PANEL_WIDTH/2) {
                        int nextPlayerX = playerX + speed;
                        if (inMapX(offsetX + nextPlayerX)) {
                            int rgb = panoramasWalk.getRGB(offsetX + nextPlayerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                player.toRight(offsetX, offsetY);
                            }
                        }
                    } else {
                        int nextOffsetX = offsetX + speed;
                        if (inMapX(nextOffsetX + playerX)) {
                            int rgb = panoramasWalk.getRGB(nextOffsetX + playerX, offsetY + playerY);
                            if (Color.WHITE.getRGB() == rgb) {
                                offsetX = nextOffsetX;
                                offsetX = Math.min(offsetX, mapWidth - RB2Panel.PANEL_WIDTH);
                                player.increaseCurrentFrame();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean inMapX(int x) {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        PanoramasGroup currentPanoramasGroup = graphicsHolder.getPanoramasHolder().getCurrentPanoramasGroup();
        int mapWidth = currentPanoramasGroup.getPanoramas().getWidth();
        int mapHeight = currentPanoramasGroup.getPanoramas().getHeight();
        return x >= 0 && x <= mapWidth;
    }

    /**
     * 绘制顺序是远景 全景 角色 近景
     * @param g g
     */
    private void drawPanoramas(Graphics g) {
        GraphicsHolder graphicsHolder = GameReourceHolder.getInstance().getGraphicsHolder();
        Player player = graphicsHolder.getCharacterHolder().getPlayer();
        PanoramasGroup panoramasGroup = graphicsHolder.getPanoramasHolder().getCurrentPanoramasGroup();
        //LOG.debug("offsetX/offsetY: " + offsetX + "/" + offsetY);
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
