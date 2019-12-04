package org.kinelights.gui.frontWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MusicPanel extends PanelBase{
	//display information about the music currently played
	private Window window;
	private int colorfulnessPoint = 255;
	private int radiusPoint = 0;
	
	private int rPoint = 0;
	private int gPoint = 0;
	private int bPoint = 0;
	
	private Color colorPoint = new Color(rPoint, bPoint, gPoint);

	private double[][] samples;
	private long nFrames = 0;
	private int indexSamples = 0;
	private int samplesBufferLength = 80000;

	
	public MusicPanel(Window windowMain) {
		this.window = windowMain;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {



		Graphics2D g2D  = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.setDecoration(g);

		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHints(rh);

		radiusPoint = (int) (Math.min(this.getWidth(), this.getHeight()) * 0.1);
		int fontSize = this.getWidth()/Window.TITLE_FONT_DIVIDER;
		
		g2D.setFont(new Font(Window.fontName, 1, fontSize));
		g2D.setColor(Window.TEXT_COLOR);
		g2D.drawString( "BPM : " + window.getRoutine().getBPM() , this.getXtextCenter(g2D, "BPM : XXX"), this.getYtextCenter(g2D, "BPM", (float) 0.1) );
		g2D.setColor(colorPoint);
		g2D.fillOval(this.getWidth()/2 -radiusPoint/2, (int) (this.getHeight()* (1.0/3.0))-radiusPoint/2,radiusPoint,radiusPoint);
	}


	public void nextSample(int currentPosInBeat) {
		
		float powerPercentage = ((float)(currentPosInBeat%5)/5  );
		powerPercentage = (float) (0.3 + powerPercentage*0.7);
		powerPercentage = (float) Math.pow(powerPercentage, 1);

		rPoint = (int) (powerPercentage * Window.PANEL_BG_COLOR.getRed());
		gPoint = (int) (powerPercentage * Window.PANEL_BG_COLOR.getGreen());
		bPoint = (int) (powerPercentage * Window.PANEL_BG_COLOR.getBlue());
		
		colorPoint = new Color(rPoint, gPoint, bPoint);
		colorfulnessPoint = (int)( 255 * powerPercentage );
		repaint();
	}


	public void drawWaveForm(Graphics2D g){
		int max = this.getHeight()/5;
		int size = (int) (this.getWidth()*0.6);
		int offsetY = (int) (this.getHeight() * (2.0/3.0) );
		int offSetX = (this.getWidth() - size)/2;
		int barSize = 2;
		int numberToMou =   (int) ((samplesBufferLength*barSize)/size);
		int factorLis = 10;

		double value = 0;
		for (int k = 0; k <(int)(size/barSize); k++){
			value = 0;
			for (int i  = indexSamples; i < indexSamples + factorLis*numberToMou ; i++){
				value+= Math.abs(samples[0][k*numberToMou + i]) * Math.abs(samples[0][k*numberToMou + i]);
			}
			value = value/(float)(numberToMou*factorLis)   ;
			value = Math.sqrt(value);

			int intValue = Math.max(1, (int) (max*value));
			g.setColor(Window.TEXT_COLOR);
			g.fillRect(offSetX + k*barSize , offsetY, barSize,  intValue);
			g.fillRect(offSetX+k*barSize, offsetY - intValue, barSize,intValue);
		}

	}

	public void updateWaveForm(long nanoDeltaT){

		indexSamples = indexSamples + (int)(nanoDeltaT*44.1);
		repaint();
	}


	
	
	
}
