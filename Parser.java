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
    0,    0,    0,    0,    0,    0,    1,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    2,    2,    2,    2,
    5,    5,    5,    5,    5,    5,    5,    5,    5,    5,
    5,    5,    9,    9,    9,    9,    9,    9,    9,    9,
    9,    9,    9,    9,    9,    9,    9,    9,    9,    8,
    8,    8,    3,    3,    3,    4,    4,    4,   10,   10,
   10,   10,   10,   10,   10,   18,   18,   18,   14,   14,
   14,   13,   13,   13,   12,   12,   12,   16,   16,   16,
   16,   19,   11,   11,   11,   11,   11,   11,    7,    7,
    7,    7,    7,    7,   20,   24,   24,   24,   23,   23,
   23,   23,   25,   25,   25,   22,   22,   22,   22,   22,
    6,    6,    6,    6,    6,    6,    6,    6,   21,   15,
   15,   17,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    3,    4,    1,    2,    2,    1,
    1,    2,    1,    3,    2,    4,    3,    2,    3,    2,
   15,   15,   14,   13,   13,   12,   14,   13,   14,   12,
   12,   11,    3,    3,    5,    2,    3,    2,    3,    2,
    2,    4,    4,    1,    2,    1,    2,    2,    2,    3,
    3,    2,    3,    2,    1,    3,    2,    1,    0,    1,
    1,   11,   10,    3,    2,    5,    3,    4,    3,    1,
    2,    2,    1,    1,    3,    2,    1,    6,    5,    3,
    1,    1,    3,    1,    1,    1,    8,    5,    3,    3,
    3,    3,    2,    1,    4,    3,    2,    1,    3,    3,
    2,    1,    1,    1,    1,    3,    3,    3,    3,    2,
   14,   10,   10,    8,   13,    9,    9,    7,    4,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    7,    0,    0,    0,    0,    0,    0,    0,  121,  120,
    0,    0,  122,    0,    0,    0,    0,    0,    0,    0,
    0,   60,   61,    0,    0,   84,   85,    0,    0,    0,
    0,    0,    0,   76,    0,    0,    0,    0,    0,    0,
   15,    2,    8,    0,    9,    0,   12,    0,    0,  104,
   20,  105,    0,  102,   54,   57,   72,    0,    0,    5,
    0,    3,   83,    0,    0,   75,    0,    0,    0,    0,
    0,    0,   14,   53,   56,   17,    0,    0,    0,    0,
    0,    0,  101,    0,    0,   64,    0,    1,   95,    0,
   97,    0,    0,    0,    0,  110,    0,  119,   74,    0,
    0,    0,    0,   16,   89,    0,   90,    0,   99,  100,
  101,   67,    0,    0,   96,  107,  106,  108,  109,    0,
    0,   71,    0,    0,   88,    0,   68,    0,    0,    0,
    0,   69,    0,    0,    0,    0,   66,    0,    0,   86,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  114,    0,    0,    0,   87,    0,    0,   52,    0,    0,
    0,   56,    0,    0,    0,    0,   50,   51,    0,    0,
    0,    0,  112,  113,    0,    0,   82,   80,   63,    0,
    0,    0,    0,   62,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   26,    0,    0,    0,    0,
    0,   78,    0,   38,    0,    0,    0,    0,    0,    0,
    0,   36,    0,   24,    0,   25,  111,   37,   39,    0,
   34,   23,    0,    0,   33,    0,    0,   42,    0,   53,
   21,   22,   35,
};
final static short yydgoto[] = {                          3,
    4,  153,   16,   17,   18,  140,   67,  147,  194,   20,
   21,   22,   23,  101,   24,  154,   25,   59,  178,   52,
   27,   68,   53,   64,   54,
};
final static short yysindex[] = {                      -101,
    0,  933,    0, -104,  -35,   -6,   11, -233,    0,    0,
   24,   26,    0,  -45,  -44,  933,  933,  933, -167,   29,
   37,    0,    0, -149, -139,    0,    0,  -62,   -1,  -18,
 -234, -234, -136,    0, -234, -135,  101, -234, -115, -126,
    0,    0,    0,   85,    0,   91,    0,  115,  -41,    0,
    0,    0,  -39,    0,    0,    0,    0, -112,   -7,    0,
   32,    0,    0,  130,  -29,    0, -151,  131,  133, -154,
  136,  -92,    0,    0,    0,    0,  122, -234, -234, -234,
 -234,  -31,    0,  -87,  -80,    0,  -60,    0,    0, -234,
    0, -234, -234, -234, -234,    0,  -57,    0,    0,  -37,
  158,  759,  -71,    0,    0,  -39,    0,  -39,    0,    0,
    0,    0,  -66,  -61,    0,    0,    0,    0,    0,  209,
 -154,    0,  -11,   87,    0,  154,    0,  -53,  -18,  -79,
 -222,    0, -190,   94,  933,  -34,    0,  -40,  299,    0,
 -196,  166,  121,  933,  128, -202,  187, -169,  195,   12,
    0,  933,   -3,  132,    0, -234, -234,    0,  -33,  139,
  221,    0,  234,  172,  -38,  243,    0,    0,  272,   74,
   90,  -79,    0,    0,  302, -234,    0,    0,    0,  284,
  338,  339,  455,    0,  356, -129,  309,  307,  327,  137,
  343,  -26, -129,  363,  347,    0, -129,  349, -129,  351,
  380,    0,  381,    0,  385,  271,  -30,  386,  157, -196,
  388,    0,  658,    0,  676,    0,    0,    0,    0,  393,
    0,    0,  184,  408,    0,  410,  412,    0,  414,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  -42,    0,  -42,  405,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   27,   36,  117,  618,  384,
  423,    0,    0,   21,    0,    0,    0,   28,    0,  109,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  384,    0,  423,    0,    1,  597,    0,
    0,    0,   41,    0,    0,    0,    0,    0,  151,    0,
  474,    0,    0,    0,  133,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  441,    0,    0,    0,
    0,   57,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  436,
    0,  135,    0,    0,    0,   73,    0,   89,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -150,
    0,    0,    0,    0,    0,    0,    0,    0,  127,    0,
    0,    0,    0,    0,  -42,    0,    0,    0,    0,    0,
  217,  169,    0,  -24,    0,    0,    0,    0,    0,    0,
    0,  -24,  354,    0,    0,    0,    0,    0,    0,    0,
  191,    0,  238,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  636,
    0,    0,    0,    0,    0,  421,  462,  636,  636,    0,
  357,  833,  421,  421,  689,    0,  421,  491,  421,  513,
  345,    0,  704,    0,  727,  -28,  745,  531,    0,  820,
  772,    0,  421,    0,  421,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  559,  577,    0,  792,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0, 1153,  953, -116,   18, 1126, 1084,    0,   -9,  788,
  923,   20,  -16,    0, -103,  332,    0,    0,  310, 1059,
    0,  447,   -4,    0,  999,
};
final static int YYTABLESIZE=1288;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         32,
  103,  176,   80,   78,   32,   79,  121,   81,   33,  170,
   80,  171,   40,  139,   90,   81,   59,   76,   28,  134,
   73,    2,   48,   37,   34,   33,   10,   59,  221,  143,
   49,   41,  204,   35,   59,   11,   87,  141,  142,   50,
   94,  103,  103,  103,  103,  103,  133,  103,   82,   34,
   36,   86,   66,  100,   48,  183,   93,   73,  103,  103,
  129,   73,   60,   38,   73,   39,    8,  156,  157,    9,
   10,   50,   91,  106,  108,    9,   10,   13,   73,   73,
   42,   94,   59,  122,   10,   10,   59,   55,   92,   48,
  160,  161,   49,   11,   11,   56,   49,   93,   94,   94,
   59,   82,   99,   82,  132,   48,   50,   57,   77,   59,
   59,    9,   10,   91,   93,   93,   13,   58,   92,   93,
   30,   94,   50,   62,   95,  103,   74,    5,    6,   92,
   91,   91,    7,    8,   59,  192,    9,   10,   11,   69,
   70,   72,   12,   74,   13,   73,   92,   92,   34,   75,
   65,   10,    5,    6,   32,    1,   88,    7,    8,   84,
   11,    9,   10,   11,   12,   94,   77,   77,  118,   13,
   89,   97,   14,   98,   13,   13,  102,  138,    6,  103,
  104,   93,    7,  206,   74,   74,  112,  213,   11,  215,
  116,  113,   59,   59,    5,    6,  114,   91,  123,    7,
    8,  120,  126,    9,   10,   11,   12,  127,   65,  135,
  128,   13,  136,   92,   14,   48,  144,   48,   48,   99,
  137,   30,  146,   12,  151,   48,  118,   48,    9,   10,
   48,   31,   50,   77,   50,   50,   31,  117,   30,   59,
  169,   13,   50,  152,   50,  159,  220,   50,  116,   85,
  203,   74,  155,  162,    9,   10,  166,  103,  103,   59,
  165,  172,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  163,  103,  103,   65,  103,  103,   73,  173,
   73,   73,   73,   73,   73,   73,   73,   73,   73,   73,
   10,   10,  174,  118,   73,  117,  175,   73,   94,   11,
   11,  179,   94,   94,   94,   94,   94,   94,   94,   94,
   94,   94,  180,   94,   93,  116,   94,   94,   93,   93,
   93,   93,   93,   93,   93,   93,   93,   93,  209,   93,
   91,  130,   93,   93,   91,   91,   91,   91,   91,   91,
   91,   91,   91,   91,  115,   91,   92,  181,   91,   91,
   92,   92,   92,   92,   92,   92,   92,   92,   92,   92,
  184,   92,  117,  182,   92,   92,   77,  196,   77,   77,
   77,   77,   77,   77,   77,   77,   77,   77,  188,  189,
   13,   13,   77,   55,   74,   77,   74,   74,   74,   74,
   74,   74,   74,   74,   74,   74,  191,  201,   59,   59,
   74,  202,  115,   74,   74,  212,  186,  214,   65,  216,
   65,   65,   65,   65,   65,   65,   65,   65,   65,   65,
  209,  223,   58,  148,   65,  118,  118,   65,  118,  197,
  118,  118,  118,  118,  118,  118,  118,  118,  217,  218,
   19,   55,  118,  219,  222,  118,  225,  116,  116,  199,
  116,  228,  116,  116,  116,  116,  116,  116,  116,  116,
  229,   32,   74,   74,  116,  129,  230,  116,  231,  115,
  232,    8,  233,    4,    9,   10,   70,   59,   81,   59,
   58,   79,   13,  164,   71,  185,    0,  208,    0,    0,
   30,    0,    0,    0,  117,  117,    0,  117,   19,  117,
  117,  117,  117,  117,  117,  117,  117,    0,   55,    0,
    0,  117,   31,    0,  117,    0,    0,    0,    0,   32,
    0,    0,    0,    0,    0,    0,    0,    5,    6,   74,
   28,    0,    7,    8,    0,  207,    9,   10,   11,    0,
  129,    0,    0,    0,   13,    0,    8,   58,   30,    9,
   10,    0,    0,    0,    0,  138,    6,   13,   27,    0,
    7,    0,    0,  129,    0,   19,   11,    0,    0,    8,
   31,    0,    9,   10,    0,    0,   29,    0,    0,  190,
   13,    0,    0,  129,    0,    0,   32,    0,   28,    8,
    0,    0,    9,   10,    0,    0,   18,    0,    0,    0,
   13,  115,  115,    0,  115,    0,  115,  115,  115,  115,
  115,  115,  115,  115,    0,   30,   27,   86,  115,    5,
    6,  115,    0,    0,    7,    8,    0,  207,    9,   10,
   11,    0,    0,    0,   29,   59,   13,   31,    0,    0,
   55,   55,    0,    0,    0,   55,   55,   55,   55,   55,
   55,   55,   55,    0,   18,   28,    0,   55,    0,    0,
   55,    0,   74,    0,    0,    0,   74,   74,   74,   74,
   74,   74,   74,   74,    0,   86,   86,    0,   74,   58,
   58,   74,    0,   27,   58,   58,   58,   58,   58,   58,
   58,   58,    0,   59,   59,    0,   58,   19,   19,   58,
    0,   29,   19,   19,   19,   19,   19,   19,   19,   19,
    0,  138,    6,    0,   19,  209,    7,   19,   32,   32,
    0,   18,   11,   32,   32,   32,   32,   32,   32,   32,
   32,    0,    0,  209,    0,   32,    0,    0,   32,    0,
    0,    0,   86,    0,    0,    0,   44,   30,   30,    0,
    0,    0,   30,   30,   30,   30,   30,   30,   30,   30,
   59,   45,    0,    0,   30,    0,    0,   30,    0,   31,
   31,    0,    0,    0,   31,   31,   31,   31,   31,   31,
   31,   31,  226,    0,   47,    0,   31,   28,   28,   31,
    0,    0,   28,   28,   28,   28,   28,   28,   28,   28,
  227,    0,   41,   44,   28,    0,    0,   28,    0,    0,
    0,    0,    0,   44,    0,   27,   27,    0,    0,    0,
   27,   27,   27,   27,   27,   27,   27,   27,   45,   40,
    0,    0,   27,   29,   29,   27,    0,    0,   29,   29,
   29,   29,   29,   29,   29,   29,    0,    0,    0,   43,
   29,   47,    0,   29,   18,    0,    0,    0,   18,   18,
   18,   18,   18,   18,   18,   18,    0,    0,    0,   41,
    0,    0,    0,   18,    0,   86,    0,   48,   48,   86,
   86,   86,   86,   86,   86,   86,   86,    0,    0,    0,
   46,    0,    0,   59,   86,    0,   40,   59,    0,   59,
   59,    0,    0,   59,   59,    0,    0,  131,    0,    0,
    0,    0,   59,    0,    5,    6,   43,    0,    0,    7,
    8,    0,  207,    9,   10,   11,    0,    0,  150,    0,
    0,   13,    5,    6,    0,    0,    0,    7,    8,   46,
  207,    9,   10,   11,   48,   44,   44,    0,    0,   13,
   44,   44,    0,   44,   44,   44,   44,   46,    0,    0,
   45,   45,   44,    0,    0,   45,   45,  187,   45,   45,
   45,   45,    0,    0,    0,  198,  200,   45,    0,    0,
   44,    0,    0,   47,   47,    0,    0,    0,   47,   47,
    0,   47,   47,   47,   47,    0,    0,  224,    0,    0,
   47,   41,   41,    0,    0,    0,   41,   41,    0,   41,
   41,   41,   41,    0,    0,    5,    6,  124,   41,    0,
    7,    8,    0,    0,    9,   10,   11,   12,   40,   40,
   65,    0,   13,   40,   40,   14,   40,   40,   40,   40,
    0,    0,    0,    0,    0,   40,    0,    0,   43,   43,
    0,   83,    0,   43,   43,    0,   43,   43,   43,   43,
   26,  149,   26,   91,    0,   43,    0,    0,    0,    0,
    0,    0,    0,    0,   26,   26,   26,   48,  109,  110,
  111,   48,    0,    0,   48,    0,   26,   48,  115,    0,
   46,    0,    0,    0,   46,   46,    0,   46,   46,   46,
   46,    0,   51,    0,   83,  149,   83,    0,  195,    0,
    0,    0,    0,    0,   63,  195,  211,    0,    0,  195,
    0,  195,    0,    0,    0,    0,    0,   19,  211,   19,
    0,    0,   77,    0,    0,  211,    0,  211,  193,    0,
    0,   19,   19,   19,    0,  193,  210,    0,    0,  193,
   96,  193,    0,   19,   15,    0,   29,    0,  210,    0,
   26,  105,  107,    0,    0,  210,    0,  210,   43,   45,
   47,    0,    0,    0,    0,  116,  117,  118,  119,    0,
   61,    0,    0,    0,    0,    0,    0,    0,   26,    5,
    6,    0,    0,   26,    7,    8,    0,   26,    9,   10,
   11,   12,   26,    0,    0,    0,   13,    0,    0,   14,
   26,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   19,    0,  158,
   26,    0,    0,    0,    0,    0,    0,    0,    0,  167,
  168,   26,    0,    0,   26,    0,    0,    0,  177,    0,
    0,   26,   26,    0,  125,   26,    0,   26,    0,  177,
   19,    0,    0,    0,   26,    0,    0,    0,    0,   19,
    0,   26,    0,   26,    0,  205,    0,   19,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  145,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   42,   43,   40,   45,   44,   47,   44,   43,
   42,   45,   58,  130,   44,   47,   59,   59,  123,  123,
    0,  123,  257,  257,    5,   44,    0,    0,   59,  133,
   59,   14,   59,   40,   59,    0,   44,  260,  261,  274,
    0,   41,   42,   43,   44,   45,   58,   47,   53,   30,
   40,   59,   33,   70,  257,  172,    0,   40,   58,   59,
  257,   41,  125,   40,   44,   40,  263,  270,  271,  266,
  267,  274,    0,   78,   79,  266,  267,  274,   58,   59,
  125,   41,  125,  100,   58,   59,   59,   59,    0,  257,
  260,  261,  260,   58,   59,   59,  125,   41,   58,   59,
  125,  106,  257,  108,  121,  257,  274,  257,    0,  260,
  261,  266,  267,   41,   58,   59,    0,  257,  270,  271,
  257,  273,  274,  125,  276,  125,    0,  257,  258,   41,
   58,   59,  262,  263,    0,  265,  266,  267,  268,  275,
   40,  257,  269,   59,  274,  125,   58,   59,  129,   59,
    0,  125,  257,  258,   40,  257,  125,  262,  263,  272,
  125,  266,  267,  268,  269,  125,   58,   59,    0,  274,
   41,   41,  277,   41,   58,   59,   41,  257,  258,  272,
   59,  125,  262,  193,   58,   59,  274,  197,  268,  199,
    0,  272,   58,   59,  257,  258,  257,  125,   41,  262,
  263,  259,  274,  266,  267,  268,  269,  274,   58,  123,
  272,  274,   59,  125,  277,  257,  123,  257,  257,  257,
  274,  257,  257,  269,   59,  257,   58,  257,  266,  267,
  257,  272,  274,  125,  274,  274,  272,    0,  257,  264,
  274,  125,  274,  123,  274,   59,  277,  274,   58,  257,
  277,  125,  125,   59,  266,  267,  125,  257,  258,  125,
  264,  123,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,  261,  273,  274,  125,  276,  277,  258,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  264,  265,   59,  125,  274,   58,  125,  277,  258,  264,
  265,   59,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  271,   41,  273,  258,  125,  276,  277,  262,  263,
  264,  265,  266,  267,  268,  269,  270,  271,   58,  273,
  258,  123,  276,  277,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  271,    0,  273,  258,  274,  276,  277,
  262,  263,  264,  265,  266,  267,  268,  269,  270,  271,
   59,  273,  125,  274,  276,  277,  258,   59,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   41,   41,
  264,  265,  274,    0,  258,  277,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,   41,  261,  264,  265,
  274,   59,   58,  277,    0,   59,  123,   59,  258,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
   58,  265,    0,  125,  274,  257,  258,  277,  260,  123,
  262,  263,  264,  265,  266,  267,  268,  269,   59,   59,
    0,   58,  274,   59,   59,  277,   59,  257,  258,  123,
  260,   59,  262,  263,  264,  265,  266,  267,  268,  269,
  277,    0,   58,   59,  274,  257,   59,  277,   59,  125,
   59,  263,   59,    0,  266,  267,   41,  261,  125,   59,
   58,  125,  274,  152,   38,  176,   -1,  125,   -1,   -1,
    0,   -1,   -1,   -1,  257,  258,   -1,  260,   58,  262,
  263,  264,  265,  266,  267,  268,  269,   -1,  125,   -1,
   -1,  274,    0,   -1,  277,   -1,   -1,   -1,   -1,   58,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  125,
    0,   -1,  262,  263,   -1,  265,  266,  267,  268,   -1,
  257,   -1,   -1,   -1,  274,   -1,  263,  125,   58,  266,
  267,   -1,   -1,   -1,   -1,  257,  258,  274,    0,   -1,
  262,   -1,   -1,  257,   -1,  125,  268,   -1,   -1,  263,
   58,   -1,  266,  267,   -1,   -1,    0,   -1,   -1,  125,
  274,   -1,   -1,  257,   -1,   -1,  125,   -1,   58,  263,
   -1,   -1,  266,  267,   -1,   -1,    0,   -1,   -1,   -1,
  274,  257,  258,   -1,  260,   -1,  262,  263,  264,  265,
  266,  267,  268,  269,   -1,  125,   58,    0,  274,  257,
  258,  277,   -1,   -1,  262,  263,   -1,  265,  266,  267,
  268,   -1,   -1,   -1,   58,    0,  274,  125,   -1,   -1,
  257,  258,   -1,   -1,   -1,  262,  263,  264,  265,  266,
  267,  268,  269,   -1,   58,  125,   -1,  274,   -1,   -1,
  277,   -1,  258,   -1,   -1,   -1,  262,  263,  264,  265,
  266,  267,  268,  269,   -1,   58,   59,   -1,  274,  257,
  258,  277,   -1,  125,  262,  263,  264,  265,  266,  267,
  268,  269,   -1,   58,   59,   -1,  274,  257,  258,  277,
   -1,  125,  262,  263,  264,  265,  266,  267,  268,  269,
   -1,  257,  258,   -1,  274,   58,  262,  277,  257,  258,
   -1,  125,  268,  262,  263,  264,  265,  266,  267,  268,
  269,   -1,   -1,   58,   -1,  274,   -1,   -1,  277,   -1,
   -1,   -1,  125,   -1,   -1,   -1,   58,  257,  258,   -1,
   -1,   -1,  262,  263,  264,  265,  266,  267,  268,  269,
  125,   58,   -1,   -1,  274,   -1,   -1,  277,   -1,  257,
  258,   -1,   -1,   -1,  262,  263,  264,  265,  266,  267,
  268,  269,  125,   -1,   58,   -1,  274,  257,  258,  277,
   -1,   -1,  262,  263,  264,  265,  266,  267,  268,  269,
  125,   -1,   58,   16,  274,   -1,   -1,  277,   -1,   -1,
   -1,   -1,   -1,  125,   -1,  257,  258,   -1,   -1,   -1,
  262,  263,  264,  265,  266,  267,  268,  269,  125,   58,
   -1,   -1,  274,  257,  258,  277,   -1,   -1,  262,  263,
  264,  265,  266,  267,  268,  269,   -1,   -1,   -1,   58,
  274,  125,   -1,  277,  258,   -1,   -1,   -1,  262,  263,
  264,  265,  266,  267,  268,  269,   -1,   -1,   -1,  125,
   -1,   -1,   -1,  277,   -1,  258,   -1,   58,   59,  262,
  263,  264,  265,  266,  267,  268,  269,   -1,   -1,   -1,
   58,   -1,   -1,  258,  277,   -1,  125,  262,   -1,  264,
  265,   -1,   -1,  268,  269,   -1,   -1,  120,   -1,   -1,
   -1,   -1,  277,   -1,  257,  258,  125,   -1,   -1,  262,
  263,   -1,  265,  266,  267,  268,   -1,   -1,  141,   -1,
   -1,  274,  257,  258,   -1,   -1,   -1,  262,  263,   17,
  265,  266,  267,  268,  125,  257,  258,   -1,   -1,  274,
  262,  263,   -1,  265,  266,  267,  268,  125,   -1,   -1,
  257,  258,  274,   -1,   -1,  262,  263,  180,  265,  266,
  267,  268,   -1,   -1,   -1,  188,  189,  274,   -1,   -1,
  193,   -1,   -1,  257,  258,   -1,   -1,   -1,  262,  263,
   -1,  265,  266,  267,  268,   -1,   -1,  210,   -1,   -1,
  274,  257,  258,   -1,   -1,   -1,  262,  263,   -1,  265,
  266,  267,  268,   -1,   -1,  257,  258,  259,  274,   -1,
  262,  263,   -1,   -1,  266,  267,  268,  269,  257,  258,
   32,   -1,  274,  262,  263,  277,  265,  266,  267,  268,
   -1,   -1,   -1,   -1,   -1,  274,   -1,   -1,  257,  258,
   -1,   53,   -1,  262,  263,   -1,  265,  266,  267,  268,
    2,  139,    4,   65,   -1,  274,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   16,   17,   18,  258,   80,   81,
   82,  262,   -1,   -1,  265,   -1,   28,  268,   90,   -1,
  258,   -1,   -1,   -1,  262,  263,   -1,  265,  266,  267,
  268,   -1,   19,   -1,  106,  183,  108,   -1,  186,   -1,
   -1,   -1,   -1,   -1,   31,  193,  194,   -1,   -1,  197,
   -1,  199,   -1,   -1,   -1,   -1,   -1,    2,  206,    4,
   -1,   -1,   49,   -1,   -1,  213,   -1,  215,  186,   -1,
   -1,   16,   17,   18,   -1,  193,  194,   -1,   -1,  197,
   67,  199,   -1,   28,    2,   -1,    4,   -1,  206,   -1,
  102,   78,   79,   -1,   -1,  213,   -1,  215,   16,   17,
   18,   -1,   -1,   -1,   -1,   92,   93,   94,   95,   -1,
   28,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  130,  257,
  258,   -1,   -1,  135,  262,  263,   -1,  139,  266,  267,
  268,  269,  144,   -1,   -1,   -1,  274,   -1,   -1,  277,
  152,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  102,   -1,  146,
  172,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  156,
  157,  183,   -1,   -1,  186,   -1,   -1,   -1,  165,   -1,
   -1,  193,  194,   -1,  102,  197,   -1,  199,   -1,  176,
  135,   -1,   -1,   -1,  206,   -1,   -1,   -1,   -1,  144,
   -1,  213,   -1,  215,   -1,  192,   -1,  152,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  135,
};
}
final static short YYFINAL=3;
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
"programa : nombrep '{' sentencias '}'",
"programa : '{' sentencias '}'",
"programa : nombrep sentencias '}'",
"programa : nombrep '{' sentencias",
"programa : nombrep '{' '}'",
"programa : nombrep '{' sentencias '}'",
"nombrep : ID",
"sentencias : bloqueDeclarativa sentencias",
"sentencias : bloqueEjecutable sentencias",
"sentencias : bloqueDeclarativa",
"sentencias : bloqueEjecutable",
"sentencias : control sentencias",
"sentencias : control",
"sentencias : ETIQUETA ':' control",
"sentencias : ETIQUETA control",
"sentencias : seleccion ELSE expresion ';'",
"sentencias : seleccion ELSE ';'",
"sentencias : seleccion ELSE",
"sentencias : seleccion ELSE expresion",
"sentencias : seleccion expresion",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{' bloqueEjecutableFOR '}' ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{' bloqueEjecutableFOR '}' ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{' bloqueEjecutableFOR '}' ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' declarativa ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' declarativa ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' VALOR ')' declarativa ';'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' '{' bloqueEjecutableFOR '}'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' VALOR ')' '{' bloqueEjecutableFOR '}'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' '{' bloqueEjecutableFOR '}'",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '+' VALOR ')' declarativa",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' '-' VALOR ')' declarativa",
"control : FOR '(' ID ASIGN VALOR ';' condicion_for ';' VALOR ')' declarativa",
"bloqueEjecutableFOR : bloqueEjecutableFOR ejecutables ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR BREAK ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR ':' BREAK ETIQUETA ';'",
"bloqueEjecutableFOR : ejecutables ';'",
"bloqueEjecutableFOR : BREAK ETIQUETA ';'",
"bloqueEjecutableFOR : BREAK ';'",
"bloqueEjecutableFOR : BREAK expresion ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR ejecutables",
"bloqueEjecutableFOR : bloqueEjecutableFOR BREAK",
"bloqueEjecutableFOR : bloqueEjecutableFOR BREAK ETIQUETA ';'",
"bloqueEjecutableFOR : bloqueEjecutableFOR ':' BREAK ETIQUETA",
"bloqueEjecutableFOR : ejecutables",
"bloqueEjecutableFOR : BREAK ETIQUETA",
"bloqueEjecutableFOR : BREAK",
"bloqueEjecutableFOR : BREAK expresion",
"bloqueEjecutableFOR : bloqueEjecutableFOR bloqueDeclarativa",
"bloqueEjecutableFOR : bloqueDeclarativa bloqueEjecutableFOR",
"condicion_for : ID MAYORIGUAL expresion",
"condicion_for : ID MENORIGUAL expresion",
"condicion_for : ID expresion",
"bloqueDeclarativa : bloqueDeclarativa declarativa ';'",
"bloqueDeclarativa : declarativa ';'",
"bloqueDeclarativa : declarativa",
"bloqueEjecutable : bloqueEjecutable ejecutables ';'",
"bloqueEjecutable : ejecutables ';'",
"bloqueEjecutable : ejecutables",
"declarativa :",
"declarativa : variables",
"declarativa : variable",
"declarativa : FUN ID '(' parametros ')' ':' tipo '{' cuerpoFUN '}' ';'",
"declarativa : FUN ID '(' parametros ')' tipo '{' cuerpoFUN '}' ';'",
"declarativa : const listaconstantes ';'",
"declarativa : const listaconstantes",
"listaconstantes : listaconstantes ',' ID ASIGN VALOR",
"listaconstantes : ID ASIGN VALOR",
"listaconstantes : listaconstantes ID ASIGN VALOR",
"parametros : variable ',' variable",
"parametros : variable",
"parametros : variable variable",
"variable : tipo ID",
"variable : tipo",
"variable : ID",
"variables : ID ',' variables",
"variables : ID variables",
"variables : ID",
"cuerpoFUN : sentencias RETURN '(' retorno ')' ';'",
"cuerpoFUN : sentencias RETURN '(' retorno ')'",
"cuerpoFUN : sentencias RETURN retorno",
"cuerpoFUN : sentencias",
"retorno : expresion",
"ejecutables : ID ASIGN expresion",
"ejecutables : funcion",
"ejecutables : salida",
"ejecutables : seleccion",
"ejecutables : WHEN '(' condicion ')' THEN '{' sentencias '}'",
"ejecutables : WHEN '(' condicion ')' sentencias",
"expresion : termino '+' expresion",
"expresion : termino '-' expresion",
"expresion : termino '+' termino",
"expresion : termino '-' termino",
"expresion : termino termino",
"expresion : termino",
"funcion : ID '(' parametrosreales ')'",
"parametrosreales : factor ',' factor",
"parametrosreales : factor factor",
"parametrosreales : factor",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : termino factor",
"termino : factor",
"factor : ID",
"factor : VALOR",
"factor : funcion",
"condicion : expresion MENORIGUAL expresion",
"condicion : expresion MAYORIGUAL expresion",
"condicion : expresion DISTINTO expresion",
"condicion : expresion IGUAL expresion",
"condicion : expresion expresion",
"seleccion : IF '(' condicion ')' THEN '{' bloqueEjecutable '}' ELSE '{' bloqueEjecutable '}' END_IF ';'",
"seleccion : IF '(' condicion ')' THEN '{' bloqueEjecutable '}' END_IF ';'",
"seleccion : IF '(' condicion ')' THEN declarativa ELSE declarativa END_IF ';'",
"seleccion : IF '(' condicion ')' THEN declarativa END_IF ';'",
"seleccion : IF '(' condicion ')' THEN '{' bloqueEjecutable '}' ELSE '{' bloqueEjecutable '}' END_IF",
"seleccion : IF '(' condicion ')' THEN '{' bloqueEjecutable '}' END_IF",
"seleccion : IF '(' condicion ')' THEN declarativa ELSE declarativa END_IF",
"seleccion : IF '(' condicion ')' THEN declarativa END_IF",
"salida : OUT '(' CADENA ')'",
"tipo : F",
"tipo : UI",
"const : VALOR",
};

//#line 186 "gramatica.y"

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
//#line 695 "Parser.java"
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
case 2:
//#line 21 "gramatica.y"
{anotarError(errorSintactico, "Falta el nombre del programa")}
break;
case 3:
//#line 22 "gramatica.y"
{anotarError(errorSintactico, "Falta el simbolo '{'")}
break;
case 4:
//#line 23 "gramatica.y"
{anotarError(errorSintactico, "Falta el simbolo '}'")}
break;
case 5:
//#line 24 "gramatica.y"
{anotarError(errorSintactico, "Falta el conjunto de sentencias")}
break;
case 6:
//#line 25 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 15:
//#line 38 "gramatica.y"
{anotarError(errorSintactico, "Se espera : luego de la ETIQUETA")}
break;
case 18:
//#line 41 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 19:
//#line 42 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 20:
//#line 43 "gramatica.y"
{anotarError(errorSintactico, "Se espera ELSE luego del bloque de control")}
break;
case 27:
//#line 53 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 28:
//#line 54 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 29:
//#line 55 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 30:
//#line 56 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 31:
//#line 57 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 32:
//#line 58 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 40:
//#line 68 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 41:
//#line 69 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 42:
//#line 70 "gramatica.y"
{anotarError(errorSintactico, "Se espera : antes de la etiqueta")}
break;
case 43:
//#line 71 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 44:
//#line 72 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 45:
//#line 73 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 46:
//#line 74 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 47:
//#line 75 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 48:
//#line 76 "gramatica.y"
{anotarError(errorSintactico, "No es posible realizar declaraciones dentro de un FOR")}
break;
case 49:
//#line 77 "gramatica.y"
{anotarError(errorSintactico, "No es posible realizar declaraciones dentro de un FOR")}
break;
case 52:
//#line 83 "gramatica.y"
{anotarError(errorSintactico, "Es necesario indicar la condicion")}
break;
case 55:
//#line 88 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 58:
//#line 92 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 63:
//#line 98 "gramatica.y"
{anotarError(errorSintactico, "Se espera :")}
break;
case 65:
//#line 100 "gramatica.y"
{anotarError(errorSintactico, "Se espera ; al final")}
break;
case 68:
//#line 104 "gramatica.y"
{anotarError(errorSintactico, "Se espera ,")}
break;
case 71:
//#line 109 "gramatica.y"
{anotarError(errorSintactico, "Se espera ,")}
break;
case 73:
//#line 113 "gramatica.y"
{anotarError(errorSintactico, "Se debe nombrar la variable")}
break;
case 74:
//#line 114 "gramatica.y"
{anotarError(errorSintactico, "Se debe definir un tipo ")}
break;
case 76:
//#line 118 "gramatica.y"
{anotarError(errorSintactico, "Se espera ,")}
break;
case 79:
//#line 123 "gramatica.y"
{anotarError(errorSintactico, "Se espera ,")}
break;
case 80:
//#line 124 "gramatica.y"
{anotarError(errorSintactico, "Se espera ,")}
break;
case 81:
//#line 125 "gramatica.y"
{anotarError(errorSintactico, "Se espera sentencia RETURN")}
break;
case 88:
//#line 134 "gramatica.y"
{anotarError(errorSintactico, "Se espera sentencia THEN")}
break;
case 93:
//#line 140 "gramatica.y"
{anotarError(errorSintactico, "Se espera un operador")}
break;
case 97:
//#line 147 "gramatica.y"
{anotarError(errorSintactico, "Se espera ,")}
break;
case 101:
//#line 152 "gramatica.y"
{anotarError(errorSintactico, "Se espera un operador")}
break;
case 110:
//#line 164 "gramatica.y"
{anotarError(errorSintactico, "Se espera una comparacion")}
break;
case 115:
//#line 171 "gramatica.y"
{anotarError(errorSintactico, "Se espera ;")}
break;
case 116:
//#line 172 "gramatica.y"
{anotarError(errorSintactico, "Se espera ;")}
break;
case 117:
//#line 173 "gramatica.y"
{anotarError(errorSintactico, "Se espera ;")}
break;
case 118:
//#line 174 "gramatica.y"
{anotarError(errorSintactico, "Se espera ;")}
break;
//#line 1032 "Parser.java"
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
