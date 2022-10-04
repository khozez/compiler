package compilador;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Tokenizer {

	public static void main(String[] args) {

		AnalizadorLexico AL = new AnalizadorLexico();
		File code = new File ("codigo.txt");
		File out = new File ("out.txt");

		char entry;

		try {
			Scanner s = new Scanner(code);
			while(s.hasNextLine()) {
				String nextLine = s.nextLine();
				for(int i=0;i<nextLine.length();i++) {
					entry = nextLine.charAt(i);
					System.out.println(entry +" "+ (int)entry);
					
					int token = AL.analizar(entry);

					if (token != -1){

					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



	}
}
