package josedavidortiz129.ClasesMaquina;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;

public class TransitionEnemigoCerca extends Transition{

	public TransitionEnemigoCerca(Tablero tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}


	/*
	 * Se lanzar� la transici�n si hay alg�n enemigo cerca
	 */
	@Override
	public boolean isTriggered() {
		boolean hayenemigocerca=false;
		int i = 0;
		
		/*
		 * Se busca alg�n enemigo que quede a menos de tres casillas del jugador.
		 * Se considerar� entonces que hay alg�n enemigo cerca.
		 */
		while ( i < tablero.getEnemigos().size() && !hayenemigocerca) {
			Node enemigo=tablero.getEnemigos().get(i);
			
			int distancia=calcularDistancia(tablero.getInicial(), enemigo) ;
			
			
			if (distancia<=3) {
				hayenemigocerca=true;
			}
			i++;
		}
		
		if (hayenemigocerca) {
			return true;
		}else return false;
	}

	/*
	 * El estado siguiente de esta transici�n es el estado Huye.
	 * Este estado ocupa la posici�n 1 en nuestro arrayList de TodosEstados.
	 */
	@Override
	public int getTargetState() {
		return 1;
	}
}
