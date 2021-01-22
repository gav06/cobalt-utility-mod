package me.gav06.cobalt.client.mods.movement;

import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.event.listeners.PacketSendEvent;
import me.gav06.cobalt.event.listeners.PlayerMoveEvent;
import me.gav06.cobalt.event.listeners.UpdateEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;

/*
this is from gamesense idk
good freecam
 */

public class Freecam extends Module {
    public Freecam() {
        super("Freecam", "look around freely", Category.MOVEMENT);
    }

    private double posX, posY, posZ;
    private float pitch, yaw;

    private EntityOtherPlayerMP clonedPlayer;

    private boolean isRidingEntity;
    private Entity ridingEntity;

    public void onEnable() {
        if (mc.player != null) {
            isRidingEntity = mc.player.getRidingEntity() != null;

            if (mc.player.getRidingEntity() == null) {
                posX = mc.player.posX;
                posY = mc.player.posY;
                posZ = mc.player.posZ;
            } else {
                ridingEntity = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
            }

            pitch = mc.player.rotationPitch;
            yaw = mc.player.rotationYaw;

            clonedPlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
            clonedPlayer.copyLocationAndAnglesFrom(mc.player);
            clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
            mc.world.addEntityToWorld(-100, clonedPlayer);
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed((float) (10 / 100f));
            mc.player.noClip = true;
        }
    }

    public void onDisable() {
        EntityPlayer localPlayer = mc.player;
        if (localPlayer != null) {
            mc.player.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
            mc.world.removeEntityFromWorld(-100);
            clonedPlayer = null;
            posX = posY = posZ = 0.D;
            pitch = yaw = 0.f;
            mc.player.capabilities.isFlying = false;
            mc.player.capabilities.setFlySpeed(0.05f);
            mc.player.noClip = false;
            mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0.f;

            if (isRidingEntity) {
                mc.player.startRiding(ridingEntity, true);
            }
        }
    }

    public void onEvent(Event e) {
        if (e instanceof UpdateEvent) {
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed((float) (10 / 100f));
            mc.player.noClip = true;
            mc.player.onGround = false;
            mc.player.fallDistance = 0;
        }

        if (e instanceof PacketSendEvent) {
            if (((PacketSendEvent) e).getPacket() instanceof CPacketPlayer || ((PacketSendEvent) e).getPacket() instanceof CPacketInput) {
                e.setCancelled(true);
            }
        }

        if (e instanceof PlayerMoveEvent) {
            //if (getState())
            mc.player.noClip = true;
        }
    }
}
