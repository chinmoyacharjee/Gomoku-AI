package displayPackage;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import gameSettings.GameSettings;

public class Display {
	public JFrame frame;
	public int height;
	public int width;
	public String title;
	public Canvas canvas;

	public Display(int width, int height, String title) {

		this.height = height;
		this.width = width;
		this.title = title;
		canvas = new Canvas();
		createDisplay();

	}

	public void createDisplay() {

		// frame
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocation(GameSettings.loacationX, GameSettings.locationY);

		// canvas
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));

		frame.add(canvas);

	}

}
