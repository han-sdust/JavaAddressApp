package hanly.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import hanly.address.MainApp;
import hanly.address.model.Person;

/**
 * 根布局的控制器。根布局提供了
 * 包含菜单栏和其他JavaFX所在空间的应用程序布局
 * 可以放置元素
 */
public class RootLayoutController {

    // 主程序的引用
    private MainApp mainApp;
    private int hasSave = 0;

    public void exitSave() {
    	if (hasSave == 0) {

			boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // 用户选择“确定”
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // 用户选择“取消”或关闭对话框
			}
        }
    }
    /**
     * 由主应用程序调用以返回其自身的引用
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * 创建空通讯簿
     */
    @FXML
    private void handleNew() {
    	if (hasSave == 0) {
    		boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // 用户选择“确定”
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // 用户选择“取消”或关闭对话框
			}
        }
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);

        hasSave = 0;
    }

    /**
     * 打开文件选择器，让用户选择要加载的通讯簿
     */
    @FXML
    private void handleOpen() {
    	if (hasSave == 0) {
    		boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // 用户选择“确定”
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // 用户选择“取消”或关闭对话框
			}
        }

        FileChooser fileChooser = new FileChooser();

        // 设置扩展筛选器
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // 显示保存文件对话框
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }

        hasSave = 0;
    }

    /**
     * 将文件保存到当前打开的个人文件。如果没有
     * 打开文件，显示“另存为”对话框。
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
        hasSave = 1;
    }

    /**
     * 打开文件选择器，让用户选择要保存到的文件
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // 设置扩展筛选器
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // 显示保存文件对话框
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // 确保它有正确的extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
        hasSave = 1;
    }

    /**
     * 打开“新建人员”对话框
     */
    @FXML
    private void newPerson() {
    	Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * 打开“关于”对话框
     */
    @FXML
    private void handleAbout() {
    	mainApp.showAboutDialog();
    }

    /**
     * 关闭应用程序
     */
    @FXML
    private void handleExit() {
    	if (hasSave == 0) {

			boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // 用户选择“确定”
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // 用户选择“取消”或关闭对话框
			}
        }
        System.exit(0);
    }
}