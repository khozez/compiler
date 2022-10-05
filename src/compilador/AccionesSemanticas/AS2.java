package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.IOException;
import java.io.Reader;

public class AS2 implements AccionSemantica{
    //ASOCIADA A LOS SALTOS DE LINEA

    @Override
    public int ejecutar(Reader lector, String lexema) {
        try {
            if ((char) lector.read() == AnalizadorLexico.NUEVA_LINEA){
                AnalizadorLexico.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
