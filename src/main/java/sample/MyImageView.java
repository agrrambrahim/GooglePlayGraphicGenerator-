package sample;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.scene.image.ImageView;

/**
 * Created by bagrram on 23/05/2018.
 */
public class MyImageView extends ImageView{
    private String originalImage;
    public MyImageView(String imageThumbnailLink, String originalImage) {
        super(imageThumbnailLink);
        this.originalImage=originalImage;
    }
    public BufferedImage loadOriginalImage() throws IOException {
        return ImageIO.read(new URL(originalImage));
    }

    public String getOriginalImageLink() {
        return originalImage;
    }
}
