package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AS8 implements AccionSemantica{
    //RETORNA EL TOKEN

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            char c = (char) lector.read();
            AnalizadorLexico.lexema += c;
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
