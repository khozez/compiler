package compilador.AccionesSemanticas;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AS9 implements AccionSemantica{
    //LEE EL CARACTER Y LO DESCARTA (COMENTARIOS) SE IDENTIFICA CON -3

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            lector.read();
            lexema = "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -2;
    }
}
