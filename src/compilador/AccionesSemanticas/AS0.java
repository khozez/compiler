package compilador.AccionesSemanticas;
import compilador.TablaSimbolos;

public class AS0 implements AccionSemantica{
    //AGREGA EL SIMBOLO A LA TABLA DE SIMBOLOS

    @Override
    public int ejecutar(String lexema, char caracter) {
        TablaSimbolos.agregarSimbolo(lexema);
        return 0;
    }
}
