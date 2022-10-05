package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;
import compilador.TablaSimbolos;

import java.io.BufferedInputStream;

public class AS5 implements AccionSemantica{
    //ASOCIADA AL CONTROL DE LOS ENTEROS LARGOS SIN SIGNO

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        long num = Long.parseLong(lexema);
        if (num > AnalizadorLexico.MAX_LONG){
            num = AnalizadorLexico.MAX_LONG;
            //INFORMAR WARNING, SE TRUNCA A MAXIMO LONG
        }

        lexema = Long.toString(num);
        if (TablaSimbolos.agregarSimbolo(lexema)){
            int id = TablaSimbolos.obtenerSimbolo(lexema);
            TablaSimbolos.agregarAtributo(id, "tipo", "long");
        }
        return 158;
    }
}
