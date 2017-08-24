package billy.rpg.mapeditor;

import billy.rpg.resource.npc.NPCImageLoader;
import billy.rpg.resource.npc.NPCMetaData;
import org.apache.commons.lang.StringUtils;
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
        setResizable(false);
        initNpcs();
        add(npcs);

        JLabel labelNumber = new JLabel("唯一编号");
        labelNumber.setToolTipText("唯一编号是指在一个地图中该npc的唯一标识，脚本中可使用'talk 唯一编号 标签'来给该npc触发事件哦~");
        labelNumber.setBounds(10, 10, 60, 20);
        add(labelNumber);
        tfNumber = new JTextField(10);
        tfNumber.setBounds(80, 10, 80, 20);
        add(tfNumber);
        btnOK = new JButton("确定");
        btnOK.setBounds(40, 100, 80, 20);
        add(btnOK);
        bindOKListener();
        setBounds(120, 120, 280, 180);
        instance = this;
    }

    private void initNpcs() {
        if (npcs == null) {
            npcs = new JComboBox<>();
            npcs.setBounds(10, 40, 150, 40);
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
        if (npcs != null) {
            npcs.setSelectedIndex(0);  // 置第一个为选中
        }
    }
    public void setNPCNum(int npcNum) {
        int eventNum = npcNum >> 16;
        int tileNum = npcNum & 0xffff;
        tfNumber.setText("" + eventNum);
        npcs.setSelectedIndex(tileNum-1);
    }

    private void bindOKListener() {
        btnOK.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String npcName = npcNames.get(npcs.getSelectedIndex());
                int npcNum = npcImageLoader.getLittleNpcNum(npcName);
                String txtNumber = tfNumber.getText();
                if (StringUtils.isEmpty(txtNumber)) {
                    // TODO error msg
                    return;
                }
                if (!StringUtils.isNumeric(txtNumber)) {
                    // TODO error msg
                }
                int number = Integer.valueOf(txtNumber);
                int finalNpcNum = (number << 16) + npcNum;

                mapEditorFrame.getMapEditorPanel().setNPC(finalNpcNum);
                instance.setVisible(false);
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
