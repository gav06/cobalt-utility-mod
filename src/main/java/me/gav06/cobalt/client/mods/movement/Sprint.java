package me.gav06.cobalt.client.mods.movement;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "automatically makes you sprint", Category.MOVEMENT);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);

        if (!mc.gameSettings.keyBindSprint.isPressed()) {
            mc.player.setSprinting(false);
        }
    }


    //super basic nofall
    @SubscribeEvent
    public void onEvent(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;

        if (mc.player.moveForward > 0) {
            mc.player.setSprinting(true);
        }
    }
}
