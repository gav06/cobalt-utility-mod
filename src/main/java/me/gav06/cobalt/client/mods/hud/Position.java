package me.gav06.cobalt.client.mods.hud;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Position extends Module {
    public Position() {
        super("Position", "displays your position", Category.HUD);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    int x, y, z, nX, nZ;
    ScaledResolution sr;
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        sr = new ScaledResolution(mc);
        x = (int) mc.player.posX; y = (int) mc.player.posY; z = (int) mc.player.posZ;
        int offset = 0;

        if (mc.currentScreen instanceof GuiChat) {
            offset = Cobalt.font.getHeight() + 6;
        }

        switch (mc.player.dimension) {
            case 0:
                nX = x / 8;
                nZ = z / 8;
                break;
            case -1:
                nX = x * 8;
                nZ = z * 8;
                break;
            case 1:
                nX = x;
                nZ = z;
                break;
        }

        switch (mc.player.dimension) {
            case 0:
            case -1:
                Cobalt.font.drawStringWithShadow("XYZ " + x + ", " + y + ", " + z + " [" + nX + ", " + nZ + "]", 3, sr.getScaledHeight() - Cobalt.font.getHeight() - 4 - offset, -1);
                break;
            case 1:
                Cobalt.font.drawStringWithShadow("XYZ " + x + ", " + y + ", " + z, 3, sr.getScaledHeight() - Cobalt.font.getHeight() - 4 - offset, -1);
        }
    }
}
