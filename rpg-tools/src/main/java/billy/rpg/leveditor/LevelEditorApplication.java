package billy.rpg.leveditor;

import billy.rpg.roleeditor.RoleEditorApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-25 14:31
 */
public class LevelEditorApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(RoleEditorApplication.class.getResource("/level-editor.fxml"));
        primaryStage.setTitle("Level Editor V1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}