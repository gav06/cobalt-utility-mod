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
        if (Cobalt.moduleManager.getModuleByName("Xray").getState()) {
            for (Block block : Xray.xray_blocks) {
                if (!blockState.getBlock().equals(block)) {
                    callbackInfoReturnable.cancel();
                }
            }
        }
    }





}
