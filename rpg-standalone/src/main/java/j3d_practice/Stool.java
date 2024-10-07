package j3d_practice;

/**
 * @author lei.liu
 * @since 2018-12-05 17:33:32
 */

import com.sun.j3d.utils.geometry.Box;

import javax.media.j3d.Appearance;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

class Stool extends TransformGroup{
    Stool(Appearance app){
        TransformGroup trans = new TransformGroup();
        Transform3D y = new Transform3D();
        y.rotZ(Math.PI /8);
        trans.setTransform(y);
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        TransformGroup g1 = new TransformGroup();
        g1.addChild(new Box(0.4f, 0.3f, 0.05f, Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS, app));

        Transform3D t = new Transform3D();
        // t.setTranslation(new Vector3f(-0.f, 0.26f, -0.35f));
        t.setTranslation(new Vector3f(-0.3f, 0.2f, -0.3f));
        TransformGroup g2 = new TransformGroup(t);
        g2.addChild(new Box(0.04f, 0.04f, 0.3f, Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS, app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(-0.3f, -0.2f, -0.3f));
        TransformGroup g3 = new TransformGroup(t);
        g3.addChild(new Box(0.04f, 0.04f, 0.3f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,  app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(0.3f, 0.2f, -0.3f));

        TransformGroup g4 = new TransformGroup(t);
        g4.addChild(new Box(0.04f, 0.04f, 0.3f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,  app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(0.3f, -0.2f, -0.3f));

        TransformGroup g5 = new TransformGroup(t);
        g5.addChild(new Box(0.04f, 0.04f, 0.3f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,  app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(-0.3f, 0.0f, -0.3f));

        TransformGroup g6 = new TransformGroup(t);
        g6.addChild(new Box(0.03f, 0.2f, 0.03f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,  app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(0.3f, 0.0f, -0.3f));

        TransformGroup g7 = new TransformGroup(t);
        g7.addChild(new Box(0.03f, 0.2f, 0.03f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,  app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(0.0f, 0.2f, -0.2f));

        TransformGroup g8 = new TransformGroup(t);
        g8.addChild(new Box(0.3f, 0.03f, 0.03f, Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS, app));

        t = new Transform3D();
        t.setTranslation(new Vector3f(0.0f, -0.2f, -0.2f));

        TransformGroup g9 = new TransformGroup(t);
        g9.addChild(new Box(0.3f, 0.03f, 0.03f,Box.GENERATE_NORMALS|Box.GENERATE_TEXTURE_COORDS,  app));

        trans.addChild(g1);
        trans.addChild(g2);
        trans.addChild(g3);
        trans.addChild(g4);
        trans.addChild(g5);
        trans.addChild(g6);
        trans.addChild(g7);
        trans.addChild(g8);
        trans.addChild(g9);
        this.addChild(trans);
    }
}

