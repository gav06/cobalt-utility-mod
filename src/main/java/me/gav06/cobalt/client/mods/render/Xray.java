package me.gav06.cobalt.client.mods.render;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockOre;

import java.util.ArrayList;

public class Xray extends Module {
    public Xray() {
        super("Xray", "lets you only see ores", Category.RENDER);

        xray_blocks.add(Block.getBlockById(56));
        xray_blocks.add(Block.getBlockById(129));
        xray_blocks.add(Block.getBlockById(14));
        xray_blocks.add(Block.getBlockById(15));
        xray_blocks.add(Block.getBlockById(21));
        xray_blocks.add(Block.getBlockById(153));
        xray_blocks.add(Block.getBlockById(73));
        xray_blocks.add(Block.getBlockById(16));

        xray_blocks.add(Block.getBlockById(8));
        xray_blocks.add(Block.getBlockById(9));
        xray_blocks.add(Block.getBlockById(10));
        xray_blocks.add(Block.getBlockById(11));
    }

    public void onEnable() {
        xray_bool = true;
        if (nullCheck())
            return;
        mc.renderGlobal.loadRenderers();
    }

    public void onDisable() {
        xray_bool = false;
        if (nullCheck())
            return;
        mc.renderGlobal.loadRenderers();
    }

    public static boolean xray_bool;

    public static ArrayList<Block> xray_blocks = new ArrayList<>();
}
