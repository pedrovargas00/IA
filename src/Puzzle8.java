import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*La práctica se realizó implementando el MVC*/
public class Puzzle8{
    
    private int matrizEntrada[][];
    private int matrizMeta[][];
    private int totalNodos;
    private int recorridos;
    private int tipoCamino;
    private long startTime;
    private long endTime;
    private Queue<Nodo> cola;
    private Stack<Nodo> pila;
    private Nodo raiz;
    private ArrayList<Nodo> arbolSolucion;
    private ArrayList<Nodo> nodos;
    private ArrayList<Nodo> hijos;
    private Hashtable<Integer, Long> valores;
    private Controlador controlador;
    
    public Puzzle8(){
        
        this.matrizEntrada = new int[3][3];
        this.matrizMeta = new int[3][3];
        this.totalNodos = 0;
        this.tipoCamino = 0;
        this.cola = new LinkedList();
        this.pila = new Stack();
        this.arbolSolucion = new ArrayList();
        this.nodos = new ArrayList();
        this.hijos = new ArrayList();
        this.valores = new Hashtable<Integer, Long>();
        this.startTime = 0;
        this.endTime = 0;
        this.recorridos = 0;
    }
    
    public void setControlador(Controlador controlador){
        
        this.controlador = controlador;
    }
    
    /*Inicia las estructuras de datos, la matriz inicial y crea el nodo raíz*/
    public int[][] iniciar(){
        this.valores.clear();
        this.matrizMeta = controlador.iniciarMeta();
        /*System.out.println("\nMeta");
        for(int i = 0; i < 3; i++){
            System.out.println("");
            for(int j = 0; j < 3; j++)
                System.out.print(matrizMeta[i][j] + " ");
        }*/
        this.matrizEntrada = obtenerMatrizEntrada(50);
        this.raiz = new Nodo(matrizEntrada, null);
        this.nodos.add(raiz);
        this.cola.add(raiz);
        this.pila.add(raiz);
        
        /*System.out.println("\nEntrada");
        for(int i = 0; i < 3; i++){
            System.out.println("");
            for(int j = 0; j < 3; j++)
                System.out.print(matrizEntrada[i][j] + " ");
        }*/
        
        return matrizEntrada;
    }
    /*
    La matriz de entrada se crea a partir de la matriz meta, ya que, una vez
    que se aplica el recorrido, la cantidad de nodos no llega a ser demasiada
    y así se evitan crasheos por falta de recursos computacionales.
    Se recibe un n que es el número de cambios de bloque que se realizarán.
    */
    private int[][] obtenerMatrizEntrada(int n){
        
        int matriz[][] = new int[3][3];
        int cero = 0;
        
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                matriz[i][j] = matrizMeta[i][j];
        
        for(int i = 0; i < n; i++){
            //arriba -> 0. abajo -> 1, izquierda -> 2, derecha -> 3
            cero = (int)(Math.random() * 4);
            switch(encontrarCero(matriz)){
                case "00":
                    if(cero == 1)
                        matriz = cambio(matriz, 0, 0, 1, 0);
                    if(cero == 3)
                        matriz = cambio(matriz, 0, 0, 0, 1);
                    break;
                case "01":
                    if (cero == 1)
                        matriz = cambio(matriz, 0, 1, 1, 1);
                    if(cero == 2)
                        matriz = cambio(matriz, 0, 1, 0, 0);
                    if (cero == 3)
                        matriz = cambio(matriz, 0, 1, 0, 2);
                    break;
                case "02":
                    if(cero == 1)
                        matriz = cambio(matriz, 0, 2, 1, 2);
                    if(cero == 2)
                        matriz = cambio(matriz, 0, 2, 0, 1);
                    break;
                case "10":
                    if (cero == 0)
                        matriz = cambio(matriz, 1, 0, 0, 0);
                     if (cero == 1)
                        matriz = cambio(matriz, 1, 0, 2, 0);
                    if (cero == 3)
                        matriz = cambio(matriz, 1, 0, 1, 1);
                case "11":
                    if (cero == 0)
                        matriz = cambio(matriz, 1, 1, 0, 1);
                    if (cero == 1)
                        matriz = cambio(matriz, 1, 1, 2, 1);
                    if (cero == 2)
                        matriz = cambio(matriz, 1, 1, 1, 0);
                    if (cero == 3)
                        matriz = cambio(matriz, 1, 1, 1, 2);
                    break;
                case "12":
                    if(cero == 0)
                        matriz = cambio(matriz, 1, 2, 0, 2);
                    if(cero == 1)
                        matriz = cambio(matriz, 1, 2, 2, 2);
                    if(cero == 2)
                        matriz = cambio(matriz, 1, 2, 1, 1);
                    break;
                case "20":
                    if (cero == 0)
                        matriz = cambio(matriz, 2, 0, 1, 0);
                    if (cero == 3)
                        matriz = cambio(matriz, 2, 0, 2, 1);
                    break;
                case "21":
                    if (cero == 0)
                        matriz = cambio(matriz, 2, 1, 1, 1);
                    if (cero == 2)
                        matriz = cambio(matriz, 2, 1, 2, 0);
                    if (cero == 3)
                        matriz = cambio(matriz, 2, 1, 2, 2);
                    break;
                case "22":
                    if (cero == 0)
                        matriz = cambio(matriz, 2, 2, 1, 2);
                    if (cero == 2)
                        matriz = cambio(matriz, 2, 2, 2, 1);
                    break;
            }
        }
        
        return matriz;
    }
    
    ////////////////////////////////////////////////////////////////////////
    //Búsqueda Ciega
    
    //Recorrido por produndidad
    public void DFS(){
         
        startTime = System.currentTimeMillis();
        setValores(totalNodos, 0);
         
        while(!pila.isEmpty())
            if (pila.peek() != null)
                if (compruebaSolucion(pila.pop()))
                    break;
            
    }
    
    //Recorrido por anchura
    public void BFS(){
        
        startTime = System.currentTimeMillis();
        setValores(totalNodos, 0);
        tipoCamino = 1;
        
        while(!cola.isEmpty())
            if (cola.peek() != null)
                if (compruebaSolucion(cola.poll()))
                    break;
            
    }
    
    /*Verifica si el nodo actual es igual al nodo solución
    * En caso de que sea cierto, genera el camino solución desde el nodo
      actual hasta el nodo raíz. En caso contrario, crea los hijos del
      nodo actual.
    */
    private boolean compruebaSolucion(Nodo nodo){
        
        if(verificarSolucion(nodo)){
            generarSolucion(nodo);
            return true;
        }
        agregarHijos(nodo);
        return false;
    }
    
    //Compara la matriz del nodo actual con la matriz meta.
    private boolean verificarSolucion(Nodo nodo){
        
        return Arrays.deepEquals(nodo.getMatriz(), matrizMeta);
    }
    
    /* Crea todos los hijos del nodo actual en base a la posición del cero,
     * la cual se obtiene mediante el método encontrarCero(). Los movimientos
     * se realizan en base al cero.
    */
    private void agregarHijos(Nodo nodo){
        
        //System.out.println("\nAgregar Hijos");
        //nodo.imprimirMatriz();
        switch(encontrarCero(nodo.getMatriz())){
            case "00":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 0, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 0, 1, 0));
                break;
            case "01":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 0, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 0, 2));
                break;
            case "02":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 2, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 2, 1, 2));
                break;
            case "10":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 0, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 2, 0));
                break;
            case "11":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 1, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 1, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 2, 1));
                break;
            case "12":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 0, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 2, 2));
                break;
            case "20":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 0, 1, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 0, 2, 1));
                break;
            case "21":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 2, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 2, 2));
                break;
            case "22":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 2, 1, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 2, 2, 1));
                break;
        }
    }
    
    ///////////////////////////////////////////////////////////////////////
    //Búsqueda heurística
    
    //Método primero el mejor
    public void BF(){
        
        startTime = System.currentTimeMillis();
        setValores(totalNodos, 0);
        tipoCamino = 2;
        
        while(!pila.isEmpty())
            if (pila.peek() != null)
                if (solucionBF(pila.pop()))
                    break;
    }
    
    /* Este método revisa si la matriz del nodo es igual a la matriz meta.
     * Esto lo hace mediante la función H que es la cantidad de elementos de
     * una matriz que están en distinta posición respecto a la matriz meta
     * incluyendo el cero. Si la función es igual a cero, entonces el nodo es
     * la solución, en caso contrario, se crearán los hijos de ese nodo.
    */
    private boolean solucionBF(Nodo nodo){
        
        //System.out.println("Verificar Funcion");
        //nodo.imprimirMatriz();
        //Caso padre
        if(nodo.getH() == -1)
            nodo.setH(generarFuncion(nodo.getMatriz()));
        
        if(nodo.getH() == 0){
            generarSolucion(nodo);
            return true;
        }
        agregarHijosBF(nodo);
        //System.out.println("Sale verificarFuncion");
        return false;
    }
    
    /* Este método es similar al método agregarHijos() anterior, con
     * la diferencia de que después de crear a los hijos del nodo actual,
     * revisa cuál hijo tiene la función H más cercana a 0, ya que, este
     * será el mejor camino a seguir.
    */
    private void agregarHijosBF(Nodo nodo){
        
        //System.out.println("\nAgregar Hijos");
        //nodo.imprimirMatriz();
        switch(encontrarCero(nodo.getMatriz())){
            case "00":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 0, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 0, 1, 0));
                ramaCamino();
                break;
            case "01":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 0, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 0, 2));
                ramaCamino();
                break;
            case "02":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 2, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 2, 1, 2));
                ramaCamino();
                break;
            case "10":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 0, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 2, 0));
                ramaCamino();
                break;
            case "11":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 1, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 1, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 2, 1));
                ramaCamino();
                break;
            case "12":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 0, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 2, 2));
                ramaCamino();
                break;
            case "20":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 0, 1, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 0, 2, 1));
                ramaCamino();
                break;
            case "21":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 2, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 2, 2));
                ramaCamino();
                break;
            case "22":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 2, 1, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 2, 2, 1));
                ramaCamino();
                break;
        }
    }
    
    /*Seleeciona el mejor camino en base a la función H de los nodos*/
    private void ramaCamino(){
        
        Nodo n;
        
        //System.out.println("Selecciona camino");
        //nodos.add(n);
        //n.setH(generarFuncion(hijo));
        
        /*
         * El error está aquí, si todos los hijos de un nodo están repetidos,
         * el arrayList hijos estará vacío, por lo que n será null. Además,
         * como todos los hijos están repetidos, la cola se mantiene vacía.
         * esto mismo se repite con A*.
        */
        n = seleccionCamino();
        if(n != null){
            hijos.removeAll(hijos);
            //pila.removeAll(pila);
            pila.add(n); 
            totalNodos++;
        }else{ hijos.removeAll(hijos); }
        //System.out.println("Camino seleccionado: ");
        //n.imprimirMatriz();
        //System.out.println("");
    }
    
    /* A partir de los hijos de un nodo, busca cuál de ellos tiene la menor
     * función H entre sí, dicho nodo se regresa.
     *
    */
    private Nodo seleccionCamino(){
        
        //System.out.println("Hijos " + hijos.toArray());
        if(hijos.isEmpty()){
            //nuevoCamino(nodo, 0);
            //System.out.println("Vacío");
            return null;    
        }
        
        Nodo menor = hijos.get(0);
        
        for(int i = 1; i < hijos.size(); i++)
            if(hijos.get(i).getH() < menor.getH())
                menor = hijos.get(i);
         
        //System.out.println("Camino regresa: " + menor.getH());
        return menor;
        
    }
    
    //Método A*
    public void aStar(){
        
        startTime = System.currentTimeMillis();
        setValores(totalNodos, 0);
        tipoCamino = 3;
        
        while(!pila.isEmpty())
            if (pila.peek() != null)
                if (solucionAstar(pila.pop()))
                    break;
    }
    
    /* Obtiene la solución si la función F es igual a 0, ya que, esto indica
     * que la matriz del nodo actual es igual a la matriz meta. Dicha función
     * es la suma de las funciones G y H.
    */
    private boolean solucionAstar(Nodo nodo){
        
        //Caso padre
        if(nodo.getF() == -1){
            nodo.setG(generarFuncion(nodo.getMatriz()));
            nodo.setH(altura(nodo, 0));
            nodo.setF();
        }
        
        if(nodo.getG() == 0){
            generarSolucion(nodo);
            return true;
        }
        agregarHijosAstar(nodo);
        //System.out.println("Sale verificarFuncion");
        return false;
    }
    
    private int altura(Nodo nodo, int i){
        
        if(nodo.equals(raiz))
            return i;
        
        return altura(nodo.getPadre(), i++);
    }
    
    //Similar al método anterior, pero utiliza las funciones para A*
    private void agregarHijosAstar(Nodo nodo){
        
        //System.out.println("\nAgregar Hijos");
        //nodo.imprimirMatriz();
        switch(encontrarCero(nodo.getMatriz())){
            case "00":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 0, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 0, 1, 0));
                ramaCaminoAstar();
                break;
            case "01":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 0, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 1, 0, 2));
                ramaCaminoAstar();
                break;
            case "02":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 2, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 0, 2, 1, 2));
                ramaCaminoAstar();
                break;
            case "10":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 0, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 0, 2, 0));
                ramaCaminoAstar();
                break;
            case "11":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 0, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 1, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 1, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 1, 2, 1));
                ramaCaminoAstar();
                break;
            case "12":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 0, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 1, 2, 2, 2));
                ramaCaminoAstar();
                break;
            case "20":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 0, 1, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 0, 2, 1));
                ramaCaminoAstar();
                break;
            case "21":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 2, 0));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 1, 1));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 1, 2, 2));
                ramaCaminoAstar();
                break;
            case "22":
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 2, 1, 2));
                revisarPadre(nodo, cambio(nodo.getMatriz(), 2, 2, 2, 1));
                ramaCaminoAstar();
                break;
        }
    }
    
    //Obtiene el mejor camino y reinicia las estructuras de datos.
    private void ramaCaminoAstar(){
        
        Nodo n;
        
        //System.out.println("Rama camino");
        //nodos.add(n);
        //n.setH(generarFuncion(hijo));
        n = seleccionCaminoAstar();
        if(n != null){
            //pila.removeAll(pila);
            hijos.removeAll(hijos);
            pila.add(n); 
            totalNodos++;
        }else{ hijos.removeAll(hijos); }
        //System.out.println("Sale rama camino");
        //System.out.println("Camino seleccionado: ");
        //n.imprimirMatriz();
        //System.out.println("")
    }
    
    //Selecciona el nodo que tenga la menor función F.
    private Nodo seleccionCaminoAstar(){
        
        //System.out.println("Selección Camino");
        if(hijos.isEmpty())
            return null;
        
        Nodo menor = hijos.get(0);
        
        for(int i = 1; i < hijos.size(); i++)
            if(hijos.get(i).getF() < menor.getF())
                menor = hijos.get(i);
         
        //System.out.println("Camino regresa: " + menor.getG());
        return menor;
        
    }
    
    /* Este método agrega un nodo hijo al arreglo de nodos, a la estructura de
     * datos y genera las funciones dependiendo del método que se utilizó
     * para solucionar el puzzle.
     * Si un nodo ya existe, no se agrega a ninguna de las anteriores.
    */
    private void revisarPadre(Nodo nodo, int hijo[][]){

        Nodo n = new Nodo(hijo, nodo);
        
        //System.out.println("Nodo entrante");
        //nodo.imprimirMatriz();
        //System.out.println("\n");
        /*System.out.println("Hijo entrante");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++)
                System.out.print(hijo[i][j] + " ");
            System.out.println("");
        }*/
        //System.out.println("\nRevisar Padre");
        System.out.println("Total: " + totalNodos);
        //System.out.println("Nodo: " + n.toString());
        if(nodo.getPadre() != null){
            if(revisarNodosCreados(hijo)){
                //////////////////////////////////////////////////////////////////////////////////
                if(totalNodos % 10 == 0)
                    setValores(totalNodos, System.currentTimeMillis() - startTime);
                switch(tipoCamino){
                    case 0:
                        pila.push(n);
                        nodos.add(n);
                        totalNodos++;
                        break;
                    case 1:
                        cola.add(n); 
                        nodos.add(n);
                        totalNodos++;
                        break;
                    case 2:
                        //System.out.println("- Nodo: " + n.toString());
                        //System.out.println("Agrega 2");
                        //n.imprimirMatriz();
                        nodos.add(n);
                        n.setH(generarFuncion(hijo));
                        hijos.add(n);
                        pila.add(n);
                        totalNodos++;
                        break;
                    case 3:
                        nodos.add(n);
                        n.setG(generarFuncion(hijo));
                        n.setH(altura(n, 0));
                        n.setF();
                        hijos.add(n);
                        pila.add(n);
                        totalNodos++;
                        break;
                }
            }
        }
        else{ 
            switch(tipoCamino){
                case 0:
                    pila.push(n);
                    nodos.add(n);
                    totalNodos++;
                    break;
                case 1:
                    cola.add(n);
                    nodos.add(n);
                    totalNodos++;
                    break;
                case 2:
                    //System.out.println("---Agrega 2---");
                    //n.imprimirMatriz();
                    nodos.add(n);
                    n.setH(generarFuncion(hijo));
                    hijos.add(n);
                    pila.add(n);
                    totalNodos++;
                    break;
                case 3:
                    nodos.add(n);
                    n.setG(generarFuncion(hijo));
                    n.setH(altura(n, 0));
                    n.setF();
                    hijos.add(n);
                    pila.add(n);
                    totalNodos++;
                    break;
            }
        }
        
        //Imprimir cola
        /*System.out.println("\nCola");
        Object array[] = cola.toArray();
        Nodo nd;
        
        //Displaying Array content
        System.out.println("Array Elements: " + array.length + " -> ");
        for(int i = 0; i < array.length; i++){
            System.out.println("Regresa de cola: " + (Nodo) array[i] + "\n---------\n");
            nd = (Nodo) array[i];
            nd.imprimirMatriz();
            System.out.println("\n");
        }*/
    }
    
    //Crea la función H, compara la matriz recibida con la matriz meta
    //Cada elemento que sea distinto aumenta el contador.
    private int generarFuncion(int hijo[][]){
        
        int diferencia = 0;
        
        for(int i = 0; i < 3; i++)
           for(int j = 0; j < 3; j++)
               if(hijo[i][j] != matrizMeta[i][j])
                   diferencia++;
        
        return diferencia;
    }
    
    //Genera la rama solución y arroja la información a la interfaz.
    private void generarSolucion(Nodo nodo){
        
        Hilo hilo;
        
        while(nodo.getPadre() != null){
            arbolSolucion.add(nodo);
            nodo = nodo.getPadre();
        }
        arbolSolucion.add(nodo);
        System.out.println("*******SOLUCIÓN*******");
        recorridos = arbolSolucion.size();
        hilo = new Hilo(arbolSolucion);
        hilo.setControlador(this.controlador);
        hilo.start();
        endTime = System.currentTimeMillis() - startTime;
        setValores(totalNodos, endTime);
        System.out.println(startTime+"    "+endTime);
        controlador.actualizarInformacion(String.valueOf(totalNodos), String.valueOf(recorridos), String.valueOf(endTime));
    }
    
    //Intercambia la posición del espacio.
    private int[][] cambio(int[][] matriz, int filaVacio,int colVacio,int filaNum,int colNum){
        
        int num = matriz[filaNum][colNum];
        int cero = matriz[filaVacio][colVacio];
        
        //System.out.println("Num: " + num + " Cero: " + 0);
        if (cero == 0){
            matriz[filaNum][colNum] = cero;
            matriz[filaVacio][colVacio] = num;
        }  
        
        return matriz;
    }
    
    //Compara la matriz de cada nodo creado con la matriz recibida.
    private boolean revisarNodosCreados(int hijo[][]){
        
        /*Scanner n = new Scanner(System.in);
        
        System.out.println("\nEntra");
        for(int i = 0; i < 3; i++){
            System.out.println("");
            for(int j = 0; j < 3; j++)
                System.out.print(hijo[i][j] + " ");
        }*/
        
        for (int i = 0; i < nodos.size(); i++)
            if (Arrays.deepEquals(nodos.get(i).getMatriz(), hijo)){
                /*System.out.println("\nMatriz repetida");
                nodos.get(i).getMatriz();
                System.out.println("");
                n.next();*/
                return false;
            }
        
        return true;
    }
    
    private String encontrarCero(int matriz[][]){
        
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) 
                if(matriz[i][j] == 0)
                    return ""+i+j;
                
        return "";
    }
    
    public void setValores(int nodos, long tiempo){valores.put(nodos, tiempo);}
    public Hashtable<Integer, Long> getValores(){ return valores; }
    public int getNumNodos(){ return totalNodos; }
    public int getNumNiveles(){ return recorridos; }
    public long getTiempo(){ return endTime; }
    
    public void limpiar(){
        this.totalNodos = 0;
        this.tipoCamino = 0;
        this.cola.removeAll(cola);
        this.pila.removeAll(pila);
        this.arbolSolucion.removeAll(arbolSolucion);
        this.nodos.removeAll(nodos);
        this.hijos.removeAll(hijos);
        //this.valores.clear();
        this.startTime = 0;
        this.endTime = 0;
        this.recorridos = 0;
    }
    ////////////////////////////////////////////////
    //Genético
    
    
}
