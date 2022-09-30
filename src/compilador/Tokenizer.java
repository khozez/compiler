package compilador;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Tokenizer {
	static String nextLine;
	static int state;
	static char entry;
	

	public static void main(String[] args) {
		File code = new File ("codigo.txt");
		try {
			Scanner s = new Scanner(code);
			while(s.hasNextLine()) {
				nextLine = s.nextLine();
				for(int i=0;i<nextLine.length();i++) {
					entry = nextLine.charAt(i);
					System.out.println((int)entry);
					state=transition(state,entry);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



	}


	private static int transition(int s, char e) {
		
		return 0;
	}

}
