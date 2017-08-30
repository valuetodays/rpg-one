package billy.rpg.mapeditor;

import billy.rpg.common.constant.MapEditorConstant;
import billy.rpg.common.util.ImageUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2017-04-28 21:40
 */
public class MapAreaPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(MapAreaPanel.class);

    private MapEditorPanel mapEditorPanel;

    public MapAreaPanel(MapEditorPanel mapEditorPanel) {
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(199, 170, 90));
    }

    private int GAME_TILE_NUN_X = 20;
    private int GAME_TILE_NUN_Y = 15;
    private java.util.List<int[][]> layers;
    private int tileXwidth;
    private int tileYheight;
    private int currentLayer = 0;
    private static final int BG_LAYER = 0; // 背景层 background
    private static final int NPC_LAYER = 1; // npc/宝箱层
    private static final int FG_LAYER = 2; // 前景层 foreground
    private static final int WALK_LAYER = 3;
    private static final int EVENT_LAYER = 4;
    private int offsetX;
    private int offsetY;

    /**
     * 置当前层为活动层
     * @param currentLayer 当前层数，注意，此处传的是1~5，但layers的下标是0~4，所以需要减1
     */
    public void setCurrentLayer(int currentLayer) {
        this.currentLayer = currentLayer-1;
        repaint();
    }

    /**
     * 初始化map显示区域，
     * @param width w
     * @param height h
     */
    public void initMapLayer(int width, int height) {
        int[][] layer1 = new int[width][height]; // BG_LAYER
        int[][] layer2 = new int[width][height]; // NPC_LAYER
        int[][] layer3 = new int[width][height]; // FG_LAYER
        int[][] layer4 = new int[width][height]; // WALK_LAYER
        int[][] layer5 = new int[width][height]; // EVENT_LAYER
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                layer1[i][j] = 0;
                layer2[i][j] = -1;
                layer3[i][j] = -1;
                layer4[i][j] = -1;
                layer5[i][j] = -1;
            }
        }
        layers = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);
        layers.add(layer4);
        layers.add(layer5);
        this.tileXwidth = width;
        this.tileYheight = height;

        LOG.debug("mapShow ["+width+"]["+height+"]");
    }

    public int getTileXwidth() {
        return tileXwidth;
    }

    public int getTileYheight() {
        return tileYheight;
    }
    private int rectX;
    private int rectY;

    public void bindMapListener() {
        // mouse move
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                mapEditorPanel.getMapArea().requestFocus();
                int x = e.getX();
                int y = e.getY();
                rectX = x / 32; // TODO 无用？？
                rectY = y / 32;
//                LOG.debug("rectX/rectY=" + rectX + "/" + rectY);
                repaint();
            }
        });

        // mouse click
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                mapEditorPanel.getMapArea().requestFocus();
                int x = e.getX();
                int y = e.getY();
                rectX = offsetX + x / 32;
                rectY = offsetY + y / 32;
                if (rectX > tileXwidth || rectY > tileYheight) {
                    return ;
                }

                // 分开处理行走层，事件层与地图层
                if (currentLayer == WALK_LAYER) { // 行走层
                    if (e.isShiftDown()) {
                        reverseFlagLayer();
                    } else {
                        int[][] flagLayer = layers.get(currentLayer);
                        flagLayer[rectX][rectY] *= -1; //
                    }
                } else if (currentLayer == EVENT_LAYER) { // 事件层 如果点击时按住shift，就把事件清理成-1，否则显示事件框
                    if (e.isShiftDown()) {
                        setEventNum(-1);
                    } else if (e.isControlDown()) { // TODO 暂时只能添加closed-box
                        setEventNum(0xed);
                    } else {
                        // TODO 位置要考虑贴边的情况
                        EventNumDialog eventNumDialog = mapEditorPanel.getMapEditorFrame().getEventNumDialog();
                        eventNumDialog.setLocation(e.getXOnScreen(), e.getYOnScreen());
                        eventNumDialog.setVisible(true);
                    }

                } else if (currentLayer == BG_LAYER || currentLayer == FG_LAYER) { // 背景层或前景层
                    int[][] tmpLayer = layers.get(currentLayer);
                    if (e.isShiftDown()) {
                        tmpLayer[rectX][rectY] = -1;
                    } else {
                        tmpLayer[rectX][rectY] = mapEditorPanel.getTileArea().getLastTileN();
                    }
                    LOG.debug("layer " + currentLayer
                            + " in map (x/y" + x + "/" + y + ")[" + rectX + "," + rectY + "]="
                            + tmpLayer[rectX][rectY]);
                } else if (currentLayer == NPC_LAYER) { // npc层
                    if (e.isShiftDown()) {
                        setNPC(-1);
                    } else {
                        int[][] npcLayer = layers.get(NPC_LAYER);
                        int currentNpcNum = npcLayer[rectX][rectY];
                        NPCDialog npcDialog = mapEditorPanel.getMapEditorFrame().getNpcDialog();
                        npcDialog.setLocation(e.getXOnScreen(), e.getYOnScreen());
                        npcDialog.setNPCNum(currentNpcNum);
                        npcDialog.setVisible(true);
                    }
                }

                repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        // keyboard
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT: {
                        if (offsetX < tileXwidth - GAME_TILE_NUN_X) {
                            offsetX++;
                        }
                    }
                    break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT: {
                        if (offsetX > 0) {
                            offsetX--;
                        }
                    }
                    break;
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP: {
                        if (offsetY > 0) {
                            offsetY--;
                        }
                    }
                    break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN: {
                        if (offsetY < tileYheight - GAME_TILE_NUN_Y) {
                            offsetY++;
                        }
                    }
                    break;
                };
                // LOG.debug("offsetX/y=" + offsetX + "/" + offsetY);
                repaint();
            }

        });
    }

    private void reverseFlagLayer() {
        int[][] flagLayer = layers.get(currentLayer);
        for (int i = 0; i < tileXwidth; i++) {
            for (int j = 0; j < tileYheight; j++) {
                flagLayer[i][j] *= -1;
            }
        }
    }

    @Override
    public String toString() {
        return "{" +
                "tileXwidth=" + tileXwidth +
                ", tileYheight=" + tileYheight +
                ", currentLayer=" + currentLayer +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                "} ";
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //if (tileXwidth > GAME_TILE_NUN_X || tileYheight > GAME_TILE_NUN_Y) {
            String msg = "按w/s/a/d(或上/下/左/右)来左右行动地图";
            g.drawString(msg, 0, 500);
        //}
        mapEditorPanel.getMapEditorFrame().setTitle(MapEditorConstant.MAPEDITOR_NAME + " " + this);

        final Image tileImage = mapEditorPanel.getTileArea().getTileImage();

        if (tileImage == null) {
            LOG.debug("image is null");
            return ;
        }
        if (layers == null) {
            LOG.debug("layers is null");
            return ;
        }

        // 先画地图层(后景，前景)
        for (int layern = 0; layern < WALK_LAYER; layern++) {
            if (layern == NPC_LAYER) {
                continue;
            }
            BufferedImage paint = new BufferedImage(
                    tileXwidth * 32,
                    tileYheight * 32,
                    BufferedImage.TYPE_4BYTE_ABGR);
            // 得到缓冲区的画笔
            Graphics g2 = paint.getGraphics();

            int[][] layer = layers.get(layern);
            for (int i = offsetX; i < offsetX + GAME_TILE_NUN_X; i++) {
                for (int j = offsetY; j < offsetY + GAME_TILE_NUN_Y; j++) {
                    int s = layer[i][j];
                    if (s != -1) {
                        int y = s % 100;
                        int x = s / 100;

                        g2.drawImage(tileImage,
                                (i-offsetX)*32, (j-offsetY)*32,
                                (i-offsetX)*32+32, (j-offsetY)*32+32,
                            x*32, y*32, x*32+32, y*32+32,
                            null);
                    }
                }
            }
            if (layern == currentLayer) {
                g.drawImage(paint, 0, 0, null);
            } else {
                g.drawImage(ImageUtil.toGray(paint), 0, 0, null);
            }
            g.drawRect(rectX * 32, rectY * 32, 32, 32);
        }

        // npc层
        int[][] npcLayer = layers.get(NPC_LAYER);
        for (int i = offsetX; i < offsetX + GAME_TILE_NUN_X; i++) {
            for (int j = offsetY; j < offsetY + GAME_TILE_NUN_Y; j++) {
                int num = npcLayer[i][j];
                if (num != -1) {
                    num = num & 0xffff;
                    BufferedImage image = mapEditorPanel.getMapEditorFrame().getNpcDialog().getImageOf(num);
                    if (NPC_LAYER == currentLayer) {
                        g.drawImage(image, (i-offsetX)*32, (j-offsetY)*32, null);
                    } else {
                        g.drawImage(ImageUtil.toGray(image), (i-offsetX)*32, (j-offsetY)*32, null);
                    }
                }
            }
        }

        if (currentLayer == WALK_LAYER) {
            // 画行走层
            Color oldColor = g.getColor();
            g.setColor(Color.MAGENTA);
            int[][] flagLayer = layers.get(WALK_LAYER);
            for (int i = offsetX; i < offsetX + GAME_TILE_NUN_X; i++) {
                for (int j = offsetY; j < offsetY + GAME_TILE_NUN_Y; j++) {
                    if (flagLayer[i][j] == -1) { // 不可行
                        int topX = (i-offsetX) * 32;
                        int topY = (j-offsetY) * 32;
                        int bottomX = (i-offsetX) * 32 + 32;
                        int bottomY = (j-offsetY) * 32 + 32;

                        // 先画边框，再画交叉
                        g.drawRect(topX, topY, 32, 32);
                        g.drawLine(topX, topY, bottomX, bottomY);
                        g.drawLine(topX, bottomY, bottomX, topY);
                    }
                }
            }
            g.setColor(oldColor);
        } else if (currentLayer == EVENT_LAYER) {
            // 画事件层
            Color oldColor = g.getColor();
            g.setColor(Color.RED);
            int[][] eventLayer = layers.get(EVENT_LAYER);
            for (int i = offsetX; i < offsetX + GAME_TILE_NUN_X; i++) {
                for (int j = offsetY; j < offsetY + GAME_TILE_NUN_Y; j++) {
                    int num = eventLayer[i][j];
                    if (num != -1) {
                        int topX = (i-offsetX) * 32 + 10;
                        int topY = (j-offsetY) * 32 + 20;
                        if (num == 0xee) { // open-box
                            BufferedImage boxImage = mapEditorPanel.getBoxImageLoader().getImageOf(num);
                            g.drawImage(boxImage, (i-offsetX)*32, (j-offsetY)*32, null);
                        } else if (num == 0xed) { // closed-box
                            BufferedImage boxImage = mapEditorPanel.getBoxImageLoader().getImageOf(num);
                            g.drawImage(boxImage, (i-offsetX)*32, (j-offsetY)*32, null);
                        } else {
                            g.drawString(num + "", topX, topY);
                        }
                    }
                }
            }
            g.setColor(oldColor);
        }


    }

    public List<int[][]> getLayers() {
        return layers;
    }

    public void setEventNum(int eventNum) {
        if (currentLayer != EVENT_LAYER) {
            return ;
        }
        int[][] eventLayer = layers.get(EVENT_LAYER);
        eventLayer[rectX][rectY] = eventNum;
    }
    public void setNPC(int npcNum) {
        if (currentLayer != NPC_LAYER) {
            return ;
        }

        int[][] npcLayer = layers.get(NPC_LAYER);
        npcLayer[rectX][rectY] = npcNum;
    }

    public void setLayers(List<int[][]> layers) {
        this.layers = layers;
    }

    public void setTileXwidth(int tileXwidth) {
        this.tileXwidth = tileXwidth;
    }

    public void setTileYheight(int tileYheight) {
        this.tileYheight = tileYheight;
    }


}
