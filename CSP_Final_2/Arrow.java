import java.util.*;
import java.awt.Color;

public class Arrow extends ScreenComponent{
    public int[] pos0; //start of arrow
    public int[] pos1; //end of arrow
    public int[] vComp; //x and y components of arrow
    public int length;
    public Color color;
    
    public boolean active = false;
    public ArrowAction method;

    public Arrow(int[] pos0Arg, int[] vCompArg, Color colorArg, String pageArg, ArrowAction methodArg){
        this.pos0 = pos0Arg;
        this.vComp = vCompArg;
        this.color = colorArg;
        this.pos1 = new int[]{this.pos0[0]+this.vComp[0], this.pos0[1]+this.vComp[1]};

        this.page = pageArg;
        this.method = methodArg;
    }

    public void checkActive(int x, int y){
        if (x>(this.pos1[0]-20) && x<(this.pos1[0]+20) && y>(this.pos1[1]-20) && y<(this.pos1[1]+30) && this.visible == true && Program.play == false){
            this.active = true;
            this.pos1[0] = x;
            this.pos1[1] = y;
            this.vComp = new int[]{this.pos1[0]-this.pos0[0], this.pos1[1]-this.pos0[1]};

            this.method.run(this.vComp);
        }
    }
}

interface ArrowAction{
    void run(int[] v);
}