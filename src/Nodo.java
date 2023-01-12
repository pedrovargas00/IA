
public class Nodo {
    
    private int matriz[][];
    private Nodo padre;
    private int f, g, h;
    private int hijos;
    
    public Nodo(int matriz[][], Nodo padre){
        
        this.padre = padre;
        this.matriz = matriz;
        this.hijos = 0;
        this.f = -1;
        this.g = -1;
        this.h = -1;        
    }
    
    public void setF(){ f = g + h; }
    
    public void setG(int g){ this.g = g; }
    
    public void setH(int h){ this.h = h; }
    
    public void setHijos(){ this.hijos++; }
    
    public int getF(){ return this.f; }
    
    public int getG(){ return this.g; }
    
    public int getH(){ return this.h; }
    
    public int getHijos(){ return this.hijos; }
    
    public Nodo getPadre(){ return this.padre; }
    
    public int[][] getMatriz(){
        
        int[][] matriz = new int[3][3];
        
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                matriz[i][j] = this.matriz[i][j];
        
        return matriz;
    }
    
    public void imprimirMatriz(){
        
        for(int i = 0; i < 3; i++){
            System.out.println("");
            for(int j = 0; j < 3; j++)
                System.out.print(this.matriz[i][j] + " ");
        }
    }
}
