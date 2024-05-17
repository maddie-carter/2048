import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Design extends JPanel implements KeyListener, ActionListener {
    private Board game;
    private JFrame frame;
    private Timer timer;
    private int animationDelay = 20; // Milliseconds between animation frames
    private int animationSteps = 20; // Number of animation steps

    public Design() {
        game = new Board();
        frame = new JFrame("2048");
        frame.add(this);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(this);

        timer = new Timer(animationDelay, this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key presses to move tiles
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            game = new Board();
            game.spawn();
            game.spawn();
            frame.repaint();
        } else if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) {
            game.spawn();
            game.down();
            startAnimation();
        } else if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.spawn();
            game.right();
            startAnimation();
        } else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.spawn();
            game.left();
            startAnimation();
        } else if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
            game.spawn();
            game.up();
            startAnimation();
        }
        if (game.gameOver()) {
            if (e.getKeyChar() == 'y') {
                restartGame();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle animation timer
        game.incrementAnimationStep();
        if (game.getAnimationStep() > animationSteps) {
            stopAnimation();
            game.resetAnimationStep();
            frame.repaint();
        } else {
            frame.repaint();
        }
    }

    private void startAnimation() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    private void stopAnimation() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }
    private void restartGame() {
        game = new Board();
        game.spawn();
        game.spawn();
        //game.gameOver() = true;
        frame.repaint();
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int centerY = frame.getHeight() / 2;
        int centerX = frame.getWidth() / 2;
        int textWidth2048 = g2.getFontMetrics().stringWidth("2048");
        g2.drawString("2048", (frame.getWidth() - textWidth2048) / 2, centerY - 100);
        String scoreText = "Score: " + game.getScore();
        int textWidthScore = g2.getFontMetrics().stringWidth(scoreText);
        g2.drawString(scoreText, (frame.getWidth() - textWidthScore) / 2, centerY - 70);
        g2.drawString("Press 'Enter' to Start", (frame.getWidth() - g2.getFontMetrics().stringWidth("Press 'Enter' to Start")) / 2, frame.getHeight() / 2 + 220);
        g2.drawString("Use 'W,A,S,D' or Arrow Keys to move", (frame.getWidth() - g2.getFontMetrics().stringWidth("Use 'W,A,S,D' or Arrow Keys to move")) / 2, frame.getHeight() / 2 + 250);
        int tileSize = Math.min(frame.getWidth(), frame.getHeight()) / 6;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                drawTiles(g, game.board[i][j], frame.getWidth() / 2 - tileSize * 2 + j * tileSize, frame.getHeight() / 2 - tileSize * 2 + i * tileSize);
            }
        }
        if (game.gameOver()) {
            String gameOverText = "Game Over! Press 'Y' to restart.";
            int textWidthGameOver = g2.getFontMetrics().stringWidth(gameOverText);
            g2.drawString(gameOverText, centerX - textWidthGameOver / 2, frame.getHeight() / 2);
            g2.setColor(Color.gray);
            g2.fillRect(140, 50, 250, 250);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    g2.setColor(Color.RED);
                    g2.fillRoundRect(j * 60 + 150, i * 60 + 60, 50, 50, 5, 5);
                    g2.setColor(Color.black);
                    g.drawString("GAME", j * 60 + 160, i * 60 + 75);
                    g.drawString("OVER", j * 60 + 160, i * 60 + 95);
                }
            }
        }
    }

    public void drawTiles(Graphics g, Square tile, int x, int y) {
        int tileValue = tile.getValue();
        Graphics2D g2 = (Graphics2D) g;
        int tileSize = Math.min(frame.getWidth(), frame.getHeight()) / 6;
        g2.setColor(Color.lightGray);
        g2.fillRoundRect(x, y, tileSize, tileSize, 10, 10);
        g2.setColor(Color.darkGray);  // You can adjust the color if needed
        g2.drawRoundRect(x, y, tileSize, tileSize, 10, 10);

        if (tileValue > 0) {
            g2.setColor(tile.getColor());
            g2.fillRoundRect(x, y, tileSize, tileSize, 10, 10);
            Font font = new Font("Arial", Font.BOLD, tileSize / 3);
            g2.setFont(font);
            String valueStr = String.valueOf(tileValue);
            int strWidth = g2.getFontMetrics().stringWidth(valueStr);
            int strHeight = g2.getFontMetrics().getHeight();
            int textX = x + (tileSize - strWidth) / 2;
            int textY = y + (tileSize + strHeight) / 2;
            g2.setColor(Color.black);
            g2.drawString(valueStr, textX, textY);
        }
    }

    public void keyReleased( KeyEvent e )
    {

    }


    public void keyTyped( KeyEvent e )
    {
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Design();
            }
        });
    }
}
