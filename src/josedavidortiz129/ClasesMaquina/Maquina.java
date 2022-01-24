package josedavidortiz129.ClasesMaquina;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.game.Observation;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import josedavidortiz129.BusquedaAestrella.Node;
import josedavidortiz129.BusquedaAestrella.AStar;
import josedavidortiz129.Tablero;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;

/**
 * Created with IntelliJ IDEA. User: ssamot Date: 14/11/13 Time: 21:45 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Maquina extends AbstractPlayer {
	/**
	 * Random generator for the agent.
	 */
	protected Random randomGenerator;
	/**
	 * List of available actions for the agent
	 */
	
	//Tablero del juego
	Tablero tablero;
	
	//States (Las transiciones se crean cuando se crean los estados)
	StateBuscaComida BuscaComida = new StateBuscaComida(tablero);
	StateHuye Huye = new StateHuye(tablero);
	
	
	/*	
	Variable que contendr� una lista con todos los estados.
	En nuestra maquina solo tenemos dos estados.
	En la posici�n 0 se encontrar� el estado BuscaComida, y en 
	la posici�n 1 se encontrar� el estado Huye.
	Esta informaci�n nos servir� para identificar los estados en la lista.
	*/
	
	ArrayList<State> TodosEstados=new ArrayList<>();
	
	//Estado en la que se encuentra la m�quina en cada ejecuci�n.  
	State EstadoActual;
			

	/**
	 * Public constructor with state observation and time due.
	 * 
	 * @param so           state observation of the current game.
	 * @param elapsedTimer Timer for the controller creation.
	 */
	public Maquina(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		//Para que la primera ejecuci�n se pueda realizar debemos a�adir 
		//los estados a la lista de estados
		TodosEstados.add(BuscaComida);
		
		TodosEstados.add(Huye);
		
		//El estado inicial ser� BuscaComida
		EstadoActual=BuscaComida;
		
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
		
		//Actualizaci�n del tablero en los estados. Se cogen, se actualizan y 
		//se vuelven a meter en la lista.
		
		State BuscaCom=TodosEstados.get(0);
		TodosEstados.remove(0);
		BuscaCom.setTablero(tablero);
		
		State Huy=TodosEstados.get(0);
		TodosEstados.remove(0);
		Huy.setTablero(tablero);
		
		TodosEstados.add(BuscaCom);
		TodosEstados.add(Huy);
		
		
		//Maquina de estados
		
		/*Se recorrer�n todas las transiciones que puedan partir del estado actual
		y se comprobar� si se cumple la condici�n. Si es as�, hace la transici�n.
		*/
		
		Transition transicionLanzada=null;
		for (int i = 0; i < EstadoActual.getTransitions().size(); i++) {
			Transition transicion=EstadoActual.getTransitions().get(i);
			if (transicion.isTriggered()) {
				transicionLanzada=transicion;
				break;
			}
		}
		
		/*
		 * Si se ha encontrado una transici�n que se pueda realizar,
		 * se obtiene el estado siguiente al que se llega desde la transici�n
		 * y se ejecuta la acci�n de ese nodo.
		 * 
		 * Si no se ha encontrado una transici�n v�lida, volver� a realizar
		 * la acci�n del nodo en el que se encuentra.
		 * 
		 */
		
		if (transicionLanzada!=null) {
			int estadosig=transicionLanzada.getTargetState(); //Obtenemos el estado que es a partir de su posici�n.
			switch (estadosig) {
				case 0: //Estado BuscaComida
					EstadoActual=TodosEstados.get(estadosig);
					System.out.println("*******ESTADO ACTUAL: Busca Comida *******");
					return EstadoActual.getAction();
				
				case 1: //Estado Huye
					EstadoActual=TodosEstados.get(estadosig);
					System.out.println("*******ESTADO ACTUAL: Huye *******");
					return EstadoActual.getAction();
			}
		}else {
			return EstadoActual.getAction();
		}
		return null;
		
	}
	
}