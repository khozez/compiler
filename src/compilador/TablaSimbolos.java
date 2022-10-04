package compilador;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TablaSimbolos {
    public static final int NO_ENCONTRADO = -1;
    public static final String NO_ENCONTRADO_MESSAGE = "No encontrado";
    public static final String LEXEMA = "lexema";
    public static Set<Integer> obtenerConjuntoPunteros() {
        return simbolos.keySet();
    }
    private static final Map<Integer, Map<String, String>> simbolos = new HashMap<>();
    private static int identificador_siguiente = 1;


    public static void agregarSimbolo(String simbolo_nuevo) {
        Map<String, String> atributos = new HashMap<>();
        atributos.put(LEXEMA, simbolo_nuevo);
        simbolos.put(identificador_siguiente, atributos);
        ++identificador_siguiente;
    }

    public static int obtenerSimbolo(String lexema) {
        for (Map.Entry<Integer, Map<String, String>> entrada: simbolos.entrySet()) {
            String lexema_actual = entrada.getValue().get(LEXEMA);

            if (lexema_actual.equals(lexema)) {
                return entrada.getKey();
            }
        }

        return NO_ENCONTRADO;
    }

    public static void eliminarSimbolo(int clave) {
        simbolos.remove(clave);
    }

    public static void agregarAtributo(int clave, String atributo, String valor) {
        if (simbolos.containsKey(clave)) {
            Map<String, String> atributos = simbolos.get(clave);
            atributos.put(atributo, valor);
        }
    }

    public static String obtenerAtributo(int clave, String atributo) {
        if (simbolos.containsKey(clave)) {
            Map<String, String> atributos = simbolos.get(clave);

            if (atributos.containsKey(atributo)) {
                return atributos.get(atributo);
            }
        }
        return NO_ENCONTRADO_MESSAGE;
    }

    public static Map<String, String> obtenerAtributos(int clave) {
        if (simbolos.containsKey(clave)) {
            return simbolos.get(clave);
        }
        return null;
    }
}