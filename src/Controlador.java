
import java.util.Hashtable;


public class Controlador {
    
    private Puzzle8 juego;
    private Puzzle8_GUI vista;
    
    public Controlador(){}
    
    public void setJuego(Puzzle8 juego){
        
        this.juego = juego;
    }
    
    public void setVista(Puzzle8_GUI vista){
        
        this.vista = vista;
    }
    
    public void activar(){    
        
        vista.setRandom(juego.iniciar());   
    }
    
    public int[][] iniciarMeta(){
        
        int m[][] = new int[3][3];
        
        if(vista.getM0Label() == " ")
            m[0][0] = 0;
        else
            m[0][0] = Integer.parseInt(vista.getM0Label());
        if(vista.getM1Label() == " ")
            m[0][1] = 0;
        else
            m[0][1] = Integer.parseInt(vista.getM1Label());
        if(vista.getM2Label() == " ")
            m[0][2] = 0;
        else
            m[0][2] = Integer.parseInt(vista.getM2Label());
        if(vista.getM3Label() == " ")
            m[1][0] = 0;
        else
            m[1][0] = Integer.parseInt(vista.getM3Label());
        if(vista.getM4Label() == " ")
            m[1][1] = 0;
        else
            m[1][1] = Integer.parseInt(vista.getM4Label());
        if(vista.getM5Label() == " ")
            m[1][2] = 0;
        else
            m[1][2] = Integer.parseInt(vista.getM5Label());
        if(vista.getM6Label() == " ")
            m[2][0] = 0;
        else
            m[2][0] = Integer.parseInt(vista.getM6Label());
        if(vista.getM7Label() == " ")
            m[2][1] = 0;
        else
            m[2][1] = Integer.parseInt(vista.getM7Label());
        if(vista.getM8Label() == " ")
            m[2][2] = 0;
        else
            m[2][2] = Integer.parseInt(vista.getM8Label());
    
        return m;
    }
    
    public void dfs(){
        
        juego.DFS();
    }
    
    public void bfs(){
        
        juego.BFS();
    }
    
    public void bf(){
        
        juego.BF();
    }
    
    public void aStar(){
        
        juego.aStar();
    }
    
    public void actualizarInformacion(String nodos, String niveles, String tiempo){
        
        vista.setNumNodos(nodos);
        vista.setNumNiveles(niveles);
        vista.setTiempo(tiempo);
    }
    
    public void obtenerSolucion(int matriz[][]){
        
        vista.setRandom(matriz);
    }
    
    public Hashtable<Integer, Long> getValores(){ return juego.getValores(); }
    
    public void limpiar(){
        juego.limpiar();
    }
}
