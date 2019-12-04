package org.kinelights.gui.frontWindow;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PanelBase extends JPanel{
	private int padding = 0;

	public PanelBase() {
		
	}

	
	public void setDecoration(Graphics g) {
		super.paintComponent(g);
		validate();
		Graphics2D g2D  = (Graphics2D) g;

		this.setBackground(Window.PANEL_PADDING_COLOR);
		padding = (int) (0.02*Math.min(this.getHeight(), this.getWidth()) );
		g2D.setColor(Window.PANEL_BG_COLOR);
		g2D.fillRect(padding,padding, this.getWidth()-2*padding, this.getHeight()-2*padding);
	}
	
	
	public int getXtextCenter(Graphics2D g2D, String text) {
		FontMetrics fm = g2D.getFontMetrics(g2D.getFont());
		return (this.getWidth()-fm.stringWidth(text))/2;
	}

	public int getYtextCenter(Graphics2D g2D, String text, float percentage) {
		FontMetrics fm = g2D.getFontMetrics(g2D.getFont());
		return (int) ( this.getHeight()*percentage + fm.getAscent());
	}
	
	public int getPadding() {
		return padding;
	}
	
	public void resizeComponents() {
		
	}
	
}


