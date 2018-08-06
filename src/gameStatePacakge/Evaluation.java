package gameStatePacakge;

import java.util.ArrayList;

import gameSettings.GameSettings;

public class Evaluation {
	// private int fx[] = { 1, 0, 1, -1 };
	// private int fy[] = { 0, 1, 1, 1 };

	// temp

	private int fx[] = { 0, 1, 1, -1 };
	private int fy[] = { 1, 0, 1, 1 };

	private int rowColom;

	private String player;
	private String playerCpu = "X";
	private String playerHuman = "O";
	private String playerEmpty = "-";

	public ArrayList<Pattern> ptrn;

	String[] patternListX = { "XXXXX", "-XXXX-", "XXXX-", "XX-XX", "XXX-X", "-XXX-X", "-XXX-", "XXX--", "-XX-X-" };
	String[] patternListO = { "OOOOO", "-OOOO-", "OOOO-", "OO-OO", "OOO-O", "-OOO-O", "-OOO-", "OOO--", "-OO-O-" };

	public Evaluation() {
		this.rowColom = GameSettings.rowColom;
		ptrn = new ArrayList<Pattern>();
	}

	private boolean isValid(int tx, int ty, String board[][], String player) {

		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
			return false;
		if (!player.equals(playerEmpty) && !player.equals(board[tx][ty]))
			return false;

		return true;
	}

	private int isPatternExist(String pat, boolean cpu) {
		for (int i = 0; i < 9; i++) {
			String s, sRev;
			if (cpu)
				s = patternListX[i];
			else
				s = patternListO[i];

			sRev = new StringBuilder(s).reverse().toString();

			if (pat.equals(s) || pat.equals(sRev)) {
				return i;
			}
		}

		return -1;
	}

	private int isWinner(int tx, int ty, int dx, int dy, String board[][]) {
		String player = board[tx][ty];
		int count = 0;

		int initialX = tx;
		int initialY = ty;

		String pat = "";
		while (true) {
			if (player.equals(board[tx][ty]) || player.equals(playerEmpty)) {
				// System.out.println(board[tx][ty]);
				pat += board[tx][ty];
				count++;
			} else
				return -1;

			if (count >= 5) {
				int index;
				System.out.println("pat: " + pat);
				if (player.equals(playerCpu))
					index = isPatternExist(pat, true);
				else
					index = isPatternExist(pat, false);
				// System.out.println(index);
				return index;
			}
			tx += dx;
			ty += dy;

			if (!isValid(tx, ty, board, player))
				return -1;
		}

	}

	private boolean isValidDir(int tx, int ty) {
		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
			return false;
		return true;
	}

	private int findPattern(int tx, int ty, int dx, int dy, String board[][]) {
		String player = board[tx][ty];

		String pat = "";

		int i = 0;
		while (i < 6) {
			 System.out.println(tx+ty+" "+board[tx][ty]);
			if (board[tx][ty].equals("-") || player.equals(board[tx][ty])) {
				pat += board[tx][ty];
				// System.out.println(board[tx][ty]);
				 System.out.println("pat: " + pat);
			} else{
				
				return -1;
			}
				
			if (pat.length() == 5) {

				int index;
				 System.out.println("Pat5: " + pat);
				if (player.equals(playerCpu))
					index = isPatternExist(pat, true);
				else
					index = isPatternExist(pat, false);
				if (index != -1)
					return index;

			} else if (pat.length() == 6) {
				int index;
				System.out.println("Pat6: " + pat);
				if (player.equals(playerCpu))
					index = isPatternExist(pat, true);
				else
					index = isPatternExist(pat, false);
				if (index != -1)
					return index;
			}
			tx += dx;
			ty += dy;

			if (!isValidDir(tx, ty))
				return -1;

			i++;
		}
		return -1;

	}

	public int evaluate(String board[][]) {
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				String player = board[i][j];
				for (int k = 0; k < 1; k++) {
					int dirX = fx[k];
					int dirY = fy[k];

					int index = isWinner(i, j, dirX, dirY, board);

					if (index != -1) {

						if (player.equals(playerCpu))
							System.out.println(patternListX[index]);
						else
							System.out.println(patternListO[index]);
					}
				}

				// if (player.equals(playerCpu) || player.equals(playerHuman)) {
			}

		}
		return 0;
	}

	public int evaluate2(String board[][]) {
		for (int i = 0; i < 10; i++) {

			String player = board[0][i];
			for (int k = 0; k < 1; k++) {
				int dirX = fx[k];
				int dirY = fy[k];

				int index = findPattern(0, i, dirX, dirY, board);

				if (index != -1) {

					if (player.equals(playerCpu))
						System.out.println("Result: " + patternListX[index]);
					else
						System.out.println("Result: " + patternListO[index]);
				}

				// if (player.equals(playerCpu) || player.equals(playerHuman)) {
			}

		}
		return 0;
	}

	public int evaluate3(String board[][]) {
		String player = board[0][0];

		int d = 0;

		int dx = fx[d];
		int dy = fy[d];

		int index = findPattern(0, 0, dx, dy, board);
		if (index != -1) {

			if (player.equals(playerCpu))
				System.out.println("Result: " + patternListX[index]);
			else
				System.out.println("Result: " + patternListO[index]);
		}

		// if (player.equals(playerCpu) || player.equals(playerHuman)) {

		return 0;
	}

}
