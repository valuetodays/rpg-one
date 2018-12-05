package j3d_practice;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

/**
 * 键盘左右键控制立方体旋转
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-05 10:53:19
 */
public class SimpleBehavior extends Applet {
    private static final long serialVersionUID = 3733967243639197667L;

    public class MyBehavior extends Behavior {
        private TransformGroup transformGroup;
        private Transform3D rotation = new Transform3D();
        private double angle = 0.0;

        public MyBehavior(TransformGroup transformGroup) {
            this.transformGroup = transformGroup;
        }

        @Override
        public void initialize() {
            this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
        }

        @Override
        public void processStimulus(Enumeration enumeration) {
            AWTEvent[] event = null;
            WakeupCriterion wakeupCriterion = (WakeupCriterion) enumeration.nextElement();
            if (wakeupCriterion instanceof WakeupOnAWTEvent) {
                event = ((WakeupOnAWTEvent) wakeupCriterion).getAWTEvent();
                KeyEvent keyEvent = (KeyEvent) event[0];
                if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                    angle -= 0.1;
                    rotation.rotY(angle);
                    transformGroup.setTransform(rotation);
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                    angle += 0.1;
                    rotation.rotY(angle);
                    transformGroup.setTransform(rotation);
                }
            }
            this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
        }
    }

    public BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        TransformGroup objRotate = new TransformGroup();
        objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objRotate);
        objRotate.addChild(new ColorCube(0.4));
        MyBehavior myBehavior = new MyBehavior(objRotate);
        myBehavior.setSchedulingBounds(new BoundingSphere());
        objRoot.addChild(myBehavior);

        objRoot.compile();

        return objRoot;
    }

    public SimpleBehavior() {
        setLayout(new BorderLayout());
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        this.add(BorderLayout.CENTER, canvas);
        BranchGroup scene = createSceneGraph();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
    }

    public static void main(String[] args) {
        new MainFrame(new SimpleBehavior(), 256, 256);
    }
}
