import java.util.LinkedList;
import java.util.List;
/**
 * Clase central de la aplicación, donde introduciremos toda la lógica
 * @author herre
 *
 */
public class Analizador 
{
    List<String> listaM;
    List<String> listaER;
    List<Integer> lineas;
    /**
       * Método que recibe una lista con strings correspondientes a macros 
       * y parsea cada uno traduciendolos a un estado común
       * @param lista con las macros
       * @return lista con las macros traducidas a un estado com�n
       */
      public List<String> translateMacro(List<String> lista)
      {
        List<String> out = new LinkedList<>();
        String aux;
        for (String cad : lista)
        {
            aux = procesarMacro(cad);
            if (!"".equals(aux))
                out.add(aux);
        }
        return out;
      }
      
      public List<Integer> caracteresMacro(List<String> lmacros) {
          List<Integer> salida = new LinkedList<>();
          int cont = 0;
          for (String macro : lmacros) {
              cont = cont + macro.length();
              salida.add(cont);
              cont++;
          }
          return salida;
      }
      /**
       * Método que recibe una lista con strings correspondientes a regex 
       * y parsea cada una traduciendolos a un estado común
       * @param lista con las regex
       * @return lista con las regex traducidas a un estado común
       */
      public List<String> translateRegex(List<String> lista)
      {
        List<String> out = new LinkedList<>();
        String aux;
        for (String cad : lista)
        {
            aux = procesarRegex(cad);
            if (!"".equals(aux))
                out.add(aux);
        }
        return out;
      }
      
      public List<Integer> caracteresRegex(List<String> lregex) {
          List<Integer> salida = new LinkedList<>();
          int cont = 0;
          int aux = -1;
          for (String reg : lregex) {
              aux++;
              if (aux < this.listaM.size())
                  continue;
              cont = cont + reg.length();
              salida.add(cont);
              cont++;
          }
          return salida;
      }
    /**
     * Algoritmo para parsear un string que contiene una 
     * expresion regular
     * @param re expresion regular
     * @return expresion regular parseada
     */
    private String procesarRegex(String re)
    {
        int le = re.length();
        int i,j,c;
        char[] rech = re.toCharArray();
        char[] auxch;
        String out;
        if (rech[le-1] == '{')
        {
            for (i = le-2; i > 0; i--)
            {
                if (rech[i] != ' ')
                    break;
            }
            auxch = new char[i+1];
            for (j = 0; j < i+1; j++)
                auxch[j] = rech[j];
            out = String.valueOf(auxch);
        }
        else
        {
            i = le-1;
            while (rech[i] == ' ')
                i--;
            auxch = new char[i];
            c = 0;
            for (j = 1; j < i+1; j++)
            {
                auxch[c] = rech[j];
                c++;
            }
            out = String.valueOf(auxch);
        }
        return out;
    }
    /**
     * Algoritmo para parsear un string que contiene una macro
     * @param re macro
     * @return macro parseada
     */
    private String procesarMacro(String re)
    {
        int i = 0;
        int c = 0;
        char[] rech = re.toCharArray();
        while (rech[i] != ' ')
        {
            c++;
            i++;
        }
        char[] auxch = new char[c];
        String out;
        for (i = 0; i < c; i++)
        {
            if (rech[i] == ' ')
                break;
            auxch[i] = rech[i];
        }
        out = String.valueOf(auxch);
        return out;
    }
    /**
     * Método para traducir una expresión regular a un formato
     * que pueda reconocer el algoritmo final
     * @param exp string de la expresión
     * @return string de la expresión traducido
     * @throws Exception 
     */
    public String traducir(String exp) throws Exception
    {
        String cadAux;
        String aux1;
        String cadena = "";
        boolean n = false;
        char[] rech = exp.toCharArray();
        int i = 0;
        int j;
        // Vamos a recorrer el string caracter a caracter
        while (i < exp.length()) 
        {	
            // Entramos en el caso en el que se abre un set
            if (rech[i] == '[') 
            {
                cadena += "\"";
                if (rech[i+1] == '^') // Comprobamos si está negado
                {
                    cadena+= rech[i+1]; // aux += ^
                    i = i+1; // Apuntamos a la negaci�n
                    if (rech[i+1] == ']')
                        n = true;
                }
                i = i+1; // Apuntamos al primer caracter del set
                while (true)
                {
                    // Comprobamos si es una letra, may�scula o min�scula, o un n�mero
                    if (((int) rech[i] > 64 && (int) rech[i] < 91) || ((int) rech[i] > 96 && (int) rech[i] < 123) || ((int) rech[i] > 47 && (int) rech[i] < 58))
                    {
                        // Caso correspondiente a [a-z] o [0-9] (por ejemplo)
                        if (rech[i+1] == '-')
                        {
                            for (j = (int) rech[i]; j <= (int) rech[i+2]; j++)
                                cadena += (char) j;
                            i = i+3; // Apuntamos al siguiente caracter de la secuencia
                        }
                        else // Si no es una secuencia, guardamos el n�mero o la letra
                        {
                            cadena += rech[i];
                            i = i+1; // Apuntamos al siguiente caracter de la secuencia
                        }
                    }
                    // Comprobamos si es un caracter de escape \
                    else if ((int) rech[i] == 10 || (int) rech[i] == 9 || (int) rech[i] == 13 || (int) rech[i] == 13 ||
                            (int) rech[i] == 12 || (int) rech[i] == 8 || (int) rech[i] == 92 || (int) rech[i] == 39 ||
                            (int) rech[i] == 34)
                    {
                        cadena += rech[i]; // Guardamos el caracter de escape
                        i = i+1;
                    }
                    // Comprobamos si es el final del set
                    else if (rech[i] == ']') // Acaba el set
                        break; // Salimos del while
                    // Cualquier otro s�mbolo
                    else
                    {
                        cadena += rech[i]; // Guardamos el s�mbolo
                        i = i+1;
                    }	
                }
                cadena += "^##\""; // A�adir algo para saber que es un set ^##
                if (n)
                {
                    n = false;
                    cadena += "*";
                }
            }
            // Entramos en el caso en el que se abre una macro
            else if (rech[i] == '{')
            {
                aux1 = "";
                j = 0;
                i = i+1;
                while (rech[i] != '}')
                {
                    aux1 += rech[i]; // Guardamos en aux1 la macro 
                    i = i+1;
                }
                // Comprobamos que la lista no esté vacia o sea nula
                if (listaM != null && !listaM.isEmpty()) 
                {
                    for (String comp : listaM) // Recorremos la lista buscando la posici�n de la macro dada
                    {
                        if (comp.equalsIgnoreCase(aux1)) // Cuando la encontramos, salimos
                            break;
                        j = j+1; // En esta variable guardamos la posici�n
                    }
                }
                else 
                    throw new Exception("Lista de Macros vacía o nula"); // Devolvemos una excepci�n 
                // Comprobamos que la lista no est� vacia o sea nula
                if (listaER != null && !listaER.isEmpty()) 
                {
                    cadAux = traducir(listaER.get(j)); // Hacemos recursividad para parsear una macro
                }
                else
                    throw new Exception("Lista de ER vacía o nula"); // Devolvemos una excepci�n
                // A�adimos el arbol de la macro como hijo del arbol act�al
                if (cadAux == null)
                    throw new Exception("Cadena auxiliar nula"); // Devolvemos una excepci�n
                else
                {
                    cadena += "(" + cadAux + ")";
                }
            }
            // Entramos en el caso en el que se abre un par�ntesis
            else if (rech[i] == '(')
            {
                cadena += "(";
            }
            else if (rech[i] == ')')
            {
                cadena += ")";
            }
            // Se encuentra un OR
            else if (rech[i] == '|')
            {
                cadena += "|";
            }
            // Se encuentra un "
            else if (rech[i] == '\"')
            {
                i = i+1;
                while (true)
                {
                        if (rech[i] == '\"')
                                break;
                        cadena += "\"" + rech[i] + "\"";
                        i = i+1;
                }
            }
            else if (rech[i] == ' ')
            {
                // Nada
            }
            else if (rech[i] == '+' || rech[i] == '*' || rech[i] == '?')
            {
                cadena += (String.valueOf(rech[i]));
            }
            else if (rech[i] == '.')
            {
                cadena += "\"" + rech[i] + "\"" + "*";
            }
            // Cualquier otro caracter
            else
            {
                //cadena += "\"";
                while (true)
                {
                    if (i == rech.length || rech[i] == '+' || rech[i] == '*' || rech[i] == '?' || rech[i] == ' ' || rech[i] == ')' || rech[i] == '|' || rech[i] == '(')
                    {
                        i = i-1;
                        break;
                    }
                    if ((int) rech[i] == 10 || (int) rech[i] == 9 || (int) rech[i] == 13 || (int) rech[i] == 13 ||
                        (int) rech[i] == 12 || (int) rech[i] == 8 || (int) rech[i] == 92 || (int) rech[i] == 39 ||
                        (int) rech[i] == 34)
                    {
                        cadena += "\"" + rech[i] + rech[i+1] + "\""; // Guardamos el caracter de escape
                        i = i + 1;
                    }
                    else
                        cadena += "\"" + rech[i] + "\"";
                    i = i+1;
                }
                //cadena += "\"";
            }
            i = i+1;
        }
        return cadena;
    }
    
    public String traducirAR(String exp) throws Exception {
        String cadAux;
        String aux1;
        String cadena = "";
        boolean n = false;
        char[] rech = exp.toCharArray();
        int i = 0;
        int j;
        // Vamos a recorrer el string caracter a caracter
        while (i < exp.length()) 
        {	
            // Entramos en el caso en el que se abre un set
            if (rech[i] == '[') 
            {
                cadena += "\"";
                if (rech[i+1] == '^') // Comprobamos si está negado
                {
                    cadena+= rech[i+1]; // aux += ^
                    i = i+1; // Apuntamos a la negaci�n
                    if (rech[i+1] == ']')
                        n = true;
                }
                i = i+1; // Apuntamos al primer caracter del set
                while (true)
                {
                    // Comprobamos si es una letra, may�scula o min�scula, o un n�mero
                    if (((int) rech[i] > 64 && (int) rech[i] < 91) || ((int) rech[i] > 96 && (int) rech[i] < 123) || ((int) rech[i] > 47 && (int) rech[i] < 58))
                    {
                        // Caso correspondiente a [a-z] o [0-9] (por ejemplo)
                        if (rech[i+1] == '-')
                        {
                            for (j = (int) rech[i]; j <= (int) rech[i+2]; j++)
                                cadena += (char) j;
                            i = i+3; // Apuntamos al siguiente caracter de la secuencia
                        }
                        else // Si no es una secuencia, guardamos el n�mero o la letra
                        {
                            cadena += rech[i];
                            i = i+1; // Apuntamos al siguiente caracter de la secuencia
                        }
                    }
                    // Comprobamos si es un caracter de escape \
                    else if ((int) rech[i] == 10 || (int) rech[i] == 9 || (int) rech[i] == 13 || (int) rech[i] == 13 ||
                            (int) rech[i] == 12 || (int) rech[i] == 8 || (int) rech[i] == 92 || (int) rech[i] == 39 ||
                            (int) rech[i] == 34)
                    {
                        cadena += rech[i]; // Guardamos el caracter de escape
                        i = i+1;
                    }
                    // Comprobamos si es el final del set
                    else if (rech[i] == ']') // Acaba el set
                        break; // Salimos del while
                    // Cualquier otro s�mbolo
                    else
                    {
                        cadena += rech[i]; // Guardamos el s�mbolo
                        i = i+1;
                    }	
                }
                cadena += "^##\""; // A�adir algo para saber que es un set ^##
                if (n)
                {
                    n = false;
                    cadena += "*";
                }
            }
            // Entramos en el caso en el que se abre un par�ntesis
            else if (rech[i] == '(')
            {
                cadena += "(";
            }
            else if (rech[i] == ')')
            {
                cadena += ")";
            }
            // Se encuentra un OR
            else if (rech[i] == '|')
            {
                cadena += "|";
            }
            // Se encuentra un "
            else if (rech[i] == '\'')
            {
                i = i+1;
                while (true)
                {
                    if (rech[i] == '\'')
                        break;
                    if ((int) rech[i] == 10 || (int) rech[i] == 9 || (int) rech[i] == 13 || (int) rech[i] == 13 ||
                        (int) rech[i] == 12 || (int) rech[i] == 8 || (int) rech[i] == 92 || (int) rech[i] == 39 ||
                        (int) rech[i] == 34)
                    {
                        cadena += "\"" + rech[i] + rech[i+1] + "\""; // Guardamos el caracter de escape
                        i = i + 1;
                    }
                    else
                        cadena += "\"" + rech[i] + "\"";
                    i = i+1;
                }
            }
            else if (rech[i] == ' ')
            {
                // Nada
            }
            else if (rech[i] == '+' || rech[i] == '*' || rech[i] == '?')
            {
                cadena += (String.valueOf(rech[i]));
            }
            // Cualquier otro caracter (MACRO)
            else if (((int) rech[i] > 96 && (int) rech[i] < 123) || ((int) rech[i] > 64 && (int) rech[i] < 91))
            {
                aux1 = "";
                j = 0;
                while (i < rech.length && rech[i] != ' ' && rech[i] != '+' && rech[i] != '*' && rech[i] != '?' && rech[i] != ')' && rech[i] != '(' && rech[i] != '|')
                {
                    aux1 += rech[i]; // Guardamos en aux1 la macro 
                    i = i+1;
                }
                if (i < rech.length)
                    i--;
                // Comprobamos que la lista no esté vacia o sea nula
                if (listaM != null && !listaM.isEmpty()) 
                {
                    for (String comp : listaM) // Recorremos la lista buscando la posici�n de la macro dada
                    {
                        if (comp.equalsIgnoreCase(aux1)) // Cuando la encontramos, salimos
                            break;
                        j = j+1; // En esta variable guardamos la posici�n
                    }
                }
                else 
                    throw new Exception("Lista de Macros vacía o nula"); // Devolvemos una excepci�n 
                // Comprobamos que la lista no est� vacia o sea nula
                if (listaER != null && !listaER.isEmpty()) 
                {
                    cadAux = traducirAR(listaER.get(j)); // Hacemos recursividad para parsear una macro
                }
                else
                    throw new Exception("Lista de ER vacía o nula"); // Devolvemos una excepci�n
                // A�adimos el arbol de la macro como hijo del arbol act�al
                if (cadAux == null)
                    throw new Exception("Cadena auxiliar nula"); // Devolvemos una excepci�n
                else
                {
                    cadena += "(" + cadAux + ")";
                }
            }
            // Cualquier otro caracter
            else
            {
                //cadena += "\"";
                while (true)
                {
                    if (i == rech.length || rech[i] == '+' || rech[i] == '*' || rech[i] == '?' || rech[i] == ' ' || rech[i] == ')' || rech[i] == '|' || rech[i] == '(')
                    {
                        i = i-1;
                        break;
                    }
                    if ((int) rech[i] == 10 || (int) rech[i] == 9 || (int) rech[i] == 13 || (int) rech[i] == 13 ||
                        (int) rech[i] == 12 || (int) rech[i] == 8 || (int) rech[i] == 92 || (int) rech[i] == 39 ||
                        (int) rech[i] == 34)
                    {
                        cadena += "\"" + rech[i] + rech[i+1] + "\""; // Guardamos el caracter de escape
                        i = i + 1;
                    }
                    else
                        cadena += "\"" + rech[i] + "\"";
                    i = i+1;
                }
                //cadena += "\"";
            }
            i = i+1;
        }
        return cadena;
    }
    /**
     * Método que devuelve si una Expresión Regular está parseada,
     * es decir, si no tiene paréntesis inicial y final.
     * @param exp Expresión Regular
     * @return True si está parseada, false en caso contrario
     */
    private boolean parseado(String exp)
    {
        char[] rech = exp.toCharArray();
        if (rech[0] != '(' || rech[exp.length()-1] != ')')
                return true;
        int i = 1;
        int pA = 0;
        int pC = 0;
        boolean com = false;
        while (i < exp.length()-1)
        {
            if (rech[i] == '\"' && !com)
            {
                com = true;
            }
            if (rech[i] == '\"' && com)
            {
                com = false;
            }
            if (rech[i] == '(' && !com)
            {
                pA++;
            }
            if (rech[i] == ')' && !com)
            {
                pC++;
                if (pC > pA)
                    return true;
            }
            i++;
        }
        return false;
    }
    /**
     * Método que recibe una expresión regular en forma de string
     * y devuelve una lista con sus componentes 
     * parseada. Es decir, los componentes que se encuentre sin 
     * paréntesis, los añade a la lista, y si se encuentra una 
     * subexpresión dentro de paréntesis, quita éstos y añade la 
     * subexpresión como un elemento más de la lista.
     * @param exp Expresión regular en forma de string
     * @return Lista de componentes 
     * @throws Exception 
     */
    public List<String> parsear(String exp) throws Exception 
    {
        List<String> lComp = new LinkedList<>();
        char[] rech = exp.toCharArray();
        String nuevo;
        int i = 0;
        int anidado;
        boolean com = false;
        while (i < exp.length())
        {		
            if (rech[i] == '\"')
            {
                nuevo = "\"";
                i = i+1;
                while (true)
                {
                    nuevo += rech[i];
                    if (rech[i] == '\"')
                        break;
                    i = i+1;
                }
                lComp.add(nuevo);
            }
            else if (rech[i] == '+' || rech[i] == '*' || rech[i] == '?')
            {
                nuevo = "";
                nuevo += rech[i];
                lComp.add(nuevo);
            }
            else if (rech[i] == '(')
            {
                anidado = 0;
                nuevo = "";
                i = i+1;
                while (true)
                {
                    if (rech[i] == '\"' && !com)
                    {
                        com = true;
                    }
                    else if (rech[i] == '\"' && com)
                    {
                        com = false;
                    }
                    else if (rech[i] == '(' && !com)
                    {
                        anidado = anidado+1;
                    }
                    else if (rech[i] == ')' && !com && anidado == 0)
                    {
                        break;
                    }
                    else if (rech[i] == ')' && !com && anidado > 0)
                    {
                        anidado = anidado-1;
                    }
                    nuevo += rech[i];
                    i = i+1;
                }
                lComp.add(nuevo);
            }
            else if (rech[i] == '|')
            {
                nuevo = "";
                nuevo += rech[i];
                lComp.add(nuevo);
            }
            i = i+1;
        }
        return lComp;
    }
    /**
     * Método para recorrer un árbol binario en pre orden
     * @param nodo Nodo del árbol del que empezaremos a recorrer
     */
    public void preOrden(NodoArbol nodo)
    {
        if (nodo != null)
        {
            System.out.print(nodo.info);
            preOrden(nodo.hijoIzdo);
            preOrden(nodo.hijoDcho);
        }
    }
    /**
     * M�todo para recorrer un �rbol binario en in orden
     * @param nodo Nodo del arbol del que empezaremos a recorrer
     */
    public void inOrden(NodoArbol nodo)
    {
        if (nodo != null)
        {
            inOrden(nodo.hijoIzdo);
            System.out.print(nodo.info);
            inOrden(nodo.hijoDcho);
        }
    }
    /**
     * M�todo para aumentar un �rbol de una expresi�n regular
     * @param nodo Ra�z del �rbol a aumentar
     * @return �rbol aumentado
     */
    public NodoArbol aumentar(NodoArbol nodo)
    {
        NodoArbol nodoAuxiliar = new NodoArbol(".");
        nodoAuxiliar.insertarIzda(nodo);
        nodoAuxiliar.insertarDcha(new NodoArbol("#"));
        return nodoAuxiliar;
    }
    /**
     * Método para numerar las hojas de un arbol sintáctico
     * en el orden de aparici�n en el mismo
     * @param nodo Nodo inicial del arbol
     * @param pos contador 
     * @return �ltima posici�n asignada
     */
    public int numerar(NodoArbol nodo, int pos)
    {
        int p = pos;
        if (nodo != null)
        {
            if (nodo.esHoja())
            {
                nodo.posicion = pos+1;
                return p+1;
            }
            else
            {
                p = numerar(nodo.hijoIzdo,p);
                p = numerar(nodo.hijoDcho,p);
            }
        }
        return p;
    }
    /**
     * Método anulable que devuelve verdadero o falso
     * @param nodo Nodo a analizar
     * @return verdadero o falso
     */
    public boolean anulable(NodoArbol nodo)
    {
        if (nodo.esHoja())
            return false;
        else if (nodo.info.equals("."))
            return (anulable(nodo.hijoIzdo) && anulable(nodo.hijoDcho));
        else if (nodo.info.equals("|"))
            return (anulable(nodo.hijoIzdo) || anulable(nodo.hijoDcho));
        else if (nodo.info.equals("*"))
            return true;
        else if (nodo.info.equals("+"))
            return anulable(nodo.hijoIzdo);
        else // nodo.info.equals("?")
            return true;
    }
    /**
     * Método primeraPos que devuelve una lista de posiciones
     * @param nodo Nodo a analizar
     * @return Lista de posiciones
     */
    public List<Integer> primeraPos(NodoArbol nodo)
    {
        List<Integer> out = new LinkedList<>();
        if (nodo.esHoja())
        {
            out.add(nodo.posicion);
        }	
        else if (nodo.info.equals("."))
        {
            if (anulable(nodo.hijoIzdo))
            {
                out.addAll(primeraPos(nodo.hijoIzdo));
                out.addAll(primeraPos(nodo.hijoDcho));
            }
            else
            {
                out.addAll(primeraPos(nodo.hijoIzdo));
            }
        }
        else if (nodo.info.equals("|"))
        {
            out.addAll(primeraPos(nodo.hijoIzdo));
            out.addAll(primeraPos(nodo.hijoDcho));
        }
        else
        {
            out.addAll(primeraPos(nodo.hijoIzdo));
        }
        return out;
    }
    /**
     * Método ultimaPos que devuelve una lista de posiciones
     * @param nodo Nodo a analizar
     * @return Lista de posiciones
     */
    public List<Integer> ultimaPos(NodoArbol nodo)
    {
        NodoArbol aux = nodo.padre;
        List<Integer> out = new LinkedList<>();
        if (nodo.esHoja())
        {
            out.add(nodo.posicion);
        }	
        else if (nodo.info.equals("."))
        {
            if (anulable(nodo.hijoDcho))
            {	
                out.addAll(ultimaPos(nodo.hijoDcho));
                out.addAll(ultimaPos(nodo.hijoIzdo));
            }
            else
            {
                out.addAll(ultimaPos(nodo.hijoDcho));
            }
        }
        else if (nodo.info.equals("|"))
        {	
            out.addAll(ultimaPos(nodo.hijoDcho));
            out.addAll(ultimaPos(nodo.hijoIzdo));
        }
        else if (nodo.info.equals("?")) // CAMBIO AQU� RESPECTO DOCUMENTACION
        {
            out.addAll(ultimaPos(nodo.hijoIzdo));
            if (aux.hijoIzdo.equals(nodo))
            {
                while (!aux.info.equals("."))
                {
                    aux = aux.padre;
                }
                //out.addAll(ultimaPos(aux.hijoDcho));
            }
        }
        else
        {
            out.addAll(ultimaPos(nodo.hijoIzdo));
        }
        return out;
    }
    /**
     * Método siguientePos que se calcula sobre los nodos hoja
     * @param nodo Nodo desde el que recorreremos el árbol
     * @param pos Posición de la hoja a analizar
     * @return Lista de posiciones
     */
    public List<Integer> siguientePos(NodoArbol nodo, int pos)
    {
        List<Integer> out = new LinkedList<>();
        if (nodo != null)
        {
            if (nodo.info.equals("."))
            {
                for (int p : ultimaPos(nodo.hijoIzdo))
                {
                    if (pos == p)
                    {
                        out.addAll(primeraPos(nodo.hijoDcho));
                        break;
                    }
                }
            }
            else if (nodo.info.equals("*") || nodo.info.equals("+")) // CAMBIO AQU� RESPECTO DOCUMENTACION
            {
                for (int p : ultimaPos(nodo))
                {
                    if (pos == p)
                    {
                        out.addAll(primeraPos(nodo));
                    }
                }
            }
            out.addAll(siguientePos(nodo.hijoIzdo,pos));
            out.addAll(siguientePos(nodo.hijoDcho,pos));
        }
        return out;
    }
    /**
     * Clase auxiliar para un �rbol binario
     * @author herre
     *
     */
    public class NodoArbol
    {
        public String info;
        public NodoArbol hijoDcho;
        public NodoArbol hijoIzdo;
        public NodoArbol padre;
        public int posicion;
        public boolean cuantificador;
        public boolean operacion;
        public boolean hoja;
        public int expReg = -1;

        /**
         * Constructor para un nodo del �rbol
         * @param info Informaci�n que llevar� el nodo
         */
        @SuppressWarnings("ConvertToStringSwitch")
        public NodoArbol(String info) 
        {
            super();
            this.info = info;
            if (info.equals("+") || info.equals("*") || info.equals("?"))
                    this.cuantificador = true;
            else if (info.equals("|") || info.equals("."))
                    this.operacion = true;
            else
                    this.hoja = true;
            this.hijoDcho = null;
            this.hijoIzdo = null;
            this.padre = null;
        }
        /**
         * M�todo que devuelve si un nodo es una hoja
         * @return verdadero si es una hoja
         */
        public boolean esHoja()
        {
            return (this.hijoIzdo == null && this.hijoDcho == null);
        }
        /**
         * M�todo para insertar un nodo como hijo derecho de otro nodo padre
         * @param nodo Nodo que insertaremos como hijo derecho 
         * @return Nodo hijo derecho
         */
        public NodoArbol insertarDcha(NodoArbol nodo) 
        {
            NodoArbol nodoAuxiliar;
            nodoAuxiliar = this;
            while (nodoAuxiliar.hijoDcho != null)
                nodoAuxiliar = nodoAuxiliar.hijoDcho;
            nodoAuxiliar.hijoDcho = nodo;
            nodoAuxiliar.hijoDcho.padre = this;
            return nodoAuxiliar.hijoDcho;
        }
        /**
         * M�todo para insertar un nodo como hijo izquierdo de otro nodo padre
         * @param nodo Nodo que insertaremos como hijo izquierdo
         * @return Nodo hijo izquierdo
         */
        public NodoArbol insertarIzda(NodoArbol nodo) 
        {
            NodoArbol nodoAuxiliar;
            nodoAuxiliar = this;
            while (nodoAuxiliar.hijoIzdo != null)
                nodoAuxiliar = nodoAuxiliar.hijoIzdo;
            nodoAuxiliar.hijoIzdo = nodo;
            nodoAuxiliar.hijoIzdo.padre = this;
            return nodoAuxiliar.hijoIzdo;
        }
    }
    /**
     * M�todo que devuelve, en forma de string, la informaci�n
     * de un nodo hoja determinado por su posici�n.
     * @param nodo Arbol a recorrer
     * @param posicion Posici�n del nodo hoja a evaluar
     * @return Informaci�n del nodo hoja en forma de string
     */
    String devolverTerminal(NodoArbol nodo, int posicion)
    {
        String res = "";
        if (nodo != null)
        {
            if (nodo.posicion == posicion)
            {
                res = nodo.info;
            }
            else
            {
                res += devolverTerminal(nodo.hijoIzdo,posicion);
                res += devolverTerminal(nodo.hijoDcho,posicion);
            }
        }
        return res;
    }
    /**
     * M�todo que devuelve la posici�n de la expresi�n regular que
     * tiene un nodo en su informaci�n.
     * @param nodo �rbol a recorrer
     * @param posicion Posici�n del nodo hoja a recorrer
     * @return Posici�n en la lista de ER
     */
    int devolverNER(NodoArbol nodo, int posicion)
    {
        int i = 0;
        if (nodo != null)
        {
            if (nodo.posicion == posicion)
            {
                i = nodo.expReg;
            }
            else
            {
                i += devolverNER(nodo.hijoIzdo,posicion);
                i += devolverNER(nodo.hijoDcho,posicion);
            }
        }
        return i;
    }
    /**
     * Método que devuelve una lista de terminales correspondientes a una expresión regular
     * dada por su índice.
     * @param nodo
     * @param lis
     * @param expReg
     * @return
     */
    List<String> devolverPos(NodoArbol nodo, List<String> lis, int expReg)
    {
        if (nodo != null)
        {
            if (nodo.expReg == expReg)
            {
                lis.add(nodo.info);
            }
            else
            {
                lis = devolverPos(nodo.hijoIzdo,lis,expReg);
                lis = devolverPos(nodo.hijoDcho,lis,expReg);
            }
        }
        return lis;
    }
    /**
     * Método que devuelve si una cadena es un conjunto. Es decir,
     * "abcdef" ser�a un conjunto determinado por a-f.
     * @param cadena Cadena a evaluar
     * @return Verdadero si es un conjunto
     */
    @SuppressWarnings("UnnecessaryContinue")
    boolean esConjunto(String cadena)
    {
        char ant = ' ';
        int i = -1;
        if (cadena.length() == 3)
                return false;
        for (char c : cadena.toCharArray())
        {
            i++;
            if (i == 0 || i == cadena.length()-1)
                continue;
            else if (i == 1 && c == '^')
                continue;
            else if (i == 1)
                ant = c;
            else if (i == 2 && ant == ' ')
                ant = c;
            else
            {
                int a1 = (int) ant;
                int a2 = (int) c;
                if (a1+1 != a2)
                        return false;
                ant = c;
            }
        }
        return true;
    }
    /**
     * Método que comprueba si dos cadenas son equivalentes para
     * su aceptación en el autómata.
     * @param c1 Cadena principal
     * @param c2 Cadena que comprobaremos con la principal
     * @return Verdadero si son equivalentes
     */
    boolean comprobarTerminal(String c1, String c2)
    {
        boolean encontrado = false;
        boolean set = false;
        boolean neg = false;
        boolean escapado = false;
        String p = "";
        int i = -1;
        char[] cAux = new char[2];
//        if (c1.equals("\".\""))
//            if ((int) c2.toCharArray()[0] != 10)
//                encontrado = true;
        if (c1.charAt(1) == '^')
                neg = true;
        if ((c1.charAt(c1.length()-2) == '#') && (c1.charAt(c1.length()-3) == '#') && (c1.charAt(c1.length()-4) == '^'))
                set = true;
        if (this.esConjunto(c1) || set)
        {
            for (char t : c1.toCharArray())
            {
                if (escapado)
                {
                    if (t == 'n')
                        p = "\\n";
                    if ((int) c2.toCharArray()[0] == 10)
                        c2 = "\\n";
                    else {
                        escapado = false;
                        continue;
                    }
                    if (p.equals(c2))
                    {
                        encontrado = true;
                        break;
                    }
                }
                if (t == '\\')
                {
                    escapado = true;
                    continue;
                }
                i++;
                if (i == 0 || i == c1.length()-1)
                    continue;
                if (set && (i == c1.length()-2) || (i == c1.length()-3) || (i == c1.length()-4))
                    continue;
                if (neg && i == 1)
                    continue;
                if (String.valueOf(t).equals(c2))
                {
                    encontrado = true;
                    break;
                }
            }
        }
        else
        {
            if ((int) c2.toCharArray()[0] == 10 || (int) c2.toCharArray()[0] == 9 || (int) c2.toCharArray()[0] == 13 || (int) c2.toCharArray()[0] == 13 ||
                (int) c2.toCharArray()[0] == 12 || (int) c2.toCharArray()[0] == 8 || (int) c2.toCharArray()[0] == 92 || (int) c2.toCharArray()[0] == 39 ||
                (int) c2.toCharArray()[0] == 34)
            {
                if ((int) c2.toCharArray()[0] == 10)
                    c2 = "\\n";
            }
            if ((c1.equals("\""+c2+"\"")))// || (c1.equals("\".\"")))
                    encontrado = true;
        }
        if (!neg)
            return encontrado;
        else
            return !encontrado;
    }
    /**
     * Clase auxiliar para definir un estado del aut�mata
     * @author herre
     *
     */
    public class Estado
    {
        public List<Integer> lposiciones;
        public boolean marcado;
        public int n;
        public boolean esfinal = false;
        public boolean esinicial = false;
        public boolean limitador = false;
        public boolean esInicio = false;
        public List<Integer> expRegsLim = new LinkedList<>();
        public List<Integer> expRegs = new LinkedList<>();
        public List<Integer> lTerm = new LinkedList<>();
        public List<List<Estado>> anterior = new LinkedList<>();

        /**
         * Constructor principal
         * @param l Lista de posiciones
         */
        public Estado(List<Integer> l)
        {
            this.lposiciones = new LinkedList<>();
            if (l != null)
                    this.lposiciones.addAll(l);
            this.marcado = false;
            //this.n = -1; // NUEVO
        }
        /**
         * M�todo para imprimir por pantalla la informaci�n de un estado
         */
        public void imprimir()
        {
            System.out.println("Estado "+this.n);
        }
    }
    /**
     * Clase auxiliar para definir una transici�n en un aut�mata
     * @author herre
     *
     */
    public class Transicion
    {
        public Estado estadoInicial;
        public String terminal;
        public Estado estadoFinal;
        /**
         * Constructor principal
         * @param ei Estado inicial
         * @param t Identificador del s�mbolo de la transici�n
         * @param ef Estado final
         */
        public Transicion(Estado ei, String t, Estado ef)
        {
            this.estadoInicial = ei;
            this.terminal = t;
            this.estadoFinal = ef;
        }
        /**
         * Metodo para imprimir por pantalla los valores de una transici�n
         */
        public void imprimir()
        {
            if (this.estadoInicial.esinicial)
                    System.out.print("INICIAL ");
            if (this.estadoInicial.esfinal)
                    System.out.print("FINAL ");
            System.out.println("Estado base: "+this.estadoInicial.n);
            System.out.println("Transicion: "+this.terminal);
            if (this.estadoFinal.esinicial)
                    System.out.print("INICIAL ");
            if (this.estadoFinal.esfinal)
                    System.out.print("FINAL ");
            System.out.println("Estado Final: "+ this.estadoFinal.n);
        }
    }
    /**
     * Método que dada una entrada en forma de String y un estado del autómata,
     * comprueba si con dicho terminal se puede avanzar a otro(s) estado(s), en
     * ese caso, los va introduciendo en una lista de estados.
     * @param tok cadena que se lee
     * @param lIni lista de estados iniciales
     * @param tabla automata
     * @param arbol arbol
     * @return Lista de estados
     */
    public List<Estado> siguienteToken(String tok, List<Estado> lIni, List<List<Estado>> tabla, NodoArbol arbol)
    {
        List<Estado> lEst = new LinkedList<>();
        if ((int) tok.toCharArray()[0] == 8)
            return lEst;
        List<Estado> lAux;
        String cad;
        boolean esfinal = false;
        boolean vacio = true;
        int i = 0;
        if (lIni.isEmpty())
        {
            lAux = tabla.get(0);
            for (Estado e : lAux)
            {    
                i++;
                if (e.n == 0)
                    continue;
                cad = this.devolverTerminal(arbol,i);
                if (this.comprobarTerminal(cad,tok))
                {
                    e.anterior = new LinkedList<>(); // OJO, PROBAR
                    e.anterior.add(lAux);
                    lEst.add(e);
                }    
            }
            
            for (Estado auxE : lEst) {
                lAux = tabla.get(auxE.n);
                for (Estado est : lAux) {
                    if (est.n != 0) {
                        vacio = false;
                        break;
                    }
                }
                if (vacio) {
                    auxE.limitador = true;
                }
                vacio = true;
            }
        }
        else
        {
            for (Estado es : lIni)
            {
                if (es.n == 0)
                    continue;
                lAux = tabla.get(es.n);
                for (Estado ea : lAux)
                    ea.anterior = new LinkedList<>();
            }
            for (Estado est : lIni)
            {
                if (this.esEstadoFinal(est.n, tabla)) {
                    esfinal = true;
                }
                if (est.limitador) {
                    continue;
                }
                i = 0;
                lAux = tabla.get(est.n);
                for (Estado e : lAux)
                {
                    i++;
                    if (e.n == 0)
                        continue;
                    cad = this.devolverTerminal(arbol,i);
                    if (this.comprobarTerminal(cad,tok))
                    {
                        e.anterior.add(lAux);
                        lEst.add(e);
                    }
                }
            }
            
            if (esfinal || lEst.isEmpty()) {
                lAux = tabla.get(0);
                i = 0;
                for (Estado e : lAux)
                {
                    
                    if (lEst.isEmpty() || !lEst.contains(e))
                        e.anterior = new LinkedList<>();
                    
                    i++;
                    if (e.n == 0)
                        continue;
                    cad = this.devolverTerminal(arbol,i);
                    if (this.comprobarTerminal(cad,tok))
                    {
                        e.anterior.add(lAux);
                        e.esInicio = true;
                        lEst.add(e);
                    }    
                }
            }
            
            for (Estado auxE : lEst) {
                lAux = tabla.get(auxE.n);
                for (Estado est : lAux) {
                    if (est.n != 0) {
                        vacio = false;
                        break;
                    }
                }
                if (vacio) {
                    auxE.limitador = true;
                }
                vacio = true;
            }
        }
        return lEst;
    }
    /**
     * Método para crear un autómata a partir de un árbol binario de una
     * expresión regular. Se devolverá una tabla representando las transiciones
     * desde cada estado con cada símbolo de entrada posible.
     * @param arbol árbol binario de la expresión regular
     * @return Tabla de transiciones
     */
    public List<List<Estado>> crearAutomata(NodoArbol arbol)
    {
        List<List<Estado>> tabla = new LinkedList<>();
        List<Estado> lest = new LinkedList<>();
        List<Integer> conjunto = new LinkedList<>();
        List<Estado> laux = new LinkedList<>();
        boolean esta = false;
        int noMarcado = 1;
        int naux = 0;
        int nER;
        // Añadimos el estado inicial para el automata
        List<Integer> la = primeraPos(arbol);
        Estado einicial = new Estado(primeraPos(arbol));
        einicial.n = naux;
        einicial.esinicial = true;
        if (la.contains(arbol.hijoDcho.posicion))
            einicial.esfinal = true;
        naux++;
        Estado nuevo;
        lest.add(einicial);
        laux.add(einicial);
        // Iniciamos el algoritmo de creación del autómata
        while (noMarcado > 0)
        {
            for (Estado actual : lest)
            {
                if (!actual.marcado)
                {
                    tabla.add(new LinkedList<>());
                    for (int x = 0; x < arbol.hijoDcho.posicion-1; x++)
                    {
                        tabla.get(tabla.size()-1).add(new Estado(null));
                    }
                    actual.marcado = true;
                    noMarcado--;
                    for (int i = 1;i < arbol.hijoDcho.posicion;i++)
                    {
                        if (actual.lposiciones.contains(i))
                        {
                            conjunto.addAll(siguientePos(arbol,i));
                        }
                        if (!conjunto.isEmpty())
                        {
                            nuevo = new Estado(conjunto);
                            nER = this.devolverNER(arbol,i); // EN PRUEBA
                            nuevo.expRegs.add(nER); // EN PRUEBA
                            nuevo.lTerm.add(i);
                            for (Estado aux : laux)
                            {
                                if (aux.lposiciones.equals(conjunto))
                                {
                                    esta = true;
                                    aux.expRegs.add(nER); // EN PRUEBA
                                    aux.lTerm.add(i);
                                    nuevo = aux;
                                    break;
                                }
                            }
                            if (!esta)
                            {
                                nuevo.n = naux;
                                naux++;
                                if (conjunto.contains(arbol.hijoDcho.posicion))
                                    nuevo.esfinal = true;
                                laux.add(nuevo);
                                noMarcado++;
                            }
                            else
                                esta = false;
                            tabla.get(actual.n).set(i-1, nuevo);
                            conjunto.clear();
                        }
                    }
                }
            }
            lest.clear();
            lest.addAll(laux);
        }
        return tabla;
    }   
    /**
     * Método que devuelve si un estado es final o no.
     * @param n índice del estado
     * @param aut autómata
     * @return true si es final
     */
    private boolean esEstadoFinal(int n, List<List<Estado>> aut)
    {
        for (List<Analizador.Estado> l : aut)
        {
            for (Analizador.Estado e : l)
            {
                if (e.n == n)
                    return e.esfinal;
            }
        }
        return false;
    }
    /**
     * Método que devuelve una lista de índices de expresiones regulares, dada una lista de estados en los que
     * se encuentra el autómata. Además, se pasa como parámetro una lista de las expresiones regulares que se 
     * activaron en el último estado. Con esta lista se comprueba si en el nuevo estado se activa alguna expresión
     * regular que anteriormente no se encontraba activa, si es así, no se activa.
     * @param tok cadena que se lee
     * @param lE lista de estados
     * @param tabla automata
     * @param lER lista de ER
     * @throws java.lang.Exception
     * @Param lista de expresiones regulares
     * @param arbol arbol
     * @return lista de ER que se activan
     */
    public List<Integer> activarER(String tok, List<Estado> lE, List<List<Estado>> tabla, List<Integer> lER, NodoArbol arbol) throws Exception
    {
        List<Integer> lOut = new LinkedList<>();
        List<Integer> lInt = new LinkedList<>();
        List<Estado> lAux;
        Estado eaux;
        String cad;
        int c, a;
        int i = 0;
        // boolean comp;
        lAux = tabla.get(0);
        
        for (@SuppressWarnings("unused") Estado e : lAux) // Este primer bucle es para coger el �ndice de terminal del string le�do
        {
            i++;
            cad = this.devolverTerminal(arbol,i);
            if (this.comprobarTerminal(cad,tok))
                lInt.add(i);
        }
        for (Estado est : lE)
        {
            for (Integer obj : lInt)
            {
                // EN PRUEBA
                c = est.lTerm.indexOf(obj); // Cogemos el índice en el que se encuentra ese terminal
                if (c != -1)
                {
                    a = est.expRegs.get(c) + 1; // Cogemos el índice de la ER que tiene el terminal c
                    // Cogemos el estado en el que estamos y comprobamos su anterior con el terminal correspondiente,
                    // si conincide la transición en alguno de sus estados anteriores, activamos la ER
                    for (List<Estado> listAux : est.anterior)
                    {
                        eaux = listAux.get(obj-1);
                        if (eaux.n != 0 && eaux.n == est.n)
                        {
                            if (est.limitador) {
                                a = a/-1;
                                lOut.add(a);
                                a = a/-1;
                            } else {
                                lOut.add(a);
                            }
                        }
                    }
                }	
            }
        }
        return lOut;
    }
    /**
     * Método para unir dos árboles mediante un nodo operación que 
     * se pasa por par�metro.
     * @param r1
     * @param r2
     * @param op String para representar el nodo raiz
     * @return raiz
     */
    public NodoArbol unirArbol(NodoArbol r1, NodoArbol r2, String op)
    {
        NodoArbol raiz = new NodoArbol(op);
        raiz.insertarIzda(r1);
        raiz.insertarDcha(r2);
        return raiz;
    }
    /**
     * Método que recibe una lista con los componentes de una 
     * expresión regular y devuelve un árbol sintáctico binario 
     * de la misma
     * @param lExp Lista de componentes de una expresión regular
     * @param posicion 
     * @return árbol sintáctico binario de una expresión regular
     * @throws Exception 
     */
    @SuppressWarnings("null")
    public NodoArbol crearArbol(List<String> lExp, int posicion) throws Exception
    {
        NodoArbol raiz = null;
        NodoArbol nodoAnterior = null;
        NodoArbol nodoActual = null;
        NodoArbol nodoAuxiliar = null;
        NodoArbol nodoAuxiliar1 = null;
        List<String> lpar;
        for (String n : lExp)
        {
            while (!parseado(n))
            {
                lpar = parsear(n);
                n = lpar.get(0);
            }
            lpar = parsear(n);
            if (lpar.size() == 1) // Caso base
            {
                nodoActual = new NodoArbol(lpar.get(0));
            }
            else // Caso recursivo
            {
                nodoActual = crearArbol(lpar,posicion);
            }	
            if (raiz == null) // Se trata del primer nodo del arbol que tenemos que crear
            {
                if (nodoActual.hoja) // Comprobamos si es una hoja y a�adimos la expresi�n regular que representa
                {
                    nodoActual.expReg = posicion;
                }
                raiz = nodoActual;
                nodoAnterior = nodoActual;
            }
            else // Ya existe algún nodo creado anteriormente
            {
                if (nodoActual.hoja) // Comprobamos si es una hoja y añadimos la expresión regular que representa
                {
                    nodoActual.expReg = posicion;
                }
                if (nodoActual.hoja || (nodoActual.cuantificador && nodoActual.hijoIzdo != null))
                {
                    if (nodoAnterior.info.equals("|"))
                    {
                        if (nodoAnterior.hijoIzdo == null)
                        {
                            nodoAnterior.insertarIzda(nodoActual);
                            nodoAnterior = nodoActual;
                        }
                        else if (nodoAnterior.hijoDcho == null)
                        {
                            nodoAnterior.insertarDcha(nodoActual);
                            nodoAnterior = nodoActual;
                        }
                        else
                        {
                            if (nodoAnterior.padre != null)
                            {
                                if (nodoAnterior.padre.cuantificador)
                                {
                                    if (nodoAnterior.padre.padre != null && nodoAnterior.padre.padre.operacion)
                                    {
                                        nodoAuxiliar = new NodoArbol(".");
                                        nodoAnterior.padre.padre.hijoDcho = null;
                                        nodoAnterior.padre.padre.insertarDcha(nodoAuxiliar);
                                        nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                    }
                                    else
                                    {
                                        nodoAuxiliar = new NodoArbol(".");
                                        nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                        raiz = nodoAuxiliar;
                                    }
                                }
                                else
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAnterior.padre.hijoDcho = null;
                                    nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                                    nodoAuxiliar.insertarIzda(nodoAnterior);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                }
                            }
                            else
                            {
                                nodoAuxiliar = new NodoArbol(".");
                                nodoAuxiliar.insertarIzda(nodoAnterior);
                                nodoAuxiliar.insertarDcha(nodoActual);
                                nodoAnterior = nodoActual;
                                raiz = nodoAuxiliar;
                            }	
                        }	
                    }
                    else if (nodoAnterior.hoja || nodoAnterior.cuantificador)
                    {
                        if (nodoAnterior.padre != null)
                        {
                            if (nodoAnterior.padre.cuantificador)
                            {
                                if (nodoAnterior.padre.padre != null && nodoAnterior.padre.padre.operacion)
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAnterior.padre.padre.hijoDcho = null;
                                    nodoAnterior.padre.padre.insertarDcha(nodoAuxiliar);
                                    nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                }
                                else
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual; 
                                    raiz = nodoAuxiliar;
                                }
                            }
                            else
                            {
                                nodoAuxiliar = new NodoArbol(".");
                                nodoAnterior.padre.hijoDcho = null;
                                nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                                nodoAuxiliar.insertarIzda(nodoAnterior);
                                nodoAuxiliar.insertarDcha(nodoActual);
                                nodoAnterior = nodoActual;
                            }
                        }
                        else
                        {
                            nodoAuxiliar = new NodoArbol(".");	
                            nodoAuxiliar.insertarIzda(nodoAnterior);
                            nodoAuxiliar.insertarDcha(nodoActual);
                            nodoAnterior = nodoActual; // nodoAuxiliar
                            raiz = nodoAuxiliar;
                        }
                    }
                    else if (nodoAnterior.info.equals("."))
                    {
                        if (nodoAnterior.padre != null)
                        {
                            if (nodoAnterior.padre.cuantificador)
                            {
                                if (nodoAnterior.padre.padre != null && nodoAnterior.padre.padre.operacion)
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAnterior.padre.padre.hijoDcho = null;
                                    nodoAnterior.padre.padre.insertarDcha(nodoAuxiliar);
                                    nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                }
                                else
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                    raiz = nodoAuxiliar;
                                }
                            }
                            else
                            {
                                nodoAuxiliar = new NodoArbol(".");
                                nodoAnterior.padre.hijoDcho = null;
                                nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                                nodoAuxiliar.insertarIzda(nodoAnterior);
                                nodoAuxiliar.insertarDcha(nodoActual);
                                nodoAnterior = nodoActual;
                            }
                        }
                        else
                        {
                            nodoAuxiliar = new NodoArbol(".");
                            nodoAuxiliar.insertarIzda(nodoAnterior);
                            nodoAuxiliar.insertarDcha(nodoActual);
                            nodoAnterior = nodoActual;
                            raiz = nodoAuxiliar;
                        }
                    }
                }
                else if (nodoActual.info.equals("|"))
                {
                    if (nodoAnterior.hoja || nodoAnterior.cuantificador)
                    {
                        if (nodoAnterior.padre != null)
                        {
                            if (nodoActual.hijoIzdo != null)
                            {
                                if (nodoAnterior.padre.operacion)
                                {
                                        nodoAuxiliar = new NodoArbol(".");
                                        nodoAnterior.padre.hijoDcho = null;
                                        nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                                        nodoAuxiliar.insertarIzda(nodoAnterior);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                }
                                else
                                {
                                    if (nodoAnterior.padre.cuantificador)
                                    {
                                        if (nodoAnterior.padre.padre != null && nodoAnterior.padre.padre.operacion)
                                        {
                                            nodoAuxiliar = new NodoArbol(".");
                                            nodoAnterior.padre.padre.hijoDcho = null;
                                            nodoAnterior.padre.padre.insertarDcha(nodoAuxiliar);
                                            nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                            nodoAuxiliar.insertarDcha(nodoActual);
                                            nodoAnterior = nodoActual;
                                        }
                                        else
                                        {
                                            nodoAuxiliar = new NodoArbol(".");
                                            nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                            nodoAuxiliar.insertarDcha(nodoActual);
                                            nodoAnterior = nodoActual;
                                            raiz = nodoAuxiliar;
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (nodoAnterior.padre.cuantificador)
                                {
                                    if (nodoAnterior.padre.padre != null)
                                    {
                                        if (nodoAnterior.padre.padre.info.equals("|"))
                                        {
                                            nodoAnterior.padre.padre.hijoDcho = null;
                                            nodoAnterior.padre.padre.insertarDcha(nodoActual);
                                            nodoActual.insertarIzda(nodoAnterior.padre);
                                            nodoAnterior = nodoActual;
                                        }
                                        else
                                        {
                                            nodoAuxiliar1 = nodoAnterior.padre;
                                            while (nodoAuxiliar1.padre != null)
                                                nodoAuxiliar1 = nodoAuxiliar1.padre;
                                            if (nodoAuxiliar1.info.equals("."))
                                            {
                                                nodoActual.insertarIzda(nodoAuxiliar1);
                                                nodoAnterior = nodoActual;
                                                raiz = nodoActual;
                                            }
                                            else
                                            {
                                                nodoAuxiliar = nodoAuxiliar1;
                                                nodoAuxiliar1 = nodoAuxiliar1.hijoDcho;
                                                nodoAuxiliar.hijoDcho = null;
                                                nodoActual.insertarIzda(nodoAuxiliar1);
                                                nodoAuxiliar.insertarDcha(nodoActual);
                                                nodoAnterior = nodoActual;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        nodoActual.insertarIzda(nodoAnterior.padre);
                                        nodoAnterior = nodoActual;
                                        raiz = nodoActual;
                                    }
                                }
                                else if (nodoAnterior.padre.info.equals("|"))
                                {
                                    nodoAnterior.padre.hijoDcho = null;
                                    nodoAnterior.padre.insertarDcha(nodoActual);
                                    nodoActual.insertarIzda(nodoAnterior);
                                    nodoAnterior = nodoActual;
                                }
                                else if (nodoAnterior.padre.info.equals("."))
                                {
                                    nodoAuxiliar1 = nodoAnterior.padre;
                                    while (nodoAuxiliar1.padre != null)
                                        nodoAuxiliar1 = nodoAuxiliar1.padre;
                                    if (nodoAuxiliar1.info.equals("."))
                                    {
                                        nodoActual.insertarIzda(nodoAuxiliar1);
                                        nodoAnterior = nodoActual;
                                        raiz = nodoActual;
                                    }
                                    else
                                    {
                                        nodoAuxiliar = nodoAuxiliar1;
                                        nodoAuxiliar1 = nodoAuxiliar1.hijoDcho;
                                        nodoAuxiliar.hijoDcho = null;
                                        nodoActual.insertarIzda(nodoAuxiliar1);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (nodoActual.hijoIzdo != null)
                            {
                                nodoAuxiliar = new NodoArbol(".");
                                nodoAuxiliar.insertarIzda(nodoAnterior);
                                nodoAuxiliar.insertarDcha(nodoActual);
                                nodoAnterior = nodoActual;
                                raiz = nodoAuxiliar;
                            }
                            else
                            {
                                nodoActual.insertarIzda(nodoAnterior);
                                nodoAnterior = nodoActual;
                                raiz = nodoActual;
                            }
                        }
                    }
                    else if (nodoAnterior.operacion)
                    {
                        if (nodoActual.hijoIzdo != null)
                        {
                            if (nodoAnterior.hijoDcho != null)
                            {
                                if (nodoAnterior.padre != null)
                                {
                                    if (nodoAnterior.padre.operacion)
                                    {
                                        nodoAuxiliar = new NodoArbol(".");
                                        nodoAnterior.padre.hijoDcho = null;
                                        nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                                        nodoAuxiliar.insertarIzda(nodoAnterior);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                    }
                                }
                                else
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAuxiliar.insertarIzda(nodoAnterior);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                    raiz = nodoAuxiliar;
                                }
                            }
                            else
                            {
                                nodoAnterior.insertarDcha(nodoActual);
                                nodoAnterior = nodoActual;
                            }
                        }
                        else
                        {
                            if (nodoAnterior.padre == null)
                            {
                                nodoActual.insertarIzda(nodoAnterior);
                                nodoAnterior = nodoActual;
                                raiz = nodoActual;
                            }
                            else
                            {
                                if (nodoAnterior.padre.cuantificador)
                                {
                                    nodoAuxiliar1 = nodoAnterior.padre;
                                    while (nodoAuxiliar1.padre != null)
                                        nodoAuxiliar1 = nodoAuxiliar1.padre;
                                    if (nodoAuxiliar1.info.equals("."))
                                    {
                                        nodoActual.insertarIzda(nodoAuxiliar1);
                                        nodoAnterior = nodoActual;
                                        raiz = nodoActual;
                                    }
                                    else
                                    {
                                        nodoAuxiliar = nodoAuxiliar1;
                                        nodoAuxiliar1 = nodoAuxiliar1.hijoDcho;
                                        nodoAuxiliar.hijoDcho = null;
                                        nodoActual.insertarIzda(nodoAuxiliar1);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                    }
                                }
                                else if (nodoAnterior.padre.info.equals("|"))
                                {
                                    nodoAnterior.padre.hijoDcho = null;
                                    nodoAnterior.padre.insertarDcha(nodoActual);
                                    nodoActual.insertarIzda(nodoAnterior);
                                    nodoAnterior = nodoActual;
                                }
                                else if (nodoAnterior.padre.info.equals("."))
                                {
                                    nodoAuxiliar1 = nodoAnterior.padre;
                                    while (nodoAuxiliar1.padre != null)
                                        nodoAuxiliar1 = nodoAuxiliar1.padre;
                                    if (nodoAuxiliar1.info.equals("."))
                                    {
                                        nodoActual.insertarIzda(nodoAuxiliar1);
                                        nodoAnterior = nodoActual;
                                        raiz = nodoActual;
                                    }
                                    else
                                    {
                                        nodoAuxiliar = nodoAuxiliar1;
                                        nodoAuxiliar1 = nodoAuxiliar1.hijoDcho;
                                        nodoAuxiliar.hijoDcho = null;
                                        nodoActual.insertarIzda(nodoAuxiliar1);
                                        nodoAuxiliar.insertarDcha(nodoActual);
                                        nodoAnterior = nodoActual;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (nodoActual.info.equals("."))
                {
                    if (nodoAnterior.hoja)
                    {
                        if (nodoAnterior.padre != null)
                        {
                            if (nodoAnterior.padre.cuantificador)
                            {
                                if (nodoAnterior.padre.padre != null && nodoAnterior.padre.padre.operacion)
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAnterior.padre.padre.hijoDcho = null;
                                    nodoAnterior.padre.padre.insertarDcha(nodoAuxiliar);
                                    nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                }
                                else
                                {
                                    nodoAuxiliar = new NodoArbol(".");
                                    nodoAuxiliar.insertarIzda(nodoAnterior.padre);
                                    nodoAuxiliar.insertarDcha(nodoActual);
                                    nodoAnterior = nodoActual;
                                    raiz = nodoAuxiliar;
                                }
                            }
                            else
                            {
                                nodoAuxiliar = new NodoArbol(".");
                                nodoAnterior.padre.hijoDcho = null;
                                nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                                nodoAuxiliar.insertarIzda(nodoAnterior);
                                nodoAuxiliar.insertarDcha(nodoActual);
                                nodoAnterior = nodoActual;
                            }
                        }
                        else
                        {
                            nodoAuxiliar = new NodoArbol(".");
                            nodoAuxiliar.insertarIzda(nodoAnterior);
                            nodoAuxiliar.insertarDcha(nodoActual);
                            nodoAnterior = nodoActual;
                            raiz = nodoAuxiliar;
                        }
                    }
                    else if (nodoAnterior.info.equals("|"))
                    {
                        nodoAnterior.insertarDcha(nodoActual);
                        nodoAnterior = nodoActual;
                    }
                    else if (nodoAnterior.info.equals("."))
                    {
                        if (nodoAnterior.padre != null)
                        {
                            nodoAuxiliar = new NodoArbol(".");
                            nodoAnterior.padre.hijoDcho = null;
                            nodoAnterior.padre.insertarDcha(nodoAuxiliar);
                            nodoAuxiliar.insertarIzda(nodoAnterior);
                            nodoAuxiliar.insertarDcha(nodoActual);
                            nodoAnterior = nodoActual;
                        }
                        else
                        {
                            nodoAuxiliar = new NodoArbol(".");
                            nodoAuxiliar.insertarIzda(nodoAnterior);
                            nodoAuxiliar.insertarDcha(nodoActual);
                            nodoAnterior = nodoActual;
                            raiz = nodoAuxiliar;
                        }
                    }
                }
                else
                {
                    if (nodoAnterior.hoja)
                    {
                        if (nodoAnterior.padre != null)
                        {
                            nodoAnterior.padre.hijoDcho = null;
                            nodoAnterior.padre.insertarDcha(nodoActual);
                            nodoActual.insertarIzda(nodoAnterior);
                        }
                        else
                        {
                            nodoActual.insertarIzda(nodoAnterior);
                            raiz = nodoActual;
                        }
                    }
                    else if (nodoAnterior.operacion)
                    {
                        if (nodoAnterior.padre != null)
                        {
                            nodoAnterior.padre.hijoDcho = null;
                            nodoAnterior.padre.insertarDcha(nodoActual);
                            nodoActual.insertarIzda(nodoAnterior);
                            nodoAnterior = nodoActual;
                        }
                        else
                        {
                            nodoActual.insertarIzda(nodoAnterior);
                            nodoAnterior = nodoActual;
                            raiz = nodoActual;
                        }
                    }
                }
            }
        }
    return raiz;
    }
}
