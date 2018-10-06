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
	 * devuelve esa expresión regular traducida para que
	 * lo entienda el algoritmo
	 * @param expresión regular
	 * @param lista de expresiones regulares
	 * @param lista de las macros
	 * @return expresión regular parseada en una lista
	 * @throws Exception 
	 */
	public List<String> parsear(String re, List<String> listaER, List<String> listaM) throws Exception
	{
		List<String> out = new LinkedList<>(); // Lista donde guardaremos las partes de la expresión regular
		List<String> listaAux;
		String aux, aux1;
		char[] rech = re.toCharArray();
		int i = 0;
		int j;
		// Vamos a recorrer el string caracter a caracter
		while (i < re.length()) 
		{	
			// Entramos en el caso en el que se abre un set
			if (rech[i] == '[') 
			{
				aux = "";
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
						if (re.length() == i) // Se acaba la expresión regular
							aux += "0"; // Añadimos un 0 si después del set no hay ni un +, ni un * o ni un ?
						else if (rech[i] == '+') 
							aux += "1"; // Añadimos un 1 si después del set hay un +
						else if (rech[i] == '*') 
							aux += "2"; // Añadimos un 2 si después del set hay un *
						else if (rech[i] == '?') 
							aux += "3"; // Añadimos un 3 si después del set hay un ?
						else
						{
							aux += "0"; // Añadimos un 0 si después del set no hay ni un +, ni un * o ni un ?
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
				out.add(aux);
			}
			// Entramos en el caso en el que se abre una macro
			else if (rech[i] == '{')
			{
				aux1 = "";
				aux = "";
				j = 0;
				while (rech[i] != '}')
				{
					aux1 += rech[i]; // Guardamos en aux1 la macro 
					i = i+1;
				}
				aux1 += rech[i]; // Guardamos también el último '}'
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
					listaAux = parsear(listaER.get(j), listaER, listaM); // Hacemos recursividad para parsear una macro
				else
					throw new Exception("Lista de ER vacía o nula"); // Devolvemos una excepción
				// Añadimos la lista auxiliar a la lista principal más paréntesis
				if (listaAux != null && !listaAux.isEmpty())
				{
					out.add("(");
					for (String a : listaAux)
						out.add(a);
				}
				else
					throw new Exception("Lista auxiliar vacía o nula"); // Devolvemos una excepción
				// Comprobamos qué hay después de la macro
				i = i+1; // Apuntamos al siguiente caracter
				if (re.length() == i) // Se acaba la expresión regular
					aux += "0"; // Añadimos un 0 si después de la macro no hay ni un +, ni un * o ni un ?
				else if (rech[i] == '+') 
					aux += "1"; // Añadimos un 1 si después de la macro hay un +
				else if (rech[i] == '*') 
					aux += "2"; // Añadimos un 2 si después de la macro hay un *
				else if (rech[i] == '?') 
					aux += "3"; // Añadimos un 3 si después de la macro hay un ?
				else
				{
					aux += "0"; // Añadimos un 0 si después de la macro no hay ni un +, ni un * o ni un ?
					i = i-1;
				}
					
				out.add(")" + aux);
			}
			// Entramos en el caso en el que se abre un paréntesis
			else if (rech[i] == '(')
			{
				aux = "";
				aux += rech[i];
				out.add(aux);
			}
			// Se encuentra un paréntesis cerrado
			else if (rech[i] == ')')
			{
				aux = "";
				// Comprobamos qué hay después del grupo
				i = i+1; // Apuntamos al siguiente caracter
				if (re.length() == i) // Se acaba la expresión regular
					aux += "0"; // Añadimos un 0 si después del grupo no hay ni un +, ni un * o ni un ?
				else if (rech[i] == '+') 
					aux += "1"; // Añadimos un 1 si después del grupo hay un +
				else if (rech[i] == '*') 
					aux += "2"; // Añadimos un 2 si después del grupo hay un *
				else if (rech[i] == '?') 
					aux += "3"; // Añadimos un 3 si después del grupo hay un ?
				else
				{
					aux += "0"; // Añadimos un 0 si después del grupo no hay ni un +, ni un * o ni un ?
					i = i-1;
				}
					
				out.add(")" + aux);
			}
			// Se encuentra un OR
			else if (rech[i] == '|')
			{
				aux = "";
				aux += rech[i]; // Añadimos simplemente un |
				out.add(aux);
			}
			// Se encuentra un "
			else if (rech[i] == '\"')
			{
				
			}
			// Cualquier otro caracter
			else
			{
				
			}
			i = i+1;
		}
		return out;
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
