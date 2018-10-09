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
//	        List<String> auxmacro = aux.translateMacro(scanner.macrosList);
//		    List<String> auxregex = aux.translateRegex(scanner.regexList);
//		    List<String> l;
//		    for (String cadena : auxregex)
//		    {
//		    	l = aux.parsear(cadena, auxregex, auxmacro);
//		    	for (String a : l)
//		    	{
//		    		System.out.println(a);
//		    	}
//		    	System.out.println();
//		    }
	        List<String> lER = new LinkedList<>();
	        lER.add("0+ a34 oj?");
	        lER.add("[A5tg]+ {auxiliar}\"hola\"");
	        List<String> lM = new LinkedList<>();
	        lM.add("{Auxiliar}");
	        lM.add("{macro}");
	        Analizador.NodoArbol n =  aux.parsear("[0-9] [A-F]+ [er] [56]?",lER,lM);
	        System.out.println("hola");
	      }
	    }
	  }
}
