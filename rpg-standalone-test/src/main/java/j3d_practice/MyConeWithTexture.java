package j3d_practice;

import billy.rpg.common.util.AssetsUtil;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Text2D;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.applet.Applet;
import java.awt.*;

/**
 * 简单物体 - 圆锥（带纹理）
 */
public class MyConeWithTexture extends Applet {
    private static final long serialVersionUID = -8665221788560356816L;

    public MyConeWithTexture() {
        setLayout(new BorderLayout());
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        this.add(BorderLayout.CENTER, canvas);
        BranchGroup scene = createSceneGraph();
        scene.compile();
        SimpleUniverse universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(scene);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        TransformGroup objTrans = new TransformGroup(); // 生成坐标系
        objRoot.addChild(objTrans); // 将坐标系添加到根结点上
        // 设置场景的有效范围
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        // 创建背景颜色
        Color3f bgColor = new Color3f(0.0f, 0.0f, 0.0f);
        Background bg = new Background(bgColor);
        objRoot.addChild(bg); // 添加背景到场景中
        // 添加平行光
        Color3f directionalColor = new Color3f(2.f, 0.5f, 1.f);
        Vector3f vector3f = new Vector3f(0.f, 0.f, -1.0f);
        // 创建平行光
        DirectionalLight directionalLight = new DirectionalLight(directionalColor, vector3f);
        directionalLight.setInfluencingBounds(boundingSphere);
        objRoot.addChild(directionalLight);


        // 设置纹理图片 需要配置合Cone.GENERATE_TEXTURE_COORDS
        Appearance appearance = new Appearance();
        TextureLoader textureLoader = new TextureLoader(AssetsUtil.getResourcePath("/texture/brick02.png"), this);
        Texture2D texture2D = (Texture2D)textureLoader.getTexture();
        texture2D.setBoundaryModeS(Texture.WRAP);
        appearance.setTexture(texture2D);

        //////////////////////
        // 生成圆锥体
        Cone cone = new Cone(0.5f, 1.0f,  Cone.GENERATE_NORMALS | Cone.GENERATE_TEXTURE_COORDS, appearance);
        objRoot.addChild(cone);


        Shape3D text2d = new Text2D("清华大学", new Color3f(1f, 1f, 1f), "宋体", 30, Font.BOLD);
        objRoot.addChild(text2d);
        return objRoot;
    }

    public static void main(String[] args) {
        new MainFrame(new MyConeWithTexture(), 400, 300);
    }
}
