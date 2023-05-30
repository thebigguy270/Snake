package snakepk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Graphismes extends JPanel implements ActionListener {

	static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int TICK_SIZE = 50;
    static final int BOARD_SIZE = (WIDTH * HEIGHT) / (TICK_SIZE * TICK_SIZE);

    final Font font = new Font("TimesRoman", Font.BOLD, 30);

    int[] snakePosX = new int[BOARD_SIZE];
    int[] snakePosY = new int[BOARD_SIZE];
    int snakeLength;

    Bouffe bouffe;
    int score;

    char direction = 'R';
    boolean boolBouge = false;
    final Timer timer = new Timer(150, this);

    public Graphismes() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (boolBouge) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            if (direction != 'R') {
                                direction = 'L';
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (direction != 'L') {
                                direction = 'R';
                            }
                            break;
                        case KeyEvent.VK_UP:
                            if (direction != 'D') {
                                direction = 'U';
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            if (direction != 'U') {
                                direction = 'D';
                            }
                            break;
                    }
                } else {
                    start();
                }
            }
        });

        start();
    }

    protected void start() {
        snakePosX = new int[BOARD_SIZE];
        snakePosY = new int[BOARD_SIZE];
        snakeLength = 5;
        score = 0;
        direction = 'R';
        boolBouge = true;
        spawnFood();
        timer.start();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        if (boolBouge) {
            g.setColor(Color.BLUE);
            g.fillOval(bouffe.getPosX(), bouffe.getPosY(), TICK_SIZE, TICK_SIZE);

            g.setColor(Color.DARK_GRAY);
            for (int i = 0; i < snakeLength; i++) {
                g.fillRect(snakePosX[i], snakePosY[i], TICK_SIZE, TICK_SIZE);
            }
        } else {
            String scoreText = String.format("Fin de partie... Score: %d... Appuie n'importe quelle touche!", score);
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(scoreText, (WIDTH - getFontMetrics(g.getFont()).stringWidth(scoreText)) / 2, HEIGHT / 2);
        }
    }

    protected void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakePosX[i] = snakePosX[i-1];
            snakePosY[i] = snakePosY[i-1];
        }

        switch (direction) {
            case 'U' -> snakePosY[0] -= TICK_SIZE;
            case 'D' -> snakePosY[0] += TICK_SIZE;
            case 'L' -> snakePosX[0] -= TICK_SIZE;
            case 'R' -> snakePosX[0] += TICK_SIZE;
        }
    }

    protected void spawnFood() {
        bouffe = new Bouffe();
    }

    protected void eatFood() {
        if ((snakePosX[0] == bouffe.getPosX()) && (snakePosY[0] == bouffe.getPosY())) {
            snakeLength++;
            score++;
            spawnFood();
        }
    }

    protected void collisionTest() {
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                boolBouge = false;
                break;
            }
        }

        if (snakePosX[0] < 0 || snakePosX[0] > WIDTH - TICK_SIZE || snakePosY[0] < 0 || snakePosY[0] > HEIGHT - TICK_SIZE) {
            boolBouge = false;
        }

        if (!boolBouge) {
            timer.stop();
        }
    }

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (boolBouge) {
            move();
            collisionTest();
            eatFood();
        }

        repaint();
	}

}
