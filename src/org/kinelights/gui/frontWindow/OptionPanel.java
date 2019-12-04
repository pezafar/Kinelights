package org.kinelights.gui.frontWindow;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionPanel extends PanelBase {
	private JButton buttonSettings = new JButton("Settings");
	private JButton buttonHelp = new JButton("Help");
	private JButton buttonGestures = new JButton("Gestures");
	private JPanel panelButtons = new JPanel();
	private GridLayout bLayout = new GridLayout(3, 1);
	private Window window;
	
	public OptionPanel(Window windowMain) {
		this.window = windowMain;
		this.setBackground(Window.PANEL_BG_COLOR);
		this.add(panelButtons);
		panelButtons.setLayout(bLayout);
		panelButtons.setPreferredSize(new Dimension(this.getWidth(), 100));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2D  = (Graphics2D) g;
		this.setDecoration(g);
	}
	
	public void formatButton(JButton button) {
		button.setContentAreaFilled(false);
		panelButtons.add(button);
		
	}
	
	@Override
	public void resizeComponents() {
		super.resizeComponents();
		int margin = this.getPadding();
	
	}
}
