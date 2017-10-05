package synthpp;

import processing.core.PApplet;
import processing.core.PFont;

import java.awt.Color;

/**
 * Created by Steven on 9/15/2017.
 */
public class Button implements Clickable{
    protected TextLabel text;
    protected PApplet pApplet;
    protected ButtonAdapter listener;
    protected boolean isPressed;
    protected boolean isDisabled;
    protected Color orginalForegroundColor;//used for mouseover effect
    protected Color orginalBackgroundColor;


    public Button(PApplet pApplet, String t, PFont pFont, int w, int h, int x, int y, Color bg, Color fg){
        this.pApplet = pApplet;
        text = new TextLabel(pApplet,t, pFont, x,y,w,h,TextLabel.HALIGN.center, TextLabel.VALIGN.center,0);
        text.setBackgroundColor(bg);
        text.setForegroundColor(fg);
        this.orginalBackgroundColor = bg;
        this.orginalForegroundColor = fg;
        isPressed = false;
        isDisabled = false;
    }
    public void draw(){
        text.draw();
    }

    //these 2 are used for highlighting with mouseover effect
    public Color getOrginalForegroundColor(){
        return this.orginalForegroundColor;
    }
    public Color getOrginalBackgroundColor(){
        return orginalBackgroundColor;
    }

    public void setBackgroundColor(Color c){
        text.setBackgroundColor(c);
    }
    public Color getBackgroundColor(){return text.getBackgroundColor();}
    public void setForgroundColor(Color c){
        text.setForegroundColor(c);
    }
    public void addButtonListener(ButtonAdapter listener){
        this.listener = listener;
    }

    @Override
    public boolean isPressed(){
        return isPressed;
    }

    public boolean isDisabled(){ return isDisabled;}
    public void setDisabled(boolean d){ isDisabled = d;}

    @Override
    public void mousePressed(PApplet pApplet) {
        if(listener != null){
            listener.mousePressed(pApplet);
            isPressed = true;
        }
    }

    @Override
    public void mouseReleased(PApplet pApplet) {
        if(listener != null && !isDisabled){
            listener.mouseReleased(pApplet);
            isPressed = false;
        }
    }

    @Override
    public int getPosX() {
        return text.getPositionX();
    }

    @Override
    public int getPosY() {
        return text.getPositionY();
    }

    @Override
    public int getWidth() {
        return text.getWidth();
    }

    @Override
    public int getHeight() {
        return text.getHeight();
    }
}
