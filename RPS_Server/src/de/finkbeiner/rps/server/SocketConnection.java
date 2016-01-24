// Package muss natürlich angepasst werden
package de.finkbeiner.rps.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import de.finkbeiner.rps.model.Figure;
import de.finkbeiner.rps.model.StreamPackage;


public class SocketConnection {

	ServerSocket server;
	// ArrayList<PrintWriter> list_clientWriter;
	ArrayList<ObjectOutputStream> list_clientObjectOutputStream;
//	ArrayList<ClientInfo> clientList;
	final int LEVEL_ERROR = 1;
	final int LEVEL_NORMAL = 0;

	public static void main(String[] args) {
		SocketConnection socketConnection = new SocketConnection();
		if (socketConnection.runServer()) {
			socketConnection.listenToClients();
		} else {
			// Do nothing
		}
	}

	public void listenToClients() {
		while (true) {
			try {
				Socket client = server.accept();
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
			server = new ServerSocket(5555);
			appendTextToConsole("Server wurde gestartet!", LEVEL_ERROR);

			list_clientObjectOutputStream = new ArrayList<ObjectOutputStream>();
			return true;
		} catch (IOException e) {
			appendTextToConsole("Server konnte nicht gestartet werden!", LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
	}
	
	public class ClientHandler implements Runnable {

		Socket client;
		ObjectInputStream objectInputStream;
//		ClientInfo clientInfo;



		public ClientHandler(Socket client) {
			try {
				this.client = client;
				objectInputStream = new ObjectInputStream(client.getInputStream());
				
				System.out.println(client.toString());
//				clientInfo.setInetAddress(client.getInetAddress());
//				clientList.add(clientInfo);


			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			String message;
			Figure figure;
			StreamPackage streamPackage;
			try {
				while ((streamPackage = (StreamPackage) objectInputStream.readObject()) != null) {

					if ((message = streamPackage.getMessage()) != null) {
						appendTextToConsole("Vom Client: \n" + message, LEVEL_NORMAL);
						sendToAllClientsMessage(message);
					}

					if ((figure = streamPackage.getFigure()) != null) {
						appendTextToConsole("Vom Client Figure: \n" + figure.getCarriedItem(), LEVEL_NORMAL);
						sendToAllClientsFigure(figure);
					}
//					if ((clientInfo = streamPackage.getClientInfo()) != null) {
//						appendTextToConsole("Vom Client ClientInfo: \n" + figure.getCarriedItem(), LEVEL_NORMAL);
//						clientInfo.getClientname();
//						sendToAllClientsClientList(clientList);
//						
//					}

				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}



	public void appendTextToConsole(String message, int level) {
		if (level == LEVEL_ERROR) {
			System.err.println(message + "\n");
		} else {
			System.out.println(message + "\n");
		}
	}

	public void sendToAllClientsMessage(String message) {
		Iterator it = list_clientObjectOutputStream.iterator();
		StreamPackage streamPackage = new StreamPackage();
		
		while (it.hasNext()) {
			ObjectOutputStream objectOutputStream = (ObjectOutputStream) it.next();
			try {
				streamPackage.setMessage(message);
				objectOutputStream.writeObject(streamPackage);
				objectOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void sendToAllClientsFigure(Figure figure) {
		Iterator it = list_clientObjectOutputStream.iterator();
		StreamPackage streamPackage = new StreamPackage();
		
		while (it.hasNext()) {
			ObjectOutputStream objectOutputStream = (ObjectOutputStream) it.next();
			try {
				streamPackage.setFigure(figure);
				objectOutputStream.writeObject(streamPackage);
				objectOutputStream.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}
//	public void sendToAllClientsClientList(ArrayList<ClientInfo> clientList) {
//		Iterator it = list_clientObjectOutputStream.iterator();
//		StreamPackage streamPackage = new StreamPackage();
//		
//		while (it.hasNext()) {
//			ObjectOutputStream objectOutputStream = (ObjectOutputStream) it.next();
//			try {
//				streamPackage.setFigure(figure);
//				objectOutputStream.writeObject(streamPackage);
//				objectOutputStream.flush();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}