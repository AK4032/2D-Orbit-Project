import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.*;
import java.lang.*;
import java.awt.Color;


public class Program{
    public static String pageState = "Main"; //the current page the window is showing(well probaly only use one page)

    public static final double[] textRatios = new double[]{0.5,0.8}; //Numbers needed for drawing text centered in a box (specific to font)

    //Lists for all the screen components
    public static List<Button> buttonList = new ArrayList<Button>(); 
    public static List<TextBox> textBoxList = new ArrayList<TextBox>();
    public static List<Graph> graphList = new ArrayList<Graph>();
    public static List<Slider> sliderList = new ArrayList<Slider>();
    public static List<Object> objectList = new ArrayList<Object>();

    public static void main(String[] args){
        //Seting up the window and key and mouse listener
        JFrame window = new JFrame("Program");
        Frame frame = new Frame();
        window.setSize(1920,1080);
        window.add(frame);
        window.addKeyListener(new KeySensor());
        window.addMouseListener(new MouseSensor());
        window.addMouseMotionListener(new MouseSensor());
        window.setVisible(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        TimerTask gameTask = new TimerTask(){
            public void run(){
                frame.picture();
            };
        };

        Timer gameTimer = new Timer();
        gameTimer.schedule(gameTask, 10,10);
    }

    public static void updateScreenComponent(){  //updates visablity status of screenComponents
        for (Button i : buttonList){
            i.updateVisible();
        }
        for (TextBox i : textBoxList){
            i.updateVisible();
        }
        for (Graph i : graphList){
            i.updateVisible();
        }
        for (Slider i : sliderList){
            i.updateVisible();
        }
        for (Object i : objectList){
            i.updateVisible();
        }
    }




//random math functions that are pretty useful

    public static double arcTan(double y,double x){
        double ans = 0.0;
        if (x>0 && y>0){
            ans = Math.atan(y/x);
        }
        else if (x<0 && y>0){
            ans = Math.PI + Math.atan(y/x);
        }
        else if (x<0 && y<0){
            ans = Math.PI + Math.atan(y/x);
        }
        else if (x>0 && y<0){
            ans = (2*Math.PI) + Math.atan(y/x);
        }

        if (x == 0 && y>0){
            ans = Math.PI/2;
        }
        else if (x == 0 && y<0){
            ans = (3*Math.PI)/2;
        }
        else if (y == 0 && x>0){
            ans = 0.0;
        }
        else if (y == 0 && x<0){
            ans = Math.PI;
        }

        return ans;
    }
    public static double triHype(double a, double b){
        return Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
    }
    public static double dotProd(double[] a, double[] b){
        return a[0]*b[0] + a[1]*b[1];
    }
    public static double crossProd(double[] a, double[] b){
        return a[0]*b[1] - a[1]*b[0];
    }
    public static double angleBetween(double[] a, double[] b){
        return Math.acos(((a[0]*b[0])+(a[1]*b[1]))/(Math.sqrt(Math.pow(a[0],2)+Math.pow(a[1],2))*Math.sqrt(Math.pow(b[0],2)+Math.pow(b[1],2))));
    }
    
}