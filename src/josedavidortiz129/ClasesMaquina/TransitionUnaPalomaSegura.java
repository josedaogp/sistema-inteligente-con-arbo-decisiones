package josedavidortiz129.ClasesMaquina;

import java.util.ArrayList;

import core.game.Observation;
import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.Node;

public class TransitionUnaPalomaSegura extends Transition {

	//Constructor. Se llama al del padre para acutalizar el tablero.
	public TransitionUnaPalomaSegura(Tablero tab) {
		
		super(tab);
		
	}

	/*
	 * Se lanzará la transición si queda una sola paloma y está en posición segura.
	 * Para que se considere que está en posición segura deberá tener alguna escapatoria.
	 * (No deberá estar al final de un pasillo)
	 */
	@Override
	public boolean isTriggered() {
		if (tablero.getComida().size()==1) { //Si solo queda una paloma
			
			boolean segura=false;
			int k = 0;
			ArrayList<Node> comida = tablero.getComida();
			ArrayList<Observation>[][] arr = tablero.getArr();
			Node AguilaSegura = tablero.getAguilaSegura();
			
			/*
			 * Se comprueba si la posición es segura.
			 * Si el águila no está al final de un pasillo, se considera segura.
			 */
			while ( k < comida.size() && !segura) {
				int fil=comida.get(k).getRow();
				int col=comida.get(k).getCol();
				
				if (arr[col+1][fil].size() <= 0 && arr[col-1][fil].size() <= 0 &&
						arr[col][fil+1].size() <= 0) {
					segura=true;
					AguilaSegura=comida.get(k);
					
				}else if(arr[col+1][fil].size() <= 0 && arr[col-1][fil].size() <= 0 &&
						arr[col][fil-1].size() <= 0){
					segura=true;
					AguilaSegura=comida.get(k);
				}else if(arr[col][fil+1].size() <= 0 && arr[col][fil-1].size() <= 0 &&
						arr[col+1][fil].size() <= 0){
					segura=true;
					AguilaSegura=comida.get(k);
				}else if(arr[col][fil+1].size() <= 0 && arr[col][fil-1].size() <= 0 &&
						arr[col-1][fil].size() <= 0){
					segura=true;
					AguilaSegura=comida.get(k);
				}
				k++;
			}
			
			if (segura) {
				return true;
			}else return false;
		}else return false;
		
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
