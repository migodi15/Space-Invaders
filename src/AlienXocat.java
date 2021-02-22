import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe Alien del Tipus Xocat (serveix per imprimir per pantalla uns segons el xoc de l'Alien)
 */
public class AlienXocat {
    private int tempsXocat;
    private int posicioX;
    private int posicioY;
    private Image imatge;

    /**
     * Constructor de la Classe AlienXocat
     * @param posicioX Posició X
     * @param posicioY Posició Y
     * @throws IOException Exception thrown
     */
    public AlienXocat(int posicioX, int posicioY) throws IOException {
        this.posicioX = posicioX;
        this.posicioY = posicioY;
        this.tempsXocat = 0;
        this.imatge = ImageIO.read(new File("img/alienXoc.png"));
    }

    /**
     * Mètode Setter del temps que porta xocat
     * @param tempsXocat Temps xocat
     */
    public void setTempsXocat(int tempsXocat) {
        this.tempsXocat = tempsXocat;
    }

    /**
     * Mètode Getter del temps que porta xocat
     * @return tempsXocat Temps xocat
     */
    public int getTempsXocat() {
        return tempsXocat;
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
     * Mètode Getter de la Imatge del Alien quan xoca
     * @return imatge Imatge
     */
    public Image getImatge() {
        return imatge;
    }
}
