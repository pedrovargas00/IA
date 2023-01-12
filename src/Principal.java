import javax.swing.JFrame;

public class Principal {
    
    public static void main(String args[]) {
        
        Puzzle8 juego = new Puzzle8();
        Puzzle8_GUI vista = new Puzzle8_GUI();
        Controlador controlador = new Controlador();
        
        
        juego.setControlador(controlador);
        vista.setControlador(controlador);
        controlador.setJuego(juego);
        controlador.setVista(vista);
        
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    
    }
        
}
