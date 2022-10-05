package compilador.AccionesSemanticas;

import java.io.Reader;

public class ASe implements AccionSemantica{
    @Override
    public int ejecutar(Reader lector, String lexema) {
        System.out.println("Error");
        return 0;
    }
}
