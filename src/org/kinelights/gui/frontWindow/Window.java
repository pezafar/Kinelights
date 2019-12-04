package org.kinelights.gui.frontWindow;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import org.kinelights.core.interfaces.GestureInterface;
import org.kinelights.core.manager.Routine;

public class Window extends JFrame {
	private AnimationPanel animpan;
	private GesturePanel gesturePanel;
	private LightPanel lightpan;
	private MusicPanel musicpan;
	private StatusPanel statuspan;
	private OptionPanel optionpan;
	
	public static int TITLE_FONT_DIVIDER = 15;

	public static Color PANEL_BG_COLOR = new Color(65,65,65);
	public static Color PANEL_PADDING_COLOR = new Color(56, 56, 56);

	public static Color TEXT_COLOR = new Color(200,200,200);
	public static Color BUTTONS_COLOR = new Color(72,72,72);

	public static String fontName = "Arial";
	
	
	
	private Routine routine;
	
	public Window(Routine routine){
		this.routine = routine;
		this.getContentPane().setLayout(new GridLayout(2, 3));
		
		animpan = new AnimationPanel(this);
		gesturePanel = new GesturePanel(this);
		musicpan = new MusicPanel(this);
		statuspan = new StatusPanel(this);
		optionpan = new OptionPanel(this);
		lightpan = new LightPanel(this);
		
		this.add(animpan);
		this.add(musicpan);
		this.add(gesturePanel);
		this.add(lightpan);
		this.add(statuspan);
		this.add(optionpan);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent componentEvent) {
				lightpan.updateSizes();
			}
		});
		
		this.setSize(800, 500);
		this.setVisible(true);
		
	}

	public void displayGesture(GestureInterface gesture) {
		this.gesturePanel.updateGestureDisplay(gesture);;
		
	}
	
	public Routine getRoutine() {
		return routine;
	}

	public void nextSample(int currentPosInBeat) {
		musicpan.nextSample(currentPosInBeat);
		
	}

	public void updateWaveForm(long nanoDeltaT){
		this.musicpan.updateWaveForm(nanoDeltaT);
	}

}
