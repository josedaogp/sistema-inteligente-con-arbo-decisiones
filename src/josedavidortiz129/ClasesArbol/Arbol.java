package josedavidortiz129.ClasesArbol;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import josedavidortiz129.Tablero;
import ontology.Types;
import tools.ElapsedCpuTimer;

/**
 * Created with IntelliJ IDEA. User: ssamot Date: 14/11/13 Time: 21:45 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Arbol extends AbstractPlayer {
	/**
	 * Random generator for the agent.
	 */
	protected Random randomGenerator;
	/**
	 * List of available actions for the agent
	 */
	protected ArrayList<Types.ACTIONS> actions;
	final int filas = 11;    //las filas y columnas serán constantes 
	final int columnas = 24;
	Tablero tablero;

	
	public Arbol(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		
		
	}

	/**
	 * Picks an action. This function is called every game step to request an action
	 * from the player.
	 * 
	 * @param stateObs     Observation of the current state.
	 * @param elapsedTimer Timer when the action returned is due.
	 * @return An action for the current state
	 */
	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		//Actualizo el tablero
		tablero=new Tablero(stateObs);
		
		
		//ARBOL DE DECISION CON IF:
		/*
		if (tablero.enemigoCerca()) {
			if (tablero.quedaUnaPaloma()) {
				return tablero.buscaComida(AguilaCercana);
			}else {
				if (tablero.comidaSegura()) {
					return tablero.buscaComida(tablero.getAguilaSegura());
				}else {
					return tablero.huye();
				}
			}
		} else return tablero.buscaComida(AguilaCercana);*/
		
		//Preguntas
		DecisionEnemigoCerca enemigoCerca=new DecisionEnemigoCerca(tablero);
		DecisionComidaSegura comidaSegura=new DecisionComidaSegura(tablero);
		DecisionQuedaUnaPaloma quedaUnaPaloma=new DecisionQuedaUnaPaloma(tablero);
		
		//Acciones
		AccionBuscaComida buscaComida=new AccionBuscaComida(tablero);
		AccionHuye huye=new AccionHuye(tablero);
		
		//Creacion del arbol
		enemigoCerca.setNodoSi(quedaUnaPaloma);
		enemigoCerca.setNodoNo(buscaComida);
		
		quedaUnaPaloma.setNodoSi(buscaComida);
		quedaUnaPaloma.setNodoNo(comidaSegura);
		
		comidaSegura.setNodoSi(buscaComida);
		comidaSegura.setNodoNo(huye);
		
		
		//El método se llamará recursivamente por cada nodo del árbol y devolverá la acción.
		return enemigoCerca.elige();
	}
	
}