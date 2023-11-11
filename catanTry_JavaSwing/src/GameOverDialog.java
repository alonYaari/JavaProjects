import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GameOverDialog extends JDialog {
    public GameOverDialog(JFrame parent, String winner) {
        super(parent, "Game Over", true);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Congratulations " + winner + "! You Won!", JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 90));

        panel.add(label, BorderLayout.CENTER); // add label to panel

        JButton restartButton = new JButton("Restart game");
        restartButton.setBounds(200,600,100,100);
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Game.frame.dispose();
                Game newCatanGame = new Game();
                newCatanGame.startGame();
            }
        });

        panel.add(restartButton, BorderLayout.SOUTH); // add button to panel
        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
