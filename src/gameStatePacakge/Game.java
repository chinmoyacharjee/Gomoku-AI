package gameStatePacakge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import displayPackage.Display;

public class Game implements Runnable, MouseListener, MouseMotionListener {

	private Display display;
	private int width;
	private int height;
	private String title;

	private Thread thread;

	private int BOX_width = 50;
	private int BOX_height = 50;

	private int paddingX = 50;
	private int paddingY = 50;

	private int mousePointAtX = -1;
	private int mousePointAtY = -1;

	private BufferStrategy buffer;
	private Graphics g;

	public Game(int width, int height, String title) {

		this.width = width;
		this.height = height;
		this.title = title;
		this.display = new Display(width, height, title);

	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();

	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private void drawBoard(Graphics g) {

		g.setColor(Color.red);

		int lineNumber = 1;

		for (int i = paddingX; i < (15 * BOX_width) + paddingX; i += BOX_width) {
			g.drawString(Integer.toString(lineNumber++), i, paddingY - 20);
			g.drawLine(i, paddingY, i, BOX_height * 15);
		}

		lineNumber = 1;

		for (int i = paddingY; i < (15 * BOX_height) + paddingY; i += BOX_height) {
			g.drawString(Integer.toString(lineNumber++), paddingX - 20, i + 2);
			g.drawLine(paddingX, i, BOX_width * 15, i);
		}

	}

	private void mousePointAt(Graphics g) {
		String s = mousePointAtX + ", " + mousePointAtY;
		g.setColor(Color.blue);
		g.setFont(new Font("arial", Font.CENTER_BASELINE, 14));

		if (!(mousePointAtX == -1 || mousePointAtY == -1)){
			g.drawString(s, width - 150, 50);
			mousePointIndicatior(g);
		}

	}

	private void mousePointIndicatior(Graphics g) {

		int x = mousePointAtX * BOX_width - (BOX_width / 2);
		int y = mousePointAtY * BOX_height - (BOX_height / 2);

		g.setColor(Color.GRAY);
		g.fillOval(x, y, BOX_width, BOX_height);

	}
	
	private void drawPlayer(){
		
	}

	private void render() {
		buffer = display.canvas.getBufferStrategy();
		if (buffer == null) {
			display.canvas.createBufferStrategy(3);
			return;
		}

		g = buffer.getDrawGraphics();

		g.clearRect(0, 0, width, height);
		
		drawBoard(g);
		
		mousePointAt(g);

		buffer.show();

		g.dispose();
	}

	private void init() {
		display.canvas.addMouseMotionListener(this);
		display.canvas.addMouseListener(this);
	}

	@Override
	public void run() {
		init();
		while (true) {
			render();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		mousePointAtX = x / BOX_width;
		mousePointAtY = y / BOX_height;

		if (mousePointAtX < 1 || mousePointAtX > 15 || mousePointAtY < 1 || mousePointAtY > 15) {
			mousePointAtX = -1;
			mousePointAtY = -1;
		}

		// System.out.println(x +" "+ y);
		

	}
}
