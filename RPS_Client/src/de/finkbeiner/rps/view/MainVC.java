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
	SocketConnection socketConnection = new SocketConnection(this);
	/*
	 * TODO Namensgebung unglücklich, zwei Klassen mit gleichem Namen SocketConnection und socketConnection
	 */

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
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
		/*
		 * TODO Abklären! Warum wird hier der Startpoint übergeben?
		 * Wird in der Klasse nicht mehr verwendet + kein Getter vorhanden.
		 */

	}

	@FXML
	private void handleSendMessage() {
		String message = chatInputAreaTxA.getText();
//		chatDisplayAreaTxA.setText(chatDisplayAreaTxA.getText() + "\n" + message);
		
		/*
		 * TODO hier Abfrage ob eine Eingabe getätigt wurde. 
		 * Ansonsten kann mit Enter immer ein NULL-Wert versendet werden!
		 * Zusätzlich, bei schnellem Senden hintereinander wird die Socket-Verbindung reseted.
		 */
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

//	 public void test(){
//	 chatDisplayAreaTxA.setText(chatDisplayAreaTxA.getText() + "\n" +
//	 "Defult");
//	 }


		@FXML
		public void writeMessage(String message){
			System.out.println("MEssage Client angekommen: "+message);
			/*
			 * TODO Warum wird hier eine Leerzeile in das Textfeld geschrieben?
			 */
		chatDisplayAreaTxA.setText(chatDisplayAreaTxA.getText() + "\n" + message);
		
		
	}
	public void incomingFigure(Figure figure){
		chatDisplayAreaTxA.setText(chatDisplayAreaTxA.getText() + "\n" + figure.getCarriedItem());
	}
}