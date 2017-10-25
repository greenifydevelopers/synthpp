package synthpp;

import processing.core.PApplet;
import processing.core.PFont;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Steven on 9/22/2017.
 */
public class AudioButtons {
    enum LAYOUT{verical, horizontal}
    enum BUTTONTYPE{play, stop, pause, record}

    protected ArrayList<Button> buttons;
    protected Button playButton;
    protected Button stopButton;
    protected Button pauseButton;
    protected Button recordButton;
    protected Boolean isPaused = false;
    protected Boolean isPlaying = false;
    protected Boolean isRecording =  false;
    protected Boolean isStopped = true;
    protected ButtonAdapter playButtonListener;
    protected ButtonAdapter stopButtonListener;
    protected ButtonAdapter pauseButtonListener;
    protected ButtonAdapter recordButtonListener;

    protected LAYOUT layout;
    protected Color backgroundColor;
    protected Color forgroundColor;
    protected int positionX;
    protected int positionY;
    protected int width;
    protected int height;
    protected PApplet pApplet;


    public AudioButtons(PApplet pApplet, int w, int h, int x, int y, Color backgroundColor,
                        Color forgroundColor, AudioButtons.LAYOUT l){

        buttons = new ArrayList<>(4);
        this.layout = l;
        this.backgroundColor = backgroundColor;
        this.forgroundColor = forgroundColor;
        this.positionX = x;
        this.positionY = y;
        this.width = w;
        this.height = h;
        this.pApplet = pApplet;
        createButtons(pApplet);

    }
    public void draw(){
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).draw();
        }
    }
    public void setButtonColor(BUTTONTYPE type, Color c){
        switch (type){
            case play: {
                playButton.setBackgroundColor(c);
                playButton.draw();
                break;
            }
            case stop: {
                stopButton.setBackgroundColor(c);
                stopButton.draw();
                break;
            }
            case pause: {
                pauseButton.setBackgroundColor(c);
                pauseButton.draw();
                break;
            }
            case record: {
                recordButton.setBackgroundColor(c);
                recordButton.draw();
                break;
            }
        }
    }
    public void disableButton(BUTTONTYPE t){
        switch (t){
            case play:{
                playButton.setDisabled(true);
                break;
            }
            case stop:{
                stopButton.setDisabled(true);
                break;
            }
            case pause:{
                pauseButton.setDisabled(true);
                break;
            }
            case record:{
                recordButton.setDisabled(true);
                break;
            }
        }
    }
    //add listeners to the buttons
    public void addPlayButtonListener(ButtonAdapter buttonAdapter){
        this.playButtonListener = buttonAdapter;
    }
    public void addStopButtonListener(ButtonAdapter buttonAdapter){
        this.stopButtonListener = buttonAdapter;
    }
    public void addPauseButtonListener(ButtonAdapter buttonAdapter){
        this.pauseButtonListener = buttonAdapter;
    }
    public void addRecordButtonListener(ButtonAdapter buttonAdapter){
        this.recordButtonListener = buttonAdapter;
    }
    public Button getPlayButtonReference(){
        return playButton;
    }
    public Button getStopButtonReference(){
        return stopButton;
    }
    public Button getPauseButtonReference(){
        return pauseButton;
    }
    public Button getRecordButtonReference(){
        return recordButton;
    }
    protected void createButtons(PApplet pApplet){
        playButton = new Button(pApplet, "", new PFont(), 15, 16,positionX, positionY,
                backgroundColor, forgroundColor){
            @Override
            public void draw(){
                if(!isDisabled){
                    pApplet.fill(getBackgroundColor().getRGB());
                    pApplet.triangle(positionX, positionY+2,
                            positionX + 15, positionY + 8,
                            positionX, positionY + 16);
                }
            }
            @Override
            public void mousePressed(PApplet pApplet){
                if(playButtonListener != null) {
                    isPlaying = isPlaying?false:true;//update the state first
                    isStopped = false;
                    playButtonListener.mousePressed(pApplet);
                }
            }
        };
        stopButton = new Button(pApplet, "", new PFont(), 15, 16,positionX + 22, positionY+2,
                backgroundColor, forgroundColor){
            @Override
            public void draw(){
                if(!isDisabled) {
                    pApplet.fill(getBackgroundColor().getRGB());
                    pApplet.rect(positionX + 22, positionY + 2, height-6, 14);
                }
            }
            @Override
            public void mousePressed(PApplet pApplet){
                if(stopButtonListener != null) {
                    isStopped = isStopped?false:true;
                    stopButtonListener.mousePressed(pApplet);
                }
            }
        };
        pauseButton = new Button(pApplet, "", new PFont(), 15, 16,positionX + 45, positionY+2,
                backgroundColor, forgroundColor){
            @Override
            public void draw(){
                if(!isDisabled){
                    pApplet.fill(getBackgroundColor().getRGB());
                    pApplet.rect(positionX + 45, positionY + 2, (height-6)/2-1, 14);
                    pApplet.rect(positionX + 55, positionY + 2, (height-6)/2-1, 14);
                }
            }
            @Override
            public void mousePressed(PApplet pApplet){
                if(pauseButtonListener != null) {
                    isPaused = isPaused?false:true;//update the state first
                    isStopped = false;
                    pauseButtonListener.mousePressed(pApplet);
                }
            }
        };
        recordButton = new Button(pApplet, "", new PFont(), 15, 16,positionX + 68, positionY,
                backgroundColor, forgroundColor){
            @Override
            public void draw(){
                if(!isDisabled){
                    pApplet.fill(getBackgroundColor().getRGB());
                    pApplet.ellipse(positionX + 78, positionY + 9, 14, 14);
                }
            }
            @Override
            public void mousePressed(PApplet pApplet){
                if(recordButtonListener != null) {
                    isRecording = (isRecording)?false:true;//set the state first
                    isStopped = false;
                    recordButtonListener.mousePressed(pApplet);
                }
            }
        };
    }
    public boolean isRecording(){return isRecording;}
    public boolean isPlaying(){return isPlaying;}
    public boolean isPaused(){return isPaused;}
    public boolean isStopped(){return isStopped;}

}
