package me.gav06.cobalt.client.mods.render;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.api.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockOutline extends Module {
    public BlockOutline() {
        super("BlockOutline", "outlines blocks better", Category.RENDER);
    }

    @SubscribeEvent
    public void onEvent(RenderWorldLastEvent event) {
        if (mc.world != null || mc.player != null) {
            if (getState()) {
                RayTraceResult lookingAt = mc.objectMouseOver;
                if (lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = lookingAt.getBlockPos();
                    Util.blockESPBox(pos, 1, 1, 1, .3f);
                }
            }
        }
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
