package compilador;

import java.io.FileNotFoundException;
import java.security.KeyStore.Entry;

public class AnalizadorLexico {
    String nextLine;
    static int state = 0;
    char entry;
    static MTE matrix = new MTE();
    static MAS matris_semantica = new MAS();
    static String cadena = new String();

    public char tipo(char entry){
        if (entry == 'D'){ return entry;}
        else if (Character.isDigit(entry)){
            return '1';
        }
        else if (Character.isLowerCase(entry)){
            return '2';
        }
        else if (Character.isUpperCase(entry)){
            return '3';
        }
        else { return entry;}
    }

	public int analizar(char entry) {
		try {
			if(((int)entry != 32 ) || (entry !='\n') || (entry !='\t')) {
				if(state!=-1) {
					int simbolo = 0;
					switch(tipo(entry)){
                        case '1': simbolo = 1;
						case '2': simbolo = 2;
                        case '3': simbolo = 3;
                        case '(': simbolo = 4;
                        case ')': simbolo = 5;
                        case '{': simbolo = 6;
                        case '}': simbolo = 7;
                        case '*': simbolo = 8;
                        case '-': simbolo = 9;
                        case '+': simbolo = 10;
                        case '/': simbolo = 11;
                        case '<': simbolo = 12;
                        case '>': simbolo = 13;
                        case ';': simbolo = 14;
                        case ',': simbolo = 15;
                        case '_': simbolo = 16;
                        case '%': simbolo = 17;
                        case '\'': simbolo = 18;
                        case '': simbolo = 17;
                        case 'D': simbolo = 17;
                        case 'D': simbolo = 17;
					}
							
                    state = matrix.transition(state,simbolo);			
					AccionSemantica AS = matris_semantica.ejecutar(state,simbolo);
					int token = AS.ejecutar(cadena, entry);
							
							
					System.out.println("Estado"+state);
                    return token;
				}
			}
            return -1;

		}
	     catch (FileNotFoundException e) {
			e.printStackTrace();
            return 0;
	        }
    }
}

