package synthpp;

import processing.core.PApplet;

/**
 * Created by Steven on 9/17/2017.
 */
public interface Clickable {
    public void mousePressed(PApplet pApplet);
    public void mouseReleased(PApplet pApplet);
    public int getPosX();
    public int getPosY();
    public int getWidth();
    public int getHeight();
    public boolean isPressed();

    public static boolean isMouseOver(PApplet pApplet, Clickable clickable){
        if (pApplet.mouseX >= clickable.getPosX() && pApplet.mouseX <= clickable.getPosX()+ clickable.getWidth() &&
                pApplet.mouseY >= clickable.getPosY() && pApplet.mouseY <= clickable.getPosY()+clickable.getHeight()) {
            return true;
        } else {
            return false;
        }
    }
}
