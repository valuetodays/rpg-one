package billy.rpg.game.core.screen;

import billy.rpg.common.formatter.DefaultDialogTextFormatter;
import billy.rpg.common.formatter.DialogFormattedResult;
import billy.rpg.common.formatter.DialogFormattedText;
import billy.rpg.common.formatter.DialogTextFormatter;
import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.command.SayCmd;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.script.variable.VariableDeterminer;
import billy.rpg.game.core.util.KeyUtil;
import com.billy.resourcefilter.ResourceFilter;
import com.billy.resourcefilter.resource.Resource;
import com.billy.resourcefilter.resource.StringResource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * intend to show dialog in game
 */
public class DialogScreen extends BaseScreen {
    private int totalLine; // 对话总共会显示多少行
    private int curLine; // 对话显示到哪一行了
    private List<DialogFormattedText> msgList; // 处理后的对话的内容
    private Image headImg; // 说话者的头像
    private CmdProcessor cmdProcessor;
    private SayCmd.PositionEnum position; // headImg location
    private String talker; // talker
//    private final DialogTextFormatter dialogTextFormatter = new ColorDialogTextFormatter(GameConstant.DIALOG_WORDS_NUM_PER_LINE);
    private final DialogTextFormatter dialogTextFormatter = new DefaultDialogTextFormatter(GameConstant.DIALOG_WORDS_NUM_PER_LINE);


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

        msg = StringUtils.isEmpty(msg) ? "" : msg;
        String filteredMsg = TextFilter.filterMsg(msg, true);
        DialogFormattedResult dialogFormattedResult = dialogTextFormatter.format(filteredMsg);
        msgList = dialogFormattedResult.getTextList();
        totalLine = dialogFormattedResult.getTotalLine();
        calculateTotalLine();
        curLine = 1;
    }

    private static class TextFilter {
        static String filterMsg(String msg, boolean flag) {
            if (!flag) {
                return msg;
            }
            Map<String, Integer> variableMap = VariableDeterminer.getInstance().realData();
            Map<String, String> stringStringMap = variableMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().toString()));
            ResourceFilter resourceFilter = new ResourceFilter();
            System.getProperties().stringPropertyNames().forEach(e -> resourceFilter.addFilter(e, System.getProperty(e))); // 添加系统变量
            resourceFilter.addFilters(stringStringMap);

            Resource stringResource = new StringResource(msg, resourceFilter);
            try {
                stringResource.doFilter();
            } catch (IOException e) {
                throw new RuntimeException("exception when filter string: " + e.getMessage(), e);
            }
            return stringResource.getOutputAsString();
        }
    }

    private void calculateTotalLine() {
        totalLine = totalLine / 2 + (totalLine % 2 > 0 ? 1 : 0);
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        if (curLine > totalLine) {
            if (cmdProcessor != null) {
                cmdProcessor.endPause();
            }
            gameContainer.getGameFrame().popScreen();
        }
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        if (CollectionUtils.isEmpty(msgList)) {
            return;
        }
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();

        Image gameDlgBg = gameContainer.getGameAboutItem().getGameDlgBg();
        Image gameDlgRoleName = gameContainer.getGameAboutItem().getGameDlgRoleName();
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
            DialogFormattedText msgContent = msgList.get(n);

            if (lineNum == start) {
                startIndexInMsgList = n;
                break;
            }
            if (msgContent.getCnt() == -1) {
                lineNum++;
            }
        }

        lineNum = 2;
        boolean newline = false;
        String msgShown = "";
        for (int n = startIndexInMsgList; n < msgList.size() && lineNum > 0; n++) {
            DialogFormattedText msgContent = msgList.get(n);
            if (msgContent.getCnt() == -1) {
                lineNum--;
                if (lineNum == 0) {
                    break;
                }
                if (lineNum == 1) {
                    newline = true;
                    msgShown = "";
                }
            } else {
                g.setColor(msgContent.getColor());
                g.drawString(msgContent.getContent(),
                        (dlgBgLeft + 20 + g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth("测"))
                                + g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth(msgShown),
                        dlgBgTop + 60 + (newline ? fontHeight : 0));
                msgShown += msgContent.getContent();
            }
        }

        g.setColor(oldColor);
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }


    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        if (KeyUtil.isDown(key)
                || KeyUtil.isUp(key)
                || KeyUtil.isLeft(key)
                || KeyUtil.isRight(key)
                ) {
            curLine += 1;
        }
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {
    }

}
