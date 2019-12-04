package org.kinelights.core.manager.materials;

import org.kinelights.core.manager.animations.AnimationStrobo;
import org.kinelights.core.manager.Routine;

public class LightMaterialStrobo extends LightMaterial{

	public LightMaterialStrobo(Routine routine, int channelStart) {
		super(routine, channelStart);
		this.setType(TYPE_STROBO);
		this.setIndicator("STROBOSCOPE");
		this.animation = new AnimationStrobo(routine);
		this.setName("Stroboscope");
		// TODO Auto-generated constructor stub
	}

}
