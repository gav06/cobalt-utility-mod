package me.gav06.cobalt.client.mods.hud;

import me.gav06.cobalt.Cobalt;
import me.gav06.cobalt.api.event.Event;
import me.gav06.cobalt.api.module.Category;
import me.gav06.cobalt.api.module.Module;
import me.gav06.cobalt.api.util.Util;
import me.gav06.cobalt.client.gui.ClickGui;
import me.gav06.cobalt.event.listeners.RenderOverlayEvent;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class TabGUI extends Module {
    Category[] categories = {
            Category.COMBAT,
            Category.RENDER,
            Category.MOVEMENT,
            Category.HUD,
            Category.WORLD};

    boolean expanded;

    public TabGUI() {
        super("TabGUI", "manage modules without clicking", Category.HUD);
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    int index0 = 0;
    int moduleIndex = 0;
    // index 0 = COMBAT
    // index 1 = HUD
    // index 2 =  MOVEMENT
    // index 3 = RENDER
    // index 4 = WORLD

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            int key = Keyboard.getEventKey();
            if (key == Keyboard.KEY_UP) {
                if (!expanded) {
                    if (index0 > 0) {
                        index0--;
                    } else {
                        index0 = 4;
                    }
                } else {
                    if (moduleIndex > 0) {
                        moduleIndex--;
                    } else {
                        moduleIndex = moduleList.size() - 1;
                    }
                }
            }

            if (key == Keyboard.KEY_DOWN) {
                if (!expanded) {
                    if (index0 < 4) {
                        index0++;
                    } else {
                        index0 = 0;
                    }
                }else {
                    if (moduleIndex < moduleList.size() - 1) {
                        moduleIndex++;
                    } else {
                        moduleIndex = 0;
                    }
                }
            }

            if (key == Keyboard.KEY_RIGHT) {
                if (!expanded) {
                    expanded = true;
                    moduleIndex = 0;
                } else {
                    moduleList.get(moduleIndex).toggle();
                }
            }

            if (key == Keyboard.KEY_RETURN) {
                if (expanded) {
                    moduleList.get(moduleIndex).toggle();
                }
            }

            if (key == Keyboard.KEY_LEFT) {
                if (expanded) {
                    expanded = false;
                }
            }
        }
    }

    List<Module> moduleList;
    public void onEvent(Event e) {
        if (e instanceof RenderOverlayEvent) {
            moduleList = Cobalt.moduleManager.getModulesByCategory(categories[index0]);

            if (!(mc.currentScreen instanceof ClickGui)) {
                //category shit
                int offset = 0;
                for (int i = 0; i < categories.length; i++) {
                    Gui.drawRect(2, 13 + offset,
                            80,
                            Cobalt.font.getHeight() + 17 + offset,
                            0x90000000);

                    int xOff = 0;

                    if (i == index0) {
                        xOff = 6;
                    }
                    Cobalt.font.drawStringWithShadow(categories[i].toString(), 5 + xOff, 15 + offset, -1);
                    offset += 12;
                }

                Gui.drawRect(2, 13 + (index0 * 12),
                        5,
                        Cobalt.font.getHeight() + 17 + (index0 * 12),
                        Util.getRGB(8, 1, .6f));

                //module shit
                if (expanded) {

                    int offset_ = 0;
                    int xOff_ = 0;
                    int color = -1;
                    for (int i = 0; i < Cobalt.moduleManager.getModulesByCategory(categories[index0]).size(); i++) {
                        Gui.drawRect(84, 13 + offset_,
                                164,
                                Cobalt.font.getHeight() + 17 + offset_,
                                0x90000000);
                        if (i == moduleIndex) {
                            xOff_ = 6;
                        } else {
                            xOff_ = 0;
                        }

                        if (moduleList.get(i).getState()) {
                            color = Util.getRGB(8, 1, .6f);
                        } else {
                            color = -1;
                        }

                        Cobalt.font.drawStringWithShadow(moduleList.get(i).getName(), 84 + 4 + xOff_, 15 + offset_, color);
                        offset_ += 12;
                    }

                    Gui.drawRect(84, 13 + (moduleIndex * 12),
                            84 + 3,
                            Cobalt.font.getHeight() + 17 + (moduleIndex * 12),
                            Util.getRGB(8, 1, .6f));
                }
            }
        }
    }
}
