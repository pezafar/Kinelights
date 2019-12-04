package org.kinelights.core.manager.animations;

import java.util.ArrayList;

import org.kinelights.core.interfaces.AnimationInterface;
import org.kinelights.core.interfaces.AnimationPictureInterface;
import org.kinelights.core.interfaces.LightMaterialInterface;
import org.kinelights.core.manager.Routine;


public class Animation implements AnimationInterface {
	
	private int id = 0;
	protected ArrayList<Integer> idList = new ArrayList<>();

	private int beatLength;
	private int channelRange;
	public static int RATE_PER_BEAT = 4;
	protected Routine routine;

	protected ArrayList<ArrayList<Integer>> values = new ArrayList<ArrayList<Integer>>();
	protected  ArrayList<String> channelsNames = new ArrayList<>();
	protected  ArrayList<Integer> channelsIndexes = new ArrayList<>();

	protected  ArrayList<ArrayList<Integer>> animationChannelsBlocks = new ArrayList<>();
	protected ArrayList<String> animationBlocksNames = new ArrayList<>();

	public Animation( int length, int channelRange, Routine routine) {
		this.beatLength = length;
		this.channelRange = channelRange;
		this.routine = routine;

		//Animation de base : tout constant à 0 sur toutes les channelsIndexes
		
		for (int i = 0; i < channelRange; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (int j = 0; j < RATE_PER_BEAT * 4; j++) {
				//Animation constante egale à 0
				temp.add(0);
			}
			values.add(temp);
		}
	}
	
	public int getID() {
		// TODO Auto-generated method stub
		return id;
	}

	public AnimationPictureInterface getPicture() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<LightMaterialInterface> getInvolvedHardware() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setID(int id) {
		this.id = id;
		this.loadAnimationValues();
	}

	private void loadAnimationValues() {};

	public String toString() {
		return ("animation id = " + id); 
	}
	
	public void setValues(int channel, ArrayList<Integer> list) {
		this.values.set(channel-1, list);
	}
	
	public ArrayList<Integer> getChannelAnimationList(int channel){
		return (this.values.get(channel));
	}

	public ArrayList<ArrayList<Integer>> getAnimationListList() {
		return this.values;
	} 

	public  ArrayList<ArrayList<Integer>> getAnimationChannelsBlocks(){
		return( this.animationChannelsBlocks);
	}
	
	public int getBrightnessChannel() {
		for (int i=0;i<channelsNames.size();i++) {
			if (channelsNames.get(i)=="brightness")
				return channelsIndexes.get(i);
		}
		return -2;
	}

	@Override
	public ArrayList<String> getAnimationBlocksNames() {
		return animationBlocksNames;
	}

	@Override
	public ArrayList<Integer> getIdList() {
		return idList;
	}
}
