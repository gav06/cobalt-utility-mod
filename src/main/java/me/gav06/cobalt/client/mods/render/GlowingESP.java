package me.gav06.cobalt.client.mods.render;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.RenderWorldEvent;
import net.minecraft.entity.Entity;

public class GlowingESP extends Module {
    public GlowingESP() {
        super("ESP", "makes entities glow", Category.RENDER);
    }

    public void onEvent(Event e) {
        if (e instanceof RenderWorldEvent) {
            if (nullCheck())
                return;

            for (Entity entity : mc.world.getLoadedEntityList()) {
                if (entity != mc.player) {
                    entity.setGlowing(true);
                }
            }
        }
    }

    public void onDisable() {
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity != mc.player) {
                entity.setGlowing(false);
            }
        }
    }
}
