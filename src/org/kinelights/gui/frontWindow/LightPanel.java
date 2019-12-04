package org.kinelights.gui.frontWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


import org.kinelights.gui.animationMapper.AnimMapper;
import org.kinelights.core.manager.Gesture;

public class LightPanel extends PanelBase{
	//displays informations about the recognized move
	private Gesture gesture;
	private org.kinelights.gui.frontWindow.Window window;
	private Clip clip;
	private String fileName = "";
	private File file;
	private AudioInputStream stream;
	private AudioFormat format;
	private DataLine.Info info;

	private JButton loadButton = new JButton("Load");
	private JButton playButton = new JButton("Play");
	private JButton pauseButton = new JButton("Pause");

	private JButton buttonMapping = new JButton("Animation Mapper");
	private JPanel p =new JPanel();


	public LightPanel(org.kinelights.gui.frontWindow.Window windowMain)  {

		this.window = windowMain;

		this.stylizeButton(playButton);
		this.stylizeButton(pauseButton);
		this.stylizeButton(loadButton);
		//this.add(loadButton);
		//this.add(playButton);
		//this.add(pauseButton);

		int padding = this.getPadding();
		this.setLayout(new GridLayout(1,1));
		p.setBorder(new EmptyBorder(padding,padding,padding,padding));
		p.setLayout(new GridLayout(5,1));

		this.add(p);
		p.setOpaque(false);
		stylizeButton(buttonMapping);
		p.add(buttonMapping);

		p.add(loadButton);
		p.add(playButton);
		p.add(pauseButton);

		//p.add(new JButton("YO"));
		this.setUpButtons();
		this.setUpButtonsOld();

	}
		
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2D  = (Graphics2D) g;
		this.setDecoration(g);

	}
	
	public void setUpAudio() {

		
		try{
			this.window.getRoutine().setWavFilename(fileName);
			file = new File(fileName);
			stream = AudioSystem.getAudioInputStream(file);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LightPanel getSelf() {
		return this;
	}
	
	public void stylizeButton(JButton button) {
		button.setContentAreaFilled(true);
		button.setBorderPainted(true);
		button.setBackground(org.kinelights.gui.frontWindow.Window.BUTTONS_COLOR);
		button.setForeground(Window.TEXT_COLOR);
		button.setBorderPainted(false);

	}

	private void setUpButtonsOld(){

		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*try {
					clip.stop();
				}
				catch (Exception ee) {
					System.out.println("oklm frérot");
				}*/
				fileName = "/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/audio/" + JOptionPane.showInputDialog(getSelf(), "Choose a wav file", "File selection", JOptionPane.QUESTION_MESSAGE);
				setUpAudio();
			}
		});

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clip.start();
			}
		});


		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clip.stop();
			}
		});
	}
	
	private void setUpButtons(){
		this.buttonMapping.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//new AnimMapper(window);
				AnimMapper mapper = new AnimMapper(window);

			}
		});
	}

	public void updateSizes(){
		int padding = this.getPadding()*2;
		p.setBorder(new EmptyBorder(padding,padding,padding,padding));

	}


}
