package snakepk;

import java.util.Random;

public class Bouffe {
	
	 private final int posX;
	private final int posY;
	
	public Bouffe() {
        posX = generatePos(Graphismes.WIDTH);
        posY = generatePos(Graphismes.HEIGHT);
    }
	private int generatePos(int size) {
        Random random = new Random();
        return random.nextInt(size / Graphismes.TICK_SIZE) * Graphismes.TICK_SIZE;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}
