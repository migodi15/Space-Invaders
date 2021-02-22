import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

/**
 * Classe Finestra
 * - extends Frame
 * - implements WindowListener
 */
public class Finestra extends Frame implements WindowListener {
    /**
     * Constant que defineix l'Alt de Finestra
     */
    public static final int ALT = 900;

    /**
     * Constant que defineix l'Ample de Finestra
     */
    public static final int AMPLE = 700;

    private Joc joc;
    private Menu menu;
    private Graphics g;
    private Image im;

    /**
     * Constructor de la classe Finestra
     */
    public Finestra() {
        addWindowListener(this);
        this.menu = new Menu(this);
        this.joc = new Joc(this);
    }

    /**
     * Mètode que inicialitza la Finestra
     */
    public void inicialitzaFinestra(){
        setSize(AMPLE, ALT);
        setVisible(true);

        im=createImage(AMPLE, ALT);
        g=im.getGraphics();
    }

    /**
     * Mètode que pinta la Finestra
     * @param g Graphics
     */
    public void paint(Graphics g) {
        g.drawImage(im,0,0,null);
    }

    /**
     * Mètode que actualitza la Finestra
     * @param g Graphics
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Mètode Getter dels Graphics
     * @return g Graphics
     */
    public Graphics getG() {
        return g;
    }

    /**
     * Mètode Getter del Menú
     * @return menu
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * windowOpened
     * @param e WindowEvent
     */
    @Override
    public void windowOpened(WindowEvent e) { }

    /**
     * windowClosing
     * @param e WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    /**
     * windowClosed
     * @param e WindowEvent
     */
    @Override
    public void windowClosed(WindowEvent e) { }

    /**
     * windowIconified
     * @param e WindowEvent
     */
    @Override
    public void windowIconified(WindowEvent e) { }

    /**
     * windowDeiconified
     * @param e WindowEvent
     */
    @Override
    public void windowDeiconified(WindowEvent e) { }

    /**
     * windowActivated
     * @param e WindowEvent
     */
    @Override
    public void windowActivated(WindowEvent e) { }

    /**
     * windowDeactivated
     * @param e WindowEvent
     */
    @Override
    public void windowDeactivated(WindowEvent e) { }
}