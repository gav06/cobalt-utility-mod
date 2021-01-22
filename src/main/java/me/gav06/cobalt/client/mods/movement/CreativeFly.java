package me.gav06.cobalt.client.mods.movement;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.UpdateEvent;

public class CreativeFly extends Module {
    public CreativeFly() {
        super("CreativeFly", "fly like in creative", Category.MOVEMENT);
    }

    public void onEvent(Event e) {
        if (e instanceof UpdateEvent) {
            mc.player.capabilities.isFlying = true;
        }
    }

    public void onDisable() {
        mc.player.capabilities.isFlying = false;
    }
}
