package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.resource.animation.AnimationMetaData;

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
    private AnimationMetaData animationMetaData;
    private List<Key> mShowList = new LinkedList<>();
    private int ITERATOR = 1;

    public AnimationScreen(int animationNumber) {
        animationMetaData =
                GameFrame.getInstance().getGameContainer().getAnimationImageLoader().getAnimationOf
                (animationNumber);
        mShowList.add(new Key(0));
    }

    private boolean update() {
        for (int j = 0; j < ITERATOR; j++) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key i = iter.next();
                --i.show;
                --i.nshow;
                if (i.nshow == 0 && i.index + 1 < animationMetaData.getFrameData().length) {
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
            this.show = animationMetaData.getFrameData()[index].show;
            this.nshow = animationMetaData.getFrameData()[index].nShow;
        }
    }


    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);

        Graphics g = paint.getGraphics();
        g.setColor(new Color(128, 0, 0));
        //g.fillRect(0, 0, paint.getWidth(), paint.getHeight());

        if (!mShowList.isEmpty()) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key key = iter.next();
                int frameIndex = key.index;
                int picIndex = animationMetaData.getFrameData()[frameIndex].index;
                BufferedImage bufferedImage = animationMetaData.getImages().get(picIndex);
                int x = animationMetaData.getFrameData()[frameIndex].x;
                int y = animationMetaData.getFrameData()[frameIndex].y;
                g.drawImage(bufferedImage, x, y, null);
            }
        } else {
           // mShowList.add(new Key(0));
        }
        gameCanvas.drawBitmap(paint, 400, 240);
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
        return false;
    }


}

