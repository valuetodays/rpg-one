package com.billy.rpg.game.screen;


import org.apache.log4j.Logger;

import com.billy.rpg.game.GameCanvas;

public abstract class BaseScreen {
    protected static final Logger LOG = Logger.getLogger(BaseScreen.class);
    
    public BaseScreen() {
        LOG.debug("new " + this.getClass().getSimpleName() + "()");
    }
    

    public abstract void update(long delta);
    public abstract void draw(GameCanvas gameCanvas);
    public abstract void onKeyDown(int key);
    public abstract void onKeyUp(int key);

    public boolean isPopup() {
        return false;
    }
}
