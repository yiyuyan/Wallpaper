package cn.ksmcbrigade.wp.utils;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class RenderUtils {

    public static void blit(GuiGraphics guiGraphics, int tex, int x, int y, int width, int height){
        blit(guiGraphics, tex, x, y,width, height, width,height);
    }

    public static void blit(GuiGraphics guiGraphics,int tex,int x,int y,int width,int height,int texWidth,int texHeight){
        blit(guiGraphics, tex, x, y, 0,0,width, height, texWidth, texHeight);
    }

    public static void blit(GuiGraphics guiGraphics, int tex, int x, int y, float u, float v, int width, int height, int texWidth, int texHeight){
        innerBlit(guiGraphics,tex,x,x+width,y,y+height,0,(u+0.0F)/texWidth,(u+width)/texWidth,(v+0.0F)/texHeight,(u+height)/texHeight);
    }

    public static void innerBlit(GuiGraphics guiGraphics,int p_283461_, int p_281399_, int p_283222_, int p_283615_, int p_283430_, int p_281729_, float p_283247_, float p_282598_, float p_282883_, float p_283017_) {
        RenderSystem.setShaderTexture(0, p_283461_);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float)p_281399_, (float)p_283615_, (float)p_281729_).setUv(p_283247_, p_282883_);
        bufferbuilder.addVertex(matrix4f, (float)p_281399_, (float)p_283430_, (float)p_281729_).setUv(p_283247_, p_283017_);
        bufferbuilder.addVertex(matrix4f, (float)p_283222_, (float)p_283430_, (float)p_281729_).setUv(p_282598_, p_283017_);
        bufferbuilder.addVertex(matrix4f, (float)p_283222_, (float)p_283615_, (float)p_281729_).setUv(p_282598_, p_282883_);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
    }
}
