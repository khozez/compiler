%{
package compilador;

import AccionesSemanticas.AccionSemantica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
%}

%token ID IF THEN ELSE END_IF OUT FUN RETURN BREAK UI F WHEN FOR MAYORIGUAL MENORIGUAL ASIGN DISTINTO VALOR CADENA IGUAL

%left '+' '-'
%left '*' '/'

%start programa

%%
programa: nombrep '{' sentencias '}' ;

sentencias: bloqueDeclarativa sentencias
        | bloqueEjecutable sentencias
        | bloqueDeclarativa 
        | bloqueEjecutable 
        | control sentencias
        | control
        | ETIQUETA':'control 
        | control ELSE expresion ';'
;

control:FOR '('ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' declarativa ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' declarativa ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' declarativa ';'
;

bloqueEjecutableFOR: bloqueEjecutableFOR  ejecutables ';'
    | bloqueEjecutableFOR  BREAK ';'
    | bloqueEjecutableFOR  ':' BREAK ETIQUETA ';'
    | ejecutables ';'
    | BREAK ETIQUETA ';'
    | BREAK ';'
    | BREAK expresion ';'
;

condicion_for: ID MAYORIGUAL expresion
    | ID MENORIGUAL expresion
;

bloqueDeclarativa: bloqueDeclarativa declarativa ';' 
    |declarativa ';'
;
bloqueEjecutable: bloqueEjecutable ejecutables ';' 
    | ejecutables ';'
;
declarativa: tipo variables
           | FUN ID '(' parametros ')' ':' tipo '{' cuerpoFUN '}' ';'
           | const listaconstantes ';'
;
listaconstantes: listaconstantes',' ID ASIGN VALOR 
    | ID ASIGN VALOR 
;

parametros: tipo ID ',' tipo ID
        | tipo ID
        |
;


variables: ID ',' variables
         | ID
;

cuerpoFUN: sentencias RETURN '(' retorno ')' ';'
;
retorno: expresion
;
ejecutables: ID ASIGN expresion
	| funcion
    | salida
    | seleccion
    | WHEN '('condicion')' THEN sentencias
;
expresion: termino '+' expresion
    |termino '-' expresion
    |termino '+' termino
	| termino '-' termino 
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
    |VALOR
    |funcion
;
condicion: expresion MENORIGUAL expresion
	| expresion MAYORIGUAL expresion
	| expresion DISTINTO expresion
	| expresion IGUAL expresion
;

seleccion: IF '(' condicion ')' THEN '{'bloqueEjecutable'}' ELSE '{'bloqueEjecutable'}' ENDIF ';'
    | IF '(' condicion ')' THEN '{'bloqueEjecutable'}' END_IF ';'
    | IF '(' condicion ')' THEN declarativa ELSE declarativa END_IF ';'
    | IF '(' condicion ')' THEN declarativa END_IF ';'
;
salida: OUT '(' CADENA ')'
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
    listaError.add(Error + " Linea:" + AnalizadorLexico.getCantLineas());
}
