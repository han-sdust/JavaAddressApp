package hanly.address.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;


/**
 * ��ʾɾ���ĶԻ���
 */
public class PersonDeleteConfirmDialogController {

    private Stage dialogStage;
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
    	okClicked = true;
    	dialogStage.close();
    }

    /**
     * ���û�������ȡ����ʱ����
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
