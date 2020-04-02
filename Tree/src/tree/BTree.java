package Tree;
import java.util.*;

public class BTree implements Tree {
    private int orden;
    private int raiz;
    private int tamañoDatos;
    private int cardinal;
    private String nFichero;
    private FicheroAyuda auxFA;

    /**
     * Clase nodo
     */
    private class Nodo {
        private int[] claves;
        private int[] enlaces;
        private int numClaves;
        private int posicion;

        public Nodo() {
            claves = new int[orden + 1];
            // Enlaces del nodo
            enlaces = new int[orden + 1]; 
            posicion = auxFA.dirNula;
            numClaves = 0;
        }

        public int search(int num) {
            int limInf = 1;
            int limSup = numClaves;
            int pos;
            while(limSup >= limInf) {
                pos = (limSup + limInf) / 2;
                if(claves[pos] == num) {
                    return pos;
                } else {
                    if(claves[pos] > num) {
                        limSup = pos - 1;
                    } else {
                        limInf = pos + 1;
                    }
                }
            }
            return limInf-1;
            }

            /**
     * Una función "esta" que devuelve si el num esta en el árbol B
     * @return verdadero si está, sino falso
     */
        public boolean esta(int num) {
            int limInf = 1;
            int limSup = numClaves;
            int pos;
            while(limSup >= limInf) {
                pos = (limSup + limInf) / 2;
                if(claves[pos] == num) {
                    return true;
                } else {
                    if(claves[pos] > num) {
                        limSup = pos - 1;
                    } else {
                        limInf = pos + 1;
                    }
            }
            }
            return false; 
        }

        /**
         * Métodos públicos
         */
        public void insert(int pos, int num, int enlace) {
            for(int i = numClaves; i >= pos; i--) {
                claves[i+1] = claves[i];
                enlaces[i+1] = enlaces[i];
            }
            claves[pos] = num;
            enlaces[pos] = enlace;
            numClaves = numClaves + 1;
        }

        public void remove(int pos) {
            for(int i = pos; i < numClaves; i++) {
                claves[i] = claves[i+1];
                enlaces[i] = enlaces[i+1];
            }
            numClaves = numClaves - 1;
        }

        public void deByte(byte[] datos) {
            int cont = 0;
            numClaves = Conversor.aInt(Conversor.toma(datos, 0, 4));
            posicion = Conversor.aInt(Conversor.toma(datos, 4, 4));
            cont = 8;
            for(int i = 0; i < numClaves + 1; i++) {
                enlaces[i] = Conversor.aInt(Conversor.toma(datos, cont, 4));
                cont = cont + 4;
            }
            for(int i = 1; i < numClaves + 1; i++) {
                claves[i] = Conversor.aInt(Conversor.toma(datos, cont, 4));
                cont = cont + 4;
            }
        }

        public byte[] aByte() {
            byte[] res = new byte[tamañoDatos];
            int pos = 0;
            pos = Conversor.añade(res, Conversor.aByte(numClaves), 0);
            pos = Conversor.añade(res, Conversor.aByte(posicion), pos);
            for(int i = 0; i < numClaves + 1; i++) {
                pos = Conversor.añade(res, Conversor.aByte(enlaces[i]), pos);
            }
            for(int i = 1; i < numClaves + 1; i++) {
                    pos = Conversor.añade(res, Conversor.aByte(claves[i]), pos);
            }
            return res;
        }

        public Nodo copia() {
            Nodo res  = new Nodo();
            res.numClaves = numClaves;
            res.posicion = posicion;
            for(int i = 0; i < orden+1; i++) {
                res.claves[i] = claves[i];
                res.enlaces[i] = enlaces[i];
            }
            return res;
        }
    }

    private class Pareja {
        private Nodo nodo;
        private int posicion;

        public Pareja() {}

        public Pareja(Nodo n, int p) {
            // Realiza una copia del nodo
            nodo = new Nodo();
            nodo.numClaves = n.numClaves;
            nodo.posicion = n.posicion;
            for(int i = 0; i < orden+1; i++) {
                nodo.claves[i] = n.claves[i];
                nodo.enlaces[i] = n.enlaces[i];

            }
            posicion = p;
        }
    }

    /**
     * Clase auxiliar pareja clave/enlace
     */
    private class Parejainsert {
        private int clave;
        private int enlace;

        public Parejainsert() {
            enlace = FicheroAyuda.dirNula;
        }

        public Parejainsert(int clave, int enlace) {
            this.clave = clave;
            this.enlace = enlace;
        }
    }

    /**
    * Una función "crear" que crea un fichero cuyo nombre es "name", lo asocia
    * al contenedor e inicializa los atributos correspondientes. El orden pasado por parámetro "orden"
    * indica el orden del árbol B
    * @void
    */
    public void crear(String name, int orden) {
        auxFA = new FicheroAyuda();
        this.orden = orden;
        nFichero = name;
        tamañoDatos = (2*(orden) + 2 )*4;
        raiz = FicheroAyuda.dirNula;
        cardinal = 0;
        auxFA.crear(name, tamañoDatos, 4);
        auxFA.adjunto(0, orden);
        auxFA.adjunto(1, raiz);
        auxFA.adjunto(2, tamañoDatos);
        auxFA.adjunto(3, cardinal);
    }

    /**
    * Una función "abrir" que asocia el fichero cuyo nombre es "name" con el
    * contenedor e inicializa los atributos correspondientes
    * @void
    */
    public void abrir (String name) {
        cerrar();
        auxFA.abrir(name);
        orden = auxFA.adjunto(0);
        raiz = auxFA.adjunto(1);
        tamañoDatos = auxFA.adjunto(2);
        cardinal = auxFA.adjunto(3);
    }

    public void cerrar() {
        auxFA.cerrar();
        cardinal = 0;
        raiz = FicheroAyuda.dirNula;
    }

    /**
    * Una función "cardinal" que devuelva el número de elementos del contenedor.
    * @return número de elementos del contendor
    */
    public int cardinal() {
        return cardinal;
    }

    /**
    *  Una función "empty" que vacía el fichero asociado al contenedor
    */
    public void empty() {
        auxFA.cerrar();
        crear(nFichero, orden);
    }

    public boolean search(int num) {
        Stack<Pareja> pila = new Stack<Pareja>();
        return search(num, pila);
    }

    private boolean search(int num, Stack<Pareja> pila) {
        int dir = raiz;
        Nodo aux = new Nodo();
        while(dir != -1) {
            byte[] datos = auxFA.leer(dir);
            aux.deByte(datos);
            int pos = aux.search(num);
            pila.add(new Pareja(aux, pos));
            if(aux.esta(num)) {
                return true;
            }
            dir = aux.enlaces[pos];
        }
        return false;
    }

    /**
    * Función "insert" que añade al contenedor un nuevo elemento pasado por parámetro, devuelve verdadero si lo añadió y falso en caso contrario.
    * @param num elemento a añadir
    * @return verdadero si lo añadio y falso si no añadio el elemento al contenedor
    */
    public boolean insert(int num) {
        Stack<Pareja> pila = new Stack<Pareja>();
        if(search(num, pila)) {
            return false;
        }
        auxFA.adjunto(3, cardinal+1);
        cardinal = cardinal+1;
        Parejainsert p = new Parejainsert();
        p.clave = num;
        Pareja info;
        if (pila.isEmpty() == false) { // El árbol no está vacío
            info = pila.pop();
            Nodo actual = info.nodo;
            actual.insert(info.posicion + 1, num, FicheroAyuda.dirNula);
            if(actual.numClaves < orden) {
                auxFA.escribir(actual.aByte(), actual.posicion);
                return true;
            }
            // Casos de sobrecarga
            while(pila.isEmpty() == false) {
                info = pila.pop();
                Nodo padre = info.nodo;
                int pos = info.posicion;
                Nodo izq = new Nodo();
                if(pos > 0) { // En estos caso tiene hermano izquierdo
                    izq.deByte(auxFA.leer(padre.enlaces[pos-1]));
                    if(izq.numClaves < orden-1) { // Realizamos rotaciÃ³n
                        rotacionDerIzq(padre, actual, izq, pos-1);
                        return true;
                    }
                }
                Nodo der = new Nodo();
                if(pos < padre.numClaves) { // En estos casos tiene hermano derecho
                    der.deByte(auxFA.leer(padre.enlaces[pos+1]));
                    if(der.numClaves < orden-1) {
                        rotacionIzqDer(padre, der, actual, pos);
                        return true;
                    }
                }
                // Si no se puede rotar hacemos partición 2-3
                if(pos == 0) { // partición con el hermano derecho
                    particion_2_3(padre, pos, actual, der);
                } else { // partición con el hermano izquierdo
                    particion_2_3(padre, pos-1, izq, actual);
                }
                if(padre.numClaves < orden) { // Comprobamos que no se haya propagado la sobrecarga
                    auxFA.escribir(padre.aByte(), padre.posicion);
                    return true;
                }
                actual = padre; // Si se propaga repetimos el proceso con el padre
            }
            p = particion_1_2(actual);
        }

        /**
         * Se crea una raíz pues o el arbol está vacío o se ha propagado la
         * sobrecarga a la raíz
         */
        Nodo aux = new Nodo();
        aux.posicion = auxFA.tomarPágina();
        aux.claves[1] = p.clave;
        aux.enlaces[0] = raiz;
        aux.enlaces[1] = p.enlace;
        aux.numClaves = 1;
        raiz = aux.posicion;
        auxFA.escribir(aux.aByte(), aux.posicion);
        auxFA.adjunto(1, aux.posicion);
        return true;
    }

    /**
    * Una función "rotacionIzqDer" realiza la rotación hacia la derecha a partir de los
    * nodos padre,izq y der; y la posición del enlace que apunta a izq,posizq
    * @void
    */
    private void rotacionIzqDer(Nodo padre, Nodo der, Nodo izq, int posIzq) {
        int clavesTotales = der.numClaves + izq.numClaves;
        // Fija el número de claves para los dos nodos
        int clavesIzq = clavesTotales / 2;
        int clavesDer = clavesTotales - clavesIzq;
        int clavesPasar = clavesDer - der.numClaves;
        int clavesAntDer = der.numClaves;
        der.numClaves = clavesDer;
        // Abrimos hueco para pasar las claves y enlaces del hermano izquierdo
        for(int i = clavesAntDer; i >= 1; i--) {
            der.claves[i+clavesPasar] = der.claves[i];
            der.enlaces[i+clavesPasar] = der.enlaces[i];
        }
        der.enlaces[clavesPasar] = der.enlaces[0];
        der.claves[clavesPasar] = padre.claves[posIzq+1]; // Les pasamos al nodo la clave del padre
        // Ahora ya pasamos las claves y enlaces del hermano izquierdo
        for(int i = clavesIzq + 2; i <= izq.numClaves; i++) {
            der.claves[i-clavesIzq-1] = izq.claves[i];
            der.enlaces[i-clavesIzq-1] = izq.enlaces[i];
        }
        der.enlaces[0] = izq.enlaces[clavesIzq+1];
        padre.claves[posIzq+1] = izq.claves[clavesIzq+1];
        izq.numClaves = clavesIzq;
        auxFA.escribir(izq.aByte(), izq.posicion);
        auxFA.escribir(der.aByte(), der.posicion);
        auxFA.escribir(padre.aByte(), padre.posicion);
    }

    /**
    * Una función "rotacionDerIzq" realiza la rotación hacia la izquierda a partir de los
    * nodos padre,izq y der; y la posición del enlace que apunta a izq,posizq
    * @void
    */
    private void rotacionDerIzq(Nodo padre, Nodo der, Nodo izq, int posIzq) {
        int clavesTotales = der.numClaves + izq.numClaves;
        // Fija el número de claves para los dos nodos
        int clavesDer = clavesTotales / 2;
        int clavesIzq = clavesTotales - clavesDer;
        int clavesPasar = clavesIzq - izq.numClaves;
        int clavesAntIzq = izq.numClaves;
        izq.numClaves = clavesIzq;
        izq.claves[clavesAntIzq+1] = padre.claves[posIzq+1];
        izq.enlaces[clavesAntIzq+1] = der.enlaces[0];
        // Pasamos las claves del nodo derecho al izquierdo 
        for(int i = 1; i < clavesPasar; i++) {
            izq.claves[clavesAntIzq+i+1] = der.claves[i];
            izq.enlaces[clavesAntIzq+i+1] = der.enlaces[i];
        }
        // Pasamos la clave al padre
        padre.claves[posIzq+1] = der.claves[clavesPasar];
        // Desplazamos en el derecho para eliminar los huecos libres
        der.enlaces[0] = der.enlaces[clavesPasar];
        for(int i  = 1; i <= clavesDer; i++) {
            der.claves[i] = der.claves[i+clavesPasar];
            der.enlaces[i] = der.enlaces[i+clavesPasar];
        }
        der.numClaves = clavesDer;
        auxFA.escribir(izq.aByte(), izq.posicion);
        auxFA.escribir(der.aByte(), der.posicion);
        auxFA.escribir(padre.aByte(), padre.posicion);
    }

    /**
    * Una función "particion_1_2" que realiza la partición 1/2
    * @return Parejainsert el par clave/dirección que deberá insertse en el padre.
    */
    private Parejainsert particion_1_2(Nodo externo){
        Parejainsert nueva;
        int nuevoClaves = (orden  - 1 )/ 2; // Establece el número de claves
        int viejasClaves = (orden - 1) - nuevoClaves;
        Nodo nuevo = new Nodo();
        nuevo.posicion = auxFA.tomarPágina();
        nuevo.numClaves = nuevoClaves;
        nuevo.enlaces[0] = externo.enlaces[viejasClaves + 1];
        for(int i = 1; i <= nuevo.numClaves; i++){
            nuevo.claves[i] = externo.claves[i + viejasClaves + 1];
            nuevo.enlaces[i] = externo.enlaces[i + viejasClaves + 1];
        }
        externo.numClaves = viejasClaves;
        nueva = new Parejainsert(externo.claves[viejasClaves + 1], nuevo.posicion);
        auxFA.escribir(nuevo.aByte(), nuevo.posicion);
        auxFA.escribir(externo.aByte(), externo.posicion);
        return nueva;
    }

    /**
    * Una función "particion 2_3" que realiza la partición 2/3 a partir de los nodos padre, izq  der
    * y la posición del enlace que  apunta a izq,posizq
    * @void
    */
    private void particion_2_3(Nodo padre, int posizq,  Nodo izq, Nodo der){
        int clavesRepartir = izq.numClaves + der.numClaves - 1;
        Nodo nuevo = new Nodo();
        nuevo.posicion = auxFA.tomarPágina();
        // Fija el número de claves para los tres nodos
        int nizq = clavesRepartir / 3;
        int nnuevo = (clavesRepartir + 1 ) / 3;
        int nder = (clavesRepartir + 2 ) / 3;
        int antncder = der.numClaves;
        int antcizq = izq.numClaves;
        // Se inserta en e padre la clave y la dirección correspondiente
        padre.insert(posizq + 1, izq.claves[nizq + 1], nuevo.posicion);
        nuevo.numClaves = nnuevo;
        nuevo.enlaces[0] = izq.enlaces[nizq + 1];
        // Se pasa de izq a nuevo los enlaces y claves correspondientes
        for(int i = nizq + 2; i <= antcizq; i++){
            nuevo.claves[i - nizq - 1] = izq.claves[i];
            nuevo.enlaces[i - nizq - 1] = izq.enlaces[i]; 
        }
        izq.numClaves = nizq;
        nuevo.claves[antcizq - nizq] = padre.claves[posizq + 2]; // Se pasa de padre a nuevo la clave correspondiente
        int pos = antcizq - nizq;
        nuevo.enlaces[pos] = der.enlaces[0];
        // Se pasa de der a nuevo los enlaces y claves correspondientes
        for(int i = pos + 1; i <= nder; i++){
            nuevo.claves[i] = der.claves[i - pos];
            nuevo.enlaces[i] = der.enlaces[i - pos];
        }
        int npasar = antncder - nder;
        padre.claves[posizq + 2] = der.claves[npasar]; // Se sustituye en padre por la clave correspondiente de der
        der.enlaces[0] = der.enlaces[npasar];
        // Se desplazan las claves y el resto de los enlaces de der
        for(int i = npasar + 1; i <= antncder; i++){
            der.claves[i - npasar] = der.claves[i];
            der.enlaces[i - npasar] = der.enlaces[i];
        }
        der.numClaves = nder;
        auxFA.escribir(der.aByte(), der.posicion);
        auxFA.escribir(izq.aByte(), izq.posicion);
        auxFA.escribir(nuevo.aByte(), nuevo.posicion);
    }

    /**
    * Una función "recombinacion_2_1" realiza la recombinación 2/1 a partir de los
    * nodos padre,izq y der; y la posición del enlace que apunta a izq,posizq
    * @void
    */
    private void recombinacion_2_1(Nodo padre, int posizq, Nodo izq, Nodo der){
        int antncizq = izq.numClaves;
        // Se baja la clave discriminante en el padre al final del izquierdo
        izq.numClaves = izq.numClaves + 1 + der.numClaves;
        izq.claves[antncizq + 1] = padre.claves[posizq + 1];
        izq.enlaces[antncizq + 1] = der.enlaces[0]; // Se pasa el enlace 0 de der a izq
        // Se pasan de der a izq los enlaces y claves correspondientes
        for (int i = 1; i<= der.numClaves;i++) {
            izq.claves[antncizq + 1 + i] = der.claves[i];
            izq.enlaces[antncizq + 1 + i] = der.enlaces[i];
        }
        // Se extraen de padre la clave y el enlace a der
        padre.remove(posizq + 1);
        auxFA.escribir(izq.aByte(), izq.posicion);
        auxFA.liberarPágina(der.posicion); // Se libera der
    }

    /**
    * Una función "recombinación_3_2" realiza la recombinación 3/2 a partir de los
    * nodos padre,izq y der; y la posición del enlace que apunta a min,posMin
    * @void
    */
    private void recombiancion_3_2(Nodo padre, int posMin, Nodo izq, Nodo min, Nodo der){
        int repartir = izq.numClaves + min.numClaves + der.numClaves + 1;
        // Fija el número de claves para los dos nodos
        int nder = repartir / 2;
        int nizq = repartir - nder;
        int izqant = izq.numClaves;
        int derant = der.numClaves;
        // Modificamos el cardinal y ponemos las claves adecuadas
        izq.numClaves = nizq;
        izq.claves[izqant + 1] = padre.claves[posMin];
        izq.enlaces[izqant + 1] = min.enlaces[0];
        // Pasamos ahora del minimo hacia el izq
        for(int i = izqant + 2; i <= nizq; i++){
            izq.claves[i] = min.claves[i - izqant - 1];
            izq.enlaces[i] = min.enlaces[i - izqant - 1];
        }
        // Modificamos el cardinal del derecho
        // Abrimos huecos para las claves nescesarias
        // Desplazamos para abrir hueco y metemos las claves
        der.numClaves = nder;
        int pasarDer = nder - derant;
        for(int i = derant; i >= 1; i--){
            der.claves[i+pasarDer] = der.claves[i];
            der.enlaces[i+pasarDer] = der.enlaces[i];
        }
        der.enlaces[pasarDer] = der.enlaces[0];
        der.claves[pasarDer] = padre.claves[posMin+1];
        // Se pasa de der a min los enlaces y claves correspondientes
        for(int i = pasarDer - 1; i >= 1; i--) {
            der.claves[i] = min.claves[min.numClaves + i - pasarDer + 1];
            der.enlaces[i] = min.enlaces[min.numClaves + i - pasarDer + 1];
        }
        der.enlaces[0] = min.enlaces[min.numClaves - pasarDer + 1]; // Primer enlace de der
        // Borramos ahora el nodo bajo minimo
        auxFA.liberarPágina(min.posicion);
        auxFA.escribir(izq.aByte(), izq.posicion);
        auxFA.escribir(der.aByte(), der.posicion);
        // Extraemos del padre
        padre.remove(posMin);
        padre.claves[posMin] = min.claves[min.numClaves - pasarDer + 1]; // Se pasa a padre la clave correspondiente de min
    }

    /**
    * Función "elementos" que devuelve un vector de enteros ordenados de menor a mayor con los elementos que se encuentren en el contenedor.
    * @return vector de enteros ordenado de menor a mayor que representa al árbol B
    * Esta Función hace uso de un método auxiliar recursivo
    */
    public int[] elementos() {
        int[] res = new int[cardinal];
        elementosRecursivo(raiz, res, 0);
        return res;
    }

    /**
     * Método auxiliar recusivo para la función "elementos"
     */
    private int elementosRecursivo(int dir, int[] res, int pos) {
        Nodo aux = new Nodo();
        if (dir != FicheroAyuda.dirNula) {
            byte[] datos = auxFA.leer(dir);
            aux.deByte(datos);
            pos = elementosRecursivo(aux.enlaces[0], res, pos);
            for (int i = 1; i<=aux.numClaves; i++){
                res[pos++] = aux.claves[i];
                pos = elementosRecursivo(aux.enlaces[i], res, pos);
            }
        }
        return pos;
    }

    /**
    * Función "remove" que extrae del contenedor el elemento pasado por parámetro, devuelve verdadero si lo eliminó y falso en caso contrario. 
    * @param num elemento a remove
    * @return verdadero si lo extrajo y falso si no extrajo el elemento del contenedor
    */
    public boolean remove(int num) {
        Stack<Pareja> pila = new Stack<Pareja>();
        if(search(num, pila) == false) { // Si el elemento no está no se hace nada
            return false;
        }
        Nodo actual = new Nodo();
        auxFA.adjunto(3, cardinal-1); // Decrementa el cardinal
        cardinal = cardinal-1;
        Pareja info = pila.pop();
        int pos = info.posicion;
        actual = info.nodo;
        if(actual.enlaces[0] != FicheroAyuda.dirNula) {
            /* En este caso el elemento está en un nodo no hoja
             se sustituye por el sucesor */
            Pareja p = new Pareja();
            p.posicion = pos;
            p.nodo = info.nodo.copia();
            pila.add(p);
            int dir = actual.enlaces[pos];
            /* Creamos una cola para guardar el camino recorrido sin
              dejar de tener en la cima de la pila el nodo que 
              contiene el elemento a remove */
            LinkedList<Pareja> cola = new LinkedList<Pareja>();
            while(dir != FicheroAyuda.dirNula) {
                byte[] datos = auxFA.leer(dir);
                actual.deByte(datos); // Modifica lo q hay en la pila FALLO
                dir = actual.enlaces[0];
                if(dir == FicheroAyuda.dirNula) {
                    pos = 1;
                } else {
                    pos = 0;
                }
                cola.addLast(new Pareja(actual, pos));
             }
            info = pila.pop();
            info.nodo.claves[info.posicion] = actual.claves[1];
            auxFA.escribir(info.nodo.aByte(), info.nodo.posicion);
            pila.add(info); // Posible nuevo fallo
            // Pasamos a la pila el camino de la cola
            while(cola.isEmpty() == false) {
                actual = cola.getFirst().nodo;
                pila.add(cola.getFirst());
                cola.removeFirst();
            }
            info = pila.pop();
            pos =info.posicion;
            actual = info.nodo;
        }
        actual.remove(pos); // Extraemos en el nodo hoja
        // Resolvemos la situación bajo mínimos si se da
        while(actual.numClaves < (orden+1)/2 - 1 && actual.posicion != raiz) {
            Nodo padre;
            Nodo der = new Nodo();
            Nodo izq = new Nodo();
            info = pila.pop();
            padre = info.nodo;
            pos = info.posicion;
            if(pos < padre.numClaves) {
                byte[] datos = auxFA.leer(padre.enlaces[pos+1]);
                der.deByte(datos);
                if(der.numClaves > (orden+1)/2 - 1) { // Podemos rotar con el hermano derecho
                    rotacionDerIzq(padre, der, actual, pos);
                    return true;
                }
            }
            if(pos > 0) {
                byte[] datos = auxFA.leer(padre.enlaces[pos-1]);
                izq.deByte(datos);
                if(izq.numClaves > (orden+1)/2 - 1) { // Podemos rotar con el hermano izquierdo
                    rotacionIzqDer(padre, actual, izq, pos-1);
                    return true;
                }
            }
            // Recombinacion
            if(pos > 0 && pos < padre.numClaves) {
                // Tiene dos hermanos por lo que recomb 3_2
                recombiancion_3_2(padre, pos, izq, actual, der);
            } else {
                if(pos > 0) { // Recombinamos con el izquierdo
                    recombinacion_2_1(padre, pos-1, izq, actual);
                } else { // Recombinamos con el derecho
                    recombinacion_2_1(padre, pos, actual, der);
                }
            }
            actual = padre;
        }
        // Cuando se ha resuelto la situación bajo mínimo, se actualiza actual
        if(actual.numClaves > 0) {
            auxFA.escribir(actual.aByte(), actual.posicion);
        } else {
            if(cardinal == 0) {
                empty();
            } else {
                raiz = actual.enlaces[0];
                auxFA.liberarPágina(actual.posicion);
                auxFA.adjunto(1, raiz);
            } 
        }
        return true;
    }
}