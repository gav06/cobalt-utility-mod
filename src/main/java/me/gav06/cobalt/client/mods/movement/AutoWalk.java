package me.gav06.cobalt.client.mods.movement;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.UpdateEvent;

public class AutoWalk extends Module {
    public AutoWalk() {
        super("AutoWalk", "walks automatically", Category.MOVEMENT);
    }

    public void onEvent(Event e) {
        if (e instanceof UpdateEvent) {
            mc.gameSettings.keyBindForward.pressed = true;
        }
    }

    public void onDisable() {
        mc.gameSettings.keyBindForward.pressed = false;
    }
}