import java.util.ArrayList;

public class Hilo extends Thread{
    
    private ArrayList<Nodo> solucion;
    private Controlador controlador;
    
    public Hilo(ArrayList<Nodo> solucion){
        
        this.solucion = solucion;
    }
    
    public void setControlador(Controlador controlador){
        
        this.controlador = controlador;
    }
    
    public void run(){
        
        for(int i = solucion.size() - 1; i >= 0 ; i--){
            controlador.obtenerSolucion(solucion.get(i).getMatriz());
            try {
                Thread.sleep(600);
            } catch (InterruptedException ex) {
                System.out.println(ex);;
            }
        }
        controlador.limpiar();
        
    }
    
}
