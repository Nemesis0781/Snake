package fr.nemesis07.snake;

import javax.swing.*;

public class GameFrame extends JFrame {

    private static GameFrame gameFrame;

    GameFrame() {
        gameFrame = this;
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public static GameFrame getFrame() {
        return gameFrame;
    }
}
