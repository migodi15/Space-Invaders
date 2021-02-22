import java.awt.*;

/**
 * Classe Abstracta que defineix una Bala
 */
public abstract class Bala {
    /**
     * Constant que defineix l'Alt de Bala
     */
    public static final int ALT = 10;

    /**
     * Constant que defineix l'Ample de Bala
     */
    public static final int AMPLE = 5;

    /**
     * Constant que defineix la Velocitat de Bala
     */
    public static final int VELOCITAT = 7;

    private int posicioX;
    private int posicioY;
    private Image imatge;

    /**
     * Constructor de la Classe Abstracta Bala
     * @param posicioX Posicio X on comença la Bala
     * @param posicioY Posicio Y on comença la Bala
     */
    public Bala(int posicioX, int posicioY) {
        this.posicioX = posicioX;
        this.posicioY = posicioY;
    }

    /**
     * Mètode Abstracte que defineix com es pinta una Bala
     * @param g Graphics
     */
    public abstract void pintaBala(Graphics g);

    /**
     * Mètode Abstracte que defineix com es mou una Bala
     */
    public abstract void mouBala();

    /**
     * Mètode Setter de la imatge de la Bala
     * @param imatge imatge
     */
    public void setImatge(Image imatge) {
        this.imatge = imatge;
    }

    /**
     * Mètode Setter de la Posició Y
     * @param posicioY Posició Y
     */
    public void setPosicioY(int posicioY) {
        this.posicioY = posicioY;
    }

    /**
     * Mètode Getter de la Posició X
     * @return posicioX Posició X
     */
    public int getPosicioX() {
        return posicioX;
    }

    /**
     * Mètode Getter de la Posició Y
     * @return posicioY Posició Y
     */
    public int getPosicioY() {
        return posicioY;
    }

    /**
     * Mètode Getter de la Imatge
     * @return imatge Imatge
     */
    public Image getImatge() {
        return imatge;
    }
}
