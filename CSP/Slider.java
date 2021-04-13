import java.util.*;
import java.awt.Color;

public class Slider extends ScreenComponent{
    public int x;
    public int y;
    public int length;
    public double[] domain = new double[2];
    public double value;
    public int currentLength = 0;
    public boolean active = false;
    public SliderAction method;

    public Slider(int xArg, int yArg, int lengthArg, double[] domainArg, String pageArg, SliderAction methodArg){
        this.page = pageArg;
        this.componentType = "Slider";

        this.x = xArg;
        this.y = yArg;
        this.length = lengthArg;
        this.domain = domainArg;
        this.value = this.domain[0];

        this.method = methodArg;
    }

    public void checkActive(int x, int y){
        if (x>(this.x+this.currentLength-20) && x<(this.x+this.currentLength+20) && y>(this.y-20) && y<(this.y+30) && this.visible == true){
            this.active = true;
            if (x>(this.x+this.length)){
                this.currentLength = this.length;
            }
            else if (x<this.x){
                this.currentLength = 0;
            }
            else{
                this.currentLength = x-this.x;
            }

            this. value = (((double)this.currentLength/(double)this.length)*(this.domain[1]-this.domain[0])) + this.domain[0];
            this.value = Math.round(this.value*1000.0)/1000.0;

            method.run(this.value);
        }
    }
}

interface SliderAction{
    void run(double x);
}