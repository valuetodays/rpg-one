package com.billy.rpg.animationeditor;

import com.billy.rpg.common.constant.AnimationEditorConstant;
import com.billy.rpg.resource.animation.AnimationLoader;
import com.billy.rpg.resource.animation.AnimationMetaData;
import com.billy.rpg.resource.animation.AnimationSaver;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;


/**
 * main panel to show the map editor
 */
public class AnimationEditorPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AnimationEditorPanel.class);


    private JTextField tfFrameCount; // 帧数
    private JTextField tfNumber; // 动画编号
    private JFileChooser picsFileChooser;
    private JFileChooser aniSaveFileChooser;
    private JFileChooser aniLoadFileChooser;

    private JTextField tfFrameX; //
    private JTextField tfFrameY;
    private JTextField tfFrameShow;
    private JTextField tfFrameNshow;
    private JTextField tfFramePicNumber;

    private JList<Integer> jlistFrames;
    private FrameData[] frameDataArr; // 每一帧的数据
    private JList<Integer> jlistPics;
    private java.util.List<BufferedImage> picImageList = new ArrayList<>();

    // 播放
    private java.util.List<Key> mShowList = new LinkedList<>();


    private class Key {
        int index;
        int show;
        int nshow;

        /**
         * @param index frame index
         */
        private Key(int index) {
            this.index = index;
            this.show = frameDataArr[index].show;
            this.nshow = frameDataArr[index].nShow;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new AnimationEditorPanel());
        frame.setTitle("动画编辑器");
        frame.setLocation(500, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.pack();
        LOG.info("AnimationEditorPanel starts");
    }

    public AnimationEditorPanel() {
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
        FileFilter filterPng = new FileNameExtensionFilter( "png file", "png");
        picsFileChooser = new JFileChooser();
        picsFileChooser.setMultiSelectionEnabled(true);
        picsFileChooser.setCurrentDirectory(new File("."));
        picsFileChooser.setFileFilter(filterPng);
        aniSaveFileChooser = new JFileChooser();
        aniSaveFileChooser.setCurrentDirectory(new File("."));
        final FileFilter filterAni = new FileNameExtensionFilter( "ani file", "ani");
        aniSaveFileChooser.setFileFilter(filterAni);


        aniLoadFileChooser = new JFileChooser();
        aniLoadFileChooser.setCurrentDirectory(new File("."));
        aniLoadFileChooser.setFileFilter(filterAni);
    }

    private void initComponents() throws Exception {
        this.setLayout(null);
        JButton btnSave = new JButton("save");
        btnSave.setToolTipText("将当前动画保存成文件");
        btnSave.setMargin(new Insets(1,1,1,1));
        btnSave.setBounds(280, 10, 35, 20);
        this.add(btnSave);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save2AniFile();
            }
        });
        JButton btnLoad = new JButton("load");
        btnLoad.setToolTipText("加载一个动画文件");
        btnLoad.setMargin(new Insets(1,1,1,1));
        btnLoad.setBounds(316, 10, 35, 20);
        this.add(btnLoad);
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFromAniFile();
            }
        });
        JButton btnPlay = new JButton("play");
        btnPlay.setToolTipText("播放当前动画");
        btnPlay.setMargin(new Insets(1,1,1,1));
        btnPlay.setBounds(280, 40, 70, 20);
        this.add(btnPlay);
        btnPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAnimation();
            }
        });
        this.add(btnPlay);

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
                LOG.debug("n=" + n);
                n = Math.max(0, n);
                String numberText = text.substring(0, n);
                LOG.debug("numberText:" + numberText);
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
                    //
                    //FrameData[] frameDataArr = new FrameData[84];
                    frameDataArr[0] = new FrameData(0, 40, 0, 7, 1);
                    frameDataArr[1] = new FrameData(1, 15, 10, 7, 1);
                    frameDataArr[2] = new FrameData(2, 80, 12, 7, 1);
                    frameDataArr[3] = new FrameData(1, 38, 0, 7, 6);
                    frameDataArr[4] = new FrameData(0, 38, 4, 7, 1);
                    frameDataArr[5] = new FrameData(1, 13, 14, 7, 1);
                    frameDataArr[6] = new FrameData(2, 78, 16, 7, 1);
                    frameDataArr[7] = new FrameData(0, 34, 6, 7, 6);
                    frameDataArr[8] = new FrameData(0, 40, 6, 7, 1);
                    frameDataArr[9] = new FrameData(1, 15, 16, 7, 1);
                    frameDataArr[10] = new FrameData(2, 80, 18, 7, 1);
                    frameDataArr[11] = new FrameData(0, 32, 10, 7, 6);
                    frameDataArr[12] = new FrameData(1, 43, 9, 7, 1);
                    frameDataArr[13] = new FrameData(2, 18, 19, 7, 1);
                    frameDataArr[14] = new FrameData(3, 83, 21, 7, 1);
                    frameDataArr[15] = new FrameData(1, 36, 12, 7, 6);
                    frameDataArr[16] = new FrameData(1, 38, 12, 7, 1);
                    frameDataArr[17] = new FrameData(2, 13, 22, 7, 1);
                    frameDataArr[18] = new FrameData(3, 78, 24, 7, 1);
                    frameDataArr[19] = new FrameData(3, 38, 16, 7, 6);
                    frameDataArr[20] = new FrameData(0, 34, 18, 7, 1);
                    frameDataArr[21] = new FrameData(3, 9, 28, 7, 1);
                    frameDataArr[22] = new FrameData(1, 74, 30, 7, 1);
                    frameDataArr[23] = new FrameData(2, 36, 21, 7, 6);
                    frameDataArr[24] = new FrameData(0, 32, 22, 7, 1);
                    frameDataArr[25] = new FrameData(3, 7, 32, 7, 1);
                    frameDataArr[26] = new FrameData(1, 72, 34, 7, 1);
                    frameDataArr[27] = new FrameData(5, 32, 28, 7, 6);
                    frameDataArr[28] = new FrameData(1, 36, 24, 7, 1);
                    frameDataArr[29] = new FrameData(0, 11, 34, 7, 1);
                    frameDataArr[30] = new FrameData(0, 76, 36, 7, 1);
                    frameDataArr[31] = new FrameData(5, 28, 32, 7, 6);
                    frameDataArr[32] = new FrameData(3, 38, 28, 7, 1);
                    frameDataArr[33] = new FrameData(0, 13, 38, 7, 1);
                    frameDataArr[34] = new FrameData(2, 78, 40, 7, 1);
                    frameDataArr[35] = new FrameData(4, 26, 36, 7, 6);
                    frameDataArr[36] = new FrameData(2, 36, 33, 7, 1);
                    frameDataArr[37] = new FrameData(1, 11, 43, 7, 1);
                    frameDataArr[38] = new FrameData(2, 76, 45, 7, 1);
                    frameDataArr[39] = new FrameData(4, 28, 40, 7, 6);
                    frameDataArr[40] = new FrameData(5, 32, 40, 7, 1);
                    frameDataArr[41] = new FrameData(7, 7, 50, 7, 1);
                    frameDataArr[42] = new FrameData(6, 72, 52, 7, 1);
                    frameDataArr[43] = new FrameData(5, 30, 44, 7, 6);
                    frameDataArr[44] = new FrameData(5, 28, 44, 7, 1);
                    frameDataArr[45] = new FrameData(6, 3, 54, 7, 1);
                    frameDataArr[46] = new FrameData(6, 68, 56, 7, 1);
                    frameDataArr[47] = new FrameData(5, 32, 47, 7, 6);
                    frameDataArr[48] = new FrameData(4, 26, 48, 7, 1);
                    frameDataArr[49] = new FrameData(6, 1, 58, 7, 1);
                    frameDataArr[50] = new FrameData(5, 66, 60, 7, 1);
                    frameDataArr[51] = new FrameData(5, 36, 50, 7, 6);
                    frameDataArr[52] = new FrameData(4, 28, 52, 7, 1);
                    frameDataArr[53] = new FrameData(6, 3, 62, 7, 1);
                    frameDataArr[54] = new FrameData(5, 68, 64, 7, 1);
                    frameDataArr[55] = new FrameData(7, 36, 52, 7, 6);
                    frameDataArr[56] = new FrameData(5, 30, 56, 7, 1);
                    frameDataArr[57] = new FrameData(4, 5, 66, 7, 1);
                    frameDataArr[58] = new FrameData(7, 70, 68, 7, 1);
                    frameDataArr[59] = new FrameData(7, 36, 56, 7, 6);
                    frameDataArr[60] = new FrameData(5, 32, 59, 7, 1);
                    frameDataArr[61] = new FrameData(4, 7, 69, 7, 1);
                    frameDataArr[62] = new FrameData(7, 72, 71, 7, 1);
                    frameDataArr[63] = new FrameData(7, 34, 60, 7,6);
                    frameDataArr[64] = new FrameData(5, 36, 62, 7, 1);
                    frameDataArr[65] = new FrameData(4, 11, 72, 7, 1);
                    frameDataArr[66] = new FrameData(4, 76, 74, 7, 1);
                    frameDataArr[67] = new FrameData(5, 36, 64, 7, 6);
                    frameDataArr[68] = new FrameData(7, 36, 64, 7, 1);
                    frameDataArr[69] = new FrameData(6, 11, 74, 7, 1);
                    frameDataArr[70] = new FrameData(4, 76, 76, 7, 1);
                    frameDataArr[71] = new FrameData(4, 36, 68, 7, 6);
                    frameDataArr[72] = new FrameData(7, 36, 68, 7, 1);
                    frameDataArr[73] = new FrameData(4, 11, 78, 7, 1);
                    frameDataArr[74] = new FrameData(4, 76, 80, 7, 1);
                    frameDataArr[75] = new FrameData(4, 38, 72, 7, 6);
                    frameDataArr[76] = new FrameData(7, 34, 72, 7, 1);
                    frameDataArr[77] = new FrameData(5, 9, 82, 7, 1);
                    frameDataArr[78] = new FrameData(7, 74, 84, 7, 1);
                    frameDataArr[79] = new FrameData(5, 38, 80, 7, 6);
                    frameDataArr[80] = new FrameData(5, 36, 76, 7, 1);
                    frameDataArr[81] = new FrameData(6, 11, 86, 7, 6);
                    frameDataArr[82] = new FrameData(4, 36, 80, 7, 6);
                    frameDataArr[83] = new FrameData(4, 38, 84, 7, 6);
                    //
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
                LOG.debug("n=" + n);
                n = Math.max(0, n);
                String numberText = text.substring(0, n);
                LOG.debug("numberText:" + numberText);
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
        this.add(btnPicAdd);
        JButton btnPicCls = new JButton("cls");
        btnPicCls.setMargin(new Insets(1, 1, 1, 1));
        btnPicCls.setBounds(320, 70, 30, 20);
        btnPicCls.setToolTipText("删除当前Pic，若没有选中项，则不进行删除。");
        btnPicCls.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (picImageList.isEmpty()) {
                    return;
                }
                int selectedIndex = jlistPics.getSelectedIndex();
                if (-1 == selectedIndex) {
                    picImageList.remove(picImageList.size()-1);
                } else {
                    picImageList.remove(selectedIndex);
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
                    repaint();
                    int frameIndex = jlistFrames.getSelectedIndex();
                    if (-1 == frameIndex) {
                        return;
                    }
                    frameDataArr[frameIndex].picNumber = jlistPics.getSelectedIndex();
                    updateXYShowNshow();
               // }
            }
        });
        JScrollPane jspPics = new JScrollPane(jlistPics);
        jspPics.setBounds(280, 90, 70, 120);
        this.add(jspPics);
    }

    Timer timer = new Timer(30, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean update = update();
            LOG.debug("play at " + System.currentTimeMillis() + " with " + update);
            if (!update) {
                LOG.debug("animation playing end");
                timer.stop();
            }
            repaint();
        }
    });
    private void playAnimation() {
        LOG.debug("play the animation. ");
        timer.stop();
        mShowList.clear();
        mShowList.add(new Key(0));
        timer.start();
    }


    private static final int ITERATOR = 1;
    /**
     * 播放动画
     * @return 返回true说明播放完毕
     */
    private boolean update() {
        for (int j = 0; j < ITERATOR; j++) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key i = iter.next();
                --i.show;
                --i.nshow;
                if (i.nshow == 0 && i.index + 1 < frameDataArr.length) {
                    iter.add(new Key(i.index + 1));
                }
            }
            iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key i = iter.next();
                if (i.show <= 0) { // 该帧的图片显示完成
                    iter.remove();
                }
            }
            if (mShowList.isEmpty())
                return false;
        }
        return true;
    }


    private void loadFromAniFile() {
        LOG.debug("load from aniFile start");

        int result = aniLoadFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = aniLoadFileChooser.getSelectedFile();
            AnimationMetaData loadedAmd = AnimationLoader.load(selectedFile.getPath());
            AnimationMetaData amd = new AnimationMetaData();
            tfNumber.setText("" + loadedAmd.getNumber());
            tfFrameCount.setText("" + loadedAmd.getFrameCount());
            setListFrames(loadedAmd.getFrameCount());
            frameDataArr = loadedAmd.getFrameData();
            picImageList = loadedAmd.getImages();
            setListPics();
            LOG.debug("load from aniFile end");
        }
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

        int result = aniSaveFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION){
            File selectedFile = aniSaveFileChooser.getSelectedFile();
            String name = aniSaveFileChooser.getName(selectedFile);
            if (!name.endsWith(".ani")) {
                name += ".ani";
            }
            AnimationMetaData amd = new AnimationMetaData();
            amd.setNumber(number);
            amd.setImageCount(picImageList.size());
            amd.setImages(picImageList);
            amd.setFrameCount(frameDataArr.length);
            amd.setFrameData(frameDataArr);
            AnimationSaver.save(aniSaveFileChooser.getCurrentDirectory() + File.separator + name, amd);
            System.out.println("save ani to file end");
        }

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
        tfFramePicNumber.setText("" + frameDataArr[frameIndex].picNumber);
    }

    private void setListFrames(int frameNumber) {
        Integer[] frames = new Integer[frameNumber];
        for (int i = 0; i < frameNumber; i++) {
            frames[i] = i;
        }
        jlistFrames.setListData(frames);
    }

    private void setListPics() {
        Integer[] pics = new Integer[picImageList.size()];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = i;
        }
        jlistPics.setListData(pics);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!mShowList.isEmpty()) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key key = iter.next();
                int frameIndex = key.index;
                int picIndex = frameDataArr[frameIndex].picNumber;
                BufferedImage bufferedImage = picImageList.get(picIndex);
                int x = frameDataArr[frameIndex].x;
                int y = frameDataArr[frameIndex].y;
                g.drawImage(bufferedImage, 400+x, y, null);
            }
        } else {
            //LOG.debug("show...");
//            if (frameDataArr != null && frameDataArr.length > 0) {
//                mShowList.add(new Key(0));
//            }
        }
        int selectedIndex = jlistPics.getSelectedIndex();
        if (-1 == selectedIndex) {
            return;
        }
        //Image image = picImageList.get(selectedIndex);
        //g.drawImage(image, 400, 10, null);
    }




}
