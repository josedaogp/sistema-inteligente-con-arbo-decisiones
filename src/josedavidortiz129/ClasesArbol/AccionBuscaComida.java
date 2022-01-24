package josedavidortiz129.ClasesArbol;

import java.util.List;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.AStar;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;

public class AccionBuscaComida extends NodoArbol {

	private Tablero tablero;
	
	/*
	 * Constructor para actualizar la info del tablero del mapa
	 */
	public AccionBuscaComida(Tablero tablero) {
		this.tablero=tablero;
	}
	
	/*
	 *  El m�todo, al ser este un nodo Hoja, tomar� la decisi�n correspondiente a buscar comida.
	 *  Para ello, se calcula la posici�n de la comida m�s cercana y se calcula el camino m�nimo
	 *  hasta esa comida.
	 *  
	 *  */
	public Types.ACTIONS elige(){
		
			//Obtengo el �guila m�s cercana para usarla en el A*
			Node AguilaCercana=AguilaBlancaMasCercana();
			AStar aEstrella = new AStar(tablero.getFilas(), tablero.getColumnas(),
							tablero.getInicial(), AguilaCercana); // Las filas y columnas est�n predeclaradas en la clase como final.
				
			//Se crean los bloques del A*
			aEstrella.setBlocks(tablero.getBloques());
				
			//aEstrella.pintarmapa();
			
			//Se calcula la ruta
			List<Node> ruta=aEstrella.findPath();
			
			//Si ha encontrado un camino...
			if (ruta.size()>0) {
				Node n = ruta.get(1);
				int filaN=n.getRow();
				int columN=n.getCol();
				
				//La siguiente funci�n calcula la acci�n a tomar
				return accionatomar(tablero.getInicial().getCol(), tablero.getInicial().getRow(),columN, filaN);
				
			}else return Types.ACTIONS.ACTION_NIL; //Si no encuentra camino, no hace nada
		
	}
	
	/*
	 * M�todo que calcula el �guila m�s cercana a nuestra posici�n.
	 * Para ello se utiliza la distancia manhattan (en el m�todo calcularDistancia).
	 * Aunque ser�a mejor utilizar un A* por cada posici�n, para reducir tiempos de 
	 * calculo se ha utilizado esa distancia manhattan, que sigue siendo v�lida y 
	 * requiere mucho menos tiempo.
	 */
	public Node AguilaBlancaMasCercana() {
		int min=Integer.MAX_VALUE;
		int coste;
		Node AguilaCercana=new Node(0,0);
		
		for (int i = 0; i < tablero.getComida().size(); i++) {
			
			Node finalNode=tablero.getComida().get(i);
			/*System.out.println();
			System.out.println("COMIDA: "+finalNode);
			System.out.println();*/
			 coste =calcularDistancia(tablero.getInicial(), finalNode) ;
			 
			 if (coste<=min) {
				min=coste;
				
				AguilaCercana=new Node(finalNode.getRow(), finalNode.getCol());
				//System.out.println("Coste: "+coste+" del aguila: "+AguilaCercana);
			}
		}

		return AguilaCercana;
	}
	
	
}
