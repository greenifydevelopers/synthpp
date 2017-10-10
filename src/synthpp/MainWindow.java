package synthpp;


/**
 * Created by Steven on 8/30/2017.
 */
import processing.core.PApplet;
import processing.core.PFont;
import javax.sound.midi.*;
//import ddf.minim.*;
//import ddf.minim.signals.*;


/**
 * Created by Steven on 8/27/2017.
 */
public class MainWindow {

    public static void main(String[] args) {
        try
        {
// Locate the default synthesizer
            Synthesizer synth = MidiSystem.getSynthesizer();

// Open the synthesizer
            synth.open();

// Get the available Midi channels - there are usually 16
            MidiChannel channels[] = synth.getChannels();

// Play a note on channel 1
            channels[1].noteOn(25, 500);

// Give the note some time to play
            Thread.sleep(4000);

// Turn the note off
            channels[1].noteOff(25);

// Close the synthesizer device
            synth.close();

// Terminate the program
            System.exit(0);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        //PApplet.main(new String[] { "--location=100,100", "synthpp.MainWindow" });
    }

    private int high;

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


        //create instance of a KeyBoard, initilize and draw it
        keyBoard = new KeyBoard(this,70,150, 405, 100);
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
        keyBoard.closeSynth();
        out.close();
        minim.stop();
        super.stop();
    }

}