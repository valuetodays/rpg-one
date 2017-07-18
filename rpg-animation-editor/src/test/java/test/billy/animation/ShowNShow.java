package test.billy.animation;

import billy.rpg.resource.animation.AnimationMetaData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class ShowNShow extends JFrame implements Runnable {
    private static final long serialVersionUID = -5832753188786680679L;
    private int ITERATOR = 4;
    private AnimationMetaData animation0;

    public ShowNShow() {
        // load animation
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(500, 100, 640, 660);
        this.setTitle("aaa");
        this.setVisible(true);

        mShowList.add(new Key(0));
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(128, 0, 0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (!mShowList.isEmpty()) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key key = iter.next();
                int frameIndex = key.index;
                int picIndex = animation0.getFrameData()[frameIndex].picNumber;
                BufferedImage bufferedImage = animation0.getImages().get(picIndex);
                int x = animation0.getFrameData()[frameIndex].x;
                int y = animation0.getFrameData()[frameIndex].y;
                g.drawImage(bufferedImage, x, y, null);
            }
        } else {
            mShowList.add(new Key(0));
        }
    }

    public static void main(String[] args) {
        ShowNShow showNShow = new ShowNShow();
        new Thread(showNShow).start();
    }

    private List<Key> mShowList = new LinkedList<>();

    private class Key {
        int index;
        int show;
        int nshow;

        public Key(int index) {
            this.index = index;
            this.show = animation0.getFrameData()[index].show;
            this.nshow = animation0.getFrameData()[index].nShow;
        }
    }

    @Override
    public void run() {
        while (true) {

            update();
            repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean update() {
        for (int j = 0; j < ITERATOR; j++) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key i = iter.next();
                --i.show;
                --i.nshow;
                if (i.nshow == 0 && i.index + 1 < animation0.getFrameData().length) {
                    iter.add(new Key(i.index + 1)); // 下一帧开始显示
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
}
