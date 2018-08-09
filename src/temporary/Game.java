package temporary;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import gameStatePacakge.Move;

public class Game {
	private int rowColom = 10;
	
	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

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
		int max = -10000;
		int ind = -1;
		for (int i = 0; i < gl.size(); i++) {
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


	public Move findOptimalMove(String board[][]) {
		int bestVal = -Integer.MAX_VALUE;


		int step = 0;
		int alpha = -Integer.MAX_VALUE;
		int beta = Integer.MAX_VALUE;

		ArrayList<GameAlgorithm> th = new ArrayList<GameAlgorithm>();
		System.out.println("Size: " + th.size());

		int thIndex = 0;

		for (int i = 0; i < rowColom; i++) {

			for (int j = 0; j < rowColom; j++) {

				if (!hasAdjacent(i, j, board))
					continue;

				if (board[i][j].equals("-")) {

					board[i][j] = "X";

					GameAlgorithm g = new GameAlgorithm(board, i, j);

					th.add(g);
					board[i][j] = "-";
				}

			}
		}
		for (int i = 0; i < th.size(); i++) {
			th.get(i).start();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < th.size(); i++) {
			try {
				th.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 1 1

		int moveI = -1, moveJ = -1;

		int ind = getBestIndex(th);
		moveI = th.get(ind).getI();
		moveJ = th.get(ind).getJ();

		System.out.println(moveI + ", " + moveJ);
		return new Move(moveI, moveJ);
	}

	public int play() {
		String[][] board = fill();

		printBoard(board);

		String playerCpu = "X";
		String playerHuman = "O";
		String player = playerCpu;

		int randomCount = 0;

		Move move;
		while (true) {

			if (player.equals(playerCpu)) {
				if (randomCount < 1) {
					move = randomMove(board);
					board[2][0] = playerCpu;
					randomCount++;
				} else {

					move = findOptimalMove(board);
					board[move.row][move.col] = playerCpu;
				}
				player = playerHuman;
				// randomCount++;
			} else if (player.equals(playerHuman)) {
				move = humanMove(board);
				board[move.row][move.col] = playerHuman;
				player = playerCpu;
			}

			printBoard(board);
			//
			// a int score = evaluate(board);

			// if (score == 10)
			// return 10;
			//
			// if (score == -10)
			// return score;
			//
			// if (isMovesLeft(board) == false)
			// return 0;
		}
	}
}
