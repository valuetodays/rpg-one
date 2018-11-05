package billy.rpg.game.loader;

import billy.rpg.game.command.CmdBase;
import billy.rpg.game.command.EmptyCmd;
import billy.rpg.game.command.parser.CommandParser;
import billy.rpg.game.command.parser.support.JlineCommandParser;
import billy.rpg.game.resource.item.ScriptItem;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * to load script(s)
 * 
 *
 */
public class ScriptDataLoader {
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
                if (!file.exists()) {
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
     */
    private static FileFilter filterScripts() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                //  we want the file whose extension is 's'.  [1: file, 2: '.s' ]
                if (pathname.isFile() && pathname.getPath().endsWith(".s")) {
                    return true;
                }
                return false;
            }
        };
    }


    public List<ScriptItem> load() throws Exception {
        List<String> scripts = loadScripts0();
        if (scripts == null || scripts.isEmpty()) {
            LOG.warn("No scripts found. System exit.");
            throw new RuntimeException("no scripts found.");
        }

        CommandParser cmdParser = new JlineCommandParser();
        String lineData = null;
        List<ScriptItem> scriptItemList = new ArrayList<>();
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
                // 以\结尾就说明该行未结束
                while (lineData.endsWith("\\")) {
                    lineData = lineData.substring(0, lineData.length()-1) + br.readLine();
                    lineNumber++;
                }
                CmdBase tmp = cmdParser.parse(script, lineNumber, lineData);
                if (tmp == null) {
                    throw new RuntimeException("tmp should not be null in ["+script+"("+lineNumber+")]");
                }
                if (!(tmp instanceof EmptyCmd)) {
                    cmdList.add(tmp);
                }

                lineData = br.readLine();
                lineNumber++;
            }

            if (cmdList.isEmpty()) {
                IOUtils.closeQuietly(br);
                IOUtils.closeQuietly(in);
                throw new RuntimeException("no scripts.");
            }
            scriptItem.init(cmdList);
            scriptItemList.add(scriptItem);

            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(in);
        }

        return  scriptItemList;
    }
    
    
}
