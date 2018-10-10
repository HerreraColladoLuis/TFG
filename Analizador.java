import java.util.LinkedList;
import java.util.List;

public class Analizador 
{
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
	 * Récibe un string que es una expresión regular y 
	 * devuelve esa expresión regular traducida en forma 
	 * de arbol para que lo entienda el algoritmo
	 * @param expresión regular
	 * @param lista de expresiones regulares
	 * @param lista de las macros
	 * @return expresión regular parseada en forma de arbol
	 * @throws Exception 
	 */
	public NodoArbol parsear(String re, List<String> listaER, List<String> listaM) throws Exception
	{
		NodoArbol arbol = new NodoArbol(); // Creamos el arbol que vamos a devolver
		arbol.setEsVacio(true); // Lo creamos vacío
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
				aux = ""; // String donde guardaremos la información del nodo
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
					{
						i = i+1; // Apuntamos al siguiente caracter
						if (re.length() == i) // Se acaba la expresión regular (no hay cuantificador)
							arbol = arbol.nuevaHoja(aux); // Añadimos solo la hoja con la expresión regular
						else if (rech[i] == '+') // Cuantificador +
						{
							arbol = arbol.nuevoHijo("+"); // Añadimos el nodo (que no va a ser hoja)
							arbol = arbol.nuevaHoja(aux); // Añadimos la hoja con la expresión regular 
							if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a él
								arbol = arbol.getPadre();
						}
						else if (rech[i] == '*') // Cuantificador *
						{
							arbol = arbol.nuevoHijo("*"); // Añadimos el nodo (que no va a ser hoja)
							arbol = arbol.nuevaHoja(aux); // Añadimos la hoja con la expresión regular
							if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a él
								arbol = arbol.getPadre();
						}
						else if (rech[i] == '?') // Cuantificador ?
						{
							arbol = arbol.nuevoHijo("?"); // Añadimos el nodo (que no va a ser hoja)
							arbol = arbol.nuevaHoja(aux); // Añadimos la hoja con la expresión regular 
							if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a él
								arbol = arbol.getPadre();
						}
						else // Sin cuantificador
						{
							arbol = arbol.nuevaHoja(aux); // Añadimos solo la hoja con la expresión regular
							i = i-1;
						}
						break; // Salimos del while
					}
					// Cualquier otro símbolo
					else
					{
						aux += rech[i]; // Guardamos el símbolo
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
					auxArbol = parsear(listaER.get(j), listaER, listaM); // Hacemos recursividad para parsear una macro
				else
					throw new Exception("Lista de ER vacía o nula"); // Devolvemos una excepción
				// Añadimos el arbol de la macro como hijo del arbol actúal
				if (auxArbol == null)
					throw new Exception("Lista auxiliar vacía o nula"); // Devolvemos una excepción
				// Comprobamos qué hay después de la macro
				i = i+1; // Apuntamos al siguiente caracter
				if (re.length() == i) // Se acaba la expresión regular
					arbol = arbol.nuevoArbol(auxArbol); // Añadimos a auxArbol como hijo del arbol actúal
				else if (rech[i] == '+') 
				{
					arbol = arbol.nuevoHijo("+"); // Añadimos el nodo (que no va a ser hoja)
					arbol = arbol.nuevoArbol(auxArbol); // Añadimos a auxArbol como hijo del arbol actúal
					if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a él
						arbol = arbol.getPadre();
				}
				else if (rech[i] == '*') 
				{
					arbol = arbol.nuevoHijo("*"); // Añadimos el nodo (que no va a ser hoja)
					arbol = arbol.nuevoArbol(auxArbol); // Añadimos a auxArbol como hijo del arbol actúal
					if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a él
						arbol = arbol.getPadre();
				}
				else if (rech[i] == '?') 
				{
					arbol = arbol.nuevoHijo("?"); // Añadimos el nodo (que no va a ser hoja)
					arbol = arbol.nuevoArbol(auxArbol); // Añadimos a auxArbol como hijo del arbol actúal
					if (arbol.getPadre() != null) // Si el padre es distinto de null, apuntamos a él
						arbol = arbol.getPadre();
				}
				else
				{
					arbol = arbol.nuevoArbol(auxArbol); // Añadimos a auxArbol como hijo del arbol actúal
					i = i-1;
				}
			}
			// Entramos en el caso en el que se abre un paréntesis
			else if (rech[i] == '(')
			{
//				aux = "";
//				aux += rech[i];
//				out.add(aux);
			}
			// Se encuentra un paréntesis cerrado
			else if (rech[i] == ')')
			{
//				aux = "";
//				// Comprobamos qué hay después del grupo
//				i = i+1; // Apuntamos al siguiente caracter
//				if (re.length() == i) // Se acaba la expresión regular
//					aux += "0"; // Añadimos un 0 si después del grupo no hay ni un +, ni un * o ni un ?
//				else if (rech[i] == '+') 
//					aux += "1"; // Añadimos un 1 si después del grupo hay un +
//				else if (rech[i] == '*') 
//					aux += "2"; // Añadimos un 2 si después del grupo hay un *
//				else if (rech[i] == '?') 
//					aux += "3"; // Añadimos un 3 si después del grupo hay un ?
//				else
//				{
//					aux += "0"; // Añadimos un 0 si después del grupo no hay ni un +, ni un * o ni un ?
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
//				// Comprobamos qué hay después
//				i = i+1; // Apuntamos al siguiente caracter
//				if (re.length() == i) // Se acaba la expresión regular
//					aux += "0"; // Añadimos un 0 si después no hay ni un +, ni un * o ni un ?
//				else if (rech[i] == '+') 
//					aux += "1"; // Añadimos un 1 si después hay un +
//				else if (rech[i] == '*') 
//					aux += "2"; // Añadimos un 2 si después hay un *
//				else if (rech[i] == '?') 
//					aux += "3"; // Añadimos un 3 si después hay un ?
//				else
//				{
//					aux += "0"; // Añadimos un 0 si después no hay ni un +, ni un * o ni un ?
//					i = i-1;
//				}
//				out.add(aux);
			}
			else if (rech[i] == ' ')
			{
				// Añadir
			}
			// Cualquier otro caracter
			else
			{
//				aux = "";
//				while (true)
//				{
//					if (re.length() == i) // Se acaba la expresión regular
//					{
//						aux += "0"; // Añadimos un 0 si después no hay ni un +, ni un * o ni un ?
//						break;
//					}
//					else if (rech[i] == '+') 
//					{
//						aux += "1"; // Añadimos un 1 si después hay un +
//						break;
//					}
//					else if (rech[i] == '*') 
//					{
//						aux += "2"; // Añadimos un 2 si después hay un *
//						break;
//					}
//					else if (rech[i] == '?') 
//					{
//						aux += "3"; // Añadimos un 3 si después hay un ?
//						break;
//					}
//					else if (rech[i] == ' ')
//					{
//						aux += "0"; // Añadimos un 0 si después hay un espacio
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
		 * Método para añadir un nuevo hijo a un nodo padre
		 * @param información del nodo hijo
		 * @param información de si es hoja o no
		 * @return devolvemos el nodo añadido
		 */
		public NodoArbol nuevoHijo(String info)
		{
			NodoArbol n = new NodoArbol(); // Nodo para el hijo que vamos a añadir
			if (this.isEsVacio()) // Si el arbol es vacío, añadimos el nodo al actúal
			{
				this.setInfo(info);
				this.setEsHoja(false);
				this.setEsVacio(false);
				return this;
			}
			else if (info == "|" && this.getInfo() != "|") // Tiene que añadir un or
			{
				n.setInfo(info);
				n.setEsHoja(false);
				this.setPadre(n);
				n.getHijos().add(this);
				return n;
			}
			else if ((this.isEsHoja() || !this.isEsOr()) && (this.getInfo() != ".")) // Se encuentra una concatenación 
			{
				NodoArbol n1 = new NodoArbol(); // Nodo para la concatenación
				// Creamos el nodo concatenación 
				n1.setInfo(".");
				n1.setEsHoja(false);
				// Asignamos n1 como padre de este arbol
				this.setPadre(n1);
				// Creamos el nodo hijo a añadir
				n.setEsHoja(false);
				n.setInfo(info);
				n.setPadre(n1);
				// Añadimos los hijos a n1
				n1.getHijos().add(this);
				n1.getHijos().add(n);
				// Devolvemos n
				return n;
			}
			else // Se encuentra un or o ya está la concatenación
			{
				// Creamos el nodo hijo
				n.setEsHoja(false);
				n.setInfo(info);
				n.setPadre(this);
				// Añadimos el hijo a este arbol
				this.getHijos().add(n);
				// Devolvemos n
				return n;
			}
		}
		/**
		 * Método para añadir una hoja a un nodo
		 * @param información del nodo hoja
		 * @return devolvemos el nodo hoja o su padre
		 */
		public NodoArbol nuevaHoja(String info)
		{
			if (this.isEsVacio()) // Si el arbol es vacío, añadimos la información a este nodo
			{
				// Añadimos la información
				this.setInfo(info);
				this.setEsHoja(true);
				this.setEsVacio(false);
				return this; // Devolvemos el nodo sobre el que operamos
			}
			else // El arbol no es vacío, por lo que creamos un nuevo nodo que será la hoja
			{
				NodoArbol n = new NodoArbol(); // Nodo para el hijo (hoja) que vamos a añadir
				// Añadimos la información
				n.setEsHoja(true);
				n.setInfo(info);
				n.setPadre(this);
				this.getHijos().add(n); // Añadimos la hoja a los hijos de este nodo
				return this; // Devolvemos el nodo padre de la hoja que acabamos de añadir
			}
		}
		/**
		 * Método para añadir un arbol como hijo de otro
		 * @param arbol a añadir
		 * @return devolvemos el arbol padre
		 */
		public NodoArbol nuevoArbol(NodoArbol arbol)
		{
			if (this.getInfo() == arbol.getInfo()) // Si ambos son una concatenación
				for (NodoArbol n : arbol.getHijos()) // Recorremos los hijos de arbol
				{
					n.setPadre(this); // Ponemos al actual como padre
					this.getHijos().add(n); // Los añadimos a su lista de hijos
				}	
			else
			{
				arbol.setPadre(this); // Añadimos el arbol inicial como padre 
				this.getHijos().add(arbol); // Añadimos el arbol a los hijos del inicial
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
