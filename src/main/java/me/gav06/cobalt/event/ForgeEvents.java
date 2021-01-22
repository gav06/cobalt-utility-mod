package me.gav06.cobalt.event;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.event.listeners.RenderOverlayEvent;
import me.gav06.cobalt.event.listeners.RenderWorldEvent;
import me.gav06.cobalt.event.listeners.UpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ForgeEvents {

    public ForgeEvents() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            Cobalt.onEvent(new RenderOverlayEvent());
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().player != null || Minecraft.getMinecraft().world != null) {
            Cobalt.onEvent(new UpdateEvent());
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        Cobalt.onEvent(new RenderWorldEvent());
    }
}
