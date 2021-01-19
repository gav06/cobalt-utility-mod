package me.gav06.cobalt.client.mods.movement;


import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.b0at.api.event.Subscribe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ElytraFly extends Module {
    public ElytraFly() {
        super("ElytraFly", "infinite elytra fly", Category.MOVEMENT);
    }

    //i commented it out because onupdate has not been registered as an event yet in the api*

    //@Subscribe
    //public void onUpdate(){
    //    if (mc.player.isAirBorne = false)
    //        return;
    //    if(mc.player.isElytraFlying()){
    //        mc.player.capabilities.isFlying = true;
    //    }
    //
    //}

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;

        if (mc.player.isAirBorne = false)
               return;
            if(mc.player.isElytraFlying()){
                mc.player.capabilities.isFlying = true;
            }
    }

}
