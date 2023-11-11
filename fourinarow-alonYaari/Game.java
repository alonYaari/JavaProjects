
public class Game {
    private int[][] board;
    private int currentPlayer;
    private boolean isFinished;

    public int[][] getBoard() {
        return board;
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public Game() {
        this.board = new int[6][7];
        this.currentPlayer = 1;
        boardInit(board);
        this.isFinished = false;
    }

    public void boardInit(int [][] board)
    {
        for (int i=0;i<6;i++){
            for (int j=0;j<7;j++){
                this.board[i][j] = 0;
            }
        }
    }

    public boolean makeMove(int column) {
        if (column < 0 || column >= 7) {
            return false;
        }

        for (int i = 5; i >= 0; i--) {
            if (this.board[i][column] == 0) {
                this.board[i][column] = currentPlayer;
                if (checkForWin(i, column)) {
                    printBoard();
                    System.out.println("Player " + this.currentPlayer + " wins!");
                    System.exit(1);
                }
                this.currentPlayer = (this.currentPlayer == 1) ? 2 : 1;
                return true;
            }
        }

        return false;
    }

    public boolean checkForWin( int row, int col){
        int playerNum = this.currentPlayer;
        if (checkCol(row, col, playerNum))
            return true;
        if (checkRow(row, col, playerNum))
            return true;
        if (checkDiagonal(row, col, playerNum))
            return true;
        return false;
    }

    public boolean checkCol(int row, int col, int playerNum){
        int count =0;
        for (int i = 0; i < 6; i++) {
            if (board[i][col] == playerNum) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }
    public boolean checkRow( int row, int col, int playerNum){
        int count = 0;

        for (int j = col; j>col - 4 && j > -1; j--){
            if (this.board[row][j]!=playerNum)
                break;
            count++;
        }

        for (int j = col; j<col + 4 && j < 7; j++){
            if (this.board[row][j]!=playerNum)
                break;
            count++;
        }

        if(count>=4) {
            return true;
        }
        return false;
    }
    public boolean checkDiagonal( int row, int col, int playerNum){
        int count = 0;

        for (int i = row, j = col; i >= 0 && j < 7; i--, j++) {
            if (this.board[i][j] == playerNum) {
                count++;
            }
            else {
                break;
            }
        }

        if (count >= 4) {
            return true;
        }

        count = 0;
        for (int i = row, j = col; i >=0  && j >=0; i--, j--) {
            if (this.board[i][j] == playerNum) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }

        count = 0;
        for (int i = row, j = col; i <6  && j >=0; i++, j--) {
            if (this.board[i][j] == playerNum) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }

        count = 0;
        for (int i = row, j = col; i <6  && j <7; i++, j++) {
            if (this.board[i][j] == playerNum) {
                count++;
            } else {
                break;
            }
        }
        if (count >= 4) {
            return true;
        }

        return false;
    }

    public boolean checkForDraw(){
        for (int j=0;j<7;j++){
            if (this.board[5][j] == 0)
                return false;
        }
        return true;
    }
    public void printBoard() {
        for (int i=0;i<6;i++){
            for (int j=0;j<7;j++){
                System.out.print(this.board[i][j]);
            }
            System.out.println();
        }
    }
}