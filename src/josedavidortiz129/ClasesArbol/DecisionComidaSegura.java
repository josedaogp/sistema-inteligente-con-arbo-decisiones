package josedavidortiz129.ClasesArbol;

import java.util.ArrayList;

import core.game.Observation;
import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;

public class DecisionComidaSegura extends NodoArbol{

	private Tablero tablero;
	 private NodoArbol nodoSi;
	 private NodoArbol nodoNo;
	
	public DecisionComidaSegura(Tablero tablero) {
		this.tablero=tablero;
	}
	
	/*
	 * Este método bifurcará al nodo izquierdo o derecho dependiendo de si la comida es segura o no.
	 * Se considerará que es segura si no está al final de un pasillo.
	 */
	public Types.ACTIONS elige(){
		boolean segura=false;
		int i = 0;
		ArrayList<Node> comida = tablero.getComida();
		ArrayList<Observation>[][] arr = tablero.getArr();
		Node AguilaSegura = tablero.getAguilaSegura();
		
		/*
		 * Se comprueba si la posición es segura.
		 * Si el águila no está al final de un pasillo, se considera segura.
		 */
		while ( i < comida.size() && !segura) {
			int fil=comida.get(i).getRow();
			int col=comida.get(i).getCol();
			
			if (arr[col+1][fil].size() <= 0 && arr[col-1][fil].size() <= 0 &&
					arr[col][fil+1].size() <= 0) {
				segura=true;
				AguilaSegura=comida.get(i);
				
			}else if(arr[col+1][fil].size() <= 0 && arr[col-1][fil].size() <= 0 &&
					arr[col][fil-1].size() <= 0){
				segura=true;
				AguilaSegura=comida.get(i);
			}else if(arr[col][fil+1].size() <= 0 && arr[col][fil-1].size() <= 0 &&
					arr[col+1][fil].size() <= 0){
				segura=true;
				AguilaSegura=comida.get(i);
			}else if(arr[col][fil+1].size() <= 0 && arr[col][fil-1].size() <= 0 &&
					arr[col-1][fil].size() <= 0){
				segura=true;
				AguilaSegura=comida.get(i);
			}
			i++;
		}
		
		if (segura) {
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
