import org.junit.Before; //JUnit 4
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.Test; //JUnit 4

import processing.core.PApplet;
import processing.core.PFont;
import synthpp.Button;
import synthpp.ButtonAdapter;
import synthpp.TextLabel;

import java.awt.*;
import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@Nested
@DisplayName("Button Testing")
public class ButtonTest {
    private static Button button;
    private Color orginalForegroundColor = Color.red;
    private Color orginalBackgroundColor = Color.blue;
    private int x = 51;
    private int y = 52;
    private int w = 49;
    private int h = 50;
    private PApplet pApp;
    private PFont pFont;


    //Change to BeforeAll for JUnit 5
    @Before
    public void initialize() {
        button = new Button(pApp, "TestText", pFont, 49, 50, 51, 52, orginalBackgroundColor, orginalForegroundColor);
    }

    @Test
    @DisplayName("get original foreground color")
    public void testgetOrginalForegroundColor() {
        assertEquals(orginalForegroundColor, button.getOrginalForegroundColor());
    }

    @Test
    @DisplayName("get original background color")
    public void testgetOrginalBackgroundColor() {
        assertEquals(orginalBackgroundColor, button.getOrginalBackgroundColor());
    }

    @Test
    @DisplayName("set background color")
    public void testsetBackgroundColor() {
        Color c = new Color(0, 0, 0);
        this.orginalBackgroundColor = c;
        button.setBackgroundColor(c);
        assertEquals(c, button.getBackgroundColor());
    }


    @Test
    @DisplayName("get background color")
    public void testgetBackgroundColor() {
        assertEquals(this.orginalBackgroundColor, button.getBackgroundColor());
    }

    @Test
    @DisplayName("set foreground color")
    public void testsetForgroundColor() {
        Color c = new Color(0, 0, 0);
        button.setForgroundColor(c);
        // Button class does not have getForegroundColor() method, how to test?
        // assertEquals(c, button.getForegroundColor());
    }
    
    //Needs working PApplet, probably not gonna happen in a test
/*
    @Test
    @DisplayName("mouse pressed")
    public void testmousePressed() {
        button.mousePressed(pApp);
        assertEquals(true, button.isPressed());
    }
*/
    @Test
    @DisplayName("mouse released")
    public void testmouseReleased() {
        assertEquals(false, button.isPressed());
    }


    @Test
    @DisplayName("get position x")
    public void testgetPosX() {
        assertEquals(x, button.getPosX());
    }

    @Test
    @DisplayName("get position y")
    public void testgetPosY() {
        assertEquals(y, button.getPosY());
    }

    @Test
    @DisplayName("get width")
    public void testgetWidth() {
        assertEquals(w, button.getWidth());
    }

    @Test
    @DisplayName("get height")
    public void testgetHeight() {
        assertEquals(h, button.getHeight());
    }

}
