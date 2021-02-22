import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe que defineix el Joc Space Invaders
 * - implements KeyListener
 */
public class Joc implements KeyListener {
    /**
     * Constant per controlar el moviment Avall dels Aliens
     */
    public static final int AVALL = 0;

    /**
     * Constant per controlar el moviment Dreta dels Aliens
     */
    public static final int DRETA = 1;

    /**
     * Constant per controlar el moviment Esquerra dels Aliens
     */
    public static final int ESQUERRA = 2;

    /**
     * Constant que controla la probabilitat de disparar dels Aliens
     */
    public static final int PROBABILITAT_ALIEN_DISPARA_BALA = 1;

    /**
     * Constant que controla la probabilitat d'aparèixer de l'Alien Bonus
     */
    public static final int PROBABILITAT_ALIEN_BONUS = 1;

    /**
     * Constant que defineix el limit de la finestra per l'esquerra
     */
    public static final int LIMIT_FINESTRA_ESQUERRA = 80;

    /**
     * Constant que defineix el limit de la finestra per la dreta
     */
    public static final int LIMIT_FINESTRA_DRETA = 620;

    /**
     * Constant que defineix el límit de la finestra superior
     */
    public static final int LIMIT_FINESTRA_SUPERIOR = 330;

    /**
     * Constant que defineix el límit de la finestra inferior
     */
    public static final int LIMIT_FINESTRA_INFERIOR = 750;

    /**
     * Constant que defineix el límit de l'inici dels escuts
     */
    public static final int LIMIT_INICI_ESCUTS = 700;

    private Finestra finestra;
    private ArrayList<Alien> aliens;
    private ArrayList<AlienXocat> aliensXocats;
    private ArrayList<Bala> balesAlienMort;
    private Alien alienBonus;
    private Jugador jugador;
    private Records records;
    private ArrayList<Escut>[] escuts;

    private boolean dretaJugadorActivada;
    private boolean esquerraJugadorActivada;
    private long antiSpam;

    private Image fonsDePantalla;
    private Clip explotaAlien;
    private Clip explotaJugador;
    private Clip apareixAlienBonus;
    private Clip tretJugador;
    private Clip musicaDeFons;
    private Font font;

    /**
     * Constructor de la classe Joc
     * @param finestra finestra
     */
    public Joc(Finestra finestra) {
        this.finestra = finestra;
        this.records = new Records(finestra);
        this.aliens = new ArrayList<>();
        this.aliensXocats = new ArrayList<>();
        this.balesAlienMort = new ArrayList<>();
        this.jugador = new Jugador();
        this.alienBonus = null;
        this.escuts = new ArrayList[4];
        for (int i = 0; i < 4; i++) {
            this.escuts[i] = new ArrayList<>();
        }
        this.antiSpam = 0;
        finestra.addKeyListener(this);
    }

    /**
     * Mètode que executa el Joc Space Invaders
     * @throws IOException Exception thrown
     * @throws LineUnavailableException Exception thrown
     * @throws UnsupportedAudioFileException Exception thrown
     */
    public void run() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        int sentit = DRETA;
        int sentitAnterior = DRETA;
        int comptadorCanviSentit = 1;
        carregaImatges();
        carregaSorolls();
        carregaFont();
        inicialitzaAliens();
        inicialitzaEscuts();
        musicaDeFons.loop(Clip.LOOP_CONTINUOUSLY);

        do {
            if (sentit != AVALL) {
                sentitAnterior = sentit;
            }

            sentit = canviaSentit(sentitAnterior, comptadorCanviSentit);

            if (comptadorCanviSentit == 120){
                comptadorCanviSentit = 0;
            } else {
                comptadorCanviSentit++;
            }

            inicialitzaAlienBonus();
            creaBalesAliens();
            fesMovimentsAliens(sentit);
            fesMovimentsJugador();
            fesMovimentsBales();
            esborraBalesForaFinestra();
            esborraAlienBonus();

            sumaTempsAliensXocats();
            esborraAliensXocats();

            gestionaXocs();
            pintaPantalla(comptadorCanviSentit);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        } while (jocEnCurs());


        if (aliens.size() == 0) {
            records.gestionaNomJugador(jugador, true);

        } else if (jugador.getVides() == 0 || !aliensPerSobreEscut()) {
            records.gestionaNomJugador(jugador, false);

        }

        musicaDeFons.stop();
        apareixAlienBonus.stop();
    }

    private void sumaTempsAliensXocats() {
        if (aliensXocats != null) {
            for (int i = 0; i < aliensXocats.size(); i++) {
                aliensXocats.get(i).setTempsXocat(aliensXocats.get(i).getTempsXocat() + 1);
            }
        }
    }

    private int canviaSentit(int sentitAnterior, int comptadorCanviSentit) {
        if (sentitAnterior == DRETA && comptadorCanviSentit == 120){
            return ESQUERRA;
        } else if (sentitAnterior == ESQUERRA && comptadorCanviSentit == 120){
            return DRETA;
        } else if (comptadorCanviSentit > 112){
            return AVALL;
        } else{
            return sentitAnterior;
        }
    }

    private void inicialitzaAliens() throws IOException {
        int posicioInicialX;
        int posicioInicialY;

        // Els posicionem en forma de matriu
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11 ; j++) {
                posicioInicialX = LIMIT_FINESTRA_ESQUERRA + 30*j;
                posicioInicialY = LIMIT_FINESTRA_SUPERIOR + 8 + 30*i;

                Alien alien;

                if (i == 0) {
                    alien = new Alien30Punts(posicioInicialX, posicioInicialY,2);
                } else if (i < 3) {
                    alien = new Alien20Punts(posicioInicialX, posicioInicialY,2);
                } else {
                    alien = new Alien10Punts(posicioInicialX, posicioInicialY,2);
                }

                alien.setImatges();
                aliens.add(alien);
            }
        }
    }

    private void inicialitzaAlienBonus() throws IOException {
        if (alienBonus == null) {
            int nombreAleatori;
            nombreAleatori = (int) Math.floor(Math.random() * 300);
            if (aliens.get(0).getPosicioY() > LIMIT_FINESTRA_SUPERIOR + 30 &&
                    nombreAleatori == PROBABILITAT_ALIEN_BONUS) {
                alienBonus = new AlienBonus(LIMIT_FINESTRA_ESQUERRA - 80, LIMIT_FINESTRA_SUPERIOR + 10, 4);
                alienBonus.setImatges();
                apareixAlienBonus.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    private void inicialitzaEscuts() {
        for (int i = 0; i < 4; i++) {
            // Fila 0
            for (int j = 0; j < 8; j++) {
                escuts[i].add(new Escut(145 + 5*j + 125*i, LIMIT_INICI_ESCUTS));
            }

            // Fila 1
            for (int j = 0; j < 10; j++) {
                escuts[i].add(new Escut(140 + 5*j + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT));
            }

            // Fila 2
            for (int j = 0; j < 10; j++) {
                escuts[i].add(new Escut(140 + 5*j + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*2));
            }

            // Fila 3
            escuts[i].add(new Escut(140 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*3));
            escuts[i].add(new Escut(145 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*3));
            escuts[i].add(new Escut(180 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*3));
            escuts[i].add(new Escut(185 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*3));

            // Fila 4
            escuts[i].add(new Escut(140 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*4));
            escuts[i].add(new Escut(145 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*4));
            escuts[i].add(new Escut(180 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*4));
            escuts[i].add(new Escut(185 + 125*i,LIMIT_INICI_ESCUTS + Escut.ALT*4));
        }
    }

    private void fesMovimentsAliens(int sentit) {
        if (sentit == DRETA){
            for (int i = 0; i < aliens.size(); i++) {
                aliens.get(i).moureDreta();
            }
        } else if (sentit == ESQUERRA){
            for (int i = 0; i < aliens.size(); i++) {
                aliens.get(i).moureEsquerra();
            }
        } else{
            for (int i = 0; i < aliens.size(); i++) {
                aliens.get(i).moureAvall();
            }
        }

        if (alienBonus != null) {
            alienBonus.moureDreta();
        }
    }

    private void fesMovimentsJugador() {
        if (dretaJugadorActivada && jugador.getPosicioX() < LIMIT_FINESTRA_DRETA - Jugador.AMPLE){
            jugador.moureDreta();
        } else if (esquerraJugadorActivada && jugador.getPosicioX() > LIMIT_FINESTRA_ESQUERRA){
            jugador.moureEsquerra();
        }
    }

    private void fesMovimentsBales() {
        for (int i = 0; i < jugador.getBales().size(); i++) {
            jugador.getBales().get(i).mouBala();
        }
        for (int j = 0; j < aliens.size(); j++) {
            for (int i = 0; i < aliens.get(j).getBales().size(); i++) {
                aliens.get(j).getBales().get(i).mouBala();
            }
        }
        for (int i = 0; i < balesAlienMort.size(); i++) {
            balesAlienMort.get(i).mouBala();
        }
    }

    private void creaBalesAliens() throws IOException {
        int nombreAleatori;
        for (int i = 0; i < aliens.size(); i++) {
            nombreAleatori = (int) Math.floor(Math.random() * 25 * aliens.size());
            if (nombreAleatori == PROBABILITAT_ALIEN_DISPARA_BALA){
                BalaAlien bala = new BalaAlien(aliens.get(i).getPosicioX(), aliens.get(i).getPosicioY());
                bala.carregaImatge();
                aliens.get(i).getBales().add(bala);
            }
        }
    }

    private void pintaPantalla(int bucle) {
        // Esborrem pantalla
        finestra.getG().setColor(Color.BLACK);
        finestra.getG().fillRect(0, 0, Finestra.AMPLE, Finestra.ALT);

        pintaAliens(bucle);
        pintaAliensXocats();
        pintaBales();
        pintaJugador();
        pintaEscuts();

        finestra.getG().setColor(Color.WHITE);
        pintaInformacio();

        // Pintem fons
        finestra.getG().drawImage(fonsDePantalla, 0, 0, Finestra.AMPLE, Finestra.ALT, null);
        finestra.repaint();
    }

    private void pintaEscuts() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < escuts[i].size(); j++) {
                escuts[i].get(j).pintaEscut(finestra.getG());
            }
        }
    }

    private void pintaAliens(int bucle) {
        int posicioXAlien;
        int posicioYAlien;

        for (int i = 0; i < aliens.size(); i++) {
            posicioXAlien = aliens.get(i).getPosicioX();
            posicioYAlien = aliens.get(i).getPosicioY();
            if (bucle % 8 == 0 || bucle % 8 == 1 || bucle % 8 == 2 || bucle % 8 == 3) {
                finestra.getG().drawImage(aliens.get(i).getImatgeA(), posicioXAlien, posicioYAlien,
                        Alien.AMPLE, Alien.ALT, null);
            } else {
                finestra.getG().drawImage(aliens.get(i).getImatgeB(), posicioXAlien, posicioYAlien,
                        Alien.AMPLE, Alien.ALT, null);
            }
        }

        if (alienBonus != null){
            finestra.getG().drawImage(alienBonus.getImatgeA(),alienBonus.getPosicioX(), alienBonus.getPosicioY(),
                    AlienBonus.AMPLE, AlienBonus.ALT, null);
        }
    }

    private void pintaAliensXocats() {
        int posicioXAlien;
        int posicioYAlien;

        if(aliensXocats != null) {
            for (int i = 0; i < aliensXocats.size(); i++) {
                posicioXAlien = aliensXocats.get(i).getPosicioX();
                posicioYAlien = aliensXocats.get(i).getPosicioY();
                finestra.getG().drawImage(aliensXocats.get(i).getImatge(), posicioXAlien, posicioYAlien,
                        Alien.AMPLE, Alien.ALT, null);
            }
        }
    }

    private void pintaJugador() {
        int posicioXJugador;
        int posicioYJugador;

        posicioXJugador = jugador.getPosicioX();
        posicioYJugador = jugador.getPosicioY();
        finestra.getG().drawImage(jugador.getImatge(), posicioXJugador, posicioYJugador, Jugador.AMPLE, Jugador.ALT, null);
    }

    private void pintaMortJugador() {
        int posicioXJugador;
        int posicioYJugador;
        posicioXJugador = jugador.getPosicioX();
        posicioYJugador = jugador.getPosicioY();

        // Per a fer pampallugues
        finestra.getG().drawImage(jugador.getImatgeXoc(), posicioXJugador - 2, posicioYJugador, Jugador.AMPLE + 4, Jugador.ALT, null);
        finestra.repaint();

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {}
        finestra.getG().drawImage(jugador.getImatge(), posicioXJugador, posicioYJugador, Jugador.AMPLE, Jugador.ALT, null);
        finestra.repaint();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {}
        finestra.getG().drawImage(jugador.getImatgeXoc(), posicioXJugador - 2, posicioYJugador, Jugador.AMPLE + 4, Jugador.ALT, null);
        finestra.repaint();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {}
    }

    private void pintaBales() {
        for (int i = 0; i < jugador.getBales().size(); i++) {
            jugador.getBales().get(i).pintaBala(finestra.getG());
        }

        for (int j = 0; j < aliens.size(); j++) {
            for (int i = 0; i < aliens.get(j).getBales().size(); i++) {
                aliens.get(j).getBales().get(i).pintaBala(finestra.getG());
            }
        }

        for (int i = 0; i < balesAlienMort.size(); i++) {
            balesAlienMort.get(i).pintaBala(finestra.getG());
        }
    }

    private void pintaInformacio() {
        String informacioVides;
        informacioVides = " L I V E S: " + jugador.getVides();
        finestra.getG().drawString(informacioVides, LIMIT_FINESTRA_DRETA - 120, LIMIT_FINESTRA_SUPERIOR);

        String informacioPuntuacio;
        informacioPuntuacio = " S C O R E: " + jugador.getPuntuacio();
        finestra.getG().drawString(informacioPuntuacio,LIMIT_FINESTRA_ESQUERRA,LIMIT_FINESTRA_SUPERIOR);
    }

    private void esborraBalesForaFinestra() {
        for (int i = 0; i < jugador.getBales().size(); i++) {
            if (jugador.getBales().get(i).getPosicioY() < LIMIT_FINESTRA_SUPERIOR) {
                jugador.getBales().remove(i);
            }
        }
        for (int j = 0; j < aliens.size(); j++) {
            for (int i = 0; i < aliens.get(j).getBales().size(); i++) {
                if (aliens.get(j).getBales().get(i).getPosicioY() > LIMIT_FINESTRA_INFERIOR + Jugador.ALT) {
                    aliens.get(j).getBales().remove(i);
                }
            }
        }
        for (int i = 0; i < balesAlienMort.size(); i++) {
            if(balesAlienMort.get(i).getPosicioY() > LIMIT_FINESTRA_INFERIOR + Jugador.ALT) {
                balesAlienMort.remove(i);
            }
        }
    }

    private void esborraBalesAliens() {
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).getBales().size(); j++) {
                aliens.get(i).getBales().remove(0);
            }
        }

        for (int i = 0; i < balesAlienMort.size(); i++) {
            balesAlienMort.remove(0);
        }
    }

    private void esborraAlienBonus() {
        if (alienBonus != null) {
            if (alienBonus.getPosicioX() > LIMIT_FINESTRA_DRETA + Alien.AMPLE) {
                alienBonus = null;
                apareixAlienBonus.stop();
                apareixAlienBonus.setFramePosition(0);
            }
        }
    }

    private void esborraAliensXocats() {
        int i = 0;

        while (aliensXocats.size() != i) {
            if(aliensXocats.get(i).getTempsXocat() == 3) {
                aliensXocats.remove(i);
                i--;
            }
            i++;
        }
    }

    private void gestionaXocs() throws IOException {
        gestionaXocAlienBalaJugador();
        gestionaXocAlienBonusBalaJugador();
        gestionaXocJugadorBalaAlien();
        gestionaXocEscutBalaJugador();
        gestionaXocEscutBalaAlien();
        gestionaXocEscutBalaAlienMort();
    }

    private void gestionaXocAlienBalaJugador() throws IOException {
        int posicioXBala;
        int posicioYBala;
        int posicioXAlien;
        int posicioYAlien;

        label:
        for (int i = 0; i < aliens.size(); i++) {
            posicioXAlien = aliens.get(i).getPosicioX();
            posicioYAlien = aliens.get(i).getPosicioY();

            for (int j = 0; j < jugador.getBales().size(); j++) {
                posicioXBala = jugador.getBales().get(j).getPosicioX();
                posicioYBala = jugador.getBales().get(j).getPosicioY();

                if (detectaXocAlienBalaJugador(posicioXBala, posicioYBala, posicioXAlien, posicioYAlien)) {
                    jugador.setPuntuacio(jugador.getPuntuacio() + aliens.get(i).getPunts());

                    // Per a que no s'eliminin les bales que ja ha disparat l'alien tocat
                    balesAlienMort.addAll(aliens.get(i).getBales());
                    guardaAliensXocats(aliens.get(i));

                    aliens.remove(i);
                    jugador.getBales().remove(j);
                    break label;
                }
            }
        }
    }

    private void gestionaXocAlienBonusBalaJugador() throws IOException {
        int posicioXBala;
        int posicioYBala;

        if (alienBonus != null) {
            for (int i = 0; i < jugador.getBales().size(); i++) {
                posicioXBala = jugador.getBales().get(i).getPosicioX();
                posicioYBala = jugador.getBales().get(i).getPosicioY();
                if (detectaXocAlienBonusBalaJugador(posicioXBala, posicioYBala, alienBonus.getPosicioX(), alienBonus.getPosicioY())) {
                    jugador.setPuntuacio(jugador.getPuntuacio() + alienBonus.getPunts());
                    alienBonus.setPosicioX(alienBonus.getPosicioX() + AlienBonus.AMPLE / 2);
                    guardaAliensXocats(alienBonus);
                    apareixAlienBonus.stop();
                    apareixAlienBonus.setFramePosition(0);
                    alienBonus = null;
                    jugador.getBales().remove(i);
                    break;
                }
            }
        }
    }

    private void gestionaXocJugadorBalaAlien() {
        int posicioXBala;
        int posicioYBala;
        int posicioXJugador;
        int posicioYJugador;

        posicioXJugador = jugador.getPosicioX();
        posicioYJugador = jugador.getPosicioY();

        label:
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).getBales().size(); j++) {
                posicioXBala = aliens.get(i).getBales().get(j).getPosicioX();
                posicioYBala = aliens.get(i).getBales().get(j).getPosicioY();

                if (detectaXocJugadorBalaAlien(posicioXBala, posicioYBala, posicioXJugador, posicioYJugador)) {
                    jugador.perdVida();
                    pintaMortJugador();
                    esborraBalesAliens();
                    break label;
                }
            }
        }


        for (int i = 0; i < balesAlienMort.size(); i++) {
            posicioXBala = balesAlienMort.get(i).getPosicioX();
            posicioYBala = balesAlienMort.get(i).getPosicioY();

            if (detectaXocJugadorBalaAlien(posicioXBala, posicioYBala, posicioXJugador, posicioYJugador)) {
                jugador.perdVida();
                pintaMortJugador();
                esborraBalesAliens();
                break;
            }
        }
    }

    private void gestionaXocEscutBalaAlien() {
        int posicioXBala;
        int posicioYBala;
        int posicioXEscut;
        int posicioYEscut;

        label:
        for (int i = 0; i < aliens.size(); i++) {
            for (int j = 0; j < aliens.get(i).getBales().size(); j++) {
                posicioXBala = aliens.get(i).getBales().get(j).getPosicioX();
                posicioYBala = aliens.get(i).getBales().get(j).getPosicioY();

                for (int k = 0; k < escuts.length; k++) {
                    for (int l = 0; l < escuts[k].size(); l++) {
                        posicioXEscut = escuts[k].get(l).getPosicioX();
                        posicioYEscut = escuts[k].get(l).getPosicioY();

                        if(detectaXocEscutBala(posicioXBala, posicioYBala, posicioXEscut, posicioYEscut)) {
                            aliens.get(i).getBales().remove(j);
                            escuts[k].remove(l);
                            break label;
                        }

                    }
                }
            }
        }
    }

    private void gestionaXocEscutBalaJugador() {
        int posicioXBala;
        int posicioYBala;
        int posicioXEscut;
        int posicioYEscut;

        label:
        for (int i = 0; i < jugador.getBales().size(); i++) {
            posicioXBala = jugador.getBales().get(i).getPosicioX();
            posicioYBala = jugador.getBales().get(i).getPosicioY();

            for (int j = 0; j < escuts.length; j++) {
                for (int k = 0; k < escuts[j].size(); k++) {
                    posicioXEscut = escuts[j].get(k).getPosicioX();
                    posicioYEscut = escuts[j].get(k).getPosicioY();

                    if(detectaXocEscutBala(posicioXBala, posicioYBala, posicioXEscut, posicioYEscut)) {
                        jugador.getBales().remove(i);
                        escuts[j].remove(k);
                        break label;
                    }

                }
            }
        }
    }

    private void gestionaXocEscutBalaAlienMort () {
        int posicioXBala;
        int posicioYBala;
        int posicioXEscut;
        int posicioYEscut;

        label:
        for (int i = 0; i < balesAlienMort.size(); i++) {
            posicioXBala = balesAlienMort.get(i).getPosicioX();
            posicioYBala = balesAlienMort.get(i).getPosicioY();
            for (int j = 0; j < escuts.length; j++) {
                for (int k = 0; k < escuts[j].size(); k++) {
                    posicioXEscut = escuts[j].get(k).getPosicioX();
                    posicioYEscut = escuts[j].get(k).getPosicioY();

                    if(detectaXocEscutBala(posicioXBala, posicioYBala, posicioXEscut, posicioYEscut)) {
                        balesAlienMort.remove(i);
                        escuts[j].remove(k);
                        break label;
                    }

                }
            }
        }
    }

    private boolean detectaXocAlienBalaJugador(int posicioXBala, int posicioYBala, int posicioXAlien, int posicioYAlien) {
        if (posicioXBala < (posicioXAlien + Alien.AMPLE) && (posicioXBala + Bala.AMPLE) > posicioXAlien) {
            if (posicioYBala < (posicioYAlien + Alien.ALT) && (posicioYBala + Bala.ALT) > posicioYAlien) {
                if (!explotaAlien.isRunning()) {
                    explotaAlien.setFramePosition(0);
                    explotaAlien.start();
                }
                return true;
            }
        }
        return false;
    }

    private boolean detectaXocAlienBonusBalaJugador(int posicioXBala, int posicioYBala, int posicioXAlien, int posicioYAlien) {
        if (posicioXBala < (posicioXAlien + AlienBonus.AMPLE) && (posicioXBala + Bala.AMPLE) > posicioXAlien) {
            if (posicioYBala < (posicioYAlien + AlienBonus.ALT) && (posicioYBala + Bala.ALT) > posicioYAlien) {
                explotaAlien.setFramePosition(0);
                explotaAlien.start();
                return true;
            }
        }
        return false;
    }

    private boolean detectaXocJugadorBalaAlien(int posicioXBala, int posicioYBala, int posicioXJugador, int posicioYJugador) {
        if (posicioXBala < (posicioXJugador + Jugador.AMPLE) && (posicioXBala + Bala.AMPLE) > posicioXJugador) {
            if (posicioYBala < (posicioYJugador + Jugador.ALT) && (posicioYBala + Bala.ALT) > posicioYJugador) {
                explotaJugador.setFramePosition(0);
                explotaJugador.start();
                return true;
            }
        }
        return false;
    }

    private boolean detectaXocEscutBala(int posicioXBala, int posicioYBala, int posicioXEscut, int posicioYEscut) {
        if (posicioXBala < (posicioXEscut + Escut.AMPLE) && (posicioXBala + Bala.AMPLE) > posicioXEscut) {
            if (posicioYBala < (posicioYEscut + Jugador.ALT) && (posicioYBala + Bala.ALT) > posicioYEscut) {
                return true;
            }
        }
        return false;
    }

    private void guardaAliensXocats(Alien alien) throws IOException {
        aliensXocats.add(new AlienXocat(alien.getPosicioX(), alien.getPosicioY()));
    }

    private boolean jocEnCurs() {
        if (aliens.size() == 0) {
            return false;
        } else if(jugador.getVides() > 0 && aliensPerSobreEscut()){
            return true;
        } else {
            return false;
        }
    }

    private boolean aliensPerSobreEscut() {
        if (aliens.get(aliens.size() - 1).getPosicioY() < LIMIT_INICI_ESCUTS - 5*Escut.ALT ) {
            return true;
        }
        return false;
    }

    private void carregaImatges() {
        try {
            fonsDePantalla = ImageIO.read(new File("img/fons.png"));
            jugador.setImatge(ImageIO.read(new File("img/jugador.png")));
            jugador.setImatgeXoc(ImageIO.read(new File("img/jugadorXoc.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregaSorolls() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        explotaAlien = AudioSystem.getClip();
        explotaJugador = AudioSystem.getClip();
        apareixAlienBonus = AudioSystem.getClip();
        tretJugador = AudioSystem.getClip();
        musicaDeFons = AudioSystem.getClip();

        explotaAlien.open(AudioSystem.getAudioInputStream(new File("sounds/explotaAlien.wav")));
        explotaJugador.open(AudioSystem.getAudioInputStream(new File("sounds/explotaJugador.wav")));
        apareixAlienBonus.open(AudioSystem.getAudioInputStream(new File("sounds/apareixAlienBonus.wav")));
        tretJugador.open(AudioSystem.getAudioInputStream(new File("sounds/tretJugador.wav")));
        musicaDeFons.open(AudioSystem.getAudioInputStream(new File("sounds/musicaDeFons.wav")));
    }

    private void carregaFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/fontMenu.ttf")).deriveFont(14f);
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
    public void keyTyped(KeyEvent e) { }

    /**
     * Mètode que vigila si la key està pressed
     * @param e key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            dretaJugadorActivada = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            esquerraJugadorActivada = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Date today = Calendar.getInstance().getTime();
            if (today.getTime() > antiSpam + 300) {
                jugador.disparaBala();
                if (tretJugador != null) {
                    tretJugador.setFramePosition(0);
                    tretJugador.start();
                }
                antiSpam = today.getTime();
            }
        }
    }

    /**
     * Mètode que vigila si la key està released
     * @param e key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            dretaJugadorActivada = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            esquerraJugadorActivada = false;
        }
    }

}