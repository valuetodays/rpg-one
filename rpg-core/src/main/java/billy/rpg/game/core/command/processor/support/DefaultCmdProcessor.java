package billy.rpg.game.core.command.processor.support;

import billy.rpg.game.core.command.CmdBase;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author lei.liu@datatist.com
 * @since 2018-09-29 11:05:10
 */
public class DefaultCmdProcessor extends CmdProcessor {
    private static Logger LOG = Logger.getLogger(CmdProcessor.class);
    private List<CmdBase> cmdList;
    private int cmdSize;
    private int cmdIndex;
    // 遇到了return命令就说明执行完毕了，典型是的if语句的子语句，它的每个分支都有return，任何一个分支走完都算结束了
    private boolean metReturnCmd;


    @Override
    public void update(GameContainer gameContainer) {
        if (innerCmdProcessor != null) {
            innerCmdProcessor.update(gameContainer);
        } else {
            if (!super.pausing) {
                if (endExecute()) {
                    return;
                }
                executeCmd(gameContainer, cmdList.get(cmdIndex));
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
    private int executeCmd(GameContainer gameContainer, CmdBase cmd) {
        cmd.execute(gameContainer, this);
        return 0;
    }


    public DefaultCmdProcessor(List<CmdBase> cmdList) {
        this.cmdList = cmdList;
        cmdSize = cmdList.size();
    }

}
