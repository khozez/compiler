package compilador;

public class MAS {
    AccionSemantica[][] matrix = new AccionSemantica[13][128];

    public MAS(){};

    public AccionSemantica ejecutar(int fila, int n_caracter){
        return matrix[fila][n_caracter];
    }

}
