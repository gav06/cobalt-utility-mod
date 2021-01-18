package me.gav06.cobalt.client.gui;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class GuiFrame {

    int x, y, width, height;

    Category category;
    Minecraft mc = Minecraft.getMinecraft();

    ArrayList<ModuleButton> moduleButtons;

    public GuiFrame(Category category, int x, int y){
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 240; //RANDOM NUMBER FOR NOW OK
        this.category = category;

        moduleButtons = new ArrayList<>();
        int offsetY = 14;
        for (Module module : Cobalt.moduleManager.getModulesByCategory(category)){
            moduleButtons.add(new ModuleButton(module, x, y + offsetY, this));
            offsetY += 14;
        }

        this.height = offsetY;
    }

    public void render(int MouseX, int MouseY){
        Gui.drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 100).getRGB());
        Cobalt.font.drawStringWithShadow(category.toString(), x + 2, y + 2, new Color(255, 255, 255).getRGB());
        for (ModuleButton moduleButton : moduleButtons){
            moduleButton.draw(MouseX, MouseY);
        }
    }

    public void onClick(int x, int y, int button){
        for (ModuleButton moduleButton : moduleButtons){
            moduleButton.onClick(x, y, button);
        }
    }
}