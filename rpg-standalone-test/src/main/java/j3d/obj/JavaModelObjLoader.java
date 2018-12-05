package j3d.obj;

import billy.rpg.common.util.AssetsUtil;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

/**
 * 鼠标控制
 */
public class JavaModelObjLoader extends Applet {
    private static final long serialVersionUID = 5583654361046824223L;

    public BranchGroup createSceneGraph() {
        // 创建场景图分支
        BranchGroup group = new BranchGroup();

        // 几何变换组节点
        TransformGroup transGroup = new TransformGroup();
        // 几何变换
        Transform3D trans3d = new Transform3D();
        // 缩放变换
        trans3d.setScale(0.8);
        // 将几何变换节点对象添加到节点组
        transGroup.setTransform(trans3d);
        // 将几何变化组添加到场景
        group.addChild(transGroup);

        // 球体作用范围边界对象
        BoundingSphere bound = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                100.0);
        Color3f bgColor = new Color3f(0.05f, 0.05f, 0.2f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bound);
        group.addChild(bg);

        // 设置光源
        Color3f lightColor = new Color3f(1.0f, 1.0f, 0.9f);
        Vector3f lightDirection = new Vector3f(4.0f, -7.0f, -12.0f);
        // 设置定向光的颜色和影响范围
        DirectionalLight light = new DirectionalLight(lightColor,
                lightDirection);
        light.setInfluencingBounds(bound);
        // 将光源添加到场景
        group.addChild(light);

        // 几何变换组节点 - 加载外部模型
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // 加载Obj格式的模型文件
//        String resourcePath = AssetsUtil.getResourcePath("/obj/female_model.obj");
//        String resourcePath = AssetsUtil.getResourcePath("/obj/Teleport.obj");
        String resourcePath = AssetsUtil.getResourcePath("/obj/comic_scene.obj");
        objTrans.addChild(new ObjFileReader(resourcePath));
        // 将模型添加到变换组节点
        transGroup.addChild(objTrans);

        // 设置模型行为,鼠标触控360度空间旋转
        KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(objTrans);
        objTrans.addChild(keyNavigatorBehavior);

        MouseRotate behavior = new MouseRotate();
        behavior.setTransformGroup(objTrans);
        objTrans.addChild(behavior);
        behavior.setSchedulingBounds(bound);
        MouseTranslate tr = new MouseTranslate();
        tr.setTransformGroup(objTrans);
        objTrans.addChild(tr);

        tr.setSchedulingBounds(bound);
        MouseWheelZoom tr1 = new MouseWheelZoom();
        tr1.setTransformGroup(objTrans);
        objTrans.addChild(tr1);
        tr1.setSchedulingBounds(bound);

        group.compile();
        return group;
    }

    public JavaModelObjLoader() {
        setLayout(new BorderLayout());
        // 创建3D场景绘制画布Canvas3D对象
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add("Center", canvas);
        BranchGroup scene = createSceneGraph();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
    }

    public static void main(String[] args) {
        new MainFrame(new JavaModelObjLoader(), 360, 360);
    }
}
