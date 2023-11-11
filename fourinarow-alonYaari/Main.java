import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        //Game loop
        while (!game.checkForDraw()){
            // Print the current state of the board
            game.printBoard();

            // Get the column from the user
            System.out.print("Player " + game.getCurrentPlayer() + ", choose a column (0-6): ");
            int column = scanner.nextInt();

            // Make the move and check for errors
            if (!game.makeMove(column)) {
                System.out.println("Invalid move, try again.");
                continue;
            }
        }
        scanner.close();
    }
}