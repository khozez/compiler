package compilador;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class AnalizadorLexico {
		public static final char TAB = '\t';
		public static final char BLANCO = ' ';
		public static final char NUEVA_LINEA = '\n';
		public static final int MAXIMA_LONGITUD_IDENTIFICADORES = 25;
		public static final long MAX_LONG = (long) Math.pow(2, 32) - 1;
		public static final double MIN_DOUBLE_VALUE = 2.2250738585072014D-308;
		public static final double MAX_DOUBLE_VALUE = 1.7976931348623157D+308;


		private File code;
		private String nextLine;
		private static int cant_lineas = 1;
		private int state = 0;
		private char entry;
		private MTE matriz_estados;

		public void AnalizadorLexico(File code_file) {
			matriz_estados = new MTE();
			code = code_file;
		}

		public static void newLine(){
			cant_lineas += 1;
		}
		public static int getCantLineas(){ return cant_lineas;}

		//QUITADO DE RESTRICCION EN EL IF, AGREGAR A MATRIZ
		public void runAnalizadorLexico() {
			matriz_estados.show();
			try {
				Scanner s = new Scanner(code);
				while(s.hasNextLine()) {
					nextLine = s.nextLine();
					for(int i=0;i<nextLine.length();i++) {
						entry = nextLine.charAt(i);
						System.out.println("Caracter: "+entry +" -- Codigo: "+(int)entry);
						if(state!=-1) {
							state = matriz_estados.proxEstado(state, (int)entry);
							System.out.println("Estado"+state);
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		private static char getTipo(char caracter){
			return 'a';
		}
}
