package cn.ksmcbrigade.wp.gui;

import cn.ksmcbrigade.wp.Config;
import cn.ksmcbrigade.wp.utils.ImageUtils;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageWidget extends AbstractButton {

    public String url;
    public byte[] bytes;
    private ResourceLocation dynamicTexture;

    public ImageWidget(int x, int y, int width, int height, Component message,String URL) throws IOException {
        super(x, y, width, height, message);
        this.setUrl(URL);
    }

    public ImageWidget(int x, int y, Component message,String URL) throws IOException {
        this(x, y, 192,48, message,URL);
    }

    public void unregister(){
        ImageUtils.unregister(dynamicTexture);
    }

    @Override
    public void onPress() {
        try {
            new File("config/wp").mkdirs();
            File file = new File("config/wp/wp_"+RandomStringUtils.randomNumeric(12)+".png");
            FileUtils.writeByteArrayToFile(file,bytes);
            Config.PATH.set(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        guiGraphics.fill(this.getX(),this.getY(),this.getX()+this.getWidth(),this.getY()+this.getHeight(), Color.DARK_GRAY.getRGB());
        guiGraphics.blit(dynamicTexture,this.getX(),this.getY(),0,0,this.width,this.height,this.width,this.height);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }

    public void setUrl(String url) throws IOException {
        this.url = url;
        URL URL = new URL(url);
        DynamicTexture dynamic;
        try (InputStream inputStream = URL.openStream()) {
            bytes = inputStream.readAllBytes();
            System.out.println("Loaded image size: "+bytes.length + " bytes");
        }
        //FileUtils.writeByteArrayToFile(new File(RandomStringUtils.randomNumeric(10)+".png"),bytes);
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            dynamic = new DynamicTexture(NativeImage.read(inputStream));
        }
        dynamicTexture = ResourceLocation.fromNamespaceAndPath("wp", this.getMessage().getString());
        ImageUtils.register(dynamic, dynamicTexture);
    }
}
