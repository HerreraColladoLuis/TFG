import java.util.LinkedList;
import java.util.List;
/**
 * Clase central de la aplicación, donde introduciré toda la lógica
 * @author herre
 *
 */
public class Analizador 
{
	List<String> listaM;
	List<String> listaER;
	/**
	   * Método que recibe una lista con strings correspondientes a macros 
	   * y parsea cada uno traduciendolos a un estado común
	   * @param lista con las macros
	   * @return lista con las macros traducidas a un estado común
	   */
	  public List<String> translateMacro(List<String> lista)
	  {
		  List<String> out = new LinkedList<>();
		  String aux;
		  for (String cad : lista)
		  {
			  aux = "";
			  aux = procesarMacro(cad);
			  if (aux != "")
				  out.add(aux);
		  }
		  return out;
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
			  aux = "";
			  aux = procesarRegex(cad);
			  if (aux != "")
				  out.add(aux);
		  }
		  return out;
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
		String out = "";
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
		String out = "";
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
					i = i+1; // Apuntamos a la negación
					if (rech[i+1] == ']')
						n = true;
				}
				i = i+1; // Apuntamos al primer caracter del set
				while (true)
				{
					// Comprobamos si es una letra, mayúscula o minúscula, o un número
					if (((int) rech[i] > 64 && (int) rech[i] < 91) || ((int) rech[i] > 96 && (int) rech[i] < 123) || ((int) rech[i] > 47 && (int) rech[i] < 58))
					{
						// Caso correspondiente a [a-z] o [0-9] (por ejemplo)
						if (rech[i+1] == '-')
						{
							for (j = (int) rech[i]; j <= (int) rech[i+2]; j++)
								cadena += (char) j;
							i = i+3; // Apuntamos al siguiente caracter de la secuencia
						}
						else // Si no es una secuencia, guardamos el número o la letra
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
					// Cualquier otro símbolo
					else
					{
						cadena += rech[i]; // Guardamos el símbolo
						i = i+1;
					}	
				}
				cadena += "^##\""; // Añadir algo para saber que es un set ^##
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
					for (String comp : listaM) // Recorremos la lista buscando la posición de la macro dada
					{
						if (comp.equalsIgnoreCase(aux1)) // Cuando la encontramos, salimos
							break;
						j = j+1; // En esta variable guardamos la posición
					}
				}
				else 
					throw new Exception("Lista de Macros vacía o nula"); // Devolvemos una excepción 
				// Comprobamos que la lista no esté vacia o sea nula
				if (listaER != null && !listaER.isEmpty()) 
				{
					cadAux = traducir(listaER.get(j)); // Hacemos recursividad para parsear una macro
				}
				else
					throw new Exception("Lista de ER vacía o nula"); // Devolvemos una excepción
				// Añadimos el arbol de la macro como hijo del arbol actúal
				if (cadAux == null)
					throw new Exception("Cadena auxiliar nula"); // Devolvemos una excepción
				else
				{
					cadena += "(" + cadAux + ")";
				}
			}
			// Entramos en el caso en el que se abre un paréntesis
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
	 * Método para recorrer un árbol binario en in orden
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
	 * Método para aumentar un árbol de una expresión regular
	 * @param nodo Raíz del árbol a aumentar
	 * @return Árbol aumentado
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
	 * en el orden de aparición en el mismo
	 * @param nodo Nodo inicial del arbol
	 * @param pos contador 
	 * @return última posición asignada
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
		else if (nodo.info.equals("?")) // CAMBIO AQUÍ RESPECTO DOCUMENTACION
		{
			out.addAll(ultimaPos(nodo.hijoIzdo));
			if (aux.hijoIzdo.equals(nodo))
			{
				while (!aux.info.equals("."))
				{
					aux = aux.padre;
				}
				out.addAll(ultimaPos(aux.hijoDcho));
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
			else if (nodo.info.equals("*") || nodo.info.equals("+")) // CAMBIO AQUÍ RESPECTO DOCUMENTACION
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
	 * Clase auxiliar para un árbol binario
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
		
		/**
		 * Constructor para un nodo del árbol
		 * @param info Información que llevará el nodo
		 */
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
		 * Método que devuelve si un nodo es una hoja
		 * @param nodo Nodo que evaluaremos
		 * @return verdadero si es una hoja
		 */
		public boolean esHoja()
		{
			return (this.hijoIzdo == null && this.hijoDcho == null);
		}
		/**
		 * Método para insertar un nodo como hijo derecho de otro nodo padre
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
		 * Método para insertar un nodo como hijo izquierdo de otro nodo padre
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
	 * Método que devuelve, en forma de string, la información
	 * de un nodo hoja determinado por su posición.
	 * @param nodo Arbol a recorrer
	 * @param posicion Posición del nodo hoja a evaluar
	 * @return Información del nodo hoja en forma de string
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
	 * Método que devuelve si una cadena es un conjunto. Es decir,
	 * "abcdef" sería un conjunto determinado por a-f.
	 * @param cadena Cadena a evaluar
	 * @return Verdadero si es un conjunto
	 */
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
		int i = -1;
		if (c1.charAt(1) == '^')
			neg = true;
		if ((c1.charAt(c1.length()-2) == '#') && (c1.charAt(c1.length()-3) == '#') && (c1.charAt(c1.length()-4) == '^'))
			set = true;
		if (this.esConjunto(c1) || set)
		{
			for (char t : c1.toCharArray())
			{
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
			if ((c1.equals("\""+c2+"\"")) || (c1.equals("\".\"")))
				encontrado = true;
		}
		if (!neg)
			return encontrado;
		else
		{
			if (encontrado)
				return false;
			else
				return true;
		}
	}
	/**
	 * Clase auxiliar para definir un estado del autómata
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
		}
		/**
		 * Método para imprimir por pantalla la información de un estado
		 */
		public void imprimir()
		{
			System.out.println("Estado "+this.n);
		}
	}
	/**
	 * Clase auxiliar para definir una transición en un autómata
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
		 * @param t Identificador del símbolo de la transición
		 * @param ef Estado final
		 */
		public Transicion(Estado ei, String t, Estado ef)
		{
			this.estadoInicial = ei;
			this.terminal = t;
			this.estadoFinal = ef;
		}
		/**
		 * Metodo para imprimir por pantalla los valores de una transición
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
			System.out.println("Estado Final: "+this.estadoFinal.n);
		}
	}
	/**
	 * Método que dada una entrada en forma de String y un estado del autómata,
	 * comprueba si con dicho terminal se puede avanzar a otro(s) estado(s). En
	 * ese caso, los va introduciendo en una lista de enteros que representan el
	 * estado.
	 * @param terminal
	 * @param estado
	 * @param tabla
	 * @param arbol
	 * @return Lista de estados
	 */
	public List<Integer> siguienteToken(String tok, int est, List<List<Estado>> tabla, NodoArbol arbol)
	{
		List<Integer> lEst = new LinkedList<>();
		List<Estado> lAux = tabla.get(est);
		for (int i = 0; i < lAux.size(); i++)
		{
			if (this.devolverTerminal(arbol,i).equals(tok))
			{
				if (lAux.get(i).n != 0)
					lEst.add(lAux.get(i).n);
			}
		}	
		return lEst;
	}
	/**
	 * Método para crear un autómata a partir de un árbol binario de una
	 * expresión regular. Se devolverá una tabla representando las transiciones
	 * desde cada estado con cada símbolo de entrada posible.
	 * @param arbol Árbol binario de la expresión regular
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
							for (Estado aux : laux)
							{
								if (aux.lposiciones.equals(conjunto))
								{
									esta = true;
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
	 * Método que recibe una lista con los componentes de una 
	 * expresión regular y devuelve un árbol sintáctico binario 
	 * de la misma
	 * @param lExp Lista de componentes de una expresión regular
	 * @return Árbol sintáctico binario de una expresión regular
	 * @throws Exception 
	 */
	public NodoArbol crearArbol(List<String> lExp) throws Exception
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
				nodoActual = crearArbol(lpar);
			}	
			if (raiz == null) // Se trata del primer nodo del arbol que tenemos que crear
			{
				raiz = nodoActual;
				nodoAnterior = nodoActual;
			}
			else // Ya existe algún nodo creado anteriormente
			{
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
