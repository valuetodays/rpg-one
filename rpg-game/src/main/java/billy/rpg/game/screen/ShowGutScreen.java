package billy.rpg.game.screen;

import billy.rpg.game.GameCanvas;
import billy.rpg.game.constants.GameConstant;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author liulei@bshf360.com
 * @since 2017-11-27 22:49
 */
public class ShowGutScreen extends BaseScreen {
    private String content;
    private String[] contentArr;
    private int currentLine = 0;
    private long lastUpdateTime = System.currentTimeMillis();
    private int delay = 2000; // 2s

    public ShowGutScreen(String content) {
        content = "引：                 天地玄黄，宇宙洪荒  自古道魔不两立，    世虽以道为正，道魔  之争却从未休止。    究其因，何以道正而  魔始未灭，          属正邪者，心也！    正所谓：             道非道，魔非魔      善恶在人心！                            江湖中有耳朵的人，绝无一人没有听见过“无机道长”这人的名字，江湖中有眼睛的人，也绝无一人不想瞧瞧“无机道长”绝世风采和他的绝代神功。        只因为任何人都知道，世上绝没有一个少女能抵挡“无机道长”的微微一笑，也绝没有一个英雄能抵挡“无机道长”的轻轻一剑！      任何人都相信，“无机道长”的剑非但能在百万军中取主帅之首级，也能将一根头发分成两根，而他的笑，却可令少女的心碎。        3月28日晚，无机和 当时危害人间的大魔头——赤血天魔依约在三清山的伏魔洞前进行生死决斗。              决斗后，无机负伤而归，赤血天魔不知所终。决斗结果无人知晓。  二十年后……                                                                                      ";
        this.content = linefeed(content, 20);
        this.contentArr = this.content.split("\n");
    }

    /**
     * 文本换行
     * @param str 原文本
     * @param num 隔多少字符换行
     * @return 换行后的文本
     */
    private static String linefeed(String str, int num) {
        if (str == null) {
            return null;
        }

        String ret = "";
        int start_pos = 0;
        while (start_pos < str.length()) {
            if (start_pos + num > str.length()) {
                num = str.length() - start_pos;
            }
            ret += str.substring(start_pos, start_pos+num) + "\n";
            start_pos += num;
        }

        return ret;
    }
    @Override
    public void update(long delta) {
        if (System.currentTimeMillis() - lastUpdateTime >= delay) {
            currentLine++;
            if (currentLine > contentArr.length) {
//                GameFrame.getInstance().popScreen();
            }
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(GameCanvas gameCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setColor(Color.WHITE);
        //g.clearRect(0, 0, paint.getWidth(), paint.getHeight());
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.setColor(Color.BLACK);

        g.setFont(new Font("宋体", Font.BOLD, 24));
        for (int i = currentLine; i < Math.min(contentArr.length, currentLine + 6); i++) {
            g.drawString(contentArr[i], 60, 150 + (i-currentLine) * (g.getFontMetrics().getHeight() + 4));
        }
        g.dispose();
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

    }


}
