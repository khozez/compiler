package compilador.AccionesSemanticas;
import java.io.BufferedInputStream;

import compilador.AnalizadorLexico;
import compilador.TablaPalabrasReservadas;
import compilador.TablaSimbolos;

public class AS1 implements AccionSemantica{
    /*
        ASOCIADA A LOS IDENTIFICADORES:
        - Trunca el identificador si su longitud es mayor a la establecida en el analizador lexico
        -
     */

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        int id_token;
        if (lexema.length() > AnalizadorLexico.MAXIMA_LONGITUD_IDENTIFICADORES){
            lexema = lexema.substring(0, AnalizadorLexico.MAXIMA_LONGITUD_IDENTIFICADORES);
            System.out.println("WARNING: Identificador en linea"+AnalizadorLexico.getCantLineas()+" fue truncado ya que supera la longitud maxima.");
        }
        if (TablaPalabrasReservadas.obtenerIdentificador(lexema) == -1){
            //NO ES PALABRA RESERVADA
            TablaSimbolos.agregarSimbolo(lexema);
            id_token = 156;
        }else{
            //ES PALABRA RESERVADA
            id_token = TablaPalabrasReservadas.obtenerIdentificador(lexema);
        }
        return id_token;
    }
}
