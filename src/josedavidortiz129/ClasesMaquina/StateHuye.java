package josedavidortiz129.ClasesMaquina;

import java.util.ArrayList;
import java.util.List;

import josedavidortiz129.Tablero;
import josedavidortiz129.BusquedaAestrella.AStar;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;
import ontology.Types.ACTIONS;

public class StateHuye extends State{

	public StateHuye(Tablero tab) {
		super(tab);
	}


	/*
	 * Este método devolverá la acción necesaria para huir del/de los enemigo/s.
	 */
	@Override
	public ACTIONS getAction() {

		int fila=0;
		int columna=0;
		int min=Integer.MIN_VALUE;
		
		/*
		 * En el for se calcula la posición más alejada a todos los enemigos que haya en el mapa a través de 
		 * la distancia mahattan.
		 * En fila y columna tendremos las coordenadas de esa posición.
		 */
		for (int k = 0; k < tablero.getEnemigos().size(); k++) {
			for (int i = 2; i < tablero.getColumnas()-2; i++) {
				for (int j = 2; j < tablero.getFilas()-3; j++) {
					
						if(tablero.getArr()[i][j].size() <= 0) {	
							
								Node casillaAc=new Node(j, i);
								
								int coste=calcularDistancia(casillaAc, tablero.getEnemigos().get(k));
								
								int[] distFinal=new int[3];
								distFinal[0]=coste;
								distFinal[1]=j;
								distFinal[2]=i;
								
								tablero.getEnemigos().get(k).setDistancia(distFinal);
								if (coste>min) {
									fila=j;
									columna=i;
									min=coste;
									
								}
						}
					
				}
			}
		}

		//Se crea un Nodo con las coordenadas de la posición más alejada y se calcula el camino más corto
		Node Final=new Node(fila, columna);
		
		System.out.println();
		System.out.println("///////NODO MAS LEJANO:  "+Final+"  ////////////////");
		
		//Si se ha encontrado esa posición más alejada...
		if (fila!=0 && columna!=0) {
			//Se calcula el A*
			AStar aEstrella = new AStar(tablero.getFilas(), tablero.getColumnas(), 
					tablero.getInicial(), Final);
			
			//Se asignan los bloques 
			aEstrella.setBlocks(tablero.getBloques());
			//Se calcula el camino más corto
			List<Node> ruta=aEstrella.findPath();
			//aEstrella.pintarmapa();
			
			//Si ha encontrado un camino...
			if (ruta.size()>0) {
				
				Node n = ruta.get(1);
				int filaN=n.getRow();
				int columN=n.getCol();
				//aEstrella.pintarmapa();
				
				//La siguiente función calcula la acción a tomar
				return accionatomar(tablero.getInicial().getCol(), tablero.getInicial().getRow(),columN, filaN);
			}else return ACTIONS.ACTION_UP; //Si no encuentra camino, para que pueda salir de un posible bucle, que haga algo.
			
		}else {// Si no se ha encontrado la posición más alejada es porque no hay enemigos y no debería pasar
			System.out.println("EL HUYE NO ESTÁ FUNCIONANDO");
			return Types.ACTIONS.ACTION_NIL;
		}
		
	}
	

	/*
	 * Este método devuelve una lista con las transiciones que salen de este estado.
	 * Las que salen de este estado son la transición si los enemigos quedan lejos (ya no
	 * hay que huir de ellos) y si queda una sola paloma en posición segura.
	 */
	@Override
	public ArrayList<Transition> getTransitions() {
		ArrayList<Transition> Transiciones = new ArrayList<>();
		Transiciones.add(new TransitionEnemigoLejos(tablero));
		Transiciones.add(new TransitionUnaPalomaSegura(tablero));
		return Transiciones;
	}

}
