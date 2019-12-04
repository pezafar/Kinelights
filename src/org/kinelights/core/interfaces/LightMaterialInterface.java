package org.kinelights.core.interfaces;

public interface LightMaterialInterface {

	
	public boolean getStatus();
	public void setStatus(boolean status);
	public AnimationInterface getAnimation();
	public void switchLooping();
	public boolean getLoop();
	public void setAnimation(AnimationInterface animationToSet);

	
}
