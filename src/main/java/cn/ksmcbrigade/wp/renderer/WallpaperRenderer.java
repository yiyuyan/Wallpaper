package cn.ksmcbrigade.wp.renderer;

import cn.ksmcbrigade.wp.Config;
import cn.ksmcbrigade.wp.utils.ImageUtils;
import net.minecraft.client.gui.GuiGraphics;

import java.io.IOException;

public class WallpaperRenderer {

    public static void render(GuiGraphics guiGraphics) throws IOException {
        guiGraphics.blit(ImageUtils.load(Config.PATH.get()),0,0,0,0,guiGraphics.guiWidth(),guiGraphics.guiHeight(),guiGraphics.guiWidth(),guiGraphics.guiHeight());
    }
}
