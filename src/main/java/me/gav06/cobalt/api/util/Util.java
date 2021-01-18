package me.gav06.cobalt.api.util;

import me.gav06.cobalt.Cobalt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Util {

    private static ScaledResolution sr;
    public static void drawTextWithBackdrop(String text, int x, int y, int color) {
        sr = new ScaledResolution(Minecraft.getMinecraft());
        int w = sr.getScaledWidth();
        int h = sr.getScaledHeight();
        Gui.drawRect(x - 1,y - 1,x +  Cobalt.font.getStringWidth(text) + 1, y + Cobalt.font.getHeight() + 1, 0x80000000 );
        Cobalt.font.drawStringWithShadow(text, x, y, color);
    }

    public static int getRGBWave(float seconds, float brightness, float saturation, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getRGB(float seconds, float brightness, float saturation) {
        float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }
}
