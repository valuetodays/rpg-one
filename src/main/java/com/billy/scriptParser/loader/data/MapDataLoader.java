package com.billy.scriptParser.loader.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;

import com.billy.constants.MapConstant;
import com.billy.scriptParser.bean.LoaderBean;
import com.billy.scriptParser.bean.MapDataLoaderBean;


/**
 * to load map
 *
 * @author liulei-frx
 * 
 * @since 2016-11-30 09:13:28
 */
public class MapDataLoader implements IDataLoader {
    
    private static final Logger LOG = Logger.getLogger(MapDataLoader.class);
    
    @Override
    public List<LoaderBean> load() throws Exception {
        List<String> mapFilepaths = load0();
        if (mapFilepaths == null) {
            LOG.warn("No maps found. System exit.");
//            System.exit(0);
        }
        
        LOG.debug(mapFilepaths);
        
        String lineData = null;
        List<LoaderBean> maps = new ArrayList<LoaderBean>();
        MapDataLoaderBean mapBean = null;
        File file = null;
        Reader in = null;
        BufferedReader br = null;

        List<String> mapData = new ArrayList<>();
        for (String map : mapFilepaths) {
            file = new File(map);
            in = new FileReader(file);
            br = new BufferedReader(in);
            mapBean = new MapDataLoaderBean();
            lineData = br.readLine();

            mapData.clear();
            
            while (lineData != null) {
                mapData.add(lineData);
                lineData = br.readLine();
            }

            in.close();
            br.close();
            mapBean.parse(file.getName(), mapData);

            maps.add(mapBean);
        } // end of for 
        
        return Collections.unmodifiableList(maps);
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
                String packagename = filepath + "map/";
                File file = new File(packagename);
                if (file == null || !file.exists()) {
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
     * 过滤指定的地图文件
     *
     * @return
     */
    private static FileFilter filterMap() {
        FileFilter filter = new FileFilter() {            // anonymous class
            @Override
            public boolean accept(File pathname) {
                //  we want the file whose extension is 's'.  [1: file, 2: '.map' ]
                if (pathname.isFile() && pathname.getPath().endsWith(MapConstant.MAP_EXT)) {
                    return true;
                }
                return false;
            }
        };

        return filter;
    }
    

}
