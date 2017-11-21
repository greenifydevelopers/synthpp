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

    private int mWidth = 1000;
    private int mHeight = 510;

    private MidiPlayer midiPlayer;
    private MP3Player mp3Player;
    private String openFilename;
    private String openFilepath;
    private MidiRecorder midiRecorder;
    private String saveFilepath;
    private String saveFilename;

    //******User interface controls follow**********
    private Color screenColor = new Color(0,0,0);

    //load up some fonts to use in the GUI-createFont called below in setup()
    private PFont titleFont;
    private PFont labelFont20;
    private PFont labelFont25;
    private PFont digitalFont28;

    //this object will handle the piano keyboard control
    private KeyBoard keyBoard;

    TextLabel titleLabel;
    TextLabel playingMidiLabel;
    TextLabel midiSectionLabel;
    TextLabel metronomeSectionLabel;
    TextLabel playingMP3Label;
    TextLabel mp3SectionLabel;
    TextLabel metronomeLabel;
    TextLabel metronomeDisplay;
    Button    metronomeMinusButton;
    Button    metronomePlusButton;
    TextLabel octaveDisplay;
    TextLabel octaveLabel;
    Button octaveMinusButton;
    Button octavePlusButton;
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
        keyBoard = new KeyBoard(this, 250,250, mWidth - 150 , mHeight/2 -10);
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

        keyBoard.draw();
        titleLabel.draw();
        playingMidiLabel.draw();
        midiSectionLabel.draw();
        playingMP3Label.draw();
        mp3SectionLabel.draw();
        metronomeSectionLabel.draw();
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

        drawSpeaker(30, 210 , 250 ,500 );
        drawSpeaker(800, 970 , 250 ,500 );

        drawBars(30, 150,44, 50, 200);
        drawBars(30, 150,32, 39, 150);
        drawBars(30, 150,20, 27, 100);

        //Black screen area to be replaced
        drawBars(160, 970,20, 90, 0);

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
        titleFont = createFont("HelveticaNeue-CondensedBold.otf",35);
        labelFont25 = createFont("HelveticaNeue-CondensedBold.otf",25);
        labelFont20 = createFont("HelveticaNeue-CondensedBold.otf",20);
        digitalFont28 = createFont("DS-DIGIT.ttf",35);

        titleLabel = new TextLabel(this,"Synth++", titleFont, 30, 35,200,40, TextLabel.HALIGN.left, TextLabel.VALIGN.center, 0);
        titleLabel.setBackgroundColor(Color.darkGray);
        titleLabel.setForegroundColor(Color.white);

        playingMidiLabel = new TextLabel(this,"LOAD MIDI", digitalFont28, 230,140,250,25, TextLabel.HALIGN.left, TextLabel.VALIGN.center, 5);
        playingMidiLabel.setBackgroundColor(screenColor);
        playingMidiLabel.setForegroundColor(Color.gray.brighter());
        playingMidiLabel.setHeight(45);

        midiSectionLabel = new TextLabel(this,"MIDI", labelFont25, 230,118,10,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center, 0);
        midiSectionLabel.setBackgroundColor(Color.darkGray);
        midiSectionLabel.setForegroundColor(Color.WHITE);
        midiSectionLabel.setHeight(10);

        playingMP3Label = new TextLabel(this,"LOAD  MP3", digitalFont28, 535,140,250,25, TextLabel.HALIGN.left, TextLabel.VALIGN.center, 5);
        playingMP3Label.setBackgroundColor(screenColor);
        playingMP3Label.setForegroundColor(Color.gray.brighter());
        playingMP3Label.setHeight(45);

        mp3SectionLabel = new TextLabel(this,"BACKTRACK", labelFont25, 535,118,30,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center, 0);
        mp3SectionLabel.setBackgroundColor(Color.darkGray);
        mp3SectionLabel.setForegroundColor(Color.WHITE);
        mp3SectionLabel.setHeight(10);

        metronomeSectionLabel = new TextLabel(this,"METRONOME", labelFont25, 845,118,10,10, TextLabel.HALIGN.left, TextLabel.VALIGN.center, 0);
        metronomeSectionLabel.setBackgroundColor(Color.darkGray);
        metronomeSectionLabel.setForegroundColor(Color.WHITE);
        metronomeSectionLabel.setHeight(10);

        metronomeDisplay = new TextLabel(this, Metronome.getBPM(), digitalFont28, 845,140,80,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center, 0);
        metronomeDisplay.setBackgroundColor(screenColor);
        metronomeDisplay.setForegroundColor(Color.gray.brighter());
        metronomeDisplay.setHeight(45);
        metronomeDisplay.setWidth(95);

        metronomeLabel = new TextLabel(this,"BPM", labelFont20, 873,183,10,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center, 0);
        metronomeLabel.setBackgroundColor(Color.darkGray);
        metronomeLabel.setForegroundColor(Color.white);

        metronomeMinusButton = new Button(this, "-", labelFont25, 10, 10, 852, 140, Color.black, Color.white);
        metronomeMinusButton.setBackgroundColor(screenColor);
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

        metronomePlusButton = new Button(this, "+", labelFont25, 10, 15, 920, 140, Color.black, Color.white);
        metronomePlusButton.setBackgroundColor(screenColor);
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

        metronomeOnOffSwitch = new ToggleButton(this, 20, 45,950, 140, Color.black, Color.white, Color.green, ToggleButton.DIRECTION.vertical);
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

        octaveDisplay = new TextLabel(this,Integer.toString(keyBoard.getOctave()), digitalFont28, 55,140,65,5, TextLabel.HALIGN.center, TextLabel.VALIGN.center, 0);
        octaveDisplay.setBackgroundColor(screenColor);
        octaveDisplay.setForegroundColor(Color.gray.brighter());
        octaveDisplay.setHeight(45);
        octaveDisplay.setWidth(75);

        octaveLabel = new TextLabel(this,"Octave", labelFont25, 59,100,10,10, TextLabel.HALIGN.center, TextLabel.VALIGN.center, 0);
        octaveLabel.setBackgroundColor(Color.darkGray);
        octaveLabel.setForegroundColor(Color.white);

        octaveMinusButton = new Button(this, "-", labelFont25, 10, 0, 65, 140, Color.black, Color.white);
        octaveMinusButton.setBackgroundColor(Color.black);
        octaveMinusButton.addButtonListener(new ButtonAdapter()
        {
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
        octavePlusButton = new Button(this, "+", labelFont20, 10, 0, 110, 145, Color.black, Color.white);
        octavePlusButton.setBackgroundColor(Color.black);
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
        loadMidi = new Button(this, "Load", labelFont20, 40, 5, 230, 185, Color.darkGray, Color.white);
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

        saveMidi = new Button(this, "Save", labelFont20, 10, 5, 280, 185, Color.darkGray, Color.white);
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
        loadMP3 = new Button(this, "Load", labelFont20, 20, 10, 535, 185, Color.darkGray, Color.white);
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
        midiPlayerButtons = new AudioButtons(this, 150, 20, 330,195,Color.white, Color.white, AudioButtons.LAYOUT.horizontal);
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
        mp3PlayerButtons = new AudioButtons(this, 150, 20, 590,195,Color.white, Color.white, AudioButtons.LAYOUT.horizontal);
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
    public void drawSpeaker(int startX, int endX, int startY, int endY)
    {
        int lineCount = 0;

        for(int y = startY; y < endY; y +=10)
        {
            if (lineCount % 2 == 0)
            {
                for (int i = startX + 5; i < endX; i += 10)
                {
                    set(i, y, color(0));
                    set(i + 1,y, color(0));
                    set(i + 2,y, color(0));
                    set(i + 3,y, color(0));

                    set(i, y + 1, color(0));
                    set(i + 1, y + 1, color(0));
                    set(i + 2, y + 1, color(0));
                    set(i + 3, y + 1, color(0));

                    set(i, y + 2, color(0));
                    set(i + 1, y + 2, color(0));
                    set(i + 2, y + 2, color(0));
                    set(i + 3, y + 2, color(0));

                    set(i, y + 3, color(0));
                    set(i + 1, y + 3, color(0));
                    set(i + 2, y + 3, color(0));
                    set(i + 3, y + 3, color(0));

                }
                lineCount++;
            }
            else
            {
                for (int i = startX; i < endX; i += 10)
                {
                    set(i, y, color(0));
                    set(i + 1,y, color(0));
                    set(i + 2,y, color(0));
                    set(i + 3,y, color(0));

                    set(i, y + 1, color(0));
                    set(i + 1, y + 1, color(0));
                    set(i + 2, y + 1, color(0));
                    set(i + 3, y + 1, color(0));

                    set(i, y + 2, color(0));
                    set(i + 1, y + 2, color(0));
                    set(i + 2, y + 2, color(0));
                    set(i + 3, y + 2, color(0));

                    set(i, y + 3, color(0));
                    set(i + 1, y + 3, color(0));
                    set(i + 2, y + 3, color(0));
                    set(i + 3, y + 3, color(0));
                }
                lineCount++;
            }
        }

    }

    public void drawBars(int startX, int endX, int startY, int endY, int colorForBar)
    {

        for(int y = startY; y < endY; y++)
        {
            for (int i = startX; i < endX; i++)
            {
                set(i, y, color(colorForBar,0,0));
                set(i + 1, y, color(colorForBar,0,0));
                set(i + 2, y, color(colorForBar,0,0));
                set(i + 3, y, color(colorForBar,0,0));
            }
        }
    }
}