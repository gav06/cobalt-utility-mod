package me.gav06.cobalt.client.mods.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", "shows a watermark", Category.HUD);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        Cobalt.font.drawStringWithShadow("Welcome to Cobalt, " + ChatFormatting.GRAY + mc.player.getName(), 3, 3, -1);
    }
}
