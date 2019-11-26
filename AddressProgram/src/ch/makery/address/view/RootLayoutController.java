package ch.makery.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    private int hasSave = 0;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
    	if (hasSave == 0) {
    		boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // ... user chose OK
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
        }
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);

        hasSave = 0;
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
    	if (hasSave == 0) {
    		boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // ... user chose OK
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
        }

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }

        hasSave = 0;
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
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
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
        hasSave = 1;
    }

    /**
     * Opens an new person dialog.
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
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	mainApp.showAboutDialog();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
    	if (hasSave == 0) {

			boolean okClicked = mainApp.showFileSaveDialog();
    		if (okClicked) {
			    // ... user chose OK
				File personFile = mainApp.getPersonFilePath();

		        if (personFile != null) {
		            mainApp.savePersonDataToFile(personFile);
		        } else {
		            handleSaveAs();
		        }
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
        }
        System.exit(0);
    }
}