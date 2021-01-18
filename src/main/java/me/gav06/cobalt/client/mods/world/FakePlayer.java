package me.gav06.cobalt.client.mods.world;

import com.mojang.authlib.GameProfile;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Module {

    public FakePlayer() {
        super("FakePlayer", "Spawns a fake player", Category.WORLD);
    }

    private EntityOtherPlayerMP fake_player;


    public void onEnable() {
        fake_player = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "sn01"));
        fake_player.copyLocationAndAnglesFrom(mc.player);
        fake_player.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(-100, fake_player);
    }

    public void onDisable() {
        try {
            mc.world.removeEntity(fake_player);
        } catch (Exception ignored) {}
    }
}