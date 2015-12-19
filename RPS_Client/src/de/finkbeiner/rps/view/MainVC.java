package de.finkbeiner.rps.view;

import de.finkbeiner.rps.Startpoint;
import de.finkbeiner.rps.model.DataBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class MainVC {
	   @FXML
	    private TextArea chatDisplayAreaTxA;
	   @FXML
	    private TextArea chatInputAreaTxA; 
	   @FXML
	    private Button chatSendBtn;


	    // Reference to the main application.
	    private Startpoint startpoint;

	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public MainVC() {
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	        // Initialize the person table with the two columns.

	    }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(Startpoint startpoint) {
	        this.startpoint = startpoint;


	    }
	}