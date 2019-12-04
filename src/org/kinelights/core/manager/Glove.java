package org.kinelights.core.manager;

import org.kinelights.core.interfaces.GestureInterface;
import org.kinelights.core.interfaces.GloveInterface;
import org.kinelights.core.interfaces.RoutineInterface;


//Not useful
//only updates the gesture when incomingGesture() is called

public class Glove  implements GloveInterface {
	
	private RoutineInterface routine;
	private boolean status = false;

	public Glove(RoutineInterface routine) {
		this.routine = routine;
	}
	
	public boolean getStatus() {
		return status;
	}


	public void sendOrientation() {
		// TODO Auto-generated method stub
	}



	public void incomingGesture(GestureInterface gesture) {
		System.out.println("gesture received : " + gesture.getMainID()  );
		routine.updateGesture(gesture);
		
	}

	
	
	
}
