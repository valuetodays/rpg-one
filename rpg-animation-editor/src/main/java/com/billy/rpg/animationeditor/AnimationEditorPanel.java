package com.billy.rpg.animationeditor;

import com.billy.rpg.common.constant.AnimationEditorConstant;
import com.billy.rpg.resource.animation.FrameData;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * main panel to show the map editor
 */
public class AnimationEditorPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AnimationEditorPanel.class);

    private AnimationEditorFrame animationFrame;
    private JTextField tfFrameCount; // 帧数
    private JTextField tfNumber; // 动画编号
    private JFileChooser picsFileChooser;

    private JTextField tfFrameX; //
    private JTextField tfFrameY;
    private JTextField tfFrameShow;
    private JTextField tfFrameNshow;
    private JTextField tfFramePicNumber;

    private JList<Integer> jlistFrames;
    private FrameData[] frameDataArr; // 每一帧的数据
    private JList<Integer> jlistPics;
    private java.util.List<Image> picImageList = new ArrayList<>();
    private java.util.List<String> picDataList  = new ArrayList<>();

    public AnimationEditorPanel(AnimationEditorFrame animationFrame) {
        this.animationFrame = animationFrame;
        setPreferredSize(new Dimension(AnimationEditorConstant.PANEL_WIDTH, AnimationEditorConstant.PANEL_HEIGHT));
        LOG.debug("new " + this.getClass().getSimpleName() + "()");

        try {
            initComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assignValues();
    }

    private void assignValues() {
        tfFrameCount.setText("0");
        FileFilter filter = new FileNameExtensionFilter( "png file", "png");
        picsFileChooser = new JFileChooser();
        picsFileChooser.setMultiSelectionEnabled(true);
        picsFileChooser.setCurrentDirectory(new File("."));
        picsFileChooser.setFileFilter(filter);
    }

    private void initComponents() throws Exception {
        this.setLayout(null);
        JButton btnSave = new JButton("save");
        btnSave.setMargin(new Insets(1,2,1,2));
        btnSave.setBounds(280, 10, 60, 20);
        this.add(btnSave);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save2AniFile();
            }
        });


        JLabel lableNumber = new JLabel("编号");
        lableNumber.setBounds(10, 10, 30, 20);
        this.add(lableNumber);
        tfNumber = new JTextField(10);
        tfNumber.setBounds(40, 10, 40, 20);
        this.add(tfNumber);

        JLabel lableFrameCount = new JLabel("帧数");
        lableFrameCount.setBounds(10, 40, 30, 20);
        this.add(lableFrameCount);
        tfFrameCount = new JTextField();
        tfFrameCount.setBounds(40, 40, 40, 20);
        tfFrameCount.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();
                if (text == null) {
                    textField.setText("");
                    return;
                }
                if (text.startsWith("-")) {
                    text = text.substring(1);
                }

                int n = 0;
                for (n = 0; n < text.length(); n++) {
                    char ch = text.charAt(n);
                    if (!Character.isDigit(ch)) {
                        break;
                    }
                }
                System.out.println("n=" + n);
                n = Math.max(0, n);
                String numberText = text.substring(0, n);
                System.out.println("numberText:" + numberText);
                if (StringUtils.EMPTY.equals(numberText)) {
                    numberText = "0";
                }
                int frameNumber = Integer.parseInt(numberText);
                if (frameNumber <= 0) {
                    textField.setText("");
                    jlistFrames.setListData(new Integer[0]);
                    frameDataArr = new FrameData[0];
                } else {
                    textField.setText(numberText);
                    setListFrames(frameNumber);
                    frameDataArr = new FrameData[frameNumber];
                    for (int x = 0; x < frameNumber; x++) {
                        frameDataArr[x] = new FrameData();
                    }
                }
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }
        });
        this.add(tfFrameCount);

        JLabel lableFrameNumebers = new JLabel("帧号");
        lableFrameNumebers.setBounds(10, 70, 30, 20);
        this.add(lableFrameNumebers);

        jlistFrames = new JList<>();
        //jlistFrames.setListData(labels);
        jlistFrames.addListSelectionListener((ListSelectionEvent e) -> {
            // Make sure selection change  is final
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = jlistFrames.getSelectedIndex();
                Object selectedValue = jlistFrames.getSelectedValue();
                System.out.println("selected index = " + selectedIndex + ", selected value = " + selectedValue);
                updateXYShowNshow();
            }
        });
        JScrollPane jspFrames = new JScrollPane(jlistFrames);
        jspFrames.setBounds(10, 90, 70, 120);
        this.add(jspFrames);


        KeyAdapter keyAdapter = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();
                if (text == null) {
                    textField.setText("");
                    return;
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
                System.out.println("n=" + n);
                n = Math.max(0, n);
                String numberText = text.substring(0, n);
                System.out.println("numberText:" + numberText);
                if (StringUtils.EMPTY.equals(numberText)) {
                    numberText = "0";
                }
                int frameNumber = Integer.parseInt(numberText);
                if (frameNumber <= 0) {
                    textField.setText("");
                } else {
                    textField.setText(numberText);
                }
            }
        };

        JLabel lableFrameX = new JLabel("x");
        lableFrameX.setBounds(150, 80, 60, 20);
        this.add(lableFrameX);
        tfFrameX = new JTextField();
        tfFrameX.setBounds(135, 100, 35, 20);
        tfFrameX.addKeyListener(keyAdapter);
        this.add(tfFrameX);

        JLabel lableFrameY = new JLabel("y");
        lableFrameY.setBounds(220, 80, 60, 20);
        this.add(lableFrameY);
        tfFrameY = new JTextField();
        tfFrameY.setBounds(205, 100, 35, 20);
        tfFrameY.addKeyListener(keyAdapter);
        this.add(tfFrameY);

        JLabel lableFrameShow = new JLabel("show"); // 比 lableFrameX的y坐标多50
        lableFrameShow.setBounds(138, 130, 60, 20);
        this.add(lableFrameShow);
        tfFrameShow = new JTextField();
        tfFrameShow.setBounds(135, 150, 35, 20);
        tfFrameShow.addKeyListener(keyAdapter);
        this.add(tfFrameShow);

        JLabel lableFrameNshow = new JLabel("nshow");
        lableFrameNshow.setBounds(205, 130, 60, 20);
        this.add(lableFrameNshow);
        tfFrameNshow = new JTextField();
        tfFrameNshow.setBounds(205, 150, 35, 20);
        tfFrameNshow.addKeyListener(keyAdapter);
        this.add(tfFrameNshow);

        JLabel lableFramePicNumber = new JLabel("图号"); // 比 lableFrameX的y坐标多50
        lableFramePicNumber.setBounds(138, 180, 60, 20);
        this.add(lableFramePicNumber);
        tfFramePicNumber = new JTextField();
        tfFramePicNumber.setBounds(205, 180, 35, 20);
        tfFramePicNumber.addKeyListener(keyAdapter);
        this.add(tfFramePicNumber);


        JButton btnPicAdd = new JButton("add");
        btnPicAdd.setMargin(new Insets(1, 1, 1, 1));
        btnPicAdd.setBounds(280, 70, 30, 20);
        btnPicAdd.setToolTipText("添加一个Pic到当前选中项的尾部，若没有选中项，则添加到尾部。");
        btnPicAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = picsFileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File[] selectedFiles = picsFileChooser.getSelectedFiles();
                    for (File file : selectedFiles) {
                        //String name = picsFileChooser.getSelectedFile().getPath();
                        String name = file.getPath();
                        LOG.debug("file: " + name);

                        int selectedIndex = jlistPics.getSelectedIndex();
                        try {
                            if (-1 == selectedIndex) {
                                picDataList.add(name);
                                BufferedImage read = ImageIO.read(new File(name));
                                picImageList.add(read);
                            } else {
                                BufferedImage read = ImageIO.read(new File(name));
                                picImageList.add(selectedIndex + 1, read);
                                picDataList.add(selectedIndex + 1, name);
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    setListPics();
                }
            }
        });
        this.add(btnPicAdd);
        JButton btnPicCls = new JButton("cls");
        btnPicCls.setMargin(new Insets(1, 1, 1, 1));
        btnPicCls.setBounds(320, 70, 30, 20);
        btnPicCls.setToolTipText("删除当前Pic，若没有选中项，则不进行删除。");
        btnPicCls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (picDataList.isEmpty()) {
                    return;
                }
                int selectedIndex = jlistPics.getSelectedIndex();
                if (-1 == selectedIndex) {
                    picDataList.remove(picDataList.size()-1);
                } else {
                    picDataList.remove(selectedIndex);
                }
                setListPics();
            }
        });
        this.add(btnPicCls);
        jlistPics = new JList<>();
        jlistPics.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
               // if (!e.getValueIsAdjusting()) {
                    int frameIndex = jlistFrames.getSelectedIndex();
                    if (-1 == frameIndex) {
                        return;
                    }
                    frameDataArr[frameIndex].index = jlistPics.getSelectedIndex();
                    updateXYShowNshow();
                    repaint();
               // }
            }
        });
        JScrollPane jspPics = new JScrollPane(jlistPics);
        jspPics.setBounds(280, 90, 70, 120);
        this.add(jspPics);


        // add north start
        /*JPanel panelNorth = new JPanel();
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
        add(panelNorth, BorderLayout.NORTH);*/
        // add north end

       /* JPanel panelEast = new JPanel();
        panelEast.setBackground(new Color(241, 187, 232));
        panelEast.setBorder(BorderFactory.createTitledBorder("图片资源"));
        //panelEast.add(new )
        add(panelEast, BorderLayout.EAST); */


    }

    /**
     * save data to *.ani
     */
    private void save2AniFile() {
        System.out.println("save ani to file start");
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

        String tfFrameCountText = tfFrameCount.getText();
        if (StringUtils.isEmpty(tfFrameCountText)) {
            JOptionPane.showMessageDialog(null, "帧数不能为空");
            return ;
        }
        if (!StringUtils.isNumeric(tfFrameCountText)) {
            JOptionPane.showMessageDialog(null, "帧数只能是整数");
            return;
        }
        int frameCount = Integer.parseInt(tfFrameCountText);
        if (frameCount <= 0) {
            JOptionPane.showMessageDialog(null, "帧数不能小于1");
            return;
        }

        System.out.println("save ani to file end");
    }

    private void updateXYShowNshow() {
        int frameIndex = jlistFrames.getSelectedIndex();
        if (-1 == frameIndex) {
            return;
        }
        tfFrameX.setText("" + frameDataArr[frameIndex].x);
        tfFrameY.setText("" + frameDataArr[frameIndex].y);
        tfFrameShow.setText("" + frameDataArr[frameIndex].show);
        tfFrameNshow.setText("" + frameDataArr[frameIndex].nShow);
        tfFramePicNumber.setText("" + frameDataArr[frameIndex].index);
    }

    private void setListFrames(int frameNumber) {
        Integer[] frames = new Integer[frameNumber];
        for (int i = 0; i < frameNumber; i++) {
            frames[i] = i;
        }
        jlistFrames.setListData(frames);
    }

    private void setListPics() {
        Integer[] pics = new Integer[picDataList.size()];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = i;
        }
        jlistPics.setListData(pics);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int selectedIndex = jlistPics.getSelectedIndex();
        if (-1 == selectedIndex) {
            return;
        }
        Image image = picImageList.get(selectedIndex);
        g.drawImage(image, 80, 300, null);
    }




}
