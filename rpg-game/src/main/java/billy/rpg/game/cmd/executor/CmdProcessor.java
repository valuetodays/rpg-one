package billy.rpg.game.cmd.executor;

import billy.rpg.game.cmd.CmdBase;
import org.apache.log4j.Logger;

import java.util.List;


/**
 * @author liulei@bshf360.com
 * @since 2017-07-21 14:03
 */
public class CmdProcessor {
    private static Logger LOG = Logger.getLogger(CmdProcessor.class);
    private boolean pausing; // 当对话开始时，该值为true，命令暂时不再往下执行
    private List<CmdBase> cmdList;
    private int cmdSize;
    private int cmdIndex;
    // 遇到了return命令就说明执行完毕了，典型是的if语句的子语句，它的每个分支都有return，任何一个分支走完都算结束了
    private boolean metReturnCmd;
    private CmdProcessor innerCmdProcessor;

    public void update() {
        if (innerCmdProcessor != null) {
            innerCmdProcessor.update();
        } else {
            if (!pausing) {
                if (endExecute()) {
                    return;
                }
                executeCmd(cmdList.get(cmdIndex));
                cmdIndex++;
            }
        }
    }

    private boolean endExecute() {
        if (cmdIndex >= cmdSize) {
            return true;
        }

        return metReturnCmd;
    }

    /**
     *
     * 执行脚本命令
     * 有的脚本应该是阻塞式的，它没执行完毕不应该往下执行，典型的就是对话
     *
     * @param cmd 命令对象
     * @return  -1: rtn occurs;
     *          -2: does not fulfill the if condition
     */
    private int executeCmd(CmdBase cmd) {
        cmd.execute(this);
        return 0;
    }


    public CmdProcessor(List<CmdBase> cmdList) {
        this.cmdList = cmdList;
        cmdSize = cmdList.size();
    }

    /**
     * 挂起脚本执行 - 开始
     */
    public void startPause() {
        pausing = true;
    }

    /**
     * 挂起脚本执行 - 结束
     */
    public void endPause() {
        pausing = false;
    }

    public void setInnerCmdProcessor(CmdProcessor innerCmdProcessor) {
        this.innerCmdProcessor = innerCmdProcessor;
    }
}
