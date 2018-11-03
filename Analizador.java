import java.util.LinkedList;
import java.util.List;

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
	 * Método que recibe una lista con los componentes de una 
	 * expresión regular y devuelve un árbol sintáctico de la
	 * misma
	 * @param lExp Lista de componentes de una expresión regular
	 * @return Árbol sintáctico de una expresión regular
	 * @throws Exception 
	 *//*
	public NodoArbol crearArbol(List<String> lExp) throws Exception
	{
		// Variables auxiliares
		NodoArbol arbol = null;
		NodoArbol nodoAnterior = null;
		NodoArbol nodoActual = null;
		NodoArbol nodoAuxiliar = null;
		boolean antRec = false;
		boolean operacionOR = false;
		List<String> lpar;
		for (String n : lExp) // Recorremos la lista 
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
				if (nodoActual.operacion)
					antRec = true;
			}	
			if (nodoAnterior == null) // Se trata del primer nodo del arbol
			{
				arbol = nodoActual;
				nodoAnterior = nodoActual;
			}
			else // No es el primer nodo del arbol
			{
				if (nodoActual.cuantificador) // NODO CUANTIFICADOR
				{
					if (nodoActual.hijos == null || nodoActual.hijos.size() == 0) // X
					{
						if (nodoAnterior.hoja || nodoAnterior.operacion)
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion) // nodoAnterior.padre
							{
								nodoAnterior.padre.borrarUltimoHijo();
								nodoActual.padre = nodoAnterior.padre;
								nodoActual.hijos.add(nodoAnterior);
								nodoAnterior.padre.hijos.add(nodoActual);
								nodoAnterior.padre = nodoActual;
								nodoAnterior = nodoActual;
							}
							else
							{
								nodoAnterior.padre = nodoActual;
								nodoActual.hijos.add(nodoAnterior);
								arbol = nodoActual;
								nodoAnterior = nodoActual;
							}
						}
						else // XX
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion)
							{
								nodoActual.padre = nodoAnterior.padre;
								nodoAnterior.padre.hijos.add(nodoActual);
								nodoAnterior = nodoActual; // CAMBIO (nodoAnterior = arbol)
							}
							else
							{
								nodoAuxiliar = new NodoArbol(".");
								nodoAnterior.padre = nodoAuxiliar;
								nodoActual.padre = nodoAuxiliar;
								nodoAuxiliar.hijos.add(nodoAnterior);
								nodoAuxiliar.hijos.add(nodoActual);
								arbol = nodoAuxiliar;
								nodoAnterior = nodoActual;
							}
						} // XX
					} // X
					else
					{
						if (nodoAnterior.operacion) // XXXXX
						{
							nodoActual.padre = nodoAnterior;
							nodoAnterior.hijos.add(nodoActual);
							arbol = nodoAnterior;
							nodoAnterior = nodoActual;
						}
						else if (nodoAnterior.padre != null && nodoAnterior.padre.operacion)
						{
							nodoActual.padre = nodoAnterior.padre;
							nodoAnterior.padre.hijos.add(nodoActual);
							nodoAnterior = nodoActual; // CAMBIO (nodoAnterior = arbol)
						}
						else
						{
							nodoAuxiliar = new NodoArbol(".");
							nodoAnterior.padre = nodoAuxiliar;
							nodoActual.padre = nodoAuxiliar;
							nodoAuxiliar.hijos.add(nodoAnterior);
							nodoAuxiliar.hijos.add(nodoActual);
							arbol = nodoAuxiliar;
							nodoAnterior = nodoActual;
						}
					}
				}
				else if (nodoActual.hoja) // NODO HOJA
				{
					if (nodoAnterior.hoja || nodoAnterior.cuantificador)
					{
						if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("."))
						{
							nodoActual.padre = nodoAnterior.padre;
							nodoAnterior.padre.hijos.add(nodoActual);
							nodoAnterior = nodoActual; // CAMBIO (nodoAnterior = arbol)
						}
						else if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("|"))
						{
							nodoAuxiliar = new NodoArbol(".");
							nodoAnterior.padre.borrarUltimoHijo();
							nodoAuxiliar.hijos.add(nodoAnterior);
							nodoAuxiliar.hijos.add(nodoActual);
							nodoAnterior.padre.hijos.add(nodoAuxiliar);
							nodoAuxiliar.padre = nodoAnterior.padre;
							arbol = nodoAnterior.padre;
							nodoAnterior.padre = nodoAuxiliar;
							nodoActual.padre = nodoAuxiliar;
							nodoAnterior = nodoActual;
						}
						else
						{
							nodoAuxiliar = new NodoArbol(".");
							nodoAnterior.padre = nodoAuxiliar;
							nodoActual.padre = nodoAuxiliar;
							nodoAuxiliar.hijos.add(nodoAnterior);
							nodoAuxiliar.hijos.add(nodoActual);
							arbol = nodoAuxiliar;
							nodoAnterior = nodoActual;
						}
					}
					else
					{
						// CAMBIO
						if (antRec)
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("."))
							{
								nodoActual.padre = nodoAnterior.padre;
								nodoAnterior.padre.hijos.add(nodoActual);
								nodoAnterior = nodoActual; 
							}
							else if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("|"))
							{
								nodoAuxiliar = new NodoArbol(".");
								nodoAnterior.padre.borrarUltimoHijo();
								nodoAuxiliar.hijos.add(nodoAnterior);
								nodoAuxiliar.hijos.add(nodoActual);
								nodoAnterior.padre.hijos.add(nodoAuxiliar);
								nodoAuxiliar.padre = nodoAnterior.padre;
								arbol = nodoAnterior.padre;
								nodoAnterior.padre = nodoAuxiliar;
								nodoActual.padre = nodoAuxiliar;
								nodoAnterior = nodoActual;
							}
							else if (nodoAnterior.padre == null)
							{
								nodoAuxiliar = new NodoArbol(".");
								nodoAnterior.padre = nodoAuxiliar;
								nodoActual.padre = nodoAuxiliar;
								nodoAuxiliar.hijos.add(nodoAnterior);
								nodoAuxiliar.hijos.add(nodoActual);
								arbol = nodoAuxiliar;
								nodoAnterior = nodoActual;
							}
							operacionOR = false; // AÑADIDO
							antRec = false;
						}
						else
						{
							nodoActual.padre = nodoAnterior;
							nodoAnterior.hijos.add(nodoActual);
							arbol = nodoAnterior;
							nodoAnterior = nodoActual;
							operacionOR = false; //////////
						}
					}
				}
				else // NODO OPERACIÓN
				{
					if (nodoActual.hijos.size() > 0)
					{
						if (nodoAnterior.hoja || nodoAnterior.cuantificador)
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("."))
							{
								nodoActual.padre = nodoAnterior.padre;
								nodoAnterior.padre.hijos.add(nodoActual);
								nodoAnterior = nodoActual; 
							}
							else if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("|"))
							{
								nodoAuxiliar = new NodoArbol(".");
								nodoAnterior.padre.borrarUltimoHijo();
								nodoAuxiliar.hijos.add(nodoAnterior);
								nodoAuxiliar.hijos.add(nodoActual);
								nodoAnterior.padre.hijos.add(nodoAuxiliar);
								nodoAuxiliar.padre = nodoAnterior.padre;
								arbol = nodoAnterior.padre;
								nodoAnterior.padre = nodoAuxiliar;
								nodoActual.padre = nodoAuxiliar;
								nodoAnterior = nodoActual;
							}
							else
							{
								nodoAuxiliar = new NodoArbol(".");
								nodoAnterior.padre = nodoAuxiliar;
								nodoActual.padre = nodoAuxiliar;
								nodoAuxiliar.hijos.add(nodoAnterior);
								nodoAuxiliar.hijos.add(nodoActual);
								arbol = nodoAuxiliar;
								nodoAnterior = nodoActual; 
							}
						}
						else
						{
							if (nodoAnterior.hijos.size() > 0) // AÑADIDO
							{ // AÑADIDOX2
								if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("."))
								{
									nodoActual.padre = nodoAnterior.padre;
									nodoAnterior.padre.hijos.add(nodoActual);
									nodoAnterior = nodoActual; 
								}
								else if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("|"))
								{
									nodoAuxiliar = new NodoArbol(".");
									nodoAnterior.padre.borrarUltimoHijo();
									nodoAuxiliar.hijos.add(nodoAnterior);
									nodoAuxiliar.hijos.add(nodoActual);
									nodoAnterior.padre.hijos.add(nodoAuxiliar);
									nodoAuxiliar.padre = nodoAnterior.padre;
									arbol = nodoAnterior.padre;
									nodoAnterior.padre = nodoAuxiliar;
									nodoActual.padre = nodoAuxiliar;
									nodoAnterior = nodoActual;
								}
								else if (operacionOR)
								{
									nodoActual.padre = nodoAnterior;
									nodoAnterior.hijos.add(nodoActual);
									arbol = nodoAnterior;
									nodoAnterior = nodoActual;
									operacionOR = false;
								}
								else
								{
									nodoAuxiliar = new NodoArbol(".");
									nodoAnterior.padre = nodoAuxiliar;
									nodoActual.padre = nodoAuxiliar;
									nodoAuxiliar.hijos.add(nodoAnterior);
									nodoAuxiliar.hijos.add(nodoActual);
									arbol = nodoAuxiliar;
									nodoAnterior = nodoActual; 
								}
							}
							else
							{
								nodoActual.padre = nodoAnterior;
								nodoAnterior.hijos.add(nodoActual);
								arbol = nodoAnterior;
								nodoAnterior = nodoActual;
							}
						}
					}
					else
					{
						if (antRec && nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("|"))
						{
							nodoAnterior = nodoAnterior.padre;
							operacionOR = true; // AÑADIDO
							antRec = false;
						}
						else if (antRec && nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("."))
						{
							nodoActual.hijos.add(nodoAnterior.padre);
							nodoAnterior.padre.padre = nodoActual;
							arbol = nodoActual;
							nodoAnterior = nodoActual;
							antRec = false;
							operacionOR = true;
						}
						else if (antRec && nodoAnterior.padre == null)
						{
							nodoAnterior.padre = nodoActual;
							nodoActual.hijos.add(nodoAnterior);
							arbol = nodoActual;
							nodoAnterior = nodoActual; //
							operacionOR = true; // AÑADIDO
							antRec = false;
						}
						else
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals(".")) // Añadido
							{
								if (nodoAnterior.padre.padre == null)
								{
									nodoAnterior.padre.padre = nodoActual;
									nodoActual.hijos.add(nodoAnterior.padre);
									arbol = nodoActual;
									nodoAnterior = nodoActual;
									operacionOR = true;
								}
								else
								{
									nodoAnterior = nodoAnterior.padre.padre;
									operacionOR = true; ///////////////////
								}
								//nodoAnterior = nodoAnterior.padre;
							}
							else if (nodoAnterior.padre != null && nodoAnterior.padre.operacion && nodoAnterior.padre.info.equals("|"))
							{
								nodoAnterior = nodoAnterior.padre;
								operacionOR = true;
							}
							else
							{
								nodoAnterior.padre = nodoActual;
								nodoActual.hijos.add(nodoAnterior);
								arbol = nodoActual;
								nodoAnterior = nodoActual;
								operacionOR = true;
							}
						}
					}
				}
			}
		}
		return arbol;
	}
	*//**
	 * Clase auxiliar para un nodo de un arbol
	 * @author herre
	 *
	 *//*
	public class NodoArbol
	{
		public boolean hoja = false;
		public boolean cuantificador = false;
		public boolean operacion = false;
		public String info;
		public List<NodoArbol> hijos;
		public NodoArbol padre;
		public int posicion = 0;
		
		*//**
		 * Constructor para un nodo del arbol
		 * @param info información que llevará el nodo
		 *//*
		public NodoArbol(String info)
		{
			this.info = info;
			if (info.equals("+") || info.equals("*") || info.equals("?"))
				this.cuantificador = true;
			else if (info.equals("|") || info.equals("."))
				this.operacion = true;
			else
				this.hoja = true;
			if (!this.hoja)
				this.hijos = new LinkedList<>();
		}
		*//**
		 * Método para borrar el último hijo añadido a la 
		 * lista de hijos de un nodo
		 *//*
		public void borrarUltimoHijo()
		{
			if (this.hijos != null)
				this.hijos.remove(this.hijos.size()-1);
		}
	}*/
	public void preOrden(NodoArbol nodo)
	{
		if (nodo != null)
		{
			System.out.print(nodo.info);
			preOrden(nodo.hijoIzdo);
			preOrden(nodo.hijoDcho);
		}
	}
	
	public void inOrden(NodoArbol nodo)
	{
		if (nodo != null)
		{
			inOrden(nodo.hijoIzdo);
			System.out.print(nodo.info);
			inOrden(nodo.hijoDcho);
		}
	}
	
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
	
	public NodoArbol crearArbol(List<String> lExp) throws Exception
	{
		NodoArbol raiz = null;
		NodoArbol nodoAnterior = null;
		NodoArbol nodoActual = null;
		NodoArbol nodoAuxiliar = null;
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
			if (raiz == null) // Se trata del primer nodo del arbol
			{
				raiz = nodoActual;
				nodoAnterior = nodoActual;
			}
			else
			{
				if (nodoActual.hoja)
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
							if (nodoAnterior.hijoDcho.hoja && nodoAnterior.hijoIzdo.hoja)
							{
								nodoAuxiliar = new NodoArbol(".");	
								nodoAuxiliar.insertarIzda(nodoAnterior);
								nodoAuxiliar.insertarDcha(nodoActual);
								nodoAnterior = nodoActual;
								raiz = nodoAuxiliar;
							}	
						}	
					}
					else if (nodoAnterior.hoja)
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
							nodoAnterior = nodoAuxiliar;
							raiz = nodoAuxiliar;
						}
					}
				}
				else if (nodoActual.info.equals("|"))
				{
					if (nodoAnterior.hoja)
					{
						if (nodoAnterior.padre != null)
						{
							if (nodoAnterior.padre.info.equals("|"))
							{
								nodoAnterior.padre.hijoDcho = null;
								nodoAnterior.padre.insertarDcha(nodoActual);
								nodoActual.insertarIzda(nodoAnterior);
								nodoAnterior = nodoActual;
							}
							else if (nodoAnterior.padre.info.equals("."))
							{
								if (nodoAnterior.padre.padre != null)
								{
									nodoAuxiliar = nodoAnterior.padre.padre;
									nodoAuxiliar.hijoDcho = null;
									nodoActual.insertarIzda(nodoAnterior.padre);
									nodoAuxiliar.insertarDcha(nodoActual);
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
						else
						{
							nodoActual.insertarIzda(nodoAnterior);
							nodoAnterior = nodoActual;
							raiz = nodoActual;
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
								if (nodoAnterior.padre.info.equals("|"))
								{
									nodoAnterior.padre.hijoDcho = null;
									nodoAnterior.padre.insertarDcha(nodoActual);
									nodoActual.insertarIzda(nodoAnterior);
									nodoAnterior = nodoActual;
								}
							}
						}
					}
				}
			}
		}
		return raiz;
	}
}
