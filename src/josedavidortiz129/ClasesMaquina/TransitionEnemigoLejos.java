package josedavidortiz129.ClasesMaquina;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;

public class TransitionEnemigoLejos extends Transition{

	public TransitionEnemigoLejos(Tablero tab) {
		super(tab);
	
		// TODO Auto-generated constructor stub
	}


	/*
	 * Se lanzar� la transici�n si hay alg�n enemigo lejos
	 */
	@Override
	public boolean isTriggered() {
		boolean hayenemigocerca2=false;
		int j = 0;
		
		/*
		 * Se busca alg�n enemigo que quede a menos de tres casillas del jugador.
		 * Se considerar� entonces que hay alg�n enemigo cerca.
		 */
		while ( j < tablero.getEnemigos().size() && !hayenemigocerca2) {
			Node enemigo=tablero.getEnemigos().get(j);
			
			int distancia=calcularDistancia(tablero.getInicial(), enemigo) ;
			
			
			if (distancia<=3) {
				hayenemigocerca2=true;
			}
			j++;
		}
		
		//Si no hay enemigos cerca, es que estar�n lejos.
		if (hayenemigocerca2) {
			return false;
		}else return true;
	}

	/*
	 * El estado siguiente de esta transici�n es el estado BuscaComida.
	 * Este estado ocupa la posici�n 0 en nuestro arrayList de TodosEstados.
	 */
	@Override
	public int getTargetState() {
		// TODO Auto-generated method stub
		return 0;
	}
}
