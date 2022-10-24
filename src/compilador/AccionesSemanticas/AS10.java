package compilador.AccionesSemanticas;

import compilador.TablaSimbolos;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AS10 implements AccionSemantica{
    //AGREGA LA CADENA DE UNA LINEA A LA TABLA DE SIMBOLOS

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            lexema += (char) lector.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TablaSimbolos.obtenerSimbolo(lexema) != TablaSimbolos.NO_ENCONTRADO) {
            TablaSimbolos.agregarSimbolo(lexema);
        }
        return 258;
    }
}
