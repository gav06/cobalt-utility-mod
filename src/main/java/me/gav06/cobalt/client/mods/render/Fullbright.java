package me.gav06.cobalt.client.mods.render;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Maxes out your brightness setting", Category.RENDER);
    }

    public void onEnable() {
        mc.gameSettings.gammaSetting = 100;
    }

    public void onDisable() {
        mc.gameSettings.gammaSetting = 1;
    }
}
