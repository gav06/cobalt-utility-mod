package me.gav06.cobalt.client.mods.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.RenderOverlayEvent;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", "shows a watermark", Category.HUD);
    }

    public void onEvent(Event e) {
        if (e instanceof RenderOverlayEvent) {
            Cobalt.font.drawStringWithShadow("Welcome to Cobalt, " + ChatFormatting.GRAY + mc.player.getName(), 3 , 3, -1);
        }
    }
}
