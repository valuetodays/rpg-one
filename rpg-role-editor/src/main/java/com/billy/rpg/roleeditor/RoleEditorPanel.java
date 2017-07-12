package com.billy.rpg.roleeditor;

import com.billy.rpg.common.constant.RoleEditorConstant;
import com.billy.rpg.resource.role.RoleMetaData;
import com.billy.rpg.resource.role.RoleSaver;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * TODO save to file
 * main panel to show the role editor
 */
public class RoleEditorPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RoleEditorPanel.class);
    private BufferedImage image;
    private JTextField tfNumber; // 编号
    private JTextField tfName; // name
    private JTextField tfHp; // hp
    private JTextField tfMaxHp; // maxHp
    private JTextField tfMp; // mp
    private JTextField tfMaxMp; // maxMp
    private JTextField tfSpeed; // speed
    private JTextField tfAttack; // attack
    private JTextField tfDefend; // defend
    private JTextField tfExp; // exp
    private JTextField tfLevelChain; // TODO levelChain

    private JFileChooser rolePicFileChooser;
    private JFileChooser roleSaveFileChooser;
    private JFileChooser roleLoadFileChooser;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new RoleEditorPanel());
        frame.setTitle("角色编辑器");
        frame.setLocation(500, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.pack();
        LOG.info("RoleEditorPanel starts");
    }

    public RoleEditorPanel() {
        setPreferredSize(new Dimension(RoleEditorConstant.PANEL_WIDTH, RoleEditorConstant.PANEL_HEIGHT));
        LOG.debug("new " + this.getClass().getSimpleName() + "()");

        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assignValues();
    }

    private void assignValues() {
        FileFilter filterPng = new FileNameExtensionFilter( "role png file", "png");
        rolePicFileChooser = new JFileChooser();
        rolePicFileChooser.setMultiSelectionEnabled(true);
        rolePicFileChooser.setCurrentDirectory(new File("."));
        rolePicFileChooser.setFileFilter(filterPng);
        roleSaveFileChooser = new JFileChooser();
        roleSaveFileChooser.setCurrentDirectory(new File("."));
        final FileFilter filterRole = new FileNameExtensionFilter( "role file", "role");
        roleSaveFileChooser.setFileFilter(filterRole);

        roleLoadFileChooser = new JFileChooser();
        roleLoadFileChooser.setCurrentDirectory(new File("."));
        roleLoadFileChooser.setFileFilter(filterRole);
    }

    private void initComponents() throws Exception {
        this.setLayout(null);
        JButton btnSave = new JButton("save");
        btnSave.setToolTipText("将当前角色保存成文件");
        btnSave.setMargin(new Insets(1,1,1,1));
        btnSave.setBounds(280, 10, 35, 20);
        this.add(btnSave);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save2RoleFile();
            }
        });
        JButton btnLoad = new JButton("load");
        btnLoad.setToolTipText("加载一个角色文件");
        btnLoad.setMargin(new Insets(1,1,1,1));
        btnLoad.setBounds(316, 10, 35, 20);
        this.add(btnLoad);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromRoleFile();
            }
        });
        JButton btnPick = new JButton("image");
        btnPick.setToolTipText("选择一张角色图片");
        btnPick.setMargin(new Insets(1,1,1,1));
        btnPick.setBounds(280, 40, 70, 20);
        this.add(btnPick);
        btnPick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    pickImage();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JLabel lableNumber = new JLabel("编号");
        lableNumber.setBounds(10, 10, 30, 20);
        this.add(lableNumber);
        tfNumber = new JTextField(10);
        tfNumber.setBounds(40, 10, 40, 20);
        tfNumber.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfNumber);

        JLabel lableName = new JLabel("名称");
        lableName.setBounds(80, 10, 30, 20);
        this.add(lableName);
        tfName = new JTextField(10);
        tfName.setBounds(110, 10, 80, 20);
        tfName.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfName);

        JLabel lableType = new JLabel("类型");
        lableType.setBounds(10, 30, 30, 20);
        this.add(lableType);
        JComboBox<Item> cbType = new JComboBox<>();
        cbType.addItem(new Item(1, "玩家角色"));
        cbType.addItem(new Item(2, "NPC 角色"));
        cbType.addItem(new Item(3, "妖怪角色"));
        cbType.addItem(new Item(4, "场景对象"));
        cbType.setEditable(false);
        cbType.setBounds(40, 30, 80, 20);
        cbType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Item selectedItem = (Item)cbType.getSelectedItem();
                int key = selectedItem.key;
                String value = selectedItem.value;
                System.out.println("Selected key=" + key + ", value=" + value);
            }
        });
        this.add(cbType);

        JLabel lableHp = new JLabel("hp");
        lableHp.setBounds(10, 80, 30, 20);
        this.add(lableHp);
        tfHp = new JTextField();
        tfHp.setBounds(40, 80, 35, 20);
        tfHp.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        tfHp.setText("1");
        this.add(tfHp);

        JLabel lableMaxHp = new JLabel("maxHp");
        lableMaxHp.setBounds(80, 80, 50, 20);
        this.add(lableMaxHp);
        tfMaxHp = new JTextField();
        tfMaxHp.setBounds(130, 80, 35, 20);
        tfMaxHp.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        tfMaxHp.setText("2");
        this.add(tfMaxHp);

        JLabel lableMp = new JLabel("mp");
        lableMp.setBounds(10, 110, 30, 20);
        this.add(lableMp);
        tfMp = new JTextField();
        tfMp.setBounds(40, 110, 35, 20);
        tfMp.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfMp);

        JLabel lableMaxMp = new JLabel("maxMp");
        lableMaxMp.setBounds(80, 110, 50, 20);
        this.add(lableMaxMp);
        tfMaxMp = new JTextField();
        tfMaxMp.setBounds(130, 110, 35, 20);
        tfMaxMp.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfMaxMp);

        JLabel lableSpeed = new JLabel("speed");
        lableSpeed.setBounds(10, 140, 40, 20);
        this.add(lableSpeed);
        tfSpeed = new JTextField();
        tfSpeed.setBounds(50, 140, 35, 20);
        tfSpeed.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfSpeed);

        JLabel lableAttack = new JLabel("attack");
        lableAttack.setBounds(10, 170, 40, 20);
        this.add(lableAttack);
        tfAttack = new JTextField();
        tfAttack.setBounds(50, 170, 35, 20);
        tfAttack.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfAttack);

        JLabel lableDefend = new JLabel("defend");
        lableDefend.setBounds(10, 200, 40, 20);
        this.add(lableDefend);
        tfDefend = new JTextField();
        tfDefend.setBounds(50, 200, 35, 20);
        tfDefend.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfDefend);

        JLabel lableExp = new JLabel("exp");
        lableExp.setBounds(10, 200, 40, 20);
        this.add(lableExp);
        tfExp = new JTextField();
        tfExp.setBounds(50, 200, 35, 20);
        tfExp.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
            }
        });
        this.add(tfExp);

        /*
        JButton btnPicAdd = new JButton("add");
        btnPicAdd.setMargin(new Insets(1, 1, 1, 1));
        btnPicAdd.setBounds(280, 70, 30, 20);
        btnPicAdd.setToolTipText("添加一个Pic到当前选中项的尾部，若没有选中项，则添加到尾部。");
        btnPicAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = rolePicFileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File[] selectedFiles = rolePicFileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        //String name = rolePicFileChooser.getSelectedFile().getPath();
                        String name = file.getPath();
                        LOG.debug("file: " + name);

                        int selectedIndex = jlistPics.getSelectedIndex();
                        try {
                            if (-1 == selectedIndex) {
                                BufferedImage read = ImageIO.read(new File(name));
                                picImageList.add(read);
                            } else {
                                BufferedImage read = ImageIO.read(new File(name));
                                picImageList.add(selectedIndex + 1, read);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    setListPics();
                }
            }
        });
        this.add(btnPicAdd);*/
    }

    private void pickImage() throws IOException {
        int result = rolePicFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = rolePicFileChooser.getSelectedFile();
            image = ImageIO.read(selectedFile);
        }
    }


    private void loadFromRoleFile() {

    }

    private void save2RoleFile() {
        System.out.println("save role to file start");
        String tfNumberText = tfNumber.getText();
        if (StringUtils.isEmpty(tfNumberText)) {
            JOptionPane.showMessageDialog(null, "编号不能为空");
            return;
        }
        if (!StringUtils.isNumeric(tfNumberText)) {
            JOptionPane.showMessageDialog(null, "编号只能是整数");
            return;
        }
        int number = Integer.parseInt(tfNumberText);
        if (number <= 0) {
            JOptionPane.showMessageDialog(null, "编号不能小于1");
            return;
        }
        String tfNameText = tfName.getText();
        if (StringUtils.isEmpty(tfNameText)) {
            JOptionPane.showMessageDialog(null, "角色名称不能为空");
            return;
        }
        if (tfNameText.length() > 10) {
            JOptionPane.showMessageDialog(null, "角色名称不能大于10个字符。");
            return;
        }


        if (null == image) {
            JOptionPane.showMessageDialog(null, "请选择角色图片");
            return;
        }


        int result = roleSaveFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = roleSaveFileChooser.getSelectedFile();
            String name = roleSaveFileChooser.getName(selectedFile);
            if (!name.endsWith(".role")) {
                name += ".role";
            }
            RoleMetaData roleMetaData = new RoleMetaData();


            RoleSaver.save(roleSaveFileChooser.getCurrentDirectory() + File.separator + name, roleMetaData);
            System.out.println("save ani to file end");
        }

    }

    /**
     * 获取到 输入框中的数字
     * @param textField 输入框
     */
    private int getNumberOfTextField(JTextField textField) {
        String text = textField.getText();
        if (text == null) {
            textField.setText("");
            return 0;
        }
        if (text.startsWith("-")) {
            text = text.substring(1);
        }

        int n = 0;
        for (; n < text.length(); n++) {
            char ch = text.charAt(n);
            if (!Character.isDigit(ch)) {
                break;
            }
        }
        LOG.debug("n=" + n);
        n = Math.max(0, n);
        String numberText = text.substring(0, n);
        LOG.debug("numberText:" + numberText);
        if (StringUtils.EMPTY.equals(numberText)) {
            numberText = "0";
        }
        int number = Integer.parseInt(numberText);
        return number;
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);

//        Image image = picImageList.get(selectedIndex);
//        g.drawImage(image, 135, 200, null);
    }

    private class Item {
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

}
