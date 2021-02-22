import java.awt.*;

/**
 * Classe que serveix per a generar els Escuts del Jugador (Píxel per Píxel)
 */
public class Escut {
    /**
     * Constant que defineix els píxels d'Alt d'Escut
     */
    public static final int ALT = 5;

    /**
     * Constant que defineix els píxels d'Ample d'Escut
     */
    public static final int AMPLE = 5;

    private int posicioX;
    private int posicioY;

    /**
     * Constructor de la Classe Escut
     * @param posicioX Posició X
     * @param posicioY Posició Y
     */
    public Escut(int posicioX, int posicioY) {
        this.posicioX = posicioX;
        this.posicioY = posicioY;
    }

    /**
     * Mètode que pinta un rectangle de color verd per l'Escut
     * @param g Graphics
     */
    public void pintaEscut(Graphics g) {
        g.setColor(Color.PINK);
        g.fillRect(posicioX, posicioY, AMPLE, ALT);
    }

    /**
     * Mètode Getter de la Posició X de l'Escut
     * @return posicioX Posició X
     */
    public int getPosicioX() {
        return posicioX;
    }

    /**
     * Mètode Getter de la Posició Y de l'Escut
     * @return posicioY Posició Y
     */
    public int getPosicioY() {
        return posicioY;
    }
}
