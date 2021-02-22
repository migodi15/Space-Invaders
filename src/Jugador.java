import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que defineix un Jugador
 */
public class Jugador {
    /**
     * Constant que defineix l'Alt de Jugador
     */
    public static final int ALT = 20;

    /**
     * Constant que defineix l'Ample de Jugador
     */
    public static final int AMPLE = 40;

    /**
     * Constant que defineix la Velocitat de Jugador
     */
    public static final int VELOCITAT = 8;

    private int vides;
    private int puntuacio;
    private int posicioX;
    private int posicioY;
    private ArrayList<Bala> bales;
    private Image imatge;
    private Image imatgeXoc;
    private String nom;

    /**
     * Constructor principal de la classe Jugador
     */
    public Jugador(){
        this.vides = 3;
        this.puntuacio = 0;
        this.posicioX = Finestra.AMPLE / 2;
        this.posicioY = Joc.LIMIT_FINESTRA_INFERIOR;
        this.bales = new ArrayList<>();
    }

    /**
     * Constructor secundari de la classe Jugador
     * @param nom Nom
     * @param puntuacio Puntuació
     */
    public Jugador(String nom, int puntuacio){
        this.nom = nom;
        this.puntuacio = puntuacio;
    }

    /**
     * Mètode que mou a la Dreta el Jugador
     */
    public void moureDreta() {
        posicioX += VELOCITAT;
    }

    /**
     * Mètode que mou a l'Esquerra el Jugador
     */
    public void moureEsquerra() {
        posicioX -= VELOCITAT;
    }

    /**
     * Mètode que fa que el Jugador dispari una bala
     */
    public void disparaBala() {
        Bala bala = new BalaJugador(posicioX + AMPLE/2, posicioY);
        bales.add(bala);
    }

    /**
     * Mètode que fa que el Jugador perdi una vida
     */
    public void perdVida() {
        vides--;
    }

    /**
     * Mètode Setter del nom
     * @param nom Nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Mètode Setter de la Imatge
     * @param imatge Imatge
     */
    public void setImatge(Image imatge) {
        this.imatge = imatge;
    }

    /**
     * Mètode Setter de la Imatge quan xoca
     * @param imatgeXoc Imatge Xoc
     */
    public void setImatgeXoc(Image imatgeXoc) {
        this.imatgeXoc = imatgeXoc;
    }

    /**
     * Mètode Setter de la puntuació
     * @param puntuacio puntuació
     */
    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    /**
     * Mètode Getter de la Imatge
     * @return imatge Imatge
     */
    public Image getImatge() {
        return imatge;
    }

    /**
     * Mètode Getter de la Imatge quan xoca
     * @return imatgeXoc Imatge
     */
    public Image getImatgeXoc() {
        return imatgeXoc;
    }

    /**
     * Mètode Getter de l'ArrayList de Bales
     * @return bales Bales
     */
    public ArrayList<Bala> getBales() {
        return bales;
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
     * Mètode Getter del Nombre de Vides
     * @return vides Nombre de Vides
     */
    public int getVides() {
        return vides;
    }

    /**
     * Mètode Getter de la Puntuació
     * @return puntuacio Puntuació
     */
    public int getPuntuacio() {
        return puntuacio;
    }

    /**
     * Mètode Getter del Nom del Jugador
     * @return nom Nom del Jugador
     */
    public String getNom() {
        return nom;
    }
}
