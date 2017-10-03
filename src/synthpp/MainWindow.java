package synthpp;


/**
 * Created by Steven on 8/30/2017.
 */
import processing.core.PApplet;
import processing.core.PFont;

import ddf.minim.*;
import ddf.minim.signals.*;

import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Steven on 8/27/2017.
 */
public class MainWindow extends PApplet {

    public static void main(String[] args) {
        PApplet.main(new String[] { "--location=100,100", "synthpp.MainWindow" });
    }
    private int high;
    private Minim minim;
    private AudioOutput out;
    private int mWidth = 800;
    private int mHeight = 370;

    private SineWave sine;
    private SineWave nullSine;

    private float freq = 0;
    private int keysPressed = 0;
    private  int port   = 30;
    private static final float MAXAMP = 1.0f;
    private float amp  = MAXAMP;

    //******User interface controls follow**********
    private Color screenColor = new Color(121,160,41);

    //load up some fonts to use in the GUI-createFont called below in setup()
    private PFont titleFont;
    private PFont labelFont12;
    private PFont labelFont15;
    private PFont labelFont28;

    //this object will handle the piano keyboard control
    private KeyBoard keyBoard;

    TextLabel titleLabel;
    TextLabel playingMidiLabel;
    TextLabel midiSectionLabel;
    TextLabel playingMP3Label;
    TextLabel mp3SectionLabel;
    TextLabel amplitudeLabel;
    TextLabel metronomeLabel;
    TextLabel metronomeDisplay;
    Button    metronomeMinusButton;
    Button    metronomePlusButton;
    TextLabel octaveDisplay;
    TextLabel octaveLabel;
    Button octaveMinusButton;
    Button octavePlusButton;
    WaveScreen waveScreen;
    Button loadMidi;
    Button saveMidi;
    Button loadMP3;
    ToggleButton metronomeOnOffSwitch;

    ArrayList<Clickable> clickables;
    AudioButtons midiPlayerButtons;
    AudioButtons mp3PlayerButtons;

    //********************************************

    @Override
    public void setup(){
        surface.setResizable(false);
        clickables = new ArrayList<>();

        ////***************Build GUI**************

        drawGUI();

        ///***************************************


        //set the minim object
        minim = new Minim(this);
        surface.setResizable(false);
        out = minim.getLineOut(Minim.STEREO);
        nullSine= new SineWave(0, 0, out.sampleRate());
        sine = new SineWave(0, amp, out.sampleRate());
        sine.portamento(port);

        //create instance of a KeyBoard, initilize it
        keyBoard = new KeyBoard(this, out,10,sketchHeight()-(mHeight/2) - 40, mWidth -145, mHeight/2 -10);
        keyBoard.init();

    }
    @Override
    public void settings() {
        size(mWidth, mHeight, "processing.opengl.PGraphics2D");
    }

    @Override
    public void mousePressed(){
        for(int i =  0; i < clickables.size(); i++){
            if(Clickable.isMouseOver(this,clickables.get(i))){
                clickables.get(i).mousePressed(this);
            }
        }
    }

    @Override
    public void mouseReleased(){
        for(int i =  0; i < clickables.size(); i++){
            if(clickables.get(i).isPressed()){
                clickables.get(i).mouseReleased(this);
            }
        }
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
        background(Color.darkGray.getRGB());

        //draw controls
        waveScreen.draw(out);
        keyBoard.draw();
        titleLabel.draw();
        playingMidiLabel.draw();
        midiSectionLabel.draw();
        playingMP3Label.draw();
        mp3SectionLabel.draw();
        amplitudeLabel.draw();
        metronomeLabel.draw();
        metronomeDisplay.draw();
        metronomePlusButton.draw();
        metronomeMinusButton.draw();
        metronomeOnOffSwitch.draw();
        octaveLabel.draw();
        octaveMinusButton.draw();
        octavePlusButton.draw();
        octaveDisplay.draw();
        loadMidi.draw();
        saveMidi.draw();
        loadMP3.draw();
        midiPlayerButtons.draw();
        mp3PlayerButtons.draw();

        //implement highlighting when mouse is over any controls that are Clickable
        for(int i = 0; i < clickables.size(); i++){
            if(Clickable.isMouseOver(this,clickables.get(i))){
                Button b = clickables.get(i) instanceof Button ? ((Button) clickables.get(i)) : null;
                if(b != null){
                    b.setForgroundColor(Color.pink);
                    b.draw();
                }
            }else{
                Button b = clickables.get(i) instanceof Button ? ((Button) clickables.get(i)) : null;
                if(b != null){
                    b.setForgroundColor(b.getOrginalForegroundColor());
                    b.draw();
                }
            }
        }
    }

    @Override
    public void stop(){
        // always close Minim audio classes when you are finished with them
        out.close();
        minim.stop();
        super.stop();
    }

    private void drawGUI(){
        titleFont = createFont("theboldfont.ttf",28);
        labelFont12 = createFont("theboldfont.ttf",12);
        labelFont15 = createFont("theboldfont.ttf",15);
        labelFont28 = createFont("theboldfont.ttf",28);

        waveScreen = new WaveScreen(this, screenColor,
                Color.black, 120,100,265,11 );


        titleLabel = new TextLabel(this,"Synth++", titleFont,
                10,10,150,40, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
                0);
        titleLabel.setBackgroundColor(Color.darkGray);
        titleLabel.setForegroundColor(Color.white);


        playingMidiLabel = new TextLabel(this,"MYSONG.MID", labelFont12,
                10,50,250,8, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
                5);
        playingMidiLabel.setBackgroundColor(screenColor);
        playingMidiLabel.setForegroundColor(Color.darkGray);


        midiSectionLabel = new TextLabel(this,"MIDI", labelFont15,
                10,66,30,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
                0);
        midiSectionLabel.setBackgroundColor(Color.darkGray);
        midiSectionLabel.setForegroundColor(Color.WHITE);

        playingMP3Label = new TextLabel(this,"MMMBOP.MP3", labelFont12,
                10,95,250,8, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
                5);
        playingMP3Label.setBackgroundColor(screenColor);
        playingMP3Label.setForegroundColor(Color.darkGray);

        mp3SectionLabel = new TextLabel(this,"BACKTRACK", labelFont15,
                10,114,30,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
                0);
        mp3SectionLabel.setBackgroundColor(Color.darkGray);
        mp3SectionLabel.setForegroundColor(Color.WHITE);

        amplitudeLabel = new TextLabel(this,"AMPLITUDE", labelFont15,
                265,113,200,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
                0);
        amplitudeLabel.setBackgroundColor(Color.darkGray);
        amplitudeLabel.setForegroundColor(Color.WHITE);


        metronomeDisplay = new TextLabel(this,"120", labelFont28,
                655,11,100,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center,
                0);
        metronomeDisplay.setBackgroundColor(screenColor);
        metronomeDisplay.setForegroundColor(Color.darkGray);

        metronomeLabel = new TextLabel(this,"BPM", labelFont15,
                690,48,10,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center,
                0);
        metronomeLabel.setBackgroundColor(Color.darkGray);
        metronomeLabel.setForegroundColor(Color.white);

        metronomeMinusButton = new Button(this, "-", labelFont12, 10, 15, 656, 48, Color.darkGray, Color.white);
        metronomeMinusButton.setBackgroundColor(Color.darkGray);
        metronomeMinusButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                int bpm = Integer.parseInt(metronomeDisplay.getText());
                if(bpm-1>=1){
                    metronomeDisplay.setText("" + (Integer.parseInt(metronomeDisplay.getText()) - 1));
                }
                System.out.println("metronomeMinusButton pressed");
            }

            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("metronomeMinusButton released");
            }
        });
        clickables.add(metronomeMinusButton);

        metronomePlusButton = new Button(this, "+", labelFont12, 10, 15, 744, 48, Color.darkGray, Color.white);
        metronomePlusButton.setBackgroundColor(Color.darkGray);
        metronomePlusButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                metronomeDisplay.setText("" + (Integer.parseInt(metronomeDisplay.getText())+1));
                System.out.println("metronomePlusButton pressed");
            }

            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("metronomePlusButton released");
            }
        });
        clickables.add(metronomePlusButton);

        metronomeOnOffSwitch = new ToggleButton(this, 20, 36,768, 12,
                Color.black, Color.white, Color.green, ToggleButton.DIRECTION.vertical);
        metronomeOnOffSwitch.addButtonListener(new ButtonAdapter(){
            @Override
            public void mousePressed(PApplet pApplet) {
                //Erik, this is where you will call start or stop depending on the
                //state of the metronmeOnOffSwitch.isOn() return value
                if(metronomeOnOffSwitch.isOn()){
                    System.out.println("metronomeOnOffSwitch is on!");
                }else{
                    System.out.println("metronomeOnOffSwitch is off!");
                }

            }

            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        clickables.add(metronomeOnOffSwitch);

        octaveDisplay = new TextLabel(this,"4-5", labelFont28,
                655,74,100,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center,
                0);
        octaveDisplay.setBackgroundColor(screenColor);
        octaveDisplay.setForegroundColor(Color.darkGray);

        octaveLabel = new TextLabel(this,"Octave", labelFont15,
                678,112,10,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center,
                0);
        octaveLabel.setBackgroundColor(Color.darkGray);
        octaveLabel.setForegroundColor(Color.white);
        octaveMinusButton = new Button(this, "-", labelFont12, 10, 15, 656, 112, Color.darkGray, Color.white);
        octaveMinusButton.setBackgroundColor(Color.darkGray);
        octaveMinusButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("octaveMinusButton pressed");
            }

            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("octaveMinusButton released");
            }
        });
        clickables.add(octaveMinusButton);
        octavePlusButton = new Button(this, "+", labelFont12, 10, 15, 744, 112, Color.darkGray, Color.white);
        octavePlusButton.setBackgroundColor(Color.darkGray);
        octavePlusButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("octavePlusButton pressed");
            }

            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("octavePlusButton released");
            }
        });
        clickables.add(octavePlusButton);


        //be sure to add anything that needs mouse clicks to the clickables arraylist
        loadMidi = new Button(this, "Load", labelFont15, 40, 10, 77, 66, Color.darkGray, Color.white);
        loadMidi.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("loadMidi pressed");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("loadMidi released");
            }

        });
        //add to clickables arraylist
        clickables.add(loadMidi);
        saveMidi = new Button(this, "Save", labelFont15, 10, 10, 122, 66, Color.darkGray, Color.white);
        saveMidi.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("saveMidi pressed");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("saveMidi released");
            }

        });
        //add to clickables arraylist
        clickables.add(saveMidi);
        loadMP3 = new Button(this, "Load", labelFont15, 20, 10, 120, 114, Color.darkGray, Color.white);
        loadMP3.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("loadMP3 pressed");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {
                System.out.println("loadMP3 released");
            }

        });
        //add to clickables arraylist
        clickables.add(loadMP3);

        //create the strip of midi player buttons
        midiPlayerButtons = new AudioButtons(this, 150, 20,
                170,66,Color.yellow, Color.white, AudioButtons.LAYOUT.horizontal);
        midiPlayerButtons.addPlayButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("midi play button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        midiPlayerButtons.addStopButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("midi stop button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        midiPlayerButtons.addPauseButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("midi pause button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        midiPlayerButtons.addRecordButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("midi record button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        clickables.add(midiPlayerButtons.getPlayButtonReference());
        clickables.add(midiPlayerButtons.getStopButtonReference());
        clickables.add(midiPlayerButtons.getPauseButtonReference());
        clickables.add(midiPlayerButtons.getRecordButtonReference());


        //create the strip of mp3 player buttons
        mp3PlayerButtons = new AudioButtons(this, 150, 20,
                170,114,Color.yellow, Color.white, AudioButtons.LAYOUT.horizontal);
        mp3PlayerButtons.addPlayButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("mp3 play button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        mp3PlayerButtons.addStopButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("mp3 stop button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        mp3PlayerButtons.addPauseButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                System.out.println("mp3 pause button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });

        //disable the record button for this strip of buttons
        mp3PlayerButtons.disableButton(AudioButtons.BUTTONTYPE.record);

        clickables.add(mp3PlayerButtons.getPlayButtonReference());
        clickables.add(mp3PlayerButtons.getStopButtonReference());
        clickables.add(mp3PlayerButtons.getPauseButtonReference());
        clickables.add(mp3PlayerButtons.getRecordButtonReference());
    }
}