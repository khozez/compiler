package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.BufferedInputStream;
import java.io.IOException;

public class ASe implements AccionSemantica{
    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            lector.mark(1);
            char value = (char) lector.read();
            lector.reset();
            System.out.println("LINEA "+AnalizadorLexico.getCantLineas()+": Error lexico, no se puede comenzar con '"+value+"'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
