package me.gav06.cobalt.api.module;

import net.minecraft.client.Minecraft;

public class Module {

    String name;
    String description;
    public boolean toggled;
    Category c;

    public Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, String description, Category c) {
        this.name = name;
        this.description = description;
        this.c = c;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getState() {
        return toggled;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void toggle() {
        if (toggled) {
            onDisable();
        } else {
            onEnable();
        }
        toggled = !toggled;
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public Category getCategory() {
        return c;
    }
}
