package com.billy.rpg.resource.monster;

import java.awt.*;
import java.util.Map;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-11 10:22
 */
public class MonsterMetaData {
    Map<Integer, Image> monsterMap;


    public Map<Integer, Image> getMonsterMap() {
        return monsterMap;
    }

    public void setMonsterMap(Map<Integer, Image> monsterMap) {
        this.monsterMap = monsterMap;
    }
}
