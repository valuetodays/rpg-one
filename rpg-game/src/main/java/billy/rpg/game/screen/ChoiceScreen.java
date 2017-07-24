package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.executor.CmdProcessor;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.container.GameContainer;
import billy.rpg.game.script.LabelBean;
import billy.rpg.game.util.KeyUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-24 14:31
 */
public class ChoiceScreen extends BaseScreen {
    private String title;
    private List<String> choice;
    private List<String> label;
    private CmdProcessor cmdProcessor;
    private int selectedInx;

    public ChoiceScreen(CmdProcessor cmdProcessor, String title, List<String> choice, List<String> label) {
        this.cmdProcessor = cmdProcessor;
        this.title = title;
        this.choice = choice;
        this.label = label;
    }

    @Override
    public void update(long delta) {

    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setColor(Color.black);
        g.setFont(GameConstant.FONT_DLG_MSG);
        g.drawString(title, 100, 20);
        for (int i = 0; i < choice.size(); i++) {
            String s = choice.get(i);
            g.drawString(s, 100, 100 + 30 * i);
        }
        Image gameArrowRight = GameContainer.getInstance().getGameAboutItem().getGameArrowRight();
        g.drawImage(gameArrowRight, 80, 100 + selectedInx * 30 - 15, null);

        gameCanvas.drawBitmap(paint, 0, 0);
    }

    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public boolean isPopup() {
        return true;
    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isEsc(key)) {
            cmdProcessor.endPause();
            GameFrame.getInstance().popScreen();
        }
        if (KeyUtil.isEnter(key)) {
            String selectedLabelTitle = label.get(selectedInx);
            LabelBean selectedLabel = GameFrame.getInstance().getGameContainer().getActiveFileItem().getLabelByTitle
                    (selectedLabelTitle);
            LOG.debug("selectInx/selectedLabelTitle=" + selectedInx + "/"+ selectedLabelTitle);
            GameFrame.getInstance().popScreen();
            CmdProcessor cmdProcessor = new CmdProcessor(selectedLabel.getCmds());
            GameFrame.getInstance().getGameContainer().getActiveFileItem().setCmdProcessor(cmdProcessor);
        }
        if (KeyUtil.isUp(key)) {
            if (selectedInx > 0) {
                selectedInx--;
            }
        } else if (KeyUtil.isDown(key)) {
            if (selectedInx < choice.size()-1) {
                selectedInx++;
            }
        }
    }


}
