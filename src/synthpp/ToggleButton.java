package synthpp;

import processing.core.PApplet;
import processing.core.PFont;

import java.awt.*;

/**
 * Created by Steven on 9/22/2017.
 */
public class ToggleButton extends Button {

    enum DIRECTION{vertical, horizontal};
    public DIRECTION direction;
    private boolean isOn;

    private int switchX;
    private int switchY;
    private int switchW;
    private int switchH;
    private Color switchColor;

    public ToggleButton(PApplet pApplet, int w, int h, int x, int y, Color bg, Color fg, DIRECTION d) {
        super(pApplet, "", new PFont(), w, h, x, y, bg, fg);
        this.direction = d;

        //set the current state of the switch to off
        isOn = false;
        switchColor = fg;
        switchX = x+4;
        switchY = y+(h-(h/2))+1;
        if(isVertical()) {
            switchW = w - 8;
            switchH = h/2-4;
        }else{
            switchW = w/2-4;
            switchH = h - 8;
        }

    }

    public boolean isOn(){
        return isOn;
    }

    public boolean isVertical(){
        if(direction == DIRECTION.vertical){
            return true;
        }
        return false;
    }

    @Override
    public void draw(){
        pApplet.noStroke();
        pApplet.fill(text.getBackgroundColor().getRGB());
        pApplet.rect(text.getPositionX(), text.getPositionY(), this.getWidth(), this.getHeight());
        pApplet.fill(switchColor.getRGB());
        pApplet.rect(switchX, switchY, switchW, switchH);
    }

    @Override
    public void mousePressed(PApplet pApplet) {
        //toggle the switch
        isOn = (isOn)?false:true;

        //change the switch positoin
        if(isOn){
            if(isVertical()){
                switchY -= getHeight()/2-1;
            }else{
                switchX -= getWidth()/2-1;
            }
            switchColor = getOrginalForegroundColor();

        }else{
            if(isVertical()){
                switchY += getHeight()/2-1;
            }else{
                switchX += getWidth()/2-1;
            }
            switchColor = getOrginalForegroundColor();
        }

        //must call super method to notify registered listener
        super.mousePressed(pApplet);
    }
}
