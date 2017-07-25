package billy.rpg.leveditor;

import billy.rpg.roleeditor.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-25 15:24
 */
public class LevelInfoController extends Pane {
    private static final Logger LOG = Logger.getLogger(LevelInfoController.class);
    private LevelEditorController levelEditorController;
    private Stage maxLevelStage;
    @FXML
    private TextField tfMaxLevel; // 最大等级数
    @FXML
    private TextField tfNumber; // 编号

    public LevelInfoController(LevelEditorController levelEditorController) {
        this.levelEditorController = levelEditorController;
        FXMLLoader fXMLLoader = new FXMLLoader(LevelInfoController.class.getResource("/level-info.fxml"));
        fXMLLoader.setRoot(LevelInfoController.this);
        fXMLLoader.setController(LevelInfoController.this);
        try {
            fXMLLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void display() {
        maxLevelStage = new Stage(StageStyle.DECORATED);
        maxLevelStage.setResizable(false);

        maxLevelStage.setTitle("关于");
        maxLevelStage.setMaximized(false);
        maxLevelStage.setResizable(false);
        maxLevelStage.setScene(new Scene(this));
        maxLevelStage.show();
    }

    @FXML
    public void onOKAction(ActionEvent event) {
        LOG.debug("ok...");
        String maxLevelText = tfMaxLevel.getText();
        String numberText = tfNumber.getText();
        try {
            int maxLevel = Integer.parseInt(maxLevelText);
            int number = Integer.parseInt(numberText);
            levelEditorController.setLevelInfo(maxLevel, number);
            maxLevelStage.hide();
        } catch (NumberFormatException nfe) {
            AlertBox.display("输入格式不对。");
        }
    }


}
