package synthpp;

import ddf.minim.*;

import processing.core.PApplet;

import java.awt.Color;

/**
 * Created by Steven on 9/15/2017.
 */
public class WaveScreen {

    //PApplet window we will draw into
    PApplet pApplet;

    //colors
    Color backgroundColor;
    Color foregroundColor;

    //geometry in MainWindow
    int width;
    int height;
    int positionX;
    int positionY;

    public WaveScreen(PApplet pApplet, Color backgroundColor, Color forgroundColor,
                      int width, int height, int x, int y){
        this.pApplet = pApplet;
        this.width = width;
        this.height = height;
        this.positionX = x;
        this.positionY = y;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = forgroundColor;
    }

    public void draw(AudioOutput out){
        int rectLeftX = positionX;
        int rectRightX = positionX  +  width;
        pApplet.fill(backgroundColor.getRGB());
        pApplet.noStroke();
        pApplet.rect(positionX, positionY, rectRightX, height);
        pApplet.stroke(foregroundColor.getRGB());
        for(int i = 0; i < out.bufferSize() - 1; i++){
            float x1 = pApplet.map(i, 0, out.bufferSize(), 0, rectRightX);
            float x2 = pApplet.map(i+1, 0, out.bufferSize(), 0, rectRightX);
            if(out.left.get(i)!=0) { //this if statement will avoid a flat line on the wavescreen
                pApplet.line(x1 + rectLeftX, 60 + out.left.get(i) * 30, x2 + rectLeftX, 60 + out.left.get(i + 1) * 30);
            }
        }
    }
}
