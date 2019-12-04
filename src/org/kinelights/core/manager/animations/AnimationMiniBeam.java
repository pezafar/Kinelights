package org.kinelights.core.manager.animations;

import java.util.ArrayList;

import org.kinelights.core.manager.Routine;

import org.kinelights.core.manager.materials.LightMaterialMiniBeam;


public class AnimationMiniBeam extends Animation {

		
		private int panChannel = 1;
		private int tiltChannel = 3;



	public static int BLOCK_ANIMATION_NUMBER = 3;
	
	public AnimationMiniBeam(Routine routine) { // A MODIFIER EN METTANT LES VRAIS CANAUX
		// TODO Auto-generated contructor stub

		super(4	, LightMaterialMiniBeam.CHANNEL_RANGE_MINI_BEAM, routine); 

		this.channelsIndexes.add(1);
		this.channelsIndexes.add(3);
		this.channelsIndexes.add(6);
		this.channelsIndexes.add(8);
		this.channelsIndexes.add(9);
		this.channelsIndexes.add(10);

		this.channelsNames.add("pan");
		this.channelsNames.add("tilt");
		this.channelsNames.add("brightness");
		this.channelsNames.add("red");
		this.channelsNames.add("green");
		this.channelsNames.add("blue");

		ArrayList<Integer> blockMotion = new ArrayList<Integer>();
		blockMotion.add(1);
		blockMotion.add(3);

		
		ArrayList<Integer> blockLight = new ArrayList<>();
		blockLight.add(6);
		
		ArrayList<Integer> blockColors = new ArrayList<>();
		blockColors.add(8);
		blockColors.add(9);
		blockColors.add(10);

		this.animationChannelsBlocks.add(blockMotion);
		this.animationChannelsBlocks.add(blockLight);
		this.animationChannelsBlocks.add(blockColors);

		this.animationBlocksNames.add("motion");
		this.animationBlocksNames.add("light");
		this.animationBlocksNames.add("colors");

		for (ArrayList<Integer> block : this.animationChannelsBlocks){
			this.idList.add(0);
		}
	}

	public int getBrightnessChannel() {
		return(6);
	}
	
}