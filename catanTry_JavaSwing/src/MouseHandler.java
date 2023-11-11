import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

public class MouseHandler implements MouseListener {
    JPanel panel;
    public MouseHandler(JPanel frame) {
        this.panel = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Player currentPlayer = Game.getCurrentPlayer();
        Settlement clicked = CatanBoard.determineSettlementPos(e.getX(), e.getY(), currentPlayer);
        if (clicked.getX() > 2){
            JPanel jPanel = new JPanel();
            jPanel.setBackground(currentPlayer.getPlayerColor());
            jPanel.setLayout(null);
            jPanel.setLocation(clicked.getX(),clicked.getY());
            Point placed =  clicked.getLevel() == 1 ? new Point(10,10) : new Point(20,20);
            jPanel.setSize(placed.x, placed.y);
            panel.add(jPanel);
            panel.revalidate();
            panel.repaint();

            if (currentPlayer.getVictoryPoints() == 10)
                new GameOverDialog(Game.frame, currentPlayer.getName());
        }
        else {
            if (clicked.getX() != 1 ){
                Point bridge [] = CatanBoard.determineBridgePos(e.getX() + 10, e.getY());
                if (bridge[0] != null)
                    if (CatanBoard.canBuildABridge(bridge[0], bridge[1], currentPlayer))
                        CatanBoard.addBridge(bridge[0], bridge[1], currentPlayer.getPlayerNum(), 1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // not used
    }

}

