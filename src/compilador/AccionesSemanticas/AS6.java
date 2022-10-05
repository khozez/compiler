package compilador.AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

public class AS6 implements AccionSemantica{
    //LEE UN CARACTER Y LO CONCATENA AL LEXEMA

    @Override
    public int ejecutar(Reader lector, String lexema) {
        try {
            lexema += (char) lector.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
