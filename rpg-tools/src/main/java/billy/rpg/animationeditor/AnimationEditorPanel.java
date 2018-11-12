package billy.rpg.animationeditor;

import billy.rpg.resource.animation.*;
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
 * main panel to show the animation editor
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

    private AnimationSaverLoader animationSaverLoader = new JsonAnimationSaverLoader();


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
        String path = frame.getContentPane().getClass().getClassLoader().getResource("").getPath() + "/AnimationEditor.png";
        Image iconImage = Toolkit.getDefaultToolkit().getImage(path);
        frame.setIconImage(iconImage);

        frame.pack();
        LOG.info("AnimationEditorPanel starts");
    }

    public AnimationEditorPanel() {
        setPreferredSize(new Dimension(1000, 350));
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
        final FileFilter filterAniJson = new FileNameExtensionFilter( "ani.json file", "ani.json");
        aniSaveFileChooser.setFileFilter(filterAniJson);


        aniLoadFileChooser = new JFileChooser();
        aniLoadFileChooser.setCurrentDirectory(new File("."));
        aniLoadFileChooser.setFileFilter(filterAniJson);
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
                try {
                    save2AniFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                LOG.info("selected index = " + selectedIndex + ", selected value = " + selectedValue);
                updateXYShowNshow();
            }
        });
        JScrollPane jspFrames = new JScrollPane(jlistFrames);
        jspFrames.setBounds(10, 90, 70, 120);
        this.add(jspFrames);


        JLabel lableFrameX = new JLabel("x");
        lableFrameX.setBounds(150, 80, 60, 20);
        this.add(lableFrameX);
        tfFrameX = new JTextField();
        tfFrameX.setBounds(135, 100, 35, 20);
        tfFrameX.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
                int selectedIndex = jlistFrames.getSelectedIndex();
                if (-1 == selectedIndex) {
                    return;
                }
                frameDataArr[selectedIndex].x = n;
            }
        });
        this.add(tfFrameX);

        JLabel lableFrameY = new JLabel("y");
        lableFrameY.setBounds(220, 80, 60, 20);
        this.add(lableFrameY);
        tfFrameY = new JTextField();
        tfFrameY.setBounds(205, 100, 35, 20);
        tfFrameY.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
                int selectedIndex = jlistFrames.getSelectedIndex();
                if (-1 == selectedIndex) {
                    return;
                }
                frameDataArr[selectedIndex].y = n;
            }
        });
        this.add(tfFrameY);

        JLabel lableFrameShow = new JLabel("show"); // 比 lableFrameX的y坐标多50
        lableFrameShow.setBounds(138, 130, 60, 20);
        this.add(lableFrameShow);
        tfFrameShow = new JTextField();
        tfFrameShow.setBounds(135, 150, 35, 20);
        tfFrameShow.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
                int selectedIndex = jlistFrames.getSelectedIndex();
                if (-1 == selectedIndex) {
                    return;
                }
                frameDataArr[selectedIndex].show = n;
            }
        });
        this.add(tfFrameShow);

        JLabel lableFrameNshow = new JLabel("nshow");
        lableFrameNshow.setBounds(205, 130, 60, 20);
        this.add(lableFrameNshow);
        tfFrameNshow = new JTextField();
        tfFrameNshow.setBounds(205, 150, 35, 20);
        tfFrameNshow.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                int n = getNumberOfTextField(textField);
                textField.setText("" + n);
                int selectedIndex = jlistFrames.getSelectedIndex();
                if (-1 == selectedIndex) {
                    return;
                }
                frameDataArr[selectedIndex].nShow = n;
            }
        });
        this.add(tfFrameNshow);

        JLabel lableFramePicNumber = new JLabel("图号"); // 比 lableFrameX的y坐标多50
        lableFramePicNumber.setBounds(138, 180, 60, 20);
        this.add(lableFramePicNumber);
        tfFramePicNumber = new JTextField();
        tfFramePicNumber.setBounds(205, 180, 35, 20);
        tfFramePicNumber.setEnabled(false);
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
        return Integer.parseInt(numberText);
    }

    private Timer timer = new Timer(30, new ActionListener() {
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
        if (frameDataArr == null || frameDataArr.length == 0) {
            return;
        }
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
            AnimationMetaData loadedAmd = null;
            try {
                loadedAmd = animationSaverLoader.load(selectedFile.getPath());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e);
            }
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
    private void save2AniFile() throws IOException {
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
            if (!name.endsWith(".ani.json")) {
                name += ".ani.json";
            }
            AnimationMetaData amd = new AnimationMetaData();
            amd.setNumber(number);
            amd.setImageCount(picImageList.size());
            amd.setImages(picImageList);
            amd.setFrameCount(frameDataArr.length);
            amd.setFrameData(frameDataArr);
            animationSaverLoader.save(aniSaveFileChooser.getCurrentDirectory() + File.separator + name, amd);
            LOG.info("save *.ani.json to file end");
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
        repaint();
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
        int offsetX = 400;
        g.drawRect(400, 0, 200, 200);

        if (!mShowList.isEmpty()) {
            for (Key key : mShowList) {
                int frameIndex = key.index;
                int picIndex = frameDataArr[frameIndex].picNumber;
                BufferedImage bufferedImage = picImageList.get(picIndex);
                int x = frameDataArr[frameIndex].x;
                int y = frameDataArr[frameIndex].y;
                g.drawImage(bufferedImage, offsetX+x, y, null);
            }
        } else {
            int frameIndex = jlistFrames.getSelectedIndex();
            if (-1 == frameIndex) {
                return;
            }
            int n = frameDataArr[frameIndex].picNumber;
            BufferedImage picImage = picImageList.get(n);
            g.drawImage(picImage, offsetX + frameDataArr[frameIndex].x, frameDataArr[frameIndex].y, null);
        }

        {
            int selectedIndex = jlistPics.getSelectedIndex();
            if (-1 == selectedIndex) {
                return;
            }
            Image image = picImageList.get(selectedIndex);
            g.drawImage(image, offsetX + 200, 0, null);
        }
    }




}
