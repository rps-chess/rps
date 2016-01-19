package de.finkbeiner.rps.view;

import java.io.IOException;

import de.finkbeiner.rps.Startpoint;
import de.finkbeiner.rps.model.DataBean;
import de.finkbeiner.rps.model.Figure;
import de.finkbeiner.socket.SocketConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class MainVC {
	@FXML
	private TextArea chatDisplayAreaTxA;
	@FXML
	private TextArea chatInputAreaTxA;
	@FXML
	private Button chatSendBtn;

	Figure figure;

	// Reference to the main application.
	private Startpoint startpoint;
	SocketConnection socketConnection = new SocketConnection();

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public MainVC() {

		socketConnection.startSocket();

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

	@FXML
	private void handleSendMessage() {
		String message = chatInputAreaTxA.getText();

		socketConnection.sendMessageToServer(message);
//		figure.setCarriedItem(message);
//		socketConnection.sendFigureToServer(figure);


		chatInputAreaTxA.clear();
		
	}

	@FXML
	public void handleEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			handleSendMessage();
		}
	}
	public void writeMessage(String message){
		chatDisplayAreaTxA.setText(chatDisplayAreaTxA.getText() + "\n" + message);
	}
	public void incomingFigure(Figure figure){
		chatDisplayAreaTxA.setText(chatDisplayAreaTxA.getText() + "\n" + figure.getCarriedItem());
	}
}