package temporary;

public class Main {

	public static void main(String[] args) {
//		int w = new Minimax().play();
		int w = new Game().play();
		if (w == 10)
			System.out.println("CpU won");
		else if (w == -10)
			System.out.println("You won");
		else
			System.out.println("Its tie");
	}

}

/*
 * 0, 1, -7
0, 2, -9
1, 0, -7
2, 1, -7
2, 2, -7s

---

0, 1, -7
0, 2, -7
1, 0, -7
2, 1, -9
2, 2, -10


---1----

0, 0, -10
0, 1, -10
0, 2, -10
1, 0, -6
1, 2, -9
2, 1, -8
2, 2, -8


*/
