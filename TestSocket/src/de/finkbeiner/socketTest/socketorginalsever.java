// Package muss natürlich angepasst werden
package de.finkbeiner.socketTest;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
 
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;


 
public class socketorginalsever {
 
        ServerSocket server;
//        ArrayList<PrintWriter> list_clientWriter;
        ArrayList<ObjectOutputStream> list_clientObjectOutputStream;
       
        final int LEVEL_ERROR = 1;
        final int LEVEL_NORMAL = 0;
 
        public static void main(String[] args) {
                socketorginalsever s = new socketorginalsever();
                if (s.runServer()) {
                        s.listenToClients();
                } else {
                        // Do nothing
                }
        }
       
        public class ClientHandler implements Runnable {
 
                Socket client;
//                BufferedReader reader;
        		ObjectInputStream objectInputStream;
        		
                public ClientHandler(Socket client) {
                        try {
                                this.client = client;
//                                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                                	objectInputStream = new ObjectInputStream(client.getInputStream());
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
               
                @Override
                public void run() {
                    String message = "";
                    Figure figure;
                    Object readedObject;
                    StreamPackage streamPackage;
                    try {
                    	while((streamPackage = (StreamPackage) objectInputStream.readObject()) != null){
                    		if (streamPackage.getMessage()!= null) {
                    			message = streamPackage.getMessage();
                               appendTextToConsole("Vom Client: \n" + message, LEVEL_NORMAL);
                               sendToAllClientsMessage(message);	
								
							}
//                    		if(readedObject instanceof String)
//                    		{
//                    			 nachricht = (String) readedObject;
//                                 appendTextToConsole("Vom Client: \n" + nachricht, LEVEL_NORMAL);
//                                 sendToAllClientsMessage(nachricht);
//                    		}
//                    		if (readedObject instanceof Figure) {
//								figure = (Figure) readedObject;
//								System.out.println(figure.getCarriedItem()+"was beim Server ankommt");
//								sendToAllClientsFigure(figure);
//							}
                    	

                            }
                    } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                    }
            }
//                public void run() {
//                    String nachricht;
//                    Figure figure;
//                    try {
//                    	while((nachricht = (String) objectInputStream.readObject()) != null){
//                                    appendTextToConsole("Vom Client: \n" + nachricht, LEVEL_NORMAL);
//                                    sendToAllClients(nachricht);
//                            }
//                    } catch (IOException | ClassNotFoundException e) {
//                            e.printStackTrace();
//                    }
//            }
        }
       
        public void listenToClients() {
                while(true) {
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
       
        public void appendTextToConsole(String message, int level) {
                if(level == LEVEL_ERROR) {
                        System.err.println(message + "\n");
                } else {
                        System.out.println(message + "\n");
                }
        }
       
        public void sendToAllClientsMessage(String message) {
            Iterator it = list_clientObjectOutputStream.iterator();
               
                while(it.hasNext()) {
                	ObjectOutputStream objectOutputStream = (ObjectOutputStream) it.next();
                    try {
						objectOutputStream.writeObject(message);
						objectOutputStream.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
        }
        
        public void sendToAllClientsFigure(Figure figure) {
            Iterator it = list_clientObjectOutputStream.iterator();
               
                while(it.hasNext()) {
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