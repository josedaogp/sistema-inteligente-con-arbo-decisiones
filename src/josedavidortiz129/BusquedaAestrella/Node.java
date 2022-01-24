package josedavidortiz129.BusquedaAestrella;

/**
 * Node Class
 *
 * @author Marcelo Surriabre
 * @version 2.0, 2018-02-23
 */
public class Node {

	public int[] distancia;
    private int g;
    private int f;
    private int h; //Heurística del nodo
    private int row; //Fila en el mapa que ocupa
    private int col; //Columna en el mapa que ocupa
    private boolean isBlock; //Si es obstáculo o es pisable
    private Node parent; //Nodo padre de éste

    public Node(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.distancia=new int[3];
        this.distancia[0]=0;
    }

    public void setDistancia(int[] d) {
    	this.distancia=d;
    }
    public int[] getDistancia() {
    	return distancia;
    }
    /*
     * Método que calcula la heurística del este nodo hasta el pasado por parámetro
     */
    public void calculateHeuristic(Node finalNode) {
        this.h = Math.abs(finalNode.getRow() - getRow()) + Math.abs(finalNode.getCol() - getCol());
    }

    public void setNodeData(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        setParent(currentNode);
        setG(gCost);
        calculateFinalCost();
    }

    /*
     * Comprueba si es el mejor nodo para la ruta
     */
    public boolean checkBetterPath(Node currentNode, int cost) {
        int gCost = currentNode.getG() + cost;
        if (gCost < getG()) {
            setNodeData(currentNode, cost);
            return true;
        }
        return false;
    }

    private void calculateFinalCost() {
        int finalCost = getG() + getH();
        setF(finalCost);
    }

    /*
     * Dos nodos se considerarán iguales si ocupan la msima posición
     */
    @Override
    public boolean equals(Object arg0) {
        Node other = (Node) arg0;
        return this.getRow() == other.getRow() && this.getCol() == other.getCol();
    }

    @Override
    public String toString() {
        return "Node [row=" + row + ", col=" + col + "]";
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
