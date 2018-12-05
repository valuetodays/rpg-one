package j3d_practice;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

/**
 * 左右键控制观察者的旋转
 * 上下键控制观察者的前进后退
 * PageUp/PageDown控制观察者的上下移动
 * +-键控制截平面（）的距离
 * =还原物体位置
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-05 10:53:19
 */
public class SimpleKeyNavigatorBehavior extends Applet {
    private static final long serialVersionUID = 3733967243639197669L;

    public BranchGroup createSceneGraph(SimpleUniverse simpleUniverse) {
        BranchGroup objRoot = new BranchGroup();
        objRoot.addChild(new Axis());
        objRoot.addChild(new ColorCube(0.4));
        Vector3f translate = new Vector3f();
        Transform3D transform3D = new Transform3D();
        TransformGroup transformGroup = simpleUniverse.getViewingPlatform().getViewPlatformTransform();
        translate.set(0.0f, 0.0f, 3.0f);
        transform3D.setTranslation(translate);
        transformGroup.setTransform(transform3D);
        KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(transformGroup);
        keyNavigatorBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));
        objRoot.addChild(keyNavigatorBehavior);

        objRoot.compile();

        return objRoot;
    }

    public SimpleKeyNavigatorBehavior() {
        setLayout(new BorderLayout());
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        this.add(BorderLayout.CENTER, canvas);
        SimpleUniverse universe = new SimpleUniverse(canvas);
        BranchGroup scene = createSceneGraph(universe);
        universe.addBranchGraph(scene);
    }

    public static void main(String[] args) {
        new MainFrame(new SimpleKeyNavigatorBehavior(), 256, 256);
    }
}
