package compilador;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class AnalizadorLexico {
		public static final char TAB = '\t';
		public static final char BLANCO = ' ';
		public static final char NUEVA_LINEA = '\n';
		public static final int MAXIMA_LONGITUD_IDENTIFICADORES = 25;

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

		//File code = new File ("codigo.txt");

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
							state = matriz_estados.transition(state,(int)entry);
							System.out.println("Estado"+state);
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
}
