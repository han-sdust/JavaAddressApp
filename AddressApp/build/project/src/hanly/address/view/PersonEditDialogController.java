package hanly.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import hanly.address.model.Person;
import hanly.address.util.DateUtil;

/**
 * �༭��Ա��ϸ��Ϣ�ĶԻ���
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
     * ��ʼ���������ࡣ�ڼ�����fxml�ļ�֮��˷������Զ�����
     */
    @FXML
    private void initialize() {
    }

    /**
     * ���öԻ���
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * ����Ҫ�ڶԻ����б༭����Ա
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
     * ����û�������ȷ�������򷵻�true�����򷵻�false
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * ���û�������ȷ����ʱ����
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
     * ���û�������ȡ����ʱ����
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * ��֤�ı��ֶ��е��û�����
     *
     * @return ���������Ч����Ϊtrue
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "��������Ϊ��!\n";
        }
        if (identityField.getText() == null || identityField.getText().length() == 0) {
            errorMessage += "��ݲ���Ϊ��!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "�ֵ�����Ϊ��!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "�������벻��Ϊ��!\n";
        } else {
            // ���Խ������������Ϊint
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "���������ʽ���� (����Ϊ����)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "���в���Ϊ��!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "���ղ���Ϊ��!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "���ո�ʽ���� ��ʹ��  yyyy.mm.dd ��ʽ!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // ��ʾ������Ϣ
        	Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("���ݴ���");
        	alert.setHeaderText("���������������");
        	alert.setContentText(errorMessage);
        	alert.showAndWait();
            return false;
        }
    }
}