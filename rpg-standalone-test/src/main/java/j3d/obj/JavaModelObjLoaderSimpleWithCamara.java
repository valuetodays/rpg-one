package j3d.obj;

import billy.rpg.common.util.AssetsUtil;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import j3d_practice.Axis;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

/**
 * 显示并控制单个模型
 * LEFT/RIGHT 旋转模型
 * HOME/END   缩放模型
 *
 */
public class JavaModelObjLoaderSimpleWithCamara extends JFrame {
    private static final long serialVersionUID = 207092618323821865L;

    private static final Vector3d UP = new Vector3d(0, 1, 0);
    private Transform3D toMove = new Transform3D();

    public class MyBehavior extends Behavior {
        private TransformGroup transformGroup;
        private Transform3D rotation = new Transform3D();
        private double angleY = 0.0;
        private double angleX = 0.0;
        private double scale = 1.0;
        private double x = 0.1001;

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
                    angleY -= 0.1;
//                    rotation.rotY(angleY);
//                    transformGroup.setTransform(rotation);
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                    angleY += 0.1;
//                    rotation.rotY(angleY);
//                    transformGroup.setTransform(rotation);
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_HOME) {
                    scale = rotation.getScale()*0.9d;
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_END) {
                    scale = rotation.getScale()/0.9d;
                } else if (KeyEvent.VK_UP == keyEvent.getKeyCode()) {
                    x += 0.101;
                } else if (KeyEvent.VK_DOWN == keyEvent.getKeyCode()) {
                    x = -0.2;
                } else if (KeyEvent.VK_R == keyEvent.getKeyCode()) {
                    x = 0.2;
                }
                System.out.println("scene: " + scene.getBounds());
                System.out.println("person: " + person.getBounds());
                System.out.println("viewer: " + "("+x+", 3, 5)");
                rotation.rotY(angleY);
//                rotation.setScale(scale);
                Transform3D transform3D = new Transform3D();
                transformGroup.getTransform(transform3D);

                toMove.setTranslation(new Vector3d(x, 0, 0));
                transform3D.mul(toMove);
//                transformGroup.getTransform(transform3D);
                // viewer person, where looking, up direction
        //transform3D.lookAt(new Point3d(x,3,5), new Point3d(1, 3, 5), UP);
//        transform3D.invert();
                transformGroup.setTransform(transform3D);
//                transformGroup.setTransform(rotation);
            }

            this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
        }
    }
    private ObjFileReader scene;
    private ObjFileReader person;

    public BranchGroup createSceneGraph() {
        // 创建场景图分支
        BranchGroup group = new BranchGroup();
        group.addChild(new Axis());
        // 几何变换组节点
        TransformGroup transGroup = new TransformGroup();
        // 几何变换
        Transform3D trans3d = new Transform3D();
        // 缩放变换
        trans3d.setScale(1.0);
//        trans3d.set(new Vector3d(12, -2, -33));
        //将几何变换节点对象添加到节点组
        transGroup.setTransform(trans3d);
        //将几何变化组添加到场景
        group.addChild(transGroup);

        // 球体作用范围边界对象
        BoundingSphere bound = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        /*
        Color3f bgColor = new Color3f(0.3f, 0.03f, 0.08f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bound);
        group.addChild(bg);*/


        // 设置光源
        Color3f lightColor = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
        //设置定向光的颜色和影响范围
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bound);
        //将光源添加到场景
        group.addChild(light);

        //几何变换组节点 - 加载外部模型
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //加载Obj格式的模型文件
        {
            String resourcePath = AssetsUtil.getResourcePath("/obj/comic/comic.obj");
            // !! why 使用F:\\aaa\bbb.obj就能显示出纹理，但使用F:/aaa/bbb.obj就显示不出纹理
//        String resourcePath = "F:\\tmp\\obj\\taibainv\\aaa.obj";
//        String resourcePath = "F:/tmp/obj/taibainv/aaa.obj";
            scene = new ObjFileReader(resourcePath);
//            objTrans.addChild(scene);
        }
        {
            String resourcePath = AssetsUtil.getResourcePath("/obj/female_model/female-model.obj");
            person = new ObjFileReader(resourcePath);
            person.setBounds(new BoundingBox());
            objTrans.addChild(person);
        }
        //将模型添加到变换组节点
        transGroup.addChild(objTrans);

        MyBehavior myBehavior = new MyBehavior(objTrans);
        myBehavior.setSchedulingBounds(new BoundingSphere());
        group.addChild(myBehavior);

        group.compile();

        return group;
    }

    public JavaModelObjLoaderSimpleWithCamara() {
        // 创建3D场景绘制画布Canvas3D对象
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        BranchGroup scene = createSceneGraph();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        viewingPlatform.setNominalViewingTransform();
        {/*
            ViewingPlatform myViewingPlatform = new ViewingPlatform();
            Transform3D transform3D = new Transform3D();
            transform3D.set(new Vector3d(-12, -3, -33));
            TransformGroup transformGroup = new TransformGroup();
            transformGroup.setTransform(transform3D);
            transformGroup.addChild(myViewingPlatform);
            scene.addChild(transformGroup);*/
        }
        universe.addBranchGraph(scene);

        this.add(canvas);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(600, 500));
        this.setLocation(100, 50);
        this.pack();
    }

    public static void main(String[] args) {
        new JavaModelObjLoaderSimpleWithCamara();
    }
}
