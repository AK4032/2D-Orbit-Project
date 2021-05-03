import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.*;
import java.lang.*;
import java.awt.Color;


public class Program{
    public static String pageState = "Main"; //the current page the window is showing(well probaly only use one page)

    public static final double[] textRatios = new double[]{0.5,0.8}; //Numbers needed for drawing text centered in a box (specific to font)
    public static final int[] screenCenter = new int[]{960,500};   //780,440

    public static double pixelsPerMeter = 0.00001;
    public static double frameRate = 0.01;
    public static double simRate = 0.01;

    public static boolean play = false;

    //Lists for all the screen components
    public static List<Button> buttonList = new ArrayList<Button>(); 
    public static List<TextBox> textBoxList = new ArrayList<TextBox>();
    public static List<Graph> graphList = new ArrayList<Graph>();
    public static List<Arrow> arrowList = new ArrayList<Arrow>();
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


        objectList.add(new Object(new double[]{0,0}, new double[]{0,0}, 6*Math.pow(10,8), 2000000));

        buttonList.add(new Button(20,20,200,30, Color.BLUE,Color.WHITE, "Start/Pause", 15, "Main", () -> {
            if (play == true){
                play = false;
            }
            else if (play == false){
                play = true;
            }
        }));
        

        updateScreenComponent();

        TimerTask gameTask = new TimerTask(){
            public void run(){
                frame.picture();
            };
        };
        Timer gameTimer = new Timer();
        gameTimer.schedule(gameTask, 10,(int)(frameRate*1000.0));


        TimerTask simTask = new TimerTask(){
            public void run(){
                for (Object i : objectList){
                    i.update();
                }
            };
        };
        Timer simTimer = new Timer();
        simTimer.schedule(simTask, 10,(int)(simRate*1000.0));
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
        for (Arrow i : arrowList){
            i.updateVisible();
        }
        for (Object i : objectList){
            i.updateVisible();
        }
    }


    public static int[] convertCoords(double[] pos){  //takes coords from object space and converts them to screen space
        int[] ans = new int[2];
        ans[0] = ((int)(pos[0]*pixelsPerMeter)) + screenCenter[0];
        ans[1] = screenCenter[1] - ((int)(pos[1]*pixelsPerMeter));
        return ans;
    }
    public static double[] convertCoordsInv(int[] pos){
        double[] ans = new double[2];
        ans[0] = (pos[0]-screenCenter[0])/pixelsPerMeter;
        ans[1] = (screenCenter[1]-pos[1])/pixelsPerMeter;
        return ans;
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
    public static double crossProd2D(double[] a, double[] b){
        return a[0]*b[1] - a[1]*b[0];
    }
    public static double[] crossProd3D(double[] a, double[] b){
        double[] ans = new double[3];
        ans[0] = a[1]*b[2] - a[2]*b[1];
        ans[1] = a[2]*b[0] - a[0]*b[2];
        ans[2] = a[0]*b[1] - a[1]*b[0];
        return ans;
    }
    public static double angleBetween(double[] a, double[] b){
        return Math.acos(((a[0]*b[0])+(a[1]*b[1]))/(Math.sqrt(Math.pow(a[0],2)+Math.pow(a[1],2))*Math.sqrt(Math.pow(b[0],2)+Math.pow(b[1],2))));
    }
    public static double getVectorMag(double[] v){
        return triHype(v[0],v[1]);
    }
    public static double[] unifyVector(double[] v){
        double mag = triHype(v[0], v[1]);
        return new double[] {v[0]/mag, v[1]/mag};
    }
}
