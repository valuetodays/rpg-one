package com.billy.rpg.game.screen;

import com.billy.rpg.game.GameCanvas;
import com.billy.rpg.game.GameFrame;
import com.billy.rpg.game.constants.GameConstant;
import com.billy.rpg.game.util.KeyUtil;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DialogScreen extends BaseScreen {
    private static final int SEP = 10; // TODO cause problem when this is 14...
    private int totalLine;
    private int curLine;
    private String msg;
    private List<MsgContent> msgList;

    public DialogScreen(String msg) {
        if (StringUtils.isEmpty(msg)) {
            this.msg = "";
        } else {
            this.msg = msg;
        }
        dealMsg();
        curLine = 1;
    }

    public static void main(String[] args) {
//		String msg = "我是`y`天大地大我最大张三`/y`，十分、一百分`r`、`/r`一千分、一万分、十万分、百万分郑重地命令你这小厮快去打`r`大龙`/r`！你快去啊，真是不听话，害我说那么多话，真可恶！什么？！你说`b`我废话多`/b`？你再说，再说一次试试？";
        String msg = "我是";
        new DialogScreen(msg);
    }

    private List<MsgContent> dealTag() {
        List<MsgContent> msgListTemp = new ArrayList<>();

        String msgTemp = msg;
        while (true) {

            int colorTagPos = msgTemp.indexOf('`');
            if (colorTagPos == -1) {
                break;
            }
            String tagBegin = msgTemp.substring(colorTagPos, colorTagPos + 3);
            int indexOf = msgTemp.indexOf(tagBegin);
            if (indexOf > -1) {
                String bef = msgTemp.substring(0, indexOf);
                msgListTemp.add(new MsgContent(bef, Color.WHITE));
                String tagEnd = tagBegin.substring(0, 1) + "/" + tagBegin.substring(1);
                int indexOf2 = msgTemp.indexOf(tagEnd, indexOf);
                String coloredMsg = msgTemp.substring(indexOf + tagBegin.length(), indexOf2);
                Color color = getColor(tagBegin);
                msgListTemp.add(new MsgContent(coloredMsg, color));
                msgTemp = msgTemp.substring(indexOf2 + tagEnd.length());
            }
        }

        msgListTemp.add(new MsgContent(msgTemp, Color.WHITE));

        return msgListTemp;
    }

    private void dealMsg() {
        List<MsgContent> msgListTemp = dealTag();

        int cnt = 0;
        initMsgList();
        for (int i = 0; i < msgListTemp.size(); i++) {
            MsgContent mc = msgListTemp.get(i);
            int mccnt = mc.cnt;
            String mccontent = mc.content;
            if (mccnt < SEP) {
                if (mccnt < SEP - cnt) {
                    msgList.add(mc);
                    cnt += mc.cnt;
                    if (i == msgListTemp.size() - 1) {
                        totalLine++;
//                        System.out.println("one");
                    }
                } else {
                    String pre = mccontent.substring(0, SEP - cnt);
                    MsgContent mPre = new MsgContent(pre, mc.color);
                    msgList.add(mPre);
                    appendSeperator(mc.color);
                    int n = pre.length();

                    MsgContent mPost = new MsgContent(mccontent.substring(n, mc.cnt), mc.color);
                    msgList.add(mPost);
                    cnt = mPost.cnt;
                }
            } else {
                String pre = mccontent.substring(0, SEP - cnt);
                MsgContent mPre = new MsgContent(pre, mc.color);
                msgList.add(mPre);
                appendSeperator(mc.color);

                int n = pre.length();
                while (mccnt > SEP) {
                    MsgContent m = new MsgContent(
                            mccontent.substring(n, Math.min(n + SEP, mc.cnt)),
                            mc.color);
                    msgList.add(m);
                    mccnt -= SEP;
                    n += SEP;
                    appendSeperator(mc.color);
                }
                if (n < mc.cnt) {
                    MsgContent mPost = new MsgContent(mccontent.substring(n, mc.cnt), mc.color);
                    msgList.add(mPost);
                    cnt = mPost.cnt;
                }
            }
        }

        calcuteTotalLine();
    }

    private void initMsgList() {
        msgList = new ArrayList<>();
        appendSeperator(Color.WHITE);
    }

    private void calcuteTotalLine() {
        totalLine--; // remove first
        totalLine = totalLine / 2 + (totalLine % 2 > 0 ? 1 : 0);
    }

    private void appendSeperator(Color color) {
        MsgContent mNull = new MsgContent(null, color);
        msgList.add(mNull);
        totalLine++;
    }

    private static List<String> tags = new ArrayList<>();

    static {
        tags.add("`r`");
        tags.add("`g`");
        tags.add("`b`");
        tags.add("`y`");
        tags.add("`/r`");
        tags.add("`/g`");
        tags.add("`/b`");
        tags.add("`/y`");
    }

    @Override
    public void update(long delta) {
        if (curLine > totalLine) {
            GameFrame.getInstance().popScreen();
        }
    }


    @Override
    public void draw(GameCanvas gameCanvas) {
        // 创建一个缓冲区
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        // 得到缓冲区的画笔
        Graphics g = paint.getGraphics();

        if (StringUtils.isEmpty(msg)) {
            msg = "empty";
        }
        int fontHeight = g.getFontMetrics().getHeight();
        g.setColor(Color.darkGray);
        g.fillRoundRect(30, 200,
                g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth("测") * (SEP + 2),
                fontHeight * 3, 20, 20);
        g.setFont(GameConstant.FONT_DLG_MSG);
        g.setColor(Color.WHITE);
        Color oldColor = g.getColor();

        int start = curLine * 2 - 1;
        int startIndexInMsgList = 0;
        int lineNum = 0;
        for (int n = 0; n < msgList.size(); n++) {
            MsgContent msgContent = msgList.get(n);

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
            MsgContent msgContent = msgList.get(n);
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
                        (30 + g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth("测"))
                                + g.getFontMetrics(GameConstant.FONT_DLG_MSG).stringWidth(msgShown),
                        220 + (newline ? fontHeight : 0));
                msgShown += msgContent.content;
            }
        }

        g.setColor(oldColor);
        gameCanvas.drawBitmap(paint, 10, 30);
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


    @Override
    public boolean isPopup() {
        return false;
    }

    private Color getColor(String tagName) {
        char flagName = tagName.charAt(1);
        if ('r' == flagName || 'R' == flagName) {
            return Color.red;
        }
        if ('b' == flagName || 'B' == flagName) {
            return Color.blue;
        }
        if ('g' == flagName || 'G' == flagName) {
            return Color.green;
        }
        if ('y' == flagName || 'Y' == flagName) {
            return Color.yellow;
        } else {
            return null;
        }
    }


    private class MsgContent {
        String content;
        Color color;
        int cnt;

        public MsgContent(String content, Color color) {
            this.content = content;
            this.color = color;
            cnt = (content == null ? -1 : content.length());
        }

        @Override
        public String toString() {
            return content + "@(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
        }
    }

}
