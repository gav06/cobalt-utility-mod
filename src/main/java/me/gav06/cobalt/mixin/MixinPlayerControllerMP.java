package me.gav06.cobalt.mixin;

import me.gav06.cobalt.Cobalt;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Inject(method = "getBlockReachDistance", at = @At("RETURN"), cancellable = true)
    private void getReachDistanceHook(final CallbackInfoReturnable<Float> distance) {
        if (Cobalt.moduleManager.getModuleByName("Reach").getState()) {
            distance.setReturnValue(8f);
        }
    }
}
