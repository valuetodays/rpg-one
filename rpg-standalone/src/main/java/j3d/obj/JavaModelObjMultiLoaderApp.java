package j3d.obj;

import billy.rpg.common.util.AssetsUtil;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

/**
 * 显示多个模型，并自动旋转
 */
public class JavaModelObjMultiLoaderApp extends Applet {
    private static final long serialVersionUID = -2088909559804590789L;

    public BranchGroup createSceneGraph(){
        // 创建场景图分支
        BranchGroup group = new BranchGroup();

        // 几何变换组节点
        TransformGroup transGroup = new TransformGroup();
        // 几何变换
        Transform3D trans3d = new Transform3D();
        // 缩放变换
        trans3d.setScale(0.8);
        //将几何变换节点对象添加到节点组
        transGroup.setTransform(trans3d);
        //将几何变化组添加到场景
        group.addChild(transGroup);

        // 球体作用范围边界对象
        BoundingSphere bound= new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f bgColor = new Color3f(0.05f,0.05f,0.2f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bound);
        group.addChild(bg);


        // 设置光源
        Color3f lightColor = new Color3f(1.0f,1.0f,0.9f);
        Vector3f lightDirection = new Vector3f(4.0f,-7.0f,-12.0f);
        //设置定向光的颜色和影响范围
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(bound);
        //将光源添加到场景
        group.addChild(light);
        String resourcePath = AssetsUtil.getResourcePath("/obj/female_model.obj");
        BranchGroup branchGroup1 = new ObjFileReader(resourcePath);
        BranchGroup branchGroup2 = new ObjFileReader(resourcePath);

        group.addChild(createObject(branchGroup1,bound, -1.3f, 0.0f, 0.0f,3000));
        group.addChild(createObject(branchGroup2,bound, 1.3f, 0.0f, 0.0f ,4000));

        group.compile();

        return group;
    }

    /**
     *
     * Description:  创建模型行为  绕Y轴旋转
     * @param group       模型结点
     * @param bound       模型作用范围边界
     * @param xpos       模型展示左边X轴位置
     * @param ypos       模型展示左边y轴位置
     * @param zpos   模型展示左边z轴位置
     * @param time   模型转动速度
     * @return
     */
    private Group createObject(BranchGroup group,BoundingSphere bound, float xpos, float ypos,float zpos, int time){
        Transform3D trans3d = new Transform3D();
        trans3d.setTranslation(new Vector3f(xpos, ypos, zpos));
        TransformGroup objTrans = new TransformGroup(trans3d);
        TransformGroup sgroup = new TransformGroup();
        sgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sgroup.addChild(group);

        //设置几何变化,绕Y轴中心旋转
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE,
                0,0,
                time,0,0,
                0,0,0);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, sgroup,yAxis,0.0f,(float)Math.PI*2.0f);
        rotator.setSchedulingBounds(bound);
        objTrans.addChild(rotator);
        objTrans.addChild(sgroup);
        return objTrans;
    }

    public JavaModelObjMultiLoaderApp(){

        setLayout(new BorderLayout());
        // 创建3D场景绘制画布Canvas3D对象
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add("Center",canvas);
        BranchGroup scene = createSceneGraph();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        //设定用户视角
        Point3d userPosition = new Point3d(0,3,6);
        initUserPosition(universe,userPosition);
        //universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
    }


    //初始化用户视角
    private void initUserPosition(SimpleUniverse universe, Point3d userPosition){
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup steerTG = vp.getViewPlatformTransform();
        Transform3D t3d = new Transform3D();
        steerTG.getTransform(t3d);
        t3d.lookAt(userPosition, new Point3d(0,0,0), new Vector3d(0,1,0));
        t3d.invert();
        steerTG.setTransform(t3d);
    }

    public static void main(String[] args) {
        new MainFrame(new JavaModelObjMultiLoaderApp(), 350,210);
    }
}
