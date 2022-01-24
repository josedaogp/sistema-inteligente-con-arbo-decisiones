package josedavidortiz129.BusquedaAestrella;

import java.util.*;

/**
 * A Star Algorithm
 *
 * @author Marcelo Surriabre
 * @version 2.1, 2017-02-23
 */
public class AStar {
    private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
    private static int DEFAULT_DIAGONAL_COST = 14;
    private int hvCost;
    private int diagonalCost;
    private Node[][] searchArea;
    private PriorityQueue<Node> openList;
    private Set<Node> closedSet;
    private Node initialNode;
    private Node finalNode;

    /*
     * Constructor principal.
     * Se inicializan los parámetros de la clase.
     */
    public AStar(int rows, int cols, Node initialNode, Node finalNode, int hvCost, int diagonalCost) {
        this.hvCost = hvCost;
        this.diagonalCost = diagonalCost;
        setInitialNode(initialNode);
        setFinalNode(finalNode);
        this.searchArea = new Node[rows][cols];
        this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node node0, Node node1) {
                return Integer.compare(node0.getF(), node1.getF());
            }
        });
        setNodes();
        this.closedSet = new HashSet<>();
    }

    /*
     * Constructor secundario.
     * Se llama al principal con costes por defecto.
     */
    public AStar(int rows, int cols, Node initialNode, Node finalNode) {
        this(rows, cols, initialNode, finalNode, DEFAULT_HV_COST, DEFAULT_DIAGONAL_COST);
    }

    /*
     * Método para mostrar por consola el mapa de búsqueda, con obstáculos (X), 
     * posición actual (J) y meta (M).
     */
    public void pintarmapa() {
    	for (int i = 0; i < searchArea.length; i++) {
    		
			for (int j = 0; j < searchArea[0].length; j++) {
				
				if (i==initialNode.getRow() && j==initialNode.getCol()) {
					System.out.print(" J ");
				}else {
					if (i==finalNode.getRow() && j==finalNode.getCol()) {
						System.out.print(" M ");
					}else {
						if (searchArea[i][j].isBlock()==true) {
							System.out.print(" X ");
						}else System.out.print(" - ");
					}
				}
				
				
				
			}

            System.out.println();
		}
    	System.out.println();
    	System.out.println("***************************************************");
        System.out.println();
    }
    
    /*
     * Método que calcula la heurística de los nodos y los incluye en el array de la clase.
     */
    private void setNodes() {
        for (int i = 0; i < searchArea.length; i++) {
            for (int j = 0; j < searchArea[0].length; j++) {
                Node node = new Node(i, j);
                node.calculateHeuristic(getFinalNode());
                this.searchArea[i][j] = node;
            }
        }
    }

    /*
     * Método utilizado para definir los bloques (obstáculos) del mapa.
     * Se le pasa un array con tantas filas como obstáculos haya, y dos filas: una 
     * para cada coordenada del mapa de dicho obstáculo.
     */
    public void setBlocks(int[][] blocksArray) {
        for (int i = 0; i < blocksArray.length; i++) {
            int row = blocksArray[i][0];
            int col = blocksArray[i][1];
            setBlock(row, col);
        }
    }

    /*
     * Método que calcula el camino hasta la meta desde la posición actual.
     * Devuelve una lista con los nodos en orden desde la posición inicial hasta la meta.
     * 
     */
    public List<Node> findPath() {
        openList.add(initialNode);
        while (!isEmpty(openList)) { //mientras queden nodos por visitar
            Node currentNode = openList.poll();
            closedSet.add(currentNode); //se incluye el nodo en el de tratados
            if (isFinalNode(currentNode)) { //si es el nodo final, ha encontrado el camino y lo devuelve
                return getPath(currentNode);
            } else {
                addAdjacentNodes(currentNode);
            }
        }
        //Si después de recorrer todos los nodos no se llega a la meta, se devuelve una lista
        //vacía como señal de que no hay camino.
        return new ArrayList<Node>();
    }

    /*
     * Método para obtener la ruta hasta el origen dado un nodo.
     */
    private List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    /*
     * Método usado para buscar los nodos alcanzables desde el nodo actual.
     */
    private void addAdjacentNodes(Node currentNode) {
        addAdjacentUpperRow(currentNode);
        addAdjacentMiddleRow(currentNode);
        addAdjacentLowerRow(currentNode);
    }

    /*
     * Calcula los nodos alcanzables en la fila de debajo del nodo actual.
     */
    private void addAdjacentLowerRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int lowerRow = row + 1;
        if (lowerRow < getSearchArea().length) {
            if (col - 1 >= 0) {
                //checkNode(currentNode, col - 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                //checkNode(currentNode, col + 1, lowerRow, getDiagonalCost()); // Comment this line if diagonal movements are not allowed
            }
            checkNode(currentNode, col, lowerRow, getHvCost());
        }
    }
    
    /*
     * Calcula los nodos alcanzables en la misma fila que el nodo actual.
     */
    private void addAdjacentMiddleRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int middleRow = row;
        if (col - 1 >= 0) {
            checkNode(currentNode, col - 1, middleRow, getHvCost());
        }
        if (col + 1 < getSearchArea()[0].length) {
            checkNode(currentNode, col + 1, middleRow, getHvCost());
        }
    }

    /*
     * Calcula los nodos alcanzables en la fila de arriba del nodo actual.
     */
    private void addAdjacentUpperRow(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();
        int upperRow = row - 1;
        if (upperRow >= 0) {
            if (col - 1 >= 0) {
                //checkNode(currentNode, col - 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            if (col + 1 < getSearchArea()[0].length) {
                //checkNode(currentNode, col + 1, upperRow, getDiagonalCost()); // Comment this if diagonal movements are not allowed
            }
            checkNode(currentNode, col, upperRow, getHvCost());
        }
    }

    /*
     * Comprueba si un nodo se puede tratar o no y lo añade a la lista de Abiertos.
     */
    private void checkNode(Node currentNode, int col, int row, int cost) {
        Node adjacentNode = getSearchArea()[row][col];
        
        //Si el nodo no es un obstáculo y no ha sido tratado ya...
        if (!adjacentNode.isBlock() && !getClosedSet().contains(adjacentNode)) {
        	//Si no está en la lista de Abiertos, lo añade para que pueda ser tratado
            if (!getOpenList().contains(adjacentNode)) {
                adjacentNode.setNodeData(currentNode, cost);
                getOpenList().add(adjacentNode);
            } else {
                boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
                if (changed) {
                    // Remove and Add the changed node, so that the PriorityQueue can sort again its
                    // contents with the modified "finalCost" value of the modified node
                    getOpenList().remove(adjacentNode);
                    getOpenList().add(adjacentNode);
                }
            }
        }
    }

    private boolean isFinalNode(Node currentNode) {
        return currentNode.equals(finalNode);
    }

    private boolean isEmpty(PriorityQueue<Node> openList) {
        return openList.size() == 0;
    }

    
    private void setBlock(int row, int col) {
        this.searchArea[row][col].setBlock(true);
    }

    public Node getInitialNode() {
        return initialNode;
    }

    public void setInitialNode(Node initialNode) {
        this.initialNode = initialNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }

    public void setFinalNode(Node finalNode) {
        this.finalNode = finalNode;
    }

    public Node[][] getSearchArea() {
        return searchArea;
    }

    public void setSearchArea(Node[][] searchArea) {
        this.searchArea = searchArea;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public void setOpenList(PriorityQueue<Node> openList) {
        this.openList = openList;
    }

    public Set<Node> getClosedSet() {
        return closedSet;
    }

    public void setClosedSet(Set<Node> closedSet) {
        this.closedSet = closedSet;
    }

    public int getHvCost() {
        return hvCost;
    }

    public void setHvCost(int hvCost) {
        this.hvCost = hvCost;
    }

    private int getDiagonalCost() {
        return diagonalCost;
    }

    private void setDiagonalCost(int diagonalCost) {
        this.diagonalCost = diagonalCost;
    }
}

