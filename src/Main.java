import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Classe Main que executa tot el Programa
 */
public class Main {
    /**
     * Mètode Main
     * @param args args
     * @throws IOException Exception thrown
     * @throws LineUnavailableException Exception thrown
     * @throws UnsupportedAudioFileException Exception thrown
     */
    public static void main (String [ ] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        Finestra finestra = new Finestra();
        finestra.inicialitzaFinestra();
        finestra.getMenu().run();
        System.exit(0);
    }
}
