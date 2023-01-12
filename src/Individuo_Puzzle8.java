import java.util.ArrayList;
import java.util.Random;
import java.util.stream.DoubleStream;

public class Individuo_Puzzle8 implements Cloneable{
    
    private int valorIndividuo[][];
    private Random random;
    private int n;
    private int lower[];
    private int upper[];
    
    public Individuo_Puzzle8(int n, int lower[], int upper[]){
        
        this.n = n;
        this.lower = lower;
        this.upper = upper;
        this.random = new Random();
        this.valorIndividuo = new int[n][n]; //valores para n
        generarDimension();
    }
    
    public Individuo_Puzzle8(int n,int[][] matriz){
        this.n = n;
        this.valorIndividuo = matriz;
    }
    
    private void generarDimension(){
        
        int dimensionLowerBounds;
        int dimensionUpperBounds;
        DoubleStream valueGenerator;
        ArrayList<Integer> valores = new ArrayList<Integer>();
        
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++){
                dimensionLowerBounds = lower[0];
                dimensionUpperBounds = upper[0];
                valueGenerator = random.doubles(dimensionLowerBounds, dimensionUpperBounds);
                int valor = (int)valueGenerator.iterator().nextDouble();
                //verificacion para ver que no se repita algun numero
                if(valores.contains(valor)){
                    do{
                        valueGenerator = random.doubles(dimensionLowerBounds, dimensionUpperBounds);
                        valor = (int)valueGenerator.iterator().nextDouble();
                    }while(valores.contains(valor));
                    valores.add(valor);
                    valorIndividuo[i][j] = valor;
                }else{
                    valores.add(valor);
                    valorIndividuo[i][j] = valor;
                }
                //valueGenerator = random.doubles(dimensionLowerBounds, dimensionUpperBounds);//?
            }
    }
    
    public int[][] getValorIndividuo(){ return valorIndividuo; }
    
    public String toString(){
        
        String string = "";
        
        for(int i = 0; i < valorIndividuo.length; i++){
            for(int j = 0; j < valorIndividuo.length; j++){
                string += Integer.toString(valorIndividuo[i][j]);
                string += "   ";
            }
            string += "|| ";
        }
            
        return string;
    }
    
    public int[] getLower(){
        return this.lower;
    }
    
    public int[] getUpper(){
        return this.upper;
    }
    
}
