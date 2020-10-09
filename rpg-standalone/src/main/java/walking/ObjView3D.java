package walking;
// ObjView3D.java
// Andrew Davison, September 2006, ad@fivedots.coe.psu.ac.th

/* The world consists of a checkboard floor, 
   with a red center square, and labelled XZ axes.

   The floor is populated with models and ground shapes. The
   ground shape images rotate around their y-axis to face
   the viewer.

   There are three choices for the background
     - a textured-wrapped sphere
     - a skybox
     - a box around the scene covered with Teragen-generated
       textures

   The user moves over the floor using key commands.
*/

import javax.swing.*;
import java.awt.*;


public class ObjView3D extends JFrame
{
  public ObjView3D() 
  {
    super("ObjView3D");
    Container c = getContentPane();
    c.setLayout( new BorderLayout() );
    WrapObjView3D w3d = new WrapObjView3D();     // panel holding the 3D canvas
    c.add(w3d, BorderLayout.CENTER);

    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    pack();
//    setResizable(false);    // fixed size display
    setVisible(true);
  } // end of ObjView3D()


// -----------------------------------------

  public static void main(String[] args)
  {  new ObjView3D(); }

} // end of ObjView3D class
