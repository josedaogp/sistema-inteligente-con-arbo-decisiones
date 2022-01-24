package josedavidortiz129.ClasesArbol;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;

public class DecisionEnemigoCerca extends NodoArbol{

	private Tablero tablero;
	 private NodoArbol nodoSi;
	 private NodoArbol nodoNo;
	
	public DecisionEnemigoCerca(Tablero tablero) {
		this.tablero=tablero;
	}
	
	/*
	 * Este método bifurcará al nodo izquierdo o derecho dependiendo de si hay algún enemigo cerca.
	 * Se considerará que hay algún enemigo cerca si hay algún enemigo que está a menos de tres posiciones
	 * de la distancia manhattan de la posición del jugador.
	 */
	
	public Types.ACTIONS elige(){
		boolean hayenemigo=false;
		int i = 0;
		/*
		 * Se busca algún enemigo que quede a menos de tres casillas del jugador.
		 * Se considerará entonces que hay algún enemigo cerca.
		 */
		while ( i < tablero.getEnemigos().size() && !hayenemigo) {
			Node enemigo=tablero.getEnemigos().get(i);
			
			int distancia=calcularDistancia(tablero.getInicial(), enemigo) ;
			
			
			if (distancia<=3) {
				hayenemigo=true;
			}
			i++;
		}
		
		//Se bifurca
		if (hayenemigo) {
			return nodoSi.elige();
		}else return nodoNo.elige();
		
	}
	
	public NodoArbol getNodoSi() {
		return nodoSi;
	}

	public void setNodoSi(NodoArbol nodoSi) {
		this.nodoSi = nodoSi;
	}

	public NodoArbol getNodoNo() {
		return nodoNo;
	}

	public void setNodoNo(NodoArbol nodoNo) {
		this.nodoNo = nodoNo;
	}
	
	
}
