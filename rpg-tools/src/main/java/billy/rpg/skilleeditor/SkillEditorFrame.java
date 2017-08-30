/*
 * Created by JFormDesigner on Wed Aug 30 13:17:11 CST 2017
 */

package billy.rpg.skilleeditor;

import billy.rpg.animationeditor.AnimationEditorPanel;
import billy.rpg.resource.skill.SkillLoader;
import billy.rpg.resource.skill.SkillMetaData;
import billy.rpg.resource.skill.SkillSaver;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author adfds
 */
public class SkillEditorFrame extends JFrame {
    private static final Logger LOG = Logger.getLogger(AnimationEditorPanel.class);

    private JFileChooser sklSaveFileChooser;
    private JFileChooser sklLoadFileChooser;


    public static void main(String[] args) {
        JFrame frame = new SkillEditorFrame();
        frame.setTitle("技能编辑器");
        frame.setLocation(500, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        String path = frame.getClass().getClassLoader().getResource("").getPath() + "/AnimationEditor.png";
        Image iconImage = Toolkit.getDefaultToolkit().getImage(path);
        frame.setIconImage(iconImage);

        frame.pack();
        LOG.info("SkillEditorFrame starts");
    }


    private void initData() {
        cbType.addItem(new Item(0, "伤害型"));
        //cbType.addItem(new Item(1, "治疗型"));
        cbType.setSelectedIndex(0);
        cbTargetType.addItem(new Item(0, "单体"));
        //cbTargetType.addItem(new Item(0, "全体"));
        cbTargetType.setSelectedIndex(0);

        sklSaveFileChooser = new JFileChooser();
        sklSaveFileChooser.setCurrentDirectory(new File("."));
        final FileFilter filterAni = new FileNameExtensionFilter( "skill file", "skl");
        sklSaveFileChooser.setFileFilter(filterAni);

        sklLoadFileChooser = new JFileChooser();
        sklLoadFileChooser.setCurrentDirectory(new File("."));
        sklLoadFileChooser.setFileFilter(filterAni);
    }

    public SkillEditorFrame() {
        initComponents();
        initData();
    }

    private void btnSaveActionPerformed(ActionEvent e) {
        String tfNumberText = tfNumber.getText();
        if (StringUtils.isEmpty(tfNumberText)) {
            JOptionPane.showMessageDialog(this, "编号不能为空");
            return;
        }
        if (!StringUtils.isNumeric(tfNumberText)) {
            JOptionPane.showMessageDialog(this, "编号只能是整数");
            return;
        }
        int number = Integer.parseInt(tfNumberText);
        if (number <= 0) {
            JOptionPane.showMessageDialog(this, "编号不能小于1");
            return;
        }
        String tfNameText = tfName.getText();
        if (StringUtils.isEmpty(tfNameText)) {
            JOptionPane.showMessageDialog(this, "名称不能为空");
            return;
        }

        int cbTypeIndex = cbType.getSelectedIndex();
        String tfEffectText = tfEffect.getText();
        if (StringUtils.isEmpty(tfEffectText)) {
            JOptionPane.showMessageDialog(this, "效果不能为空");
            return;
        }
        if (!StringUtils.isNumeric(tfEffectText)) {
            JOptionPane.showMessageDialog(this, "效果只能是整数");
            return;
        }
        int effect = Integer.parseInt(tfEffectText);
        if (effect < 0) {
            JOptionPane.showMessageDialog(this, "效果不能小于0");
            return;
        }
        String tfConsumeText = tfConsume.getText();
        if (StringUtils.isEmpty(tfConsumeText)) {
            JOptionPane.showMessageDialog(this, "消耗不能为空");
            return;
        }
        if (!StringUtils.isNumeric(tfConsumeText)) {
            JOptionPane.showMessageDialog(this, "消耗只能是整数");
            return;
        }
        int consume = Integer.parseInt(tfConsumeText);
        if (consume < 0) {
            JOptionPane.showMessageDialog(this, "消耗不能小于0");
            return;
        }
        int cbTargetTypeIndex = cbTargetType.getSelectedIndex();
        String tfAnimationIdText = tfAnimationId.getText();
        if (StringUtils.isEmpty(tfAnimationIdText)) {
            JOptionPane.showMessageDialog(this, "动画不能为空");
            return;
        }
        if (!StringUtils.isNumeric(tfAnimationIdText)) {
            JOptionPane.showMessageDialog(this, "动画只能是整数");
            return;
        }
        int animationId = Integer.parseInt(tfAnimationIdText);
        if (animationId < 0) {
            JOptionPane.showMessageDialog(this, "动画不能小于0");
            return;
        }

        String taDescText = taDesc.getText();
        if (StringUtils.isEmpty(taDescText)) {
            JOptionPane.showMessageDialog(this, "技能说明不能为空");
            return;
        }

        int result = sklSaveFileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = sklSaveFileChooser.getSelectedFile();
            String name = sklSaveFileChooser.getName(selectedFile);
            if (!name.endsWith(".skl")) {
                name += ".skl";
            }
            SkillMetaData smd = new SkillMetaData();
            smd.setNumber(number);
            smd.setName(tfNameText);
            smd.setType(cbTypeIndex);
            smd.setBaseDamage(effect);
            smd.setConsume(consume);
            smd.setTargetType(cbTargetTypeIndex);
            smd.setAnimationId(animationId);
            smd.setDesc(taDescText);

            LOG.debug(smd);
            SkillSaver.save(sklSaveFileChooser.getCurrentDirectory() + File.separator + name, smd);
        }
    }



    private void btnLoadActionPerformed(ActionEvent e) {
        int result = sklLoadFileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = sklLoadFileChooser.getSelectedFile();
            SkillMetaData loadData = SkillLoader.load(selectedFile.getPath());
            tfNumber.setText("" + loadData.getNumber()); // TODO

            LOG.debug("load from aniFile end");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        label1 = new JLabel();
        tfNumber = new JTextField();
        label3 = new JLabel();
        tfName = new JTextField();
        label2 = new JLabel();
        cbType = new JComboBox();
        label4 = new JLabel();
        tfEffect = new JTextField();
        label10 = new JLabel();
        tfConsume = new JTextField();
        label7 = new JLabel();
        cbTargetType = new JComboBox();
        label8 = new JLabel();
        tfAnimationId = new JTextField();
        label5 = new JLabel();
        textField4 = new JTextField();
        label6 = new JLabel();
        taDesc = new JTextArea();
        btnSave = new JButton();
        btnLoad = new JButton();

        //======== this ========
        Container contentPane = getContentPane();

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder("\u57fa\u672c\u4fe1\u606f"));

            //---- label1 ----
            label1.setText("\u7f16\u53f7");
            label1.setToolTipText("\u552f\u4e00\u7f16\u53f7");

            //---- label3 ----
            label3.setText("\u540d\u79f0");
            label3.setToolTipText("\u540d\u79f0");

            //---- label2 ----
            label2.setText("\u7c7b\u578b");
            label2.setToolTipText("\u662f\u653b\u51fb\u578b\uff0c\u8fd8\u662f\u6cbb\u7597\u578b");

            //---- label4 ----
            label4.setText("\u6548\u679c");
            label4.setToolTipText("\u6839\u636e\u7c7b\u578b\u7684\u4e0d\u540c\uff0c\u4ee5\u505a\u4e0d\u540c\u7684\u6548\u679c");

            //---- label10 ----
            label10.setText("\u6d88\u8017");
            label10.setToolTipText("\u4f7f\u7528\u8be5\u6280\u80fd\u4f1a\u6d88\u8017\u591a\u5c11Mp");

            //---- label7 ----
            label7.setText("\u76ee\u6807");
            label7.setToolTipText("\u8be5\u6280\u80fd\u662f\u9488\u5bf9\u4e00\u4e2a\u76ee\u6807\uff0c\u8fd8\u662f\u5168\u4f53");

            //---- label8 ----
            label8.setText("\u52a8\u753b\u7f16\u53f7");
            label8.setToolTipText("\u8be5\u6280\u80fd\u4f7f\u7528\u54ea\u4e2a\u6280\u80fd");

            //---- label5 ----
            label5.setText("\u6301\u7eed\u56de\u5408\u6570");

            //---- label6 ----
            label6.setText("\u8bf4\u660e");
            label6.setToolTipText("\u6280\u80fd\u63cf\u8ff0");

            //---- taDesc ----
            taDesc.setRows(4);
            taDesc.setWrapStyleWord(true);
            taDesc.setLineWrap(true);

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label10, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfConsume, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label7, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbTargetType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label8, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfAnimationId, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                            .addComponent(taDesc, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfEffect, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(panel1Layout.createParallelGroup()
                                            .addComponent(cbType)
                                            .addComponent(tfName, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
                                        .addGap(90, 90, 90)
                                        .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel1Layout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfNumber, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label1)
                            .addComponent(tfNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfName))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(label2)
                                    .addComponent(cbType))
                                .addGap(13, 13, 13))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfEffect, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label10, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfConsume, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel1Layout.createParallelGroup()
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup()
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTargetType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label8, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfAnimationId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(taDesc, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
            );
        }

        //---- btnSave ----
        btnSave.setText("Save");
        btnSave.addActionListener(e -> btnSaveActionPerformed(e));

        //---- btnLoad ----
        btnLoad.setText("Load");
        btnLoad.addActionListener(e -> btnLoadActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(7, Short.MAX_VALUE))
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addComponent(btnSave)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                    .addComponent(btnLoad)
                    .addGap(54, 54, 54))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLoad)
                        .addComponent(btnSave))
                    .addGap(0, 11, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel label1;
    private JTextField tfNumber;
    private JLabel label3;
    private JTextField tfName;
    private JLabel label2;
    private JComboBox cbType;
    private JLabel label4;
    private JTextField tfEffect;
    private JLabel label10;
    private JTextField tfConsume;
    private JLabel label7;
    private JComboBox cbTargetType;
    private JLabel label8;
    private JTextField tfAnimationId;
    private JLabel label5;
    private JTextField textField4;
    private JLabel label6;
    private JTextArea taDesc;
    private JButton btnSave;
    private JButton btnLoad;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

class Item {
    final int key;
    final String value;
    public Item(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}