package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AS2 implements AccionSemantica{
    //ASOCIADA A LOS SALTOS DE LINEA

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
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
