// Package muss natürlich angepasst werden
package de.finkbeiner.socket;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import de.finkbeiner.rps.model.Figure;

public class SocketConnection {

	Socket client;
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;


	public void startSocket() {

		if (!connectToServer()) {
			// Connect-Label anzeigen ob verbunden oder nicht...
		}

		Thread t = new Thread(new MessagesFromServerListener());
		t.start();

	}

	public boolean connectToServer() {
		try {
			client = new Socket("127.0.0.1", 5556);
			objectInputStream = new ObjectInputStream(client.getInputStream());
			objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			System.out.println("verbindung zu server hergestellt");

			return true;
		} catch (Exception e) {
			System.out.println("verbindung zu server nicht hergestellt");
			e.printStackTrace();

			return false;
		}
	}

	public void sendFigureToServer(Figure figure) throws IOException {
		objectOutputStream.writeObject(figure);
		objectOutputStream.flush();
	}

	public class MessagesFromServerListener implements Runnable {

		@Override
		public void run() {
			Figure fiugre;

			try {

				while ((fiugre = (Figure) objectInputStream.readObject()) != null) {
					System.out.println("Figur ist zurück beim Client: " + fiugre.getCarriedItem());

				}

			} catch (IOException | ClassNotFoundException e) {

				e.printStackTrace();
			}
		}

	}
}