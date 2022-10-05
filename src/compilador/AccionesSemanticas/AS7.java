package compilador.AccionesSemanticas;

import compilador.TablaPalabrasReservadas;

import java.io.IOException;
import java.io.Reader;

public class AS7 implements AccionSemantica{
    //LEE EL ULTIMO CARACTER, CONCATENA Y DEVUELVE EL TOKEN ASOCIADO (PALABRAS RESERVADAS)

    @Override
    public int ejecutar(Reader lector, String lexema) {
        try {
            lexema += (char) lector.read();
            return TablaPalabrasReservadas.obtenerIdentificador(lexema);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
