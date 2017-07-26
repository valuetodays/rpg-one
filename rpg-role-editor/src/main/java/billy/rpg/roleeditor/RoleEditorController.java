package billy.rpg.roleeditor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author liulei@bshf360.com
 * @since 2017-07-13 15:37
 */
public class RoleEditorController implements Initializable {
    private static final Logger LOG = Logger.getLogger(RoleEditorController.class);

    @FXML
    private Text actiontarget;
    @FXML
    private TextField tfNumber;
    @FXML
    private TextField tfName;
    @FXML
    private ChoiceBox<Item> cbType;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbType.setItems(FXCollections.observableArrayList(
                new Item(1, "玩家角色"),
                new Item(2, "NPC 角色"),
                new Item(3, "妖怪角色"),
                new Item(4, "场景对象")));
        cbType.getSelectionModel().select(0);

        tfNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    String s = newValue.replaceAll("[^\\d]", "");
                    tfNumber.setText(Integer.parseInt(s) + "");
                }
                // remove the leading '0'
                String oriText = tfNumber.getText();
                tfNumber.setText("" + Integer.parseInt(oriText));
            }
        });

    }

    private void ensureNumber(TextField textField) {
        String text = textField.getText();
        if (text == null) {
            textField.setText("");
            return;
        }
        if (text.startsWith("-")) {
            text = text.substring(1);
        }

        int n = 0;
        for (; n < text.length(); n++) {
            char ch = text.charAt(n);
            if (!Character.isDigit(ch)) {
                break;
            }
        }
        LOG.debug("n=" + n);
        n = Math.max(0, n);
        String numberText = text.substring(0, n);
        LOG.debug("numberText:" + numberText);
        if (StringUtils.EMPTY.equals(numberText)) {
            numberText = "0";
        }
        int number = Integer.parseInt(numberText);
        tfNumber.setText("" + number);
        tfNumber.positionCaret(tfNumber.getLength());
        return;
    }



    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }

    @FXML
    protected void save() {
        String tfNumberText = tfNumber.getText();
        if (StringUtils.isEmpty(tfNumberText)) {
            AlertBox.alert("不能为空");
            return;
        }
        LOG.debug("number=" + tfNumberText);

        return;
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
