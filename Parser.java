//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
package compilador;

import AccionesSemanticas.AccionSemantica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
//#line 27 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short IF=258;
public final static short THEN=259;
public final static short ELSE=260;
public final static short END_IF=261;
public final static short OUT=262;
public final static short FUN=263;
public final static short RETURN=264;
public final static short BREAK=265;
public final static short UI=266;
public final static short F=267;
public final static short WHEN=268;
public final static short FOR=269;
public final static short MAYORIGUAL=270;
public final static short MENORIGUAL=271;
public final static short ASIGN=272;
public final static short DISTINTO=273;
public final static short VALOR=274;
public final static short CADENA=275;
public final static short IGUAL=276;
public final static short ETIQUETA=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    6,
    6,    6,    9,    9,    9,    9,    9,    9,    9,    8,
    8,    4,    4,    5,    5,   12,   12,   12,   14,   16,
   15,   15,   20,   18,   18,   21,   21,   13,   17,   17,
   22,   23,   10,   10,   10,   10,   11,   11,   11,   27,
   28,   28,   26,   26,   26,   29,   29,   25,   25,   25,
   25,    7,    7,   24,   19,   19,
};
final static short yylen[] = {                            2,
    5,    1,    2,    1,    1,    1,    1,    1,    3,   15,
   15,   14,    3,    3,    5,    2,    3,    2,    3,    3,
    3,    2,    1,    3,    2,    1,    1,    1,    5,    7,
    3,    1,    4,    3,    1,    4,    2,    2,    1,    1,
    5,    1,    3,    1,    1,    8,    3,    3,    1,    4,
    3,    1,    3,    3,    1,    1,    1,    3,    3,    3,
    3,   13,    9,    4,    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    4,    0,    0,    7,    0,    0,   23,    0,
   27,    0,    0,   32,   44,    0,    0,    0,    0,    0,
    0,    0,    0,    3,    0,   22,    0,   45,    0,   25,
   66,   65,    0,   38,    0,    0,    0,    0,    0,    0,
   57,   55,    0,    0,    0,    0,    0,    0,    9,    1,
    0,    0,   24,   37,    0,   31,    0,    0,    0,   40,
    0,   33,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   64,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   53,   54,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   36,    0,    0,   29,   50,    0,
    0,    0,   34,    0,    0,    0,   51,    0,   30,    0,
    0,    0,   41,    0,   46,    0,    0,    0,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   62,
    0,   18,    0,    0,    0,    0,    0,   16,    0,    0,
   17,   19,   14,   12,    0,   13,    0,    0,    0,   10,
   11,   15,
};
final static short yydgoto[] = {                          2,
    3,   12,   13,   14,   15,   16,   38,  122,  146,   18,
   49,   19,   20,   21,   22,   23,   69,   83,   43,   24,
   44,   70,  107,   25,   54,   50,   51,   90,   52,
};
final static short yysindex[] = {                      -219,
    0,    0,  -37, -178, -190,   28,   49, -161,   62,   63,
   46, -111,    0, -207, -216,    0,    0,   47,    0, -231,
    0,   61,  -16,    0,    0, -223, -149, -166,   70, -149,
 -146, -157,   54,    0, -158,    0, -156,    0,   56,    0,
    0,    0, -140,    0, -139, -203,   81,   65,   19,   16,
    0,    0,  -15,   84,   85, -231,   87, -142,    0,    0,
 -143, -149,    0,    0,  -39,    0,   92, -216,    8,    0,
 -149,    0, -149, -149, -149, -149, -149, -149, -149, -149,
 -125,    0,   94,   95, -119, -133, -115, -149,   89,  102,
  100,   16,   16,    0,    0,   19,   19,   19,   19,   26,
   97, -231,   30,   91,    0,   19,  115,    0,    0, -149,
 -216, -231,    0, -178,  -96,  103,    0,   29,    0,  -98,
 -204,  108,    0, -185,    0, -149, -149,  -33,   50,    0,
   19,   19,  128, -102, -100, -216,   69,  143,  152,   38,
 -170,   73,   75,  -59,  -43,    2,  144, -170, -170,    0,
  145,    0,   -6,  146,  149,  -52,  151,    0,   11,   20,
    0,    0,    0,    0,  -62,    0,  157,  160,  161,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -68,  -78,    0,  -51,    0,    0,  -82,
    0,  -80,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,  163,  -34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   98,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  183,    0,    0,    0,    0,    0,    0,
  184,  -26,  -19,    0,    0,  190,  192,  197,  199,    0,
    0,    0,    0,    0,    0,  202,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  187,  189,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,  135,   17,    0,  -14,  221,    9,    0,  -55,  153,
   -7,  248,  220,    0,    0,    0,    0,    0,  154,  227,
  -25,    0,    0,    0,  235,   27,    0,    0,  -27,
};
final static int YYTABLESIZE=313;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         56,
   56,   56,   56,   56,   87,   56,   49,   45,   49,  134,
   49,  135,   17,   33,   47,  152,   47,   56,   47,   53,
   17,   48,   53,   48,   49,   48,  125,   73,   34,   74,
   84,   68,   47,   47,   41,   42,   73,    1,   74,   48,
   37,    6,   26,   91,   28,    7,    6,   94,   95,   35,
   48,    9,  162,   37,    6,    8,    5,   75,    7,  156,
   67,   73,   76,   74,    9,  126,  127,   27,  156,   96,
   97,   98,   99,    8,  129,  130,  113,  156,    5,    6,
  106,   26,  117,    7,    8,    4,   37,    6,   28,    9,
   10,    7,  159,  160,  145,   29,  118,    9,   11,   92,
   93,   30,   31,   32,   45,   40,   46,   47,   55,   56,
   58,   10,   60,   61,   63,   62,   64,   35,  131,  132,
   71,  140,   17,   72,   81,   82,  155,   85,   17,   86,
   48,   88,   89,  100,  101,  167,   34,  153,  102,  103,
  104,  105,  109,  110,  168,    5,    6,  108,  111,  115,
    7,    8,  114,  124,  112,  116,    9,   10,    5,    6,
  121,  123,  144,    7,    8,   11,  128,   39,  137,    9,
   10,  138,  136,  139,   26,   26,   28,   28,   11,   26,
   26,   28,   28,  142,    6,   26,   26,   28,   28,    5,
    6,  141,  143,    5,   26,  148,   28,  149,    6,    5,
    5,  150,  158,  161,  163,    8,    8,  164,    5,  166,
    8,    8,  165,   47,  169,  170,    8,    8,  171,  172,
   39,   43,   39,   35,   52,    8,   41,   42,   56,   56,
   59,   56,   58,  151,   56,   49,   49,   60,   49,   61,
  133,   49,   42,   47,   47,   20,   47,   21,  120,   47,
   48,   48,   59,   48,   77,   78,   48,   79,   37,    6,
   80,   36,   65,    7,   57,  119,  154,   37,    6,    9,
   39,   66,    7,    0,    0,  154,   37,    6,    9,    0,
    0,    7,    0,    0,  154,   37,    6,    9,    0,    0,
    7,    0,   39,  147,   37,    6,    9,    0,  157,    7,
  147,  147,    0,    0,    0,    9,    0,    0,    0,    0,
    0,  157,  157,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   44,   47,   41,   59,   43,   43,
   45,   45,    4,  125,   41,   59,   43,   59,   45,   27,
   12,   41,   30,   43,   59,   45,  125,   43,   12,   45,
   56,   46,   59,  257,  266,  267,   43,  257,   45,   59,
  257,  258,  125,   71,  125,  262,  125,   75,   76,  257,
  274,  268,   59,  257,  258,  263,  125,   42,  262,   58,
  264,   43,   47,   45,  268,  270,  271,   40,   58,   77,
   78,   79,   80,  125,  260,  261,  102,   58,  257,  258,
   88,  272,  110,  262,  263,  123,  257,  258,   40,  268,
  269,  262,  148,  149,  265,  257,  111,  268,  277,   73,
   74,   40,   40,   58,   44,   59,  123,  257,  275,   40,
  257,  269,   59,  272,   59,  272,  257,  257,  126,  127,
   40,  136,  114,   59,   41,   41,  125,   41,  120,  272,
  274,   40,  125,  259,   41,  125,  120,  145,   44,  259,
  274,  257,   41,   44,  125,  257,  258,   59,  123,   59,
  262,  263,  123,  125,   58,   41,  268,  269,  257,  258,
  257,   59,  125,  262,  263,  277,   59,   15,   41,  268,
  269,  274,  123,  274,  257,  258,  257,  258,  277,  262,
  263,  262,  263,   41,  263,  268,  269,  268,  269,  258,
  269,  123,   41,  262,  277,  123,  277,  123,  277,  268,
  269,  261,   59,   59,   59,  257,  258,   59,  277,   59,
  262,  263,  265,  257,  277,   59,  268,  269,   59,   59,
   68,   59,  125,   41,   41,  277,  266,  267,  270,  271,
   41,  273,   41,  277,  276,  270,  271,   41,  273,   41,
  274,  276,   41,  270,  271,   59,  273,   59,  114,  276,
  270,  271,   32,  273,  270,  271,  276,  273,  257,  258,
  276,   14,   43,  262,   30,  112,  265,  257,  258,  268,
  118,   45,  262,   -1,   -1,  265,  257,  258,  268,   -1,
   -1,  262,   -1,   -1,  265,  257,  258,  268,   -1,   -1,
  262,   -1,  140,  141,  257,  258,  268,   -1,  146,  262,
  148,  149,   -1,   -1,   -1,  268,   -1,   -1,   -1,   -1,
   -1,  159,  160,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","IF","THEN","ELSE","END_IF","OUT","FUN",
"RETURN","BREAK","UI","F","WHEN","FOR","MAYORIGUAL","MENORIGUAL","ASIGN",
"DISTINTO","VALOR","CADENA","IGUAL","ETIQUETA",
};
final static String yyrule[] = {
"$accept : programa",
"programa : nombrep '{' sentencias '}' ';'",
"nombrep : ID",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : bloqueDeclarativa",
"sentencia : bloqueEjecutable",
"sentencia : control",
"sentencia : seleccion",
"sentencia : ETIQUETA ':' control",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{' bloqueEjecutableFOR '}' ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{' bloqueEjecutableFOR '}' ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{' bloqueEjecutableFOR '}' ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR ejecutables ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR BREAK ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR ':' BREAK ETIQUETA ';'",
"bloqueEjecutableFOR : ejecutables ';'",
"bloqueEjecutableFOR : BREAK ETIQUETA ';'",
"bloqueEjecutableFOR : BREAK ';'",
"bloqueEjecutableFOR : BREAK expresion ';'",
"condicion_for : ID MAYORIGUAL expresion",
"condicion_for : ID MENORIGUAL expresion",
"bloqueDeclarativa : bloqueDeclarativa declarativa",
"bloqueDeclarativa : declarativa",
"bloqueEjecutable : bloqueEjecutable ejecutables ';'",
"bloqueEjecutable : ejecutables ';'",
"declarativa : variables",
"declarativa : declaracion_funcion",
"declarativa : listaconstantes",
"declaracion_funcion : header_funcion '{' cuerpoFUN '}' ';'",
"header_funcion : FUN ID '(' parametros ')' ':' tipo",
"listaconstantes : listaconstantes ',' const",
"listaconstantes : const",
"const : ID ASIGN VALOR ';'",
"parametros : variable ',' variable",
"parametros : variable",
"variable : tipo variables ',' ID",
"variable : tipo ID",
"variables : variables variable",
"cuerpoFUN : bloqueEjecutable",
"cuerpoFUN : retorno_funcion",
"retorno_funcion : RETURN '(' retorno ')' ';'",
"retorno : expresion",
"ejecutables : ID ASIGN expresion",
"ejecutables : salida",
"ejecutables : seleccion",
"ejecutables : WHEN '(' condicion ')' THEN '{' sentencias '}'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"funcion : ID '(' parametrosreales ')'",
"parametrosreales : factor ',' factor",
"parametrosreales : factor",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : funcion",
"condicion : expresion MENORIGUAL expresion",
"condicion : expresion MAYORIGUAL expresion",
"condicion : expresion DISTINTO expresion",
"condicion : expresion IGUAL expresion",
"seleccion : IF '(' condicion ')' THEN '{' bloqueEjecutable '}' ELSE '{' bloqueEjecutable '}' END_IF",
"seleccion : IF '(' condicion ')' THEN '{' bloqueEjecutable '}' END_IF",
"salida : OUT '(' CADENA ')'",
"tipo : F",
"tipo : UI",
};

//#line 156 "gramatica.y"

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
//#line 415 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
