package compilador;

import compilador.AccionesSemanticas.*;

public class MAS {
    public AccionSemantica[][] action_matrix = new AccionSemantica[12][26];

    public MAS(){
        action_matrix[0][0]= new AS2();
        action_matrix[0][1]= new AS2();
        action_matrix[0][2]= new AS2();
        action_matrix[0][3]= new AS6();
        action_matrix[0][4]= new AS6();
        action_matrix[0][5]= new AS6();
        action_matrix[0][6]= new AS6();
        action_matrix[0][7]= new ASe();
        action_matrix[0][8]= new AS6();
        action_matrix[0][9]= new ASe();
        action_matrix[0][10]= new ASe();
        for (int i=11; i<=20; i++){
            action_matrix[0][i]= new AS8();
        }
        action_matrix[0][21]= new AS6();
        action_matrix[0][22]= new AS6();
        action_matrix[0][23]= new AS6();
        action_matrix[0][24]= new AS6();
        action_matrix[0][25]= new ASe();
        action_matrix[1][0]= new AS1();
        action_matrix[1][1]= new AS1();
        action_matrix[1][2]= new AS1();
        action_matrix[1][3]= new AS6();
        action_matrix[1][4]= new AS6();
        action_matrix[1][5]= new AS6();
        action_matrix[1][6]= new AS6();
        action_matrix[1][7]= new AS6();
        for (int i=8; i<=25; i++){
            action_matrix[1][i]= new AS1();
        }
        for (int i=0; i<=8; i++){
            action_matrix[2][i]= new AS8();
        }
        action_matrix[2][9]= new AS7();
        action_matrix[2][10]= new AS7();
        for (int i=11; i<=25; i++){
            action_matrix[2][i]= new AS8();
        }
        for (int i=0; i<=5; i++){
            action_matrix[3][i]= new AS5();
        }
        action_matrix[3][6]= new AS6();
        for (int i=7; i<=20; i++){
            action_matrix[3][i]= new AS5();
        }
        action_matrix[3][21]= new AS6();
        for (int i=22; i<=25; i++){
            action_matrix[3][i]= new AS5();
        }
        for (int i=0; i<=2; i++){
            action_matrix[4][i]= new AS4();
        }
        action_matrix[4][3]= new AS6();
        action_matrix[4][4]= new AS4();
        action_matrix[4][5]= new AS4();
        action_matrix[4][6]= new AS6();
        for (int i=7; i<=25; i++){
            action_matrix[4][i]= new AS4();
        }
        for (int i=0; i<=5; i++){
            action_matrix[5][i]= new ASd();
        }
        for (int i=7; i<=12; i++){
            action_matrix[5][i]= new ASd();
        }
        for (int i=15; i<=25; i++){
            action_matrix[5][i]= new ASd();
        }
        action_matrix[5][6]= new AS6();
        action_matrix[5][13]= new AS6();
        action_matrix[5][14]= new AS6();
        for (int i=0; i<=5; i++){
            action_matrix[6][i]= new AS4();
        }
        action_matrix[6][6]= new AS6();
        for (int i=7; i<=25; i++){
            action_matrix[6][i]= new AS4();
        }
        for (int i=0; i<=7; i++){
            action_matrix[7][i]= new AS8();
        }
        action_matrix[7][8]= new AS7();
        for (int i=9; i<=21; i++){
            action_matrix[7][i]= new AS8();
        }
        action_matrix[7][22]= new AS9();
        action_matrix[7][23]= new AS8();
        action_matrix[7][24]= new AS8();
        action_matrix[7][25]= new AS8();
        for (int i=0; i<=25; i++){
            action_matrix[8][i]= new AS9();
        }
        for (int i=0; i<=22; i++){
            action_matrix[9][i]= new AS9();
        }
        action_matrix[9][23]= new AS3();
        action_matrix[9][24]= new AS9();
        action_matrix[9][25]= new AS9();
        action_matrix[10][0]= new AS6();
        action_matrix[10][1]= new AS6();
        action_matrix[10][2]= new ASn();
        for (int i=3; i<=23; i++){
            action_matrix[10][i]= new AS6();
        }
        action_matrix[10][24]= new AS10();
        action_matrix[10][25]= new AS6();
        for (int i=0; i<=7; i++){
            action_matrix[11][i]= new AS8();
        }
        action_matrix[11][8]= new AS7();
        for (int i=9; i<=25; i++){
            action_matrix[11][i]= new AS8();
        }
    }
}
