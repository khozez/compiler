package compilador.AccionesSemanticas;

public class AS3 implements AccionSemantica{
    //CONCATENA EL ULTIMO CARACTER LEIDO A LA CADENA

    @Override
    public int ejecutar(String lexema, char caracter) {
        lexema += caracter;
        return 0;
    }
}
