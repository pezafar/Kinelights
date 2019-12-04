package org.kinelights.core.manager.materials;

import org.kinelights.core.manager.animations.AnimationMiniWash;
import org.kinelights.core.manager.Routine;

public class LightMaterialMiniWash extends LightMaterial{

	public LightMaterialMiniWash(Routine routine, int channelStart) {
		super(routine, channelStart);
		this.setType(TYPE_MINI_WASH);
		this.setIndicator("MINIWASH");
		this.animation = new AnimationMiniWash(routine);
		this.setName("MiniWash");


		// TODO Auto-generated constructor stub
		
	}



}
