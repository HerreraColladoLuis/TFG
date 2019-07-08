import java.util.LinkedList;
import java.util.List;

/**
 * Clase Principal para manejar el analizador.
 * @author herre
 */
public class Procesador {
    
    static int tipo = -1;
    private static List<String> auxregex;
    private static List<String> auxmacro;
    private static List<Integer> lineas;
    private static List<Integer> ncaracP;
    private static List<Integer> ncaracD;
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
    @SuppressWarnings("null")
    public static List<String> procesarEsp(String argv) throws Exception {
        List<String> outList = new LinkedList<>();
        String encodingName = "UTF-8";
        boolean jf = false;
        boolean ar = false;
        Lexicojfn scannerjf = null;
        Lexicoalr scannerar = null;
        if (argv.charAt(argv.length()-1) == 'x') {
            jf = true;
            tipo = 1;
        }
        else {
            ar = true;
            tipo = 2;
        }
        try 
        {
            java.io.FileInputStream stream = new java.io.FileInputStream(argv);
            java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
            if (jf == true) {
                scannerjf = new Lexicojfn(reader);
                while ( !scannerjf.zzAtEOF ) scannerjf.yylex();
            }
            else {
                scannerar = new Lexicoalr(reader);
                while ( !scannerar.zzAtEOF ) scannerar.yylex();
            }
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
        List<String> auxmacro1;
        List<String> auxregex1;
        if (jf == true) {
            auxmacro1 = aux.translateMacro(scannerjf.macrosList);
            auxregex1 = aux.translateRegex(scannerjf.regexList);
            aux.lineas = scannerjf.lineas;
            aux.listaER = auxregex1;
            aux.listaM = auxmacro1;
            outList.addAll(auxmacro1);
        }
        else {
            auxmacro1 = scannerar.macrosList;
            auxregex1 = aux.translateRegex(scannerar.regexList);
            aux.lineas = scannerar.lineas;
            aux.listaER = auxregex1;
            aux.listaM = scannerar.macrosList;
            outList.addAll(scannerar.macrosList);
        }
        
        if (auxregex1.size() > auxmacro1.size())
        {
            for (int cont = auxmacro1.size();cont < auxregex1.size();cont++)
            {
                outList.add(auxregex1.get(cont));
            }
        }
        Procesador.auxregex = auxregex1;
        Procesador.auxmacro = auxmacro1;
        Procesador.lineas = aux.lineas;
        Procesador.ncaracP = aux.caracteresMacro(auxmacro1);
        Procesador.ncaracD = aux.caracteresRegex(auxregex1);
        return outList;
    }
    
    public static List<String> getER() {
        return auxregex;
    }
    
    public static List<String> getMacro() {
        return auxmacro;
    }
    
    public static List<Integer> getLineas() {
        return lineas;
    }
    
    public static List<Integer> getNcaracP() {
        return ncaracP;
    }
    
    public static List<Integer> getNcaracD() {
        return ncaracD;
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
        if (Procesador.tipo == 1)
            outAux = aux.traducir(auxregex.get(0)); 
        else
            outAux = aux.traducirAR(auxregex.get(0)); 
        lParseadaAux = aux.parsear(outAux);
        arbol = aux.crearArbol(lParseadaAux,0);
        for (int x = 1; x < auxregex.size(); x++)
        {
            if (Procesador.tipo == 1)
                outAux = aux.traducir(auxregex.get(x)); 
            else
                outAux = aux.traducirAR(auxregex.get(x)); 
            lParseadaAux = aux.parsear(outAux);
            arbolAux1 = aux.crearArbol(lParseadaAux,x);
            arbol = aux.unirArbol(arbol, arbolAux1, "|");
            fcad += "|" + auxregex.get(x);
        } 
        if (Procesador.tipo == 1)
            out = aux.traducir(fcad);
        else
            out = aux.traducirAR(fcad);
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
