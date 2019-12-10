package hanly.address;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import hanly.address.model.Person;
import hanly.address.model.PersonListWrapper;
import hanly.address.view.FileSaveDialogController;
import hanly.address.view.PersonDeleteConfirmDialogController;
import hanly.address.view.PersonEditDialogController;
import hanly.address.view.PersonOverviewController;
import hanly.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * ����һ����Ա��Ŀ��ӻ��б�
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * ��������
     */
    public MainApp() {
        // �������
        personData.add(new Person("����", "��ʦ"));
        personData.add(new Person("����", "����Ա"));
    }

    /**
     * ����һ����Ա��Ŀ��ӻ��б�
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("��ϵ�˳���");
        this.primaryStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));



        initRootLayout();

        showPersonOverview();
	}

    /**
     * ��ʼ������ܲ����Լ������򿪵��û��ļ�
     */
    public void initRootLayout() {
        try {
        	// ���ظ���ܵ�fxml�ļ�
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // ��ʱ����ܵĴ�������
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            // ���������������
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

            // ����ϵͳ�¼����������˳�ʱִ�б������
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                	controller.exitSave();
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ���Լ������򿪵��û��ļ�
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * �ڸ�����м��س�Աչʾ����
     */
    public void showPersonOverview() {
    	try {
            // ���س�Աչʾ����
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // ʹ��Աչʾ�����ڸ��������
            rootLayout.setCenter(personOverview);

            // ���������������
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��һ���༭��Ա��Ϣ�Ի�������û����OK�ͽ��Ķ����浽��Ա�࣬������true
     *
     * @param person �༭��Ա���еĳ�Ա
     * @return ����û����OK����true, ���򷵻�false
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // ����fxml�ļ�������һ���´��ڸ���������ʹ��
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // �����Ի�����
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("�༭����");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // ���س�Ա�༭�Ի��������
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // �򿪴���ֱ���û��ر�
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * �û���ͼɾ��һ����Աʱ��һ����ʾ���ڣ�����û����OK���Ķ����浽��Ա�б�������true
     *
     * @return ����û����OK����true, ���򷵻�false
     */
    public boolean showPersonDeleteConfirmDialog() {
        try {
            // ����fxml�ļ�������һ���´��ڸ���������ʹ��
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonDeleteConfirmDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // �����Ի�����
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("����");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // ���س�Աɾ���Ի��������
            PersonDeleteConfirmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // �򿪴���ֱ���û��ر�
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * ��һ���Ի�����ʾ�û������ļ�������û����OK�����浽xml�ļ���������true
     *
     * @param �༭xml�ļ�
     * @return ����û����OK����true, ���򷵻�false
     */
    public boolean showFileSaveDialog() {
        try {
            // ����fxml�ļ�������һ���´��ڸ���������ʹ��
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileSaveDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // �����Ի�����
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("��ʾ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // �����ļ�����Ի��������
            FileSaveDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // �򿪴���ֱ���û��ر�
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * �򿪹��ڴ��ڣ�����û����OK������true
     *
     * @return ����û����OK����true, ���򷵻�false
     */
    public boolean showAboutDialog() {
        try {
            // ����fxml�ļ�������һ���´��ڸ���������ʹ��
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AboutDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // �����Ի�����
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("����");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // �����ļ�����Ի��������
            FileSaveDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // �򿪴���ֱ���û��ر�
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * �����û��ļ����ã��ļ�Ϊ�û����򿪵ģ����ôӲ���ϵͳ�ض�ע����ȡ�����û���û��ļ�������null
     *
     * @return ����û����OK����true, ���򷵻�false
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }


    /**
     * Ϊ��ǰ�û��ļ�����·����·��ά���ڲ���ϵͳ�ض�ע�����
     *
     * @param file �û��ļ�������·��
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // ���´��ڱ���
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // ���´��ڱ���
            primaryStage.setTitle("AddressApp");
        }
    }


    /**
     * ���ض��ļ������û����ݣ������ǵ�ǰ����
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // ���ļ���ȡXML���ݲ����
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // ����·����ע���
            setPersonFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("����");
        	alert.setHeaderText("�޷���ȡ�ļ�����:\n" + file.getPath());
        	alert.showAndWait();
        }
    }


    /**
     * ���浱ǰ���ݵ��ض��û��ļ�
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // ��װ��Ա����
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // �ѳ�Ա���ݰ��任Ϊ�ʺϴ洢���͵����ݸ�ʽXML�����浽�ļ�
            m.marshal(wrapper, file);

            // ����·����ע���
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("����");
        	alert.setHeaderText("�޷��������ݵ��ļ�:\n" + file.getPath());
        	alert.showAndWait();
        }
    }

    /**
     * ����������
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	public static void main(String[] args) {
		launch(args);
	}
}
