package com.billy.rpg.resource.animation;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-07 10:33
 */
public class FrameData {
    public int index;
    public int x;
    public int y;
    public int show;
    public int nShow;

    public FrameData(int index, int x, int y, int show, int nShow) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.show = show;
        this.nShow = nShow;
    }

    public FrameData() {
    }
}
