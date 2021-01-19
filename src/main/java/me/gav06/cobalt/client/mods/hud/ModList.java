package me.gav06.cobalt.client.mods.hud;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.api.util.Util;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModList extends Module {
    public ModList() {
        super("ArrayList", "Shows a list of enabled mods", Category.HUD);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    ScaledResolution sr;
    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
         sr = new ScaledResolution(mc);
        int offsetY = 0;

        for (Module m : Cobalt.moduleManager.getSortedMods()) {
            if (m.getState()) {
                if (!m.getCategory().equals(Category.HUD)) {

                    Cobalt.font.drawStringWithShadow(m.getName(), sr.getScaledWidth() - Cobalt.font.getStringWidth(m.getName()) - 3, 1 + offsetY, Util.getRGBWave(8, 1, .5f, 40 * offsetY));
                    offsetY += Cobalt.font.getHeight() + 1;
                }
            }
        }
    }
}
