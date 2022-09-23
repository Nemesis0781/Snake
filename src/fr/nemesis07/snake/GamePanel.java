package fr.nemesis07.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (WIDTH*HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    int x[] = new int[GAME_UNITS];
    int y[] = new int[GAME_UNITS];
    int bodyParts = 5;
    int applesEaten;
    int highestScore;
    int appleX;
    int appleY;
    char direction = 'R';
    int running = 0; //running statement 0 = not started, 1 = started, 2 = game over
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        startGame();
    }

    public void startGame() {
        this.addKeyListener(new SnakeKeyAdapter());
        newApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running == 0) {
            waitingStart(g);
        }
        if(running == 1) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if(i == 0)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(new Color(45, 180, 0));

                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.CENTER_BASELINE, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, HEIGHT/10);
        } else if(running == 2) {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt(WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void waitingStart(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.CENTER_BASELINE, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Press any key to start", (WIDTH - metrics.stringWidth("Press any key to start"))/2, HEIGHT/2);
    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        //check if head collide with body
        for (int i = bodyParts; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = 2;
            }
        }
        //check if head collide Left border
        if(x[0] < 0)
            running = 2;

        //check if head collide Right border
        if(x[0] > WIDTH)
            running = 2;

        //check if head collide Top border
        if(y[0] < 0)
            running = 2;

        //check if head collide Bottom border
        if(y[0] > HEIGHT)
            running = 2;

        if(running != 1) timer.stop();
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Monospaced", Font.BOLD, 75));
        FontMetrics gameOver = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - gameOver.stringWidth("Game Over"))/2, HEIGHT/2);

        g.setFont(new Font("Monospaced", Font.BOLD, 25));
        FontMetrics score = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (WIDTH - score.stringWidth("Score: " + applesEaten))/2, HEIGHT-HEIGHT/3);

        this.add(new RetryButton(this));
        if(highestScore < applesEaten) {
            highestScore = applesEaten;
            g.setFont(new Font("Monospaced", Font.BOLD, 25));
            FontMetrics congratulation = getFontMetrics(g.getFont());
            g.drawString("Félicitation nouveau record", (WIDTH - congratulation.stringWidth("Félicitation nouveau record"))/2, HEIGHT-HEIGHT/6);
        }
        g.setFont(new Font("Monospaced", Font.BOLD, 25));
        FontMetrics highScore = getFontMetrics(g.getFont());
        g.drawString("Record actuelle: " + highestScore, (WIDTH - highScore.stringWidth("Record actuelle: " + highestScore))/2, HEIGHT-HEIGHT/8);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running == 1) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class SnakeKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if(running == 0)
                running = 1;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }
}
