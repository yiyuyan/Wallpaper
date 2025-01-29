package cn.ksmcbrigade.wp.mixin;

import cn.ksmcbrigade.wp.Config;
import cn.ksmcbrigade.wp.gui.DownloadWallpapersFromInternetScreen;
import cn.ksmcbrigade.wp.renderer.WallpaperRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Unique
    private Button button;

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "renderBackground",at = @At("HEAD"),cancellable = true)
    private void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) throws Exception {
        if(Config.SPEC.isLoaded() && Config.ENABLED.get() && Config.check() && Config.TITLE_SCREEN_ENABLED.get()){
            WallpaperRenderer.render(guiGraphics);
            ci.cancel();
        }
    }

    @Inject(method = "init",at = @At("TAIL"))
    private void init(CallbackInfo ci){
        button = Button.builder(Component.literal("DownloadWallpapers"),(button)-> Minecraft.getInstance().setScreen(new DownloadWallpapersFromInternetScreen())).width(100).pos(2,this.height-2-20).build();
        this.addWidget(button);
    }

    @Inject(method = "render",at = @At("TAIL"))
    private void render2(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci){
        button.render(guiGraphics, mouseX, mouseY, partialTick);
    }

}
