package me.gav06.cobalt.mixin;

import me.gav06.cobalt.Cobalt;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPortal.class)
public class MixinBlockPortal {

    //@Inject(method = "", at = @At("HEAD"), cancellable = true)
    //public void getCollisionBoundingBoxHook(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable callbackInfoReturnable) {
    //    if (Cobalt.moduleManager.getModuleByName("BetterPortals").getState()) {
    //        callbackInfoReturnable.cancel();
    //    }
    //}
}
