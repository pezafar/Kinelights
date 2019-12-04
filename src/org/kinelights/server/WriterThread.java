package org.kinelights.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WriterThread extends Thread {
	Socket socket;
	ServerSocket serverSocket;
	public WriterThread(String name, Socket socket,ServerSocket serverSocket){
	    super(name);
	    this.socket = socket;
	    this.serverSocket = serverSocket;
	  }
	
	  public void run(){
			try {
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				getStatus(out); //Test, r�ponse attendue : STATUS 1/0
				Thread.sleep(100);
				pause(out); //Test, r�ponse attendue : STATUS 0
				Thread.sleep(100);
				getStatus(out); //Test, r�ponse attendue : STATUS 0
				Thread.sleep(100);
				resume(out); //Test, r�ponse attendue : STATUS 1
				Thread.sleep(100);
				getStatus(out); //Test, r�ponse attendue : STATUS 1
			}catch (Exception e) {
				e.printStackTrace();
			}
	  }
	  public static void resume(PrintWriter out){ //Send a message to restart the client
			System.out.println("We (the server) are sending the request RESUME");
			out.println("RESUME");
			out.flush();
		}
		
	public void pause(PrintWriter out) { //Send a message to the client to pause it
			System.out.println("We (the server) are sending the request PAUSE");
			out.println("PAUSE");
			out.flush();
	}

	public void getStatus(PrintWriter out) { //Send a message to the client to pause it
		System.out.println("We (the server) are sending the request STATUS");
		out.println("STATUS");
		out.flush();
	}
}