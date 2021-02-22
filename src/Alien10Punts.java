import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Classe Alien del Tipus 10 Punts
 * - extends Alien
 */
public class Alien10Punts extends Alien{
    /**
     * Constructor de la Classe Alien10Punts
     * @param posicioX Posició X
     * @param posicioY Posició Y
     * @param velocitat Velocitat
     */
    public Alien10Punts(int posicioX, int posicioY, int velocitat) {
        super(posicioX, posicioY, velocitat);
        setPunts(10);
    }

    /**
     * Mètode Setter de les dues Imatges d'aquest Alien
     * @throws IOException Exception thrown
     */
    @Override
    public void setImatges() throws IOException {
        setImatgeA(ImageIO.read(new File("img/alien10A.png")));
        setImatgeB(ImageIO.read(new File("img/alien10B.png")));
    }


}
