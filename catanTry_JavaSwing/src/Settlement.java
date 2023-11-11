import java.awt.*;

public class Settlement {
    private int x;
    private int y;
    private int level;
    private int playerNum;
    private boolean isShow;
    private int nearResources[];

    public Settlement(int x, int y) {
        this.x = x;
        this.y = y;
        this.level = 0;
        this.playerNum = -1;
        this.isShow = false;
        this.nearResources = new int [3];
        for (int i = 0; i <3 ; i++) {
                nearResources[i] = 0;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incLevel(){
        this.level++;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setOccupied() {
        this.isShow = true;
    }


    public int getType() {
        return level;
    }

    public void setType(int type) {
        this.level = type;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int[] getNearResources() {
        return nearResources;
    }

    public void setNearResources(int first, int second, int third) {
        this.nearResources[first]++;
        this.nearResources[second]++;
        this.nearResources[third]++;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        int x = this.x;
        int y = this.y;


        switch (this.level) {
            case 1:
                g.fillOval(x - 5, y - 5, 10, 10);
                break;
            case 2:
                g.fillOval(x - 10, y - 10, 20, 20);
                break;
            default:
                break;
        }
    }
}
