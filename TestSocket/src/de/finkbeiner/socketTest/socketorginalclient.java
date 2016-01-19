// Package muss natürlich angepasst werden
package de.finkbeiner.socketTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class socketorginalclient {

	JFrame clientFrame;
	JPanel clientPanel;
	JTextArea textArea_Messages;
	JTextField textField_ClientMessage;
	JButton button_SendMessage;
	JTextField textField_Username;
	JScrollPane scrollPane_Messages;

	Socket client;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;

	public static void main(String[] args) {
		socketorginalclient c = new socketorginalclient();
		c.createGUI();
	}

	public void createGUI() {
		clientFrame = new JFrame("TutCubeDE-Chat");
		clientFrame.setSize(800, 600);

		// Panel erzeugen, welches alle anderen Inhalte enthält
		clientPanel = new JPanel();

		textArea_Messages = new JTextArea();
		textArea_Messages.setEditable(false);

		textField_ClientMessage = new JTextField(38);
		textField_ClientMessage.addKeyListener(new SendPressEnterListener());

		button_SendMessage = new JButton("Senden");
		button_SendMessage.addActionListener(new SendButtonListener());

		textField_Username = new JTextField(10);

		// Scrollbalken zur textArea hinzufügen
		scrollPane_Messages = new JScrollPane(textArea_Messages);
		scrollPane_Messages.setPreferredSize(new Dimension(700, 500));
		scrollPane_Messages.setMinimumSize(new Dimension(700, 500));
		scrollPane_Messages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_Messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		if (!connectToServer()) {
			// Connect-Label anzeigen ob verbunden oder nicht...
		}

		Thread t = new Thread(new MessagesFromServerListener());
		t.start();

		clientPanel.add(scrollPane_Messages);
		clientPanel.add(textField_Username);
		clientPanel.add(textField_ClientMessage);
		clientPanel.add(button_SendMessage);

		// Panel zum ContentPane (Inhaltsbereich) hinzufügen
		clientFrame.getContentPane().add(BorderLayout.CENTER, clientPanel);

		clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientFrame.setVisible(true);
	}

	public boolean connectToServer() {
		try {
			client = new Socket("127.0.0.1", 5555);
			objectInputStream = new ObjectInputStream(client.getInputStream());
			objectOutputStream = new ObjectOutputStream(client.getOutputStream());
			appendTextMessages("Netzwerkverbindung hergestellt");

			return true;
		} catch (Exception e) {
			appendTextMessages("Netzwerkverbindung konnte nicht hergestellt werden");
			e.printStackTrace();

			return false;
		}
	}

	public void sendMessageToServer(String message) {
		StreamPackage streamPackage = new StreamPackage();
		

		try {
			message = textField_ClientMessage.getText();
			streamPackage.setMessage(message);
			objectOutputStream.writeObject(streamPackage);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		textField_ClientMessage.setText("");
		textField_ClientMessage.requestFocus();
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

//		textField_ClientMessage.setText("");
		textField_ClientMessage.requestFocus();
	}

	public void appendTextMessages(String message) {
		textArea_Messages.append(message + "\n");
	}

	// Listener
	public class SendPressEnterListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				Figure figure = new Figure();
				figure.setCarriedItem(textField_ClientMessage.getText());
				String message= textField_ClientMessage.getText();
				sendFigureToServer(figure);
				sendMessageToServer(message);

			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}

	public class SendButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		}

	}

	public class MessagesFromServerListener implements Runnable {

		@Override
		public void run() {

			String message;
			Figure figure;
			StreamPackage StreamPackage;

			try {

				while ((StreamPackage = (StreamPackage) objectInputStream.readObject()) != null) {

					if ((message = StreamPackage.getMessage()) != null) {
						appendTextMessages(message);
						textArea_Messages.setCaretPosition(textArea_Messages.getText().length());
					}

					if ((figure = StreamPackage.getFigure()) != null) {
						appendTextMessages("Figure: " + figure.getCarriedItem());
						textArea_Messages.setCaretPosition(textArea_Messages.getText().length());
					}
				}

			} catch (IOException | ClassNotFoundException e) {
				appendTextMessages("Nachricht konnte nicht empfangen werden!");
				e.printStackTrace();
			}
		}

	}
}