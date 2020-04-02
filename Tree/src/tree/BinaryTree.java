package tree;

public class BinaryTree implements Tree {
    int size = 0;
    Node root;

    private class Node {
        int value;
        Node[] childs = new Node[2];
    }

    public BinaryTree() {
        root = null;
    }

    public int size(){
        return size;
    }

    /**
    * Función "insertar" que añade al contenedor un nuevo elemento pasado por parámetro
    * @return verdadero si lo añadio y falso si no añadio el elemento al contenedor
    */
    public boolean insert(int val){
        if (size != 0) {
            Node act = root;
            int side = 0;
            Node ant = null;
            while(act != null){
                if (val == act.value) {
                    return false;
                }else if(val > act.value){
                    side = 1;
                    ant = act;
                    act = act.childs[side];
                }else{
                    side = 0;
                    ant = act;
                    act = act.childs[side];
                }
            }
            ant.childs[side] = new Node();
            ant.childs[side].value = val;
            size++;
            return true;
        }else{
            // Caso de que el árbol está vacío
            root = new Node();
            root.value = val;
            size++;
            return true;
        }
    }

    /**
    * Función "extraer" que extrae del contenedor el elemento pasado por parámetro, 
    * devuelve verdadero si lo eliminó y falso en caso contrario. Si no se encuentra no se altera el contenedor.
    * Esta función hace uso de dos métodos auxiliares.
    */
    public boolean remove(int val){
        int oldSize = size;
        root = extraer(root, val);
        if (oldSize == size) {
            return false;
        }else{
            return true;
        }
    }

    /**
    * Método auxiliar de extraer
    * @param Node raíz, Node de un subárbol o null
    * @param val elemento a extraer
    * @return Node raíz, Node de un subárbol o null
    */
    private Node extraer (Node Node, int val) {
        if(Node != null) {
            if (val == Node.value) { 
                if ((Node.childs[0] == null || Node.childs[1] == null)) {
                    size--;
                    if (Node.childs[0] == null) {
                        return Node.childs[1];
                    }else{
                        return Node.childs[0];
                    }
                }else{
                    Node.childs[1]= extraerSucesor (Node, Node.childs[1]);
                }
            }else{
                if (val < Node.value) {
                    Node.childs[0] = extraer(Node.childs[0], val);
                } else {
                    Node.childs[1] = extraer(Node.childs[1], val);
                }
            }
        }
        return Node;
    }

    /**
    * Método auxiliar de extraer para el caso de extraer el Sucesor simétrico
    * @param NodeToRemove Node a extraer
    * @param Node Node o null
    * @return Node raíz, Node de un subárbol o null
    */
    private Node extraerSucesor(Node NodeToRemove, Node Node) {
        if (Node.childs[0]==null) {
            NodeToRemove.value = Node.value;
            size--;
            Node = Node.childs[1];
        } else {
            Node.childs[0] = extraerSucesor(NodeToRemove, Node.childs[0]);			
        }
        return Node;
    }

    /**
    * Función "buscar" que devuelve verdadero si el valor pasado por parámetro pertenece al contenedor y falso en caso contrario.
    * @param val elemento a buscar
    * @return verdadero si encontro el elemento y falso si no encuentra el elemento
    */
    public boolean search(int val){
        Node act = root;
        while(act != null){
            if (act.value == val) {
                return true;
            }else if (act.value < val) {
               act = act.childs[1];
            }else{
                act = act.childs[0];
            }
        }
        return false;
    }

    public void empty(){
        root = null;
        size = 0;
    }

    /**
    * Función "elementos" que devuelve un vector de enteros ordenados de menor a mayor con los elementos que se encuentren en el contenedor.
    * @return vector de enteros ordenado de menor a mayor que representa al árbol binario de búsqueda
    * Esta Función hace uso de un método auxiliar
    */
    public int[] elementos(){
        int[] vector = new int[size];
        int i = 0;
        Node actual = root;
        inorden(actual, i, vector);
        return vector;
    }

    /**
    * Método auxiliar para elementos, que realiza un recorrido en inorden del árbol binario de búsqueda
    * @param actual Node  en él que estás situado en el recorrido en inorden
    * @param vector arrays donde se almacenan los elementos de menor a mayor
    * @param i posición del vector del último valor insertado en cada hilo de ejecución
    * @return devuelve la posición del vector del último valor insertado en cada hilo de ejecución.
    */
    private int inorden(Node actual, int i, int[] vector) {
        if (actual != null) {
            i = inorden(actual.childs[0], i, vector);			
            vector[i] = actual.value;
            i++;
            i= inorden(actual.childs[1], i, vector);			
        }
        return i;
    }
    
}