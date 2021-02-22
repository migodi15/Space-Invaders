import java.awt.*;

/**
 * Classe BalaJugador (correspon a les Bales que dispara el Jugador)
 * - extends Bala
 */
public class BalaJugador extends Bala {
    /**
     * Constructor de la Classe BalaJugador
     * @param posicioX Posició X inicial
     * @param posicioY Posició Y inicial
     */
    public BalaJugador(int posicioX, int posicioY) {
        super(posicioX, posicioY);
    }

    /**
     * Mètode que Mou la Bala d'una forma determinada cap Amunt
     */
    @Override
    public void mouBala() {
        setPosicioY(getPosicioY() - VELOCITAT);
    }

    /**
     * Mètode que Pinta la Bala en forma de línia
     * @param g Graphics
     */
    @Override
    public void pintaBala(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(getPosicioX(), getPosicioY(), getPosicioX(), getPosicioY() + ALT);

    }
}
