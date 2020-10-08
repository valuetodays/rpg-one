package billy.rpg.game.core.screen;

import billy.rpg.game.core.DesktopCanvas;
import billy.rpg.game.core.constants.GameConstant;
import billy.rpg.game.core.container.GameContainer;
import billy.rpg.game.core.util.KeyUtil;
import org.apache.commons.lang.StringUtils;

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
    private int delay = 1500; // 2s

    /**
     * `br` --> \n
     * @param content  content
     */
    public ShowGutScreen(String content) {
        if (StringUtils.isEmpty(content)) {
            throw new RuntimeException("content of showgut is null or empty.");
        }
        this.content = linefeed(content.replace("`br`", "\n"), 20);
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
            String substring = str.substring(start_pos, start_pos + num);
            int n = substring.indexOf("\n");
            if (n  > -1) {
                String ret0 = "";
                while (n > -1) {
                    String substring1 = substring.substring(0, n+1);
                    ret0 += substring1;
                    substring = substring.substring(n+1);
                    n = substring.indexOf("\n");
                }
                int num0 =  str.substring(start_pos, start_pos + num).length() - ret0.length();
                ret += ret0;
                start_pos += ret0.length();
            } else {
                ret += substring + "\n";
                start_pos += num;
            }
        }

        return ret;
    }

    @Override
    public void update(GameContainer gameContainer, long delta) {
        if (System.currentTimeMillis() - lastUpdateTime >= delay) {
            currentLine++;
            if (currentLine > contentArr.length) {
                gameContainer.getGameFrame().popScreen();
            }
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(GameContainer gameContainer, DesktopCanvas desktopCanvas) {
        BufferedImage paint = new BufferedImage(
                GameConstant.GAME_WIDTH,
                GameConstant.GAME_HEIGHT,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = paint.getGraphics();
        g.setColor(Color.WHITE);
        //g.clearRect(0, 0, paint.getWidth(), paint.getHeight());
        g.fillRect(0, 0, paint.getWidth(), paint.getHeight());
        g.setColor(Color.BLACK);

        g.setFont(new Font("黑体", Font.BOLD, 24));
        for (int i = currentLine; i < Math.min(contentArr.length, currentLine + 6); i++) {
            g.drawString(contentArr[i], 60, 150 + (i-currentLine) * (g.getFontMetrics().getHeight() + 4));
        }
        g.dispose();
        desktopCanvas.drawBitmap(gameContainer.getGameFrame(), paint, 0, 0);
    }

    @Override
    public void onKeyDown(GameContainer gameContainer, int key) {
        if (KeyUtil.isDown(key)) {
            currentLine++;
            update(gameContainer, 0L);
        }
    }

    @Override
    public boolean isPopup() {
        return true;
    }

    @Override
    public void onKeyUp(GameContainer gameContainer, int key) {

    }


}
