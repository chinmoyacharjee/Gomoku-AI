package temporary;

import gameStatePacakge.Evaluation;

public class GameAlgorithm extends Thread {
	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	private int rowColom = 10;
	private int winValue = 5;
	
	private int best;

	private String[][] board;

	private int row;
	private int col;
	
	private Evaluation evaluation;

	public GameAlgorithm(String[][] board, int i, int j) {
		this.board = board;
		this.row = i;
		this.col = j;
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

			if (count == winValue)
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
								return -2;
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


	private int minimax(String board[][], boolean turn, int step, int alpha, int beta) {

		if (step == 2) {
			return evaluation.evaluate(board);
		}
		int score = evaluate(board);

		if (turn) {
			int best = -Integer.MAX_VALUE;
			for (int i = 0; i < rowColom; i++) {
				if (alpha >= beta) {
					return alpha;
				}
				for (int j = 0; j < rowColom; j++) {
					if (!hasAdjacent(i, j, board))
						continue;

					if (board[i][j].equals("-")) {
						board[i][j] = "X";

						int minmaxValue = minimax(board, !turn, step + 1, alpha, beta);
						board[i][j] = "-";
						best = Math.max(best, minmaxValue);
						alpha = Math.max(best, alpha);

						if (alpha >= beta) {
							return alpha;
						}
					}
				}
			}
			return best;

		} else {
			int best = Integer.MAX_VALUE;
			for (int i = 0; i < rowColom; i++) {
				if (alpha >= beta) {
					return beta;
				}
				for (int j = 0; j < rowColom; j++) {
					if (!hasAdjacent(i, j, board))
						continue;

					if (board[i][j].equals("-")) {
						board[i][j] = "O";

						int minmaxValue = minimax(board, turn, step + 1, alpha, beta);
						board[i][j] = "-";
						best = Math.min(best, minmaxValue);
						beta = Math.min(best, beta);

						if (alpha >= beta) {
							return beta;
						}
					}
				}
			}

			return best;
		}

	}

	public int getBest() {
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return best;
	}

	public int getI() {
		return row;
	}

	public int getJ() {
		return col;
	}

	@Override
	public void run() {

//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		System.out.println("AliveBefore: " + this.getId() + "; " + this.isAlive() + " best: " + best + " " + row + col);
		//
		best = minimax(board, false, 0, -1000, 1000);

		System.out.println("AliveAfter : " + this.getId() + "; " + this.isAlive() + " best: " + best + " " + row + col);

		// synchronized (board) {
		// }
		// System.out.println(best + ", " + row + ", " + col);

	}

}
