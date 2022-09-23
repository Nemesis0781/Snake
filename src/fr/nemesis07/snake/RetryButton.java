package fr.nemesis07.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RetryButton extends JButton {

    private RetryButton instance;

    public RetryButton(GamePanel gamePanel) {
        instance = this;
        this.setBackground(Color.WHITE);
        this.setSize(150, 30);
        this.setLocation((gamePanel.WIDTH-getWidth())/2, gamePanel.HEIGHT-(gamePanel.HEIGHT/3)+20);
        this.setVisible(true);

        this.setFont(new Font("Monospaced", Font.BOLD, 25));
        this.setText("Retry");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.x = new int[gamePanel.GAME_UNITS];
                gamePanel.y = new int[gamePanel.GAME_UNITS];
                gamePanel.bodyParts = 5;
                gamePanel.applesEaten = 0;
                gamePanel.direction = 'R';
                gamePanel.running = 0;
                gamePanel.startGame();
                SwingUtilities.updateComponentTreeUI(GameFrame.getFrame());
                instance.setVisible(false);
            }
        });
    }

}
