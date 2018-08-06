package gameStatePacakge;

public class Pattern {

	public int startI;
	public int startJ;
	public int endI;
	public int endJ;
	public String type;
	public int openEnds;
	public String pattern;

	public Pattern(String pattern, int startI, int startJ, int endI, int endJ, String type, int openEnds) {
		this.pattern = pattern;
		this.startI = startI;
		this.startJ = startJ;
		this.endI = endI;
		this.endJ = endJ;
		this.type = type;
		this.openEnds = openEnds;
	}

}
