package compilador;

import compilador.AccionesSemanticas.AccionSemantica;

public class MTE {
	public int[][] states_matrix = new int[12][26];
	public static final int ERROR = -3;
	public static final int ESTADO_FINAL = -5;

	public MTE() {
		states_matrix[0][0] = 0;
		states_matrix[0][1] = 0;
		states_matrix[0][2] = 0;
		states_matrix[0][3] = 1;
		states_matrix[0][4] = 1;
		states_matrix[0][5] = 1;
		states_matrix[0][6] = 3;
		states_matrix[0][7] = ERROR;
		states_matrix[0][8] = 2;
		states_matrix[0][9] = ERROR;
		states_matrix[0][10] = ERROR;
		for (int i=11; i<=20; i++){
			states_matrix[0][i] = ESTADO_FINAL;
		}
		states_matrix[0][21] = 4;
		states_matrix[0][22] = 7;
		states_matrix[0][23] = 11;
		states_matrix[0][24] = 10;
		states_matrix[0][25] = ERROR;
		for (int i=0; i<=2; i++){
			states_matrix[1][i] = ESTADO_FINAL;
		}
		for (int i=3; i<=7; i++){
			states_matrix[1][i] = 1;
		}
		for (int i=8; i<=25; i++){
			states_matrix[1][i] = ESTADO_FINAL;
		}
		for (int i=0; i<=25; i++){
			states_matrix[2][i] = ESTADO_FINAL;
		}
		for (int i=0; i<=5; i++){
			states_matrix[3][i] = ESTADO_FINAL;
		}
		states_matrix[3][6] = 3;
		for (int i=7; i<=20; i++){
			states_matrix[3][i] = ESTADO_FINAL;
		}
		states_matrix[3][21] = 4;
		states_matrix[3][22] = ESTADO_FINAL;
		states_matrix[3][23] = ESTADO_FINAL;
		states_matrix[3][24] = ESTADO_FINAL;
		states_matrix[3][25] = ESTADO_FINAL;
		states_matrix[4][0] = ESTADO_FINAL;
		states_matrix[4][1] = ESTADO_FINAL;
		states_matrix[4][2] = ESTADO_FINAL;
		states_matrix[4][3] = 5;
		states_matrix[4][4] = ESTADO_FINAL;
		states_matrix[4][5] = ESTADO_FINAL;
		states_matrix[4][6] = 4;
		for (int i=7; i<=25; i++){
			states_matrix[4][i] = ESTADO_FINAL;
		}
		for (int i=0; i<=5; i++){
			states_matrix[5][i] = ERROR;
		}
		states_matrix[5][6] = 6;
		for (int i=7; i<=12; i++){
			states_matrix[5][i] = ERROR;
		}
		states_matrix[5][13] = 6;
		states_matrix[5][14] = 6;
		for (int i=15; i<=25; i++){
			states_matrix[5][i] = ERROR;
		}
		for (int i=0; i<=5; i++){
			states_matrix[6][i] = ESTADO_FINAL;
		}
		states_matrix[6][6] = 6;
		for (int i=7; i<=25; i++){
			states_matrix[6][i] = ESTADO_FINAL;
		}
		for (int i=0; i<=21; i++){
			states_matrix[7][i] = ESTADO_FINAL;
		}
		states_matrix[7][22] = 8;
		for (int i=23; i<=25; i++){
			states_matrix[7][i] = ESTADO_FINAL;
		}
		for (int i=0; i<=22; i++){
			states_matrix[8][i] = 8;
		}
		states_matrix[8][23] = 9;
		states_matrix[8][24] = 8;
		states_matrix[8][25] = 8;
		for (int i=0; i<=22; i++){
			states_matrix[9][i] = 8;
		}
		states_matrix[9][23] = 0;
		states_matrix[9][25] = 8;
		states_matrix[9][25] = 8;
		states_matrix[10][0] = 10;
		states_matrix[10][1] = 10;
		states_matrix[10][2] = ERROR;
		for (int i=3; i<=23; i++){
			states_matrix[10][i] = 10;
		}
		states_matrix[10][24] = ESTADO_FINAL;
		states_matrix[10][25] = 10;
		for (int i=0; i<=25; i++){
			states_matrix[11][i] = ESTADO_FINAL;
		}
	}

	public void show(){
		System.out.println("	MATRIZ DE TRANSICION DE ESTADOS");
		for (int i = 0; i < states_matrix.length; i++) {
			for (int j = 0; j < states_matrix[i].length; j++) {
				System.out.print(states_matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
