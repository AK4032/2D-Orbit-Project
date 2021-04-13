import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;
import java.awt.geom.GeneralPath;

public class Frame extends JPanel{
    public Frame(){}

    public void picture(){
        repaint();
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,1920,1080);

        for (Button i : Program.buttonList){
            this.drawButton(g2d,i);
        }
        for (TextBox i : Program.textBoxList){
            this.drawTextBox(g2d,i);
        }
        for (Graph i : Program.graphList){
            this.drawGraph(g2d,i);
        }
        for (Slider i : Program.sliderList){
            this.drawSlider(g2d,i);
        }
    }

    private void drawDot(Graphics2D g2d, int x, int y, int r){ //draw a dot on the screen
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.RED);
        g2d.drawOval(x-r, y-r, 2*r, 2*r);
        g2d.setColor(oldColor);
    }

    //methods to draw screencomponents on screen
    private void drawButton(Graphics2D g2d, Button i){
        if (i.visible){
            if (i.lit) {g2d.setColor(i.colorLit);}
            else {g2d.setColor(i.color);}
            g2d.setFont(new Font("Century Gothic", Font.PLAIN, i.fontSize));
            g2d.fillRect(i.x,i.y,i.width,i.height);

            if (i.lit) {g2d.setColor(Color.BLACK);}
            else {g2d.setColor(Color.WHITE);}
            int xText = i.x+(i.width/2) - (int)((i.textLength*Program.textRatios[0])/2);
            int yText = i.y+(i.height/2) + (int)((i.fontSize*Program.textRatios[1])/2);
            g2d.drawString(i.text, xText, yText);
        }
    }
    private void drawTextBox(Graphics2D g2d, TextBox i){
        if (i.visible){
            g2d.setColor(i.color);
            g2d.setFont(new Font("Century Gothic", Font.PLAIN, i.fontSize));
            g2d.drawRect(i.x,i.y,i.width,i.height);
            g2d.setColor(Color.WHITE);
            int xText = i.x+(i.width/2) - (int)((i.textLength*Program.textRatios[0])/2);
            int yText = i.y+(i.height/2) + (int)((i.fontSize*Program.textRatios[1])/2);
            g2d.drawString(i.text, xText, yText);
        }
    }
    private void drawGraph(Graphics2D g2d, Graph i){
        if (i.visible){
            //axis
            g2d.setColor(Color.WHITE);
            g2d.drawLine(i.convertCoords(new double[]{i.width[0],0})[0], i.convertCoords(new double[]{i.width[0],0})[1], i.convertCoords(new double[]{i.width[1],0})[0], i.convertCoords(new double[]{i.width[1],0})[1]);
            g2d.drawLine(i.convertCoords(new double[]{0,i.height[0]})[0], i.convertCoords(new double[]{0,i.height[0]})[1], i.convertCoords(new double[]{0,i.height[1]})[0], i.convertCoords(new double[]{0,i.height[1]})[1]);

            //dashed lines on xAxis
            int numDashX = 1+ (int)Math.round((i.width[1]-i.width[0])/i.xStep);
            for (int e = 0; e<numDashX; e++){
                int xCoord = i.convertCoords(new double[]{i.width[0]+(e*i.xStep),0})[0];
                g2d.drawLine(xCoord, i.originCoords[1]-2, xCoord, i.originCoords[1]+2);
            }

            //dashed lines on yAxis
            int numDashY = 1+ (int)Math.round((i.height[1]-i.height[0])/i.yStep);
            for (int e = 0; e<numDashY; e++){
                int yCoord = i.convertCoords(new double[]{0, i.height[0]+(e*i.yStep)})[1];
                g2d.drawLine(i.originCoords[0]-2, yCoord, i.originCoords[0]+2, yCoord);
            }

            //line
            if (i.lineCoords.size()>0){
                g2d.setColor(new Color(100,100,255));
                GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
                path.moveTo(i.lineCoords.get(0)[0], i.lineCoords.get(0)[1]);       
                for(int e = 1; e<i.lineCoords.size(); e++){
                    path.lineTo(i.lineCoords.get(e)[0], i.lineCoords.get(e)[1]);
                }
                g2d.draw(path);
            }

            //box
            g2d.setColor(Color.WHITE);
            int[] boxCoords1 = i.convertCoords(new double[]{i.width[0],i.height[1]});
            int[] boxCoords2 = i.convertCoords(new double[]{i.width[1],i.height[0]});
            g2d.drawRect(boxCoords1[0], boxCoords1[1], boxCoords2[0]-boxCoords1[0], boxCoords2[1]-boxCoords1[1]);
        }
    } 
    private void drawSlider(Graphics2D g2d, Slider i){
        if (i.visible){
            g2d.setColor(Color.WHITE);
            g2d.drawLine(i.x,i.y,i.x+i.length,i.y);
            this.drawDot(g2d, i.x+i.currentLength,i.y,10);

            g2d.setFont(new Font("Century Gothic", Font.PLAIN, 10));
            g2d.drawString(String.valueOf(i.value), i.x-40,i.y);
        }
    }
}