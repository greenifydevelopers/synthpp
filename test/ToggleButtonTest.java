import org.junit.Before;
import org.junit.Test;
import processing.core.PApplet;
import synthpp.ToggleButton;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToggleButtonTest
{
    private static ToggleButton toggleButton, toggleButtonNotVertical;
    private static PApplet p;

    @Before
    public void initialize()
    {
        p = new PApplet();
        toggleButton = new ToggleButton(p, 20, 36,768, 12, Color.black, Color.white, Color.green, ToggleButton.DIRECTION.vertical);
        toggleButtonNotVertical = new ToggleButton(p, 20, 36,768, 12, Color.black, Color.white, Color.green, ToggleButton.DIRECTION.horizontal);
    }

    @Test
    public void toggleButtonOff()
    {
        boolean result = false;

        if(!toggleButton.isOn())
        {
            result = true;
        }

        assertEquals(true, result);
    }

    @Test
    public void toggleButtonOnAndBackOff()
    {
        boolean result = false;

        toggleButton.mousePressed(p);

        if(toggleButton.isOn())
        {
            result = true;
        }

        toggleButton.mousePressed(p);

        if(toggleButton.isOn())
        {
            result = false;
        }

        assertEquals(true, result);
    }

    @Test
    public void toggleButtonDirection()
    {
        boolean result = false;

        if(toggleButton.isVertical())
        {
            result = true;
        }

        assertEquals(true, result);
    }
    @Test
    public void toggleButtonDirectionWrongDirection()
    {
        boolean result = false;

        if(!toggleButtonNotVertical.isVertical())
        {
            result = true;
        }

        assertEquals(true, result);
    }
}
