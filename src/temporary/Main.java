package temporary;

public class Main {

	public static void main(String[] args) {
//		int w = new Minimax().play();
		int w = new Game().play();
//		if (w == 10)
//			System.out.println("CpU won");
//		else if (w == -10)
//			System.out.println("You won");
//		else
//			System.out.println("Its tie");
	}

}

/*
 * 0, 0, -9
0, 1, -8
0, 2, -8
1, 0, -9
1, 2, -10
2, 1, -10
2, 2, -10

0, 0, -6

0, 1, -6

0, 2, -6

1, 0, -7

1, 2, -7

2, 1, -7

2, 2, -7

*/
