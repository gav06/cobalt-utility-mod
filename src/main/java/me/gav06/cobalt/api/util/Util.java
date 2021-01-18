package me.gav06.cobalt.api.util;

import me.gav06.cobalt.Cobalt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Util {

    private static ScaledResolution sr;
    public static void drawTextWithBackdrop(String text, int x, int y, int color) {
        sr = new ScaledResolution(Minecraft.getMinecraft());
        int w = sr.getScaledWidth();
        int h = sr.getScaledHeight();
        Gui.drawRect(x - 1,y - 1,x +  Cobalt.font.getStringWidth(text) + 1, y + Cobalt.font.getHeight() + 1, 0x80000000 );
        Cobalt.font.drawStringWithShadow(text, x, y, color);
    }

    public static int getRGBWave(float seconds, float brightness, float saturation, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static int getRGB(float seconds, float brightness, float saturation) {
        float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (float) (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static void blockESPBox(BlockPos blockPos, int r, int g, int b, float a)
    {
        double x =
                blockPos.getX()
                        - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double y =
                blockPos.getY()
                        - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double z =
                blockPos.getZ()
                        - Minecraft.getMinecraft().getRenderManager().renderPosZ;



        //GL11.glBlendFunc(770, 771);
        //GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(2.0F);
        //GL11.glColor4d(0, 1, 0, 0.15F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        //GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        //drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        //lukflugs code
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(/*GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA,GL11.GL_ZERO,GL11.GL_ONE*/ 770, 771);

        RenderGlobal.renderFilledBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), r, g, b, a);
        RenderGlobal.drawBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0, r, g, b, .5f);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
    }

    //from salhack by ionar
    public static void drawLine(float x, float y, float x1, float y1, float thickness, int hex) {
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        float alpha = (hex >> 24 & 0xFF) / 255.0F;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GL11.glLineWidth(5);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH, GL11.GL_NICEST);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) x, (double) y, (double) 0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double) x1, (double) y1, (double) 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
