package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.character.battle.HeroBattle;
import com.billy.rpg.game.character.battle.MonsterBattle;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.scriptParser.item.ScriptItem;
import com.billy.rpg.game.util.CoreUtil;
import com.billy.rpg.game.util.KeyUtil;
import com.billy.rpg.resource.role.RoleMetaData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author liulei@bshf360.com
 * @since 2017-06-08 15:29
 */
public class BattleScreen extends BaseScreen {
    private Stack<BaseScreen> screenStack = new Stack<>(); //

    public BattleScreen(final int[] metMonsterIds) {
        BattleUIScreen battleUIScreen = new BattleUIScreen(metMonsterIds, this);
        screenStack.push(battleUIScreen);
        GameFrame.getInstance().pushScreen(this);
    }

    @Override
    public void update(long delta) {
//        for (int j = 0; j < screenStack.size(); j++) {
//            BaseScreen baseScreen = screenStack.get(j);
//            baseScreen.update(delta);
//        }

        int n = 0;
        for (n = screenStack.size()-1; n >= 0 ;n--) {
            BaseScreen baseScreen = screenStack.get(n);
            if (!baseScreen.isPopup()) {
                break;
            }
        }

        if (n < 0) {
            n = 0;
        }
        for (int j = n; j < screenStack.size(); j++) {
            BaseScreen baseScreen = screenStack.get(j);
            baseScreen.update(delta);
        }

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
//        for (int j = 0; j < screenStack.size(); j++) {
//            BaseScreen baseScreen = screenStack.get(j);
//            baseScreen.draw(gameCanvas);
//        }
       int n = 0;
        for (n = screenStack.size()-1; n >= 0 ;n--) {
            BaseScreen baseScreen = screenStack.get(n);
            if (!baseScreen.isPopup()) {
                break;
            }
        }

        if (n < 0) {
            n = 0;
        }
        for (int j = n; j < screenStack.size(); j++) {
            BaseScreen baseScreen = screenStack.get(j);
            baseScreen.draw(gameCanvas);
        }
    }

    public void push(BaseScreen baseScreen) {
        if (baseScreen instanceof BattleSuccessScreen) {
            screenStack.clear();
        }
        screenStack.push(baseScreen);
    }
    public void pop() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
        }
    }

    @Override
    public void onKeyDown(int key) {
    }

    @Override
    public void onKeyUp(int key) {
        if (screenStack.size() == 1) {
            screenStack.get(0).onKeyUp(key);
        } else {
            LOG.debug("尽管按了回车键，但上次攻击动画还未结束呢。。");
        }
    }

}
