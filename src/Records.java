import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Classe Records que controla la lectura i escriptura dels rècords del Joc
 * - implements KeyListener
 */
public class Records implements KeyListener{
    private HashMap<String,Jugador> recordsJugadors;
    private Font font;
    private Font fontPetita;
    private Font fontGran;
    private Finestra finestra;
    private int comptadorLlargadaNom;
    private String nomIntroduit;
    private boolean escapeClicat;
    private boolean enterClicat;
    private Image fonsDePantalla;
    private Image gameOver;
    private Image youWin;

    /**
     * Constructor de la classe Records
     * @param finestra finestra
     */
    public Records(Finestra finestra) {
        this.recordsJugadors = new HashMap<>();
        this.finestra = finestra;
        this.comptadorLlargadaNom = 0;
        this.escapeClicat = false;
        this.nomIntroduit = "";
        this.enterClicat = false;
        finestra.addKeyListener(this);
    }

    /**
     * Mètode run de la classe Records que gestiona la impressió per pantalla del top 10
     * @throws IOException Exception thrown
     */
    public void run() throws IOException {
        this.comptadorLlargadaNom = 0;
        this.escapeClicat = false;
        this.nomIntroduit = "";
        this.enterClicat = false;

        carregaImatges();
        carregaFont();
        pintaFinestra();
        llegeixFitxer();
        imprimeix10MillorsJugadors();

        finestra.getG().setFont(fontPetita);
        finestra.getG().drawString("Clica Escape per tornar al Menu", Joc.LIMIT_FINESTRA_ESQUERRA + 138, Joc.LIMIT_FINESTRA_SUPERIOR + 400);

        finestra.getG().setFont(font);
        finestra.repaint();

        do {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        } while (!escapeClicat);
    }

    private void llegeixFitxer() throws IOException {
        String linia;

        File file = new File("records.txt");
        FileReader fr  = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        while ((linia = br.readLine()) != null) {
            String[] separador = linia.split(" ");
            recordsJugadors.put(separador[0], new Jugador(separador[0], Integer.parseInt(separador[1])));
        }
    }

    private void escriuFitxer() throws IOException {
        File file = new File("records.txt");
        FileWriter fw = new FileWriter(file);

        ArrayList<String> nomsJugadors = new ArrayList<>();
        nomsJugadors.addAll(recordsJugadors.keySet());

        String linies = "";

        for (int i = 0; i < recordsJugadors.size(); i++) {
            linies += nomsJugadors.get(i) + " " + recordsJugadors.get(nomsJugadors.get(i)).getPuntuacio() + "\n";
        }
        fw.write(linies);
        fw.close();
    }

    private void imprimeix10MillorsJugadors() {
        // Primer els ordenem
        String linia;
        List<Jugador> llistaOrdenada = new ArrayList(recordsJugadors.values());
        Collections.sort(llistaOrdenada, Comparator.comparing(Jugador::getPuntuacio).reversed());
        while (llistaOrdenada.size() > 10) {
            llistaOrdenada.remove(10);
        }

        finestra.getG().setColor(Color.WHITE);
        finestra.getG().drawString("TOP 10 PUNTUACIONS", Joc.LIMIT_FINESTRA_ESQUERRA + 125, Joc.LIMIT_FINESTRA_SUPERIOR + 40);
        for (int i = 0; i < llistaOrdenada.size(); i++) {
            linia = llistaOrdenada.get(i).getNom() + "         " + llistaOrdenada.get(i).getPuntuacio();
            finestra.getG().drawString(linia, Joc.LIMIT_FINESTRA_ESQUERRA + 180, Joc.LIMIT_FINESTRA_SUPERIOR + 80 + 30*i);
        }
    }

    /**
     * Mètode que permet entrar el nom del Jugador per a guardar el seu rècord
     * @param jugador Jugador (nom i puntuació)
     * @param jocGuanyat booleà que ens diu si el jugador ha guanyat el joc o no
     * @throws IOException Exception thrown
     */
    public void gestionaNomJugador(Jugador jugador, boolean jocGuanyat) throws IOException {
        this.comptadorLlargadaNom = 0;
        this.escapeClicat = false;
        this.nomIntroduit = "";
        this.enterClicat = false;

        carregaFont();
        carregaImatges();

        do {
            pintaFinestra();
            pintaResultat(jocGuanyat);

            // Preguntem nom jugador
            finestra.getG().setColor(Color.WHITE);
            finestra.getG().setFont(font);
            finestra.getG().drawString("ENTRA NOM (3 CARACTERS)", Joc.LIMIT_FINESTRA_ESQUERRA + 100, Joc.LIMIT_FINESTRA_SUPERIOR + 220);

            // Veiem com s'escriu
            finestra.getG().setFont(fontGran);
            finestra.getG().drawString(nomIntroduit, Joc.LIMIT_FINESTRA_ESQUERRA + 225, Joc.LIMIT_FINESTRA_SUPERIOR + 280);

            if (nomIntroduit.length() == 0) {
                finestra.getG().drawString("_", Joc.LIMIT_FINESTRA_ESQUERRA + 225, Joc.LIMIT_FINESTRA_SUPERIOR + 280);
                finestra.getG().drawString("_", Joc.LIMIT_FINESTRA_ESQUERRA + 251, Joc.LIMIT_FINESTRA_SUPERIOR + 280);
                finestra.getG().drawString("_", Joc.LIMIT_FINESTRA_ESQUERRA + 277, Joc.LIMIT_FINESTRA_SUPERIOR + 280);
            } else if (nomIntroduit.length() == 1) {
                finestra.getG().drawString("_", Joc.LIMIT_FINESTRA_ESQUERRA + 251, Joc.LIMIT_FINESTRA_SUPERIOR + 280);
                finestra.getG().drawString("_", Joc.LIMIT_FINESTRA_ESQUERRA + 277, Joc.LIMIT_FINESTRA_SUPERIOR + 280);
            } else if (nomIntroduit.length() == 2){
                finestra.getG().drawString("_", Joc.LIMIT_FINESTRA_ESQUERRA + 277, Joc.LIMIT_FINESTRA_SUPERIOR + 280);
            }

            pintaInformacioTecles();
            finestra.repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        } while (!escapeClicat && !enterClicat);

        if (enterClicat) {
            guardaRecordJugador(jugador);
        }
    }

    private void guardaRecordJugador (Jugador jugador) throws IOException {
        int puntuacio = jugador.getPuntuacio();

        llegeixFitxer();
        jugador.setNom(nomIntroduit.toUpperCase());

        if (recordsJugadors.containsKey(jugador.getNom())) {
            if (puntuacio > recordsJugadors.get(jugador.getNom()).getPuntuacio()) {
                recordsJugadors.remove(jugador.getNom());
                recordsJugadors.put(jugador.getNom(), jugador);
            }
        } else {
            recordsJugadors.put(jugador.getNom(), jugador);
        }

        escriuFitxer();
    }

    private void pintaFinestra() {
        finestra.getG().setColor(Color.BLACK);
        finestra.getG().fillRect(0, 0, Finestra.AMPLE, Finestra.ALT);
        finestra.getG().drawImage(fonsDePantalla, 0, 0, Finestra.AMPLE, Finestra.ALT, null);
    }

    private void pintaResultat(boolean jocGuanyat) {
        if (jocGuanyat) {
            finestra.getG().drawImage(youWin, Joc.LIMIT_FINESTRA_ESQUERRA + 140, Joc.LIMIT_FINESTRA_SUPERIOR + 20, 250, 150, null);
        } else {
            finestra.getG().drawImage(gameOver, Joc.LIMIT_FINESTRA_ESQUERRA + 170, Joc.LIMIT_FINESTRA_SUPERIOR + 20, 200, 100, null);
        }
    }

    private void pintaInformacioTecles() {
        finestra.getG().setFont(fontPetita);
        finestra.getG().drawString("Clica Enter per registrar", Joc.LIMIT_FINESTRA_ESQUERRA + 160, Joc.LIMIT_FINESTRA_SUPERIOR + 370);
        finestra.getG().drawString("Clica Escape per tornar al Menu", Joc.LIMIT_FINESTRA_ESQUERRA + 140, Joc.LIMIT_FINESTRA_SUPERIOR + 400);
    }

    private void carregaFont() {
        try {
            fontGran = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")).deriveFont(30f);
            fontPetita = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")).deriveFont(10f);
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")).deriveFont(20f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        finestra.getG().setColor(Color.WHITE);
        finestra.getG().setFont(font);
    }

    private void carregaImatges() {
        try {
            fonsDePantalla = ImageIO.read(new File("img/fons.png"));
            gameOver = ImageIO.read(new File("img/game_over.png"));
            youWin = ImageIO.read(new File("img/you_win.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que vigila si la key està typed
     * @param e key
     */
    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if (Character.isDigit(key) || Character.isAlphabetic(key)) {
            if (comptadorLlargadaNom < 3){
                nomIntroduit += key;
                comptadorLlargadaNom++;
            }
        }
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

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (nomIntroduit.length() == 3) {
                enterClicat = true;
            }
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
