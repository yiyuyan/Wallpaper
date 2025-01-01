package cn.ksmcbrigade.wp.mixin;

import cn.ksmcbrigade.wp.Config;
import cn.ksmcbrigade.wp.renderer.WallpaperRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "renderPanorama",at = @At("HEAD"),cancellable = true)
    private void render(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) throws Exception {
        if(Config.SPEC.isLoaded() && Config.ENABLED.get() && Config.check()){
            if(((Screen)((Object)this)) instanceof WinScreen && !Config.WIN_SCREEN_ENABLED.get()) return;
            WallpaperRenderer.render(guiGraphics);
            ci.cancel();
        }
    }

    @Inject(method = "renderBlurredBackground",at = @At("HEAD"),cancellable = true)
    private void render(float partialTick, CallbackInfo ci){
        if(Config.SPEC.isLoaded() && Config.ENABLED.get() && Config.check() && !Config.BLUR_ENABLED.get()){
            ci.cancel();
        }
    }
}
