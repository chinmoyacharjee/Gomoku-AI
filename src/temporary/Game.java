package temporary;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private int rowColom = 3;

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

	private Move findOptimalMove(String board[][]) {
		int bestVal = -1000;

		ArrayList<GameAlgorithm> th = new ArrayList<GameAlgorithm>();

		int thIndex = 0;
		
//		GameAlgorithm g;

		for (int i = 0; i < rowColom; i++) {

			for (int j = 0; j < rowColom; j++) {

				if (board[i][j].equals("-")) {

					board[i][j] = "X";
					int step = 0;
					int alpha = -1000;
					int beta = 1000;

					GameAlgorithm g = new GameAlgorithm(board, i, j);
					//ga.add(g);
//					g.start();
					th.add(g);
					board[i][j] = "-";
					th.get(thIndex).start();
					try {
						th.get(thIndex).join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					thIndex++;
					board[i][j] = "-";

					// int moveVal = minimax(board, false, step, alpha, beta);

					// System.out.println(i + ", " + j + ", "+ moveVal);
					//
					// System.out.println();
					
					//
					// if (moveVal > bestVal) {
					// moveI = i;
					// moveJ = j;
					// bestVal = moveVal;
					// }
				}

			}
		}
//		for (int i = 0; i < th.size(); i++) {
//			th.get(i).start();
//		}

//		1 1

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
