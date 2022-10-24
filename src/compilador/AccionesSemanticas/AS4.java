package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;
import compilador.TablaSimbolos;

import java.io.BufferedInputStream;

public class AS4 implements AccionSemantica{
    //ASOCIADA AL CONTROL DE DOBLES

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        double valor = Double.parseDouble(lexema);
        if (valor <= AnalizadorLexico.MIN_DOUBLE_VALUE){
            //INFORMAR WARNING, SE TRUNCA AL MENOR VALOR.
            valor = AnalizadorLexico.MIN_DOUBLE_VALUE;

        } else if (valor >= AnalizadorLexico.MAX_DOUBLE_VALUE) {
            //INFORMAR WARNING, SE TRUNCA AL MENOR VALOR.
            valor = AnalizadorLexico.MAX_DOUBLE_VALUE;

        }

        lexema = Double.toString(valor);
        if (TablaSimbolos.agregarSimbolo(lexema)){
            int id = TablaSimbolos.obtenerSimbolo(lexema);
            TablaSimbolos.agregarAtributo(id, "tipo", "doble");
        }

        //RETORNAMOS EL ID DEL TOKEN DE UNA CONSTANTE
        System.out.println("Constante doble: "+lexema);
        return 259;
    }
}
