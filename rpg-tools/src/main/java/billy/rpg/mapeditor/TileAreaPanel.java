package billy.rpg.mapeditor;

import billy.rpg.common.constant.ToolsConstant;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;

/**
 * to show tile
 *
 * @author <a href="http://blog.sina.com.cn/valuetodays">liulei-home</a>
 * @since 2017-04-28 21:40
 */
public class TileAreaPanel extends JPanel {
    private static final Logger LOG = Logger.getLogger(TileAreaPanel.class);

    public static final int TILE_NUM_ONE_LINE = ToolsConstant.TILE_NUM_ONE_LINE;

    private MapEditorPanel mapEditorPanel;
    private File tilePath;
    private String tileId;

    public TileAreaPanel(MapEditorPanel mapEditorPanel) {
        this.mapEditorPanel = mapEditorPanel;
        setBackground(new Color(214, 31, 17));
    }

    private Image tileImage;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (tileImage != null) {
            g.drawImage(tileImage, 0, 0, null);
            Color oldColor = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(rectX*32, rectY*32, width, height);
            g.setColor(oldColor);
//            LOG.debug("paint()");
        }

    }
    private int rectX;
    private int rectY;
    private int width = 32;
    private int height = 32;

    public void bindTileListener() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                rectX = x / 32;
                rectY = y / 32;
//                LOG.debug("rectX/rectY=" + rectX + "/" + rectY);
                repaint();
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int nx = x / 32;
                int ny = y / 32;
                LOG.debug("title...x/y=[" + x + "," + y + "], "
                        + ", nx/ny=" + nx + "/" + ny
                );

                lastTileN = ny * TILE_NUM_ONE_LINE + nx;
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
    }



    private int lastTileN;

    public int getLastTileN() {
        return lastTileN;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void initTileImage(String imagePath) {
        try {
            tileImage = ImageIO.read(new FileInputStream(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tilePath = new File(imagePath);
        tileId = tilePath.getName();
        LOG.debug("tileImage" + (tileImage == null));
        repaint();
    }

    public String getTileId() {
        return tileId;
    }
    public void setTileId(String tileId) {
        // TODO 当直接Load时暂时无法更新tile图片
        String s = tilePath.getParent() + "/" + tileId;
        initTileImage(s);
    }
}
