package me.gav06.cobalt.api.module;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.client.mods.hud.ModList;
import me.gav06.cobalt.client.mods.hud.Watermark;
import me.gav06.cobalt.client.mods.movement.NoSlow;
import me.gav06.cobalt.client.mods.render.*;
import me.gav06.cobalt.client.mods.world.BetterPortals;
import me.gav06.cobalt.client.mods.world.FastPlace;
import me.gav06.cobalt.client.mods.world.LiquidInteract;
import me.gav06.cobalt.client.mods.world.NoBreakDelay;

import java.util.ArrayList;
import java.util.Collections;

public class ModuleManager {

    ArrayList<Module> modules = new ArrayList<Module>();

    private void addMod(Module m) {
        modules.add(m);
    }

    public ModuleManager() {
        //render
        addMod(new Fullbright());
        addMod(new NoHurtCam());
        addMod(new NoBossbar());
        addMod(new NoArmor());
        addMod(new ClearChatBox());
        addMod(new NoRender());
        addMod(new AntiPotion());
        addMod(new AntiOverlay());
        addMod(new Xray()); //- WIP

        //combat

        //movement
        addMod(new NoSlow());

        //world
        addMod(new LiquidInteract());
        addMod(new FastPlace());
        addMod(new NoBreakDelay());
        addMod(new BetterPortals());

        //hud
        addMod(new Watermark());
        addMod(new ModList());
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Module> getModulesByCategory(Category c) {
        ArrayList<Module> modList = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.c.equals(c)) {
                modList.add(m);
            }
        }

        modList.sort(this::compareTo);
        return modList;
    }


    // alphabetical sort
    public int compareTo(Module firstMod, Module secondMod) {
        return Integer.compare(firstMod.name.compareTo(secondMod.name), 0);
    }

    public ArrayList<Module> getSortedMods() {
        ArrayList<Module> m = modules;

        Collections.sort(m, new Comparator());

        return m;
    }

    public static class Comparator implements java.util.Comparator<Module> {

        @Override
        public int compare(Module arg0, Module arg1) {
            if (Cobalt.font.getStringWidth(arg0.getName()) > Cobalt.font.getStringWidth(arg1.getName())) {
                return -1;
            }

            if (Cobalt.font.getStringWidth(arg0.getName()) < Cobalt.font.getStringWidth(arg1.getName())) {
                return 1;
            }
            return 0;
        }
    }
}
