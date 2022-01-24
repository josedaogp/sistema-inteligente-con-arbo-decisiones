package josedavidortiz129.ClasesMaquina;

import java.util.ArrayList;

import core.game.Observation;
import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;

public abstract class  Transition {

	public Tablero tablero;
	
	public Transition(Tablero tab) {
		 this.tablero=tab;	
		}
	
	
	/*
	 * No se necesita un método para actualizar el tablero debido a que las transiciones
	 * las crea los estados con el tablero ya actualizado
	 * 
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}*/

	
	
	public abstract boolean isTriggered(); //Devuelve true si se cumple la condición de la transición
	public abstract int getTargetState(); //Devuelve la posición que ocupa el siguiente estado en la lista de TodosEstados
	
	
	//MÉTODOS GENERALES PARA LOS HIJOS
	protected int calcularDistancia(Node Inicial, Node Final) {
	
		//Distancia manhattan
		return Math.abs(Final.getRow() - Inicial.getRow()) 
		+ Math.abs(Final.getCol() - Inicial.getCol());
	}
}
