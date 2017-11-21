package synthpp;

import processing.core.*;

import java.awt.Color;
import java.awt.Toolkit;

public class TextLabel
{

    //PApplet window we will draw into
    PApplet pApplet;

    //text on the label
    private String text;
    private PFont pFont;

    //geometry in MainWindow
    private int positionX;
    private int positionY;
    private int positionZ;
    private int width;
    private int height;

    //foreground/background colors
    private Color backgroundColor;
    private Color foregroundColor;

    //border color
    private Color strokeColor;
    private float radius;

    //alignment
    HALIGN hAlign;
    VALIGN vAlign;
    int minLeftMargin;

    public static enum HALIGN{
        left, center, right
    }
    public static enum VALIGN{
        top, center, bottom
    }

    public TextLabel(PApplet pApplet, String text, PFont font, int x, int y, int w, int h, HALIGN hAlign, VALIGN vAlign, int minLeftMargin)
    {
        this.pApplet = pApplet;
        this.setText(text);
        this.pFont = font;
        positionX = x;
        positionY = y;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        this.minLeftMargin = minLeftMargin;

        double textWidth = setTextWidth(text);
        width = (int)(textWidth > w ? textWidth:w);

        double fontHeight = setFontHeight();
        height = (int)(fontHeight > h ? fontHeight:h);
        backgroundColor = Color.black;
        foregroundColor = Color.white;
        strokeColor = new Color(100,100,100, 0);
    }

    //setter for the textWdith
    public double setTextWidth(String text)
    {
        double textWidth;
        if(!text.isEmpty())
        {
            //textWidth = pApplet.textWidth(text);
            textWidth = (double) text.length();
        }
        else
        {
            textWidth = 0.0;
        }
        return textWidth;
    }

    public double setFontHeight()
    {
        if(pFont != null)
        {
            return pFont.getSize() * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0;
        }
        else
        {
            return 0.0;
        }
    }

    void draw(){
        //width = (int)((pApplet.textWidth(text) > width)?pApplet.textWidth(text):width);
        pApplet.stroke(strokeColor.getRGB(),strokeColor.getAlpha());
        pApplet.fill(backgroundColor.getRGB());
        pApplet.rect(positionX, positionY, width, height);
        pApplet.textFont(pFont);
        pApplet.fill(foregroundColor.getRGB());
        double fontSize = pFont.getSize() * Toolkit.getDefaultToolkit().getScreenResolution() / 72.0;
        int drawX = positionX;
        int drawY = positionY;
        if(hAlign ==  HALIGN.center) {
            int textX = (positionX) + (width / 2) - ((int) (pApplet.textWidth(text) / 2));
            int textY = (positionY + (height / 2) + ((int) (fontSize) / 4));
            drawX = textX > positionX ? textX : positionX;
            drawY = textY > positionY ? textY : positionY;
        }else if(hAlign == HALIGN.left){
            int textY = (positionY + (height/2) + ((int)(fontSize)/4));
            drawX = positionX +  minLeftMargin; //we have to add minLeftMargin because left margin will show up here
            drawY = textY > positionY?textY:positionY;

        }else if(hAlign == HALIGN.right){
            int textX = (positionX) + (width / 2) - ((int) (pApplet.textWidth(text) / 2));
            int textY = (positionY + (height / 2) + ((int) (fontSize) / 4));
            drawX = textX > positionX ? textX : positionX;
            drawY = textY > positionY ? textY : positionY;
        }
        pApplet.text(text,drawX,drawY);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PFont getpFont() {
        return pFont;
    }

    public void setpFont(PFont pFont) {
        this.pFont = pFont;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(int positionZ) {
        this.positionZ = positionZ;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
}
