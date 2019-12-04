package org.kinelights.core.interfaces;
import java.util.ArrayList;


/*
Interface of the Animation class
The animation class loads its content froms text files
 */
public interface AnimationInterface {
	public int getID();
	public ArrayList<Integer> getIdList();

	public AnimationPictureInterface getPicture();
	public ArrayList<LightMaterialInterface> getInvolvedHardware();
	public void setID(int id);
	public ArrayList<Integer> getChannelAnimationList(int channel);
	public ArrayList<ArrayList<Integer>> getAnimationListList();
	public ArrayList<ArrayList<Integer>> getAnimationChannelsBlocks();
	public ArrayList<String> getAnimationBlocksNames();
	public int getBrightnessChannel();
	
}
