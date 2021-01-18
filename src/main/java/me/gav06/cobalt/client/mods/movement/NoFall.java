package me.gav06.cobalt.client.mods.movement;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "tries to prevent fall damage", Category.MOVEMENT);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }


    //super basic nofall
    @SubscribeEvent
    public void onEvent(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;

        if (mc.player.fallDistance > 2) {
            mc.getConnection().sendPacket(new CPacketPlayer(true));
        }
    }
}
