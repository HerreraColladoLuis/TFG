import java.util.LinkedList;
import java.util.List;

public class Analizador 
{
	List<String> listaM;
	List<String> listaER;
	/**
	   * Recibe una lista con strings correspondientes a macros y parsea
	   * cada uno traduciendolos a un estado com�n
	   * @param lista con las macros
	   * @return lista con las macros traducidas a un estado com�n
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
	   * cada una traduciendolos a un estado com�n
	   * @param lista con las regex
	   * @return lista con las regex traducidas a un estado com�n
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
	 * M�todo para traducir una expresi�n regular a un formato
	 * que pueda reconocer el algoritmo
	 * @param String de la expresi�n
	 * @return String de la expresi�n traducido
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
					if (i == rech.length || rech[i] == '+' || rech[i] == '*' || rech[i] == '?' || rech[i] == ' ' || rech[i] == ')')
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
	 * M�todo que recibe una expresi�n regular en forma de string
	 * y devuelve una lista con sus componentes o null si ya est� 
	 * parseada
	 * @param Expresi�n regular en forma de string
	 * @return Lista de componentes o null
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
		int cont = 0;
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
				cont++;
			}
			else if (rech[i] == '+' || rech[i] == '*' || rech[i] == '?')
			{
				nuevo = "";
				nuevo += rech[i];
				lComp.add(nuevo);
				cont++;
			}
			else if (rech[i] == '(')
			{
				anidado = 0;
				nuevo = "";
				i = i+1;
				while (true)
				{
					if (rech[i] == '\"' && !com)
						com = true;
					else if (rech[i] == '\"' && com)
						com = false;
					else if (rech[i] == '(' && !com)
						anidado = anidado+1;
					else if (rech[i] == ')' && !com && anidado == 0)
						break;
					else if (rech[i] == ')' && !com && anidado > 0)
						anidado = anidado-1;
					nuevo += rech[i];
					i = i+1;
				}
				lComp.add(nuevo);
				cont++;
			}
			else if (rech[i] == '|')
			{
				nuevo = "";
				nuevo += rech[i];
				lComp.add(nuevo);
				cont++;
			}
			i = i+1;
		}
		if (cont == 1)
			return null;
		else
			return lComp;
	}
	/**
	 * M�todo que recibe una lista con los componentes de una 
	 * expresi�n regular y devuelve un �rbol sint�ctico de la
	 * misma
	 * @param Lista de componentes de una expresi�n regular
	 * @return �rbol sint�ctico de una expresi�n regular
	 * @throws Exception 
	 */
	public NodoArbol crearArbol(List<String> lExp) throws Exception
	{
		// Punteros auxiliares
		NodoArbol arbol = null;
		NodoArbol nodoAnterior = null;
		NodoArbol nodoActual = null;
		NodoArbol nodoAuxiliar = null;
		boolean antRec = false;
		List<String> lpar;
		for (String n : lExp) // Recorremos la lista 
		{
			lpar = parsear(n);
			if (lpar == null) // Caso base
				nodoActual = new NodoArbol(n);
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
			else
			{
				if (nodoActual.cuantificador == true) // NODO CUANTIFICADOR
				{
					if (nodoAnterior.hoja == true || nodoAnterior.operacion == true)
					{
						if (arbol.operacion == true && nodoAnterior.padre != null) // nodoAnterior.padre
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
							nodoAnterior = arbol;
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
						if (antRec)
						{
							nodoAnterior = nodoAnterior.padre;
							antRec = false;
						}
						nodoActual.padre = nodoAnterior;
						nodoAnterior.hijos.add(nodoActual);
						arbol = nodoAnterior;
						nodoAnterior = nodoActual;
					}
				}
				else // NODO OPERACI�N
				{
					if (nodoActual.hijos.size() > 0)
					{
						if (nodoAnterior.hoja == true || nodoAnterior.cuantificador == true)
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion == true)
							{
								nodoActual.padre = nodoAnterior.padre;
								nodoAnterior.padre.hijos.add(nodoActual);
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
							nodoActual.padre = nodoAnterior;
							nodoAnterior.hijos.add(nodoActual);
							nodoAnterior = nodoActual;
						}
					}
					else
					{
						if (antRec && nodoAnterior.padre != null && nodoAnterior.padre.operacion == true)
						{
							nodoAnterior = nodoAnterior.padre;
							antRec = false;
						}
						else if (antRec && nodoAnterior.padre == null)
						{
							nodoAnterior.padre = nodoActual;
							nodoActual.hijos.add(nodoAnterior);
							arbol = nodoActual;
							nodoAnterior = nodoActual;
							antRec = false;
						}
						else
						{
							if (nodoAnterior.padre != null && nodoAnterior.padre.operacion == true)
								nodoAnterior = nodoAnterior.padre;
							else
							{
								nodoAnterior.padre = nodoActual;
								nodoActual.hijos.add(nodoAnterior);
								arbol = nodoActual;
								nodoAnterior = nodoActual;
							}
						}
					}
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
		 * @param Informaci�n que llevar� el nodo
		 */
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
		/**
		 * M�todo para borrar el �ltimo hijo a�adido a la 
		 * lista de hijos de un nodo
		 */
		public void borrarUltimoHijo()
		{
			if (this.hijos != null)
				this.hijos.remove(this.hijos.size()-1);
		}
		
		public List<String> inOrden()
		{
			return null;
		}
	}
}









