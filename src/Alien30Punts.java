import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Classe Alien del Tipus 30 Punts
 * - extends Alien
 */
public class Alien30Punts extends Alien{
    /**
     * Constructor de la Classe Alien30Punts
     * @param posicioX Posició X
     * @param posicioY Posició Y
     * @param velocitat Velocitat
     */
    public Alien30Punts(int posicioX, int posicioY, int velocitat) {
        super(posicioX, posicioY, velocitat);
        setPunts(30);
    }

    /**
     * Mètode Setter de les dues Imatges d'aquest Alien
     * @throws IOException Exception thrown
     */
    @Override
    public void setImatges() throws IOException {
        setImatgeA(ImageIO.read(new File("img/alien30A.png")));
        setImatgeB(ImageIO.read(new File("img/alien30B.png")));
    }
}
