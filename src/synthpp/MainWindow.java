package synthpp;

import processing.core.PApplet;
import processing.core.PFont;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainWindow extends PApplet
{

    public static void main(String[] args)
    {
        PApplet.main(new String[] { "--location=100,100", "synthpp.MainWindow" });
    }
    private Metronome metro;
    private int high;

    private int mWidth = 800;
    private int mHeight = 370;

    private MidiPlayer midiPlayer;
    private MP3Player mp3Player;
    private String openFilename;
    private String openFilepath;
    private MidiRecorder midiRecorder;
    private String saveFilename;
    private String saveFilepath;

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
    public void setup()
    {
        surface.setResizable(false);
        clickables = new ArrayList<>();
        midiRecorder = new MidiRecorder();
        midiPlayer = new MidiPlayer();

        //create instance of a KeyBoard, initilize it, and register a MidiRecorder
        keyBoard = new KeyBoard(this, 10,sketchHeight()-(mHeight/2) - 40, mWidth -145, mHeight/2 -10);
        keyBoard.init();
        keyBoard.registerRecorder(midiRecorder); //now we will be able to record our notes played

        ////***************Build GUI**************

        drawGUI();

        ///***************************************
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
        waveScreen.draw();
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
                10,70,30,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center,
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


        metronomeDisplay = new TextLabel(this, Metronome.getBPM(), labelFont28,
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
        metronomeMinusButton.addButtonListener(new ButtonAdapter()
        {
            @Override
            public void mousePressed(PApplet pApplet)
            {
               if(Metronome.bpm > 30)
               {
                   Metronome.bpm--;
                   metronomeDisplay.setText(Metronome.getBPM());
               }
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        clickables.add(metronomeMinusButton);

        metronomePlusButton = new Button(this, "+", labelFont12, 10, 15, 744, 48, Color.darkGray, Color.white);
        metronomePlusButton.setBackgroundColor(Color.darkGray);
        metronomePlusButton.addButtonListener(new ButtonAdapter()
        {
            @Override
            public void mousePressed(PApplet pApplet)
            {
                if(Metronome.bpm < 400)
                {
                    Metronome.bpm++;
                    metronomeDisplay.setText(Metronome.getBPM());
                }
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        clickables.add(metronomePlusButton);

        metronomeOnOffSwitch = new ToggleButton(this, 20, 36,768, 12,
                Color.black, Color.white, Color.green, ToggleButton.DIRECTION.vertical);
        metronomeOnOffSwitch.addButtonListener(new ButtonAdapter()
        {
            @Override
            public void mousePressed(PApplet pApplet)
            {
                    if (metro == null)
                    {
                        metro = new Metronome();
                        Thread t = new Thread(metro);
                        t.start();
                    }
                    else
                    {
                        metro.end();
                        metro = null;
                    }
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        clickables.add(metronomeOnOffSwitch);

        octaveDisplay = new TextLabel(this,Integer.toString(keyBoard.getOctave()), labelFont28,
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
            public void mousePressed(PApplet pApplet)
            {
                if(keyBoard.getOctave() <= 10 && keyBoard.getOctave() > 1)
                {
                    int octaveForGUI = keyBoard.getOctave();
                    keyBoard.setOctave(octaveForGUI - 1);
                    octaveDisplay.setText(Integer.toString(octaveForGUI - 1));
                }
            }

            @Override
            public void mouseReleased(PApplet pApplet) { }
        });
        clickables.add(octaveMinusButton);
        octavePlusButton = new Button(this, "+", labelFont12, 10, 15, 744, 112, Color.darkGray, Color.white);
        octavePlusButton.setBackgroundColor(Color.darkGray);
        octavePlusButton.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet)
            {
                if(keyBoard.getOctave() < 10 && keyBoard.getOctave() >= 1)
                {
                    int octaveForGUI = keyBoard.getOctave();
                    keyBoard.setOctave(octaveForGUI + 1);
                    octaveDisplay.setText(Integer.toString(octaveForGUI + 1));
                }
            }

            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        clickables.add(octavePlusButton);


        //be sure to add anything that needs mouse clicks to the clickables arraylist
        loadMidi = new Button(this, "Load", labelFont15, 40, 10, 77, 70, Color.darkGray, Color.white);
        loadMidi.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                //set these to null for the next loading
                openFilename = null;
                openFilepath = null;
                pApplet.selectInput("Select a file to open:", "openMidiFile", null, pApplet);
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}

        });
        //add to clickables arraylist
        clickables.add(loadMidi);
        saveMidi = new Button(this, "Save", labelFont15, 10, 10, 122, 70, Color.darkGray, Color.white);
        saveMidi.addButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                //set these to null for the next saving
                saveFilepath = null;
                saveFilename = null;
                pApplet.selectOutput("Select a file to save to:", "saveMidiFile", null, pApplet);
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
                //set these to null for the next loading
                openFilename = null;
                openFilepath = null;
                pApplet.selectInput("Select a file to open:", "openMP3File", null, pApplet);
            }
            @Override
            public void mouseReleased(PApplet pApplet){}

        });
        //add to clickables arraylist
        clickables.add(loadMP3);

        //create the strip of midi player buttons
        midiPlayerButtons = new AudioButtons(this, 150, 20,
                170,70,Color.white, Color.white, AudioButtons.LAYOUT.horizontal);
        midiPlayerButtons.addPlayButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                midiPlayer.play();
                if(midiPlayer.isPlaying()) {
                    midiPlayerButtons.setButtonColor(AudioButtons.BUTTONTYPE.play, Color.green);
                }
                System.out.println("midi play button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        midiPlayerButtons.addStopButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                midiPlayer.stop();
                midiPlayerButtons.setButtonColor(AudioButtons.BUTTONTYPE.play, Color.white);
            }
            @Override
            public void mouseReleased(PApplet pApplet) { }
        });
        midiPlayerButtons.addPauseButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                midiPlayer.pause();
                System.out.println("midi pause button clicked!");
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        midiPlayerButtons.addRecordButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                //change background color of record button on click
                if(midiPlayerButtons.isRecording){
                    midiPlayerButtons.setButtonColor(AudioButtons.BUTTONTYPE.record, Color.red);
                    keyBoard.recordNotes(true);
                }else{
                    midiPlayerButtons.setButtonColor(AudioButtons.BUTTONTYPE.record, Color.white);
                    keyBoard.recordNotes(false);
                }
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
                170,117,Color.white, Color.white, AudioButtons.LAYOUT.horizontal);
        mp3PlayerButtons.addPlayButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                if(mp3Player != null){
                    mp3Player.play();
                }
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        mp3PlayerButtons.addStopButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                if(mp3Player != null){
                    mp3Player.stop();
                }
            }
            @Override
            public void mouseReleased(PApplet pApplet) {}
        });
        mp3PlayerButtons.addPauseButtonListener(new ButtonAdapter() {
            @Override
            public void mousePressed(PApplet pApplet) {
                if(mp3Player != null) {
                    mp3Player.pause();
                }
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

    public void saveMidiFile(File selection) {
        if (selection != null) {
            saveFilename = selection.getName();
            saveFilepath = selection.getAbsolutePath();
            midiRecorder.saveToFile(saveFilepath);
            println("saved to file " + saveFilepath);

        }else{
            println("file selection is null");
        }
    }
    public void openMidiFile(File selection) {
        if (selection != null) {
            openFilename = selection.getName();
            openFilepath = selection.getAbsolutePath();
            playingMidiLabel.setText(openFilename);
            midiPlayer.load(openFilepath);
        }else{
            println("file selection is null");
        }
    }
    public void openMP3File(File selection) {
        if (selection != null) {
            openFilename = selection.getName();
            openFilepath = selection.getAbsolutePath();
            playingMP3Label.setText(openFilename);
            mp3Player = new MP3Player(openFilepath);
        }else{
            println("file selection is null");
        }
    }
}