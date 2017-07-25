package billy.rpg.mapeditor;

import billy.rpg.resource.npc.NPCImageLoader;
import billy.rpg.resource.npc.NPCMetaData;
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
    private JTextField tfNumber;

    public NPCDialog(MapEditorFrame frame) {
        super(frame, "请选择npc", true);
        this.mapEditorFrame = frame;
        setLayout(null);

        initNpcs();

        add(npcs);
        JLabel labelNumber = new JLabel("唯一编号");
        labelNumber.setToolTipText("唯一编号是指在一个地图中该npc的唯一标识，脚本中可使用'talk 唯一编号 标签'来给该npc触发事件哦~");
        add(labelNumber);
        tfNumber = new JTextField(10);
        add(tfNumber);
        btnOK = new JButton("确定");
        add(btnOK);
        bindOKListener();
        setBounds(120, 120, 280, 180);
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
