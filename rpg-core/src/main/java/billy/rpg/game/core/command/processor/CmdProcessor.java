package billy.rpg.game.core.command.processor;

import billy.rpg.game.core.container.GameContainer;

/**
 * 命令处理器
 *
 * @author liulei@bshf360.com
 * @since 2017-07-21 14:03
 */
public abstract class CmdProcessor {

    protected boolean pausing; // 当对话开始时，该值为true，命令暂时不再往下执行
    protected CmdProcessor innerCmdProcessor;

    public abstract void update(GameContainer gameContainer);

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
