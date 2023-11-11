import java.awt.*;

public class Bridge {
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int playerNum;
    private boolean isShow;
    private int [] hexagonNumber;

    public Bridge(int x1, int y1, int x2, int y2, int row, int col) {
        this.xStart = x1;
        this.yStart = y1;
        this.xEnd = x2;
        this.yEnd = y2;
        this.playerNum = 0;
        this.isShow = false;
        this.hexagonNumber = new int [] {row, col};
    }

    public int getxStart() {
        return xStart;
    }

    public void setxStart(int xStart) {
        this.xStart = xStart;
    }

    public int getyStart() {
        return yStart;
    }

    public void setyStart(int yStart) {
        this.yStart = yStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int[] getHexagonNumber() {
        return hexagonNumber;
    }

    public void setHexagonNumber(int col, int row) {
        this.hexagonNumber[0] = col;
        this.hexagonNumber[1] = row;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Game.getCurrentPlayer().getPlayerColor());
        g2.setStroke(new BasicStroke(6));
        g2.drawLine(xStart + 5, yStart + 30, xEnd + 5, yEnd + 30);
    }
}
