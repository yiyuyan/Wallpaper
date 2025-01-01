package cn.ksmcbrigade.wp.mixin;

import cn.ksmcbrigade.wp.Config;
import cn.ksmcbrigade.wp.renderer.WallpaperRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {
    @Inject(method = "renderMenuBackground",at = @At("HEAD"),cancellable = true)
    private void render(GuiGraphics guiGraphics, CallbackInfo ci) throws Exception {
        if(Config.SPEC.isLoaded() && Config.ENABLED.get() && Config.check()){
            WallpaperRenderer.render(guiGraphics);
            ci.cancel();
        }
    }
}
