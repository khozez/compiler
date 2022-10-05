package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.BufferedInputStream;

public class AS3 implements AccionSemantica{
    //REINICIA EL LEXEMA, UTILIZADA EN COMENTARIOS

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        AnalizadorLexico.lexema =  "";
        return 0;
    }
}
