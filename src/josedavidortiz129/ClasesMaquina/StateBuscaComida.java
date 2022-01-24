package josedavidortiz129.ClasesMaquina;

import java.util.ArrayList;
import java.util.List;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.AStar;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;
import ontology.Types.ACTIONS;

public class StateBuscaComida extends State{

	public StateBuscaComida(Tablero tab) {
		super(tab);
	}

	/*
	 * Este método devolverá la acción necesaria para llegar buscar comida y comer.
	 */
	@Override
	public ACTIONS getAction() {
		
		//Obtengo el águila más cercana para usarla en el A*
		Node AguilaCercana=AguilaBlancaMasCercana();
		AStar aEstrella = new AStar(tablero.getFilas(), tablero.getColumnas(),
				tablero.getInicial(), AguilaCercana); // Las filas y columnas están predeclaradas en la clase como final.
		
		//Se crea los bloques del A* (Los bloques estarán definidos en el tablero)
		
		aEstrella.setBlocks(tablero.getBloques());
		
		//aEstrella.pintarmapa();
		
		//Se calcula la ruta
		List<Node> ruta=aEstrella.findPath();
	
		//Si ha encontrado un camino...
		if (ruta.size()>0) {
			Node n = ruta.get(1);
			int filaN=n.getRow();
			int columN=n.getCol();
			
			//La siguiente función calcula la acción a tomar para llegar al destino
			return accionatomar(tablero.getInicial().getCol(), tablero.getInicial().getRow(),columN, filaN);
			
		}else return Types.ACTIONS.ACTION_NIL; //Si no encuentra camino, no hace nada
	}

	/*
	 * Este método devuelve una lista con las transiciones que salen de este estado.
	 * La única que se tiene es la transición de si hay algún enemigo cerca.
	 */
	@Override
	public ArrayList<Transition> getTransitions() {
		ArrayList<Transition> Transiciones = new ArrayList<Transition>();
		Transiciones.add(new TransitionEnemigoCerca(tablero));
		return Transiciones;
	}
	
	
	/*
	 * Método que calcula el águila más cercana a nuestra posición.
	 * Para ello se utiliza la distancia manhattan (en el método calcularDistancia).
	 * Aunque sería mejor utilizar un A* por cada posición, para reducir tiempos de 
	 * calculo se ha utilizado esa distancia manhattan, que sigue siendo válida y 
	 * requiere mucho menos tiempo.
	 */
	public Node AguilaBlancaMasCercana() {
		int min=Integer.MAX_VALUE;
		int coste;
		Node AguilaCercana=new Node(0,0);
		
		//Para cada candidato a comida cercana, se mira la distancia y finalmente se selecciona la que menor distancia tenga.
		for (int i = 0; i < tablero.getComida().size(); i++) {
			
			Node finalNode=tablero.getComida().get(i);
			
			 coste =calcularDistancia(tablero.getInicial(), finalNode) ;
			 
			 if (coste<=min) {
				min=coste;
				AguilaCercana=new Node(finalNode.getRow(), finalNode.getCol());
			}
		}

		return AguilaCercana;
	}

}
