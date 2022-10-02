package compilador;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Tokenizer {

	public static void main(String[] args) {
		String nextLine;
		int state = 0;
		char entry;
		MTE matrix = new MTE();
		File code = new File ("codigo.txt");
		matrix.show();
		try {
			Scanner s = new Scanner(code);
			while(s.hasNextLine()) {
				nextLine = s.nextLine();
				for(int i=0;i<nextLine.length();i++) {
					entry = nextLine.charAt(i);
					System.out.println(entry +" "+ (int)entry);
					if(((int)entry != 32 ) || (entry !='\n') || (entry !='\t')) {
						if(state!=-1) {
							state = matrix.transition(state,(int)entry);
							System.out.println("Estado"+state);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}



	}
}
