package billy.rpg.resource.animation;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-07 10:33
 */
public class FrameData {
    public int picNumber;
    public int x;
    public int y;
    public int show;
    public int nShow;

    public FrameData(int picNumber, int x, int y, int show, int nShow) {
        this.picNumber = picNumber;
        this.x = x;
        this.y = y;
        this.show = show;
        this.nShow = nShow;
    }

    public FrameData() {
    }
}
