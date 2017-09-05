package billy.rpg.goodseditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-04 14:46
 */
public class GoodsEditorApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = GoodsEditorApplication.class.getResource("/goods-editor.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        fxmlLoader.setController(new GoodsEditorController());
        javafx.scene.Parent root = fxmlLoader.load(location.openStream());
        primaryStage.setTitle("Goods Editor V1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
