import java.util.*;

public class Function{
    private Inter1 method1;
    private Inter2 method2;
    private boolean isList1 = false;
    private List<double[]> list1 = new ArrayList<double[]>();
    private boolean isList2 = false;
    private List<double[]> list2 = new ArrayList<double[]>();

    public Function(Inter1 method){
        this.method1 = method;
    }
    public Function(Inter2 method, String parametric){
        this.method2 = method;
    }

    public void setList1(List<double[]> list1Arg){
        this.isList1 = true;
        this.list1 = list1Arg;
    }
    public void setList2(List<double[]> list2Arg){
        this.isList2 = true;
        this.list2 = list2Arg;
    }

    public double run1(double x, boolean derivitive){
        double ans = 0;
        if (derivitive == false){
            if(this.isList1 == false){
                ans = method1.run(x);
            }
            else{
                int e = 0;
                for(int i = 1; i<this.list1.size(); i++){
                    if(Math.abs(this.list1.get(i)[0] - x) < Math.abs(this.list1.get(e)[0] - x)){
                        e = i;
                    }
                }
                ans = this.list1.get(e)[1];
            }
        }
        else if (derivitive == true){
            double dx = 0.001;
            if(this.isList1 == false){
                ans = (method1.run(x+dx) - method1.run(x))/dx;
            }
            else{
                int e = 0;
                for(int i = 1; i<this.list1.size(); i++){
                    if(Math.abs(this.list1.get(i)[0] - x) < Math.abs(this.list1.get(e)[0] - x)){
                        e = i;
                    }
                }
                ans = (this.list1.get(e+1)[1] - this.list1.get(e)[1])/(this.list1.get(e+1)[0] - this.list1.get(e)[0]);
            }
        }

        return ans;
    }
    public double[] run2(double x){
        double[] ans;
        if(this.isList2 == false){
            ans = method2.run(x);
        }
        else{
            int e = 0;
            for(int i = 1; i<this.list2.size(); i++){
                if(Math.abs(this.list2.get(i)[0] - x) < Math.abs(this.list2.get(e)[0] - x)){
                    e = i;
                }
            }
            ans = new double[]{this.list2.get(e)[1], this.list2.get(e)[2]};
        }
        return ans;
    }
}




interface Inter1{
    double run(double x);
}
interface Inter2{
    double[] run(double t);
}