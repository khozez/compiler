package compilador;

public interface AccionSemantica{
    abstract int ejecutar(String cadena, char entry);
}