import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.*;

public class Game {
    private static ArrayList<Player> players;
    private CatanBoard catanBoard;
    public static JFrame frame;
    private static int currentPlayerIndex;

    public Game() {
        this.catanBoard = new CatanBoard();
        this.players = new ArrayList<Player>();
        this.currentPlayerIndex = 0;

        this.frame = new JFrame("Settlers of Catan");
        this.frame.setLayout(null);

        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.setBounds(100,600,100,100);
        endTurnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                endTurn();
            }
        });

        JButton rollButton = new JButton("roll");
        rollButton.setBounds(200,600,100,100);
        rollButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rollTheDice();
            }
        });

        frame.add(endTurnButton);
        frame.add(rollButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(catanBoard);

        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numOfPlayers = sc.nextInt();
        System.out.println(numOfPlayers);

        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("Enter the name of player " + (i+1) + ": ");
            String name = sc.next();
            // Only work through cmd.
            Color playerColor = JColorChooser.showDialog(null, "Choose a Color", Color.WHITE);

            //Color playerColor = Color.BLUE;
            this.players.add(new Player(name, playerColor, i));
        }

        System.out.println("Game starts now!");
        startTurn();
    }

    public void startTurn() {
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + "'s turn");
        // Do whatever the player needs to do at the start of their turn
    }

    public void endTurn() {
        for(Player player:players)
            player.printResources();

        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + "'s turn has ended");
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Move to the next player
        startTurn(); // Start the next player's turn
    }

    public void rollTheDice(){
        Random rnd = new Random();
        int sum = rnd.nextInt(1,6);
        sum += rnd.nextInt(1,6);

        System.out.println("The dice shows: " + sum);
        CatanBoard.giveResources(sum);
    }

    public static Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public static Player getPlayerByNumber(int number) {
        return players.get(number);
    }
}
