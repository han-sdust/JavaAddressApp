package hanly.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import hanly.address.MainApp;
import hanly.address.model.Person;
import hanly.address.util.DateUtil;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> identityColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label identityLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    @FXML
    private ImageView personImage;

    // 对主应用程序的引用
    private MainApp mainApp;

    /**
     * 构造器
     * 在initialize（）方法之前调用构造函数
     */
    public PersonOverviewController() {
    }

    /**
     * 初始化控制器类，在加载了fxml文件之后此方法将自动调用
     */
    @FXML
    private void initialize() {
        // 用这两列初始化person表
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        identityColumn.setCellValueFactory(cellData -> cellData.getValue().identityProperty());

        // 清除个人详细信息
        showPersonDetails(null);

        // 监听更改并在更改时更新显示人员详细信息
        personTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * 填充所有文本字段以显示有关此人的详细信息。
     * 如果指定的人员为空，则清除所有文本字段
     *
     * @param person 指定人员 或 null
     */
    public void showPersonDetails(Person person) {
        if (person != null) {
            // 用person对象中的信息填充标签
            nameLabel.setText(person.getName());
            identityLabel.setText(person.getIdentity());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            //把生日变成一个字符串！
            Image image = new Image(person.getImage());
            personImage.setImage(image);

        } else {
            // Person为空，删除所有文本
            nameLabel.setText("");
            identityLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
            Image image = new Image("file:resources/images/person.png");
            personImage.setImage(image);
        }
    }

    /**
     * 当用户单击“删除”按钮时调用
     */
    @FXML
    public void handleDeletePerson() {

    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
    	if(selectedIndex>=0) {
    		boolean okClicked = mainApp.showPersonDeleteConfirmDialog();
    		if (okClicked)
            	personTable.getItems().remove(selectedIndex);
    	}
    }

    /**
     * 当用户单击“新建”按钮时调用。打开要编辑新人员的详细信息的对话框
     */
    @FXML
    public void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * 当用户单击编辑按钮时调用。打开要编辑所选人员的详细信息的对话框
     */
    @FXML
    public void handleEditPerson() {
    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
    	if(selectedIndex>=0) {
	        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
	        if (selectedPerson != null) {
	            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
	            if (okClicked) {
	                showPersonDetails(selectedPerson);
	            }
	        }
    	}
    }

    /**
     * 当用户单击图片时调用。打开要编辑所选人员的图片的对话框
     */
    @FXML
    public void handleImage() {
    	int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
    	if(selectedIndex>=0) {
	        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
	        if (selectedPerson != null) {
	        	String imagePath = mainApp.getImage();
	        	selectedPerson.setImage(imagePath);
	        	showPersonDetails(selectedPerson);
	        }
    	}
    }


    /**
     * 为其它函数调用PersonTable
     */
    @FXML
    public TableView<Person> getPersonTable() {
    	return personTable;
    }

    /**
     * 由主应用程序调用以返回其自身的引用
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 将可观察列表数据observable添加到表中
        personTable.setItems(mainApp.getPersonData());
    }
}