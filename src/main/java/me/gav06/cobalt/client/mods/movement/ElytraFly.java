package me.gav06.cobalt.client.mods.movement;


import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.b0at.api.event.Subscribe;

public class ElytraFly extends Module {
    public ElytraFly() {
        super("ElytraFly", "infinite elytra fly", Category.MOVEMENT);
    }


    @Subscribe
    public void onUpdate(){
        if (mc.player.isAirBorne = false)
            return;
        if(mc.player.isElytraFlying()){
            mc.player.capabilities.isFlying = true;
        }

    }


}
