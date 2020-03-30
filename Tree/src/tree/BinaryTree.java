package tree;

public class BinaryTree implements Tree {
    int size = 0;
    Nodo root;

    /**
    * Nodo que almacena el valor como entero y como máximo dos enlaces a otros nodos
    */
    private class Nodo {
        int value;
        Nodo[] hijos = new Nodo[2];
    }

    /**
    * Un constructor por defecto que permita crear contenedores vacíos.
    */
    public BinaryTree() {
        root = null;
    }

    /**
    * Una función "cardinal" que devuelva el número de elementos del contenedor.
    * @return número de elementos del contendor
    */
    public int size(){
        return size;
    }

    /**
    * Función "insertar" que añade al contenedor un nuevo elemento pasado por parámetro, devuelve verdadero si lo añadió y falso en caso contrario.
    * @param val elemento a añadir
    * @return verdadero si lo añadio y falso si no añadio el elemento al contenedor
    */
    public boolean insert(int val){
        if (size != 0) {
            Nodo act = root;
            int side = 0;
            Nodo ant = null;
            while(act != null){
                if (val == act.value) {
                    return false;
                }else if(val > act.value){
                    side = 1;
                    ant = act;
                    act = act.hijos[side];
                }else{
                    side = 0;
                    ant = act;
                    act = act.hijos[side];
                }
            }
            ant.hijos[side] = new Nodo();
            ant.hijos[side].value = val;
            size++;
            return true;
        }else{
            // Caso de que el árbol está vacío
            root = new Nodo();
            root.value = val;
            size++;
            return true;
        }
    }

    /**
    * Función "extraer" que extrae del contenedor el elemento pasado por parámetro, devuelve verdadero si lo eliminó y falso en caso contrario. Si no se encuentra no se altera el contenedor.
    * @param val elemento a extraer
    * @return verdadero si lo extrajo y falso si no extrajo el elemento del contenedor
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
    * @param nodo raíz, nodo de un subárbol o null
    * @param val elemento a extraer
    * @return nodo raíz, nodo de un subárbol o null
    */
    private Nodo extraer (Nodo nodo, int val) {
        if(nodo != null) {
            if (val == nodo.value) { 
                if ((nodo.hijos[0] == null || nodo.hijos[1] == null)) {
                    size--;
                    if (nodo.hijos[0] == null) {
                        return nodo.hijos[1];
                    }else{
                        return nodo.hijos[0];
                    }
                }else{
                    nodo.hijos[1]= extraerSucesor (nodo, nodo.hijos[1]);
                }
            }else{
                if (val < nodo.value) {
                    nodo.hijos[0] = extraer(nodo.hijos[0], val);
                } else {
                    nodo.hijos[1] = extraer(nodo.hijos[1], val);
                }
            }
        }
        return nodo;
    }

    /**
    * Método auxiliar de extraer para el caso de extraer el Sucesor simétrico
    * @param nodoExtraer nodo a extraer
    * @param nodo nodo o null
    * @return nodo raíz, nodo de un subárbol o null
    */
    private Nodo extraerSucesor(Nodo nodoExtraer, Nodo nodo) {
        if (nodo.hijos[0]==null) {
            nodoExtraer.value = nodo.value;
            size--;
            nodo = nodo.hijos[1];
        } else {
            nodo.hijos[0] = extraerSucesor(nodoExtraer, nodo.hijos[0]);			
        }
        return nodo;
    }

    /**
    * Función "buscar" que devuelve verdadero si el valor pasado por parámetro pertenece al contenedor y falso en caso contrario.
    * @param val elemento a buscar
    * @return verdadero si encontro el elemento y falso si no encuentra el elemento
    */
    public boolean search(int val){
        Nodo act = root;
        while(act != null){
            if (act.value == val) {
                return true;
            }else if (act.value < val) {
               act = act.hijos[1];
            }else{
                act = act.hijos[0];
            }
        }
        return false;
    }

    /**
    *  Función "vaciar" que deja el contenedor sin ningún elemento.
    */
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
        Nodo actual = root;
        inorden(actual, i, vector);
        return vector;
    }

    /**
    * Método auxiliar para elementos, que realiza un recorrido en inorden del árbol binario de búsqueda
    * @param actual nodo  en él que estás situado en el recorrido en inorden
    * @param vector arrays donde se almacenan los elementos de menor a mayor
    * @param i posición del vector del último valor insertado en cada hilo de ejecución
    * @return devuelve la posición del vector del último valor insertado en cada hilo de ejecución.
    */
    private int inorden(Nodo actual, int i, int[] vector) {
        if (actual != null) {
            i = inorden(actual.hijos[0], i, vector);			
            vector[i] = actual.value;
            i++;
            i= inorden(actual.hijos[1], i, vector);			
    }
        return i;
    }
}