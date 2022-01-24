package josedavidortiz129.ClasesArbol;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;

public class DecisionQuedaUnaPaloma extends NodoArbol{

	private Tablero tablero;
	 private NodoArbol nodoSi;
	 private NodoArbol nodoNo;
	
	public DecisionQuedaUnaPaloma(Tablero tablero) {
		this.tablero=tablero;
	}
	
	/*
	 * Este método bifurcará al nodo izquierdo o derecho dependiendo de si queda un solo águila blanco.
	 */
	
	public Types.ACTIONS elige(){
		if (tablero.getComida().size()==1) {//Si solo queda una
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
