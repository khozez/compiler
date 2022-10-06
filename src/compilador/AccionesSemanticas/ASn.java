package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;

import java.io.BufferedInputStream;

public class ASn implements AccionSemantica{
    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        System.out.println("LINEA "+ AnalizadorLexico.getCantLineas()+": Error lexico, se esperaba un caracter y se encontro un salto de linea.");
        return 0;
    }
}
