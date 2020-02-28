package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.screen.battle.BattleScreen;
import billy.rpg.resource.animation.AnimationMetaData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * to show animation (animation, magic )
 * 目前情况下有两种情况
 * - 战斗特效
 * - 过场动画
 *
 * @author liulei
 */
public class AnimationScreen extends BaseScreen {
    private BaseScreen ownerScreen;
    private AnimationMetaData animationMetaData;
    private List<Key> mShowList = new LinkedList<>();
    private int ITERATOR = 1;  // TODO 该值要存放到动画文件里？
    private int posX;
    private int posY;

    public AnimationScreen(GameContainer gameContainer, int animationNumber, int posX, int posY, BaseScreen ownerScreen) {
        animationMetaData = gameContainer.getAnimationOf(animationNumber);
        this.posX = posX;
        this.posY = posY;
        this.ownerScreen = ownerScreen;
        mShowList.add(new Key(0));
    }

    /**
     * 播放动画
     * history: 本方法之前是private的  2017-07-15
     *
     * @return 返回false说明播放完毕
     */
    public boolean update() {
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
    public void update(GameContainer gameContainer, long delta) {
        if (!update()) {
           end(gameContainer);
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
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        if (!mShowList.isEmpty()) {
            ListIterator<Key> iter = mShowList.listIterator();
            while (iter.hasNext()) {
                Key key = iter.next();
                int frameIndex = key.index;
                int picIndex = animationMetaData.getFrameData()[frameIndex].picNumber;
                BufferedImage bufferedImage = animationMetaData.getImages().get(picIndex);
                int x = animationMetaData.getFrameData()[frameIndex].x;
                int y = animationMetaData.getFrameData()[frameIndex].y;
                g.drawImage(bufferedImage, x, y, null);
            }
        } else {
           // mShowList.add(new Key(0));
        }
        g.dispose();

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, posX-70, posY-30);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
    }

    private void end(GameContainer gameContainer) {
        if (ownerScreen instanceof BattleScreen) { // 战斗场景中的播放动画，就把战斗场景弹出一个
            ((BattleScreen)ownerScreen).pop();
        } else {
            gameContainer.getGameFrame().popScreen();
        }
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
    }


    @Override
    public boolean isPopup() {
        return true;// 这个返回false试一下会发现动画会有上帧的残影
    }


}

