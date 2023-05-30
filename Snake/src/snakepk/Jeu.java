package snakepk;

import javax.swing.*;

public class Jeu extends JFrame {
	public Jeu() {
		this.add(new Graphismes());
        this.setTitle("Snake Game");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
	}
}
