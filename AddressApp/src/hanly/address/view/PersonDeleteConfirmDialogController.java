package hanly.address.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;


/**
 * 提示删除的对话框
 */
public class PersonDeleteConfirmDialogController {

    private Stage dialogStage;
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
    	okClicked = true;
    	dialogStage.close();
    }

    /**
     * 当用户单击“取消”时调用
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
