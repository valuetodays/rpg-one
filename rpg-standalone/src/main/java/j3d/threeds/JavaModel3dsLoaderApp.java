package j3d.threeds;

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
 * http://www.cnblogs.com/dennisit/archive/2013/05/07/3065479.html
 *
 * @author lei.liu
 * @since 2018-12-05 18:36:38
 */
public class JavaModel3dsLoaderApp extends Applet {

    private static final long serialVersionUID = -7138942246695392743L;

    public BranchGroup createSceneGraph(){
        // 创建场景图分支
        BranchGroup group = new BranchGroup();

        // 几何变换组节点
        TransformGroup transGroup = new TransformGroup();
        // 几何变换
        Transform3D trans3d = new Transform3D();
        // 缩放变换
        trans3d.setScale(0.7);
        //将几何变换节点对象添加到节点组
        transGroup.setTransform(trans3d);
        //将几何变化组添加到场景
        group.addChild(transGroup);

        // 球体作用范围边界对象
        BoundingSphere bound= new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
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

        //几何变换组节点 - 加载外部模型
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //加载Obj格式的模型文件
        String resourcePath = AssetsUtil.getResourcePath("/3ds/bounce.3ds");
        objTrans.addChild(new ThreeDSFileReader(resourcePath));
        //将模型添加到变换组节点
        transGroup.addChild(objTrans);

        //设置几何变化,绕Y轴中心旋转
        Transform3D yAxis = new Transform3D();
        Alpha rotationAlpha = new Alpha(-1,Alpha.INCREASING_ENABLE,
                0,0,
                6000,0,0,
                0,0,0);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objTrans,yAxis,0.0f,(float)Math.PI*2.0f);
        rotator.setSchedulingBounds(bound);
        objTrans.addChild(rotator);

        group.compile();

        return group;
    }


    public  JavaModel3dsLoaderApp(){

        setLayout(new BorderLayout());
        // 创建3D场景绘制画布Canvas3D对象
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center",canvas);
        BranchGroup scene = createSceneGraph();

        SimpleUniverse universe = new SimpleUniverse(canvas);
        //设定用户视角
        Point3d userPosition = new Point3d(0,18,18);
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
        new MainFrame(new JavaModel3dsLoaderApp(), 340,340);
    }
}