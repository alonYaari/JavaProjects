import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Random;

public class CatanBoard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static Hexagon[][] hexagons = new Hexagon[5][];
    public static Settlement [] settlements;
    public static ArrayList<Bridge> bridges;

    public CatanBoard() {
        this.setLayout(null);
        this.setBounds(0,0,1500,1000);
        this.addMouseListener(new MouseHandler(this));
        settlements = new Settlement[19*6];

        // Initialize the hexagons array
        hexagons[0] = new Hexagon[3];
        hexagons[1] = new Hexagon[4];
        hexagons[2] = new Hexagon[5];
        hexagons[3] = new Hexagon[4];
        hexagons[4] = new Hexagon[3];

        int randomNumber;
        Random random = new Random();

        // Initiate each resource.
        int resourcArr [] = new int [6];
        for (int i = 1;i < resourcArr.length; i++)
            resourcArr[i] = 4;
        resourcArr[0] = 1;

        // Needs that 2 resources will be initiate with 3 instead of 4.
        int toDecrease = random.nextInt(1,5);
        resourcArr[toDecrease]--;
        toDecrease = random.nextInt(1,5);
        while (resourcArr[toDecrease] == 3)
          toDecrease = random.nextInt(1,5);
        resourcArr[toDecrease]--;

        // For the numbers that will appear on the hexagons.
        int availableNums [] = new int[]{0,0,1,2,2,2,2,0,2,2,2,2,1};
        int count = 17;

        // Create the hexagons
        // Determines the size and the place of the hexagons.
        int x = 380;
        int y = 100;
        int width = 150;
        int height = 150;
        int padding = 10;

        // Running through all the hexagons.
        for (int i = 0; i < hexagons.length; i++) {
            int numHexagonsInRow = hexagons[i].length;
            int startX = x + (numHexagonsInRow - 3) * (width / 2);

            for (int j = 0; j < numHexagonsInRow; j++) {

                // Needs to randomly give a "resource" to each hexagon.
                int resourceNum = random.nextInt(6);
                while (resourcArr[resourceNum]-- <= 0 )
                    resourceNum = random.nextInt(6);

                if (resourceNum != 0) {// Isn't desert.
                    // Needs to randomly give a number to each hexagon.
                    randomNumber = random.nextInt(2,12);
                    while (availableNums[randomNumber]-- <= 0 && count > 0)
                        randomNumber = random.nextInt(2,12);
                    count--;
                    if(count == -1)
                        randomNumber = findLast(availableNums);
                }
                else
                    randomNumber = 0; // When it is a desert.

                switch (i){
                    case 0:
                    case 4:
                        hexagons[i][j] = new Hexagon(startX + j * (width + padding) + 155  , y, width, height, resourceNum,randomNumber);
                        break;
                    case 1:
                    case 3:
                        hexagons[i][j] = new Hexagon(startX + j * (width + padding), y, width, height, resourceNum,randomNumber);
                        break;
                    case 2:
                        hexagons[i][j] = new Hexagon(startX + j * (width + padding) - 155 , y, width, height, resourceNum,randomNumber);
                        break;
                    default:
                        break;
                }
            }
            y += height - 28;
        }
        buildSettlements();
        bridges = new ArrayList<Bridge>();
        buildBridges();
    }

    public static void lookForNearRoads(Point start, Point end, int playerNumber){
        Point middle = new Point((start.x + end.x) / 2, (start.y + end.y) / 2);
        for (int i=0; i<bridges.size(); i++){
            Point centerOfOther = new Point((bridges.get(i).getxStart() + bridges.get(i).getxEnd()) / 2,
                    (bridges.get(i).getyStart() + bridges.get(i).getyEnd()) / 2);
            if (middle.distance(centerOfOther.x, centerOfOther.y)  > 1 && middle.distance(centerOfOther.x, centerOfOther.y) < 20)
                addBridge(new Point(bridges.get(i).getxStart(), bridges.get(i).getyStart()),
                        new Point(bridges.get(i).getxEnd(), bridges.get(i).getyEnd()), playerNumber, 0);
        }
    }
    public static void addBridge(Point start, Point end, int playerNumber, int again){
        for (Bridge bridge: bridges){
            if (bridge.getxStart() == start.x && bridge.getyStart() == start.y &&
                    bridge.getxEnd() == end.x && bridge.getyEnd() == end.y && bridge.getPlayerNum() == 0){
                bridge.setShow(true);
                bridge.setPlayerNum(playerNumber);
                drawBridge(Game.frame.getGraphics());

                // In order to draw the closest road.
                if (again == 1)
                    lookForNearRoads(start, end, playerNumber);
                else
                    break;
            }
        }
    }

    public static Point tileIndexes(int x, int y){
        Point curr = new Point(x,y);

        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                if (hexagons[i][j].getPolygon().contains(curr))
                    return new Point(i, j);
            }
        }
        return null;
    }

    public static boolean alreadyIn(int x, int y){
        for (int i = 0; i<settlements.length; i++){
            if (settlements[i] != null){
                if (Math.abs(settlements[i].getX() - x) < 25 && Math.abs(settlements[i].getY() - y) < 25)
                    return true;
            }
        }
        return false;
    }
    public void buildBridges() {
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                Hexagon hexagon = hexagons[i][j];
                for (int k =0 ;k < 5; k++){
                    bridges.add(new Bridge(hexagon.getPolygon().xpoints[k], hexagon.getPolygon().ypoints[k],
                            hexagon.getPolygon().xpoints[k + 1], hexagon.getPolygon().ypoints[k + 1],i, j));
                }
                bridges.add(new Bridge(hexagon.getPolygon().xpoints[5], hexagon.getPolygon().ypoints[5],
                        hexagon.getPolygon().xpoints[0], hexagon.getPolygon().ypoints[0], i, j));
            }
        }
    }

    public static void giveResources(int diceResult){
        for(int x = 0;x < hexagons.length;x++){
            for (int y=0; y < hexagons[x].length;y++){
                Hexagon curr = hexagons[x][y];
                if (curr.getNumber() == diceResult){
                    for (Settlement settlement : settlements){
                        if (settlement != null && settlement.getPlayerNum() != -1){
                            int [] xPoints = hexagons[x][y].getPolygon().xpoints;
                            int [] yPoints = hexagons[x][y].getPolygon().ypoints;

                            for (int i=0;i<6;i++){
                                if(Math.abs(settlement.getX() - xPoints[i]) < 15 && Math.abs(settlement.getY() - yPoints[i]) < 15){
                                    if(settlement.getLevel() > 1)
                                        Game.getCurrentPlayer().incResource(curr.getResourceType());
                                    Game.getCurrentPlayer().incResource(curr.getResourceType());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void findResources(Settlement settlement){
        boolean firstTurn = Game.getCurrentPlayer().getVictoryPoints() < 2 ? true : false;
        for(int x = 0;x<hexagons.length;x++){
            for (int y=0; y<hexagons[x].length;y++){
                Hexagon curr = hexagons[x][y];
                int [] xPoints = hexagons[x][y].getPolygon().xpoints;
                int [] yPoints = hexagons[x][y].getPolygon().ypoints;

                for (int i=0;i<6;i++){
                    if(Math.abs(settlement.getX() - xPoints[i]) < 15 && Math.abs(settlement.getY() - yPoints[i]) < 15){
                        if(firstTurn)
                            Game.getCurrentPlayer().incResource(curr.getResourceType());
                    }
                }
            }
        }

        if (firstTurn)
            Game.getCurrentPlayer().setFirstTurn(false);
    }
    public static void buildSettlements(){
        int count = 0;
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                Polygon p = hexagons[i][j].getPolygon();
                for (int k = 0; k<p.npoints;k++) {
                    if (!alreadyIn(p.xpoints[k],p.ypoints[k]) && count < settlements.length){
                        settlements[count++] = new Settlement(p.xpoints[k],p.ypoints[k]);
                    }
                }
            }
        }
    }
    public static int findIndexOfSettle(int x, int y){
        for (int i=0; i < settlements.length;i++){
            if (settlements[i] != null && Math.abs(settlements[i].getX() - x) < 25 && Math.abs(settlements[i].getY() - y) < 25)
                return i;
        }
        return -1;
    }

    public static boolean canBuildABridge(Point bridgeStart,Point bridgeEnd, Player player){
        if(player.getResources(1) == 0 || player.getResources(3) == 0){
            System.out.println("not enough resources");
            if (player.getNumOfBridge() > 1){
                System.out.println("player " + player.getPlayerNum() + " dont have enough resources to build a bridge");
                return false;
            }
        }

        boolean found = false;

        for (Bridge bridge: bridges){
            if (bridge.isShow()){
                if ((Math.abs(bridge.getxStart() - bridgeStart.x) < 2 && (Math.abs(bridge.getyStart() - bridgeStart.y) < 2)) &&
                        ((Math.abs(bridge.getxEnd() - bridgeEnd.x)) < 2 && (Math.abs(bridge.getyEnd() - bridgeEnd.y) < 2))) {
                    System.out.println("bridge is already settled on that place");
                    return false;
                }

                if ((Math.abs(bridge.getxStart() - bridgeStart.x) < 15 && (Math.abs(bridge.getyStart() - bridgeStart.y) < 15)) ||
                        ((Math.abs(bridge.getxEnd() - bridgeStart.x)) < 15 && (Math.abs(bridge.getyEnd() - bridgeStart.y) < 15)))
                    found = true;
            }
        }

        if (!found) {
            for (Settlement settlement : settlements){
                if (settlement != null){
                    if (settlement.getPlayerNum() != -1){
                        if ((Math.abs(settlement.getX() - bridgeStart.x) < 15 && (Math.abs(settlement.getY() - bridgeStart.y) < 15)) ||
                                ((Math.abs(settlement.getX() - bridgeEnd.x)) < 15 && (Math.abs(settlement.getY() - bridgeEnd.y) < 15)))
                            found = true;
                    }
                }
            }
        }

        if(!found){
            System.out.println("Player " + player.getPlayerNum() + " cant build a bridge there because of the rules.");
            return false;
        }
        if (player.getNumOfBridge() > 1){
            player.decResource(1);
            player.decResource(3);
        }

        player.incNumOfBridge();
        return true;
    }
    public static Point [] determineBridgePos(int x, int y){
        for (Bridge bridge : bridges){
            // Calculate distance from click to line segment
            double dist = Line2D.ptSegDist(bridge.getxStart(), bridge.getyStart(), bridge.getxEnd(), bridge.getyEnd(), x, y);
            if (dist < 5) { // If click is within 5 pixels of line segment, add a new bridge
                return new Point[] {new Point(bridge.getxStart(), bridge.getyStart()),
                        new Point(bridge.getxEnd(), bridge.getyEnd())};
            }
        }
        return new Point[]{null, null};
    }

    public static boolean canBuildSettlement(int x, int y, Player player){
        if(player.getResources(1) == 0 || player.getResources(3) == 0 || player.getResources(4) == 0 || player.getResources(5) == 0){
            System.out.println("player " + player.getPlayerNum() + " dont have enough resources to build a settlement");
            return false;
        }

        for (Bridge bridge: bridges){
            if ((Math.abs(bridge.getxStart() - x) < 5 && (Math.abs(bridge.getyStart() - y) < 5)) ||
                    ((Math.abs(bridge.getxEnd() - x)) < 5 && (Math.abs(bridge.getyEnd() - y) < 5))) {
                return true;
            }
        }

        System.out.println("player " + player.getPlayerNum() + " cant build a settlement at (" +x + ", " +y +")");
        return false;
    }

    public static boolean canBuildACity(Player player){
        if (player.getResources(2) < 3 || player.getResources(5) < 2) {
            System.out.println("dont have enough resources to build a city");
            return false;
        }

        player.setResources(2, player.getResources(2) - 3);
        player.setResources(5, player.getResources(5) - 2);
        return true;
    }


    public static Settlement determineSettlementPos(int x, int y, Player player)
    {
        int index = findIndexOfSettle(x, y);
        if (player.getVictoryPoints() < 2 || (index == -1 && canBuildSettlement(x, y, player)) || (index != -1 && canBuildACity(player))  ){
            for (int i = 0; i < hexagons.length; i++) {
                for (int j = 0; j < hexagons[i].length; j++) {
                    Polygon currPolygon = hexagons[i][j].getPolygon();
                    for (int vertex = 0; vertex < currPolygon.npoints; vertex++) {
                        int currx = currPolygon.xpoints[vertex];
                        int curry = currPolygon.ypoints[vertex];
                        if (Math.abs(currx - x) < 10 && Math.abs(curry - y) < 10) {
                            if(settlements[index].getLevel() == 0 ||
                                    (settlements[index].getLevel() != 0 && Game.getCurrentPlayer().getPlayerNum() == settlements[index].getPlayerNum())){
                                if (settlements[index].getLevel() == 1){
                                    if(!canBuildACity(player)){
                                        return new Settlement(1,1);
                                    }

                                } else if (player.getVictoryPoints() > 1) {
                                    player.decResource(1);
                                    player.decResource(3);
                                    player.decResource(4);
                                    player.decResource(5);
                                }

                                settlements[index].setOccupied();
                                settlements[index].setPlayerNum(player.getPlayerNum());
                                settlements[index].incLevel();
                                findResources(settlements[index]);
                                player.incVictoryPoints();

                            }
                            return index != -1 ? settlements[index] : new Settlement(2,2);
                        }
                    }
                }
            }
        }
        return index != -1 ? new Settlement(1,1) : new Settlement(2,2);
    }

    public static void drawBridge(Graphics graphics){
        for (Bridge curr :bridges){
            if (curr.isShow() == true){
                curr.draw(graphics);
            }
        }
    }
    private int findLast(int availableNums[]){
        for (int i=0;i<availableNums.length;i++){
            if(availableNums[i]>0)
                return i;
        }
        return 0;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the hexagons
        for (int i = 0; i < hexagons.length; i++) {
            for (int j = 0; j < hexagons[i].length; j++) {
                switch (hexagons[i][j].getResourceType()){
                    case 0:
                        hexagons[i][j].setColor(Color.BLACK);
                        break;
                    case 1:
                        hexagons[i][j].setColor(new Color(0,100,0)); // Dark gray, tree
                        break;
                    case 2:
                        hexagons[i][j].setColor(Color.RED); // Clay
                        break;
                    case 3:
                        hexagons[i][j].setColor(Color.gray); // rocks
                        break;
                    case 4:
                        hexagons[i][j].setColor(new Color(0, 255, 51)); // Sheep
                        break;
                    case 5:
                        hexagons[i][j].setColor(Color.yellow); // Wheat
                        break;
                    default: break;
                }
                hexagons[i][j].draw(g);
            }
        }
    }
}
