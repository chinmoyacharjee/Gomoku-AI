package temporary;

public class Main {

	public static void main(String[] args) {
		int w = new Minimax().play();
		if (w == 10)
			System.out.println("CpU won");
		else if (w == -10)
			System.out.println("You won");
		else
			System.out.println("Its tie");
	}

}
