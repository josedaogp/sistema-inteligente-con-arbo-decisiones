package josedavidortiz129;

import java.util.ArrayList;
import java.util.List;

import core.game.Observation;
import core.game.StateObservation;
import josedavidortiz129.BusquedaAestrella.AStar;
import josedavidortiz129.BusquedaAestrella.Node;
import ontology.Types;
import ontology.Types.ACTIONS;
import tools.Vector2d;

public class Tablero {
	
	final int filas = 11;    //las filas y columnas serán constantes 
	final int columnas = 24;
	
	private Node[][] tablero;
	private ArrayList<Node> comida;
	private ArrayList<Node> enemigos;
	private Node Inicial;
	private Node AguilaSegura;
	private StateObservation stateObs;
	ArrayList<Observation>[][] arr;
	int[][] bloques = new int[420][2];
	
	public Tablero(StateObservation stateObs) {
		//Posicion actual
		this.stateObs=stateObs;
		Vector2d posAc = stateObs.getAvatarPosition();

		int xInicial = (int) (posAc.x / stateObs.getBlockSize());
		int yInicial = (int) (posAc.y / stateObs.getBlockSize());
		this.Inicial = new Node(yInicial, xInicial);
		
		this.comida=new ArrayList<>();
		this.enemigos=new ArrayList<>();
		this.tablero=new Node[columnas][filas];
		
		
		this.arr= stateObs.getObservationGrid();
		
		
		mapear(arr);
		

		//AguilasBlancas();
		
	}
	public void mapear(ArrayList<Observation>[][] grid) {
		//int[][] bloques = new int[420][2];
		int k = 0;
		for (int c = 0; c < 24; c++) {
			for (int f = 0; f < 11; f++) {
				if (arr[c][f].size() > 0) { //Si hay algo en el grid, será un obstaculo
					
					//Se recoge el tipo del elemento del Grid.
					int tipo=arr[c][f].get(0).itype;
					
					switch (tipo) {
					case 0: //Arboles
						bloques[k][0] = f;     
						bloques[k][1] = c;
						k++;
						break;
					case 6:
							comida.add(new Node(f,c));
						
						break;
					case 5: //Aguila Negra
						enemigos.add(new Node(f,c));
						bloques[k][0] = f;     
						bloques[k][1] = c;
						k++;
						//Se guardará también una posición a la derecha más, para evitar que el jugador
						//se situe justo delante del coche. Lo mismo para los siguientes casos.
						bloques[k][0] = f;     
						bloques[k][1] = c+1;
						k++;
						
						bloques[k][0] = f;     
						bloques[k][1] = c-1;
						k++;
						
						bloques[k][0] = f-1;     
						bloques[k][1] = c;
						k++;
						
						bloques[k][0] = f+1;     
						bloques[k][1] = c;
						k++;
						/*
						bloques[k][0] = f-1;     
						bloques[k][1] = c-1;
						k++;
						
						bloques[k][0] = f-1;     
						bloques[k][1] = c+1;
						k++;
						
						bloques[k][0] = f+1;     
						bloques[k][1] = c-1;
						k++;
						
						bloques[k][0] = f+1;     
						bloques[k][1] = c+1;
						k++;
						break;*/
					
					default: //Si no es uno de los anteriores, el elemento será la meta o el jugador.
						     //En ese caso, no es un obstáculo.
						//System.out.println("Meta o jugador");
						break;
					}
				}
			}
		}
		
	}
	
	public void pintarmapa() {
    	for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {
				if (i==Inicial.getRow() && j==Inicial.getCol()) {
					System.out.print(" J ");
				}else {
						if (tablero[i][j].isBlock()==true) {
							System.out.print(" X ");
						}else System.out.print(" - ");
					
					}
				
			}

            System.out.println();
		}
    	System.out.println();
    	System.out.println("***************************************************");
        System.out.println();
    }
	
	public void AguilasBlancas() {

		
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				if (arr[i][j].size() > 0) { 
					if (arr[i][j].get(0).itype == 6 && arr[i][j].get(0).category==3) 
					{
						if (j!=Inicial.getRow() && i!=Inicial.getCol()) {

							comida.add(new Node(j,i));
						}
					}
				}
			}
		}
		
		
	}
	
	
	//PREGUNTAS
	
	
	
	public boolean enemigoCerca() {
		boolean hayenemigo=false;
		int i = 0;
		while ( i < enemigos.size() && !hayenemigo) {
			Node enemigo=enemigos.get(i);
			
			int distancia=calcularDistancia(this.Inicial, enemigo) ;
			
			
			if (distancia<=3) {
				hayenemigo=true;
			}
			i++;
		}
		
		return hayenemigo;
	}
	
	public boolean quedaUnaPaloma() {
		if (comida.size()==1) {
			return true;
		}else return false;
	}
	
	public boolean comidaSegura() {
		boolean segura=false;
		int i = 0;
		while ( i < comida.size() && !segura) {
			int fil=comida.get(i).getRow();
			int col=comida.get(i).getCol();
			
			if (arr[col+1][fil].size() <= 0 && arr[col-1][fil].size() <= 0 &&
					arr[col][fil+1].size() <= 0) {
				segura=true;
				this.AguilaSegura=comida.get(i);
				
			}else if(arr[col+1][fil].size() <= 0 && arr[col-1][fil].size() <= 0 &&
					arr[col][fil-1].size() <= 0){
				segura=true;
				this.AguilaSegura=comida.get(i);
			}else if(arr[col][fil+1].size() <= 0 && arr[col][fil-1].size() <= 0 &&
					arr[col+1][fil].size() <= 0){
				segura=true;
				this.AguilaSegura=comida.get(i);
			}else if(arr[col][fil+1].size() <= 0 && arr[col][fil-1].size() <= 0 &&
					arr[col-1][fil].size() <= 0){
				segura=true;
				this.AguilaSegura=comida.get(i);
			}
			i++;
		}
		return segura;
	}
	
	
	
	//ACCIONES
	
	public Types.ACTIONS buscaComida(Node AguilaCercana) {
		//System.out.println("Aguila cercana: "+AguilaCercana);

		AStar aEstrella = new AStar(filas, columnas, Inicial, AguilaCercana); // Las filas y columnas están predeclaradas en la clase como final.
		
		//Se crea los bloques del A*
		aEstrella.setBlocks(bloques);
		
		//aEstrella.pintarmapa();
		
		//Se calcula la ruta
		List<Node> ruta=aEstrella.findPath();
	
		//Si ha encontrado un camino...
		if (ruta.size()>0) {
			Node n = ruta.get(1);
			int filaN=n.getRow();
			int columN=n.getCol();
			
			//La siguiente función calcula la acción a tomar
			return accionatomar(Inicial.getCol(), Inicial.getRow(),columN, filaN);
			
		}else return Types.ACTIONS.ACTION_NIL; //Si no encuentra camino, no hace nada
	}
	
	public ACTIONS huye() {
		
		System.out.println("ENTRA EN HUYE");
		//Inicializo la distancia del enemigo a cada punto
		

		int fila=0;
		int columna=0;
		int min=Integer.MIN_VALUE;
		
		for (int k = 0; k < enemigos.size(); k++) {
			for (int i = 2; i < columnas-2; i++) {
			for (int j = 2; j < filas-3; j++) {
				
					if(arr[i][j].size() <= 0) {
						
							Node casillaAc=new Node(j, i);
							
							int coste=calcularDistancia(casillaAc, enemigos.get(k));
							//int coste=dist+enemigos.get(k).getDistancia()[0];
							
							int[] distFinal=new int[3];
							distFinal[0]=coste;
							distFinal[1]=j;
							distFinal[2]=i;
							
							enemigos.get(k).setDistancia(distFinal);
							if (coste>min) {
								System.out.println("Entra en la comparaciooon");
								//fila=enemigos.get(k).getDistancia()[1];
								fila=j;
								//columna=enemigos.get(k).getDistancia()[2];
								columna=i;
								min=coste;
								
							}
							
				}
				
				}
			}
		}
/*
		int fila=0;
		int columna=0;
		int max=Integer.MAX_VALUE;
		for (int i = 0; i < enemigos.size(); i++) {
			
				int coste=enemigos.get(i).getDistancia()[0];
				
				if (coste<max) {
					fila=enemigos.get(i).getDistancia()[1];
					columna=enemigos.get(i).getDistancia()[2];
					max=coste;
					
				}
			
		}
		*/
		Node Final=new Node(fila, columna);
		System.out.println();
		System.out.println("///////NODO MAS LEJANO:  "+Final+"  ////////////////");
		if (fila!=0 && columna!=0) {
		
			AStar aEstrella = new AStar(filas, columnas, Inicial, Final);
			aEstrella.setBlocks(bloques);
			List<Node> ruta=aEstrella.findPath();
			aEstrella.pintarmapa();
			//Si ha encontrado un camino...
			if (ruta.size()>0) {
				System.out.println("Entra en el if del ruta.size");
				Node n = ruta.get(1);
				int filaN=n.getRow();
				int columN=n.getCol();
				
				//La siguiente función calcula la acción a tomar
				aEstrella.pintarmapa();
				return accionatomar(Inicial.getCol(), Inicial.getRow(),columN, filaN);
			}else return ACTIONS.ACTION_UP;/*else {
				int[][] bloques = new int[312][2];
				int k = 0;
				for (int c = 0; c < 24; c++) {
					for (int f = 0; f < 11; f++) {
						if (arr[c][f].size() > 0) { //Si hay algo en el grid, será un obstaculo
							
							//Se recoge el tipo del elemento del Grid.
							int tipo=arr[c][f].get(0).itype;
							
							switch (tipo) {
							case 0: //Arboles
								bloques[k][0] = f;     
								bloques[k][1] = c;
								k++;
								break;
							case 6:
									comida.add(new Node(f,c));
								
								break;
							case 5: //Aguila Negra
								//enemigos.add(new Node(f,c));
								bloques[k][0] = f;     
								bloques[k][1] = c;
								k++;
								//Se guardará también una posición a la derecha más, para evitar que el jugador
								//se situe justo delante del coche. Lo mismo para los siguientes casos.
							
								
								break;
							
							default: //Si no es uno de los anteriores, el elemento será la meta o el jugador.
								     //En ese caso, no es un obstáculo.
								//System.out.println("Meta o jugador");
								break;
							}
						}
					}
				}
				Node Final2=new Node(7, 2);
				AStar aEstrella2 = new AStar(filas, columnas, Inicial, Final2);
				aEstrella2.setBlocks(bloques);
				List<Node> ruta2=aEstrella2.findPath();
				
				if (ruta2.size()>0) {
					Node n = ruta2.get(1);
					int filaN=n.getRow();
					int columN=n.getCol();
					
					//La siguiente función calcula la acción a tomar
					aEstrella.pintarmapa();
					return accionatomar(Inicial.getCol(), Inicial.getRow(),columN, filaN);
				}
				
				aEstrella.pintarmapa();
				System.out.println("HUYE NO ENCUENTRA CAMINO***********************************");
				
				return ACTIONS.ACTION_NIL;
				
			}*/
		}else {
			System.out.println("EL HUYE NO ESTÁ FUNCIONANDO");
			return Types.ACTIONS.ACTION_NIL;
		}
		
	}
	
	//AguilaBlancaMasCercana
	public Node AguilaBlancaMasCercana() {
		int min=Integer.MAX_VALUE;
		int coste;
		Node AguilaCercana=new Node(0,0);
		
		for (int i = 0; i < comida.size(); i++) {
			
			Node finalNode=comida.get(i);
			/*System.out.println();
			System.out.println("COMIDA: "+finalNode);
			System.out.println();*/
			 coste =calcularDistancia(this.Inicial, finalNode) ;
			 
			 if (coste<=min) {
				min=coste;
				
				AguilaCercana=new Node(finalNode.getRow(), finalNode.getCol());
				//System.out.println("Coste: "+coste+" del aguila: "+AguilaCercana);
			}
		}

		return AguilaCercana;
	}
	
	//Calcular distancia
	private int calcularDistancia(Node Inicial, Node Final) {
		//Distancia euclidea
		/*double diferenciaX = Inicial.getCol() - Final.getCol();
        double diferenciaY = Inicial.getRow() - Final.getRow();

        return Math.sqrt(Math.pow(diferenciaX, 2) + Math.pow(diferenciaY, 2));*/
		//Distancia manhattan
		return Math.abs(Final.getRow() - Inicial.getRow()) 
		+ Math.abs(Final.getCol() - Inicial.getCol());
	}
	
	//Accion a tomar
	public Types.ACTIONS accionatomar(int xac, int yac, int x1, int y1){
		/*
		 * Restando la posición actual a la del siguiente nodo, podemos saber 
		 * qué acción tomar. Existirán los cuatro casos debajo descritos.
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
	
	public Node[][] getTablero() {
		return tablero;
	}
	public void setTablero(Node[][] tablero) {
		this.tablero = tablero;
	}
	public ArrayList<Node> getComida() {
		
		return comida;
	}
	public void setComida(ArrayList<Node> comida) {
		this.comida = comida;
	}
	public ArrayList<Node> getEnemigos() {
		return enemigos;
	}
	public void setEnemigos(ArrayList<Node> enemigos) {
		this.enemigos = enemigos;
	}
	public ArrayList<Observation>[][] getArr() {
		return arr;
	}
	public void setArr(ArrayList<Observation>[][] arr) {
		this.arr = arr;
	}
	public int[][] getBloques() {
		return bloques;
	}
	public void setBloques(int[][] bloques) {
		this.bloques = bloques;
	}
	public int getFilas() {
		return filas;
	}
	public int getColumnas() {
		return columnas;
	}
	public void setAguilaSegura(Node aguilaSegura) {
		AguilaSegura = aguilaSegura;
	}
	public boolean comidaCerca() {
		return true;
	}
	
	public Node getInicial() {
		return Inicial;
	}
	
	public Node getAguilaSegura() {
		return AguilaSegura;
	}
	
	public void setInicial(Node inicial) {
		Inicial = inicial;
	}
	public Node[][] getSearchArea() {
		return tablero;
	}

	public void setSearchArea(Node[][] tablero) {
		this.tablero = tablero;
	}
	public Node getFinal() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
