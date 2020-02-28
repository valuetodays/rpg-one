package billy.rpg.game.core.screen;

import billy.rpg.common.util.DrawUtil;
import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.walkable.WalkableCharacter;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EmotionScreen extends BaseScreen {
    private int playerIdOrNpcId;
    private int type;
    private static final int FRAMES = 8;
    private int currentFrame = 0;
    private int deltas;
    private static final int DELTAS_COUNT = 90;

    public EmotionScreen(int playerIdOrNpcId, int type) {
        this.playerIdOrNpcId = playerIdOrNpcId;
        this.type = type;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        deltas += delta;
        if (deltas > DELTAS_COUNT) {
            deltas -= DELTAS_COUNT;
            if (!update0()) {
                end(gameContainer);
            }
        }
    }

    private void end(GameContainer gameContainer) {
        gameContainer.getGameFrame().popScreen();
    }

    /**
     * 返回false说明表情结束
     */
    private boolean update0() {
        if (++currentFrame >= FRAMES) {
            return false;
        }
        return true;
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_TILE_WIDTH,
                GameConstant.GAME_TILE_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR); // 生成32*32的图像
        Graphics g = paint.getGraphics();
        BufferedImage gameEmotion = (BufferedImage)gameContainer.getGameAboutItem().getGameEmotion();

        DrawUtil.drawSubImage(g, gameEmotion,
                0,0,
                (currentFrame)*GameConstant.GAME_TILE_WIDTH, (type-1) * GameConstant.GAME_TILE_HEIGHT,
                GameConstant.GAME_TILE_WIDTH, GameConstant.GAME_TILE_HEIGHT);
        g.dispose();

        WalkableCharacter playerOrNpc = gameContainer.getActiveScriptItem().getPlayerOrNpc(playerIdOrNpcId);
        int x = playerOrNpc.getPosX();
        int y = playerOrNpc.getPosY();
        // 把图像画到玩家角色的正上方
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint,
                x * GameConstant.GAME_TILE_WIDTH, y * GameConstant.GAME_TILE_HEIGHT - GameConstant.GAME_TILE_HEIGHT);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {

    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {

    }

    @Override
    public boolean isPopup() {
        return true;
    }

}
