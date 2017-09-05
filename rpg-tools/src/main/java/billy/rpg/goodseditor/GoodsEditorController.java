package billy.rpg.goodseditor;

import billy.rpg.resource.goods.GoodsMetaData;
import billy.rpg.resource.goods.GoodsSaverLoader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author liulei@bshf360.com
 * @since 2017-09-04 14:46
 */
public class GoodsEditorController implements Initializable {
    private static final Logger LOG = Logger.getLogger(GoodsEditorController.class);

    private FileChooser loadFileChooser;
    private FileChooser saveFileChooser;
    @FXML
    private TextField tfNumber;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfBuy;
    @FXML
    private TextField tfSell;
    @FXML
    private TextField tfImageId;
    @FXML
    private TextField tfEvent;
    @FXML
    private TextField tfAnimationId;
    @FXML
    private ChoiceBox<Item> cbType;
    @FXML
    private CheckBox cbHero1;
    @FXML
    private CheckBox cbHero2;
    @FXML
    private CheckBox cbHero3;
    @FXML
    private CheckBox cbHero4;
    @FXML
    private TextArea taDesc;
    @FXML
    private TextField tfHp;
    @FXML
    private TextField tfMp;
    @FXML
    private TextField tfAttack;
    @FXML
    private TextField tfDefend;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final ExtensionFilter gdsFileExt = new ExtensionFilter("物品文件", "*.gds");
        loadFileChooser = new FileChooser();
        loadFileChooser.getExtensionFilters().add(gdsFileExt);
        saveFileChooser = new FileChooser();
        saveFileChooser.getExtensionFilters().add(gdsFileExt);
        List<Item> cbTypeItems = new ArrayList<>();
        cbTypeItems.add(new Item(0, "药物类"));
        cbTypeItems.add(new Item(1, "仙药类"));
        cbTypeItems.add(new Item(2, "武器类"));
        cbTypeItems.add(new Item(3, "披风类"));
        cbTypeItems.add(new Item(4, "鞋子类"));
        cbType.setItems(FXCollections.observableArrayList(cbTypeItems));
        cbType.getSelectionModel().select(0);
    }

    @FXML
    public void onLoad(ActionEvent e) {
        File file = loadFileChooser.showOpenDialog(null);
        if (file != null) {
            GoodsMetaData loadedGoods = GoodsSaverLoader.load(file.getPath());
            tfNumber.setText("" + loadedGoods.getNumber());
            cbType.getSelectionModel().select(cbType.getSelectionModel().getSelectedIndex());
            tfName.setText(loadedGoods.getName());
            tfBuy.setText("" + loadedGoods.getBuy());
            tfSell.setText("" + loadedGoods.getSell());
            tfImageId.setText("" + loadedGoods.getImageId());
            tfEvent.setText("" + loadedGoods.getEvent());
            tfAnimationId.setText("" + loadedGoods.getAnimationId());
            tfHp.setText("" + loadedGoods.getHp());
            tfMp.setText("" + loadedGoods.getMp());
            tfAttack.setText("" + loadedGoods.getAttack());
            tfDefend.setText("" + loadedGoods.getDefend());
            taDesc.setText("" + loadedGoods.getDesc());
        }

    }


    private boolean checkNumber(String tfText, String type) {
        if (StringUtils.isEmpty(tfText)) {
            new Alert(Alert.AlertType.ERROR, type + "不能为空").showAndWait();
            return false;
        }
        if (!StringUtils.isNumeric(tfText)) {
            new Alert(Alert.AlertType.ERROR, type + "只能是整数").showAndWait();
            return false;
        }
        int number = Integer.parseInt(tfText);
        if (number < 1) {
            new Alert(Alert.AlertType.ERROR, type + "不能小于1").showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    public void onSave(ActionEvent e) {
        boolean res = checkNumber(tfNumber.getText(), "number");
        if (!res) {
            return;
        }
        String tfNameText = tfName.getText();
        if (StringUtils.isEmpty(tfNameText)) {
            new Alert(Alert.AlertType.ERROR, "物品名称不能为空").showAndWait();
            return;
        }
        if (tfNameText.length() > 10) {
            new Alert(Alert.AlertType.ERROR, "物品名称不能大于10个字符").showAndWait();
            return;
        }
        res = checkNumber(tfBuy.getText(), "buy");
        if (!res) {
            return;
        }
        res = checkNumber(tfSell.getText(), "sell");
        if (!res) {
            return;
        }
        res = checkNumber(tfImageId.getText(), "image");
        if (!res) {
            return;
        }
        res = checkNumber(tfEvent.getText(), "event");
        if (!res) {
            return;
        }
        res = checkNumber(tfAnimationId.getText(), "动画");
        if (!res) {
            return;
        }
        res = checkNumber(tfHp.getText(), "hp");
        if (!res) {
            return;
        }
        res = checkNumber(tfMp.getText(), "mp");
        if (!res) {
            return;
        }

        String desc = taDesc.getText();
        if (StringUtils.isEmpty(desc)) {
            new Alert(Alert.AlertType.ERROR, "说明不能为空").showAndWait();
            return;
        }
        if (desc.length() > 50) {
            new Alert(Alert.AlertType.ERROR, "说明不能大于50个字符").showAndWait();
            return;
        }

        File file = saveFileChooser.showSaveDialog(null);
        if (file != null) {
            GoodsMetaData gmd = new GoodsMetaData();
            gmd.setNumber(Integer.parseInt(tfNumber.getText()));
            gmd.setType(cbType.getSelectionModel().getSelectedIndex());
            gmd.setName(tfNameText);
            gmd.setBuy(Integer.parseInt(tfBuy.getText()));
            gmd.setSell(Integer.parseInt(tfSell.getText()));
            gmd.setImageId(Integer.parseInt(tfImageId.getText()));
            gmd.setEvent(Integer.parseInt(tfEvent.getText()));
            gmd.setAnimationId(Integer.parseInt(tfAnimationId.getText()));
            gmd.setHp(Integer.parseInt(tfHp.getText()));
            gmd.setMp(Integer.parseInt(tfMp.getText()));
            gmd.setAttack(Integer.parseInt(tfAttack.getText()));
            gmd.setDefend(Integer.parseInt(tfDefend.getText()));
            gmd.setDesc(desc);
            GoodsSaverLoader.save(file.getPath(), gmd);
            LOG.debug("save to " + file.getPath());
        }
    }


    @FXML
    public void onExit(ActionEvent e) {
        LOG.debug("exit");
        Platform.exit();
    }


    private class Item {
        final int key;
        final String value;
        public Item(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
