%{
package compilador;

import AccionesSemanticas.AccionSemantica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
%}

%token ID CADENA VALOR IF THEN ELSE END_IF OUT FUN RETURN BREAK UI F WHEN FOR MAYORIGUAL MENORIGUAL ASIGN DISTINTO IGUAL ETIQUETA
%left '+' '-'
%left '*' '/'

%start programa

%%
programa: nombrep '{' sentencias '}' ';'
		| '{' sentencias '}' ';' {anotarError(errorSintactico, "Falta el nombre del programa")}
		| nombrep sentencias '}' ';' {anotarError(errorSintactico, "Falta el simbolo '{'")}
		| nombrep '{' sentencias ';' {anotarError(errorSintactico, "Falta el simbolo '}'")}
		| nombrep '{' '}' ';' {anotarError(errorSintactico, "Falta el conjunto de sentencias")}
    		| nombrep '{' sentencias '}' {anotarError(errorSintactico, "Se espera ; al final")}
   ;
nombrep: ID
;

sentencias: bloqueDeclarativa bloqueEjecutable  


;


control: FOR '(' ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |error ';' {anotarError(errorSintactico, "Se espera ; al final")}

;

bloqueEjecutableFOR: bloqueEjecutableFOR ejecutables ';'
    | bloqueEjecutableFOR BREAK ';'
    | bloqueEjecutableFOR ':' BREAK ETIQUETA ';'
    | ejecutables ';'
    | BREAK ETIQUETA ';'
    | BREAK ';'
    | BREAK expresion ';'
    | error ';' {anotarError(errorSintactico, "Se espera ; al final")}
    

;

condicion_for: ID MAYORIGUAL expresion
    | ID MENORIGUAL expresion
;

bloqueDeclarativa: bloqueDeclarativa declarativa 
    |declarativa 
;
bloqueEjecutable: bloqueEjecutable ejecutables ';'
    | ejecutables ';'
;

declarativa: variables
           | declaracion_funcion
           | listaconstantes 
;


declaracion_funcion: header_funcion '{' cuerpoFUN '}' ';'

;

header_funcion: FUN ID '(' parametros ')' ':' tipo 

;




listaconstantes: listaconstantes',' const 
    |const
;

const: ID ASIGN VALOR ';'
;

parametros: variable ',' variable
        | variable
;
 

variable: tipo variables ',' ID
	| tipo ID

;

variables: variables variable

cuerpoFUN: bloqueEjecutable	 
	| retorno_funcion

;

retorno_funcion: RETURN '(' retorno ')' ';'
		| error ';' {anotarError(errorSintactico, "Se espera ; al final")}
;

retorno: expresion
;

ejecutables: ID ASIGN expresion
    	| salida
    	| seleccion
    	| WHEN '('condicion')' THEN '{'sentencias'}'
    	| control
	| ETIQUETA ':' control 
;

expresion: expresion '+' termino  
    	| expresion '-' termino  
	| termino
;

funcion: ID '('parametrosreales')'
;
parametrosreales: factor ',' factor
    |factor

termino: termino '*' factor 
	|termino '/' factor 
	|factor
;

factor: ID
    |funcion
;
condicion: expresion MENORIGUAL expresion
	| expresion MAYORIGUAL expresion
	| expresion DISTINTO expresion
	| expresion IGUAL expresion
	| expresion expresion {anotarError(errorSintactico, "Se espera un operador de comparacion")}
;

seleccion: IF '(' condicion ')' THEN '{'bloqueEjecutable'}' ELSE '{'bloqueEjecutable'}' END_IF ';'
    | IF '(' condicion ')' THEN '{'bloqueEjecutable'}' END_IF ';'



;
salida: OUT '(' CADENA ')'
;

tipo: F
   | UI
;


%%

public static List<String> errorLexico = new ArrayList<>();
public static List<String> errorSintactico = new ArrayList<>();


void yyerror(String mensaje) {
        // funcion utilizada para imprimir errores que produce yacc
        System.out.println("Error yacc: " + mensaje);
}

int yylex(){
    int IDtoken = 0; // IDtoken va a guardar el ID del token que genere el AL
    BufferedInputStream lector = AnalizadorLexico.lector; // agarro el lector
    while (true) {
        try {
            if (lector.available() <= 0) { // me aseguro que no este vacio el buffer
                break; // si esta vacio se detiene el while
            }

            lector.mark(1);
            char c = (char) lector.read(); // consigo el siguiente caracter
            lector.reset();
            IDtoken = AnalizadorLexico.getToken(lector, c); // pido el token
            if (IDtoken != -1){ // si AL no define un token entonces sigue probando con los proximos caracteres
                yylval = new ParserVal(AnalizadorLexico.lexema.toString());
                AnalizadorLexico.lexema= ""; // borro lexema porque no lo necesito mas
                return IDtoken; // devuelvo el ID del token
            }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    return 0; // 0 indica que se llego al EOF

void anotarError (ArrayList<String> listaError, String error){ // Agrega un error encontrado, "error" es la descripcion
    listaError.add(Error + " Linea: " + AnalizadorLexico.getCantLineas() + " Posicion: " + AnalizadorLexico.getPosicion());
}

public static void main(String[] args) {
        if (args.length > 1) {
                String archivo_a_leer = args[0];

                try {
                        AnalizadorLexico.lector = new BufferedReader(new FileReader(archivo_a_leer));
                        Parser parser = new Parser();
                        parser.run();
                } catch (IOException excepcion) {
                        excepcion.printStackTrace();
                }
                TablaSimbolos.imprimirTabla();
        } else {
                System.out.println("No se especifico el archivo a compilar");
        }
