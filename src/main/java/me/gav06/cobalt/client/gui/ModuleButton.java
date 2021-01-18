package me.gav06.cobalt.client.gui;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.api.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;

import java.awt.*;

public class ModuleButton {

    int x, y, width, height;

    Module module;

    GuiFrame parent;

    Minecraft mc = Minecraft.getMinecraft();

    public ModuleButton(Module module, int x, int y, GuiFrame parent) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.width = parent.width;
        this.height = 14;
    }

    public void draw(int mx, int my) {
        Gui.drawRect(x + 1, y, x + width - 1, y + height - 1, 0x80000000);

        if (module.getState()) {
            Cobalt.font.drawStringWithShadow(module.getName(), x + 2, y + 2, Util.getRGB(8, 1, .5f));
        }
        else {
            Cobalt.font.drawStringWithShadow(module.getName(), x + 2, y + 2, new Color(154, 154, 154).getRGB());
        }

        if (mx >= this.x && mx <= this.x + this.width && my >= this.y && my <= this.y + this.height) {
            Util.drawTextWithBackdrop(module.getDescription(), mx - (Cobalt.font.getStringWidth(module.getDescription()) / 2), my - 10, -1);
        }
    }

    public void onClick(int x, int y, int button) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F, 0.4F));
            module.toggle();
        }
    }
}