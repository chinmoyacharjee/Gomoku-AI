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

	private int startI;
	private int startJ;
	private int endI;
	private int endJ;

	int depth = 2;

	private Evaluation evaluation;

	public GameAlgorithm(String[][] board, int i, int j, int startI, int startJ, int endI, int endJ) {
		this.board = board;
		this.row = i;
		this.col = j;
		this.startI = startI;
		this.startJ = startJ;
		this.endI = endI;
		this.endJ = endJ;
		this.evaluation = new Evaluation();
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

		if (step == depth) {
			return evaluation.evaluate(board, turn);
		}

		if (turn) {
			int best = -Integer.MAX_VALUE;
			for (int i = startI; i < endI; i++) {
				if (alpha >= beta) {
					return alpha;
				}
				for (int j = startJ; j < endJ; j++) {
//					if (!hasAdjacent(i, j, board))
//						continue;

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
			for (int i = startI; i < endI; i++) {
				if (alpha >= beta) {
					return beta;
				}
				for (int j = startJ; j < endJ; j++) {
//					if (!hasAdjacent(i, j, board))
//						continue;

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
			// TODO Auto-generated catch block
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

//		System.out.println(startI + " " + startJ);

		int alpha = -Integer.MAX_VALUE;
		int beta = Integer.MAX_VALUE;

		System.out.println("AliveBefore: " + this.getId() + "; " + this.isAlive() + " best: " + best + " " + row + col);
		//
		best = minimax(board, false, 0, alpha, beta);

		System.out.println("AliveAfter : " + this.getId() + "; " + this.isAlive() + " best: " + best + " " + row + col);

	}

}
