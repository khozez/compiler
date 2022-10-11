%{
package compilador;

import AccionesSemanticas.AccionSemantica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
%}

%token ID IF THEN ELSE END_IF OUT FUN RETURN BREAK UI F WHEN FOR MAYORIGUAL MENORIGUAL ASIGN DISTINTO VALOR CADENA IGUAL ETIQUETA
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

sentencias: bloqueDeclarativa sentencias
        | bloqueEjecutable sentencias
        | bloqueDeclarativa 
        | bloqueEjecutable 
        | control sentencias
        | control
        | ETIQUETA':'control 
        | ETIQUETA control {anotarError(errorSintactico, "Se espera : luego de la ETIQUETA")}
        | seleccion ELSE expresion ';'
        | seleccion ELSE ';'
        | seleccion ELSE {anotarError(errorSintactico, "Se espera ; al final")}
        | seleccion ELSE expresion {anotarError(errorSintactico, "Se espera ; al final")}
        | seleccion expresion {anotarError(errorSintactico, "Se espera ELSE luego del bloque de control")}

;

control:FOR '('ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{'bloqueEjecutableFOR'}' ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' declarativa ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' declarativa ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' declarativa ';'
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{'bloqueEjecutableFOR'}' {anotarError(errorSintactico, "Se espera ; al final")}
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{'bloqueEjecutableFOR'}' {anotarError(errorSintactico, "Se espera ; al final")}
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{'bloqueEjecutableFOR'}' {anotarError(errorSintactico, "Se espera ; al final")}
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' declarativa {anotarError(errorSintactico, "Se espera ; al final")}
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' declarativa {anotarError(errorSintactico, "Se espera ; al final")}
    |FOR '('ID ASIGN VALOR ';' condicion_for ';' VALOR ')' declarativa {anotarError(errorSintactico, "Se espera ; al final")}
;

bloqueEjecutableFOR: bloqueEjecutableFOR ejecutables ';'
    | bloqueEjecutableFOR BREAK ';'
    | bloqueEjecutableFOR ':' BREAK ETIQUETA ';'
    | ejecutables ';'
    | BREAK ETIQUETA ';'
    | BREAK ';'
    | BREAK expresion ';'
    |bloqueEjecutableFOR  ejecutables {anotarError(errorSintactico, "Se espera ; al final")}
    | bloqueEjecutableFOR  BREAK {anotarError(errorSintactico, "Se espera ; al final")}
    | bloqueEjecutableFOR BREAK ETIQUETA ';' {anotarError(errorSintactico, "Se espera : antes de la etiqueta")}
    | bloqueEjecutableFOR  ':' BREAK ETIQUETA {anotarError(errorSintactico, "Se espera ; al final")}
    | ejecutables {anotarError(errorSintactico, "Se espera ; al final")}
    | BREAK ETIQUETA {anotarError(errorSintactico, "Se espera ; al final")}
    | BREAK {anotarError(errorSintactico, "Se espera ; al final")}
    | BREAK expresion {anotarError(errorSintactico, "Se espera ; al final")}
    | bloqueEjecutableFOR bloqueDeclarativa {anotarError(errorSintactico, "No es posible realizar declaraciones dentro de un FOR")}
    | bloqueDeclarativa bloqueEjecutableFOR {anotarError(errorSintactico, "No es posible realizar declaraciones dentro de un FOR")}
;

condicion_for: ID MAYORIGUAL expresion
    | ID MENORIGUAL expresion
    | ID expresion {anotarError(errorSintactico, "Es necesario indicar la condicion")}
;

bloqueDeclarativa: bloqueDeclarativa declarativa ';' 
    |declarativa ';'
    |declarativa {anotarError(errorSintactico, "Se espera ; al final")}
;
bloqueEjecutable: bloqueEjecutable ejecutables ';' 
    | ejecutables ';'
    | ejecutables {anotarError(errorSintactico, "Se espera ; al final")}
;
declarativa: 
            | variables
            | variable
           | FUN ID '(' parametros ')' ':' tipo '{' cuerpoFUN '}' ';'
           | FUN ID '(' parametros ')' tipo '{' cuerpoFUN '}' ';' {anotarError(errorSintactico, "Se espera :")}
           | const listaconstantes ';'
           | const listaconstantes {anotarError(errorSintactico, "Se espera ; al final")}
;
listaconstantes: listaconstantes',' ID ASIGN VALOR 
    | ID ASIGN VALOR 
    | listaconstantes ID ASIGN VALOR {anotarError(errorSintactico, "Se espera ,")}
;

parametros: variable ',' variable
        | variable
        | variable variable {anotarError(errorSintactico, "Se espera ,")}
;

variable: tipo ID
    | tipo {anotarError(errorSintactico, "Se debe nombrar la variable")}
    | ID {anotarError(errorSintactico, "Se debe definir un tipo ")}


variables: ID ',' variables
        | ID variables {anotarError(errorSintactico, "Se espera ,")}
        | ID
;

cuerpoFUN: sentencias RETURN '(' retorno ')' ';'
    | sentencias RETURN '(' retorno ')' {anotarError(errorSintactico, "Se espera ,")}
    | sentencias RETURN retorno {anotarError(errorSintactico, "Se espera ,")}
    | sentencias {anotarError(errorSintactico, "Se espera sentencia RETURN")}
;
retorno: expresion
;
ejecutables: ID ASIGN expresion
	| funcion
    | salida
    | seleccion
    | WHEN '('condicion')' THEN '{'sentencias'}'
    | WHEN '('condicion')' sentencias {anotarError(errorSintactico, "Se espera sentencia THEN")}
;
expresion: termino '+' expresion
    |termino '-' expresion
    |termino '+' termino
	| termino '-' termino 
    | termino termino {anotarError(errorSintactico, "Se espera un operador")}
	| termino
;

funcion: ID '('parametrosreales')'
;
parametrosreales: factor ',' factor
    |factor factor {anotarError(errorSintactico, "Se espera ,")}
    |factor

termino: termino '*' factor 
	|termino '/' factor 
    |termino factor {anotarError(errorSintactico, "Se espera un operador")}
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
    | expresion expresion {anotarError(errorSintactico, "Se espera un operador de comparacion")}
;

seleccion: IF '(' condicion ')' THEN '{'bloqueEjecutable'}' ELSE '{'bloqueEjecutable'}' END_IF ';'
    | IF '(' condicion ')' THEN '{'bloqueEjecutable'}' END_IF ';'
    | IF '(' condicion ')' THEN declarativa ELSE declarativa END_IF ';'
    | IF '(' condicion ')' THEN declarativa END_IF ';'
    | IF '(' condicion ')' THEN '{'bloqueEjecutable'}' ELSE '{'bloqueEjecutable'}' END_IF {anotarError(errorSintactico, "Se espera ;")}
    | IF '(' condicion ')' THEN '{'bloqueEjecutable'}' END_IF {anotarError(errorSintactico, "Se espera ;")}
    | IF '(' condicion ')' THEN declarativa ELSE declarativa END_IF  {anotarError(errorSintactico, "Se espera ;")}
    | IF '(' condicion ')' THEN declarativa END_IF {anotarError(errorSintactico, "Se espera ;")}
;
salida: OUT '(' CADENA ')'
;

tipo: F
	| UI
;

const: VALOR
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
