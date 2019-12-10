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
     * 设置一个成员类的可视化列表
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * 程序主体
     */
    public MainApp() {
        // 添加样例
        personData.add(new Person("张三", "教师"));
        personData.add(new Person("李四", "程序员"));
    }

    /**
     * 返回一个成员类的可视化列表
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("联系人程序");
        this.primaryStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));



        initRootLayout();

        showPersonOverview();
	}

    /**
     * 初始化根框架并尝试加载最后打开的用户文件
     */
    public void initRootLayout() {
        try {
        	// 加载根框架的fxml文件
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // 限时根框架的窗口容器
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            // 载入主程序控制器
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

            // 监听系统事件，当程序退出时执行保存操作
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                	controller.exitSave();
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 尝试加载最后打开的用户文件
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * 在根框架中加载成员展示界面
     */
    public void showPersonOverview() {
    	try {
            // 加载成员展示界面
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // 使成员展示界面在根框架中心
            rootLayout.setCenter(personOverview);

            // 载入主程序控制器
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开一个编辑成员信息对话框，如果用户点击OK就将改动保存到成员类，并返回true
     *
     * @param person 编辑成员类中的成员
     * @return 如果用户点击OK返回true, 否则返回false
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // 加载fxml文件并创建一个新窗口给弹出窗口使用
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // 创建对话窗口
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("编辑内容");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 加载成员编辑对话框控制器
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // 打开窗口直到用户关闭
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 用户试图删除一个成员时打开一个提示窗口，如果用户点击OK将改动保存到成员列表，并返回true
     *
     * @return 如果用户点击OK返回true, 否则返回false
     */
    public boolean showPersonDeleteConfirmDialog() {
        try {
            // 加载fxml文件并创建一个新窗口给弹出窗口使用
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonDeleteConfirmDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // 创建对话窗口
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("警告");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 加载成员删除对话框控制器
            PersonDeleteConfirmDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // 打开窗口直到用户关闭
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 打开一个对话框提示用户保存文件，如果用户点击OK，保存到xml文件，并返回true
     *
     * @param 编辑xml文件
     * @return 如果用户点击OK返回true, 否则返回false
     */
    public boolean showFileSaveDialog() {
        try {
            // 加载fxml文件并创建一个新窗口给弹出窗口使用
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileSaveDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // 创建对话窗口
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("提示");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 加载文件保存对话框控制器
            FileSaveDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // 打开窗口直到用户关闭
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 打开关于窗口，如果用户点击OK，返回true
     *
     * @return 如果用户点击OK返回true, 否则返回false
     */
    public boolean showAboutDialog() {
        try {
            // 加载fxml文件并创建一个新窗口给弹出窗口使用
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AboutDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // 创建对话窗口
            Stage dialogStage = new Stage();
            dialogStage.getIcons().add(new Image("file:resources/images/iconAddressBook.png"));
            dialogStage.setTitle("关于");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // 加载文件保存对话框控制器
            FileSaveDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // 打开窗口直到用户关闭
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 返回用户文件设置，文件为用户最后打开的，设置从操作系统特定注册表读取，如果没有用户文件，返回null
     *
     * @return 如果用户点击OK返回true, 否则返回false
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
     * 为当前用户文件设置路径，路径维持在操作系统特定注册表中
     *
     * @param file 用户文件或者无路径
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // 更新窗口标题
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // 更新窗口标题
            primaryStage.setTitle("AddressApp");
        }
    }


    /**
     * 从特定文件加载用户数据，并覆盖当前数据
     *
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // 从文件读取XML数据并解包
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // 保存路径到注册表
            setPersonFilePath(file);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("错误");
        	alert.setHeaderText("无法读取文件内容:\n" + file.getPath());
        	alert.showAndWait();
        }
    }


    /**
     * 保存当前数据到特定用户文件
     *
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // 包装成员数据
            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            // 把成员数据包变换为适合存储或发送的数据格式XML并保存到文件
            m.marshal(wrapper, file);

            // 保存路径到注册表
            setPersonFilePath(file);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
        	alert.setTitle("错误");
        	alert.setHeaderText("无法保存内容到文件:\n" + file.getPath());
        	alert.showAndWait();
        }
    }

    /**
     * 返回主窗口
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	public static void main(String[] args) {
		launch(args);
	}
}
