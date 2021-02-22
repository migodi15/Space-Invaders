import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe Abstracta Alien
 * - implements Cloneable
 */
public abstract class Alien implements Cloneable {
    /**
     * Constant que defineix l'Alt d'ALien
     */
    public static final int ALT = 20;

    /**
     * Constant que defineix l'Ample d'Alien
     */
    public static final int AMPLE = 20;

    private int posicioX;
    private int posicioY;
    private int velocitat;
    private ArrayList<Bala> bales;
    private int punts;
    private Image imatgeA;
    private Image imatgeB;

    /**
     * Constructor de la classe Alien
     * @param posicioX Posicio X on comença l'Alien
     * @param posicioY Posicio Y on comença l'Alien
     * @param velocitat Velocitat de l'Alien
     */
    public Alien(int posicioX, int posicioY, int velocitat) {
        this.posicioX = posicioX;
        this.posicioY = posicioY;
        this.velocitat = velocitat;
        this.bales = new ArrayList<>();
    }

    /**
     * Mètode que mou a la Dreta l'Alien
     */
    public void moureDreta() {
        posicioX += velocitat;
    }

    /**
     * Mètode que mou a l'Esquerra l'Alien
     */
    public void moureEsquerra() {
        posicioX -= velocitat;
    }

    /**
     * Mètode que mou Avall l'Alien
     */
    public void moureAvall() {
        posicioY += velocitat;
    }

    /**
     * Mètode que clona un Alien
     * @return Alien Clonat
     * @throws CloneNotSupportedException Exceptions thrown
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Mètode Abstracte per a diferents Imatges
     * @throws IOException Exception thrown
     */
    public abstract void setImatges() throws IOException;

    /**
     * Mètode Setter de la Imatge 1 de l'Alien
     * @param imatgeA Imatge (moguda) de l'Alien
     */
    public void setImatgeA(Image imatgeA) {
        this.imatgeA = imatgeA;
    }

    /**
     * Mètode Setter de la Imatge 2 de l'Alien
     * @param imatgeB Imatge (moguda) de l'Alien
     */
    public void setImatgeB(Image imatgeB) {
        this.imatgeB = imatgeB;
    }

    /**
     * Mètode Setter dels Punts que dóna Alien
     * @param punts punts del Alien
     */
    public void setPunts(int punts) {
        this.punts = punts;
    }

    /**
     * Mètode Setter de la Posició X
     * @param posicioX Posició X
     */
    public void setPosicioX(int posicioX) {
        this.posicioX = posicioX;
    }

    /**
     * Mètode Setter de la Velocitat
     * @param velocitat Velocitat d'Alien
     */
    public void setVelocitat(int velocitat) {
        this.velocitat = velocitat;
    }

    /**
     * Mètode Getter de les Bales
     * @return bales
     */
    public ArrayList<Bala> getBales() {
        return bales;
    }

    /**
     * Mètode Getter Imatge A
     * @return ImatgeA
     */
    public Image getImatgeA() {
        return imatgeA;
    }

    /**
     * Mètode Getter Imatge B
     * @return ImatgeB
     */
    public Image getImatgeB() {
        return imatgeB;
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
     * Mètode Getter dels Punts
     * @return punts Punts
     */
    public int getPunts() {
        return punts;
    }
}