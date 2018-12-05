package j3d_practice;

import billy.rpg.common.util.AssetsUtil;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.BitSet;

/**
 * https://blog.csdn.net/tingary/article/details/80486890
 * @author lei.liu@datatist.com
 * @since 2018-12-05 17:32:55
 */

public class SimpleWithTexture extends Applet implements ActionListener {
    private static final long serialVersionUID = -821384797468203413L;

    public static void main(String[] args) {
            new MainFrame(new SimpleWithTexture(), 400, 400);
        }

        String filename;
        BitSet mask;
        Switch sw = null;
        private SimpleUniverse u;
        private Button b;

        public void init() {

            setLayout(new BorderLayout());
            Panel panel = new Panel();
            panel.setLayout(new GridLayout(1, 5));
            add(panel, BorderLayout.SOUTH);
            b = new Button("sphere");
            b.addActionListener(this);
            panel.add(b);
            b = new Button("cyclinder");
            b.addActionListener(this);
            panel.add(b);
            b = new Button("box");
            b.addActionListener(this);
            panel.add(b);
            b = new Button("cone");
            b.addActionListener(this);
            panel.add(b);
            b = new Button("stool");
            b.addActionListener(this);
            panel.add(b);
            GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
            Canvas3D c = new Canvas3D(gc);
            add(c, BorderLayout.CENTER);
            BranchGroup scene = createSceneGraph();
            scene.compile();
            u = new SimpleUniverse(c);
            u.getViewingPlatform().setNominalViewingTransform();
            u.addBranchGraph(scene);
        }

        BranchGroup createSceneGraph() {
            BranchGroup group = new BranchGroup();
            group.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
            Transform3D t = new Transform3D();
            t.setScale(0.5);
            t.setTranslation(new Vector3d(.0f, 0.1f, .0f));
            TransformGroup trans = new TransformGroup(t);
            trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            trans.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
            trans.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
            group.addChild(trans);
            Color3f light1Color = new Color3f(1.0f, 1.0f, 0.9f);
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
            Background bg = new Background(1.0f, 1.0f, 1.0f);
            bg.setApplicationBounds(bounds);
            group.addChild(bg);
            MouseRotate behavior = new MouseRotate();
            behavior.setTransformGroup(trans);
            group.addChild(behavior);
            behavior.setSchedulingBounds(bounds);
            MouseZoom behavior2 = new MouseZoom();
            behavior2.setTransformGroup(trans);
            group.addChild(behavior2);
            behavior2.setSchedulingBounds(bounds);
            filename = AssetsUtil.getResourcePath("/texture/earth.png");
            Appearance app = creatTexture(filename);
            Sphere shp1 = new Sphere(1.f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, 120, app);
            filename = AssetsUtil.getResourcePath("/texture/2.png");
            app = creatTexture(filename);
            Cylinder shp2 = new Cylinder(0.7f, 1.3f, Cylinder.GENERATE_NORMALS | Cylinder.GENERATE_TEXTURE_COORDS, 200, 200,
                    app);
            filename = AssetsUtil.getResourcePath("/texture/3.png");
            app = creatTexture(filename);
            Box shp3 = new Box(.6f, .6f, .6f, Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS, app);
            filename = AssetsUtil.getResourcePath("/texture/4.png");
            app = creatTexture(filename);
            Cone shp4 = new Cone(0.9f, 1.f, Cone.GENERATE_NORMALS | Cone.GENERATE_TEXTURE_COORDS, 200, 200, app);
            filename = AssetsUtil.getResourcePath("/texture/5.png");
            app = creatTexture(filename);
            TransformGroup g1 = new Stool(app);
            t = new Transform3D();
            t.rotX(-Math.PI / 2);
            t.setScale(2.f);
            g1.setTransform(t);
            g1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            g1.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            sw = new Switch();
            sw.setWhichChild(sw.CHILD_MASK);
            sw.setCapability(sw.ALLOW_SWITCH_WRITE);
            sw.addChild(shp1);
            sw.addChild(shp2);
            sw.addChild(shp3);
            sw.addChild(shp4);
            sw.addChild(g1);// g1子节点只能有一个根sw
            mask = new BitSet();
            mask.set(0);
            sw.setChildMask(mask);
            sw.setWhichChild(Switch.CHILD_MASK);
            trans.addChild(sw);
            return group;
        }

        Appearance creatTexture(String image) {
            Appearance app = new Appearance();// 图片放文件第一层目录下
            TextureLoader load = new TextureLoader(image, this);
            ImageComponent2D ima = load.getImage();
            Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, ima.getWidth(), ima.getHeight());
            texture.setImage(0, ima);
            texture.setEnable(true);
            texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
            texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
            app.setTexture(texture);
            return app;
        }

        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            mask = new BitSet();
            if ("sphere".equals(cmd)) {
                mask.set(0);
            } else if ("cyclinder".equals(cmd)) {
                mask.set(1);
            } else if ("box".equals(cmd)) {
                mask.set(2);
            } else if ("cone".equals(cmd)) {
                mask.set(3);
            } else if ("stool".equals(cmd)) {
                mask.set(4);
            }

            sw.setChildMask(mask);
            sw.setWhichChild(Switch.CHILD_MASK);

        }
    }
