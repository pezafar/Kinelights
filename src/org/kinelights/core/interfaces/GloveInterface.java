package org.kinelights.core.interfaces;

public interface GloveInterface {
	
	boolean getStatus();
	void incomingGesture(GestureInterface gesture);
	void sendOrientation();
	
	
}
