package me.gav06.cobalt;

import me.gav06.cobalt.api.font.CFontRenderer;
import me.gav06.cobalt.api.module.ModuleManager;
import me.gav06.cobalt.client.gui.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Mod(modid = Cobalt.MOD_ID)
public class Cobalt {
    public static final String MOD_ID = "cobalt", MOD_NAME = "Cobalt", VERSION = "1.0", NAME_VERSION = MOD_NAME + " " + VERSION;

    public static ModuleManager moduleManager;

    public static CFontRenderer font = new CFontRenderer(new Font("Whitney", 0, 18), true, true);

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        moduleManager = new ModuleManager();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_RSHIFT) {
                Minecraft.getMinecraft().displayGuiScreen(ClickGui.INSTANCE);
            }
        }
    }
}
