package hanly.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import hanly.address.MainApp;
import hanly.address.model.Person;

/**
 * �����ֵĿ��������������ṩ��
 * �����˵���������JavaFX���ڿռ��Ӧ�ó��򲼾�
 * ���Է���Ԫ��
 */
public class RootLayoutController {

    // �����������
    private MainApp mainApp;
    private int hasSave = 0;

    public void exitSave() {
    	if (hasSave == 0) {

			boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // �û�ѡ��ȷ����
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // �û�ѡ��ȡ������رնԻ���
			}
        }
    }
    /**
     * ����Ӧ�ó�������Է��������������
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * ������ͨѶ��
     */
    @FXML
    private void handleNew() {
    	if (hasSave == 0) {
    		boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // �û�ѡ��ȷ����
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // �û�ѡ��ȡ������رնԻ���
			}
        }
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);

        hasSave = 0;
    }

    /**
     * ���ļ�ѡ���������û�ѡ��Ҫ���ص�ͨѶ��
     */
    @FXML
    private void handleOpen() {
    	if (hasSave == 0) {
    		boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // �û�ѡ��ȷ����
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // �û�ѡ��ȡ������رնԻ���
			}
        }

        FileChooser fileChooser = new FileChooser();

        // ������չɸѡ��
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // ��ʾ�����ļ��Ի���
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }

        hasSave = 0;
    }

    /**
     * ���ļ����浽��ǰ�򿪵ĸ����ļ������û��
     * ���ļ�����ʾ�����Ϊ���Ի���
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
     * ���ļ�ѡ���������û�ѡ��Ҫ���浽���ļ�
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // ������չɸѡ��
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // ��ʾ�����ļ��Ի���
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // ȷ��������ȷ��extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
        hasSave = 1;
    }

    /**
     * �򿪡��½���Ա���Ի���
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
     * �򿪡����ڡ��Ի���
     */
    @FXML
    private void handleAbout() {
    	mainApp.showAboutDialog();
    }

    /**
     * �ر�Ӧ�ó���
     */
    @FXML
    private void handleExit() {
    	if (hasSave == 0) {

			boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // �û�ѡ��ȷ����
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // �û�ѡ��ȡ������رնԻ���
			}
        }
        System.exit(0);
    }
}