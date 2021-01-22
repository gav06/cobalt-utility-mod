package me.gav06.cobalt.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.event.listeners.PacketRecieveEvent;
import me.gav06.cobalt.event.listeners.PacketSendEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onPacketSend(Packet<?> packet, CallbackInfo ci) {
        PacketSendEvent sendEvent = new PacketSendEvent(packet);
        Cobalt.onEvent(sendEvent);
        if (sendEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onPacketRead(ChannelHandlerContext chc, Packet<?> packet, CallbackInfo ci) {
        PacketRecieveEvent recieveEvent = new PacketRecieveEvent(packet);
        Cobalt.onEvent(recieveEvent);
        if (recieveEvent.isCancelled()) {
            ci.cancel();
        }
    }
}
