package org.kinelights.core.manager;

import java.util.ArrayList;

import org.kinelights.core.manager.animations.Animation;
import org.kinelights.core.interfaces.AnimationInterface;
import org.kinelights.core.interfaces.GestureInterface;
import org.kinelights.core.interfaces.GesturePictureInterface;

public class Gesture  implements GestureInterface {
	public static int NUMBER_OF_FINGER = 3;
	private int mainID = 0;
	private int motionID = 0;
	private Animation animation;
	private ArrayList<Boolean> fingersPosition = new ArrayList<Boolean>();
	Routine routine;
	
	
	public Gesture(int motionID, ArrayList<Boolean> fingersPosition,Routine routine) {
		this.fingersPosition=fingersPosition;
		this.motionID=motionID;
		this.routine = routine;		
		this.mainID= routine.getDBmanager().getGestureMainID(this.motionID,this.fingersPosition);
	}
	
	public void setFingerPosition(ArrayList<Boolean> positionToSet) {
			this.fingersPosition=positionToSet;
	}

	

	public String getName() {
		// TODO Auto-generated method stub
		return "gesture";
	}

	public GesturePictureInterface getLogo() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Boolean> getFingersPostion(){
		return fingersPosition;
	}
	public int getMotionID() {
		return this.motionID;
	}
	private ArrayList<Boolean> getFingersPosition() {
		return this.fingersPosition; 
	}

	public int getMainID() {
		// TODO Auto-generated method stub
		return (mainID);
	}

	public AnimationInterface getAnimation() {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString(){


		String ret = "";
		ret += String.valueOf(motionID) + " - ";
		for (Boolean boolFinger : fingersPosition){
			ret += String.valueOf(boolFinger) + ",";
		}

		return (ret);
	}

	public int getID(){
		int id  = 0;
		for (int i =0; i < fingersPosition.size(); i++){

			if (fingersPosition.get(i)){
			id += (int)Math.pow(2,i);
			}
		}
		return(id);
	}


	//Retrieving the fingers positions from the fingerId (encoded as 3 int)
	public static boolean[] getFingersFromFingersId(int id){
		int f1;
		int f2;
		int f3;

		boolean[] ret = new boolean[3];
		f1 = (int)(id)%2;
		f2 = (int) (id/2)%2;
		f3 = (int) (id/4)%2;

		if (f1 == 1){
			ret[0] = true;
		}
		if (f2 == 1){
			ret[1] = true;
		}
		if (f3 == 1){
			ret[2] = true;
		}



		return(ret);
	}

}
 