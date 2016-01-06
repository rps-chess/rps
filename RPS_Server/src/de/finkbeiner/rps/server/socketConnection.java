
// Package muss natürlich angepasst werden
package de.finkbeiner.rps.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class socketConnection {

	ServerSocket server;
	// ArrayList<PrintWriter> list_clientWriter;
	ArrayList<ObjectOutputStream> list_clientObjectOutputStream;

	final int LEVEL_ERROR = 1;
	final int LEVEL_NORMAL = 0;

	public static void main(String[] args) {
		socketConnection s = new socketConnection();
		if (s.runServer()) {
			s.listenToClients();
		} else {
			// Do nothing
		}
	}

	public class ClientHandler implements Runnable {

		Socket client;
		// BufferedReader reader;
		ObjectInputStream objectInputStream;

		public ClientHandler(Socket client) {
			try {
				this.client = client;
				// reader = new BufferedReader(new
				// InputStreamReader(client.getInputStream()));
				objectInputStream = new ObjectInputStream(client.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			Figure figure;

			try {
				// while((nachricht = reader.readLine()) != null) {
				while (true) {
					figure = (Figure) objectInputStream.readObject();
					appendTextToConsole("Vom Client: \n" + figure.getCarriedItem(), LEVEL_NORMAL);
					sendToAllClients(figure);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void listenToClients() {
		while (true) {
			try {
				Socket client = server.accept();

				// PrintWriter writer = new
				// PrintWriter(client.getOutputStream());
				// list_clientWriter.add(objectOutputStream);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				list_clientObjectOutputStream.add(objectOutputStream);

				Thread clientThread = new Thread(new ClientHandler(client));
				clientThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean runServer() {
		try {
			server = new ServerSocket(5556);
			appendTextToConsole("Server wurde gestartet!", LEVEL_ERROR);

			// list_clientWriter = new ArrayList<PrintWriter>();
			return true;
		} catch (IOException e) {
			appendTextToConsole("Server konnte nicht gestartet werden!", LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
	}

	public void appendTextToConsole(String message, int level) {
		if (level == LEVEL_ERROR) {
			System.err.println(message + "\n");
		} else {
			System.out.println(message + "\n");
		}
	}

	public void sendToAllClients(Figure figure) {
		Iterator it = list_clientObjectOutputStream.iterator();

		while (it.hasNext()) {
			ObjectOutputStream objectOutputStream = (ObjectOutputStream) it.next();
			try {
				objectOutputStream.writeObject(figure);
				objectOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}