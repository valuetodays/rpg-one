package com.billy.rpg.game.scriptParser.loader.data;

import com.billy.rpg.game.constants.AnimationConstant;
import com.billy.rpg.game.scriptParser.bean.AnimationDataLoaderBean;
import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.resource.animation.AnimationLoader;
import com.billy.rpg.resource.animation.AnimationMetaData;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-10 11:06
 */
public class AnimationDataLoader implements IDataLoader {
    private static final Logger LOG = Logger.getLogger(AnimationDataLoader.class);


    @Override
    public List<LoaderBean> load() throws Exception {
        List<String> aniFilePaths = load0();
        if (aniFilePaths == null) {
            throw new RuntimeException("No ani file(s) found.");
        }

        LOG.debug("read data from " + aniFilePaths);

        List<LoaderBean> anis = new ArrayList<>();
        AnimationDataLoaderBean aniBean = null;

        for (String aniFilePath : aniFilePaths) {
            AnimationMetaData amd = AnimationLoader.load(aniFilePath);
            aniBean = toAniBean(amd);

            anis.add(aniBean);
        } // end of for

        return Collections.unmodifiableList(anis);
    }

    private AnimationDataLoaderBean toAniBean(AnimationMetaData aniMetaData) throws InvocationTargetException,
            IllegalAccessException {
        AnimationDataLoaderBean adlb = new AnimationDataLoaderBean();
        BeanUtils.copyProperties(adlb, aniMetaData);
        return adlb;
    }

    private List<String> load0() {
        Enumeration<URL> urls = null;
        List<String> list = new ArrayList<>();
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filepath = url.getPath();
                String packagename = filepath + "animation/";
                File file = new File(packagename);
                if (!file.exists()) {
                    continue;
                }
                File[] listFiles = file.listFiles(filterMap());

                for (File f : listFiles) {
                    String tmp = f.getPath();
                    LOG.debug("map loaded:" + f.getPath());
                    list.add(tmp);
                }
            }
        }

        if (list.isEmpty()) {
            return null;
        }

        return list;
    }


    /**
     * 过滤指定的ani文件
     */
    private static FileFilter filterMap() {
        FileFilter filter = new FileFilter() {            // anonymous class
            @Override
            public boolean accept(File pathname) {
                //  we want the file [1: '.ani' ]
                if (pathname.getPath().endsWith(AnimationConstant.EXT)) {
                    return true;
                }
                return false;
            }
        };

        return filter;
    }


}
