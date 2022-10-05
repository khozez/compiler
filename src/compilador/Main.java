package compilador;

import java.io.IOException;
import java.io.Reader;

import static compilador.AnalizadorLexico.lector;

public class Main {
    public static void main(String[] args) {
            AnalizadorLexico Alexico = new AnalizadorLexico();
            int id_token = -44;
            while (true) {
                try {
                    if (!!endOfFile(lector))
                        break;


                    //OBTENEMOS CARACTER DEL CODIGO, Y RESETEAMOS EL READER PARA QUE NO AFECTE A LAS AS
                    lector.mark(1);
                    char c = (char) lector.read();
                    lector.reset();
                    id_token = Alexico.getToken(lector, c);

                    if (id_token == -1) {
                        //LLEGAMOS A ESTADO FINAL

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    public static boolean endOfFile(Reader lector) throws IOException {
        lector.mark(1);
        int value = lector.read();
        lector
                .reset();

        return value < 0;
    }
}