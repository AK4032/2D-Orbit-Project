import java.util.*;

public class ScreenComponent{
    public String componentType;
    public String page;
    public boolean visible;

    public void updateVisible(){
        if (this.page == Program.pageState || this.page == "All"){this.visible=true;}
        else{this.visible=false;}
    }
}