package synthpp;

//import ddf.minim.AudioOutput;
//import ddf.minim.signals.SineWave;
import processing.core.PApplet;
import javax.sound.midi.*;

import java.awt.*;

/**
 * Created by Steven on 9/5/2017.
 */
public class KeyBoard {

    private KeyButton buttons[];
    private int[] referenceNoteNumbers = {0,1,2,3,4,5,6,7,8,9,10,11,12};
    private int[] noteNumbers;
    private float[] referenceTones = {8.1757989156f, 8.6619572180f, 9.1770239974f, 9.7227182413f,
            10.3008611535f, 10.9133822323f, 11.5623257097f, 12.2498573744f,
            12.9782717994f,13.7500000000f,14.5676175474f,15.4338531643f,16.3515978312f};
    private float[] tones;
    private char keys[] = {'A','W','S','E','D','F','T','G','Y','H','U','J','K'};
    private String notes[] = {"C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B","C"};
    private int octave = 7;
    private boolean keyStates[];
    private PApplet parent;
    private boolean isInitialized = false;
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private int keysPressed = 0;
    private float MAXAMP = 1.0f;
    private MidiChannel channels[];
    private Synthesizer synth;

    //create private members for all

    //private AudioOutput out;

    public KeyBoard(PApplet pApplet, int xPositoin, int yPosition, int width, int height){
        this.parent = pApplet;
        //this.out = out;
        this.xPosition = xPositoin;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        octave = 7;
        try {
            this.synth = MidiSystem.getSynthesizer();
            synth.open();
            this.channels = synth.getChannels();

            System.out.println("Made it 1");
        } catch (Exception exc){
            exc.printStackTrace();
            System.out.println("Exception Caught");
        }



    }

    public void setOctave(int octave){
        if(octave >= -1 && octave <= 11) {
            this.octave = octave;
            shiftOctave();
        }
    }

    public void keyPressed(char key){

        keysPressed = 0;
        //out.clearSignals();
        for(int i=0;i<keys.length;i++){
            if(Character.toUpperCase(key) == keys[i]){
                keyStates[i] = true;
            }
            if(keyStates[i]){
                keysPressed++;
            }
        }
        float amp = MAXAMP;
        if(keysPressed == 0) amp = MAXAMP;
        else amp=MAXAMP/keysPressed;

        for(int i=0;i<keys.length;i++){
            if(keyStates[i]){



                //insert key down synth here
                channels[1].noteOn(referenceNoteNumbers[i] + 12*octave,5000);

               // out.addSignal(new SineWave(tones[i], amp, out.sampleRate()));
            }
        }
    }
    public void keyReleased(char key){
        keysPressed = 0;
        //out.clearSignals();
        for(int i=0;i<keys.length;i++)
        {
            if(Character.toUpperCase(key) == keys[i])
            {
                keyStates[i] = false;
                //channels[1].noteOff(referenceNoteNumbers[i] + 12*octave);
            }
            if(keyStates[i])
            {
                keysPressed++;
            }
        }
        float amp = MAXAMP;
        if(keysPressed == 0) amp = MAXAMP;
        else amp=MAXAMP/keysPressed;

        for(int i=0;i<keys.length;i++){
            if(keyStates[i]){
               // out.addSignal(new SineWave(tones[i], amp, out.sampleRate()));
            }
        }
    }

    //return array of notes
    public String[] getNotes(){
        return notes;
    }

    public void draw(){
        if(isInitialized) {
            // Display all the keyButtons
            for (int i = 0; i <  buttons.length; i++) {
                buttons[i].draw(keyStates[i]);
            }
            //draw the black keys second
            for (int i = 0; i <  buttons.length; i++) {
                if (i == 1 || i == 3 || i == 6 || i == 8 || i == 10) {
                    buttons[i].draw(keyStates[i]);
                }
            }
        }
    }

    public void init(){
        buttons = new KeyButton[keys.length];
        keyStates = new boolean[keys.length];
        tones =  new float[referenceTones.length];

        //shift the octave to the default tones
        shiftOctave();

        // Initialize all "keyButtons"
        int whiteKeyWidth = width/keys.length;
        int blackKeyWidth = whiteKeyWidth/2 + 8;
        int whiteKeyHeight = height;
        int blackKeyHeight = whiteKeyHeight/2;
        int numberOfWhiteKeys = 0;
        for (int i = 0; i < buttons.length; i++) {
            switch(i){
                case 1:
                case 3:
                case 6:
                case 8:
                case 10:{ //these will be the black keys
                    buttons[i] = new KeyButton(parent, (xPosition + (whiteKeyWidth * numberOfWhiteKeys))-(blackKeyWidth/2), yPosition, blackKeyWidth, blackKeyHeight, '\0',Color.WHITE, Color.BLACK, Color.pink);
                    break;
                }
                default:{//the rest will be white keys
                    buttons[i] = new KeyButton(this.parent, xPosition + (whiteKeyWidth * numberOfWhiteKeys++), yPosition, whiteKeyWidth, whiteKeyHeight, notes[i].charAt(0),Color.BLACK, Color.WHITE, Color.pink);
                    break;
                }
            }
        }

        isInitialized = true;
    }

    //helper function
    private void shiftOctave(){
        for(int i = 0; i < tones.length; ++i){
            tones[i] =  (float)(referenceTones[i] * Math.pow(2,((octave*11)/12)));
        }
    }

    //private inner class
    private class KeyButton {
        private float xPos;         // horizontal location of keyButton
        private float yPos;         //vertical location of keyButton
        private float width;        // width of key
        private float height;
        private char text;          //text on the key
        private Color foreGround;
        private Color backGround;
        private Color highLight;
        private boolean isPressed;      // state of keyButton (mouse is over or not?)
        private PApplet parent;     // The parent PApplet that we will render ourselves onto


        public KeyButton(PApplet p, int xPosition, int yPositoin, int keyWidth, int keyHeight, char keyText, Color fg, Color bg, Color pressedHighLight) {
            parent = p;
            xPos = xPosition;
            yPos = yPositoin;
            width = keyWidth; // parent.random(10,30);
            height = keyHeight;
            text = keyText;
            foreGround = fg;
            backGround = bg;
            isPressed = false;
            highLight = pressedHighLight;
        }

        // Draw key
        public void draw (boolean pressed) {
            parent.strokeWeight(2);
            parent.stroke(0);
            if (pressed) {
                parent.fill(255, 200, 200);
            } else {
                parent.fill(backGround.getRGB());
            }
            parent.rect(xPos, yPos, width, height);
            parent.fill(foreGround.getRGB());
            parent.text(text, xPos + (width / 2 - 3), yPos + height - 5);

        }

    }

    public void closeSynth(){
        synth.close();
    }
}
