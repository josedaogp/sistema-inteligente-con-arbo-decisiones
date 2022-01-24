package josedavidortiz129.ClasesArbol;

import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;

public abstract class NodoArbol {

	/*
	 * Los hijos podr�n ser hojas o nodos. Las hojas devolver�n la 
	 * acci�n a tomar y los nodos ser�n las preguntas
	 */
	
	public abstract Types.ACTIONS elige();
	
	//M�TODOS NECESARIOS PARA LOS HIJOS
	protected int calcularDistancia(Node Inicial, Node Final) {
		//Distancia euclidea
		/*double diferenciaX = Inicial.getCol() - Final.getCol();
        double diferenciaY = Inicial.getRow() - Final.getRow();

        return Math.sqrt(Math.pow(diferenciaX, 2) + Math.pow(diferenciaY, 2));*/
		//Distancia manhattan
		return Math.abs(Final.getRow() - Inicial.getRow()) 
		+ Math.abs(Final.getCol() - Inicial.getCol());
	}
	
	public Types.ACTIONS accionatomar(int xac, int yac, int x1, int y1){
		/*
		 * Restando la posici�n actual a la del siguiente nodo, podemos saber 
		 * qu� acci�n tomar. Existir�n los cuatro casos debajo descritos.
		 */
		int sumx=xac-x1;
		int sumy=yac-y1;
		
		if (sumx==1) {
			System.out.println("Izquierda");
			return Types.ACTIONS.ACTION_LEFT;
		}
		else if(sumx==-1) {
			System.out.println("Derecha");
			return Types.ACTIONS.ACTION_RIGHT;
			
		}
		else if (sumy==1) {
			System.out.println("Arriba");
			return Types.ACTIONS.ACTION_UP;
			
		}
		else if(sumy==-1) {
			System.out.println("Abajo");
			return Types.ACTIONS.ACTION_DOWN;
		}
		else return Types.ACTIONS.ACTION_NIL;
		
	}
	
}
