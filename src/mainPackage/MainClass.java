package mainPackage;

import displayPackage.Display;
import gameStatePacakge.Game;

public class MainClass {
	public static void main(String [] args){
		new Game(1000, 900, "Gomoku-AI").start();
	}
}
