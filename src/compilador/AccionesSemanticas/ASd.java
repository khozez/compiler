package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.BufferedInputStream;
import java.io.IOException;

public class ASd implements AccionSemantica{
    //INFORMA ERROR ASOCIADA A LOS DOBLES

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            lector.mark(1);
            char value = (char) lector.read();
            lector.reset();
            System.out.println("LINEA "+ AnalizadorLexico.getCantLineas()+": Error lexico, se esperaba +, - o digito, y se encontró '"+value+"'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
