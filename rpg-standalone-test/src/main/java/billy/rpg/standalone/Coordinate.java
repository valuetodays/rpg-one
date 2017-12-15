package billy.rpg.standalone;

import java.awt.*;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 17:45
 */
public class Coordinate {

    private static final int MAPGRID_WIDTH = 64;
    private static final int MAPGRID_HEIGHT = 32;

    public Point pointToIndex(int x, int y) {
        y = (y - MAPGRID_HEIGHT * 13 / 2) * 2;
        return new Point((x-y+MAPGRID_WIDTH*10)/MAPGRID_WIDTH - 10,
                (x+y+MAPGRID_WIDTH * 10)/MAPGRID_WIDTH-10);
    }

    public Point pointToIndex(Point point) {
        return pointToIndex(point.x, point.y);
    }

    public Point indexToPoint(int x, int y) {
        return new Point((x + y) * (MAPGRID_WIDTH/2),
                (12 - x + y)*(MAPGRID_HEIGHT/2));
    }

    public Point indexToPoint(Point point) {
        return indexToPoint(point.x, point.y);
    }
}
