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
    private Color orginalForegroundColor;
    private Color orginalBackgroundColor;
    private int x;
    private int y;
    private int w;
    private int h;

    //Change to BeforeAll for JUnit 5
    @Before
    public void initialize(PApplet pApplet, String t, PFont pFont, int w, int h, int x, int y, Color bg, Color fg) {
        this.orginalForegroundColor = fg;
        this.orginalBackgroundColor = bg;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        button = new Button(pApplet, t, pFont, w, h, x, y, bg, fg);
    }

    @Test
    @DisplayName("get original foreground color")
    public void getOrginalForegroundColor() {
        assertEquals(orginalForegroundColor, button.getOrginalForegroundColor());
    }

    @Test
    @DisplayName("get original background color")
    public void getOrginalBackgroundColor() {
        assertEquals(orginalBackgroundColor, button.getOrginalBackgroundColor());
    }

    @Test
    @DisplayName("set background color")
    public void setBackgroundColor() {
        Color c = new Color(0, 0, 0);
        this.orginalBackgroundColor = c;
        button.setBackgroundColor(c);
        assertEquals(c, button.getBackgroundColor());
    }

    @Test
    @DisplayName("get background color")
    public void getBackgroundColor() {
        assertEquals(this.orginalBackgroundColor, button.getBackgroundColor());
    }

    @Test
    @DisplayName("set foreground color")
    public void setForgroundColor() {
        Color c = new Color(0, 0, 0);
        button.setForgroundColor(c);
        // Button class does not have getForegroundColor() method, how to test?
        // assertEquals(c, button.getForegroundColor());
    }

    @Test
    @DisplayName("mouse pressed")
    public void mousePressed(PApplet pApplet) {
        assertEquals(true, button.isPressed());
    }

    @Test
    @DisplayName("mouse released")
    public void mouseReleased(PApplet pApplet) {
        assertEquals(false, button.isPressed());
    }

    @Test
    @DisplayName("get position x")
    public void getPosX() {
        assertEquals(x, button.getPosX());
    }

    @Test
    @DisplayName("get position y")
    public void getPosY() {
        assertEquals(y, button.getPosY());
    }

    @Test
    @DisplayName("get width")
    public void getWidth() {
        assertEquals(w, button.getWidth());
    }

    @Test
    @DisplayName("get height")
    public void getHeight() {
        assertEquals(h, button.getHeight());
    }
}
