import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;

public class MouseSensor implements MouseListener, MouseMotionListener{
    
    public MouseSensor(){}

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){
        for (Button i : Program.buttonList){ //checks if your mouse curser is hovering over any of the buttons
            i.checkLit(e.getX()-5,e.getY()-28);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        for (Arrow i : Program.arrowList){
            i.active = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseClicked(MouseEvent e){
        for (Button i : Program.buttonList){ //checks if your mouse curser clicked any of the buttons
            i.checkClick(e.getX()-5,e.getY()-28);
        }

        if (e.getX()>230){
            double[] pos = Program.convertCoordsInv(new int[]{e.getX(), e.getY()-30});
            Program.objectList.add(new Object(pos, new double[]{0,2000000}, 10, 1000000));
        }

        Program.updateScreenComponent(); //everytime the user clicks, update the visiblilty on the screenComponents
    }

    @Override
    public void mouseDragged(MouseEvent e){
        for (Arrow i : Program.arrowList){
            i.checkActive(e.getX(),e.getY()-30);
        }
    }
}