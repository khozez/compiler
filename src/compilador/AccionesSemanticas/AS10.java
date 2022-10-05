package compilador.AccionesSemanticas;

import compilador.TablaSimbolos;

import java.io.IOException;
import java.io.Reader;

public class AS10 implements AccionSemantica{
    //AGREGA LA CADENA DE UNA LINEA A LA TABLA DE SIMBOLOS

    @Override
    public int ejecutar(Reader lector, String lexema) {
        try {
            lexema += (char) lector.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (TablaSimbolos.obtenerSimbolo(lexema) != TablaSimbolos.NO_ENCONTRADO) {
            TablaSimbolos.agregarSimbolo(lexema);
        }

        //DEBEMOS RETORNAR EL ID DEL TOKEN DE UNA CADENA
        return 0;
    }
}
