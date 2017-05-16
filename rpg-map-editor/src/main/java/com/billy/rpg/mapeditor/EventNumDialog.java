package com.billy.rpg.mapeditor;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * event number dialog
 *
 * event number description
 *
 * 255 (ff) transfer
 *
 * @author liulei
 * @since 2017-05-12 09:24
 */
public class EventNumDialog extends JDialog {
    private static final Logger LOG = Logger.getLogger(EventNumDialog.class);
    private JButton btnOK;
    private JTextField tfEventNum;
    private MapEditorFrame mapEditorFrame;
    private EventNumDialog instance;

    public EventNumDialog(MapEditorFrame frame) {
        super(frame, "请指定事件编号", true);
        this.mapEditorFrame = frame;
        LayoutManager borderLayout = new BorderLayout(5, 5);
        setLayout(borderLayout);

        tfEventNum = new JTextField();

        add(tfEventNum, BorderLayout.NORTH);
        btnOK = new JButton("确定");
        add(btnOK, BorderLayout.SOUTH);
        bindOKListener();
        setBounds(120, 120, 180, 100);
        instance = this;
    }

    private void bindOKListener() {
        btnOK.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = tfEventNum.getText();

                if (StringUtils.isEmpty(text)) {
                    return ;
                }
                text = text.trim();
                if (!StringUtils.isNumeric(text)) {
                    return ;
                }

                int eventNum = Integer.parseInt(text);
                LOG.debug("clicked, and get `event num="+eventNum+"`.");
                mapEditorFrame.getMapEditorPanel().setEventNum(eventNum);
                tfEventNum.setText("1".trim());
//                instance.setVisible(false);
                instance.dispose();
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
}
