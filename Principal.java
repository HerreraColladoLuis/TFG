import java.util.LinkedList;
import java.util.List;

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
	        Analizador aux = new Analizador();
/*	        List<String> auxmacro = aux.translateMacro(scanner.macrosList);
		    List<String> auxregex = aux.translateRegex(scanner.regexList);
		    aux.listaER = auxregex;
		    aux.listaM = auxmacro;
		    String out;
		    for (String cadena : auxregex)
		    {
		    	out = aux.traducir(cadena);
		    	System.out.println(out);
		    }*/
	        List<String> lER = new LinkedList<>();
	        lER.add("[A-F]+ | er+");
	        lER.add("{auxiliar}");
	        List<String> lM = new LinkedList<>();
	        lM.add("Auxiliar");
	        lM.add("macro");
	        aux.listaER = lER;
	        aux.listaM = lM;
	        String out = aux.traducir("([A5tg]+ | (\"abd\"))? | ({Auxiliar}* [hola]?)");
	        String out1 = aux.traducir(""); 
	        System.out.println(out);
	        List<String> salida = aux.parsear(out);
	        for (String n : salida)
	        	System.out.println(n);
	        aux.crearArbol(salida);
	        System.out.println("hola");
	        /*List<String> lExp = new LinkedList<>();
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
