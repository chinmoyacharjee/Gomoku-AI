package gameStatePacakge;

import java.util.Random;

import gameSettings.GameSettings;

public class Game {

	public int rowColom;

	private int fx[] = { +0, +0, +1, -1, -1, +1, -1, +1 };
	private int fy[] = { -1, +1, +0, +0, +1, +1, -1, -1 };

	private Evaluation evaluation;
	private int depth = 2;

	int startI = 0;
	int startJ = 0;

	int endI = rowColom;
	int endJ = rowColom;

	public Game() {
		this.rowColom = GameSettings.rowColom;
		evaluation = new Evaluation();
	}

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

	public Move randomMove(String board[][]) {

		while (true) {
			int i = new Random().nextInt(rowColom / 2);
			int j = new Random().nextInt(rowColom / 2);

			if (board[i][j].equals("-")) {
				return new Move(i, j);
			}
		}
	}

	public Move getFirstMove() {
		return new Move(4, 4);
	}

	public String[][] initialiseBoard() {
		String[][] board = new String[rowColom][rowColom];
		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {
				board[i][j] = "-";
			}
		}
		return board;
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

	private boolean isValidDir(int tx, int ty) {
		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
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

	public boolean isMovesLeft(String board[][]) {
		for (int i = 0; i < rowColom; i++)
			for (int j = 0; j < rowColom; j++)
				if (board[i][j].equals("-"))
					return true;
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
			for (int i = startI; i < endI; i++) {
				if (alpha >= beta) {
					return beta;
				}
				for (int j = startJ; j < endJ; j++) {
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

	private boolean hasAdjacent(int i, int j, String board[][]) {

		for (int ii = 0; ii < 8; ii++) {
			int x = i;
			int y = j;
			for (int jj = 0; jj < 2; jj++) {

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
		int bestVal = -Integer.MAX_VALUE;

		int moveI = -9;
		int moveJ = -9;

		int step = 0;
		int alpha = -Integer.MAX_VALUE;
		int beta = Integer.MAX_VALUE;

		board = buildSmallBoard(board);

		for (int i = startI; i < endI; i++) {
			if (alpha >= beta)
				break;

			for (int j = startJ; j < endJ; j++) {
				// temp
				// if (!hasAdjacent(i, j, board))
				// continue;

				if (board[i][j].equals("-")) {

					board[i][j] = "X";

					int moveVal = minimax(board, false, step, alpha, beta);

					alpha = Math.max(moveVal, alpha);

					board[i][j] = "-";

					if (moveVal > bestVal) {
						moveI = i;
						moveJ = j;
						bestVal = moveVal;
					}
					if (alpha >= beta)
						break;

				}
			}
		}
		System.out.println(moveI + ", " + moveJ);
		return new Move(moveI, moveJ);
	}

}
