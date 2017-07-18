package billy.rpg.roleeditor;

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
    /*= new ChoiceBox<>(FXCollections.observableArrayList(
            new Item(1, "玩家角色"),
            new Item(2, "NPC 角色"),
            new Item(3, "妖怪角色"),
            new Item(4, "场景对象")));*/


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbType.setItems(FXCollections.observableArrayList(
                new Item(1, "玩家角色"),
                new Item(2, "NPC 角色"),
                new Item(3, "妖怪角色"),
                new Item(4, "场景对象")));
        tfNumber.setOnKeyTyped(event -> {
            int n = getNumberOfTextField(tfNumber);
            tfNumber.setText("" + n);
        });
        cbType.getSelectionModel().select(0);
    }

    private static int getNumberOfTextField(TextField textField) {
        String text = textField.getText();
        if (text == null) {
            textField.setText("");
            return 0;
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
        return number;
    }



    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }

    @FXML
    protected void save() {
        String tfNumberText = tfNumber.getText();
        if (StringUtils.isEmpty(tfNumberText)) {
            AlertBox.display("不能为空");
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
