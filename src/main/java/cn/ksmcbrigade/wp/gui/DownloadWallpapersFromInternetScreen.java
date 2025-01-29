package cn.ksmcbrigade.wp.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.io.IOException;
import java.time.chrono.MinguoEra;

public class DownloadWallpapersFromInternetScreen extends Screen {

    private boolean first = true;
    private boolean full = false;
    private ImageWidget imageWidget01,imageWidget02,imageWidget03,imageWidget04;

    public DownloadWallpapersFromInternetScreen() {
        super(Component.literal("Download Wallpapers"));
    }

    @Override
    protected void init() {
        full = Minecraft.getInstance().options.fullscreen().get();
        if(first) Minecraft.getInstance().options.fullscreen().set(true);
        this.addRenderableWidget(Button.builder(Component.literal("Random"),(button)->{
            try {
                System.out.println("Random the wallpapers...");
                random();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).pos(15,this.height/2).build());
        this.addRenderableWidget(Button.builder(Component.literal("Return"),(button)->{
            this.onClose();
        }).pos(15,this.height/2+2+20).build());
        try {
            if(first){
                this.random();
                first = false;
            }
            this.addRenderableWidget(this.imageWidget01);
            this.addRenderableWidget(this.imageWidget02);
            this.addRenderableWidget(this.imageWidget03);
            this.addRenderableWidget(this.imageWidget04);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void random() throws IOException {
        boolean set01 = true,set02=true,set03=true,set04=true;
        if(imageWidget01==null){
            imageWidget01 = new ImageWidget(15+150+5,this.height/5-32,Component.literal("internent_image_1"+ RandomStringUtils.randomNumeric(7)),"https://www.dmoe.cc/random.php");
            set01 = false;
        }
        if(imageWidget02==null){
            imageWidget02 = new ImageWidget(15+150+5,this.height/5-32+2+64-20,Component.literal("internent_image_2"+ RandomStringUtils.randomNumeric(7)),"https://api.paugram.com/wallpaper/");
            set02 = false;
        }
        if(imageWidget03==null){
            imageWidget03 = new ImageWidget(15+150+5,this.height/5-32+2*2+2*64-30,Component.literal("internent_image_3"+ RandomStringUtils.randomNumeric(7)),"https://imgapi.xl0408.top/index.php");
            set03 = false;
        }
        if(imageWidget04==null){
            imageWidget04 = new ImageWidget(15+150+5,this.height/5-32+2*3+64*3-46,Component.literal("internent_image_4"+ RandomStringUtils.randomNumeric(7)),"https://api.likepoems.com/img/pc");
            set04 = false;
        }
        if(set01){
            imageWidget01.unregister();
            imageWidget01.setUrl("https://www.dmoe.cc/random.php");
        }
        if(set02){
            imageWidget02.unregister();
            imageWidget02.setUrl("https://api.paugram.com/wallpaper/");
        }
        if(set03){
            imageWidget03.unregister();
            imageWidget03.setUrl("https://imgapi.xl0408.top/index.php");
        }
        if(set04){
            imageWidget04.unregister();
            imageWidget04.setUrl("https://api.likepoems.com/img/pc");
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font,this.title,this.width/2,8, Color.WHITE.getRGB());
        //System.out.println(imageWidget04.getY()+":"+imageWidget03.getY());
    }

    @Override
    public void onClose() {
        if(!full){
            Minecraft.getInstance().options.fullscreen().set(false);
        }
        super.onClose();
    }
}
