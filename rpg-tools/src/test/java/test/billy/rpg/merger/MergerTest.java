package test.billy.rpg.merger;

import billy.rpg.merger.LibLoader;
import billy.rpg.merger.Merger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-31 14:55
 */
public class MergerTest {

    @Test
    public void merge() throws Exception {
        List<String> srcList = new ArrayList<>();
        srcList.add("z:/merge/1.role");
//        srcList.add("z:/merge/2.role");
//        srcList.add("z:/merge/3.role");
        srcList.add("z:/merge/51.role");
        srcList.add("z:/merge/1.skl");
        //srcList.add("z:/merge/1.s");

        Merger m = new Merger();
        m.merge("z:/merge/dat.lib", srcList);
    }

    @Test
    public void loadLib() throws Exception {
        String libPath = "z:/merge/dat.lib";
        new LibLoader(libPath);

    }

}