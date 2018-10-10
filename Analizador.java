import java.util.LinkedList;
import java.util.List;

public class Analizador 
{
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
	 * R�cibe un string que es una expresi�n regular y 
	 * devuelve esa expresi�n regular traducida en forma 
	 * de arbol para que lo entienda el algoritmo
	 * @param expresi�n regular
	 * @param lista de expresiones regulares
	 * @param lista de las macros
	 * @return expresi�n regular parseada en forma de arbol
	 * @throws Exception 
	 */
	public NodoArbol parsear(String re, List<String> listaER, List<String> listaM) throws Exception
	{
		NodoArbol arbol = new NodoArbol(); // Creamos el arbol que vamos a devolver
		arbol.setEsVacio(true); // Lo creamos vac�o
		NodoArbol auxArbol;
		String aux, aux1;
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
				aux = ""; // String donde guardaremos la informaci�n del nodo
				if (rech[i+1] == '^') // Comprobamos si est� negado
				{
					aux+= rech[i+1]; // aux += ^
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
								aux += (char) j;
							i = i+3; // Apuntamos al siguiente caracter de la secuencia
						}
						else // Si no es una secuencia, guardamos el n�mero o la letra
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
					{
						i = i+1; // Apuntamos al siguiente caracter
						if (re.length() == i) // Se acaba la expresi�n regular (no hay cuantificador)
							arbol = arbol.nuevaHoja(aux); // A�adimos solo la hoja con la expresi�n regular
						else if (rech[i] == '+') // Cuantificador +
						{
							arbol = arbol.nuevoHijo("+"); // A�adimos el nodo (que no va a ser hoja)
							arbol = arbol.nuevaHoja(aux); // A�adimos la hoja con la expresi�n regular 
							if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a �l
								arbol = arbol.getPadre();
						}
						else if (rech[i] == '*') // Cuantificador *
						{
							arbol = arbol.nuevoHijo("*"); // A�adimos el nodo (que no va a ser hoja)
							arbol = arbol.nuevaHoja(aux); // A�adimos la hoja con la expresi�n regular
							if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a �l
								arbol = arbol.getPadre();
						}
						else if (rech[i] == '?') // Cuantificador ?
						{
							arbol = arbol.nuevoHijo("?"); // A�adimos el nodo (que no va a ser hoja)
							arbol = arbol.nuevaHoja(aux); // A�adimos la hoja con la expresi�n regular 
							if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a �l
								arbol = arbol.getPadre();
						}
						else // Sin cuantificador
						{
							arbol = arbol.nuevaHoja(aux); // A�adimos solo la hoja con la expresi�n regular
							i = i-1;
						}
						break; // Salimos del while
					}
					// Cualquier otro s�mbolo
					else
					{
						aux += rech[i]; // Guardamos el s�mbolo
						i = i+1;
					}	
				}			
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
					auxArbol = parsear(listaER.get(j), listaER, listaM); // Hacemos recursividad para parsear una macro
				else
					throw new Exception("Lista de ER vac�a o nula"); // Devolvemos una excepci�n
				// A�adimos el arbol de la macro como hijo del arbol act�al
				if (auxArbol == null)
					throw new Exception("Lista auxiliar vac�a o nula"); // Devolvemos una excepci�n
				// Comprobamos qu� hay despu�s de la macro
				i = i+1; // Apuntamos al siguiente caracter
				if (re.length() == i) // Se acaba la expresi�n regular
					arbol = arbol.nuevoArbol(auxArbol); // A�adimos a auxArbol como hijo del arbol act�al
				else if (rech[i] == '+') 
				{
					arbol = arbol.nuevoHijo("+"); // A�adimos el nodo (que no va a ser hoja)
					arbol = arbol.nuevoArbol(auxArbol); // A�adimos a auxArbol como hijo del arbol act�al
					if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a �l
						arbol = arbol.getPadre();
				}
				else if (rech[i] == '*') 
				{
					arbol = arbol.nuevoHijo("*"); // A�adimos el nodo (que no va a ser hoja)
					arbol = arbol.nuevoArbol(auxArbol); // A�adimos a auxArbol como hijo del arbol act�al
					if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a �l
						arbol = arbol.getPadre();
				}
				else if (rech[i] == '?') 
				{
					arbol = arbol.nuevoHijo("?"); // A�adimos el nodo (que no va a ser hoja)
					arbol = arbol.nuevoArbol(auxArbol); // A�adimos a auxArbol como hijo del arbol act�al
					if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a �l
						arbol = arbol.getPadre();
				}
				else
				{
					arbol = arbol.nuevoArbol(auxArbol); // A�adimos a auxArbol como hijo del arbol act�al
					i = i-1;
				}
			}
			// Entramos en el caso en el que se abre un par�ntesis
			else if (rech[i] == '(')
			{
//				aux = "";
//				aux += rech[i];
//				out.add(aux);
			}
			// Se encuentra un par�ntesis cerrado
			else if (rech[i] == ')')
			{
//				aux = "";
//				// Comprobamos qu� hay despu�s del grupo
//				i = i+1; // Apuntamos al siguiente caracter
//				if (re.length() == i) // Se acaba la expresi�n regular
//					aux += "0"; // A�adimos un 0 si despu�s del grupo no hay ni un +, ni un * o ni un ?
//				else if (rech[i] == '+') 
//					aux += "1"; // A�adimos un 1 si despu�s del grupo hay un +
//				else if (rech[i] == '*') 
//					aux += "2"; // A�adimos un 2 si despu�s del grupo hay un *
//				else if (rech[i] == '?') 
//					aux += "3"; // A�adimos un 3 si despu�s del grupo hay un ?
//				else
//				{
//					aux += "0"; // A�adimos un 0 si despu�s del grupo no hay ni un +, ni un * o ni un ?
//					i = i-1;
//				}	
//				out.add(")" + aux);
			}
			// Se encuentra un OR
			else if (rech[i] == '|')
			{
				arbol = arbol.nuevoHijo("|");
			}
			// Se encuentra un "
			else if (rech[i] == '"')
			{
//				aux = "";
//				i = i+1; // Apuntamos al siguiente caracter
//				while (rech[i] != '"') // REPASAR ESTO
//				{
//					aux += rech[i];
//					i = i+1;
//				}
//				// Comprobamos qu� hay despu�s
//				i = i+1; // Apuntamos al siguiente caracter
//				if (re.length() == i) // Se acaba la expresi�n regular
//					aux += "0"; // A�adimos un 0 si despu�s no hay ni un +, ni un * o ni un ?
//				else if (rech[i] == '+') 
//					aux += "1"; // A�adimos un 1 si despu�s hay un +
//				else if (rech[i] == '*') 
//					aux += "2"; // A�adimos un 2 si despu�s hay un *
//				else if (rech[i] == '?') 
//					aux += "3"; // A�adimos un 3 si despu�s hay un ?
//				else
//				{
//					aux += "0"; // A�adimos un 0 si despu�s no hay ni un +, ni un * o ni un ?
//					i = i-1;
//				}
//				out.add(aux);
			}
			else if (rech[i] == ' ')
			{
				// A�adir
			}
			// Cualquier otro caracter
			else
			{
//				aux = "";
//				while (true)
//				{
//					if (re.length() == i) // Se acaba la expresi�n regular
//					{
//						aux += "0"; // A�adimos un 0 si despu�s no hay ni un +, ni un * o ni un ?
//						break;
//					}
//					else if (rech[i] == '+') 
//					{
//						aux += "1"; // A�adimos un 1 si despu�s hay un +
//						break;
//					}
//					else if (rech[i] == '*') 
//					{
//						aux += "2"; // A�adimos un 2 si despu�s hay un *
//						break;
//					}
//					else if (rech[i] == '?') 
//					{
//						aux += "3"; // A�adimos un 3 si despu�s hay un ?
//						break;
//					}
//					else if (rech[i] == ' ')
//					{
//						aux += "0"; // A�adimos un 0 si despu�s hay un espacio
//						i = i-1;
//						break;
//					}
//					else
//					{
//						aux += rech[i];
//						i = i+1;
//					}
//				}
//				out.add(aux);
			}
			i = i+1;
		}
		return arbol;
	}
	/**
	 * Clase auxiliar para un nodo del arbol
	 * @author Luis Herrera
	 *
	 */
	public class NodoArbol
	{
		private NodoArbol padre;
		private List<NodoArbol> hijos;
		private String info;
		private boolean esHoja;
		private boolean esOr;
		private boolean esVacio;
		
		/**
		 * M�todo para a�adir un nuevo hijo a un nodo padre
		 * @param informaci�n del nodo hijo
		 * @param informaci�n de si es hoja o no
		 * @return devolvemos el nodo a�adido
		 */
		public NodoArbol nuevoHijo(String info)
		{
			NodoArbol n = new NodoArbol(); // Nodo para el hijo que vamos a a�adir
			if (this.isEsVacio()) // Si el arbol es vac�o, a�adimos el nodo al act�al
			{
				this.setInfo(info);
				this.setEsHoja(false);
				this.setEsVacio(false);
				return this;
			}
			else if (info == "|" && this.getInfo() != "|") // Tiene que a�adir un or
			{
				n.setInfo(info);
				n.setEsHoja(false);
				this.setPadre(n);
				n.getHijos().add(this);
				return n;
			}
			else if ((this.isEsHoja() || !this.isEsOr()) && (this.getInfo() != ".")) // Se encuentra una concatenaci�n 
			{
				NodoArbol n1 = new NodoArbol(); // Nodo para la concatenaci�n
				// Creamos el nodo concatenaci�n 
				n1.setInfo(".");
				n1.setEsHoja(false);
				// Asignamos n1 como padre de este arbol
				this.setPadre(n1);
				// Creamos el nodo hijo a a�adir
				n.setEsHoja(false);
				n.setInfo(info);
				n.setPadre(n1);
				// A�adimos los hijos a n1
				n1.getHijos().add(this);
				n1.getHijos().add(n);
				// Devolvemos n
				return n;
			}
			else // Se encuentra un or o ya est� la concatenaci�n
			{
				// Creamos el nodo hijo
				n.setEsHoja(false);
				n.setInfo(info);
				n.setPadre(this);
				// A�adimos el hijo a este arbol
				this.getHijos().add(n);
				// Devolvemos n
				return n;
			}
		}
		/**
		 * M�todo para a�adir una hoja a un nodo
		 * @param informaci�n del nodo hoja
		 * @return devolvemos el nodo hoja o su padre
		 */
		public NodoArbol nuevaHoja(String info)
		{
			if (this.isEsVacio()) // Si el arbol es vac�o, a�adimos la informaci�n a este nodo
			{
				// A�adimos la informaci�n
				this.setInfo(info);
				this.setEsHoja(true);
				this.setEsVacio(false);
				return this; // Devolvemos el nodo sobre el que operamos
			}
			else // El arbol no es vac�o, por lo que creamos un nuevo nodo que ser� la hoja
			{
				NodoArbol n = new NodoArbol(); // Nodo para el hijo (hoja) que vamos a a�adir
				// A�adimos la informaci�n
				n.setEsHoja(true);
				n.setInfo(info);
				n.setPadre(this);
				this.getHijos().add(n); // A�adimos la hoja a los hijos de este nodo
				return this; // Devolvemos el nodo padre de la hoja que acabamos de a�adir
			}
		}
		/**
		 * M�todo para a�adir un arbol como hijo de otro
		 * @param arbol a a�adir
		 * @return devolvemos el arbol padre
		 */
		public NodoArbol nuevoArbol(NodoArbol arbol)
		{
			if (this.getInfo() == arbol.getInfo()) // Si ambos son una concatenaci�n
				for (NodoArbol n : arbol.getHijos()) // Recorremos los hijos de arbol
				{
					n.setPadre(this); // Ponemos al actual como padre
					this.getHijos().add(n); // Los a�adimos a su lista de hijos
				}	
			else
			{
				arbol.setPadre(this); // A�adimos el arbol inicial como padre 
				this.getHijos().add(arbol); // A�adimos el arbol a los hijos del inicial
			}
			return this; // Devolvemos el padre
		}
		
		public boolean isEsVacio() {
			return esVacio;
		}
		public void setEsVacio(boolean esVacio) {
			this.esVacio = esVacio;
		}
		public boolean isEsOr() {
			return this.getInfo() == "|";
		}
		public void setEsOr(boolean esOr) {
			this.esOr = esOr;
		}
		public boolean isEsHoja() {
			return this.getHijos() == null;
		}
		public void setEsHoja(boolean esHoja) {
			if (!esHoja)
				this.setHijos(new LinkedList<>());
			this.esHoja = esHoja;
		}
		public NodoArbol getPadre() {
			return padre;
		}
		public void setPadre(NodoArbol padre) {
			this.padre = padre;
		}
		public List<NodoArbol> getHijos() {
			return hijos;
		}
		public void setHijos(List<NodoArbol> hijos) {
			this.hijos = hijos;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
	}
	/**
	 * Clase auxiliar para un nodo
	 * @author herre
	 *
	 */
	private class Nodo
	{
		private List<Arista> aristas;
		private int id;
		private boolean efinal;
	}
	/**
	 * Clase auxiliar para una arista
	 * @author herre
	 *
	 */
	private class Arista
	{
		private Nodo origen;
		private Nodo destino;
		private List<String> condiciones;
	}
	/**
	 * Clase auxiliar para un automata (grafo)
	 * @author herre
	 *
	 */
	private class Automata
	{
		private List<Nodo> afd;
	}
}
