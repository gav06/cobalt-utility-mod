package me.gav06.cobalt.client.mods.combat;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.PacketSendEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", "tries to make every hit a critical", Category.COMBAT);
    }

    public void onEvent(Event e) {
        if (e instanceof PacketSendEvent) {
            if (((PacketSendEvent) e).getPacket() instanceof CPacketUseEntity) {
                if (((CPacketUseEntity) ((PacketSendEvent) e).getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                }
            }
        }
    }
}
