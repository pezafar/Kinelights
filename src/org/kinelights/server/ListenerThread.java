package org.kinelights.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


import org.kinelights.core.manager.Gesture;
import org.kinelights.core.manager.Glove;
import org.kinelights.core.manager.Routine;


	public class ListenerThread extends Thread {
		Socket socket;
		BufferedReader in;
		private String message_distant;
		ServerSocket serverSocket;
		WriterThread thread;
		Routine routine;
		Glove glove;
		
		
		public ListenerThread(String name,Socket socket, ServerSocket serverSocket, Routine routine) {
		    super(name);
		    this.routine = routine;
		    this.glove = routine.getGlove();
		    this.socket = socket;
		    this.message_distant = "";
		    this.serverSocket = serverSocket;
		    try {
				this.in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		    }catch (IOException e) {
				e.printStackTrace();
		    }
		    System.out.println("Constructeur ListenerThread terminé.");
		  }
		
		public void run(){
			float agitation = 0;
			try {
				System.out.println("Listener incoming....");
				while (true) {
					message_distant = in.readLine();
                    if(message_distant != null) {
						System.out.println("We received from the client : " + message_distant);
						agitation = Float.parseFloat(message_distant.split(";")[1]);
						routine.updatePow(agitation);
						if(convertMessageDistant(message_distant) != -1) {
							ArrayList<Boolean> fingerPos = new ArrayList<Boolean>();
							System.out.println("geste main reconnu");
							for (int i = 0; i <  Gesture.NUMBER_OF_FINGER; i++) {

								if (message_distant.split(";")[1+i].equals("1.0") ){
									fingerPos.add(true);
								}
								else{
									fingerPos.add(false);
								}
							}

							Gesture gestureToSend = new Gesture(convertMessageDistant(message_distant), fingerPos, routine);
							glove.incomingGesture(gestureToSend);
						}
					}
					
				}
			}catch (IOException e) {
					e.printStackTrace();
			}
		}

		private int convertMessageDistant(String message_distant2) {
			if(message_distant2.startsWith("A1"))
				return 0;
			else if(message_distant2.startsWith("A2")) {
				return 1;
			}
			else if(message_distant2.startsWith("B")) {
				return 2;
			}
			else if(message_distant2.startsWith("C")) {
				return 3;
			}
			else {
				return -1;
			}
		}

	
}
