package j3d.obj;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;

import javax.media.j3d.BranchGroup;

public class ObjFileReader extends BranchGroup {

    private double creaseAngle = 60.0;

    /**
     *
     * 读取ObjModel文件
     *
     * @param filePath obj文件路径
     */
    public ObjFileReader(String filePath) {
        BranchGroup branchGroup = new BranchGroup();
        int flags = ObjectFile.RESIZE /*| ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY */;
        ObjectFile objFile = new ObjectFile(flags, (float)(creaseAngle*Math.PI)/180);
        Scene scenen = null;
        try {
            scenen = objFile.load(filePath);
        } catch (Exception e) {
            System.err.println("OBJ模型加载失败" + e.getMessage());
            e.printStackTrace();
        }
        branchGroup.addChild(scenen.getSceneGroup());
        this.addChild(branchGroup);
    }
}
