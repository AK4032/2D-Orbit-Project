import java.util.*;

public class View{
    public Matrix rot;  //Matrix with basis vectors for View coordinate system (rotation matrix)

    public double x;    //Coordinates of View in Gloabal coordinate System
    public double y;
    public double z;

    public double FOVX = 1.0;
    public double FOVY = FOVX*(9.0/16.0);

    public View(double xArg, double yArg, double zArg){
        this.rot = new Matrix(new double[][]{new double[]{1,0,0}, new double[]{0,1,0}, new double[]{0,0,1}}, new int[]{3,3});

        this.x = xArg;
        this.y = yArg;
        this.z = zArg;
    }





    public void rotateZ(double da){
        Matrix newX0 = new Matrix(new double[][]{new double[]{Math.cos(da)}, new double[]{Math.sin(da)}, new double[]{0}}, new int[]{1,3});   //View Coords
        Matrix newY0 = new Matrix(new double[][]{new double[]{-Math.sin(da)}, new double[]{Math.cos(da)}, new double[]{0}}, new int[]{1,3});  //View Coords
        //z is unchanged

        Matrix newX1 = Matrix.multiply(this.rot, newX0);
        Matrix newY1 = Matrix.multiply(this.rot, newY0);

        this.rot.values[0][0] = newX1.values[0][0];
        this.rot.values[1][0] = newX1.values[1][0];
        this.rot.values[2][0] = newX1.values[2][0];

        this.rot.values[0][1] = newY1.values[0][0];
        this.rot.values[1][1] = newY1.values[1][0];
        this.rot.values[2][1] = newY1.values[2][0];
    }
    public void rotateY(double da){
        Matrix newX0 = new Matrix(new double[][]{new double[]{Math.cos(da)}, new double[]{0}, new double[]{Math.sin(da)}}, new int[]{1,3});   //View Coords
        Matrix newZ0 = new Matrix(new double[][]{new double[]{-Math.sin(da)}, new double[]{0}, new double[]{Math.cos(da)}}, new int[]{1,3});  //View Coords
        //y is unchanged

        Matrix newX1 = Matrix.multiply(this.rot, newX0);
        Matrix newZ1 = Matrix.multiply(this.rot, newZ0);

        this.rot.values[0][0] = newX1.values[0][0];
        this.rot.values[1][0] = newX1.values[1][0];
        this.rot.values[2][0] = newX1.values[2][0];

        this.rot.values[0][2] = newZ1.values[0][0];
        this.rot.values[1][2] = newZ1.values[1][0];
        this.rot.values[2][2] = newZ1.values[2][0];
    }
    public void rotateX(double da){
        Matrix newY0 = new Matrix(new double[][]{new double[]{0}, new double[]{Math.cos(da)}, new double[]{Math.sin(da)}}, new int[]{1,3});   //View Coords
        Matrix newZ0 = new Matrix(new double[][]{new double[]{0}, new double[]{-Math.sin(da)}, new double[]{Math.cos(da)}}, new int[]{1,3});  //View Coords
        //x is unchanged

        Matrix newY1 = Matrix.multiply(this.rot, newY0);
        Matrix newZ1 = Matrix.multiply(this.rot, newZ0);

        this.rot.values[0][1] = newY1.values[0][0];
        this.rot.values[1][1] = newY1.values[1][0];
        this.rot.values[2][1] = newY1.values[2][0];

        this.rot.values[0][2] = newZ1.values[0][0];
        this.rot.values[1][2] = newZ1.values[1][0];
        this.rot.values[2][2] = newZ1.values[2][0];
    }

    public void forward(double dx){
        this.x += this.rot.values[0][0];
        this.y += this.rot.values[1][0];
        this.z += this.rot.values[2][0];
    }
    public void back(double dx){
        this.x -= this.rot.values[0][0];
        this.y -= this.rot.values[1][0];
        this.z -= this.rot.values[2][0];
    }
    public void right(double dx){
        this.x += this.rot.values[0][1];
        this.y += this.rot.values[1][1];
        this.z += this.rot.values[2][1];
    }
    public void left(double dx){
        this.x -= this.rot.values[0][1];
        this.y -= this.rot.values[1][1];
        this.z -= this.rot.values[2][1];
    }
    public void up(double dx){
        this.x += this.rot.values[0][2];
        this.y += this.rot.values[1][2];
        this.z += this.rot.values[2][2];
    }
    public void down(double dx){
        this.x -= this.rot.values[0][2];
        this.y -= this.rot.values[1][2];
        this.z -= this.rot.values[2][2];
    }

    
    
    
    
    
    
    public int[] convertPointTo2D(double[] point){    //input is 3d point in global coordinate system, and output is 2d point on screen coordinate system
        int ans = new int[2];

        Matrix p0 = new Matrix(new double[][]{new double[]{point[0]-this.x}, new double[]{point[1]-this.y}, new double[]{point[2]-this.z}}, new int[]{1,3});
        Matrix rotInv = this.rot.inverse3x3();

        Matrix p1 = Matrix.multiply(rotInv, p0);

        ans[0] = (int)(960.0 * (p1.values[1][0]/(p1.values[0][0]*this.FOVx)));
        ans[1] = (int)(540.0 * (p1.values[2][0]/(p1.values[0][0]*this.FOVy)));
        ans[0] += 960;
        ans[1] = 540 - ans[1];

        return ans;
    }
}