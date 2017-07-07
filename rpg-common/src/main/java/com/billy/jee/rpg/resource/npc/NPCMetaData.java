package com.billy.jee.rpg.resource.npc;

import java.awt.*;
import java.util.List;

/**
 *
 * @author liulei
 * @since 2017-05-16 19:14
 */
public class NPCMetaData {
    private List<Image> littleImages;
    private List<Image> fullImages;
    private List<String> littleNames;
    private List<String> fullNames;

    public List<Image> getLittleImages() {
        return littleImages;
    }

    public void setLittleImages(List<Image> littleImages) {
        this.littleImages = littleImages;
    }

    public List<String> getLittleNames() {
        return littleNames;
    }

    public void setLittleNames(List<String> littleNames) {
        this.littleNames = littleNames;
    }

    public List<Image> getFullImages() {
        return fullImages;
    }

    public void setFullImages(List<Image> fullImages) {
        this.fullImages = fullImages;
    }

    public List<String> getFullNames() {
        return fullNames;
    }

    public void setFullNames(List<String> fullNames) {
        this.fullNames = fullNames;
    }
}
