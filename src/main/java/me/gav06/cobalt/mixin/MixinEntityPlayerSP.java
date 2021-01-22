package me.gav06.cobalt.mixin;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.event.listeners.PlayerMoveEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer {

    public MixinEntityPlayerSP() {
        super(null, null);
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        PlayerMoveEvent moveEvent = new PlayerMoveEvent(type, x, y, z);
        Cobalt.onEvent(moveEvent);

        super.move(type, moveEvent.x, moveEvent.y, moveEvent.z);
    }
}
