package me.gav06.cobalt.mixin;

import me.gav06.cobalt.api.util.FileHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "shutdownMinecraftApplet", at = @At("HEAD"))
    public void shutdownMinecraftAppletHook(CallbackInfo ci) {
        FileHandler.saveModulesToFile();
    }
}