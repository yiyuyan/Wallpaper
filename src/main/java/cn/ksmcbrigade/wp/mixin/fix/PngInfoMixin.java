package cn.ksmcbrigade.wp.mixin.fix;

import net.minecraft.util.PngInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;

@Mixin(PngInfo.class)
public class PngInfoMixin {

    @Inject(method = "validateHeader",at = @At("HEAD"),cancellable = true)
    private static void v(ByteBuffer buffer, CallbackInfo ci){
        ci.cancel();
    }
}
