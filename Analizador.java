import java.util.LinkedList;
import java.util.List;

public class Analizador 
{
	List<String> listaM;
	List<String> listaER;
	/**
	   * M�todo que recibe una lista con strings correspondientes a macros 
	   * y parsea cada uno traduciendolos a un estado com�n
	   * @param lista con las macros
	   * @return lista con las macros traducidas a un estado com�n
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
	   * M�todo que recibe una lista con strings correspondientes a regex 
	   * y parsea cada una traduciendolos a un estado com�n
	   * @param lista con las regex
	   * @return lista con las regex traducidas a un estado com�n
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
	 * M�todo para traducir una expresi�n regular a un formato
	 * que pueda reconocer el algoritmo final
	 * @param exp string de la expresi�n
	 * @return string de la expresi�n traducido
	 * @throws Exception 
	 */
	public String traducir(String exp) throws Exception
	{
		String cadAux;
		String aux1;
		String cadena = "";
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
				if (rech[i+1] == '^') // Comprobamos si est� negado
				{
					cadena+= rech[i+1]; // aux += ^
					i = i+1; // Apuntamos a la negaci�n
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
				cadena += "\"";
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
				// Comprobamos que la lista no est� vacia o sea nula
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
					throw new Exception("Lista de Macros vac�a o nula"); // Devolvemos una excepci�n 
				// Comprobamos que la lista no est� vacia o sea nula
				if (listaER != null && !listaER.isEmpty()) 
				{
					cadAux = traducir(listaER.get(j)); // Hacemos recursividad para parsear una macro
				}
				else
					throw new Exception("Lista de ER vac�a o nula"); // Devolvemos una excepci�n
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
				cadena += rech[i];
				i = i+1;
				while (true)
				{
					if (rech[i] == '\"')
						break;
					cadena += rech[i];
					i = i+1;
				}
				cadena += "\"";
			}
			else if (rech[i] == ' ')
			{
				// Nada
			}
			else if (rech[i] == '+' || rech[i] == '*' || rech[i] == '?')
			{
				cadena += (String.valueOf(rech[i]));
			}
			// Cualquier otro caracter
			else
			{
				cadena += "\"";
				while (true)
				{
					if (i == rech.length || rech[i] == '+' || rech[i] == '*' || rech[i] == '?' || rech[i] == ' ' || rech[i] == ')' || rech[i] == '|' || rech[i] == '(')
					{
						i = i-1;
						break;
					}
					cadena += rech[i];
					i = i+1;
				}
				cadena += "\"";
			}
			i = i+1;
		}
		return cadena;
	}
	/**
	 * M�todo que devuelve si una Expresi�n Regular est� parseada,
	 * es decir, si no tiene par�ntesis inicial y final.
	 * @param exp Expresi�n Regular
	 * @return True si est� parseada, false en caso contrario
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
	 * M�todo que recibe una expresi�n regular en forma de string
	 * y devuelve una lista con sus componentes 
	 * parseada. Es decir, los componentes que se encuentre sin 
	 * par�ntesis, los a�ade a la lista, y si se encuentra una 
	 * subexpresi�n dentro de par�ntesis, quita �stos y a�ade la 
	 * subexpresi�n como un elemento m�s de la lista.
	 * @param exp Expresi�n regular en forma de string
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
	 * M�todo para recorrer un �rbol binario en pre orden
	 * @param nodo Nodo del �rbol del que empezaremos a recorrer
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
	 * M�todo para numerar las hojas de un arbol sint�ctico
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
	 * M�todo anulable que devuelve verdadero o falso
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
	 * M�todo primeraPos que devuelve una lista de posiciones
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
	 * M�todo ultimaPos que devuelve una lista de posiciones
	 * @param nodo Nodo a analizar
	 * @return Lista de posiciones
	 */
	public List<Integer> ultimaPos(NodoArbol nodo)
	{
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
		else
		{
			out.addAll(ultimaPos(nodo.hijoIzdo));
		}
		return out;
	}
	/**
	 * M�todo siguientePos que se calcula sobre los nodos hoja
	 * @param nodo Nodo desde el que recorreremos el �rbol
	 * @param pos Posici�n de la hoja a analizar
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
			else if (nodo.info.equals("*"))
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
		
		/**
		 * Constructor para un nodo del �rbol
		 * @param info Informaci�n que llevar� el nodo
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
		 * M�todo que devuelve si un nodo es una hoja
		 * @param nodo Nodo que evaluaremos
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
	 * M�todo que recibe una lista con los componentes de una 
	 * expresi�n regular y devuelve un �rbol sint�ctico binario 
	 * de la misma
	 * @param lExp Lista de componentes de una expresi�n regular
	 * @return �rbol sint�ctico binario de una expresi�n regular
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
			else // Ya existe alg�n nodo creado anteriormente
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
