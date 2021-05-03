import java.util.*;
import java.awt.Color;

public class Object extends ScreenComponent{
    public static double gravityConstant = 6.6743 * Math.pow(10,11);

    public static double velocityAConst = 4.0; 

    public double[] pos; //coordinates in meters from the center of the screen
    public double radius; //radius of object in meters
    public double[] velocity; //in meters/second
    public double mass; // in kg
    public double[] netForce; //net force acting on the object in newtons
    public double totalEngery;

    public Arrow velocityArrow;
    public Graph graph;
    public double currentAngle;
    public double orientation;


    public Object(double[] posArg, double[] velocityInit, double massArg, double radiusArg){
        this.pos = posArg;
        this.velocity = velocityInit;
        this.mass = massArg;
        this.radius = radiusArg;


        // inits the velocity Arrow
        int[] posA = Program.convertCoords(this.pos);
        int[] velocityA = new int[]{(int)(velocityInit[0]*Program.pixelsPerMeter*velocityAConst), (int)(-velocityAConst*velocityInit[1]*Program.pixelsPerMeter)};
        this.velocityArrow = new Arrow(posA, velocityA, Color.RED, "Main", (v) -> {
            this.velocity[0] = ((double)v[0])/(Program.pixelsPerMeter*velocityAConst);
            this.velocity[1] = -((double)v[1])/(Program.pixelsPerMeter*velocityAConst);
            this.updatePath();
            this.setEnergy();
        });
        Program.arrowList.add(this.velocityArrow);


        //inits the graph/path of object
        Function f1 = new Function((x) -> {return 1.0;});
        Function f2 = new Function((x) -> {return new double[]{0,0};}, "para");
        this.graph = new Graph(Program.screenCenter, new double[]{-80000000,80000000}, new double[]{-40000000,40000000}, Program.pixelsPerMeter,Program.pixelsPerMeter, 10000000,10000000, new double[]{0,2.0*Math.PI}, "p", 0.005, "Main", f1,f2);
        Program.graphList.add(this.graph);
        this.updateOrientation();


        this.updatePath();
        Program.updateScreenComponent();

        this.setEnergy();
    }

    

    public void update(){ //This function is called every frame
        if (this != Program.objectList.get(0)){
            if (Program.play){
                this.updateVelocity();
                this.updateArrow();
                this.updatePos();
            }
        }
    }

    public void updatePos(){
        double arcLength = Program.getVectorMag(this.velocity) * Program.simRate;
        double rPos = this.pos[0];
        double thetaPos = Program.arcTan(this.pos[1],this.pos[0]);
        double d0 = arcLength/(Math.sqrt(this.graph.method1.run1(thetaPos,false)*this.graph.method1.run1(thetaPos,false) + Math.pow(this.graph.method1.run1(thetaPos,true), 2)));
        d0 *= this.orientation;

        double new0 = d0 + thetaPos;
        double newR = this.graph.method1.run1(new0,false);

        this.pos = new double[]{newR*Math.cos(new0), newR*Math.sin(new0)};
        this.currentAngle = new0;
    }
    public void updateArrow(){
        int[] posA = Program.convertCoords(this.pos);
        int[] velocityA = new int[]{(int)(this.velocity[0]*Program.pixelsPerMeter*velocityAConst), (int)(-velocityAConst*this.velocity[1]*Program.pixelsPerMeter)};
        this.velocityArrow.vComp = velocityA;
        this.velocityArrow.pos0 = posA;
        this.velocityArrow.pos1 = new int[]{posA[0]+velocityA[0], posA[1]+velocityA[1]};
    }
    public void updateVelocity(){
        double KE = this.totalEngery - this.potentialEnergy();

        double vMag = Math.sqrt((KE*2.0)/this.mass);
        if (Double.isNaN(vMag)){
            vMag = 0.0;
        }

        double vMag1 = Program.getVectorMag(this.velocity);


        double r = this.graph.method1.run1(this.currentAngle,false);
        double drd0 = this.graph.method1.run1(this.currentAngle,true);
        double dxd0 = drd0*Math.cos(this.currentAngle) - r*Math.sin(this.currentAngle);
        double dyd0 = drd0*Math.sin(this.currentAngle) + r*Math.cos(this.currentAngle);

        double[] direction = Program.unifyVector(new double[]{this.orientation*dxd0,this.orientation*dyd0});

        this.velocity = new double[]{direction[0]*vMag, direction[1]*vMag};
    }

    public double potentialEnergy(){
        double PE = 0.0;
        try{
            double r = Program.triHype(this.pos[0]-Program.objectList.get(0).pos[0], this.pos[1]-Program.objectList.get(0).pos[1]);
            PE = -1.0 * gravityConstant * this.mass * Program.objectList.get(0).mass / r;
        }
        catch(Exception e){}
        return PE;
    }
    public double kineticEnergy(){
        return 0.5 * this.mass * Math.pow(Program.getVectorMag(this.velocity),2);
    }
    public void setEnergy(){
        this.totalEngery = this.kineticEnergy() + this.potentialEnergy();
    }

    public void updatePath(){
        try{
            Function newFunc = this.getPath(Program.objectList.get(0));
            this.graph.method1 = newFunc;
            this.graph.createLine();
            this.updateOrientation();
        }
        catch(Exception e){}
        
    }
    public void updateOrientation(){
        double x = this.pos[0];
        double y = this.pos[1];

        double[] rV = new double[]{x,y,0};
        double[] vV = new double[]{this.velocity[0],this.velocity[1],0}; 

        double[] omega = Program.crossProd3D(rV,vV);

        if(omega[2] > 0.0){
            this.orientation = 1.0;
        }
        else if(omega[2] < 0.0){
            this.orientation = -1.0;
        }
        else{
            this.orientation = 1.0;
        }
    };
    
    public Function getPath(Object obj){
        double[] r = new double[]{this.pos[0]-obj.pos[0], this.pos[1]-obj.pos[1]};
        double rMag = Program.getVectorMag(r);
        double mu = gravityConstant * obj.mass;
        double a = (mu*rMag)/((2*mu) - (Program.dotProd(this.velocity,this.velocity)*rMag));
        double[] h = Program.crossProd3D(new double[]{r[0],r[1],0}, new double[]{this.velocity[0],this.velocity[1], 0});

        double[] vxh = Program.crossProd3D(new double[]{this.velocity[0],this.velocity[1],0}, h);
        vxh = new double[]{vxh[0]/mu, vxh[1]/mu};
        double[] rUnit = Program.unifyVector(r);
        double[] e = new double[]{rUnit[0]-vxh[0], rUnit[1]-vxh[1]};

        double[] f2 = new double[]{2*a*e[0], 2*a*e[1]};
        
        double f2Mag = Program.getVectorMag(f2);
        double eMag = Program.getVectorMag(e);
        double c = f2Mag/2.0;
        double b2 = Math.pow(a,2) - Math.pow(c,2);
        double p = b2/a;

        double angle = Program.arcTan(f2[1],f2[0]);

        Function ans = new Function((x) -> {return 1.0;});
        if (eMag>1.0){
            ans = new Function((x) -> {return -(p)/(1.0-eMag*Math.cos(x-angle));});
        }
        else{
            ans = new Function((x) -> {return (p)/(1.0-eMag*Math.cos(x-angle));});
        }
        
        return ans;
    }
    public double getE(Object obj){
        double[] r = new double[]{this.pos[0]-obj.pos[0], this.pos[1]-obj.pos[1]};
        double rMag = Program.getVectorMag(r);
        double mu = gravityConstant * obj.mass;
        double a = (mu*rMag)/((2*mu) - (Program.dotProd(this.velocity,this.velocity)*rMag));
        double[] h = Program.crossProd3D(new double[]{r[0],r[1],0}, new double[]{this.velocity[0],this.velocity[1], 0});

        double[] vxh = Program.crossProd3D(new double[]{this.velocity[0],this.velocity[1],0}, h);
        vxh = new double[]{vxh[0]/mu, vxh[1]/mu};
        double[] rUnit = Program.unifyVector(r);
        double[] e = new double[]{rUnit[0]-vxh[0], rUnit[1]-vxh[1]};

        double[] f2 = new double[]{2*a*e[0], 2*a*e[1]};
        
        double f2Mag = Program.getVectorMag(f2);
        double eMag = Program.getVectorMag(e);
        double c = f2Mag/2.0;
        double b2 = Math.pow(a,2) - Math.pow(c,2);
        double p = b2/a;

        double angle = Program.arcTan(f2[1],f2[0]);

        Function ans = new Function((x) -> {return 1.0;});
        if (eMag>1.0){
            ans = new Function((x) -> {return -(p)/(1.0-eMag*Math.cos(x-angle));});
        }
        else{
            ans = new Function((x) -> {return (p)/(1.0-eMag*Math.cos(x-angle));});
        }
        
        return eMag;
    }
}