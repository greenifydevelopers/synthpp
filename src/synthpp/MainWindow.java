package synthpp;


/**
 * Created by Steven on 8/30/2017.
 */
import processing.core.PApplet;
import processing.core.PFont;

import ddf.minim.*;
import ddf.minim.signals.*;


/**
 * Created by Steven on 8/27/2017.
 */
public class MainWindow extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[] { "--location=100,100", "synthpp.MainWindow" });
    }

    private Minim minim;
    private AudioOutput out;
    private SineWave sine;
    private SineWave nullSine;

    private float freq = 0;
    private int keysPressed = 0;
    private  int port   = 30;
    private static final float MAXAMP = 1.0f;
    private float amp  = MAXAMP;
    PFont font;

    //this object will handle the piano keyboard model and view
    private KeyBoard keyBoard;

    @Override
    public void setup(){
        minim = new Minim(this);

        out = minim.getLineOut(Minim.STEREO);
        nullSine= new SineWave(0, 0, out.sampleRate());
        sine = new SineWave(0, amp, out.sampleRate());
        sine.portamento(port);

        //create instance of a KeyBoard, initilize and draw it
        keyBoard = new KeyBoard(this, out,70,150, 405, 100);
        keyBoard.init();
        keyBoard.draw();

    }
    @Override
    public void settings() {
        size(440, 310, "processing.opengl.PGraphics2D");
    }

    //determine which keys have been pressed
    @Override
    public void keyPressed(){
        //the keyboard has it's own key mappings and will handle state changes if this key is one it is watching
        keyBoard.keyPressed(key);
    }

    //determine which keys have been released
    @Override
    public void keyReleased(){
        //the keyboard has it's own key mappings and will handle state changes if this key is one it is watching
        keyBoard.keyReleased(key);
    }

    @Override
    public void draw(){
        background(100);
        stroke(255);

        //draw wave
        for(int i = 0; i < out.bufferSize() - 1; i++){
            float x1 = map(i, 0, out.bufferSize(), 0, width);
            float x2 = map(i+1, 0, out.bufferSize(), 0, width);
            line(x1, 50 + out.left.get(i)*50, x2, 50 + out.left.get(i+1)*50);
        }
        stroke(120);
        line(0, 100, 512, 100);
        line(0, 0, 512, 0);
        fill(0);
        text("amplitude: "+amp*100+"%", 10, 115);

        keyBoard.draw();
    }

    @Override
    public void stop(){
        // always close Minim audio classes when you are finished with them
        out.close();
        minim.stop();
        super.stop();
    }
}

//delete this comment later