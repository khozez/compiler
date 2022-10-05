package compilador.AccionesSemanticas;

import compilador.TablaPalabrasReservadas;

import java.io.BufferedInputStream;
import java.io.IOException;

public class AS7 implements AccionSemantica{
    //LEE EL ULTIMO CARACTER, CONCATENA Y DEVUELVE EL TOKEN ASOCIADO (PALABRAS RESERVADAS)

    @Override
    public int ejecutar(BufferedInputStream lector, String lexema) {
        try {
            lexema += (char) lector.read();
            return TablaPalabrasReservadas.obtenerIdentificador(lexema);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ERROR
        return -5;
    }
}
