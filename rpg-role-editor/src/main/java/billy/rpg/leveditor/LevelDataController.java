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
public class LevelDataController extends Pane {
    private static final Logger LOG = Logger.getLogger(LevelDataController.class);
    private LevelEditorController levelEditorController;
    private Stage maxLevelStage;
    @FXML
    private TextField tfMaxLevel; // 最大等级数
    @FXML
    private TextField tfNumber; // 编号

    public LevelDataController(LevelEditorController levelEditorController) {
        this.levelEditorController = levelEditorController;
        FXMLLoader fXMLLoader = new FXMLLoader(LevelDataController.class.getResource("/level-info.fxml"));
        fXMLLoader.setRoot(this);
        fXMLLoader.setController(this);
        try {
            fXMLLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void display() {
        maxLevelStage = new Stage(StageStyle.DECORATED);
        maxLevelStage.setResizable(false);
        maxLevelStage.setTitle("新建");
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
            levelEditorController.initLevelInfo(maxLevel, number);
            maxLevelStage.hide();
        } catch (NumberFormatException nfe) {
            AlertBox.alert("输入格式不对。");
        }
    }


}
