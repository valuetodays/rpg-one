package j3d_practice;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Hello {
    public Hello() {
        // 创建一个虚拟空间
        SimpleUniverse universe = new SimpleUniverse();
        // 创建一个用来包含对象的数据结构
        BranchGroup group = new BranchGroup();

        // 创建一个球并把它加到group中
        Sphere sphere = new Sphere(0.5f); // 小球半径为0.5米
        group.addChild(sphere);

        // 创建一束红光
        Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
        // 设置光线的作用范围
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        // 设置光线的方向
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        // 指定颜色和方向，产生单向光源
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(boundingSphere);
        group.addChild(light1);

        // 安放观察点
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(group);
    }

    public static void main(String[] args) {
        new Hello();
    }
}
