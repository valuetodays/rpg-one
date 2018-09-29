package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.GameFrame;
import billy.rpg.game.cmd.SayCmd;
import billy.rpg.game.cmd.processor.CmdProcessor;
import billy.rpg.game.constants.GameConstant;
import billy.rpg.game.formatter.ColorDialogTextFormatter;
import billy.rpg.game.formatter.DialogFormattedResult;
import billy.rpg.game.formatter.DialogTextFormatter;
import billy.rpg.game.util.KeyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * intend to show dialog in game
 */
public class DialogScreen extends BaseScreen {
    private int totalLine; // 对话总共会显示多少行
    private int curLine; // 对话显示到哪一行了
    private List<DialogFormattedResult.DialogFormattedText> msgList; // 处理后的对话的内容
    private Image headImg; // 说话者的头像
    private CmdProcessor cmdProcessor;
    private SayCmd.PositionEnum position; // headImg location
    private String talker; // talker

    private DialogTextFormatter dialogTextFormatter = new ColorDialogTextFormatter();

    /**
     * 对话框窗口
     * @param cmdProcessor 脚本处理器
     * @param headImg 说话者头像编号
     * @param msg 对话内容
     */
    public DialogScreen(CmdProcessor cmdProcessor, Image headImg, String name, SayCmd.PositionEnum position, String msg) {
        this.cmdProcessor = cmdProcessor;
        this.headImg = headImg;
        SayCmd.PositionEnum.assertLegal(position, "对话框角色图片位置有误");
        this.position = position;
        this.talker = name;
        DialogFormattedResult dialogFormattedResult = dialogTextFormatter.format(StringUtils.isEmpty(msg) ? "" : msg);
        msgList = dialogFormattedResult.getTextList();
        totalLine = dialogFormattedResult.getTotalLine();
        calculateTotalLine();
        curLine = 1;
    }

    private void calculateTotalLine() {
        totalLine--; // remove first
        totalLine = totalLine / 2 + (totalLine % 2 > 0 ? 1 : 0);
    }

    @Override
    public void update(long delta) {
        if (curLine > totalLine) {
            if (cmdProcessor != null) {
                cmdProcessor.endPause();
            }
            GameFrame.getInstance().popScreen();
        }
    }


    @Override
    public void draw(GameCanvas gameCanvas) {
        if (CollectionUtils.isEmpty(msgList)) {
            return;
        }
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        Image gameDlgBg = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameDlgBg();
        Image gameDlgRoleName = GameFrame.getInstance().getGameContainer().getGameAboutItem().getGameDlgRoleName();
        final int dlgBgLeft = (GameConstant.GAME_WIDTH-gameDlgBg.getWidth(null)) / 2;
        final int dlgBgTop = (GameConstant.GAME_HEIGHT-gameDlgBg.getHeight(null)) - 20;
        g.drawImage(gameDlgBg,
                dlgBgLeft,
                dlgBgTop,
                null);
        if (position == SayCmd.PositionEnum.LEFT) {
            g.drawImage(gameDlgRoleName,
                    dlgBgLeft + 20,
                    dlgBgTop - gameDlgRoleName.getHeight(null),
                    null);
            g.drawImage(headImg,
                    dlgBgLeft + 10,
                    dlgBgTop - gameDlgRoleName.getHeight(null) - headImg.getHeight(null) - 2,
                    null);
            g.setFont(GameConstant.FONT_ROLENAME_IN_DLG);
            g.drawString(talker,
                    dlgBgLeft + 45,
                    dlgBgTop - gameDlgRoleName.getHeight(null)/3);
        } else if (position == SayCmd.PositionEnum.RIGHT) {
            g.drawImage(gameDlgRoleName,
                    GameConstant.GAME_WIDTH/2 + gameDlgBg.getWidth(null)/2
                            - gameDlgRoleName.getWidth(null) - 20,
                    dlgBgTop - gameDlgRoleName.getHeight(null),
                    null);
            g.drawImage(headImg,
                    GameConstant.GAME_WIDTH/2 + gameDlgBg.getWidth(null)/2
                            - headImg.getWidth(null) - 10,
                    dlgBgTop - gameDlgRoleName.getHeight(null) - headImg.getHeight(null) - 2,
                    null);
            g.setFont(GameConstant.FONT_ROLENAME_IN_DLG);
            g.drawString(talker,
                    GameConstant.GAME_WIDTH/2 + gameDlgBg.getWidth(null)/2
                            - 30 - 60,
                    dlgBgTop - gameDlgRoleName.getHeight(null)/3);
        } else if (position == SayCmd.PositionEnum.NONE) {
            g.setFont(GameConstant.FONT_ROLENAME_IN_DLG);
            g.drawString(talker,
                    dlgBgLeft + 45,
                    dlgBgTop - gameDlgRoleName.getHeight(null)/3);
        }


        int fontHeight = g.getFontMetrics().getHeight();
        //g.setColor(Color.darkGray);
//        g.fillRoundRect(30, 200,
//                g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth("测") * (SEP + 2),
//                fontHeight * 3, 20, 20);
        g.setFont(GameConstant.FONT_DLG_MSG);
        g.setColor(Color.WHITE);
        Color oldColor = g.getColor();

        int start = curLine * 2 - 1;
        int startIndexInMsgList = 0;
        int lineNum = 0;
        for (int n = 0; n < msgList.size(); n++) {
            DialogFormattedResult.DialogFormattedText msgContent = msgList.get(n);

            if (lineNum == start) {
                startIndexInMsgList = n;
                break;
            }
            if (msgContent.cnt == -1) {
                lineNum++;
            }
        }

        lineNum = 2;
        boolean newline = false;
        String msgShown = "";
        for (int n = startIndexInMsgList; n < msgList.size() && lineNum > 0; n++) {
            DialogFormattedResult.DialogFormattedText msgContent = msgList.get(n);
            if (msgContent.cnt == -1) {
                lineNum--;
                if (lineNum == 0) {
                    break;
                }
                if (lineNum == 1) {
                    newline = true;
                    msgShown = "";
                }
            } else {
                g.setColor(msgContent.color);
                g.drawString(msgContent.content,
                        (dlgBgLeft + 20 + g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth("测"))
                                + g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth(msgShown),
                        dlgBgTop + 60 + (newline ? fontHeight : 0));
                msgShown += msgContent.content;
            }
        }

        g.setColor(oldColor);
        gameCanvas.drawBitmap(paint, 0, 0);
    }


    @Override
    public void onKeyDown(int key) {

    }

    @Override
    public void onKeyUp(int key) {
        if (KeyUtil.isDown(key)
                || KeyUtil.isUp(key)
                || KeyUtil.isLeft(key)
                || KeyUtil.isRight(key)
                ) {
            curLine += 1;
        }
    }

}
