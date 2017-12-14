/*
 * Created by JFormDesigner on Thu Aug 24 16:09:47 CST 2017
 */

package billy.rpg.animationeditor;

import billy.rpg.resource.animation.AnimationLoader;
import billy.rpg.resource.animation.AnimationMetaData;
import billy.rpg.resource.animation.AnimationSaver;
import billy.rpg.resource.animation.FrameData;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
 * @author unknown
 */
public class AnimationEditorFrame extends JFrame {
    private static final Logger LOG = Logger.getLogger(AnimationEditorPanel.class);

    public AnimationEditorFrame instance;
    private FrameData[] frameDataArr; // 每一帧的数据
    private java.util.List<BufferedImage> picImageList = new ArrayList<>();
    private JFileChooser picsFileChooser;
    private JFileChooser aniSaveFileChooser;
    private JFileChooser aniLoadFileChooser;


    public AnimationEditorFrame getInstance() {
        return instance;
    }

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

    public AnimationEditorFrame() {
        initComponents();
        assignValues();
        instance = this;
    }

    public static void main(String[] args) {
        JFrame frame = new AnimationEditorFrame();
        frame.setTitle("动画编辑器");
        frame.setLocation(500, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        String path = frame.getClass().getClassLoader().getResource("").getPath() + "/AnimationEditor.png";
        Image iconImage = Toolkit.getDefaultToolkit().getImage(path);
        frame.setIconImage(iconImage);

        frame.pack();
        LOG.info("AnimationEditorPanel starts");
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

    private void tfFrameCountKeyReleased(KeyEvent e) {
        String frameCountText = tfFrameCount.getText();
        int frameCount = Integer.parseInt(frameCountText);
        if (frameCount <= 0) {
            tfFrameCount.setText("");
            frameList.setListData(new Integer[0]);
            frameDataArr = new FrameData[0];
        } else {
            tfFrameCount.setText(""+frameCount);
            setFrameList(frameCount);
            frameDataArr = new FrameData[frameCount];
            for (int x = 0; x < frameCount; x++) {
                frameDataArr[x] = new FrameData();
            }
        }
    }

    private void setFrameList(int frameCount) {
        Integer[] frames = new Integer[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = i;
        }
        frameList.setListData(frames);
    }

    private void btnPicAddActionPerformed(ActionEvent e) {
        int result = picsFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = picsFileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                String name = file.getPath();

                int selectedIndex = picList.getSelectedIndex();
                try {
                    if (-1 == selectedIndex) {
                        BufferedImage read = ImageIO.read(new File(name));
                        picImageList.add(read);
                    } else {
                        BufferedImage read = ImageIO.read(new File(name));
                        picImageList.add(selectedIndex + 1, read);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            setPicList();
        }
    }

    private void setPicList() {
        Integer[] pics = new Integer[picImageList.size()];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = i;
        }
        picList.setListData(pics);
    }

    private void btnPicClsActionPerformed(ActionEvent e) {
        if (picImageList.isEmpty()) {
            return;
        }
        int selectedIndex = picList.getSelectedIndex();
        if (-1 == selectedIndex) {
            picImageList.remove(picImageList.size()-1);
        } else {
            picImageList.remove(selectedIndex);
        }
        setPicList();
    }

    private void btnSaveActionPerformed(ActionEvent e) {
        save2AniFile();
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

    private void btnLoadActionPerformed(ActionEvent e) {
        loadFromAniFile();
    }

    private void loadFromAniFile() {
        LOG.debug("load from aniFile start");

        int result = aniLoadFileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = aniLoadFileChooser.getSelectedFile();
            AnimationMetaData loadedAmd = AnimationLoader.load(selectedFile.getPath());
            tfNumber.setText("" + loadedAmd.getNumber());
            tfFrameCount.setText("" + loadedAmd.getFrameCount());
            setFrameList(loadedAmd.getFrameCount());
            frameDataArr = loadedAmd.getFrameData();
            picImageList = loadedAmd.getImages();
            setPicList();
            LOG.debug("load from aniFile end");
        }
    }

    private void btnPlayActionPerformed(ActionEvent e) {
        playAnimation();
    }

    private Timer timer = new Timer(30, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean update = update();
            LOG.debug("play at " + System.currentTimeMillis() + " with " + update);
            if (!update) {
                LOG.debug("animation playing end");
                timer.stop();
            }
            panelPreview.repaint();
        }
    });

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


    private void playAnimation() {
        if (frameDataArr == null || frameDataArr.length == 0) {
            return;
        }
        LOG.debug("play the animation. ");

        timer.stop();
        mShowList.clear();
        mShowList.add(new Key(0));
        timer.start();
    }

    private void frameListValueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedIndex = frameList.getSelectedIndex();
            Object selectedValue = frameList.getSelectedValue();
            System.out.println("selected index = " + selectedIndex + ", selected value = " + selectedValue);
            updateXYShowNshow();
        }
    }

    private void updateXYShowNshow() {
        int frameIndex = frameList.getSelectedIndex();
        if (-1 == frameIndex) {
            return;
        }
        tfX.setText("" + frameDataArr[frameIndex].x);
        tfY.setText("" + frameDataArr[frameIndex].y);
        tfShow.setText("" + frameDataArr[frameIndex].show);
        tfNShow.setText("" + frameDataArr[frameIndex].nShow);
        tfPicNumber.setText("" + frameDataArr[frameIndex].picNumber);
    }


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

    private void tfXKeyReleased(KeyEvent e) {
        JTextField textField = (JTextField) e.getSource();
        int n = getNumberOfTextField(textField);
        textField.setText("" + n);
        int selectedIndex = frameList.getSelectedIndex();
        if (-1 == selectedIndex) {
            return;
        }
        frameDataArr[selectedIndex].x = n;
    }

    private void tfYKeyReleased(KeyEvent e) {
        JTextField textField = (JTextField) e.getSource();
        int n = getNumberOfTextField(textField);
        textField.setText("" + n);
        int selectedIndex = frameList.getSelectedIndex();
        if (-1 == selectedIndex) {
            return;
        }
        frameDataArr[selectedIndex].y = n;
    }

    private void tfShowKeyReleased(KeyEvent e) {
        JTextField textField = (JTextField) e.getSource();
        int n = getNumberOfTextField(textField);
        textField.setText("" + n);
        int selectedIndex = frameList.getSelectedIndex();
        if (-1 == selectedIndex) {
            return;
        }
        frameDataArr[selectedIndex].show = n;
    }

    private void tfNShowKeyReleased(KeyEvent e) {
        JTextField textField = (JTextField) e.getSource();
        int n = getNumberOfTextField(textField);
        textField.setText("" + n);
        int selectedIndex = frameList.getSelectedIndex();
        if (-1 == selectedIndex) {
            return;
        }
        frameDataArr[selectedIndex].nShow = n;
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        lNumber = new JLabel();
        tfNumber = new JTextField();
        tfFrameCount = new JTextField();
        lNumber2 = new JLabel();
        panel2 = new JPanel();
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        frameList = new JList();
        panel3 = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        tfX = new JTextField();
        tfY = new JTextField();
        label4 = new JLabel();
        label5 = new JLabel();
        tfShow = new JTextField();
        tfNShow = new JTextField();
        label6 = new JLabel();
        tfPicNumber = new JTextField();
        panelPreview = new JPanel() {
            @Override
            public void paint(Graphics g) {
                //super.paint(g);
                if (!mShowList.isEmpty()) {
                    ListIterator<Key> iter = mShowList.listIterator();
                    while (iter.hasNext()) {
                        Key key = iter.next();
                        int frameIndex = key.index;
                        int picIndex = frameDataArr[frameIndex].picNumber;
                        BufferedImage bufferedImage = picImageList.get(picIndex);
                        int x = frameDataArr[frameIndex].x;
                        int y = frameDataArr[frameIndex].y;
                        g.drawImage(bufferedImage, 20+x, y, null);
                    }
                }

                int selectedIndex = picList.getSelectedIndex();
                if (-1 == selectedIndex) {
                    return;
                }
                Image image = picImageList.get(selectedIndex);
                g.drawImage(image, 10, 10, null);
            }
        };
        panel5 = new JPanel();
        btnSave = new JButton();
        btnLoad = new JButton();
        btnPlay = new JButton();
        panel6 = new JPanel();
        scrollPane2 = new JScrollPane();
        picList = new JList();
        picList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // if (!e.getValueIsAdjusting()) {
                repaint();
                int frameIndex = frameList.getSelectedIndex();
                if (-1 == frameIndex) {
                    return;
                }
                frameDataArr[frameIndex].picNumber = picList.getSelectedIndex();
                updateXYShowNshow();
                // }
            }
        });

        btnPicAdd = new JButton();
        btnPicCls = new JButton();
        textPane1 = new JTextPane();
        panelLogo = new JPanel(){
            @Override
            public void paint(Graphics g) {
                String path = AnimationEditorFrame.class.getClassLoader().getResource("").getPath() +
                        "/AnimationEditor.png";
                Image iconImage = Toolkit.getDefaultToolkit().getImage(path);
                g.drawImage(iconImage, 40, 0, null);
                //g.dispose();
                AnimationEditorFrame.this.repaint();
                //getInstance().repaint();
            }
        };

        //======== this ========
        Container contentPane = getContentPane();

        //======== panel1 ========
        {
            panel1.setBorder(new TitledBorder(null, "\u8bbe\u5b9a", TitledBorder.LEADING, TitledBorder.TOP));

            //---- lNumber ----
            lNumber.setText("\u7f16\u53f7");

            //---- tfFrameCount ----
            tfFrameCount.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    tfFrameCountKeyReleased(e);
                }
            });

            //---- lNumber2 ----
            lNumber2.setText("\u5e27\u6570");

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(lNumber, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addComponent(lNumber2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfFrameCount, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                            .addComponent(tfNumber))
                        .addContainerGap(47, Short.MAX_VALUE))
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lNumber)
                            .addComponent(tfNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(lNumber2)
                            .addComponent(tfFrameCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        //======== panel2 ========
        {
            panel2.setBorder(new TitledBorder("\u5e27\u4fe1\u606f"));

            //---- label1 ----
            label1.setText("\u5e27\u5217\u8868");

            //======== scrollPane1 ========
            {

                //---- frameList ----
                frameList.addListSelectionListener(e -> frameListValueChanged(e));
                scrollPane1.setViewportView(frameList);
            }

            //======== panel3 ========
            {

                //---- label2 ----
                label2.setText("    x");

                //---- label3 ----
                label3.setText("   y");

                //---- tfX ----
                tfX.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        tfXKeyReleased(e);
                    }
                });

                //---- tfY ----
                tfY.setAutoscrolls(false);
                tfY.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        tfYKeyReleased(e);
                    }
                });

                //---- label4 ----
                label4.setText("show");

                //---- label5 ----
                label5.setText("nShow");
                label5.setFocusable(false);

                //---- tfShow ----
                tfShow.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        tfShowKeyReleased(e);
                    }
                });

                //---- tfNShow ----
                tfNShow.setAutoscrolls(false);
                tfNShow.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        tfNShowKeyReleased(e);
                    }
                });

                //---- label6 ----
                label6.setText("\u56fe\u53f7");

                //---- tfPicNumber ----
                tfPicNumber.setEditable(false);

                GroupLayout panel3Layout = new GroupLayout(panel3);
                panel3.setLayout(panel3Layout);
                panel3Layout.setHorizontalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addGroup(panel3Layout.createParallelGroup()
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addGap(9, 9, 9)
                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panel3Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(tfX)))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(label3, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addComponent(tfY)))
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addGap(0, 9, Short.MAX_VALUE)
                                    .addGroup(panel3Layout.createParallelGroup()
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                            .addComponent(label4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                            .addComponent(tfShow, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(tfNShow, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))))
                                .addGroup(panel3Layout.createSequentialGroup()
                                    .addContainerGap(13, Short.MAX_VALUE)
                                    .addComponent(label6)
                                    .addGap(18, 18, 18)
                                    .addComponent(tfPicNumber, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap())
                );
                panel3Layout.setVerticalGroup(
                    panel3Layout.createParallelGroup()
                        .addGroup(panel3Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(label2))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tfY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label5)
                                .addComponent(label4))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel3Layout.createParallelGroup()
                                .addComponent(tfShow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfNShow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tfPicNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label6))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }

            GroupLayout panel2Layout = new GroupLayout(panel2);
            panel2.setLayout(panel2Layout);
            panel2Layout.setHorizontalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel2Layout.createParallelGroup()
                            .addComponent(label1)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(3, 3, 3))
            );
            panel2Layout.setVerticalGroup(
                panel2Layout.createParallelGroup()
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(label1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel2Layout.createParallelGroup()
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(20, Short.MAX_VALUE))
            );
        }

        //======== panelPreview ========
        {
            panelPreview.setFocusable(false);
            panelPreview.setBorder(new LineBorder(Color.black, 1, true));

            GroupLayout panelPreviewLayout = new GroupLayout(panelPreview);
            panelPreview.setLayout(panelPreviewLayout);
            panelPreviewLayout.setHorizontalGroup(
                panelPreviewLayout.createParallelGroup()
                    .addGap(0, 247, Short.MAX_VALUE)
            );
            panelPreviewLayout.setVerticalGroup(
                panelPreviewLayout.createParallelGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
            );
        }

        //======== panel5 ========
        {

            //---- btnSave ----
            btnSave.setText("save");
            btnSave.setToolTipText("\u5c06\u5f53\u524d\u52a8\u753b\u4fdd\u5b58\u6210\u6587\u4ef6");
            btnSave.addActionListener(e -> btnSaveActionPerformed(e));

            //---- btnLoad ----
            btnLoad.setText("load");
            btnLoad.setToolTipText("\u52a0\u8f7d\u4e00\u4e2a\u52a8\u753b\u6587\u4ef6");
            btnLoad.addActionListener(e -> btnLoadActionPerformed(e));

            //---- btnPlay ----
            btnPlay.setText("play");
            btnPlay.setToolTipText("\u64ad\u653e\u5f53\u524d\u52a8\u753b");
            btnPlay.addActionListener(e -> btnPlayActionPerformed(e));

            GroupLayout panel5Layout = new GroupLayout(panel5);
            panel5.setLayout(panel5Layout);
            panel5Layout.setHorizontalGroup(
                panel5Layout.createParallelGroup()
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoad)
                        .addGap(43, 43, 43))
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnPlay)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            panel5Layout.setVerticalGroup(
                panel5Layout.createParallelGroup()
                    .addGroup(panel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnLoad))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPlay)
                        .addContainerGap())
            );
        }

        //======== panel6 ========
        {

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(picList);
            }

            //---- btnPicAdd ----
            btnPicAdd.setText("add");
            btnPicAdd.setToolTipText("\u6dfb\u52a0\u4e00\u4e2aPic\u5230\u5f53\u524d\u9009\u4e2d\u9879\u7684\u5c3e\u90e8\uff0c\u82e5\u6ca1\u6709\u9009\u4e2d\u9879\uff0c\u5219\u6dfb\u52a0\u5230\u5c3e\u90e8\u3002");
            btnPicAdd.addActionListener(e -> btnPicAddActionPerformed(e));

            //---- btnPicCls ----
            btnPicCls.setText("cls");
            btnPicCls.setToolTipText("\u5220\u9664\u5f53\u524dPic\uff0c\u82e5\u6ca1\u6709\u9009\u4e2d\u9879\uff0c\u5219\u4e0d\u8fdb\u884c\u5220\u9664\u3002");
            btnPicCls.addActionListener(e -> btnPicClsActionPerformed(e));

            //---- textPane1 ----
            textPane1.setText("\u56fe\u7247\u8d44\u6e90");
            textPane1.setEditable(false);

            GroupLayout panel6Layout = new GroupLayout(panel6);
            panel6.setLayout(panel6Layout);
            panel6Layout.setHorizontalGroup(
                panel6Layout.createParallelGroup()
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addGroup(panel6Layout.createParallelGroup()
                            .addGroup(panel6Layout.createSequentialGroup()
                                .addComponent(textPane1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnPicAdd)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPicCls)))
                        .addContainerGap(8, Short.MAX_VALUE))
            );
            panel6Layout.setVerticalGroup(
                panel6Layout.createParallelGroup()
                    .addGroup(panel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPicCls)
                            .addComponent(btnPicAdd))
                        .addGap(23, 23, 23)
                        .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(textPane1, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                        .addGap(51, 51, 51))
            );
        }

        //======== panelLogo ========
        {
            panelLogo.setBorder(new CompoundBorder(
                new EtchedBorder(Color.gray, Color.gray),
                null));

            GroupLayout panelLogoLayout = new GroupLayout(panelLogo);
            panelLogo.setLayout(panelLogoLayout);
            panelLogoLayout.setHorizontalGroup(
                panelLogoLayout.createParallelGroup()
                    .addGap(0, 224, Short.MAX_VALUE)
            );
            panelLogoLayout.setVerticalGroup(
                panelLogoLayout.createParallelGroup()
                    .addGap(0, 100, Short.MAX_VALUE)
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(6, 6, 6)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(panel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(panelPreview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(panel5, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(panelLogo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelLogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(panel6, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(panelPreview, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JLabel lNumber;
    private JTextField tfNumber;
    private JTextField tfFrameCount;
    private JLabel lNumber2;
    private JPanel panel2;
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JList frameList;
    private JPanel panel3;
    private JLabel label2;
    private JLabel label3;
    private JTextField tfX;
    private JTextField tfY;
    private JLabel label4;
    private JLabel label5;
    private JTextField tfShow;
    private JTextField tfNShow;
    private JLabel label6;
    private JTextField tfPicNumber;
    private JPanel panelPreview;
    private JPanel panel5;
    private JButton btnSave;
    private JButton btnLoad;
    private JButton btnPlay;
    private JPanel panel6;
    private JScrollPane scrollPane2;
    private JList picList;
    private JButton btnPicAdd;
    private JButton btnPicCls;
    private JTextPane textPane1;
    private JPanel panelLogo;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
