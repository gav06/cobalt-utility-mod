package me.gav06.cobalt.event.listeners;

import me.gav06.cobalt.api.event.Event;
import net.minecraft.network.Packet;

public class PacketSendEvent extends Event<PacketSendEvent> {

    public Packet packet;

    public PacketSendEvent(Packet packetIn) {
        this.packet = packetIn;
    }

    public Packet getPacket() {
        return packet;
    }
}
