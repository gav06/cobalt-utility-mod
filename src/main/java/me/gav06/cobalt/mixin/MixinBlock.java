package me.gav06.cobalt.mixin;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.client.mods.render.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "shouldSideBeRendered",at = @At("HEAD"), cancellable = true)
    public void shouldSideBeRenderedHook(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (Xray.xray_bool) {
            if (!Xray.xray_blocks.contains(blockState.getBlock())) {
                callbackInfoReturnable.cancel();
            } else {
                callbackInfoReturnable.setReturnValue(true);
            }
        }
    }
}
