package cn.ksmcbrigade.wp.mixin;

import cn.ksmcbrigade.wp.Config;
import cn.ksmcbrigade.wp.renderer.WallpaperRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.WinScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WinScreen.class)
public class WinScreenMixin {
    @Inject(method = "renderBackground",at = @At("HEAD"),cancellable = true)
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) throws Exception {
        if(Config.SPEC.isLoaded() && Config.ENABLED.get() && Config.check() && Config.WIN_SCREEN_ENABLED.get()){
            WallpaperRenderer.render(guiGraphics);
            ci.cancel();
        }
    }
}
