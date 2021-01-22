package me.gav06.cobalt.api.module;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.client.mods.combat.*;
import me.gav06.cobalt.client.mods.hud.*;
import me.gav06.cobalt.client.mods.movement.*;
import me.gav06.cobalt.client.mods.render.*;
import me.gav06.cobalt.client.mods.world.*;

import java.util.ArrayList;
import java.util.Collections;

public class ModuleManager {

    ArrayList<Module> modules = new ArrayList<Module>();

    private void addMod(Module m) {
        modules.add(m);
    }

    public ArrayList<Module> getModuleList() {
        return modules;
    }

    public ModuleManager() {
        //render
        addMod(new GlowingESP());
        addMod(new Fullbright());
        addMod(new NoHurtCam());
        addMod(new NoBossbar());
        addMod(new NoArmor());
        addMod(new ClearChatBox());
        addMod(new NoRender());
        //addMod(new AntiPotion());
        addMod(new AntiOverlay());
        addMod(new BlockOutline());
        addMod(new Xray()); //- WIP

        //combat
        addMod(new Criticals());
        addMod(new KillAura());
        addMod(new AutoTotem());
        addMod(new FastXP());

        //movement
        addMod(new Freecam());
        addMod(new CreativeFly());
        addMod(new AutoWalk());
        addMod(new NoSlow());
        addMod(new NoFall());
        addMod(new Sprint());
        addMod(new ElytraFly());

        //world
        addMod(new LiquidInteract());
        addMod(new FastPlace());
        addMod(new NoBreakDelay());
        addMod(new BetterPortals());
        addMod(new Reach());
        addMod(new FakePlayer());
        addMod(new NoRotate());

        //hud
        addMod(new Watermark());
        addMod(new ModList());
        addMod(new Position());
        addMod(new TabGUI());

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

    public ArrayList<String> getModulesByName() {
        ArrayList<String> arr = new ArrayList<>();
        for (Module m : modules) {
            arr.add(m.getName());
        }

        return arr;
    }
}
