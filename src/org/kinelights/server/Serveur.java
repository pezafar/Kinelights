package org.kinelights.server;

import org.kinelights.core.manager.Routine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread {

	private static int port=8022;

	public Serveur(Routine routine) {
	    super();
	    this.goServer(routine);
    }

	public void goServer(Routine routine) {
		try {
			ServerSocket ServerSocketListener = new ServerSocket(port);
			Socket socketListener = ServerSocketListener.accept();
			ListenerThread listener = new ListenerThread("listener", socketListener,ServerSocketListener, routine);
			listener.start();
			System.out.println("Connexion établie.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}