package compilador.AccionesSemanticas;

import java.io.IOException;
import java.io.Reader;

public class AS9 implements AccionSemantica{
    //LEE EL CARACTER Y LO DESCARTA (COMENTARIOS) SE IDENTIFICA CON -3

    @Override
    public int ejecutar(Reader lector, String lexema) {
        try {
            lector.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -3;
    }
}
