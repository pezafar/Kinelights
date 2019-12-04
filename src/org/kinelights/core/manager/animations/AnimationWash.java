package org.kinelights.core.manager.animations;

import java.util.ArrayList;

import org.kinelights.core.manager.Routine;
import org.kinelights.core.manager.materials.LightMaterialWash;

public class AnimationWash extends Animation {

		
		private int panChannel = 1;
		private int tiltChannel = 3;
	
	
	
	
	
	public AnimationWash(Routine routine) { // A MODIFIER EN METTANT LES VRAIS CANAUX
		// TODO Auto-generated contructor stub
		super(4	, LightMaterialWash.CHANNEL_RANGE_WASH, routine); 

		this.channelsIndexes.add(1);
		this.channelsIndexes.add(2);
		this.channelsIndexes.add(4);
		this.channelsIndexes.add(5);
		this.channelsIndexes.add(6);
		this.channelsIndexes.add(7);

		this.channelsNames.add("pan");
		this.channelsNames.add("tilt");
		this.channelsNames.add("brightness");
		this.channelsNames.add("red");
		this.channelsNames.add("green");
		this.channelsNames.add("blue");

		ArrayList<Integer> blockMotion = new ArrayList<Integer>();
		blockMotion.add(1);
		blockMotion.add(2);

		
		ArrayList<Integer> blockLight = new ArrayList<>();
		blockLight.add(4);
		
		ArrayList<Integer> blockColors = new ArrayList<>();
		blockColors.add(5);
		blockColors.add(6);
		blockColors.add(7);

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
	
}