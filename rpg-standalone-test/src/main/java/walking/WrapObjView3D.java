package walking;
// WrapObjView3D.java
// Andrew Davison, September 2006, ad@fivedots.coe.psu.ac.th

/* The world consists of a checkboard floor, 
   with a red center square, and labelled XZ axes,
   created by CheckerFloor with the help of the ColouredTiles
   class.

   The floor is populated with models and ground shapes. The
   models are loaded by the ModelLoader class, the ground shapes
   by GroundShape.

   The user moves over the floor using key commands processed by
   KeyBehavior.

   The lighting, background, and initial user positioning are done 
   in this class. 

   Three different background techniques are illustrated:
    1) using a textured-wrapped sphere in the Background node(
       (see addBackground())
    2) a texture applied individually to each face of a cube
       in the Background node (i.e. a skybox)
       (see addSkyBox())
    3) six different textures applied to quads forming a
       box around the scene
       (see addSceneBox())

   The calls to these methods are located in createSceneGraph(). Only
   uncomment one of them.
*/


import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;



public class WrapObjView3D extends JPanel
// Holds the 3D canvas where the loaded image is displayed
{
  private static final int PWIDTH = 512;   // size of panel
  private static final int PHEIGHT = 512; 

  private static final int BOUNDSIZE = 100;  // larger than world

  // info used to position initial viewpoint
  private final static double Z_START = 9.0;

  private static final String SKY_DIR = "walking/skyBox/";
               // location of sky textures
  private final static double DY = 0.01;


  private SimpleUniverse su;
  private BranchGroup sceneBG;
  private BoundingSphere bounds;   // for environment nodes


  public WrapObjView3D()
  // A panel holding a 3D canvas
  {
    setLayout( new BorderLayout() );
    setOpaque( false );
    setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

    GraphicsConfiguration config =
					SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas3D = new Canvas3D(config);
    add("Center", canvas3D);
    canvas3D.setFocusable(true);     // give focus to the canvas 
    canvas3D.requestFocus();

    su = new SimpleUniverse(canvas3D);

    // depth-sort transparent objects on a per-geometry basis
    View view = su.getViewer().getView();
    view.setTransparencySortingPolicy(View.TRANSPARENCY_SORT_GEOMETRY);

    createSceneGraph();
    createUserControls();
    
    su.addBranchGraph( sceneBG );
  } // end of WrapObjView3D()



  private void createSceneGraph()
  // initilise the scene
  { 
    sceneBG = new BranchGroup();
    bounds = new BoundingSphere(new Point3d(0,0,0), BOUNDSIZE);  

    lightScene();         // add the lights

    // three background methods: choose one 
    // addBackground("lava.jpg"); // or "stars.jpg", "clouds.jpg"
    // addSkyBox("stars.jpg");      // skybox version 1
    addSceneBox(BOUNDSIZE/2);    // version 2

    sceneBG.addChild( new CheckerFloor().getBG() );  // add the floor
    addModels();
    addGroundCover();

    sceneBG.compile();   // fix the scene
  } // end of createSceneGraph()


  private void lightScene()
  /* One ambient light, 2 directional lights */
  {
    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

    // Set up the ambient light
    AmbientLight ambientLightNode = new AmbientLight(white);
    ambientLightNode.setInfluencingBounds(bounds);
    sceneBG.addChild(ambientLightNode);

    // Set up the directional lights
    Vector3f light1Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);
       // left, down, backwards 
    Vector3f light2Direction  = new Vector3f(1.0f, -1.0f, 1.0f);
       // right, down, forwards

    DirectionalLight light1 = 
            new DirectionalLight(white, light1Direction);
    light1.setInfluencingBounds(bounds);
    sceneBG.addChild(light1);

    DirectionalLight light2 = 
        new DirectionalLight(white, light2Direction);
    light2.setInfluencingBounds(bounds);
    sceneBG.addChild(light2);
  }  // end of lightScene()



  private void addModels()
  /* The ModelLoader getModel() method expects a OBJ filename, for
     files in the Models/ subdirectory, and an optional y-axis 
     translation. The method returns a TransformGroup which can
     be used with other TGs to further position, rotate, or scale
     the model.

     List of models with their optional y-axis offsets:
       penguin.obj, sword.obj 1, humanoid.obj 0.8
       hand.obj 1, barbell.obj 0.25, longBox.obj, cone.obj 0.4
       heli.obj 0, matCube.obj 0.5, cubeSphere.obj 0.9
  */
  {
    ModelLoader ml = new ModelLoader();
    Transform3D t3d = new Transform3D();

    t3d.setIdentity();   // resets t3d  (just to be safe)
    t3d.setTranslation( new Vector3d(0,0,-7));   // move
    t3d.setScale(2.5);   // enlarge
    t3d.setRotation( new AxisAngle4d(0,1,0, Math.toRadians(90)) ); 
              // rotate 90 degrees anticlockwise around y-axis
    TransformGroup tg1 = new TransformGroup(t3d);
    tg1.addChild( ml.getModel("humanoid.obj", 0.8) );
    sceneBG.addChild(tg1);

    t3d.set( new Vector3d(-1,0,2));   // move, and resets other parts of t3d
    t3d.setScale(0.5);   // shrink
    TransformGroup tg2 = new TransformGroup(t3d);
    tg2.addChild( ml.getModel("penguin.obj") );
    sceneBG.addChild(tg2);

    t3d.set( new Vector3d(7,0,-7)); 
    TransformGroup tg3 = new TransformGroup(t3d);
    tg3.addChild( ml.getModel("barbell.obj", 0.25) );
    sceneBG.addChild(tg3);

    t3d.set( new Vector3d(2,0,5)); 
    t3d.setScale( new Vector3d(4,1,0.25));  
          // stretch along x-axis, shrink along z-axis
    t3d.setRotation( new AxisAngle4d(1,0,0, Math.toRadians(-45)) ); 
              // rotate 45 degrees anticlockwise around x-axis
    TransformGroup tg4 = new TransformGroup(t3d);
    tg4.addChild( ml.getModel("longBox.obj") );
    sceneBG.addChild(tg4);

    t3d.set( new Vector3d(1,2,0));   // up into the air
    TransformGroup tg5 = new TransformGroup(t3d);
    tg5.addChild( ml.getModel("heli.obj") );
    sceneBG.addChild(tg5);

    t3d.set( new Vector3d(-3,2,-5));   // up into the air
    TransformGroup tg6 = new TransformGroup(t3d);
    tg6.addChild( ml.getModel("colorCube.obj"));
    sceneBG.addChild(tg6);

  }  // end of addModels()


  private void addGroundCover()
  /* GroundShape expects a GIF filename, located in the images/
     subdirectory and a scale factor. The resulting shape is
     located at (0,0) on the (x, z) plain, and can be adjusted
     with an additional TG.
  */
  {
    Transform3D t3d = new Transform3D();

    t3d.set( new Vector3d(4,0,0)); 
    TransformGroup tg1 = new TransformGroup(t3d);
    tg1.addChild( new GroundShape("tree1.gif", 3) );
    sceneBG.addChild(tg1);

    t3d.set( new Vector3d(-3,0,0));
    TransformGroup tg2 = new TransformGroup(t3d);
    tg2.addChild( new GroundShape("tree2.gif", 2) );
    sceneBG.addChild(tg2);

    t3d.set( new Vector3d(2,0,-6));
    TransformGroup tg3 = new TransformGroup(t3d);
    tg3.addChild( new GroundShape("tree4.gif", 3) );
    sceneBG.addChild(tg3);

    t3d.set( new Vector3d(-1,0,-4));
    TransformGroup tg4 = new TransformGroup(t3d);
    tg4.addChild( new GroundShape("cactus.gif") );
    sceneBG.addChild(tg4);
  }  // end of addGroundCover()



  private void createUserControls()
  /*  Attach the keyboard behavior for moving around the scene,
      and set the initial viewpoint.
  */
  { 
    ViewingPlatform vp = su.getViewingPlatform();

    // position viewpoint
    TransformGroup targetTG = vp.getViewPlatformTransform();
    Transform3D t3d = new Transform3D();
    targetTG.getTransform(t3d);
    t3d.setTranslation( new Vector3d(0,1,Z_START));
    targetTG.setTransform(t3d);

    // set up keyboard controls to move the viewpoint
    KeyBehavior keyBeh = new KeyBehavior();
    keyBeh.setSchedulingBounds(bounds);
    vp.setViewPlatformBehavior(keyBeh);
  } // end of createUserControls()


  // ------------------ background methods -------------------------

  private void addBackground(String fnm)
  /* Create a spherical background. The texture for the 
     sphere comes from fnm. */
  {
    Texture2D tex = loadTexture(SKY_DIR + fnm);

    Sphere sphere = new Sphere(1.0f,
			      Sphere.GENERATE_NORMALS_INWARD |
				  Sphere.GENERATE_TEXTURE_COORDS, 8);   // default = 15 (4, 8)
    Appearance backApp = sphere.getAppearance();
	backApp.setTexture( tex );

	BranchGroup backBG = new BranchGroup();
    backBG.addChild(sphere);

    Background bg = new Background();
    bg.setApplicationBounds(bounds);
    bg.setGeometry(backBG);

    sceneBG.addChild(bg);
  }  // end of addBackground()


  private Texture2D loadTexture(String fn)
  // load image from file fn as a texture
  {
    TextureLoader texLoader = new TextureLoader(fn, null);
    Texture2D texture = (Texture2D) texLoader.getTexture();
    if (texture == null)
      System.err.println("Cannot load texture from " + fn);
    else {
      System.err.println("Loaded texture from " + fn);
      texture.setEnable(true);
    }
    return texture;
  }  // end of loadTexture()


  // ------------------------ skybox version 1 ---------------------
  // applies texture to the faces of a cube in a Background node

  private void addSkyBox(String fnm)
  {
    com.sun.j3d.utils.geometry.Box texCube =
         new com.sun.j3d.utils.geometry.Box(1.0f, 1.0f, 1.0f, 
                    Primitive.GENERATE_TEXTURE_COORDS, 
                    new Appearance());

    Texture2D tex = loadTexture(SKY_DIR + fnm);

    setFaceTexture(texCube, com.sun.j3d.utils.geometry.Box.FRONT, tex);
    setFaceTexture(texCube, com.sun.j3d.utils.geometry.Box.LEFT, tex);
    setFaceTexture(texCube, com.sun.j3d.utils.geometry.Box.RIGHT, tex);
    setFaceTexture(texCube, com.sun.j3d.utils.geometry.Box.BACK, tex);
    setFaceTexture(texCube, com.sun.j3d.utils.geometry.Box.TOP, tex);
    setFaceTexture(texCube, com.sun.j3d.utils.geometry.Box.BOTTOM, tex);

	BranchGroup backBG = new BranchGroup();
    backBG.addChild(texCube);

    Background bg = new Background();
    bg.setApplicationBounds(bounds);
    bg.setGeometry(backBG);

    sceneBG.addChild(bg);
  }  // end of addSkyBox()


  private void setFaceTexture(com.sun.j3d.utils.geometry.Box texCube,
                                   int faceID, Texture2D tex)
  {
    Appearance app = new Appearance();

    // make texture appear on back side of face
    PolygonAttributes pa = new PolygonAttributes();
    pa.setCullFace( PolygonAttributes.CULL_FRONT);
    app.setPolygonAttributes( pa );

    if (tex != null)
      app.setTexture(tex);

    texCube.getShape(faceID).setAppearance(app);
  }  // end of setFaceTexture()


  // ------------------------ skybox version 2 ---------------------


  private void addSceneBox(double wallLen)
  /* applies different textures to the faces six quads forming a
     box around the scene, of dimensions wallLen
  */
  { 
    // the eight corner points
    /* base starting from front/left then anti-clockwise, at a small
       offset below floor, DY */
    Point3d p1 = new Point3d(-wallLen/2, -DY, wallLen/2); 
    Point3d p2 = new Point3d(wallLen/2, -DY, wallLen/2);
    Point3d p3 = new Point3d(wallLen/2, -DY, -wallLen/2);
    Point3d p4 = new Point3d(-wallLen/2, -DY, -wallLen/2);

    /* top starting from front/left then anti-clockwise, and at height
       wallLen/4 */
    Point3d p5 = new Point3d(-wallLen/2, wallLen/4, wallLen/2);
    Point3d p6 = new Point3d(wallLen/2, wallLen/4, wallLen/2);
    Point3d p7 = new Point3d(wallLen/2, wallLen/4, -wallLen/2);
    Point3d p8 = new Point3d(-wallLen/2, wallLen/4, -wallLen/2);

    /* the six textures were created using Terragen */
    // floor
    sceneBG.addChild( new TexPlane(p2, p3, p4, p1, SKY_DIR+"floor.jpg"));

    // front wall
    sceneBG.addChild( new TexPlane(p4, p3, p7, p8, SKY_DIR+"skyFront.jpg"));

    // right wall
    sceneBG.addChild( new TexPlane(p3, p2, p6, p7, SKY_DIR+"skyRight.jpg"));

    // back wall
    sceneBG.addChild( new TexPlane(p2, p1, p5, p6, SKY_DIR+"skyBack.jpg"));

    // left wall
    sceneBG.addChild( new TexPlane(p1, p4, p8, p5, SKY_DIR+"skyLeft.jpg"));

    // ceiling
    sceneBG.addChild( new TexPlane(p5, p8, p7, p6, SKY_DIR+"skyAbove.jpg"));

  } // end of addSceneBox()

} // end of WrapObjView3D class