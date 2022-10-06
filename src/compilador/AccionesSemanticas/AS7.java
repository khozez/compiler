package compilador.AccionesSemanticas;

import compilador.AnalizadorLexico;
import compilador.TablaPalabrasReservadas;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AS7 implements AccionSemantica{
    //LEE EL ULTIMO CARACTER, CONCATENA Y DEVUELVE EL TOKEN ASOCIADO (PALABRAS RESERVADAS)

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            AnalizadorLexico.lexema += (char) lector.read();
            System.out.println("Palabra reservada: "+lexema);
            return TablaPalabrasReservadas.obtenerIdentificador(lexema);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ERROR
        return -5;
    }
}
