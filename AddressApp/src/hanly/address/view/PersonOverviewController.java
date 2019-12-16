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

    // ����Ӧ�ó��������
    private MainApp mainApp;

    /**
     * ������
     * ��initialize��������֮ǰ���ù��캯��
     */
    public PersonOverviewController() {
    }

    /**
     * ��ʼ���������࣬�ڼ�����fxml�ļ�֮��˷������Զ�����
     */
    @FXML
    private void initialize() {
        // �������г�ʼ��person��
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        identityColumn.setCellValueFactory(cellData -> cellData.getValue().identityProperty());

        // ���������ϸ��Ϣ
        showPersonDetails(null);

        // �������Ĳ��ڸ���ʱ������ʾ��Ա��ϸ��Ϣ
        personTable.getSelectionModel().selectedItemProperty().addListener(
        		(observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * ��������ı��ֶ�����ʾ�йش��˵���ϸ��Ϣ��
     * ���ָ������ԱΪ�գ�����������ı��ֶ�
     *
     * @param person ָ����Ա �� null
     */
    public void showPersonDetails(Person person) {
        if (person != null) {
            // ��person�����е���Ϣ����ǩ
            nameLabel.setText(person.getName());
            identityLabel.setText(person.getIdentity());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            //�����ձ��һ���ַ�����
            Image image = new Image(person.getImage());
            personImage.setImage(image);

        } else {
            // PersonΪ�գ�ɾ�������ı�
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
     * ���û�������ɾ������ťʱ����
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
     * ���û��������½�����ťʱ���á���Ҫ�༭����Ա����ϸ��Ϣ�ĶԻ���
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
     * ���û������༭��ťʱ���á���Ҫ�༭��ѡ��Ա����ϸ��Ϣ�ĶԻ���
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
     * ���û�����ͼƬʱ���á���Ҫ�༭��ѡ��Ա��ͼƬ�ĶԻ���
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
     * Ϊ������������PersonTable
     */
    @FXML
    public TableView<Person> getPersonTable() {
    	return personTable;
    }

    /**
     * ����Ӧ�ó�������Է��������������
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // ���ɹ۲��б�����observable��ӵ�����
        personTable.setItems(mainApp.getPersonData());
    }
}