package me.gav06.cobalt.mixin;

import me.gav06.cobalt.Cobalt;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinPlayerOverlay {

    @Inject(method = "renderPumpkinOverlay", at = @At("HEAD"), cancellable = true)
    protected void renderPumpkinOverlayHook(ScaledResolution scaledRes, CallbackInfo callbackInfo) {
        if (Cobalt.moduleManager.getModuleByName("AntiOverlay").getState()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "renderPotionEffects", at = @At("HEAD"), cancellable = true)
    protected void renderPotionEffectsHook(ScaledResolution scaledRes, CallbackInfo callbackInfo) {
        if (Cobalt.moduleManager.getModuleByName("AntiPotion").getState()){
            callbackInfo.cancel();
        }
    }

    @Inject(method = "renderVignette", at = @At("HEAD"), cancellable = true)
    protected void renderVignetteHook(float lightLevel, ScaledResolution scaledRes, CallbackInfo callbackInfo) {
        if (Cobalt.moduleManager.getModuleByName("AntiOverlay").getState()) {
            callbackInfo.cancel();
        }
    }
}