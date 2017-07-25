package billy.rpg.leveditor;

import billy.rpg.roleeditor.AlertBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 注意要想让表格里的数据可编辑，要使用 TableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
 *  我也是整了好久没有仔细看官方示例http://docs.oracle.com/javafx/2/ui_controls/table-view.htm
 *  最终在该页面http://blog.csdn.net/mexel310/article/details/23364397
 *  才找到解决办法的。
 *
 * @author liulei@bshf360.com
 * @since 2017-07-25 14:37
 */
public class LevelEditorController implements Initializable {
    private static final Logger LOG = Logger.getLogger(LevelEditorController.class);

    @FXML
    private MenuItem menuFileNew;
    @FXML
    private MenuItem menuFileClose;
    @FXML
    private MenuItem menuHelpAbout;
    @FXML
    private TableView<LevelMetaData> table;

    private int maxLevel; // 最大等级数
    private int number; // 编号

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuFileNew.setOnAction((ActionEvent t) -> {
            new LevelInfoController(this).display();
        });
        menuFileClose.setOnAction((ActionEvent t) -> {
            LOG.debug("exit");
            Platform.exit();
        });
        menuHelpAbout.setOnAction((ActionEvent t) -> {
            LOG.debug("help -> about");
            AlertBox.display("帮助：\r\n1. aaaa\r\n2. bbbb\r\n3. cccc");
        });

        /*
                  <TableColumn prefWidth="44.0" text="level" />
            <TableColumn prefWidth="70.0" text="maxHp" />
            <TableColumn prefWidth="58.0" text="maxMp" />
            <TableColumn prefWidth="50.0" text="attack" />
            <TableColumn prefWidth="50.0" text="defend" />
            <TableColumn prefWidth="53.0" text="exp" />

         */
        table.setEditable(true);
        String[] columns = new String[]{"level", "maxHp", "maxMp", "attack", "defend", "exp"};
        TableColumn<LevelMetaData, Integer> levelCol = new TableColumn<>(columns[0]);
        levelCol.setMinWidth(100);
        //levelCol.setEditable(false);
        levelCol.setCellValueFactory(
                new PropertyValueFactory<>("level"));
        table.getColumns().add(levelCol);
        for (int i = 1; i < columns.length; i++) {
            String columnName = columns[i];
            TableColumn<LevelMetaData, Integer> col = new TableColumn<>(columnName);
            col.setMinWidth(100);
            col.setEditable(true);
            col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<LevelMetaData, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LevelMetaData, Integer> t) {
                        try {
                            Method declaredMethod = LevelMetaData.class.getDeclaredMethod(
                                    "set" + columnName.substring(0, 1).toUpperCase() +
                                    columnName.substring(1), t.getNewValue().getClass());

                            LevelMetaData levelMetaData = t.getTableView().getItems().get(t.getTablePosition().getRow());
                            declaredMethod.invoke(levelMetaData, t.getNewValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                            AlertBox.display("编辑时出错，请检查。");
                        }
                    }
                }
            );
            col.setCellValueFactory(new PropertyValueFactory<>(columnName));
            table.getColumns().add(col);
        }
    }


    public void setLevelInfo(int maxLevel, int number) {
        this.maxLevel = maxLevel;
        this.number = number;
        System.out.println(maxLevel);
        List<LevelMetaData> metaDataList = new ArrayList<>();
        for (int i = 1; i <= maxLevel; i++) {
            metaDataList.add(new LevelMetaData(i));
        }
        table.setEditable(true);
        table.setItems(FXCollections.observableArrayList(metaDataList));
    }

    @FXML
    public void onSaveAction(ActionEvent event) {
        LOG.debug("maxLevel:" + maxLevel);
        LOG.debug("number:" + number);
        ObservableList<LevelMetaData> items = table.getItems();
        items.forEach(e -> {
            LOG.debug(e);
        });
    }
}
