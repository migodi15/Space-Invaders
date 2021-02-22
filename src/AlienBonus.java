import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Classe Alien del Tipus Bonus
 * - extends Alien
 */
public class AlienBonus extends Alien{
    /**
     * Constant que redefineix l'Ample d'AlienBonus
     */
    public static final int AMPLE = 40;

    /**
     * Constructor de la Classe AlienBonus
     * @param posicioX Posició X
     * @param posicioY Posició Y
     * @param velocitat Velocitat
     */
    public AlienBonus(int posicioX, int posicioY, int velocitat) {
        super(posicioX, posicioY, velocitat);
        setPunts(100);
        setVelocitat(6);
    }

    /**
     * Mètode Setter de les dues Imatges d'aquest Alien
     * @throws IOException Exception thrown
     */
    @Override
    public void setImatges() throws IOException {
        setImatgeA(ImageIO.read(new File("img/alienBonus.png")));
        setImatgeB(ImageIO.read(new File("img/alienBonus.png")));
    }
}
