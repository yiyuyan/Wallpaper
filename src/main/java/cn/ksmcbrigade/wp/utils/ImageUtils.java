package cn.ksmcbrigade.wp.utils;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

public class ImageUtils {

    public static final Map<String,ResourceLocation> loadedLocation = new HashMap<>();
    public static final Map<String,Integer> loaded = new HashMap<>();
    public static final Map<ResourceLocation,DynamicTexture> loadedTexture = new HashMap<>();

    public static int loadTexture(String filePath) {
        if(loaded.containsKey(filePath)) return loaded.get(filePath);
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        try (MemoryStack ignored = MemoryStack.stackPush()) {
            ByteBuffer imageBuffer = stbi_load(filePath, w, h, channels, 4);
            if (imageBuffer == null) {
                throw new RuntimeException("Failed to load texture: " + stbi_failure_reason());
            }

            int width = w.get(0);
            int height = h.get(0);

            int textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            final boolean b = filePath.toLowerCase().endsWith(".jpg") || filePath.toLowerCase().endsWith(".jpeg");
            glTexImage2D(GL_TEXTURE_2D, 0, (b ?GL_RGB:GL_RGBA), width, height, 0,
                    (b ?GL_RGB:GL_RGBA), GL_UNSIGNED_BYTE, imageBuffer);

            glGenerateMipmap(GL_TEXTURE_2D);

            stbi_image_free(imageBuffer); // Free native memory
            imageBuffer.clear();

            loaded.put(filePath,textureID);

            return textureID;
        }
    }

    public static ResourceLocation load(String file) throws IOException {
        if(loadedLocation.containsKey(file)) return loadedLocation.get(file);
        File file1 = new File(file);
        ResourceLocation textureResourceLocation = ResourceLocation.tryBuild("wp","textures/" + file1.getName().toLowerCase().replace(" ","_"));
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        ByteBuffer imageBuffer = stbi_load(file, w, h, channels, 4);
        if (imageBuffer == null) {
            throw new RuntimeException("Failed to load texture: " + stbi_failure_reason());
        }
        stbi_image_free(imageBuffer);
        imageBuffer.clear();
        try (InputStream in = FileUtils.openInputStream(file1)){
            DynamicTexture texture = new DynamicTexture(NativeImage.read(channels.get(0)==4?NativeImage.Format.RGBA: NativeImage.Format.RGB,in));
            Minecraft.getInstance().getTextureManager().register(textureResourceLocation, texture);
            loadedLocation.put(file,textureResourceLocation);
        }
        return textureResourceLocation;
    }

    public static File toPng(File file) throws IOException {
        File file1 = new File(file+".png");
        BufferedImage image = ImageIO.read(file);
        BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        outputImage.createGraphics().drawImage(image, 0, 0, null);
        ImageIO.write(outputImage,"png",file1);
        return file1;
    }

    public static DynamicTexture register(DynamicTexture dynamicTexture,ResourceLocation key){
        if(loadedTexture.containsKey(key)){
            return dynamicTexture;
        }
        else{
            Minecraft.getInstance().textureManager.register(key,dynamicTexture);
            loadedTexture.put(key,dynamicTexture);
            return dynamicTexture;
        }
    }

    public static void unregister(ResourceLocation resourceLocation){
        Minecraft.getInstance().textureManager.release(resourceLocation);
        loadedTexture.remove(resourceLocation);
    }
}
