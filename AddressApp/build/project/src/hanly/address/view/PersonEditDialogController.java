package hanly.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import hanly.address.model.Person;
import hanly.address.util.DateUtil;

/**
 * 编辑人员详细信息的对话框
 */
public class PersonEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField identityField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * 初始化控制器类。在加载了fxml文件之后此方法将自动调用
     */
    @FXML
    private void initialize() {
    }

    /**
     * 设置对话框
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * 设置要在对话框中编辑的人员
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        nameField.setText(person.getName());
        identityField.setText(person.getIdentity());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("yyyy.mm.dd");
    }

    /**
     * 如果用户单击“确定”，则返回true，否则返回false
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * 当用户单击“确定”时调用
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setName(nameField.getText());
            person.setIdentity(identityField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * 当用户单击“取消”时调用
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * 验证文本字段中的用户输入
     *
     * @return 如果输入有效，则为true
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "姓名不能为空!\n";
        }
        if (identityField.getText() == null || identityField.getText().length() == 0) {
            errorMessage += "身份不能为空!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "街道不能为空!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "邮政编码不能为空!\n";
        } else {
            // 尝试将邮政编码解析为int
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "邮政编码格式错误 (必须为数字)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "城市不能为空!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "生日不能为空!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "生日格式错误。 请使用  yyyy.mm.dd 格式!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // 显示错误信息
        	Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("内容错误");
        	alert.setHeaderText("请修正错误的内容");
        	alert.setContentText(errorMessage);
        	alert.showAndWait();
            return false;
        }
    }
}