import java.util.*;

public class Matrix{
    public double[][] values;
    public int[] size;  //rowLength x columnLength

    public Matrix(double[][] valuesArg, int[] sizeArg){   // formate of Arg: new double[][]{row1, ..., rowN}
        this.values = valuesArg;
        this.size = sizeArg;
    }

    public double det(){
        double ans = 0;
        if (this.size[0] == 2 && this.size[1] == 2){
            ans = values[0][0]*values[1][1] - values[0][1]*values[1][0];
        }
        
        return ans;
    }
    public Matrix inverse(){
        double deter = this.det();
        double a = this.values[0][0]/deter;
        double b = this.values[0][1]/deter;
        double c = this.values[1][0]/deter;
        double d = this.values[1][1]/deter;
        return new Matrix(new double[][]{new double[]{d,-b}, new double[]{-c,a}}, new int[]{2,2});
    }

    public static Matrix multiply(Matrix m1, Matrix m2){
        Matrix ans = new Matrix(new double[][]{}, new int[]{m2.size[0], m1.size[1]});
        double[][] newValues = new double[m1.size[1]][m2.size[0]];

        if (m1.size[0] == m2.size[1]){
            for(int i = 0; i<m1.size[1]; i++){
                for (int e = 0; e<m2.size[0]; e++){
                    double valueIE = 0;
                    for (int a = 0; a<m1.size[0]; a++){
                        //   row values of m1:   m1.values[i][a]
                        //column values of m2:   m2.values[a][e]
                        try{valueIE += m1.values[i][a]*m2.values[a][e];}
                        catch(Exception exc){}
                    }
                    newValues[i][e] = valueIE;
                }
            }

            ans.values = newValues;
        }
        return ans;
    }
    public static void printMatrix(Matrix m){
        for (int i = 0; i<m.size[1]; i++){
            for (int e = 0; e<m.size[0]; e++){
                System.out.print(m.values[i][e] +" ");
            }
            System.out.println("");
        }
    }
}

