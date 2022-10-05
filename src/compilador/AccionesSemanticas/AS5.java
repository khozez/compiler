package compilador.AccionesSemanticas;

public class AS5 implements AccionSemantica{
    //REINICIA EL STRING, UTILIZADA EN COMENTARIOS

    @Override
    public int ejecutar(String lexema, char caracter) {
        lexema =  "";
        return 0;
    }
}
