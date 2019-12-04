package org.kinelights.core.manager.animations;

import java.util.ArrayList;

import org.kinelights.core.manager.Routine;
import org.kinelights.core.manager.materials.LightMaterialMiniWash;

public class AnimationStrobo extends Animation {
	
public static int BLOCK_ANIMATION_NUMBER = 1;
	
	public AnimationStrobo(Routine routine) {
		super(4	, LightMaterialMiniWash.CHANNEL_RANGE_STROBO, routine);

		this.channelsIndexes.add(1);
		this.channelsIndexes.add(2);
		this.channelsIndexes.add(3);

		this.channelsNames.add("light");
		this.channelsNames.add("light2");
		this.channelsNames.add("light3strb");

		ArrayList<Integer> blockLight = new ArrayList<Integer>();
		blockLight.add(1);
		blockLight.add(2);
		blockLight.add(3);



		this.animationChannelsBlocks.add(blockLight);
		
		this.animationBlocksNames.add("light");

		for (ArrayList<Integer> block : this.animationChannelsBlocks){
			this.idList.add(0);
		}
	}
}
