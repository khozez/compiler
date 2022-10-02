package compilador;

public class MTE {
	int[][] matrix = new int[13][128];//matrix[estado][entrada]
	public static final int FINAL_STATE = 12;
	

	public MTE() {
		for (int i=0;i<=FINAL_STATE;i++) {
			for(int j=0;j<127;j++) {
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
		matrix[0][47]=FINAL_STATE;//transicion operador /
		matrix[0][42]=FINAL_STATE;//transicion operador *
		matrix[0][43]=FINAL_STATE;//transicion operador +
		matrix[0][45]=FINAL_STATE;//transicion operador -
		//otros simbolos
		matrix[0][40]=FINAL_STATE;//transicion (
		matrix[0][41]=FINAL_STATE;//transicion )
		matrix[0][123]=FINAL_STATE;//transicion {
		matrix[0][125]=FINAL_STATE;//transicion }
		matrix[0][44]=FINAL_STATE;//transicion ,
		matrix[0][59]=FINAL_STATE;//transicion ;
		//transicion <
		matrix[0][60]=7;
		//transicion '
		matrix[0][39]=10;
		//transicion >
		matrix[0][62]=11;
		//+++TRANSICIONES DESDE e1+++
		matrix[1][95]=1;//transicion _
		
		//transicion cualquier caracter menos los permitidos para
		for(int i=33;i<=47;i++) {
			matrix[1][i]=FINAL_STATE;
		}
		for(int i=58;i<=64;i++) {
			matrix[1][i]=FINAL_STATE;
		}
		for(int i=91;i<=94;i++) {
			matrix[1][i]=FINAL_STATE;
		}
		for(int i=123;i<=126;i++) {
			matrix[1][i]=FINAL_STATE;
		}
		matrix[1][96]=FINAL_STATE;
		
		//+++TRANSICIONES DESDE e2+++
		for(int i=33;i<=126;i++) {
			matrix[2][i]=FINAL_STATE;
		}
		//+++TRANSICIONES DESDE e3+++
		for(int i=33;i<=47;i++) {//transicion c-digitos
			matrix[3][i]=FINAL_STATE;
		}
		for(int i=58;i<=126;i++) {
			matrix[3][i]=FINAL_STATE;
		}
		//+++TRANSICIONES DESDE e4+++
		for(int i=33;i<=47;i++) {//transicion c-digitos
			matrix[4][i]=FINAL_STATE;
		}
		for(int i=58;i<=126;i++) {
			matrix[4][i]=FINAL_STATE;
		}
		matrix[4][68]=5;//transicion "D"
		//+++TRANSICIONES DESDE e5+++
		matrix[5][43]=6;//transicion +
		matrix[5][45]=6;//transicion -
		for(int d=48;d<=57;d++) {//transicion cuando llega un digito
			matrix[5][d]=6;
		}
		//+++TRANSICIONES DESDE e6+++
		for(int d=48;d<=57;d++) {//transicion cuando llega un digito
			matrix[6][d]=6;
		}
		for(int i=33;i<=47;i++) {//transicion c-digitos
			matrix[6][i]=FINAL_STATE;
		}
		for(int i=58;i<=126;i++) {
			matrix[6][i]=FINAL_STATE;
		}
		//+++TRANSICIONES DESDE e7+++
		for(int i=32;i<=59;i++) {//transicion c-<
			matrix[7][i]=FINAL_STATE;
		}
		for(int i=61;i<=126;i++) {//transicion c-<
			matrix[7][i]=FINAL_STATE;
		}
		matrix[7][60]=8;
		//+++TRANSICIONES DESDE e8+++
		for(int i=32;i<=61;i++) {//transicion c->
			matrix[8][i]=FINAL_STATE;
		}
		for(int i=63;i<=126;i++) {//transicion c->
			matrix[8][i]=FINAL_STATE;
		}
		matrix[8][62]=9;
		//+++TRANSICIONES DESDE e9+++
		for(int i=32;i<=61;i++) {//transicion c->
			matrix[9][i]=8;
		}
		for(int i=63;i<=126;i++) {//transicion c->
			matrix[9][i]=8;
		}
		matrix[9][62]=FINAL_STATE;
		//+++TRANSICIONES DESDE e10+++
		for(int i=32;i<=38;i++) {//transicion c-'
			matrix[10][i]=10;
		}
		for(int i=40;i<=126;i++) {//transicion c-'
			matrix[10][i]=10;
		}
		matrix[10][39]=FINAL_STATE;
		//+++TRANSICIONES DESDE e11+++
		for(int i=32;i<=60;i++) {//transicion c-=
			matrix[11][i]=FINAL_STATE;
		}
		for(int i=62;i<=126;i++) {//transicion c-=
			matrix[11][i]=FINAL_STATE;
		}
		matrix[11][61]=FINAL_STATE;
		
	}

	public int transition(int s, int e) {

		return matrix[s][e];
	}
	
	public void show() {
		System.out.println(matrix[0][62]);
	}
}
