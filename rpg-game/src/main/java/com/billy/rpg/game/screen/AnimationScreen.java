package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.scriptParser.bean.AnimationDataLoaderBean;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * to show animation (animation, magic )
 *
 * @author liulei
 */
public class AnimationScreen extends BaseScreen {
    private AnimationDataLoaderBean animationDataLoaderBean;
    private List<Key> mShowList = new LinkedList<>();
    private int ITERATOR = 1;
    private int posX;
    private int posY;

    public AnimationScreen(int animationNumber, int posX, int posY) {
        animationDataLoaderBean =
                GameFrame.getInstance().getGameContainer().getAnimationOf(animationNumber);
        this.posX = posX;
        this.posY = posY;
        mShowList.add(new Key(0));
    }

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
                if (i.nshow == 0 && i.index + 1 < animationDataLoaderBean.getFrameData().length) {
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



    @Override
    public void update(long delta) {
        if (!update()) {
           onKeyDown(1);
        }
    }

    private class Key {
        int index;
        int show;
        int nshow;

        /**
         *
         * @param index frame index
         */
        private Key(int index) {
            this.index = index;
            this.show = animationDataLoaderBean.getFrameData()[index].show;
            this.nshow = animationDataLoaderBean.getFrameData()[index].nShow;
        }
    }


    @Override
    public void draw(GameCanvas gameCanvas) {
        //gameCanvas.drawColor(new Color(255, 255, 255));
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);

        Graphics g = paint.getGraphics();
        //g.setColor(new Color(0, 0, 0, 0)); //透明色
       // g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        //int COLOR_WHITE = new Color(204, 199, 237, 255).getRGB();//Color.argb(255, 199, 237, 204);
        //int COLOR_BLACK = new Color(0, 0, 0, 255).getRGB();//Color.argb(255, 0, 0, 0);
        //int COLOR_TRANSP = new Color(0, 0, 0, 0).getRGB();//Color.argb(0, 0, 0, 0);

        if (!mShowList.isEmpty()) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key key = iter.next();
                int frameIndex = key.index;
                int picIndex = animationDataLoaderBean.getFrameData()[frameIndex].picNumber;
                BufferedImage bufferedImage = animationDataLoaderBean.getImages().get(picIndex);
                int x = animationDataLoaderBean.getFrameData()[frameIndex].x;
                int y = animationDataLoaderBean.getFrameData()[frameIndex].y;
                g.drawImage(bufferedImage, x, y, null);
            }
        } else {
           // mShowList.add(new Key(0));
        }
        gameCanvas.drawBitmap(paint, posX, posY);
    }

    @Override
    public void onKeyDown(int key) {
        GameFrame.getInstance().popScreen();
    }

    @Override
    public void onKeyUp(int key) {
    }


    @Override
    public boolean isPopup() {
        return true;// 这个返回false试一下会发现动画会有上帧的残影
    }


}

