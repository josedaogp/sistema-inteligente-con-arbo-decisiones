package josedavidortiz129.ClasesMaquina;

import java.util.ArrayList;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;

public abstract class State {

	public Tablero tablero;
	
	//Constructor que inicializa el tablero
	public State(Tablero tab) {
		 this.tablero=tab;	
	}
	
	//Método para actualizar el tablero del mapa
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}
	
	
	//Métodos abstractos para los hijos de la clase
	public abstract Types.ACTIONS getAction(); //Devuelve la acción a realizar en un estado concreto.
	public abstract ArrayList<Transition> getTransitions(); //Devuelve todas las transiciones posibles que salen de un estado concreto.
	
	//MÉTODOS GENERALES PARA LOS HIJOS DE LA CLASE
	public Types.ACTIONS accionatomar(int xac, int yac, int x1, int y1){
		/*
		 * Restando la posición actual a la del siguiente nodo, podemos saber 
		 * qué acción tomar. Existirán los cuatro casos debajo descritos.
		 */
		int sumx=xac-x1;
		int sumy=yac-y1;
		
		if (sumx==1) {
			System.out.println("Izquierda");
			return Types.ACTIONS.ACTION_LEFT;
		}
		else if(sumx==-1) {
			System.out.println("Derecha");
			return Types.ACTIONS.ACTION_RIGHT;
			
		}
		else if (sumy==1) {
			System.out.println("Arriba");
			return Types.ACTIONS.ACTION_UP;
			
		}
		else if(sumy==-1) {
			System.out.println("Abajo");
			return Types.ACTIONS.ACTION_DOWN;
		}
		else return Types.ACTIONS.ACTION_NIL;
		
	}
	
	protected int calcularDistancia(Node Inicial, Node Final) {
		//Distancia euclidea
		/*double diferenciaX = Inicial.getCol() - Final.getCol();
        double diferenciaY = Inicial.getRow() - Final.getRow();

        return Math.sqrt(Math.pow(diferenciaX, 2) + Math.pow(diferenciaY, 2));*/
		//Distancia manhattan
		return Math.abs(Final.getRow() - Inicial.getRow()) 
		+ Math.abs(Final.getCol() - Inicial.getCol());
	}
	
}
