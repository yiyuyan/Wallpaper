package cn.ksmcbrigade.wp;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.io.IOException;

@Mod(Wallpaper.MODID)
public class Wallpaper {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "wp";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public Wallpaper(IEventBus modEventBus, ModContainer modContainer) throws IOException {
        modContainer.registerConfig(ModConfig.Type.CLIENT,Config.SPEC);
        LOGGER.info("Wallpaper mod loaded.");
    }
}
