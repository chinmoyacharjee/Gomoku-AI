package gameStatePacakge;

import java.util.Random;

public class Game {

	public int rowColom = 3;

	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	public void printBoard(String board[][]) {
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

	private boolean isValid(int tx, int ty, String board[][]) {

		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
			return false;

		String player = board[tx][ty];

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

			if (count == rowColom)
				return true;

			tx += dx;
			ty += dy;

			if (!isValid(tx, ty, board))
				return false;
		}

	}

	public int evaluate(String board[][]) {
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

	public boolean isMovesLeft(String board[][]) {
		for (int i = 0; i < rowColom; i++)
			for (int j = 0; j < rowColom; j++)
				if (board[i][j].equals("-"))
					return true;
		return false;
	}

	private int minimax(String board[][], boolean turn, int step, int alpha, int beta) {

		int score = evaluate(board);

		if (score == 10) {
			return score - step;
		}

		if (score == -10) {
			return score + step;
		}

		if (isMovesLeft(board) == false)
			return 0 - step;

		if (turn) {
			int best = -10000;
			for (int i = 0; i < rowColom; i++) {
				for (int j = 0; j < rowColom; j++) {
					if (board[i][j].equals("-")) {
						board[i][j] = "X";

						int minmaxValue = minimax(board, !turn, step + 1, alpha, beta);
						board[i][j] = "-";
						best = Math.max(best, minmaxValue);

						if (alpha >= beta) {
							alpha = Math.max(best, alpha);
							return alpha;
						}
					}
				}
			}
			return best;

		} else {
			int best = 10000;
			for (int i = 0; i < rowColom; i++) {
				for (int j = 0; j < rowColom; j++) {
					if (board[i][j].equals("-")) {
						board[i][j] = "O";

						int minmaxValue = minimax(board, turn, step + 1, alpha, beta);
						board[i][j] = "-";
						best = Math.min(best, minmaxValue);

						if (alpha >= beta) {
							beta = Math.min(best, beta);
							return beta;
						}
					}
				}
			}

			return best;
		}

	}

	public Move findOptimalMove(String board[][]) {
		int bestVal = -1000;

		int moveI = -9;
		int moveJ = -9;

		for (int i = 0; i < rowColom; i++) {

			for (int j = 0; j < rowColom; j++) {

				if (board[i][j].equals("-")) {

					board[i][j] = "X";
					int step = 0;
					int alpha = -1000;
					int beta = 1000;
					int moveVal = minimax(board, false, step, alpha, beta);

					System.out.println(i + ", " + j + ", " + moveVal);

					System.out.println();
					board[i][j] = "-";

					if (moveVal > bestVal) {
						moveI = i;
						moveJ = j;
						bestVal = moveVal;
					}
				}
			}
		}
		System.out.println(moveI + ", " + moveJ);
		return new Move(moveI, moveJ);
	}

}
