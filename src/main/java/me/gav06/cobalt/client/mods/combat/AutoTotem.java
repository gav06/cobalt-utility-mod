package me.gav06.cobalt.client.mods.combat;

import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", "puts a totem in your offhand", Category.COMBAT);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck())
            return;

        if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == Items.TOTEM_OF_UNDYING)
            return;

        final int slot = this.getItemSlot();

        if (slot != -1) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
            mc.playerController.updateController();
        }
    }

    private int getItemSlot() {
        for (int i = 0; i < 36; i++) {
            final Item item = mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Items.TOTEM_OF_UNDYING) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
