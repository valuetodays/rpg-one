package j3d.threeds;

import com.microcrowd.loader.java3d.max3ds.Loader3DS;
import com.sun.j3d.loaders.Scene;

import javax.media.j3d.BranchGroup;

/**
 * http://www.cnblogs.com/dennisit/archive/2013/05/07/3065479.html
 *
 * 读取.3ds文件
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-05 18:33:45
 */
public class ThreeDSFileReader  extends BranchGroup {

    /**
     *
     * 读取.3ds文件
     *
     * @param filePath    3ds文件路径
     *
     */
    public ThreeDSFileReader(String filePath){
        BranchGroup branchGroup = new BranchGroup();
        Loader3DS l3ds = new Loader3DS();
        Scene scene = null;
        try {
            scene = l3ds.load(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("3DS模型加载失败" + e.getMessage());
        }
        branchGroup.addChild(scene.getSceneGroup());
        this.addChild(branchGroup);

    }

}