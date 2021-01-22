package me.gav06.cobalt.event.listeners;

import me.gav06.cobalt.api.event.Event;
import net.minecraft.network.Packet;

public class PacketRecieveEvent extends Event<PacketRecieveEvent> {

    public Packet packet;

    public PacketRecieveEvent(Packet packetIn) {
        this.packet = packetIn;
    }

    public Packet getPacket() {
        return packet;
    }
}
