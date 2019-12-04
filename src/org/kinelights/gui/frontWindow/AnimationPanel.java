package org.kinelights.gui.frontWindow;

import java.awt.Graphics;
import java.awt.Graphics2D;

import org.kinelights.core.manager.animations.Animation;

public class AnimationPanel extends PanelBase {
	//display information about the animation currently running
	
	private Animation anim;
	private int width = 0;
	private int height = 0;
	private Window window;
	
	public AnimationPanel(Window windowMain) {
		
		this.window = windowMain;
		this.setBackground(Window.PANEL_BG_COLOR);
		this.width = windowMain.getWidth()/6;
		this.height = windowMain.getHeight()/6;		
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D  = (Graphics2D) g;
		this.setDecoration(g);
	}
	
}
