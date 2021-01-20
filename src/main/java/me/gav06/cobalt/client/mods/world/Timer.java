package me.gav06.cobalt.client.mods.world;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Timer extends Module {
    public Timer() {
        super("Timer", "doubles the client tickrate", Category.WORLD);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        mc.timer.tickLength = 50f;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;

        mc.timer.tickLength = 25f;
    }
}
