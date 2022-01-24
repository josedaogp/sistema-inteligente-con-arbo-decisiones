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
	 * Este m�todo devolver� la acci�n necesaria para llegar buscar comida y comer.
	 */
	@Override
	public ACTIONS getAction() {
		
		//Obtengo el �guila m�s cercana para usarla en el A*
		Node AguilaCercana=AguilaBlancaMasCercana();
		AStar aEstrella = new AStar(tablero.getFilas(), tablero.getColumnas(),
				tablero.getInicial(), AguilaCercana); // Las filas y columnas est�n predeclaradas en la clase como final.
		
		//Se crea los bloques del A* (Los bloques estar�n definidos en el tablero)
		
		aEstrella.setBlocks(tablero.getBloques());
		
		//aEstrella.pintarmapa();
		
		//Se calcula la ruta
		List<Node> ruta=aEstrella.findPath();
	
		//Si ha encontrado un camino...
		if (ruta.size()>0) {
			Node n = ruta.get(1);
			int filaN=n.getRow();
			int columN=n.getCol();
			
			//La siguiente funci�n calcula la acci�n a tomar para llegar al destino
			return accionatomar(tablero.getInicial().getCol(), tablero.getInicial().getRow(),columN, filaN);
			
		}else return Types.ACTIONS.ACTION_NIL; //Si no encuentra camino, no hace nada
	}

	/*
	 * Este m�todo devuelve una lista con las transiciones que salen de este estado.
	 * La �nica que se tiene es la transici�n de si hay alg�n enemigo cerca.
	 */
	@Override
	public ArrayList<Transition> getTransitions() {
		ArrayList<Transition> Transiciones = new ArrayList<Transition>();
		Transiciones.add(new TransitionEnemigoCerca(tablero));
		return Transiciones;
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
