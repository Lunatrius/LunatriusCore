package com.github.lunatrius.core.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class GuiHelper {
    private static final RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

    public static void drawItemStackWithSlot(final TextureManager textureManager, final ItemStack itemStack, final int x, final int y) {
        drawItemStackSlot(textureManager, x, y);

        if (itemStack != null && itemStack.getItem() != null) {
            drawItemStack(itemStack, x + 2, y + 2);
        }
    }

    public static void drawItemStackSlot(final TextureManager textureManager, final int x, final int y) {
        textureManager.bindTexture(Gui.statIcons);

        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        final double uScale = 1.0 / 128.0;
        final double vScale = 1.0 / 128.0;

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        drawTexturedRectangle(worldRenderer, x + 1, y + 1, x + 1 + 18, y + 1 + 18, 0, uScale * 0, vScale * 0, uScale * 18, vScale * 18);
        tessellator.draw();
    }

    public static void drawItemStack(final ItemStack itemStack, final int x, final int y) {
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemIntoGUI(itemStack, x, y);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
    }

    public static void drawTexturedRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1) {
        worldRenderer.pos(x0, y0, z).tex(u0, v0).endVertex();
        worldRenderer.pos(x0, y1, z).tex(u0, v1).endVertex();
        worldRenderer.pos(x1, y1, z).tex(u1, v1).endVertex();
        worldRenderer.pos(x1, y0, z).tex(u1, v0).endVertex();
    }

    public static void drawTexturedRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final double textureWidth, final double textureHeight, final int argb) {
        final double u0 = x0 / textureWidth;
        final double v0 = y0 / textureHeight;
        final double u1 = x1 / textureWidth;
        final double v1 = y1 / textureHeight;

        drawTexturedRectangle(worldRenderer, x0, y0, x1, y1, z, u0, v0, u1, v1, argb);
    }

    public static void drawTexturedRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final double u0, final double v0, final double u1, final double v1, final int argb) {
        final int a = (argb >> 24) & 0xFF;
        final int r = (argb >> 16) & 0xFF;
        final int g = (argb >> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawTexturedRectangle(worldRenderer, x0, y0, x1, y1, z, u0, v0, u1, v1, r, g, b, a);
    }

    public static void drawTexturedRectangle(WorldRenderer worldRenderer, double x0, double y0, double x1, double y1, double z, double u0, double v0, double u1, double v1, int r, int g, int b, int a) {
        worldRenderer.pos(x0, y0, z).tex(u0, v0).color(r, g, b, a).endVertex();
        worldRenderer.pos(x0, y1, z).tex(u0, v1).color(r, g, b, a).endVertex();
        worldRenderer.pos(x1, y1, z).tex(u1, v1).color(r, g, b, a).endVertex();
        worldRenderer.pos(x1, y0, z).tex(u1, v0).color(r, g, b, a).endVertex();
    }

    public static void drawColoredRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final int argb) {
        final int a = (argb >> 24) & 0xFF;
        final int r = (argb >> 16) & 0xFF;
        final int g = (argb >> 8) & 0xFF;
        final int b = argb & 0xFF;

        drawColoredRectangle(worldRenderer, x0, y0, x1, y1, z, r, g, b, a);
    }

    public static void drawColoredRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final int r, final int g, final int b, final int a) {
        worldRenderer.pos(x0, y0, z).color(r, g, b, a).endVertex();
        worldRenderer.pos(x0, y1, z).color(r, g, b, a).endVertex();
        worldRenderer.pos(x1, y1, z).color(r, g, b, a).endVertex();
        worldRenderer.pos(x1, y0, z).color(r, g, b, a).endVertex();
    }

    public static void drawVerticalGradientRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final int startColor, final int endColor) {
        final int sa = (startColor >> 24) & 255;
        final int sr = (startColor >> 16) & 255;
        final int sg = (startColor >> 8) & 255;
        final int sb = startColor & 255;
        final int ea = (endColor >> 24) & 255;
        final int er = (endColor >> 16) & 255;
        final int eg = (endColor >> 8) & 255;
        final int eb = endColor & 255;

        drawVerticalGradientRectangle(worldRenderer, x0, y0, x1, y1, z, sr, sg, sb, sa, er, eg, eb, ea);
    }

    public static void drawVerticalGradientRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final int sr, final int sg, final int sb, final int sa, final int er, final int eg, final int eb, final int ea) {
        worldRenderer.pos(x0, y0, z).color(sr, sg, sb, sa).endVertex();
        worldRenderer.pos(x0, y1, z).color(er, eg, eb, ea).endVertex();
        worldRenderer.pos(x1, y1, z).color(er, eg, eb, ea).endVertex();
        worldRenderer.pos(x1, y0, z).color(sr, sg, sb, sa).endVertex();
    }

    public static void drawHorizontalGradientRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final int startColor, final int endColor) {
        final int sa = (startColor >> 24) & 255;
        final int sr = (startColor >> 16) & 255;
        final int sg = (startColor >> 8) & 255;
        final int sb = startColor & 255;
        final int ea = (endColor >> 24) & 255;
        final int er = (endColor >> 16) & 255;
        final int eg = (endColor >> 8) & 255;
        final int eb = endColor & 255;

        drawHorizontalGradientRectangle(worldRenderer, x0, y0, x1, y1, z, sr, sg, sb, sa, er, eg, eb, ea);
    }

    public static void drawHorizontalGradientRectangle(final WorldRenderer worldRenderer, final double x0, final double y0, final double x1, final double y1, final double z, final int sr, final int sg, final int sb, final int sa, final int er, final int eg, final int eb, final int ea) {
        worldRenderer.pos(x0, y0, z).color(sr, sg, sb, sa).endVertex();
        worldRenderer.pos(x0, y1, z).color(sr, sg, sb, sa).endVertex();
        worldRenderer.pos(x1, y1, z).color(er, eg, eb, ea).endVertex();
        worldRenderer.pos(x1, y0, z).color(er, eg, eb, ea).endVertex();
    }
}
