package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;
import compilador.TablaSimbolos;

public class AS7 implements AccionSemantica{
    //ASOCIADA AL CONTROL DE LOS ENTEROS LARGOS SIN SIGNO

    @Override
    public int ejecutar(String lexema, char caracter) {
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
        return 0;
    }
}
