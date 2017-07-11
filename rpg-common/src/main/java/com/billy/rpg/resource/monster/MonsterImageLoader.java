package com.billy.rpg.resource.monster;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-11 10:21
 */
public class MonsterImageLoader {
    private MonsterMetaData monsterMetaData;


    public MonsterMetaData load() {
        monsterMetaData = new MonsterMetaData();
        Map<Integer, Image> monsterMap = new HashMap<>();
        //
        final String monsterPath = "/Images/character/monster/";
        URL resource = MonsterImageLoader.class.getResource(monsterPath);
        try {
            File file = new File(resource.getPath());
            // little
            File[] files = file.listFiles();
            if (ArrayUtils.isEmpty(files)) {
                throw new RuntimeException("没有找到monster数据");
            }

            for (File f : files) {
                final String monsterFileName = f.getName();
                InputStream is = MonsterImageLoader.class.getResourceAsStream(monsterPath + monsterFileName);
                Image img = ImageIO.read(is);
                IOUtils.closeQuietly(is);
                monsterMap.put(Integer.valueOf(monsterFileName.split("-")[0]), img);
            }

            monsterMetaData.setMonsterMap(monsterMap);
        } catch (IOException e) {
            e.printStackTrace();
            monsterMetaData = null;
        }

        if (monsterMetaData == null) {
            throw new RuntimeException("加载monster出错");
        }

        return monsterMetaData;
    }

    public MonsterMetaData getMonsterMetaData() {
        return monsterMetaData;
    }
}
