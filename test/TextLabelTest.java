//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import processing.core.PApplet;
import processing.core.PFont;
import synthpp.TextLabel;
import synthpp.TextLabel.HALIGN;
import synthpp.TextLabel.VALIGN;

public class TextLabelTest
{
    private static TextLabel textLabel;
    private static PApplet p;
    private static String t;
    private static int x;
    private static int y;
    private static int w;
    private static int h;

    @Before
    public void initialize()
    {
        p = new PApplet();
        t = "test";
        x = 10;
        y = 15;
        w = 20;
        h = 20;
        textLabel = new TextLabel(p, t, (PFont)null, x, y, w, h, HALIGN.center, VALIGN.center, 0);
    }

    @Test
    public void verifyTextLabelString()
    {
        boolean result = false;
        String check = textLabel.getText();
        if (check.equals(textLabel.getText()))
        {
            result = true;
        }

        check = "setText";
        textLabel.setText(check);
        if (!check.equals(textLabel.getText()))
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelX()
    {
        boolean result = false;
        int xValue = textLabel.getPositionX();
        if (xValue == textLabel.getPositionX())
        {
            result = true;
        }

        xValue = 0;
        textLabel.setPositionX(xValue);
        if (xValue != textLabel.getPositionX())
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelY()
    {
        boolean result = false;
        int yValue = textLabel.getPositionY();
        if (yValue == textLabel.getPositionY())
        {
            result = true;
        }

        yValue = 0;
        textLabel.setPositionY(yValue);
        if (yValue != textLabel.getPositionY())
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelZ()
    {
        boolean result = false;
        int zValue = textLabel.getPositionZ();
        if (zValue == textLabel.getPositionZ())
        {
            result = true;
        }

         zValue = 0;
        textLabel.setPositionZ(zValue);
        if (zValue != textLabel.getPositionZ())
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelWidth()
    {
        boolean result = false;
        int width = textLabel.getWidth();
        if (width == textLabel.getWidth())
        {
            result = true;
        }

        width = 0;
        textLabel.setWidth(width);
        if (width != textLabel.getWidth())
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelHeight()
    {
        boolean result = false;
        int height = textLabel.getHeight();
        if (height == textLabel.getHeight())
        {
            result = true;
        }

        height = 0;
        textLabel.setHeight(height);
        if (height != textLabel.getHeight())
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelBackgroundColor()
    {
        boolean result = false;
        Color c = textLabel.getBackgroundColor();
        if (c.equals(textLabel.getBackgroundColor()))
        {
            result = true;
        }

        c = Color.white;
        textLabel.setBackgroundColor(c);
        if (!c.equals(textLabel.getBackgroundColor()))
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }

    @Test
    public void verifyTextLabelForegroundColor()
    {
        boolean result = false;
        Color c = textLabel.getForegroundColor();
        if (c.equals(textLabel.getForegroundColor()))
        {
            result = true;
        }

        c = Color.white;
        textLabel.setForegroundColor(c);
        if (!c.equals(textLabel.getForegroundColor()))
        {
            result = false;
        }

        Assertions.assertEquals(true, result);
    }
}