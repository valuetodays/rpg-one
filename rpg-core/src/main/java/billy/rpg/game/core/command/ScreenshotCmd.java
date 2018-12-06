package billy.rpg.game.core.command;

import billy.rpg.common.util.AssetsUtil;
import billy.rpg.game.core.command.processor.CmdProcessor;
import billy.rpg.game.core.container.GameContainer;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 截屏
 *
 * @author lei.liu@datatist.com
 * @since 2018-12-06 18:35:06
 */
public class ScreenshotCmd extends CmdBase {
    private String remark;

    @Override
    public void init() {
        List<String> arguments = super.getArguments();
        remark = arguments.get(0);
    }

    @Override
    public int execute(GameContainer gameContainer, CmdProcessor cmdProcessor) {
        BufferedImage background = gameContainer.getGameFrame().getGamePanel().background;
        try {
            String resourcePath = AssetsUtil.getResourcePath("/");
            logger.debug(resourcePath);
            File saveDirectory = new File(resourcePath + "/save/");
            saveDirectory.mkdirs();
            String fileName = DateFormatUtils.format(new Date(), "yyyyMMdd_HH_mm_ss") + remark + ".png";
            File savedFile = new File(saveDirectory.getPath() + "/" + fileName);
            ImageIO.write(background, "PNG", savedFile);
            logger.debug("Screenshot file is saved as " + savedFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getUsage() {
        return "screenshot";
    }

    @Override
    public int getArgumentSize() {
        return 0;
    }
}
