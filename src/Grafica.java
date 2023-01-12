import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica{
    
    private JFreeChart grafica;
    private XYDataset datos;
    private XYSeries serie;
    private JLabel grafico;
    
    public Grafica(){
        serie = new XYSeries("Nodos - Tiempo");
    }
    
    public void agregarDatos(int x, long y){
        System.out.println("Agregando datos");
        serie.add(x, y);
        
    }
    
    public void iniciarGrafica(String titulo, String ejeX, String ejeY, JPanel pnl){
        System.out.println("Iniciar grafica");
        datos = new XYSeriesCollection(serie);
        
        grafica = ChartFactory.createXYLineChart(titulo, ejeY, ejeX, datos,
                PlotOrientation.VERTICAL, true, true, false);
        
        BufferedImage graficotorta = grafica.createBufferedImage(650, 450);
        ImageIcon img = new ImageIcon(graficotorta);
        grafico = new JLabel(img);

        //add();
    }
    
    
}
