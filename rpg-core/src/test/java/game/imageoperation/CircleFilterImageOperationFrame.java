package game.imageoperation;

import com.jhlabs.image.CircleFilter;

import java.awt.*;

/**
 * @author lei.liu
 * @since 2018-12-07 10:48:20
 */
public class CircleFilterImageOperationFrame extends ImageOperationBaseFrame {

    public CircleFilterImageOperationFrame(String title) throws HeadlessException {
        super(title);
    }

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new CircleFilterImageOperationFrame("CircleFilter");
    }


    public void transForm() {
        CircleFilter circleFilter = new CircleFilter();
        imageTarget = circleFilter.filter(imageOrig, imageTarget);
//        imageTarget = ImageUtil.fogImage(imageOrig);
        repaint();
    }
}
