import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Classe BalaAlien (correspon a les Bales que disparen els Aliens)
 * - extends Bala
 */
public class BalaAlien extends Bala{
    /**
     * Constructor de la Classe BalaAlien
     * @param posicioX Posició X inicial
     * @param posicioY Posició Y inicial
     */
    public BalaAlien(int posicioX, int posicioY) {
        super(posicioX, posicioY);
    }

    /**
     * Mètode que carrega la Imatge de la Bala Alien
     * @throws IOException Exception thrown
     */
    public void carregaImatge() throws IOException {
        setImatge(ImageIO.read(new File("img/balaAlien.png")));
    }

    /**
     * Mètode que mou la Bala del Alien cap Avall
     */
    @Override
    public void mouBala(){
        setPosicioY(getPosicioY() + VELOCITAT);
    }

    /**
     * Mètode que Pinta la Bala del Alien amb una Imatge
     * @param g Graphics
     */
    @Override
    public void pintaBala(Graphics g){
        g.drawImage(getImatge(), getPosicioX(), getPosicioY(), AMPLE, ALT, null);
    }

}
