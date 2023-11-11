import java.awt.*;
import java.util.Arrays;

public class Player {
    private String name;
    private int playerNum;
    private int victoryPoints;
    private int numOfBridge;
    private Color playerColor;
    private int[] resources = new int[6];
    private boolean isFirstTurn;

    public Player(String name, Color playerColor, int playerNum) {
        this.name = name;
        this.playerColor = playerColor;
        this.playerNum = playerNum;
        this.victoryPoints = 0;
        this.numOfBridge = 0;
        this.isFirstTurn = true;

        for (int i=0; i<resources.length;i++)
            resources[i] = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResources(int index) {
        return resources[index];
    }

    public void setResources(int index, int amount) {
        this.resources[index] = amount;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public void incResource(int index){
        this.resources[index]++;
    }

    public void decResource(int index){
        this.resources[index]--;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public void incVictoryPoints(){
        this.victoryPoints++;
    }

    public int getNumOfBridge() {
        return numOfBridge;
    }

    public void setNumOfBridge(int numOfBridge) {
        this.numOfBridge = numOfBridge;
    }

    public void incNumOfBridge() {
        this.numOfBridge = this.getNumOfBridge() + 1;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    public boolean isFirstTurn() {
        return isFirstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        isFirstTurn = firstTurn;
    }

    public void printResources(){
        System.out.println("player " + this.playerNum + " resources: " + Arrays.toString(this.resources));
    }
}
