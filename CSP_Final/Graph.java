import java.util.*;
import java.awt.Color;

public class Graph extends ScreenComponent{
    public int[] originCoords = new int[2];
    public double[] width = new double[2];
    public double[] height = new double[2];
    public double xScale;  //pixels per unit
    public double yScale;
    public double xStep;
    public double yStep;
    public double[] domain = new double[2];
    public String type;
    public double valueDef;
    public Function method1;
    public Function method2;
    
    public List<int[]> lineCoords = new ArrayList<int[]>();

        // ARGS:  origin coords(screen coords), width left and right of origin(units), height down and up of origin(units),
        //        scale of xAxis in px/unit, scale of yAxis in px/unit, step of each tic mark on xAxis(units), step of each tic mark on yAxis(units),
        //        domain for input variable for line(units), type of line(input: x,y,t,p,n), step for the input value(units), which page,
        //        function for type x and y, function for t
    public Graph(int[] originCoordsArg, 
        double[] widthArg, double[] heightArg, double xScaleArg, double yScaleArg, double xStepArg, double yStepArg, 
        double domainArg[], String typeArg, double valueDefArg, String pageArg, Function method1Arg, Function method2Arg){

        this.page = pageArg;
        this.type = "Graph";

        this.originCoords = originCoordsArg;
        this.width = widthArg;
        this.height = heightArg;
        this.xScale = xScaleArg;
        this.yScale = yScaleArg;
        this.xStep = xStepArg;
        this.yStep = yStepArg;
        this.domain = domainArg;
        this.type = typeArg;
        this.valueDef = valueDefArg;
        this.method1 = method1Arg;
        this.method2 = method2Arg;

        this.createLine();
    }

    public void createLine(){
        if (this.type != "n"){
            this.lineCoords.clear();
            int numOfPoints = 1+ (int)Math.round((this.domain[1]-this.domain[0])/this.valueDef);
            for (int i = 0; i<numOfPoints; i++){
                double e = this.domain[0]+(i*this.valueDef);

                if(this.type == "x"){
                    this.lineCoords.add(this.convertCoords(new double[]{e, this.method1.run1(e)}));
                }
                else if(this.type == "y"){
                    this.lineCoords.add(this.convertCoords(new double[]{this.method1.run1(e), e}));
                }
                else if(this.type == "t"){
                    this.lineCoords.add(this.convertCoords(this.method2.run2(e)));
                }
                else if(this.type == "p"){
                    double r = this.method1.run1(e);
                    this.lineCoords.add(this.convertCoords(new double[]{r*Math.cos(e), r*Math.sin(e)}));
                }
            }
        }
    }

    public int[] convertCoords(double[] arg){
        int[] ans = new int[2];
        ans[0] = (int)Math.round((arg[0]*this.xScale)+this.originCoords[0]);
        ans[1] = (int)Math.round(this.originCoords[1]-(arg[1]*this.yScale));
        return ans;
    }
    public double[] convertCoordsInv(int[] arg){
        double[] ans = new double[2];
        ans[0] = ((double)(arg[0]-this.originCoords[0]))/this.xScale;
        ans[1] = ((double)(this.originCoords[1]-arg[1]))/this.yScale;
        return ans;
    }
}

interface GraphFunction1{
    double run(double x);
}
interface GraphFunction2{
    double[] run(double t);
}