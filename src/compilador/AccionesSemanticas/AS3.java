package compilador.AccionesSemanticas;

import java.io.Reader;

public class AS3 implements AccionSemantica{
    //REINICIA EL LEXEMA, UTILIZADA EN COMENTARIOS

    @Override
    public int ejecutar(Reader lector, String lexema) {
        lexema =  "";
        return 0;
    }
}
