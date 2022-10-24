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
    0,    1,    2,    5,    5,    5,    7,    7,    7,    7,
    7,    7,    7,    6,    6,    3,    3,    4,    4,   10,
   10,   10,   12,   14,   13,   13,   18,   16,   16,   19,
   19,   11,   15,   15,   20,   21,    8,    8,    8,    8,
    8,    8,    9,    9,    9,   26,   27,   27,   25,   25,
   25,   28,   28,   24,   24,   24,   24,   23,   23,   22,
   17,   17,
};
final static short yylen[] = {                            2,
    5,    1,    2,   15,   15,   14,    3,    3,    5,    2,
    3,    2,    3,    3,    3,    2,    1,    3,    2,    1,
    1,    1,    5,    7,    3,    1,    4,    3,    1,    4,
    2,    2,    1,    1,    5,    1,    3,    1,    1,    8,
    1,    3,    3,    3,    1,    4,    3,    1,    3,    3,
    1,    1,    1,    3,    3,    3,    3,   13,    9,    4,
    1,    1,
};
final static short yydefred[] = {                         0,
    2,    0,    0,    0,    0,    0,    0,    0,   17,    0,
   21,    0,    0,   26,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,   16,   38,   39,   62,
   61,    0,   32,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,   19,   31,    0,   25,
    0,    0,    0,   34,   27,    0,    0,    0,    0,    0,
   53,   51,    0,    0,    0,    0,    0,   42,    0,   18,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   60,    0,    0,   30,    0,
    0,   23,    0,   28,    0,    0,    0,    0,   49,   50,
    0,    0,    0,    0,    0,    0,    0,    0,   24,   46,
    0,    0,    0,    0,   35,   47,    0,    0,    0,    0,
    0,   40,    0,    0,    0,    0,   59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,    0,   12,    0,
    0,    0,    0,    0,   10,    0,    0,   11,   13,    8,
    6,    0,    7,    0,    0,    0,    4,    5,    9,
};
final static short yydgoto[] = {                          2,
    3,    7,    8,   24,   25,  120,  143,   26,   59,    9,
   10,   11,   12,   13,   53,   56,   32,   14,   33,   54,
   91,   28,   29,   64,   60,   61,   95,   62,
};
final static short yysindex[] = {                      -234,
    0,    0,  -87, -225, -233, -214,  -75, -216,    0, -176,
    0,   13,  -60,    0, -209,   32,   18, -184,   57,   59,
   62,   74,   58,  -86,    0,   61,    0,    0,    0,    0,
    0, -136,    0, -135, -213,   66, -176,    0, -220, -131,
 -147, -131, -127, -138, -139,   75,    0,    0,  -39,    0,
   95,  -86,   11,    0,    0,   96,   94,   99,   35,   20,
    0,    0,  -15,  101,  102,  103, -125,    0, -131,    0,
 -106, -131,   93,   97, -176, -131, -131, -131, -131, -131,
 -131, -131, -131, -131, -105,    0, -101, -111,    0,   35,
  123,    0, -176,    0,  125,  129,   20,   20,    0,    0,
   35,   35,   35,   35,   51,   52,  118,  119,    0,    0,
 -131,  -86, -225,  -78,    0,    0, -112,   60, -187,  127,
 -168,    0, -131, -131,  -33,   64,    0,   35,   35,  143,
  -85,  -84,  -86,   65,  151,  152, -109, -183,   71,   72,
  -65,  -51,  -44,  138, -183, -183,    0,  139,    0,   44,
  140,  141,  -64,  144,    0,    2,   15,    0,    0,    0,
    0,  -73,    0,  146,  148,  149,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -162,
    0, -145,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   77,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   84,    0,    0,    0,    0,  169,  -41,  153,  -34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  170,
    0,    0,    0,    0,    0,  174,  -26,  -19,    0,    0,
  175,  176,  178,  179,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  163,  164,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  121,    0,  -14,  187,    0,  -37,   24,  -13,  230,
  208,    0,    0,    0,    0,    0,  150,  212,  -17,    0,
    0,    0,    0,  206,   27,    0,    0,  -45,
};
final static int YYTABLESIZE=292;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
   52,   52,   52,   52,   71,   52,   45,  149,   45,  131,
   45,  132,  121,  153,   43,  141,   43,   52,   43,   57,
   52,   44,    1,   44,   45,   44,   63,   77,   63,   78,
   96,    5,   43,   99,  100,    4,   58,    6,   15,   44,
   18,   19,   16,   45,   19,   20,    6,   46,   20,   17,
   51,   21,   22,   36,   21,   22,   34,   94,   90,  153,
   23,   79,   35,   23,   36,  116,   80,  101,  102,  103,
  104,   37,  153,   45,   19,   46,   38,   77,   20,   78,
  152,  142,  123,  124,   21,   22,   77,   39,   78,   30,
   31,  126,  127,   23,   20,   20,   40,  117,   41,   20,
   20,   42,  159,   97,   98,   20,   20,  156,  157,  128,
  129,   22,   22,   43,   20,   44,   22,   22,  137,   47,
   48,    5,   22,   22,   55,   58,  164,   65,  150,   67,
   22,   22,   69,   70,   72,   73,   74,   75,   76,  165,
   46,   85,   86,   87,   45,   19,   88,   45,   19,   20,
   89,   92,   20,  105,   93,   21,   22,  106,   21,   22,
   46,  144,  107,  108,   23,  110,  154,   23,  144,  144,
   45,   19,  111,  112,  113,   20,  114,  115,  119,  154,
  154,   21,   22,  134,  122,  125,  133,  138,  135,  136,
   23,  139,  140,  145,  146,  147,  155,  158,  160,  161,
  162,    3,  163,  166,  167,   58,  168,  169,   33,   29,
   36,   37,   45,   19,   48,   55,   54,   20,   56,   57,
  151,   14,   15,   21,   22,  148,   30,   31,   52,   52,
   68,   52,   23,  118,   52,   45,   45,   27,   45,   49,
  130,   45,  109,   43,   43,   50,   43,   66,    0,   43,
   44,   44,    0,   44,   81,   82,   44,   83,   45,   19,
   84,    0,    0,   20,    0,    0,  151,    0,    0,   21,
   22,   45,   19,    0,    0,    0,   20,    0,   23,  151,
    0,    0,   21,   22,    0,    0,    0,    0,    0,    0,
    0,   23,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   44,   47,   41,   59,   43,   43,
   45,   45,  125,   58,   41,  125,   43,   59,   45,   37,
   35,   41,  257,   43,   59,   45,   40,   43,   42,   45,
   76,  257,   59,   79,   80,  123,  257,  263,  272,   59,
  257,  258,  257,  257,  258,  262,  263,   24,  262,  125,
  264,  268,  269,  274,  268,  269,   44,   75,   72,   58,
  277,   42,  123,  277,  274,  111,   47,   81,   82,   83,
   84,   40,   58,  257,  258,   52,   59,   43,  262,   45,
  125,  265,  270,  271,  268,  269,   43,  272,   45,  266,
  267,  260,  261,  277,  257,  258,   40,  112,   40,  262,
  263,   40,   59,   77,   78,  268,  269,  145,  146,  123,
  124,  257,  258,   40,  277,   58,  262,  263,  133,   59,
  257,  257,  268,  269,   59,  257,  125,  275,  142,  257,
  269,  277,  272,   59,   40,  125,   41,   44,   40,  125,
  117,   41,   41,   41,  257,  258,  272,  257,  258,  262,
  257,   59,  262,  259,   58,  268,  269,  259,  268,  269,
  137,  138,  274,   41,  277,   41,  143,  277,  145,  146,
  257,  258,   44,  123,  123,  262,   59,   59,  257,  156,
  157,  268,  269,   41,  125,   59,  123,  123,  274,  274,
  277,   41,   41,  123,  123,  261,   59,   59,   59,   59,
  265,  125,   59,  277,   59,  257,   59,   59,  125,   41,
   41,   59,  257,  258,   41,   41,   41,  262,   41,   41,
  265,   59,   59,  268,  269,  277,  266,  267,  270,  271,
   44,  273,  277,  113,  276,  270,  271,    8,  273,   32,
  274,  276,   93,  270,  271,   34,  273,   42,   -1,  276,
  270,  271,   -1,  273,  270,  271,  276,  273,  257,  258,
  276,   -1,   -1,  262,   -1,   -1,  265,   -1,   -1,  268,
  269,  257,  258,   -1,   -1,   -1,  262,   -1,  277,  265,
   -1,   -1,  268,  269,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,
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
"sentencias : bloqueDeclarativa bloqueEjecutable",
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
"ejecutables : control",
"ejecutables : ETIQUETA ':' control",
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

//#line 152 "gramatica.y"

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
//#line 404 "Parser.java"
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
