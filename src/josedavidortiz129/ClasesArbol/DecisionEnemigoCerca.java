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
	 * Este m�todo bifurcar� al nodo izquierdo o derecho dependiendo de si hay alg�n enemigo cerca.
	 * Se considerar� que hay alg�n enemigo cerca si hay alg�n enemigo que est� a menos de tres posiciones
	 * de la distancia manhattan de la posici�n del jugador.
	 */
	
	public Types.ACTIONS elige(){
		boolean hayenemigo=false;
		int i = 0;
		/*
		 * Se busca alg�n enemigo que quede a menos de tres casillas del jugador.
		 * Se considerar� entonces que hay alg�n enemigo cerca.
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
