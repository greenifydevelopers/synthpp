package synthpp;

import processing.core.PApplet;
import processing.core.PFont;

import java.awt.*;

public class ToggleButton extends Button {

    enum DIRECTION{horizontal, vertical};
    public DIRECTION direction;
    private boolean isOn;

    private int switchX;
    private int switchY;
    private int switchW;
    private int switchH;
    private Color switchColor;
    private Color onColor;
    private Color offColor;

    public ToggleButton(PApplet pApplet, int w, int h, int x, int y, Color bg, Color offColor, Color onColor, DIRECTION d) {
        super(pApplet, "", new PFont(), w, h, x, y, bg, offColor);
        this.direction = d;
        this.onColor = onColor;
        this.offColor = offColor;

        //set the current state of the switch to off
        isOn = false;
        switchColor = offColor;
        switchX = x+ (h - 3); //+ (h-(h/2))+1; // +4
        switchY = y + 4; //y+(h-(h/2))+1;
        if(isHorizontal())
        {
            switchW = w - (h+3);
            switchH = h - 8; // h/2-4
        }
        else{
            switchW = w + (h+3); // h/2-4
            switchH = h + 8;
        }

    }

    public boolean isOn(){
        return isOn;
    }

    public boolean isHorizontal(){
        if(direction == DIRECTION.horizontal){
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
        //isOn = (isOn)?false:true;

        if(isOn)
        {
            isOn = false;
        }
        else
        {
            isOn = true;
        }

        //change the switch position
        if(isOn){
            if(isHorizontal())
            {
                switchX -= 10;
            }
            else
            {
                //switchY -= getHeight();
            }
            switchColor = onColor;

        }else{
            if(isHorizontal())
            {
                switchX += 10;
            }
            else
            {
                //switchY += getHeight();
            }
            switchColor = offColor;
        }

        //must call super method to notify registered listener
        super.mousePressed(pApplet);
    }
}
