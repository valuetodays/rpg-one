package billy.rpg.rb2.draw;

import billy.rpg.rb2.RB2Cavas;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-18 15:15
 */
public abstract class BaseDraw {
    public abstract void onKeyUp(int keyCode);
    public abstract void onKeyDown(int keyCode);

    public abstract void draw(RB2Cavas cavas);

    public abstract void update();

}
