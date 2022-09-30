package compilador;

import java.util.ArrayList;

public class Automata {
	int[][] matrix = new int[7][127];//matrix[estado][entrada]
	
	
	public void Automata() {
		for (int i=0;i<=7;i++) {
			for(int j=0;j<=127;j++) {
				matrix[i][j]=-1;
			}
		}
		for(int d=48;d<=57;d++) {//transicion cuando llega un digito
			matrix[0][d]=3;
			matrix[1][d]=1;
			matrix[3][d]=3;
			matrix[4][d]=4;
		}
		for(int c=65;c<=90;c++) {//transicion letras mayusculas
			matrix[0][c]=1;
			matrix[1][c]=1;
		}
		for(int l=97;l<=122;l++) {//transicion letras minusculas
			matrix[0][l]=1;
			matrix[1][l]=1;
		}
		//+++TRANSICIONES DESDE e0+++
		matrix[0][61]=2;//transicion =
		matrix[0][46]=4;//transicion .
		matrix[3][46]=4;//transicion .
		//operadores
		matrix[0][47]=7;//transicion operador /
		matrix[0][42]=7;//transicion operador *
		matrix[0][43]=7;//transicion operador +
		matrix[0][45]=7;//transicion operador -
		//otros simbolos
		matrix[0][40]=7;//transicion (
		matrix[0][41]=7;//transicion )
		matrix[0][123]=7;//transicion {
		matrix[0][125]=7;//transicion }
		matrix[0][44]=7;//transicion ,
		matrix[0][59]=7;//transicion ;
		
		//+++TRANSICIONES DESDE e1+++
		matrix[1][95]=1;//transicion _
		
		//transicion cualquier caracter menos los permitidos para
		for(int i=33;i<=47;i++) {
			matrix[1][i]=7;
		}
		for(int i=58;i<=64;i++) {
			matrix[1][i]=7;
		}
		for(int i=91;i<=94;i++) {
			matrix[1][i]=7;
		}
		for(int i=123;i<=126;i++) {
			matrix[1][i]=7;
		}
		matrix[1][96]=7;
		
		//+++TRANSICIONES DESDE e2+++
		for(int i=33;i<=126;i++) {
			matrix[2][i]=7;
		}
		//+++TRANSICIONES DESDE e3+++
		for(int i=33;i<=47;i++) {//transicion c-digitos
			matrix[3][i]=7;
		}
		for(int i=58;i<=126;i++) {
			matrix[3][i]=7;
		}
		//+++TRANSICIONES DESDE e4+++
		for(int i=33;i<=47;i++) {//transicion c-digitos
			matrix[4][i]=7;
		}
		for(int i=58;i<=126;i++) {
			matrix[4][i]=7;
		}
		matrix[4][70]=5;//transicion "F"
		matrix[4][68]=5;//transicion "D"
		//+++TRANSICIONES DESDE e5+++
		matrix[5][43]=6;
		matrix[5][45]=6;
		for(int d=48;d<=57;d++) {//transicion cuando llega un digito
			matrix[5][d]=6;
		}
		//+++TRANSICIONES DESDE e6+++
		for(int d=48;d<=57;d++) {//transicion cuando llega un digito
			matrix[6][d]=6;
		}
		for(int i=33;i<=47;i++) {//transicion c-digitos
			matrix[6][i]=7;
		}
		for(int i=58;i<=126;i++) {
			matrix[6][i]=7;
		}
		
	}

		

	
	
	


}
