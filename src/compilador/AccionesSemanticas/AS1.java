package compilador.AccionesSemanticas;
import java.io.Reader;
import compilador.AnalizadorLexico;
import compilador.TablaPalabrasReservadas;

public class AS1 implements AccionSemantica{
    /*
        ASOCIADA A LOS IDENTIFICADORES:
        - Devuelve al analizador lexico el ultimo caracter leido
        - Trunca el identificador si su longitud es mayor a la establecida en el analizador lexico
        -
     */

    @Override
    public int ejecutar(Reader lector, String lexema) {
        if (lexema.length() > AnalizadorLexico.MAXIMA_LONGITUD_IDENTIFICADORES){
            lexema = lexema.substring(0, AnalizadorLexico.MAXIMA_LONGITUD_IDENTIFICADORES);
        }
        if (TablaPalabrasReservadas.obtenerIdentificador(lexema) != -1){
            //NO ES PALABRA RESERVADA



        }else{
            //ES PALABRA RESERVADA



        }
        return 0;
    }
}
