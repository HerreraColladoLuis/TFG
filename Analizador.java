import java.util.LinkedList;
import java.util.List;

public class Analizador 
{
	List<String> listaM;
	List<String> listaER;
	private boolean parentesis = false;
	/**
	   * Recibe una lista con strings correspondientes a macros y parsea
	   * cada uno traduciendolos a un estado común
	   * @param lista con las macros
	   * @return lista con las macros traducidas a un estado común
	   */
	  public List<String> translateMacro(List<String> list)
	  {
		  List<String> out = new LinkedList<>();
		  String aux;
		  for (String cad : list)
		  {
			  aux = "";
			  aux = procesarMacro(cad);
			  if (aux != "")
				  out.add(aux);
		  }
		  return out;
	  }
	  /**
	   * Recibe una lista con strings correspondientes a regex y parsea
	   * cada una traduciendolos a un estado común
	   * @param lista con las regex
	   * @return lista con las regex traducidas a un estado común
	   */
	  public List<String> translateRegex(List<String> list)
	  {
		  List<String> out = new LinkedList<>();
		  String aux;
		  for (String cad : list)
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
	 * @param expresion regular
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
			{
				auxch[j] = rech[j];
			}
			out = String.valueOf(auxch);
		}
		else
		{
			i = le-1;
			while (rech[i] == ' ')
			{
				i--;
			}
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
	 * @param macro
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
	 * Método que recibe una expresión regular en forma de string
	 * y devuelve una lista con sus componentes o null si ya está 
	 * parseada
	 * @param Expresión regular en forma de string
	 * @return Lista de componentes o null
	 * @throws Exception 
	 */
	public List<String> parsear(String re) throws Exception 
	{
		String aux, aux1, aux2;
		List<String> listaAux;
		List<String> out = new LinkedList<>();
		boolean comillas;
		char[] rech = re.toCharArray();
		int i = 0;
		int j;
		int pos;
		// Vamos a recorrer el string caracter a caracter
		while (i < re.length()) 
		{	
			// Entramos en el caso en el que se abre un set
			if (rech[i] == '[') 
			{
				aux = "\""; // String donde guardaremos la información del nodo hoja en este caso
				if (rech[i+1] == '^') // Comprobamos si está negado
				{
					aux+= rech[i+1]; // aux += ^
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
								aux += (char) j;
							i = i+3; // Apuntamos al siguiente caracter de la secuencia
						}
						else // Si no es una secuencia, guardamos el número o la letra
						{
							aux += rech[i];
							i = i+1; // Apuntamos al siguiente caracter de la secuencia
						}
					}
					// Comprobamos si es un caracter de escape \
					else if ((int) rech[i] == 10 || (int) rech[i] == 9 || (int) rech[i] == 13 || (int) rech[i] == 13 ||
							 (int) rech[i] == 12 || (int) rech[i] == 8 || (int) rech[i] == 92 || (int) rech[i] == 39 ||
							 (int) rech[i] == 34)
					{
						aux += rech[i]; // Guardamos el caracter de escape
						i = i+1;
					}
					// Comprobamos si es el final del set
					else if (rech[i] == ']') // Acaba el set
						break; // Salimos del while
					// Cualquier otro símbolo
					else
					{
						aux += rech[i]; // Guardamos el símbolo
						i = i+1;
					}	
				}
				aux += "\"";
				out.add(aux);
			}
			// Entramos en el caso en el que se abre una macro
			else if (rech[i] == '{')
			{
				aux1 = "";
				aux = "";
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
					listaAux = parsear(listaER.get(j)); // Hacemos recursividad para parsear una macro
				}
				else
					throw new Exception("Lista de ER vacía o nula"); // Devolvemos una excepción
				// Añadimos el arbol de la macro como hijo del arbol actúal
				if (listaAux == null)
					throw new Exception("Lista auxiliar vacía o nula"); // Devolvemos una excepción
				else
				{
					aux2 = "";
					if (parentesis)
						aux2 += "(";
					for (String cad : listaAux)
						aux2 += cad;
					if (parentesis)
						aux2 += ")";
					out.add(aux2);
					parentesis = true;
				}
			}
			// Entramos en el caso en el que se abre un paréntesis
			else if (rech[i] == '(')
			{
				aux = "";
				aux2 = "";
				i = i+1;
				comillas = false;
				while (true)
				{
					if (rech[i] == '\"' && !comillas)
						comillas = true;
					if (rech[i] == '\"' && comillas)
						comillas = false;
					if (!comillas && rech[i] == '(')
					{
						i = i+1;
						while (true)
						{
							// PARENTESIS ANIDADOS MIRAR!!!!!!!!!!!!
							aux2 += rech[i];
							i = i+1;
						}
						
					}
					if (!comillas && rech[i] == ')')
						break;
					aux += rech[i]; // Guardamos en aux el contenido del paréntesis
					i = i+1;
				}
				listaAux = parsear(aux);
				if (listaAux == null)
					throw new Exception("Lista auxiliar vacía o nula"); // Devolvemos una excepción
				else
				{
					aux2 = "";
					if (parentesis)
						aux2 += "(";
					for (String cad : listaAux)
						aux2 += cad;
					if (parentesis)
						aux2 += ")";
					out.add(aux2);
					parentesis = true;
				}
			}
			// Se encuentra un OR
			else if (rech[i] == '|')
			{
				out.add("|");
			}
			// Se encuentra un "
			else if (rech[i] == '"')
			{
				aux = "\"";
				i = i+1;
				while (rech[i] != '\"')
				{
					aux += rech[i];
					i = i+1;
				}
				aux += "\"";
				out.add(aux);
			}
			else if (rech[i] == ' ')
			{
				// Añadir
			}
			else if (rech[i] == '+' || rech[i] == '*' || rech[i] == '?')
			{
				out.add(String.valueOf(rech[i]));
			}
			// Cualquier otro caracter
			else
			{
				aux = "";
				while (true)
				{
					if (i == rech.length || rech[i] == '+' || rech[i] == '*' || rech[i] == '?' || rech[i] == ' ')
					{
						i = i-1;
						break;
					}
					aux += rech[i];
					i = i+1;
				}
				out.add(aux);
			}
			i = i+1;
		}
		return out;
	}
	/**
	 * Método que recibe una lista con los componentes de una 
	 * expresión regular y devuelve un árbol sintáctico de la
	 * misma
	 * @param Lista de componentes de una expresión regular
	 * @return Árbol sintáctico de una expresión regular
	 * @throws Exception 
	 */
	public NodoArbol crearArbol(List<String> lExp) throws Exception
	{
		// Punteros auxiliares
		NodoArbol arbol = null;
		NodoArbol nodoAnterior = null;
		NodoArbol nodoActual = null;
		NodoArbol nodoAuxiliar = null;
		List<String> lpar;
		for (String n : lExp) // Recorremos la lista 
		{
			lpar = parsear(n);
			if (lpar == null) // Caso base
				nodoActual = new NodoArbol(n);
			else // Caso recursivo
				nodoActual = crearArbol(lpar);
			
			if (nodoAnterior == null) // Se trata del primer nodo del arbol
			{
				arbol = nodoActual;
				nodoAnterior = nodoActual;
			}
			else
			{
				if (nodoActual.cuantificador == true) // NODO CUANTIFICADOR
				{
					if (nodoAnterior.hoja == true || nodoAnterior.operacion == true)
					{
						if (arbol.operacion == true) // nodoAnterior.padre
						{
							arbol.borrarUltimoHijo();
							nodoAnterior.padre = nodoActual;
							nodoActual.hijos.add(nodoAnterior);
							nodoActual.padre = arbol;
							arbol.hijos.add(nodoActual);
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
				}
				else if (nodoActual.hoja == true) // NODO HOJA
				{
					if (nodoAnterior.hoja == true || nodoAnterior.cuantificador == true)
					{
						if (nodoAnterior.padre != null && nodoAnterior.padre.operacion == true)
						{
							nodoActual.padre = nodoAnterior.padre;
							nodoAnterior.padre.hijos.add(nodoActual);
						}
						else
						{
							nodoAuxiliar = new NodoArbol("c");
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
				else // NODO OPERACIÓN
				{
					nodoAnterior.padre = nodoActual;
					nodoActual.hijos.add(nodoAnterior);
					arbol = nodoActual;
					nodoAnterior = nodoActual;
				}
			}
		}
		return arbol;
	}
	/**
	 * Clase auxiliar para un nodo de un arbol
	 * @author herre
	 *
	 */
	private class NodoArbol
	{
		public boolean hoja = false;
		public boolean cuantificador = false;
		public boolean operacion = false;
		public String info;
		public List<NodoArbol> hijos;
		public NodoArbol padre;
		
		/**
		 * Constructor para un nodo del arbol
		 * @param Información que llevará el nodo
		 */
		public NodoArbol(String info)
		{
			this.info = info;
			if (info == "+" || info == "*" || info == "?")
				this.cuantificador = true;
			else if (info == "|" || info == "c")
				this.operacion = true;
			else
				this.hoja = true;
			if (!this.hoja)
				this.hijos = new LinkedList<>();
		}
		/**
		 * Método para borrar el último hijo añadido a la 
		 * lista de hijos de un nodo
		 */
		public void borrarUltimoHijo()
		{
			if (this.hijos != null)
				this.hijos.remove(this.hijos.size()-1);
		}
	}
}









