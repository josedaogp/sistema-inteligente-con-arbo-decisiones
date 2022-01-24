package josedavidortiz129.ClasesMaquina;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;

public class TransitionEnemigoLejos extends Transition{

	public TransitionEnemigoLejos(Tablero tab) {
		super(tab);
	
		// TODO Auto-generated constructor stub
	}


	/*
	 * Se lanzará la transición si hay algún enemigo lejos
	 */
	@Override
	public boolean isTriggered() {
		boolean hayenemigocerca2=false;
		int j = 0;
		
		/*
		 * Se busca algún enemigo que quede a menos de tres casillas del jugador.
		 * Se considerará entonces que hay algún enemigo cerca.
		 */
		while ( j < tablero.getEnemigos().size() && !hayenemigocerca2) {
			Node enemigo=tablero.getEnemigos().get(j);
			
			int distancia=calcularDistancia(tablero.getInicial(), enemigo) ;
			
			
			if (distancia<=3) {
				hayenemigocerca2=true;
			}
			j++;
		}
		
		//Si no hay enemigos cerca, es que estarán lejos.
		if (hayenemigocerca2) {
			return false;
		}else return true;
	}

	/*
	 * El estado siguiente de esta transición es el estado BuscaComida.
	 * Este estado ocupa la posición 0 en nuestro arrayList de TodosEstados.
	 */
	@Override
	public int getTargetState() {
		// TODO Auto-generated method stub
		return 0;
	}
}
