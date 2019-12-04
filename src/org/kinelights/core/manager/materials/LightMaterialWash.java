package org.kinelights.core.manager.materials;

import org.kinelights.core.manager.Routine;

public class LightMaterialWash extends LightMaterial {
	public LightMaterialWash(Routine routine, int channelStart) {
		super(routine, channelStart);
		this.setType(TYPE_WASH);
		this.setIndicator("WASH");
		this.setName("Wash");


		// TODO Auto-generated constructor stub
		
	}


}
