package me.gav06.cobalt;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.font.CFontRenderer;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.api.module.ModuleManager;
import me.gav06.cobalt.api.util.FileHandler;
import me.gav06.cobalt.api.util.FontUtil;
import me.gav06.cobalt.client.gui.ClickGui;
import me.gav06.cobalt.event.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Mod(modid = Cobalt.MOD_ID)
public class Cobalt {
    public static final String MOD_ID = "cobalt", MOD_NAME = "Cobalt", VERSION = "1.1", NAME_VERSION = MOD_NAME + " " + VERSION;

    private static ForgeEvents forgeEvents;
    public static ModuleManager moduleManager;
    public static CFontRenderer font;
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) throws IOException, FontFormatException {
        moduleManager = new ModuleManager();
        forgeEvents = new ForgeEvents();
        font = new CFontRenderer(new Font("Default", Font.PLAIN, 18), true, true);


        FileHandler.setup();
        FileHandler.loadModulesFromFile();
        MinecraftForge.EVENT_BUS.register(this);

        for (String s : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            System.out.println(s);
        }
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_RSHIFT) {
                Minecraft.getMinecraft().displayGuiScreen(ClickGui.INSTANCE);
            }
        }
    }

    // using nef's event system because its easy idk
    // even tho i have b0at api lmao

    public static void onEvent(Event e) {
        for (int i = 0; i < moduleManager.getModuleList().size(); i++) {
            Module m = moduleManager.getModuleList().get(i);
            if (m.getState()) {
                m.onEvent(e);
            }
        }
    }
}
