import java.util.LinkedList;
import java.util.List;

/**
 * Clase Principal para manejar el analizador.
 * @author herre
 */
public class Procesador {
    
    private static List<String> auxregex;
    private static List<String> auxmacro;
    private static List<List<Analizador.Estado>> automata;
    private static Analizador.NodoArbol arbolAux;
    private static List<List<Analizador.Estado>> estadoEntrada = new LinkedList<>();
    
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
        try 
        {
            java.io.FileInputStream stream = new java.io.FileInputStream(argv);
            java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
            scanner = new Lexicojf(reader);
            while ( !scanner.zzAtEOF ) scanner.yylex();
        }
        catch (java.io.FileNotFoundException e) 
        {
            System.out.println("File not found : \""+argv+"\"");
        }
        catch (java.io.IOException e) 
        {
            System.out.println("IO error scanning file \""+argv+"\"");
            System.out.println(e);
        }
        catch (Exception e) 
        {
            System.out.println("Unexpected exception:");
        }
        // DECLARACI�N DE VARIABLES A UTILIZAR
        Analizador aux = new Analizador();
        @SuppressWarnings("null")
        List<String> auxmacro1 = aux.translateMacro(scanner.macrosList);
        List<String> auxregex1 = aux.translateRegex(scanner.regexList);
        aux.listaER = auxregex1;
        aux.listaM = auxmacro1;
        outList.addAll(auxmacro1);
        if (auxregex1.size() > auxmacro1.size())
        {
            outList.add("-----------------------------------------------"); // Diferenciar Macro de ER
            for (int cont = auxmacro1.size();cont < auxregex1.size();cont++)
            {
                outList.add(auxregex1.get(cont));
            }
        }
        Procesador.auxregex = auxregex1;
        Procesador.auxmacro = auxmacro1;
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
        Analizador.NodoArbol arbol, arbolAux1;
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
            arbolAux1 = aux.crearArbol(lParseadaAux,x);
            arbol = aux.unirArbol(arbol, arbolAux1, "|");
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
    /**
     * Método para actualizar la lista de estados dada una entrada y la lista
     * de estados.
     * @param lE lista de estados
     * @param tok entrada leida
     * @return lista de estados actualizada
     */
    public static List<Analizador.Estado> actualizarLE(List<Analizador.Estado> lE, String tok)
    {
        Analizador aux = new Analizador();
        aux.listaER = auxregex;
        aux.listaM = auxmacro;
        List<Analizador.Estado> lEAux;
        
        lEAux = aux.siguienteToken(tok, lE, Procesador.automata, Procesador.arbolAux);
        estadoEntrada.add(lEAux);
        
        return lEAux;
    }
    /**
     * Método que dada una entrada, una lista de estados y una lista de ER 
     * reconocidas, devuelve una lista de ER que se reconocen.
     * @param lE lista de estados
     * @param lnER lista de ER
     * @param tok entrada leida
     * @return lista de ER nueva
     * @throws Exception 
     */
    public static List<Integer> reconocer(List<Analizador.Estado> lE, List<Integer> lnER, String tok) throws Exception
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
    }
    /**
     * Método que devuelve si un estado es final o no.
     * @param n índice del estado
     * @return true si es final
     */
    public static boolean esEstadoFinal(int n)
    {
        for (List<Analizador.Estado> l : Procesador.automata)
        {
            for (Analizador.Estado e : l)
            {
                if (e.n == n)
                    return e.esfinal;
            }
        }
        return false;
    }
}
