import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Classe Instruccions que mostra la informació dels punts dels Aliens
 * - implements KeyListener
 */
public class Instruccions implements KeyListener {
    private Finestra finestra;
    private Image fonsDePantalla;
    private Image punts;
    private Font font;
    private boolean escapeClicat;

    /**
     * Constructor de la classe Instruccions
     * @param finestra Finestra
     */
    public Instruccions(Finestra finestra) {
        this.finestra = finestra;
        this.escapeClicat = false;
        finestra.addKeyListener(this);
    }

    /**
     * Mètode run que executa la impressió de la imatge de informació
     */
    public void run() {
        carregaFont();
        escapeClicat = false;

        try {
            fonsDePantalla = ImageIO.read(new File("img/fons.png"));
            punts = ImageIO.read(new File("img/punts.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        finestra.getG().setColor(Color.BLACK);
        finestra.getG().fillRect(0, 0, Finestra.AMPLE, Finestra.ALT);
        finestra.getG().drawImage(fonsDePantalla, 0, 0, Finestra.AMPLE, Finestra.ALT, null);

        finestra.getG().drawImage(punts, Joc.LIMIT_FINESTRA_ESQUERRA + 120, Joc.LIMIT_FINESTRA_SUPERIOR + 50, 300, 230, null);
        finestra.getG().setColor(Color.WHITE);

        // Pintem informacio botons
        finestra.getG().drawString("Clica Escape per tornar al Menu", Joc.LIMIT_FINESTRA_ESQUERRA + 138, Joc.LIMIT_FINESTRA_SUPERIOR + 400);
        finestra.repaint();

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        } while (!escapeClicat);
    }

    private void carregaFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")).deriveFont(10f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        finestra.getG().setColor(Color.WHITE);
        finestra.getG().setFont(font);
    }

    /**
     * Mètode que vigila si la key està typed
     * @param e key
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Mètode que vigila si la key està pressed
     * @param e key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escapeClicat = true;
        }
    }

    /**
     * Mètode que vigila si la key està released
     * @param e key
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
