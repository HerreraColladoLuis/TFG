import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Principal {
	
	  public static void main(String argv[]) throws Exception {
	    if (argv.length == 0) {
	      System.out.println("Usage : java Lexicojf [ --encoding <name> ] <inputfile(s)>");
	    }
	    else {
	      int firstFilePos = 0;
	      String encodingName = "UTF-8";
	      if (argv[0].equals("--encoding")) {
	        firstFilePos = 2;
	        encodingName = argv[1];
	        try {
	          java.nio.charset.Charset.forName(encodingName); // Side-effect: is encodingName valid? 
	        } catch (Exception e) {
	          System.out.println("Invalid encoding '" + encodingName + "'");
	          return;
	        }
	      }
	      for (int i = firstFilePos; i < argv.length; i++) {
	        Lexicojf scanner = null;
	        try {
	          java.io.FileInputStream stream = new java.io.FileInputStream(argv[i]);
	          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
	          scanner = new Lexicojf(reader);
	          while ( !scanner.zzAtEOF ) scanner.yylex();
	        }
	        catch (java.io.FileNotFoundException e) {
	          System.out.println("File not found : \""+argv[i]+"\"");
	        }
	        catch (java.io.IOException e) {
	          System.out.println("IO error scanning file \""+argv[i]+"\"");
	          System.out.println(e);
	        }
	        catch (Exception e) {
	          System.out.println("Unexpected exception:");
	          e.printStackTrace();
	        }
	        // DECLARACIÓN DE VARIABLES A UTILIZAR
	        Analizador aux = new Analizador();
	        List<String> auxmacro = aux.translateMacro(scanner.macrosList);
		    List<String> auxregex = aux.translateRegex(scanner.regexList);
		    aux.listaER = auxregex;
		    aux.listaM = auxmacro;
		    String out, outAux;
		    List<String> lParseada, lParseadaAux;
		    Analizador.NodoArbol arbol, arbolAux;
		    List<List<Analizador.Estado>> li;
		    // EMPEZAMOS A CREAR EL ÁRBOL DE TODAS LAS EXPRESIONES REGULARES QUE ENCONTREMOS
		    String fcad = auxregex.get(0);
		    outAux = aux.traducir(auxregex.get(0)); 
		    lParseadaAux = aux.parsear(outAux);
		    arbol = aux.crearArbol(lParseadaAux,0);
		    for (int x = 1; x < auxregex.size(); x++)
		    {
		    	outAux = aux.traducir(auxregex.get(x));
			    lParseadaAux = aux.parsear(outAux);
			    arbolAux = aux.crearArbol(lParseadaAux,x);
			    arbol = aux.unirArbol(arbol, arbolAux, "|");
		    	fcad += "|" + auxregex.get(x);
		    } 
	    	out = aux.traducir(fcad);
	    	System.out.println("ER traducida: " + out);
	    	lParseada = aux.parsear(out);
	    	System.out.print("ER parseada: ");
	        for (String n : lParseada)
	        	System.out.print(n + " ");
	    	arbol = aux.aumentar(arbol);
	    	aux.numerar(arbol, 0);
	        System.out.println();
	        System.out.println();
	        System.out.print("Arbol inOrden: ");
	        aux.inOrden(arbol);
	        System.out.println();
	        System.out.print("Arbol preOrden: ");
	        aux.preOrden(arbol);
	    	System.out.println();
	    	System.out.println();
	        li = aux.crearAutomata(arbol); // AUTOMATA CREADO
	        List<Analizador.Estado> lEstAux = new LinkedList<>();
	        List<Integer> nER = null;
	        int nEst = -1;
	        for (List<Analizador.Estado> l : li)
	        {
	        	nEst++;
	        	System.out.print(nEst + "  ");
	        	for (Analizador.Estado e : l)
	        	{
	        		if (e.n == 0)
	        			System.out.print("NNN  ");
	        		else
	        		{	
	        			if (!lEstAux.contains(e))
	        				lEstAux.add(e); // EN PRUEBA
	        			if (e.esinicial)
	        				System.out.print("I");
	        			else
	        				System.out.print("-");
	        			if (e.esfinal)
	        				System.out.print("F");
	        			else 
	        				System.out.print("-");
	        			System.out.print(e.n + "  ");
	        		}
	        	}
	        	int cc;
	        	for (Analizador.Estado estado : lEstAux) // EN PRUEBA
	        	{
	        		cc = -1;
	        		if (estado.n == nEst)
	        		{
	        			for (int a : estado.expRegs)
	        			{
	        				cc++;
	        				System.out.print(a + "-");
	        				System.out.print(estado.lTerm.get(cc) + " ");
	        			}
	        		}
	        	}
	        	System.out.println();
	        }
	        System.out.println();
	    	System.out.println();
		    	
	    	List<Integer> lnER = new LinkedList<>();
	    	List<Integer> lnE;
		    List<Analizador.Estado> lE = new LinkedList<>();
		    while (true)
		    {
		    	lnER.clear();
		    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    	String cad = reader.readLine();
		    	lE = aux.siguienteToken(cad, lE, li, arbol);
		    	/** Hay que llevar un conteo de los terminales que hemos leido, y a partir de ahí saber
		    	en qué expresión regular estamos **/
		    	lnE = aux.activarER(cad, lE, li, arbol);
		    	for (int nExp : lnE)
		    	{
		    		if (!lnER.contains(nExp))
	    			{
	    				lnER.add(nExp);
		    			// EN PRUEBA
		    			if (nExp >= auxmacro.size())
		    				System.out.println(auxregex.get(nExp));
		    			else
		    				System.out.println(auxmacro.get(nExp));
	    			}
		    	}
//		    	int cont;
//		    	for (Analizador.Estado e : lE)
//		    	{
//		    		cont = -1;
//		    		for (int nExp : e.expRegs)
//		    		{
//		    			cont++;
//		    			if (!lnER.contains(nExp))
//		    			{
//		    				lnER.add(nExp);
//			    			// EN PRUEBA
//			    			if (nExp >= auxmacro.size())
//			    				System.out.println(auxregex.get(nExp));
//			    			else
//			    				System.out.println(auxmacro.get(nExp));
//		    			}
//		    		}
//		    	}
		    	System.out.println();
		    }
	        /*List<String> lER = new LinkedList<>();
	        lER.add("([A-F]+ | er+)");
	        lER.add("{Auxiliar}");
	        List<String> lM = new LinkedList<>();
	        lM.add("Auxiliar");
	        lM.add("Macro");
	        aux.listaER = lER;
	        aux.listaM = lM;
	        
	        //String out = aux.traducir("([A5tg]+ (\"abd\")) ({Auxiliar}* [hola]?)");
	        String out1 = aux.traducir("(a | b) d*");
	        System.out.println("ER traducida: " + out1);
	        
	        List<String> salida = aux.parsear(out1);
	        System.out.print("ER parseada: ");
	        for (String n : salida)
	        	System.out.print(n + " ");
	        System.out.println();
	        Analizador.NodoArbol arbol;
	        arbol = aux.aumentar(aux.crearArbol(salida));
	        aux.numerar(arbol, 0);
	        System.out.println();
	        System.out.print("Arbol inOrden: ");
	        aux.inOrden(arbol);
	        System.out.println();
	        System.out.print("Arbol preOrden: ");
	        aux.preOrden(arbol);
	        
	        System.out.println();
	        System.out.println();
	        List<Analizador.Transicion> li = aux.crearAutomata(arbol);
	        for (Analizador.Transicion tr : li)
	        {
	        	tr.imprimir();
	        	System.out.println();
	        }
	        Analizador.Estado es = aux.comprobarEntrada("h",li,null);
	        if (es != null)
	        	es.imprimir();
	        else
	        	System.out.println("Sin transicion");*/
	       /* List<String> lExp = new LinkedList<>();
	        lExp.add("A");
	        lExp.add("+");
	        lExp.add("|");
	        lExp.add("B");
	        lExp.add("?");
	        aux.crearArbol(lExp);
	        System.out.println("hola");*/
	      }
	    }
	  }
}
