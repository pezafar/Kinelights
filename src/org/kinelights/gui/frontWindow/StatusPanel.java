package org.kinelights.gui.frontWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.kinelights.core.manager.materials.LightMaterial;

public class StatusPanel extends PanelBase{
	//display status of material (glove and lights)

	private Window window;
	private JTextArea textAreaStatus = new JTextArea();
	private int paddingTextArea = 0;
	
	public StatusPanel(Window windowMain) {
		this.window = windowMain;
		this.setBackground(Color.yellow);
		this.setLayout(new BorderLayout());
	
		//textAreaStatus.setBackground(Color.TRANSLUCENT);
		Border border = BorderFactory.createEmptyBorder(60,60,60,60);
		Border line  = BorderFactory.createLineBorder(Color.black);
		
		//textAreaStatus.setBorder(BorderFactory.createCompoundBorder(border,border));		
		textAreaStatus.setOpaque(false);
		//textAreaStatus.setMargin(new Insets(padding, padding, padding, padding));
		textAreaStatus.setBackground(Color.red);
		textAreaStatus.setForeground(Window.TEXT_COLOR);
		this.add(textAreaStatus);
		textAreaStatus.setText("sdfsdfsdf\nsdfsdfsdf");
		setText();
		
	}

	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D  = (Graphics2D) g;
		this.setDecoration(g);
		
		paddingTextArea = (int) (Math.min(this.getHeight(), this.getWidth())*0.1)  ;
		textAreaStatus.setMargin(new Insets(paddingTextArea,paddingTextArea,paddingTextArea,paddingTextArea));
		//System.out.println("repaintText");
	}

	
	public void setText() {
		
		String materialSubText = "";
		
		
		for (LightMaterial material : window.getRoutine().getLightMaterialList() ) {
			materialSubText+= material.getName() + " : Starting Channel " + material.getChannelStart() + "\n";
		}
		
		
		String text = "DMX  : connected \n"
				+ "GLOVE : Connected\n\n"
				+ "----LIGHT MATERIALS----\n\n"
				+ materialSubText;
		textAreaStatus.setText(text);
	}


	public void adapt() {
		// TODO Auto-generated method stub
		
		textAreaStatus.setFont(new Font (textAreaStatus.getFont().getFontName(), 1,  (int) (Math.min(this.getHeight(), this.getWidth() )*0.01 )));

	}
	
}
