package me.gav06.cobalt.mixin;

import me.gav06.cobalt.Cobalt;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// intellij wanted the class to not be public for some retarded reason

@Mixin(EntityRenderer.class)
class MixinEntityRenderer {
    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    public void hurtCameraEffect(float ticks, CallbackInfo callbackInfo) {
        if (Cobalt.moduleManager.getModuleByName("NoHurtCam").getState()) {
            callbackInfo.cancel();
        }
    }
}