package com.billy.rpg.resource.animation;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-07 10:33
 */
public class FrameData {
    public final int index;
    public final int x;
    public final int y;
    public final int show;
    public final int nShow;

    public FrameData(int index, int x, int y, int show, int nShow) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.show = show;
        this.nShow = nShow;
    }
}
