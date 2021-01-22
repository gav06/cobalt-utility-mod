package me.gav06.cobalt.client.mods.combat;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.UpdateEvent;
import net.minecraft.init.Items;

public class FastXP extends Module {
    public FastXP() {
        super("FastXP", "Fastplace but only for xp", Category.COMBAT);
    }

    public void onEvent(Event e) {
        if (e instanceof UpdateEvent) {
            if (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
                mc.rightClickDelayTimer = 0;
            }
        }
    }
}
