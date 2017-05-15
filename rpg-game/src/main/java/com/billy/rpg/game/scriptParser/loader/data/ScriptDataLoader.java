package com.billy.rpg.game.scriptParser.loader.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;

import com.billy.rpg.game.scriptParser.bean.LoaderBean;
import com.billy.rpg.game.scriptParser.cmd.CmdBase;
import com.billy.rpg.game.scriptParser.cmd.executor.CmdExecutor;
import com.billy.rpg.game.scriptParser.item.ScriptItem;

/**
 * to load script(s)
 * 
 *
 */
public class ScriptDataLoader implements IDataLoader {
    private static final Logger LOG = Logger.getLogger(ScriptDataLoader.class);

    private List<String> loadScripts0() throws IOException {
        Enumeration<URL> urls = null;
        List<String> list = new ArrayList<>();
        urls = Thread.currentThread().getContextClassLoader().getResources("");
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filepath = url.getPath();
                String packagename = filepath + "script/";
                File file = new File(packagename);
                if (file == null || !file.exists()) {
                    continue;
                }
                File[] listFiles = file.listFiles(filterScripts());

                for (File f : listFiles) {
                    String tmp = f.getPath();
                    LOG.debug("scripts loaded:" + f.getPath());
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
     * 过滤指定的脚本文件
     *
     * @return
     */
    private static FileFilter filterScripts() {
        FileFilter filter = new FileFilter() {            // anonymous class
            @Override
            public boolean accept(File pathname) {
                //  we want the file whose extension is 's'.  [1: file, 2: '.s' ]
                if (pathname.isFile() && pathname.getPath().endsWith(".s")) {
                    return true;
                }
                return false;
            }
        };

        return filter;
    }


    public List<LoaderBean> load() throws Exception {
        List<String> scripts = loadScripts0();
        if (scripts == null || scripts.isEmpty()) {
            LOG.warn("No scripts found. System exit.");
            System.exit(0);
        }

        String lineData = null;
        List<LoaderBean> scriptItemList = new ArrayList<LoaderBean>();
        ScriptItem scriptItem = null;
        File file = null;
        Reader in = null;
        BufferedReader br = null;

        for (String script : scripts) {
            file = new File(script);
            in = new FileReader(file);
            br = new BufferedReader(in);
            scriptItem = new ScriptItem();
            lineData = br.readLine();

            List<CmdBase> cmdList = new ArrayList<>();
            int lineNumber = 1;
            while (lineData != null) {
                CmdBase tmp = CmdExecutor.processLine(lineData);
                if (tmp != null) {
                    tmp.setLineNo(lineNumber);
                    cmdList.add(tmp);
                }

                lineData = br.readLine();
                lineNumber++;
            }

            scriptItem.init(cmdList);
            scriptItemList.add(scriptItem);

            in.close();
            br.close();
        }

        return  scriptItemList;
    }
    
    
}
