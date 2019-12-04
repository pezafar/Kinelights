package org.kinelights.core.manager.materials;

import org.kinelights.core.manager.animations.AnimationMiniBeam;
import org.kinelights.core.manager.Routine;

public class LightMaterialMiniBeam extends LightMaterial {

	public LightMaterialMiniBeam(Routine routine, int channelStart) {
		super(routine, channelStart);
		this.setType(TYPE_MINI_BEAM);
		this.setIndicator("MINIBEAM");
		this.animation = new AnimationMiniBeam(routine);
		this.setName("MiniBeam");
		// TODO Auto-generated constructor stub
	}

}
