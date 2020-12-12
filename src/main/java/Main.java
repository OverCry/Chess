import Interfaces.IBoard;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        System.out.println("\nChess");

        //generate board
        IBoard board = new Board();
        board.play();
    }
}
