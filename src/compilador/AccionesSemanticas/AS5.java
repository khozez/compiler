package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;
import compilador.TablaSimbolos;
import java.io.Reader;

public class AS5 implements AccionSemantica{
    //ASOCIADA AL CONTROL DE LOS ENTEROS LARGOS SIN SIGNO

    @Override
    public int ejecutar(Reader lector, String lexema) {
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

        //DEBEMOS RETORNAR EL ID DEL TOKEN DE UN ENTERO LARGO SIN SIGNO
        return 0;
    }
}
