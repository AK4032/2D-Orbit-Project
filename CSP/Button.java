import java.util.*;
import java.awt.Color;

public class Button extends ScreenComponent{
    public int x;
    public int y;
    public int width;
    public int height;
    public boolean lit = false;
    private ButtonAction method;
    public Color color;
    public Color colorLit;
    public String text;
    public int fontSize;
    public int textLength;

    public Button(int xArg, int yArg, int widthArg, int heightArg, Color colorArg, Color colorLitArg, String textArg, int fontSizeArg, String pageArg, ButtonAction methodArg){
        this.page = pageArg;
        if(this.page == "All" || this.page == Program.pageState){this.visible=true;}
        else{this.visible=false;}
        this.componentType = "Button";

        this.x = xArg;
        this.y = yArg;
        this.width = widthArg;
        this.height = heightArg;
        this.method = methodArg;
        this.color = colorArg;
        this.colorLit = colorLitArg;
        this.text = textArg;
        this.fontSize = fontSizeArg;
        this.textLength = (this.text.length()*this.fontSize);
    }

    public void setText(String newText){
        this.text = newText;
        this.textLength = (this.text.length()*this.fontSize);
    }

    private void action(){
        this.method.run();
    }

    public void checkLit(int x, int y){
        if(x>this.x && x<(this.x+this.width) && y>this.y && y<(this.y+this.height)){this.lit=true;}
        else{this.lit=false;}
    }
    public void checkClick(int x, int y){
        if(x>this.x && x<(this.x+this.width) && y>this.y && y<(this.y+this.height) && this.visible == true){this.action();}
    }
}

interface ButtonAction{
    void run();
}