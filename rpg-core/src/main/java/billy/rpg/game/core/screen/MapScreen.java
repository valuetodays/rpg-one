package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.character.walkable.HeroWalkableCharacter;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.constants.ScreenCodeEnum;
import billy.rpg.game.core.constants.WalkableConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.item.ScriptItem;
import billy.rpg.game.core.screen.window.MapWindow;
import billy.rpg.game.core.script.event.EventTableDeterminer;
import billy.rpg.game.core.util.KeyUtil;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * 地图界面
 */
public class MapScreen extends BaseScreen {

    private MapWindow mapWindow = new MapWindow(this);

    @Override
    public void update(GameContainer gameContainer, long delta) {
        ScriptItem activeScriptItem = gameContainer.getActiveScriptItem();
        if (activeScriptItem == null) {
            throw new RuntimeException("没有ActiveScriptItem");
        }
        CmdProcessor cmdProcessor = activeScriptItem.getCmdProcessor();
        if (cmdProcessor != null) {
            cmdProcessor.update(gameContainer);
        }
    }


    /**
     * @param desktopCanvas desktopCanvas
     */
    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        mapWindow.update(g);
        g.dispose();

        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        ScriptItem active = gameContainer.getActiveScriptItem();
        active.checkMonster(gameContainer);
        HeroWalkableCharacter hero = active.getHero();
        if (KeyUtil.isLeft(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.LEFT);
        } else if (KeyUtil.isRight(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.RIGHT);
        } else if (KeyUtil.isUp(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.UP);
        } else if (KeyUtil.isDown(key)) {
            hero.setDirection(WalkableConstant.PositionEnum.DOWN);
        }

        if (!active.checkTrigger(gameContainer)) { // 检查触发器
            active.toCheckTrigger(); // 设置下一步要检查触发器
            if (KeyUtil.isLeft(key)) {
                hero.decreaseX(gameContainer, this);
            } else if (KeyUtil.isRight(key)) {
                hero.increaseX(gameContainer, this);
            } else if (KeyUtil.isUp(key)) {
                hero.decreaseY(gameContainer, this);
            } else if (KeyUtil.isDown(key)) {
                hero.increaseY(gameContainer, this);
            }
        }
    }


    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
        if (KeyUtil.isEsc(key)) {
            gameContainer.getGameFrame().changeScreen(ScreenCodeEnum.SCREEN_CODE_SYSTEM_UI_SCREEN);
            return;
        } else if (KeyUtil.isHome(key)) {
            ScriptItem active = gameContainer.getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();

            BaseScreen bs = new AnimationScreen(gameContainer, 10001, hero.getPosX() * 32, hero.getPosY() * 32, this);
            gameContainer.getGameFrame().pushScreen(bs);
            return;
        } else if (KeyUtil.isLeft(key) || KeyUtil.isRight(key) || KeyUtil.isUp(key) || KeyUtil.isDown(key)) {
            ScriptItem active = gameContainer.getActiveScriptItem();
            HeroWalkableCharacter hero = active.getHero();
            hero.resetFrame();
        } else if (KeyUtil.isG(key)) {
            EventTableDeterminer.getInstance().printEvents();
        } else if (KeyUtil.isQ(key)) {
            gameContainer.getGameData().exChangeControlId();
        }
    }

    @Override
    public boolean isPopup() {
        return false;
    }

    public void increaseOffsetX(GameContainer gameContainer) {
        mapWindow.increaseOffsetX(gameContainer);
    }

    public void decreaseOffsetX(GameContainer gameContainer) {
        mapWindow.decreaseOffsetX(gameContainer);
    }

    public void increaseOffsetY(GameContainer gameContainer) {
        mapWindow.increaseOffsetY(gameContainer);
    }

    public void decreaseOffsetY(GameContainer gameContainer) {
        mapWindow.decreaseOffsetY(gameContainer);
    }

    public int getOffsetTileX() {
        return mapWindow.getOffsetTileX();
    }

    public int getOffsetTileY() {
        return mapWindow.getOffsetTileY();
    }

    public void clearOffset() {
        setOffset(0, 0);
    }

    public void setOffset(int offsetX, int offsetY) {
        mapWindow.setOffsetTileX(offsetX);
        mapWindow.setOffsetTileY(offsetY);
    }
}
