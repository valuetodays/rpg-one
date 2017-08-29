package billy.rpg.leveditor;

import billy.rpg.resource.level.LevelData;
import billy.rpg.resource.level.LevelLoader;
import billy.rpg.resource.level.LevelMetaData;
import billy.rpg.resource.level.LevelSaver;
import billy.rpg.roleeditor.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 注意要想让表格里的数据可编辑，要使用 TableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
 *  我也是整了好久没有仔细看官方示例http://docs.oracle.com/javafx/2/ui_controls/tvTable-view.htm
 *  最终在该页面http://blog.csdn.net/mexel310/article/details/23364397
 *  才找到解决办法的。
 *
 * @author liulei@bshf360.com
 * @since 2017-07-25 14:37
 */
public class LevelEditorController implements Initializable {
    private static final Logger LOG = Logger.getLogger(LevelEditorController.class);

    @FXML
    private TableView<LevelData> tvTable;

    @FXML
    private TextField tfNumber; // 编号
    @FXML
    private TextField tfMaxLevel; // 最大等级数
    private FileChooser fileSaveChooser = new FileChooser();
    private FileChooser fileLoadChooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvTable.setEditable(true);
        String[] columns = new String[]{"level", "maxHp", "maxMp", "attack", "defend", "exp"};
        TableColumn<LevelData, Integer> levelCol = new TableColumn<>(columns[0]);
        levelCol.setMinWidth(100);
        levelCol.setCellValueFactory(
                new PropertyValueFactory<>("level"));
        tvTable.getColumns().add(levelCol);
        for (int i = 1; i < columns.length; i++) {
            String columnName = columns[i];
            TableColumn<LevelData, Integer> col = new TableColumn<>(columnName);
            col.setMinWidth(100);
            col.setEditable(true);
            col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<LevelData, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<LevelData, Integer> t) {
                        try {
                            Method declaredMethod = LevelData.class.getDeclaredMethod(
                                    "set" + columnName.substring(0, 1).toUpperCase() +
                                    columnName.substring(1), t.getNewValue().getClass());

                            LevelData levelMetaData = t.getTableView().getItems().get(t.getTablePosition().getRow());
                            declaredMethod.invoke(levelMetaData, t.getNewValue());
                        } catch (Exception e) {
                            e.printStackTrace();
                            AlertBox.alert("编辑时出错，请检查。");
                        }
                    }
                }
            );
            col.setCellValueFactory(new PropertyValueFactory<>(columnName));
            tvTable.getColumns().add(col);
        } // end of for

        fileSaveChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileSaveChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("level file", "*.lvl")
        );
        fileLoadChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileLoadChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("level file", "*.lvl")
        );
    }

    /**
     * 获取控件元素的窗口对象
     * @param event
     * @return
     */
    public static Window getWindow(ActionEvent event){
        return((Node)event.getSource()).getScene().getWindow();
    }


    public void initLevelInfo(int maxLevel, int number) {
        tfMaxLevel.setText("" + maxLevel);
        tfNumber.setText("" + number);
        List<LevelData> metaDataList = new ArrayList<>();
        for (int i = 1; i <= maxLevel; i++) {
            metaDataList.add(new LevelData(i));
        }
        tvTable.setEditable(true);
        tvTable.setItems(FXCollections.observableArrayList(metaDataList));
    }

    /**
     * 新建
     * @param event event
     */
    @FXML
    public void onNewAction(ActionEvent event) {
        new LevelDataController(this).display();
    }

    /**
     * 保存
     * @param event event
     */
    @FXML
    public void onSaveAction(ActionEvent event) {
        String numberText = tfNumber.getText();
        if (StringUtils.isEmpty(numberText)) {
            AlertBox.alert("请输入编号");
            return;
        }
        int number = Integer.parseInt(numberText);
        String maxLevelText = tfMaxLevel.getText();
        if (StringUtils.isEmpty(maxLevelText)) {
            AlertBox.alert("请输入最大等级数");
            return;
        }
        int maxLevel = Integer.parseInt(maxLevelText);
        LOG.debug("number:" + number);
        LOG.debug("maxLevel:" + maxLevel);
        ObservableList<LevelData> items = tvTable.getItems();
        for (LevelData item : items) {
            if (!item.isValid()) {
                AlertBox.alert("有数据为空，请补全后再保存");
                return;
            }
        }
        LevelMetaData lmd = new LevelMetaData();
        lmd.setNumber(number);
        lmd.setMaxLevel(maxLevel);
        // 将ObservableList转成ArrayList
        List<LevelData> levelDataList = items.stream().collect(Collectors.toList());
        lmd.setLevelDataList(levelDataList);

        File fileSave = fileSaveChooser.showSaveDialog(getWindow(event));
        if (fileSave != null) {
            String parentPath = fileSave.getParentFile().getPath();
            String name = fileSave.getName();
            // TODO aa.LVL ??
            if (!name.endsWith(".lvl")) {
                name += ".lvl";
            }
            LevelSaver.save(parentPath + File.separator + name, lmd);
        }

    }

    /**
     * 加载
     * @param event event
     */
    @FXML
    public void onLoadAction(ActionEvent event) {
        File fileLoad = fileLoadChooser.showOpenDialog(getWindow(event));

        if (fileLoad != null) {
            LevelMetaData loadedLmd = LevelLoader.load(fileLoad.getPath());
            tfNumber.setText("" + loadedLmd.getNumber());
            tfMaxLevel.setText("" + loadedLmd.getMaxLevel());
            List<LevelData> levelDataList = loadedLmd.getLevelDataList();
            tvTable.setItems(FXCollections.observableArrayList(levelDataList));
        }
    }

    @FXML
    public void onAboutAction(ActionEvent event) {
        AlertBox.alert("帮助：\r\n1. aaaa\r\n2. bbbb\r\n3. cccc");
    }


}
