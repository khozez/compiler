package compilador.AccionesSemanticas;
import java.io.Reader;

public interface AccionSemantica {

    int ejecutar(Reader lector, String lexema);
}
