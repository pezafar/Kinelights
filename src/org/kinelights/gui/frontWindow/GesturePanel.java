package org.kinelights.gui.frontWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;

import org.kinelights.core.interfaces.GestureInterface;

public class GesturePanel extends PanelBase{

	private Window window;
	private int width = 0;
	private int height = 0;
	private String title = "GESTURE RECOGNITION";
	private int currentGestureMainID = 0;
	
	
	private JLabel labelTitle = new JLabel(title);
	private JLabel gestureDisplay = new JLabel("None");
	
	
	public GesturePanel(Window windowMain) {
		this.window = windowMain;
		this.width = windowMain.getWidth()/6;
		this.height = windowMain.getHeight()/6;	
		this.setBackground(Color.black);
		
		//this.add(labelTitle);
		//this.add(gestureDisplay);
		this.labelTitle.setForeground(Color.white);
		this.gestureDisplay.setForeground(Color.white);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;	
		setDecoration(g);
		
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		int fontSize = this.getWidth()/Window.TITLE_FONT_DIVIDER;
		
		g2D.setFont(new Font("Arial", 1, fontSize));
		g2D.setColor(Color.black);
		FontMetrics fm = g2D.getFontMetrics(g2D.getFont());
		int yCenter = (int) ( this.getHeight()*0.1 + fm.getAscent());
		int xCenter = (this.getWidth()-fm.stringWidth(title))/2;
		g2D.drawString(title,xCenter,yCenter);
		yCenter = (int) ( this.getHeight()/2 + fm.getAscent());
		xCenter = (this.getWidth()-fm.stringWidth(String.valueOf(currentGestureMainID)))/2;

		g2D.drawString(String.valueOf(currentGestureMainID),xCenter, yCenter);
		
	}
	
	public void updateGestureDisplay(GestureInterface gesture) {
		//this.gestureDisplay.setText(String.valueOf(gesture.getMainID()));
		this.currentGestureMainID = gesture.getMainID();
		System.out.println(currentGestureMainID);
		this.repaint();
		
	}
	
}
