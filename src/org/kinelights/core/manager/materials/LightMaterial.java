package org.kinelights.core.manager.materials;

import org.kinelights.core.manager.animations.Animation;
import org.kinelights.core.interfaces.AnimationInterface;
import org.kinelights.core.interfaces.LightMaterialInterface;
import org.kinelights.core.manager.Routine;

public class LightMaterial  implements LightMaterialInterface {
	
	protected Animation animation;
	private boolean loop;
	private boolean status;
	
	private int typeLight = 0;
	private String name = "light";

	private String indicator = "";

	public static final int CHANNEL_RANGE_WASH = 14;
	public static final int CHANNEL_RANGE_STROBO = 3;
	public static final int CHANNEL_RANGE_MINI_WASH = 13;
	public static final int CHANNEL_RANGE_MINI_BEAM = 13;
	
	
	private int channelStart = 0;
	
	public static final int TYPE_WASH = 1;
	public static final int TYPE_STROBO = 2;
	public static final int TYPE_MINI_WASH = 3;
	public static final int TYPE_MINI_BEAM = 4;

	public LightMaterial(Routine routine, int channelStart) {
		loop=false;
		status=false;
		this.channelStart = channelStart;
		animation=new Animation(4,4, routine);
	}
	
	public boolean getStatus() {
		// TODO Auto-generated method stub
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status=status;
	}

	public AnimationInterface getAnimation() {
		// TODO Auto-generated method stub
		return animation;
	}
	
	public boolean getLoop() {
		return loop;
	}
	
	public void switchLooping() {
		// TODO Auto-generated method stub
		loop=!loop;
		System.out.println("Loop value is " + loop);
	}

	public void setAnimation(AnimationInterface animationToSet) {
		// TODO Auto-generated method stub
		this.animation = (Animation) animationToSet;
		System.out.println("new animation on " + this.getName() +  " : "+ this.animation);
	
	}

	public int getType() {
		return (typeLight);
	}
	public void setType(int type)
	{
		this.typeLight = type;
	}
	
	public void setName(String name) {
		this.name = name;
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getChannelStart() {
		return(channelStart);
	}
	
	public void setChannelStart(int channel) {
		channelStart = channel;
		}

	public void setIndicator(String indicator){
		this.indicator = indicator;
	}

	public String getIndicator(){
		return (indicator);
	}

}
 