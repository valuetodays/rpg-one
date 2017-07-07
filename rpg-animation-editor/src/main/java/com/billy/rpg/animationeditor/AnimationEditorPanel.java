package com.billy.rpg.animationeditor;

import com.billy.rpg.common.constant.AnimationEditorConstant;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * main panel to show the map editor
 */
public class AnimationEditorPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AnimationEditorPanel.class);

    private AffineTransform transform = new AffineTransform();
    private AffineTransformOp ato;
    private AnimationFrame animationFrame;
    private JTextField tfFrameNumber;

    private JTextField tfMapName = null;
    private JTextField tfMapWidth = null;
    private JTextField tfMapHeight = null;

    public AnimationEditorPanel(AnimationFrame mapEditorFrame) {
        this.animationFrame = mapEditorFrame;
        setPreferredSize(new Dimension(AnimationEditorConstant.PANEL_WIDTH, AnimationEditorConstant.PANEL_HEIGHT));
        LOG.debug("new " + this.getClass().getSimpleName() + "()");
        transform.setToScale(1, 1);
        ato = new AffineTransformOp(transform, null);
        initComponents();
    }

    private void initComponents() {
        LayoutManager borderLayout = new BorderLayout(5, 5);
        setLayout(borderLayout);
        JPanel panelWest = new JPanel();
        //panelWest.setBounds(41, 34, 313, 194);
        panelWest.setPreferredSize(new Dimension(260, 200)); // height is unused
        panelWest.setBackground(new Color(241, 219, 179));
        panelWest.setBorder(BorderFactory.createTitledBorder("基本设定"));
       // panelWest.setLayout(new CardLayout());
        panelWest.add(new JLabel("帧数"));
        tfFrameNumber = new JTextField(10);
        panelWest.add(tfFrameNumber);
        //
        add(panelWest, BorderLayout.WEST);


        // add north start
        JPanel panelNorth = new JPanel();
        JLabel labelMapName = new JLabel("动画名");
        panelNorth.add(labelMapName);
        tfMapName = new JTextField(10);
        tfMapName.setText("降魔伏法");
        panelNorth.add(tfMapName);
        JLabel labelMapWidth = new JLabel("宽");
        panelNorth.add(labelMapWidth);
        tfMapWidth = new JTextField(10);
        tfMapWidth.setText("20");
        panelNorth.add(tfMapWidth);
        JLabel labelMapHeight = new JLabel("高");
        panelNorth.add(labelMapHeight);
        tfMapHeight = new JTextField(10);
        tfMapHeight.setText("15");
        panelNorth.add(tfMapHeight);
        JButton btnApply = new JButton("应用");
        panelNorth.add(btnApply);
        panelNorth.setBackground(new Color(79, 189, 58));
        bindApplyListener(btnApply);
        add(panelNorth, BorderLayout.NORTH); // add north panel to main panel
        // add north end

        JPanel panelEast = new JPanel();
        panelEast.setBackground(new Color(241, 187, 232));
        panelEast.setBorder(BorderFactory.createTitledBorder("图片资源"));
        //panelEast.add(new )
        add(panelEast, BorderLayout.EAST); // add north panel to main panel


    }

    private void bindApplyListener(JButton btnApply) {
        btnApply.addMouseListener( new MouseListener() {
            // 点击后鼠标移开不会触发此方法
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
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


    private BufferedImage background;
    private BufferedImage dest;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (background != null) {
            if (dest == null) {
                dest = new BufferedImage(
                        AnimationEditorConstant.PANEL_WIDTH,
                        AnimationEditorConstant.PANEL_HEIGHT,
                        BufferedImage.TYPE_4BYTE_ABGR);
            }

            ato.filter(background, dest);
            g.drawImage(dest, 0, 0, null);
        }
    }




}
