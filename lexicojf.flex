%%
%class Lexicojf
%standalone

%{
	// Esto es el código mio
%}

Finlinea = \r | \n | \r\n
Simbolo = [^\r\n]

Comentario = {Comentario1} | {Comentario2}
Comentario1 = "/*" [^*] ~"*/" | "/*" "*"+ "/"
Comentario2 = "//" {Simbolo}* {Finlinea}?

Caracter = .

Macro = [a-zA-Z0-9]+
Expresion = {Caracter}+ 
Exp = [^ <\n\r\t] {Caracter}+ " {"

%state DECL
%state AUX
%state REGEXP
%state REGLEX
%state ERAUX
%state ESTAUX
%state ESTAUX2

%%
<YYINITIAL> {
	{Comentario}        {/*Sin accion*/}
	"%%"				{yybegin(DECL);}
}
<DECL> {
	{Comentario}		{/*Sin accion*/}
	"%{"				{yybegin(AUX);}
	"%init{"			{yybegin(AUX);}
	"%eof{" 			{yybegin(AUX);}
	"%initthrow{"		{yybegin(AUX);}
	{Macro} [ ]+ "="	{System.out.println("Macro: " + yytext());
						 yybegin(ESTAUX);}
}
<AUX> {
	{Comentario}		{/*Sin accion*/}
	"%}"				{yybegin(DECL);}
	"%init}"			{yybegin(DECL);}
	"%eof}" 			{yybegin(DECL);}
	"%initthrow}"		{yybegin(DECL);}
}
<ESTAUX> {
	{Comentario}		{/*Sin accion*/}
	{Expresion}			{System.out.println("ER1: " + yytext());
						 yybegin(REGEXP);}
}
<ESTAUX2> {
	{Comentario}		{/*Sin accion*/}
	{Expresion}			{System.out.println(yytext());
						 yybegin(REGEXP);}
}
<REGEXP> {
	{Comentario}		{/*Sin accion*/}
	"%"					{yybegin(REGLEX);}
	[ ]+				{yybegin(ESTAUX2);}
	{Macro} [ ]+ "="	{System.out.println("Macro: " + yytext());
						 yybegin(ESTAUX);}
	"%%"				{yybegin(REGLEX);}
}
<REGLEX> {
	{Comentario}		{/*Sin accion*/}
	"<"					{yybegin(ERAUX);}
	{Exp}				{System.out.println("EXP: " + yytext());
						 yybegin(ERAUX);}
}
<ERAUX> {
	{Comentario}		{/*Sin accion*/}
	{Caracter}+			{yybegin(REGLEX);}
}
/* error fallback */
[^]						{;}