package compilador.AccionesSemanticas;

import java.io.BufferedInputStream;

public class ASe implements AccionSemantica{
    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        System.out.println("Error");
        return 0;
    }
}
