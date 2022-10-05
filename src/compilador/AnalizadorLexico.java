package compilador;
import compilador.AccionesSemanticas.AccionSemantica;

import java.io.*;


public class AnalizadorLexico {
		public static final char TAB = '\t';
		public static final char BLANCO = ' ';
		public static final char NUEVA_LINEA = '\n';
		public static final char DIGITO = 'd';
		public static final char MAYUSCULA = 'M';
		public static final char MINUSCULA = 'm';
		public static final char COMILLA = 'x';
		public static final int MAXIMA_LONGITUD_IDENTIFICADORES = 25;
		public static final long MAX_LONG = (long) Math.pow(2, 32) - 1;
		public static final double MIN_DOUBLE_VALUE = 2.2250738585072014D-308;
		public static final double MAX_DOUBLE_VALUE = 1.7976931348623157D+308;
		public static BufferedInputStream lector;
		private static TablaPalabrasReservadas PR;
		private static TablaSimbolos TS;

		private static int cant_lineas = 1;
		private char entry;
		private static MTE matriz_estados;
		private static MAS mas;
		public static String lexema = "";
		private int estado = 0;

		public AnalizadorLexico() {
			matriz_estados = new MTE();
			mas = new MAS();
			PR = new TablaPalabrasReservadas();
			TS = new TablaSimbolos();
		}

		public static void newLine(){
			cant_lineas += 1;
		}

		public static int getCantLineas(){ return cant_lineas;}

		private static char getTipo(char c){
			int caracter = (int) c;
			if (caracter >= 48 && caracter <= 57){
				return DIGITO;
			}
			if (caracter >= 65 && caracter <= 90) {
				return MAYUSCULA;
			}
			if (caracter >= 97 && caracter <=122){
				return MINUSCULA;
			}
			if (caracter == 39)
				return COMILLA;
			if (caracter == 13)
				return NUEVA_LINEA;
			return c;
		}

		public int getToken(BufferedInputStream lector, char c){
			int id_columna;
			switch (getTipo(c)){
				case DIGITO:
					id_columna = 6;
					break;
				case MAYUSCULA:
					id_columna = 5;
					break;
				case MINUSCULA:
					id_columna = 4;
					break;
				case BLANCO:
					id_columna = 0;
					break;
				case TAB:
					id_columna = 1;
					break;
				case NUEVA_LINEA:
					id_columna = 2;
					break;
				case 'D':
					id_columna = 3;
					break;
				case '_':
					id_columna = 7;
					break;
				case '=':
					id_columna = 8;
					break;
				case ':':
					id_columna = 9;
					break;
				case '!':
					id_columna = 10;
					break;
				case '/':
					id_columna = 11;
					break;
				case '*':
					id_columna = 12;
					break;
				case '+':
					id_columna = 13;
					break;
				case '-':
					id_columna = 14;
					break;
				case '(':
					id_columna = 15;
					break;
				case ')':
					id_columna = 16;
					break;
				case '{':
					id_columna = 17;
					break;
				case '}':
					id_columna = 18;
					break;
				case ';':
					id_columna = 19;
					break;
				case ',':
					id_columna = 20;
					break;
				case '.':
					id_columna = 21;
					break;
				case '<':
					id_columna = 22;
					break;
				case '>':
					id_columna = 23;
					break;
				case COMILLA:
					id_columna = 24;
					break;
				default:
					id_columna = 25;
					break;
			}

			//System.out.println("Par: ["+estado+", "+id_columna+"]");
			AccionSemantica as = mas.action_matrix[estado][id_columna];
			int id_token = as.ejecutar(lector, lexema);
			if (id_token != -1){
				lexema = "";
			}
			estado = matriz_estados.states_matrix[estado][id_columna];
			if (estado == MTE.ESTADO_FINAL){
				estado = 0;
			}
			return id_token;
		}
}