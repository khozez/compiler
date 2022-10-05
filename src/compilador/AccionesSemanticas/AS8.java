package compilador.AccionesSemanticas;

import java.io.Reader;

public class AS8 implements AccionSemantica{
    //RETORNA EL TOKEN

    @Override
    public int ejecutar(Reader lector, String lexema) {
        return lexema.charAt(0);
    }
}
