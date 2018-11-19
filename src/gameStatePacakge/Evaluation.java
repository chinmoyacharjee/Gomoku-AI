package gameStatePacakge;

import gameSettings.GameSettings;

public class Evaluation {
	private int fx[] = { 0, 1, 1, -1 };
	private int fy[] = { 1, 0, 1, 1 };

	private int rowColom;

	private Pattern patternClass;

	public Evaluation() {
		this.rowColom = GameSettings.rowColom;
		patternClass = new Pattern();
	}

	private boolean isValidDirection(int tx, int ty) {
		if (tx >= rowColom || tx < 0)
			return false;
		if (ty >= rowColom || ty < 0)
			return false;
		return true;
	}

	private int findPattern(int tx, int ty, int dx, int dy, String board[][], boolean cpuTurn) {

		String consecutivePattern = "";
		int saveIndex = -1;

		int i = 0;
		while (i < 6) {

			consecutivePattern += board[tx][ty];
			if (consecutivePattern.length() >= 5 && consecutivePattern.length() <= 6) {
				int index;

				String type = patternClass.patternType(consecutivePattern);

				if (type.equals("mix") && consecutivePattern.length() != 6)
					return -1;

				boolean cpu;
				if (type.equals("CPU"))
					cpu = true;
				else
					cpu = false;

				if (consecutivePattern.length() == 5) {
					index = patternClass.isPatternExist(consecutivePattern, cpu);

					if (index == -1)
						return -1;

					if (tx == rowColom - 1 || ty == rowColom - 1) {
						return patternClass.getEvaluateValuePatternWise(index, cpu, cpuTurn);

					}

					tx += dx;
					ty += dy;

					if (!isValidDirection(tx, ty))
						return -1;
					consecutivePattern += board[tx][ty];

					saveIndex = index;
					index = patternClass.isPatternExist(consecutivePattern, false);

					if (index == -1) {
						return patternClass.getEvaluateValuePatternWise(saveIndex, cpu, cpuTurn);
					} else {
						return patternClass.getEvaluateValuePatternWise(index, cpu, cpuTurn);

					}

				}
			}

			tx += dx;
			ty += dy;

			if (!isValidDirection(tx, ty))
				return -1;

			i++;
		}
		return -1;

	}

	public int evaluate(String board[][], boolean cpuTurn) {

		int evalVal = 0;
		for (int i = 0; i < rowColom; i++) {
			for (int j = 0; j < rowColom; j++) {
				for (int k = 0; k < 4; k++) {
					int dirX = fx[k];
					int dirY = fy[k];

					int value = findPattern(i, j, dirX, dirY, board, cpuTurn);

					if (value != -1) {
						// System.out .println(value);
						evalVal += value;
					}
				}

			}

		}

		return evalVal;
	}

}
