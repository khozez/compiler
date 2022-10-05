package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

public class AS4 implements AccionSemantica{
    //ASOCIADA A LOS SALTOS DE LINEA

    @Override
    public int ejecutar(String lexema, char caracter) {
        if (caracter == AnalizadorLexico.NUEVA_LINEA){
            AnalizadorLexico.newLine();
        }
        return 0;
    }
}
