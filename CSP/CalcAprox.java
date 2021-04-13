import java.util.*;

public class CalcAprox{
    public static double intagrateByRect(Function function, double a, double b, int n){
        double deltaX = (b-a)/((double)n);

        double sum = 0.0;
        for (int i = 1; i<=n; i++){
            double xi = a + (deltaX*i);
            sum += function.run1(xi)*deltaX;
        }
        return sum;
    }

    public static double intagrateByTrap(Function function, double a, double b, int n){
        double deltaX = (b-a)/((double)n);

        double sum = 0.0;
        for (int i = 1; i<=n-1; i++){
            double xi = a + (i*deltaX);
            sum += 2*function.run1(xi);
        }
        sum = sum + function.run1(a) + function.run1(b);
        return 0.5*deltaX*sum;
    }

    public static Function differentiate(Function function, double dx){
        Function ans = new Function((x) -> {return (function.run1(x+dx)-function.run1(x))/dx;});
        return ans;
    }
}

