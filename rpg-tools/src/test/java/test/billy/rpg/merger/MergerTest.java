package test.billy.rpg.merger;

import billy.rpg.merger.Merger;
import billy.rpg.merger.Splitter;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-08-31 14:55
 */
public class MergerTest {
    private static final Logger LOG = Logger.getLogger(MergerTest.class);

    @Test
    public void merge() throws Exception {
        List<String> srcList = new ArrayList<>();
        srcList.add("z:/merge/1.rol");
//        srcList.add("z:/merge/2.rol");
        srcList.add("z:/merge/3.rol");
        srcList.add("z:/merge/51.rol");
        srcList.add("z:/merge/1.skl");
        //srcList.add("z:/merge/1.s");

        Merger m = new Merger();
        m.merge("z:/merge/dat.lib", srcList);
    }

    @Test
    public void loadLib() throws Exception {
        String libPath = "z:/merge/dat.lib";
        Splitter splitter = new Splitter(libPath);
        String bytes1_1 = splitter.getResource(1, 1);
        LOG.debug("bytes1_1 = " + bytes1_1);
        splitter.close();
    }

}