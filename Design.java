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
            game.moveTiles(Board.Direction.DOWN);
            startAnimation();
        } else if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.spawn();
            game.moveTiles(Board.Direction.RIGHT);
            startAnimation();
        } else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.spawn();
            game.moveTiles(Board.Direction.LEFT);
            startAnimation();
        } else if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
            game.spawn();
            game.moveTiles(Board.Direction.UP);
            startAnimation();
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
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawString("2048", frame.getWidth() / 2 - 20, 40);
        g2.drawString("Score: " + game.getScore(), frame.getWidth() / 2 - 20, 70);
        g2.drawString("Press 'Enter' to Start", frame.getWidth() / 2 - 70, frame.getHeight() / 2 + 220);
        g2.drawString("Use 'W,A,S,D' or Arrow Keys to move", frame.getWidth() / 2 - 100, frame.getHeight() / 2 + 250);
        int tileSize = Math.min(frame.getWidth(), frame.getHeight()) / 6;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                drawTiles(g, game.board[i][j], frame.getWidth() / 2 - tileSize * 2 + j * tileSize, frame.getHeight() / 2 - tileSize * 2 + i * tileSize);
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
