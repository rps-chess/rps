// Package muss nat�rlich angepasst werden
// Kai ist am Start
package de.finkbeiner.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import de.finkbeiner.rps.model.Figure;
import de.finkbeiner.rps.model.StreamPackage;
import de.finkbeiner.rps.view.MainVC;

public class SocketConnection {

	Socket client;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	MainVC mainVC;

	public SocketConnection(MainVC mainVC) {
		this.mainVC = mainVC;
		startSocket();
	}

	public void startSocket() {

		if (!connectToServer()) {
			// Connect-Label anzeigen ob verbunden oder nicht...
		}

		Thread t = new Thread(new MessagesFromServerListener());
		t.start();
	}

	public boolean connectToServer() {
		try {
			client = new Socket("127.0.0.1", 5555);
			objectInputStream = new ObjectInputStream(client.getInputStream());
			objectOutputStream = new ObjectOutputStream(
					client.getOutputStream());
			// MainVC mainVC = new MainVC();
			// mainVC.writeMessage("Netzwerkverbindung hergestellt");

			return true;
		} catch (Exception e) {
			// MainVC mainVC = new MainVC();
			// mainVC.writeMessage("Netzwerkverbindung konnte nicht hergestellt
			// werden");
			// e.printStackTrace();

			return false;
		}
	}

	public void sendMessageToServer(String message) {
		StreamPackage streamPackage = new StreamPackage();

		try {
			streamPackage.setMessage(message);
			objectOutputStream.writeObject(streamPackage);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// textField_ClientMessage.setText("");
		// textField_ClientMessage.requestFocus();
	}

	public void sendFigureToServer(Figure figure) {
		StreamPackage streamPackage = new StreamPackage();

		try {
			streamPackage.setFigure(figure);
			objectOutputStream.writeObject(streamPackage);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// textField_ClientMessage.setText("");
		// textField_ClientMessage.requestFocus();
	}

	// // Listener
	// public class SendPressEnterListener implements KeyListener {
	// @Override
	// public void keyPressed(KeyEvent arg0) {
	// if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
	// Figure figure = new Figure();
	// figure.setCarriedItem(textField_ClientMessage.getText());
	// String message= textField_ClientMessage.getText();
	// sendFigureToServer(figure);
	// sendMessageToServer(message);
	//
	// }
	// }
	//
	// @Override
	// public void keyReleased(KeyEvent arg0) {
	// }
	//
	// @Override
	// public void keyTyped(KeyEvent arg0) {
	// }
	//
	// }
	//
	// public class SendButtonListener implements ActionListener {
	//
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// }
	//
	// }

	public class MessagesFromServerListener implements Runnable {

		@Override
		public void run() {

			String message;
			Figure figure;
			StreamPackage streamPackage;
			// MainVC mainVC = new MainVC();
			try {

				while ((streamPackage = (StreamPackage) objectInputStream
						.readObject()) != null) {

					if ((message = streamPackage.getMessage()) != null) {
						mainVC.writeMessage(message);
						// textArea_Messages.setCaretPosition(textArea_Messages.getText().length());
					}

					if ((figure = streamPackage.getFigure()) != null) {
						// mainVC.incomingFigure(figure);

					}
				}

			} catch (IOException | ClassNotFoundException e) {
				// mainVC.writeMessage("Nachricht konnte nicht empfangen
				// werden!");
				e.printStackTrace();
			}
		}

	}
}
//// Package muss nat�rlich angepasst werden
// package de.finkbeiner.socket;
//
//
// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
// import java.net.Socket;
// import de.finkbeiner.rps.model.Figure;
//
// public class SocketConnection {
//
// Socket client;
// ObjectInputStream objectInputStream;
// ObjectOutputStream objectOutputStream;
//
//
// public void startSocket() {
//
// if (!connectToServer()) {
// // Connect-Label anzeigen ob verbunden oder nicht...
// }
//
// Thread t = new Thread(new MessagesFromServerListener());
// t.start();
//
// }
//
// public boolean connectToServer() {
// try {
// client = new Socket("127.0.0.1", 5556);
// objectInputStream = new ObjectInputStream(client.getInputStream());
// objectOutputStream = new ObjectOutputStream(client.getOutputStream());
// System.out.println("verbindung zu server hergestellt");
//
// return true;
// } catch (Exception e) {
// System.out.println("verbindung zu server nicht hergestellt");
// e.printStackTrace();
//
// return false;
// }
// }
//
// public void sendFigureToServer(Figure figure) throws IOException {
// objectOutputStream.writeObject(figure);
// objectOutputStream.flush();
// }
//
// public class MessagesFromServerListener implements Runnable {
//
// @Override
// public void run() {
// Figure fiugre;
//
// try {
//
// while ((fiugre = (Figure) objectInputStream.readObject()) != null) {
// System.out.println("Figur ist zur�ck beim Client: " +
//// fiugre.getCarriedItem());
//
// }
//
// } catch (IOException | ClassNotFoundException e) {
//
// e.printStackTrace();
// }
// }
//
// }
// }