package me.gav06.cobalt.client.gui;

import me.gav06.cobalt.api.module.Category;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;

public class ClickGui extends GuiScreen {

    public static ClickGui INSTANCE = new ClickGui();

    ArrayList<GuiFrame> frames;

    public ClickGui(){
        frames = new ArrayList<>();
        int offset = 0;
        for (Category category : Category.values()){
            frames.add(new GuiFrame(category, 10 + offset, 20));
            offset += 110;
        }
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (GuiFrame frame : frames){
            frame.render(mouseX, mouseY);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (GuiFrame frame : frames){
            frame.onClick(mouseX, mouseY, mouseButton);
        }
    }
}