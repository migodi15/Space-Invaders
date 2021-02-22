import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Classe Menu que gestiona el menú principal
 */
public class Menu implements KeyListener {
    private Finestra finestra;
    private Image fonsDePantalla;
    private Font font;
    private int posicioFletxa;
    private boolean enterClicat;

    /**
     * Constructor de la classe Menu
     * @param finestra finestra
     */
    public Menu(Finestra finestra){
        this.finestra = finestra;
        this.posicioFletxa = 0;
        this.enterClicat = false;
        finestra.addKeyListener(this);
    }

    /**
     * Mètode run que gestiona el menú principal
     * @throws UnsupportedAudioFileException Exception thrown
     * @throws IOException Exception thrown
     * @throws LineUnavailableException Exception thrown
     */
    public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        boolean programaEnExecucio;

        carregaFont();
        carregaImatges();

        do {
            pintaPantalla();

            programaEnExecucio = gestionaMenu();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        } while (programaEnExecucio);
    }

    private boolean gestionaMenu() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (enterClicat) {
            if (posicioFletxa == 0) {
                Joc joc = new Joc(finestra);
                joc.run();
                enterClicat = false;
            } else if (posicioFletxa == 1) {
                Records records = new Records(finestra);
                records.run();
                enterClicat = false;
            } else if (posicioFletxa == 2) {
                Instruccions instruccions = new Instruccions(finestra);
                instruccions.run();
                enterClicat = false;
            } else {
                return false;
            }
        }
        return true;
    }

    private void pintaPantalla() {
        // Esborrem pantalla
        finestra.getG().setFont(font);
        finestra.getG().setColor(Color.BLACK);
        finestra.getG().fillRect(0, 0, Finestra.AMPLE, Finestra.ALT);

        finestra.getG().setColor(Color.WHITE);
        pintaMenu();

        // Pintem fons
        finestra.getG().drawImage(fonsDePantalla, 0, 0, Finestra.AMPLE, Finestra.ALT, null);
        finestra.repaint();
    }

    private void pintaMenu() {
        String[] menu = new String[4];

        menu[0] = "   J U G A R";
        menu[1] = "   R E C O R D S";
        menu[2] = "   I N F O";
        menu[3] = "   S O R T I R";

        for (int i = 0; i < menu.length; i++) {
            if (posicioFletxa == i) {
                menu[i] = ">" + menu[i];
            } else {
                menu[i] = " " + menu[i];
            }
            finestra.getG().drawString(menu[i], Joc.LIMIT_FINESTRA_ESQUERRA + 160, Joc.LIMIT_FINESTRA_SUPERIOR + 130 + 50*i);
        }
    }

    private void carregaFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        finestra.getG().setColor(Color.WHITE);
        finestra.getG().setFont(font);
    }

    private void carregaImatges() {
        try {
            fonsDePantalla = ImageIO.read(new File("img/fons.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que vigila si la key està typed
     * @param e key
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * Mètode que vigila si la key està pressed
     * @param e key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (posicioFletxa != 3) {
                posicioFletxa++;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (posicioFletxa != 0) {
                posicioFletxa--;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterClicat = true;
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