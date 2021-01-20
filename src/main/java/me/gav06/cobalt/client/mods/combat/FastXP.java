package me.gav06.cobalt.client.mods.combat;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FastXP extends Module {
    public FastXP() {
        super("FastXP", "Fastplace but only for xp", Category.COMBAT);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;
    }
}
