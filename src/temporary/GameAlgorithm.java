package temporary;

import java.sql.Time;

public class GameAlgorithm extends Thread {
	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	private int rowColom = 3;
	private int best;

	private String[][] board;

	private int i;
	private int j;

	public GameAlgorithm(String[][] board, int i, int j) {
		this.board = board;
		this.i = i;
		this.j = j;
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

	private int evaluate(String board[][]) {
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

	boolean isMovesLeft(String board[][]) {
		for (int i = 0; i < rowColom; i++)
			for (int j = 0; j < rowColom; j++)
				if (board[i][j].equals("-"))
					return true;
		return false;
	}

	private int minimax(String board[][], boolean turn, int step, int alpha, int beta) {

		int score = evaluate(board);

		// System.out.println(step);
		if (score == 10) {
			return score - step;
		}

		if (score == -10) {
			return score + step;
		}

		if (isMovesLeft(board) == false)
			return 0;

		if (turn) {
			int best = -10000;
			for (int i = 0; i < rowColom; i++) {
				for (int j = 0; j < rowColom; j++) {
					if (board[i][j].equals("-")) {
						board[i][j] = "X";

						int minmaxValue = minimax(board, !turn, ++step, alpha, beta);
						board[i][j] = "-";
						best = Math.max(best, minmaxValue);

						if (alpha >= beta) {
							alpha = Math.max(best, alpha);
							return best;
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

						int minmaxValue = minimax(board, turn, ++step, alpha, beta);
						board[i][j] = "-";
						best = Math.min(best, minmaxValue);

						if (alpha >= beta) {
							beta = Math.min(best, beta);
							return best;
						}
					}
				}
			}

			return best;
		}
		// return 0;

	}

	public int getBest() {
		return best;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	@Override
	public void run() {
		best = minimax(board, false, 0, -1000, 1000);

//		System.out.println("id: " + this.getId() + ", " + best);
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

}
