package com.billy.rpg.mapeditor;

import com.billy.rpg.resource.npc.NPCImageLoader;
import com.billy.rpg.resource.npc.NPCMetaData;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * npc dialog
 *
 *
 * @author liulei
 * @since 2017-05-12 09:24
 */
public class NPCDialog extends JDialog {
    private static final Logger LOG = Logger.getLogger(NPCDialog.class);
    private JButton btnOK;
    private NPCImageLoader npcImageLoader = new NPCImageLoader();
    private JComboBox<ImageIcon> npcs;
    private List<String> npcNames;
    private MapEditorFrame mapEditorFrame;
    private NPCDialog instance;

    public NPCDialog(MapEditorFrame frame) {
        super(frame, "请选择npc", true);
        this.mapEditorFrame = frame;
        LayoutManager borderLayout = new BorderLayout(5, 5);
        setLayout(borderLayout);

        initNpcs();

        add(npcs, BorderLayout.NORTH);
        btnOK = new JButton("确定");
        add(btnOK, BorderLayout.SOUTH);
        bindOKListener();
        setBounds(120, 120, 180, 150);
        instance = this;
    }

    private void initNpcs() {
        if (npcs == null) {
            npcs = new JComboBox<>();
            NPCMetaData npcMetaData = npcImageLoader.loadNpcs();
            List<Image> images = npcMetaData.getLittleImages();
            images.forEach(e -> {
                npcs.addItem(new ImageIcon(e));
            });
            npcNames = npcMetaData.getLittleNames();
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        npcs.setSelectedIndex(0);  // 置第一个为选中
    }

    private void bindOKListener() {
        btnOK.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String npcName = npcNames.get(npcs.getSelectedIndex());
                int npcNum = npcImageLoader.getLittleNpcNum(npcName);

                mapEditorFrame.getMapEditorPanel().setNPC(npcNum);
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


    public BufferedImage getImageOf(int npcNum) {
        return npcImageLoader.getLittleImageOf(npcNum);

    }
}
