package gameStatePacakge;

import java.util.HashMap;
import java.util.HashSet;

public class Pattern {

	public static HashMap<String, Integer> patternsCPU;
	public static HashMap<String, Integer> patternsHUMAN;


	private String[] patternListX = { "XXXXX", "-XXXX-", "XXXX-", "XX-XX", "XXX-X", "X-XXX-", "-XXX-", "XXX--", "-XX-X",
			"XX-X-", "-XX-X-", "--XX-", "XX---", "X---X", "X----", "--X--", "-X---" };
	private String[] patternListO = { "OOOOO", "-OOOO-", "OOOO-", "OO-OO", "OOO-O", "O-OOO-", "-OOO-", "OOO--", "-OO-O",
			"OO-O-", "-OO-O-", "--OO-", "OO---", "O---O", "O----", "--O--", "-O---" };

	int[] profit = { 200000000, 50000, 15000, 420, 420, 480, 350, 300, 100, 100, 100, 50 / 15, 50 / 15, 50 / 15,
			20 / 10, 20 / 10, 20 / 10 };

	public Pattern() {
		patternsCPU = createList(true);
		patternsHUMAN = createList(false);
	}

	private HashMap<String, Integer> createList(boolean cpu) {
		HashMap<String, Integer> temp = new HashMap<String, Integer>();

		for (int i = 0; i < 17; i++) {
			String pat;
			int val;

			if (cpu) {
				pat = patternListX[i];
				val = profit[i];
			} else {

				pat = patternListO[i];
				val = (-1) * profit[i];
			}

			temp.put(pat, val);
		}

		return temp;
	}

	public boolean isValidPattern(String pattern) {
		HashSet<Character> set = new HashSet<>();
		for (int i = 0; i < pattern.length(); i++) {
			set.add(pattern.charAt(i));
		}
		if (set.size() > 2)
			return false;
		else
			return true;
	}

	public String patternType(String pattern) {
		HashSet<Character> set = new HashSet<Character>();
		for (int i = 0; i < pattern.length(); i++) {
			set.add(pattern.charAt(i));
		}
		if (set.size() > 2)
			return "mix";
		else {
			if (set.contains('X'))
				return "CPU";
			return "human";
		}
	}

	public int isPatternExist(String pat, boolean cpu) {
		for (int i = 0; i < 16; i++) {
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

	public int getEvaluateValuePatternWise(int index, boolean isCpuPatternType, boolean cpuTurn) {

		int value;

		if (isCpuPatternType) {
			value = patternsCPU.get(patternListX[index]);
//			if (cpuTurn)
//				value *= 5;
		} else {

			value = patternsHUMAN.get(patternListO[index]);
//			if (!cpuTurn)
//				value *= 5;
		}

		return value;
	}

	public int getEvaluateValuePatternWise2(int index, boolean isCpuPatternType, boolean cpuTurn) {
		// 5 in a row
		if (index == 0) {
			if (isCpuPatternType)
				return 200000000;
			else
				return -200000000;
		}

		// 4 consecutive with 2 open ends
		if (index == 1) {
			if (isCpuPatternType && cpuTurn)
				return 100000000;
			else if (isCpuPatternType && !cpuTurn)
				return 500000;

			if (!isCpuPatternType && !cpuTurn)
				return -100000000;
			else if (!isCpuPatternType && cpuTurn)
				return -500000;
		}
		// 4 consecutive or broken with one open end
		if (index > 1 && index < 6) {
			if (index == 2) {
				if (isCpuPatternType && cpuTurn)
					return 100000000;
				else if (isCpuPatternType && !cpuTurn)
					return -100000000;

				if (!isCpuPatternType && !cpuTurn)
					return -100000000;
				else if (!isCpuPatternType && cpuTurn)
					return 100000000;

			}
			if (index == 5) {
				if (isCpuPatternType && cpuTurn)
					return 4000;
				else if (isCpuPatternType && !cpuTurn)
					return 20;

				if (!isCpuPatternType && !cpuTurn)
					return -4000;
				else if (!isCpuPatternType && cpuTurn)
					return -20;

			} else {
				if (isCpuPatternType && cpuTurn)
					return 100;
				else if (isCpuPatternType && !cpuTurn)
					return 20;

				if (!isCpuPatternType && !cpuTurn)
					return -100;
				else if (!isCpuPatternType && cpuTurn)
					return -20;

			}
		}

		// 3 consecutive
		if (index > 5 && index < 11) {
			if (index == 6) {
				if (isCpuPatternType && cpuTurn)
					return 1000;
				else if (isCpuPatternType && !cpuTurn)
					return 50;

				if (!isCpuPatternType && !cpuTurn)
					return -1000;
				else if (!isCpuPatternType && cpuTurn)
					return -50;

			}
			if (index == 7) {
				if (isCpuPatternType && cpuTurn)
					return 7;
				else if (isCpuPatternType && !cpuTurn)
					return 5;

				if (!isCpuPatternType && !cpuTurn)
					return -7;
				else if (!isCpuPatternType && cpuTurn)
					return -5;

			} else {
				if (isCpuPatternType && cpuTurn)
					return 3;
				else if (isCpuPatternType && !cpuTurn)
					return 1;

				if (!isCpuPatternType && !cpuTurn)
					return -3;
				else if (!isCpuPatternType && cpuTurn)
					return -1;

			}

		}

		// 2
		if (index > 10 && index < 14) {
			if (isCpuPatternType && cpuTurn)
				return 20;
			else if (isCpuPatternType && !cpuTurn)
				return 5;

			if (!isCpuPatternType && !cpuTurn)
				return -2;
			else if (!isCpuPatternType && cpuTurn)
				return -500;
		}
		// 1
		else {
			if (isCpuPatternType)
				return 1;
			else
				return 1;
		}

		return 0;
	}

}
