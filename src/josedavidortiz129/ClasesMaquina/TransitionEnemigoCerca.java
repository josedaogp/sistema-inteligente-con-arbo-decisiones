package josedavidortiz129.ClasesMaquina;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;

public class TransitionEnemigoCerca extends Transition{

	public TransitionEnemigoCerca(Tablero tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}


	/*
	 * Se lanzará la transición si hay algún enemigo cerca
	 */
	@Override
	public boolean isTriggered() {
		boolean hayenemigocerca=false;
		int i = 0;
		
		/*
		 * Se busca algún enemigo que quede a menos de tres casillas del jugador.
		 * Se considerará entonces que hay algún enemigo cerca.
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
	 * El estado siguiente de esta transición es el estado Huye.
	 * Este estado ocupa la posición 1 en nuestro arrayList de TodosEstados.
	 */
	@Override
	public int getTargetState() {
		return 1;
	}
}
