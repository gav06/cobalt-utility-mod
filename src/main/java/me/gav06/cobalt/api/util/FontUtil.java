package me.gav06.cobalt.api.util;

// from xenon client
// because i couldn't get comfortaa to load normally

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class FontUtil {
    public static Font UBUNTU_FONT = getFontByName("ubuntu");

    public static Font getFontByName(String name) {
        if (name.equalsIgnoreCase("comfortaa")) {
            return getFontFromInput("/assets/cobalt/Comfortaa.ttf");
        }
        return null;
    }

    public static Font getFontFromInput(String path) {

        try {
            //ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

            Font newFont = Font.createFont(Font.TRUETYPE_FONT, FontUtil.class.getResourceAsStream(path));

            return newFont;
        }
        catch (Exception e) {
            return null;
        }
    }
}
