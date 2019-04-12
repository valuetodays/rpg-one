package billy.rpg.game.core.loader;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.game.core.command.CmdBase;
import billy.rpg.game.core.command.EmptyCmd;
import billy.rpg.game.core.command.parser.CommandParser;
import billy.rpg.game.core.command.parser.support.JlineCommandParser;
import billy.rpg.game.core.item.ScriptItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * to load script(s)
 * 
 *
 */
public class ScriptDataLoader {
    private static final Logger LOG = Logger.getLogger(ScriptDataLoader.class);

    private List<String> loadScripts0() {
        List<String> list = new ArrayList<>();
        String scriptPath = AssetsUtil.getResourcePath("/assets/script");

        File file = new File(scriptPath);
        if (!file.exists()) {
            throw new RuntimeException("no scripts");
        }
        File[] listFiles = file.listFiles(filterScripts());

        for (File f : listFiles) {
            String tmp = f.getPath();
            LOG.debug("scripts loaded:" + f.getPath());
            list.add(tmp);
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
        return pathname -> {
            //  we want the file whose extension is 's'.  [1: file, 2: '.s' ]
            return pathname.isFile() && pathname.getPath().endsWith(".s");
        };
    }

    public List<ScriptItem> load() throws Exception {
        List<String> scripts = loadScripts0();
        if (scripts == null || scripts.isEmpty()) {
            LOG.warn("No scripts found. System exit.");
            throw new RuntimeException("no scripts found.");
        }

        CommandParser cmdParser = new JlineCommandParser();
        List<ScriptItem> scriptItemList = new ArrayList<>();
        ScriptItem scriptItem = null;

        for (String script : scripts) {
            scriptItem = new ScriptItem();

            FileInputStream fileInputStream = new FileInputStream(new File(script));
            LineIterator lineIterator = IOUtils.lineIterator(fileInputStream, "utf-8");
            List<CmdBase> cmdList = new ArrayList<>();
            int lineNumber = 1;
            while (lineIterator.hasNext()) {
                String lineData = lineIterator.next();
                // 以\结尾就说明该行未结束
                while (lineData.endsWith("\\")) {
                    lineData = lineData.substring(0, lineData.length()-1) + lineIterator.next();
                    lineNumber++;
                }
                CmdBase tmp = cmdParser.parse(script, lineNumber, lineData);
                if (tmp == null) {
                    throw new RuntimeException("tmp should not be null in ["+script+"("+lineNumber+")]");
                }
                if (!(tmp instanceof EmptyCmd)) {
                    cmdList.add(tmp);
                }

                lineNumber++;
            }

            if (cmdList.isEmpty()) {
                IOUtils.closeQuietly(fileInputStream);
                throw new RuntimeException("no scripts.");
            }
            scriptItem.init(cmdList);
            scriptItemList.add(scriptItem);

            IOUtils.closeQuietly(fileInputStream);
        }

        return scriptItemList;
    }
    
    
}
