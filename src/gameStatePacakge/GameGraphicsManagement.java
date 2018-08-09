package gameStatePacakge;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import asset.ImageLoader;
import displayPackage.Display;
import gameSettings.GameSettings;

public class GameGraphicsManagement implements Runnable, KeyListener, MouseListener, MouseMotionListener {

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

	private int rowColom;
	// temp
	private Game game;
	//
	private Evaluation evaluation;
	private BufferedImage black;
	private BufferedImage white;
	private BufferedImage mouseIndicator;

	private boolean isGameEnd = false;

	private boolean firstMove = true;

	// temporary
	private String[][] board;
	private String player;
	private String playerCpu = "X";
	private String playerHuman = "O";
	private String playerEmpty = "-";
	//

	public GameGraphicsManagement() {

		this.width = GameSettings.width;
		this.height = GameSettings.height;
		this.title = GameSettings.title;
		this.display = new Display(width, height, title);

		this.rowColom = GameSettings.rowColom;
	}

	private void init() {

		// temporary
		player = playerCpu;
		//
		game = new Game();
		evaluation = new Evaluation();

		black = ImageLoader.loadImage("/Images/bl1.png", BOX_width, BOX_height);
		white = ImageLoader.loadImage("/Images/w1.png", BOX_width, BOX_height);
		mouseIndicator = ImageLoader.loadImage("/Images/w1.png", BOX_width, BOX_height);

		display.canvas.addKeyListener(this);
		display.canvas.addMouseMotionListener(this);
		display.canvas.addMouseListener(this);
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

		g.setColor(Color.BLACK);

		int lineNumber = 1;

		for (int i = paddingX; i < (rowColom * BOX_width) + paddingX; i += BOX_width) {
			g.drawString(Integer.toString(lineNumber++), i, paddingY - 20);
			g.drawLine(i, paddingY, i, BOX_height * rowColom);
		}

		lineNumber = 1;

		for (int i = paddingY; i < (rowColom * BOX_height) + paddingY; i += BOX_height) {
			g.drawString(Integer.toString(lineNumber++), paddingX - 20, i + 2);
			g.drawLine(paddingX, i, BOX_width * rowColom, i);
		}

	}

	private void mousePointAt(Graphics g) {

		g.setColor(Color.blue);
		g.setFont(new Font("arial", Font.CENTER_BASELINE, 14));

		String s = mousePointAtX + ", " + mousePointAtY;

		if (!(mousePointAtX == -1 || mousePointAtY == -1)
				&& board[mousePointAtX - 1][mousePointAtY - 1].equals(playerEmpty)) {
			g.drawString(s, width - 150, 50);
			mousePointIndicatior(g);
		}

	}

	private void mousePointIndicatior(Graphics g) {

		int x = mousePointAtY * BOX_width - (BOX_width / 2);
		int y = mousePointAtX * BOX_height - (BOX_height / 2);

		/*
		 * g.setColor(Color.GRAY); g.fillOval(x, y, BOX_width, BOX_height);
		 */

		g.drawImage(mouseIndicator, x, y, null);

	}

	private void drawPlayer(Graphics g) {

		Color playerHumanColor = Color.BLUE;
		Color playerCpuColor = Color.GREEN;

		Color color = playerCpuColor;

		BufferedImage playerImage = null;
		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {

				if (board[i][j].equals(playerCpu)) {
					// color = playerCpuColor;
					playerImage = black;
				} else if (board[i][j].equals(playerHuman)) {
					// color = playerHumanColor;
					playerImage = white;
				}
				if (!board[i][j].equals(playerEmpty)) {
					g.setColor(color);
					int x = (j + 1) * BOX_width - (BOX_width / 2);
					int y = (i + 1) * BOX_height - (BOX_height / 2);
					// g.fillOval(x, y, BOX_width, BOX_height);
					g.drawImage(playerImage, x, y, null);

				}
			}
		}

	}

	private void checkWin(Graphics g) {

		int x = game.checkWin(board);

		g.setFont(new Font("Century Gothic", Font.CENTER_BASELINE, 20));

		isGameEnd = true;

		if (x == 10) {
			g.drawString("Cpu won", width - 150, 250);
		} else if (x == -10) {
			g.drawString("You won", width - 150, 250);
		} else if (game.isMovesLeft(board) == false) {
			g.drawString("Tie", width - 150, 250);
		} else {
			isGameEnd = false;
		}

	}

	private void render() {
		buffer = display.canvas.getBufferStrategy();
		if (buffer == null) {
			display.canvas.createBufferStrategy(3);
			return;
		}

		g = buffer.getDrawGraphics();

		g.clearRect(0, 0, width, height);

		checkWin(g);
		drawBoard(g);
		drawPlayer(g);
		mousePointAt(g);

		buffer.show();
		g.dispose();
	}

	private void play() {
		Move move;
		if (player.equals(playerCpu)) {

			if (firstMove) {
				move = game.getFirstMove();
				firstMove = false;
			} else {
				move = game.findOptimalMove(board);
			}
			//
			board[move.row][move.col] = playerCpu;
			// game.printBoard(board);
			player = playerHuman;
		}

	}

	private String[][] fill() {
		String[][] board = new String[rowColom][rowColom];
		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {
				board[i][j] = playerEmpty;
			}
		}
		return board;
	}

	public void resetGame() {
		board = fill();
		isGameEnd = false;
		// temporary
		player = playerCpu;
		//
		firstMove = true;
	}

	@Override
	public void run() {
		init();
		board = fill();
		while (true) {
			render();
			play();

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (!isGameEnd && player.equals(playerHuman)) {

			if (mousePointAtX < 1 || mousePointAtX > rowColom || mousePointAtY < 1 || mousePointAtY > rowColom) {
				mousePointAtX = -1;
				mousePointAtY = -1;
			}

			else {

				int row = mousePointAtX - 1;
				int col = mousePointAtY - 1;

				if (board[row][col].equals(playerEmpty)) {
					//
					board[row][col] = "O";
					//
					// temporary
					player = playerCpu;
					//

				}
			}
		} else {
			mousePointAtX = -1;
			mousePointAtY = -1;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		int x = e.getX();
		int y = e.getY();

		if (!isGameEnd && player.equals(playerHuman)) {

			mousePointAtX = y / BOX_height;
			mousePointAtY = x / BOX_width;

			if (mousePointAtX < 1 || mousePointAtX > rowColom || mousePointAtY < 1 || mousePointAtY > rowColom) {
				mousePointAtX = -1;
				mousePointAtY = -1;
			}
		} else {
			mousePointAtX = -1;
			mousePointAtY = -1;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			resetGame(); // temp
			// if (isGameEnd)
			// resetGame();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
