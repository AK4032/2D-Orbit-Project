import java.util.*;

public class Scribble extends ScreenComponent{
    public boolean active = false;
    public List<int[]> lineCoords = new ArrayList<int[]>();

    public Scribble(String pageArg){
        this.page = pageArg;
        this.componentType = "Scribble";
        this.active = true;
        this.visible = true;
    }

    public void addCoord(int x , int y){
        if (this.visible && this.active){
            this.lineCoords.add(new int[]{x,y});
        }
    }
}