package me.gav06.cobalt.client.mods.hud;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Comparator;

public class NearestPlayer extends Module {
    public NearestPlayer() {
        super("NearestPlayer", "shows information about the nearest player", Category.HUD);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    Entity nearest_player_entity;
    EntityPlayer nearest_player;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;


    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (nearest_player != null) {
            Gui.drawRect(0, 100, 300, 250, 0x90000000);
            Cobalt.font.drawStringWithShadow(nearest_player.getName(), 5, 105 + Cobalt.font.getHeight(), -1);

        }
    }
}
