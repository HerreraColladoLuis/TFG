import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Procesador {
    
    private static List<String> auxregex;
    private static List<String> auxmacro;
    private static List<List<Analizador.Estado>> automata;
    private static Analizador.NodoArbol arbolAux;
    
    /**
     * Método para procesar una especificación. Devuelve una lista de las 
     * expresiones regulares encontradas.
     * @param argv path de la especificación
     * @return lista de ER
     * @throws Exception 
     */
    public static List<String> procesarEsp(String argv) throws Exception {
        List<String> outList = new LinkedList<>();
        String encodingName = "UTF-8";
        Lexicojf scanner = null;
        try {
          java.io.FileInputStream stream = new java.io.FileInputStream(argv);
          java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
          scanner = new Lexicojf(reader);
          while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
        }
        // DECLARACI�N DE VARIABLES A UTILIZAR
        Analizador aux = new Analizador();
        List<String> auxmacro = aux.translateMacro(scanner.macrosList);
        List<String> auxregex = aux.translateRegex(scanner.regexList);
        aux.listaER = auxregex;
        aux.listaM = auxmacro;
        outList.addAll(auxmacro);
        //outList.addAll(auxregex);
        Procesador.auxregex = auxregex;
        Procesador.auxmacro = auxmacro;
        return outList;
    }
    /**
     * Método para crear un autómata a partir de la información dada por las
     * expresiones regulares recogidas antes.
     * @throws Exception 
     */
    public static void crearAutomata() throws Exception
    {
        Analizador aux = new Analizador();
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
        Procesador.arbolAux = arbol;
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
        Procesador.automata = li;
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
    }
    
    public static List<Analizador.Estado> actualizarLE(List<Analizador.Estado> lE, String tok)
    {
        Analizador aux = new Analizador();
        aux.listaER = auxregex;
        aux.listaM = auxmacro;
        List<Analizador.Estado> lEAux;
        
        lEAux = aux.siguienteToken(tok, lE, Procesador.automata, Procesador.arbolAux);
        
        return lEAux;
    }
    
    public static List<Integer> reconocer(List<Analizador.Estado> lE, List<Integer> lnER, String tok)
    {
        Analizador aux = new Analizador();
        aux.listaER = auxregex;
        aux.listaM = auxmacro;
        List<Integer> lnE;
        List<Analizador.Estado> lEAux;
        
        lEAux = aux.siguienteToken(tok, lE, Procesador.automata, Procesador.arbolAux);
        /** Hay que llevar un conteo de los terminales que hemos leido, y a partir de ahí saber
        en qué expresión regular estamos **/
        lnE = aux.activarER(tok, lEAux, Procesador.automata, lnER, Procesador.arbolAux);
        return lnE;
//        lnER.clear();
//        for (int nExp : lnE)
//        {
//            if (!lnER.contains(nExp))
//            {
//                lnER.add(nExp);
//                // EN PRUEBA
//                if (nExp >= auxmacro.size())
//                    System.out.println(auxregex.get(nExp));
//                else
//                    System.out.println(auxmacro.get(nExp));
//            }
//        }
//        System.out.println();
    }
}