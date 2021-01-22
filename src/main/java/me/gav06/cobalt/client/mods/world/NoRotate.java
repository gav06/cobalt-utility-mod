package me.gav06.cobalt.client.mods.world;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.PacketRecieveEvent;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", "cancels server rotate packets", Category.WORLD);
    }

    public void onEvent(Event e) {
        if (e instanceof PacketRecieveEvent) {
            if (((PacketRecieveEvent) e).getPacket() instanceof SPacketPlayerPosLook) {
                SPacketPlayerPosLook packet = (SPacketPlayerPosLook) ((PacketRecieveEvent) e).getPacket();
                packet.pitch = mc.player.rotationPitch;
                packet.yaw = mc.player.rotationYaw;
            }
        }
    }
}
