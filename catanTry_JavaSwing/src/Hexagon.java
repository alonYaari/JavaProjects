import java.awt.*;

public class Hexagon {
    private int x;
    private int y;
    private int width;
    private int height;
    private Polygon polygon;

    private int resourceType;
    private Color color;
    private int number;

    public Hexagon(int x, int y, int width, int height, int resourceType, int number) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.resourceType  = resourceType;
        this.number  = number;

        // Calculate the vertices of the hexagon
        int[] xPoints = new int[] {x, x + width / 2, x + width, x + width, x + width / 2, x};
        int[] yPoints = new int[] {y + height / 4, y, y + height / 4, y + height - height / 4, y + height, y + height - height / 4};

        polygon = new Polygon(xPoints, yPoints, 6);
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public int getResourceType() {
        return resourceType;
    }

    public void setResourceType(int resourceType) {
        this.resourceType = resourceType;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);

        // Draw the number
        int number = this.getNumber();
        if (number > 0) {
            Rectangle bounds = polygon.getBounds();
            int x = bounds.x + bounds.width / 2 - 3;
            int y = bounds.y + bounds.height / 2 + 2;
            g.setFont(new Font("default", Font.BOLD, 20));
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(number), x, y);
        }
    }
}
