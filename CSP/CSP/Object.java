import java.util.*;
import java.awt.Color;

public class Object extends ScreenComponent{
    public double[] position; //coordinates in meters from the center of the screen
    public double[] velocity; //in meters/second
    public double mass;
    public double[] netForce; //net force acting on the object in newtons

    public Object(double[] posArg, double velocityInit, double massArg){
        this.position = posArg;
        this.velocity = velocityInit;
        this.mass = massArg;
    }

    public 
}