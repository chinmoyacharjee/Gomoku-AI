package temporary;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import gameSettings.GameSettings;
import gameStatePacakge.Evaluation;
import gameStatePacakge.Move;

public class GameManipulation {
	private int rowColom = 10;

	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	private int depth = 2;

	// temp
	int startI = 0;
	int startJ = 0;

	int endI = rowColom;
	int endJ = rowColom;

	public GameManipulation() {
		this.rowColom = GameSettings.rowColom;
	}

	private void printBoard(String board[][]) {
		for (int i = 0; i < rowColom; i++)
			System.out.print("|-----");
		System.out.println("|");

		for (int i = 0; i < rowColom; i++) {
			System.out.print("|  ");
			for (int j = 0; j < rowColom; j++) {
				System.out.print(board[i][j]);
				if (j != rowColom - 1)
					System.out.print("  |  ");
			}
			System.out.println("  |  ");

			for (int k = 0; k < rowColom; k++)
				System.out.print("|-----");
			System.out.println("|");
		}

	}

	private boolean isValid(int tx, int ty, String board[][], String player) {

		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
			return false;

		if (!board[tx][ty].equals(player))
			return false;

		return true;
	}

	private boolean isWinner(int tx, int ty, int dx, int dy, String board[][]) {
		String player = board[tx][ty];
		int count = 0;
		while (true) {
			if (player.equals(board[tx][ty])) {
				count++;
			} else
				return false;

			if (count == 5)
				return true;

			tx += dx;
			ty += dy;

			if (!isValid(tx, ty, board, player))
				return false;
		}

	}

	public int checkWin(String board[][]) {
		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {
				String player = board[i][j];
				if (player.equals("X") || player.equals("O")) {
					for (int k = 0; k < 8; k++) {
						int dirX = fx[k];
						int dirY = fy[k];

						if (isWinner(i, j, dirX, dirY, board)) {
							if (player.equals("X"))
								return 10;
							else if (player.equals("O"))
								return -10;
						}
					}
				}
			}
		}
		return 0;
	}

	Move randomMove(String board[][]) {

		while (true) {
			int i = new Random().nextInt(rowColom);
			int j = new Random().nextInt(rowColom);

			if (board[i][j].equals("-")) {
				return new Move(i, j);
			}
		}
	}

	public Move getFirstMove() {
		return new Move(4, 4);
	}

	Move humanMove(String board[][]) {
		while (true) {
			int i;
			int j;
			System.out.print("row col: ");
			Scanner sc = new Scanner(System.in);
			i = sc.nextInt();
			j = sc.nextInt();

			if (board[i][j].equals("-")) {
				return new Move(i, j);
			} else {
				System.out.println("Enter Valid Input");
			}
		}
	}

	private String[][] fill() {
		String[][] board = new String[rowColom][rowColom];
		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {
				board[i][j] = "-";
			}
		}
		return board;
	}

	private int getBestIndex(ArrayList<GameAlgorithm> gl) {
		int max = -Integer.MAX_VALUE;
		int ind = -1;
		for (int i = 0; i < gl.size(); i++) {
			System.out.println(gl.get(i).getBest());
			if (max < gl.get(i).getBest()) {
				max = gl.get(i).getBest();
				ind = i;

			}
			System.out.println(gl.get(i).getI() + ", " + gl.get(i).getJ() + ", " + gl.get(i).getBest());
		}
		return ind;
	}

	private boolean isValidDir(int tx, int ty) {
		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
			return false;
		return true;
	}

	public boolean isMovesLeft(String board[][]) {
		for (int i = 0; i < rowColom; i++)
			for (int j = 0; j < rowColom; j++)
				if (board[i][j].equals("-"))
					return true;
		return false;
	}

	private boolean hasAdjacent(int i, int j, String board[][]) {

		for (int ii = 0; ii < 8; ii++) {
			int x = i;
			int y = j;
			for (int jj = 0; jj < 1; jj++) {

				x += fx[ii];
				y += fy[ii];

				if (!isValidDir(x, y))
					continue;

				if (board[x][y] == "X" || board[x][y] == "O")
					return true;
			}
		}
		return false;
	}

	private String[][] buildSmallBoard(String board[][]) {
		int maxI = 0;
		int maxJ = 0;

		int minI = rowColom - 1;
		int minJ = rowColom - 1;

		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {
				if (board[i][j] == "X" || board[i][j] == "O") {
					if (minI > i) {
						minI = i;
					}
					if (minJ > j) {
						minJ = j;
					}

					if (maxI < i) {
						maxI = i;
					}
					if (maxJ < j) {
						maxJ = j;
					}
				}
			}
		}

		String[][] smallBoard = new String[rowColom][rowColom];

		int x = 1;
		if (minI >= x)
			minI -= x;
		else
			minI = 0;
		if (minJ >= x)
			minJ -= x;
		else
			minJ = 0;

		if (maxI < rowColom - x)
			maxI += x;
		else
			maxI = rowColom - 1;
		if (maxJ < rowColom - x)
			maxJ += x;
		else
			maxJ = 0;

		startI = minI;
		startJ = minJ;

		endI = maxI + 1;
		endJ = maxJ + 1;

		for (int i = minI; i <= maxI; i++) {
			for (int j = 0; j <= maxJ; j++) {
				smallBoard[i][j] = board[i][j];
			}
		}

		return smallBoard;
	}

	public Move findOptimalMove(String board[][]) {

		ArrayList<GameAlgorithm> th = new ArrayList<GameAlgorithm>();

		board = buildSmallBoard(board);

		for (int i = startI; i < endI; i++) {

			for (int j = startJ; j < endJ; j++) {

				if (board[i][j].equals("-")) {

					board[i][j] = "X";

					GameAlgorithm g = new GameAlgorithm(board, i, j, startI, startJ, endI, endJ);

					th.add(g);
					board[i][j] = "-";
				}
			}
		}

		for (int i = 0; i < th.size(); i++) {
			th.get(i).start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < th.size(); i++) {
			try {
				th.get(i).join(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		int moveI = -1, moveJ = -1;

		int ind = getBestIndex(th);
		moveI = th.get(ind).getI();
		moveJ = th.get(ind).getJ();

		System.out.println(moveI + ", " + moveJ);
		return new Move(moveI, moveJ);
	}

	// if (board[i][j].equals("-")) {
	//
	// board[i][j] = "X";
	//
	// GameAlgorithm g = new GameAlgorithm(board, i, j, startI, startJ, endI,
	// endJ);
	//
	// th.add(g);
	// board[i][j] = "-";
	// }

}
