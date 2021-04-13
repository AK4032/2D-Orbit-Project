import java.util.*;
import java.awt.Color;

public class TextBox extends ScreenComponent{
    public int x;
    public int y;
    public int width;
    public int height;
    public Color color;
    public String text;
    public int fontSize;
    public int textLength;

    public TextBox(int xArg, int yArg, int widthArg, int heightArg, Color colorArg, String textArg, int fontSizeArg, String pageArg){
        this.page = pageArg;
        if(this.page == "All" || this.page == Program.pageState){this.visible=true;}
        else{this.visible=false;}
        this.componentType = "TextBox";

        this.x = xArg;
        this.y = yArg;
        this.width = widthArg;
        this.height = heightArg;
        this.color = colorArg;
        this.text = textArg;
        this.fontSize = fontSizeArg;
        this.textLength = (this.text.length()*this.fontSize);
    }

    public void setText(String newText){
        this.text = newText;
        this.textLength = (this.text.length()*this.fontSize);
    }
}
