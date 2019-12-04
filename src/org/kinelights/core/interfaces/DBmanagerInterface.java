package org.kinelights.core.interfaces;

import org.kinelights.core.manager.materials.LightMaterial;

public interface DBmanagerInterface {
	public boolean getStatus();
	public AnimationInterface getAnimation(GestureInterface gesture, LightMaterial material);

}
