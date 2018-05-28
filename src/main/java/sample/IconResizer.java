package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class IconResizer {

public static String RESULT_SUFFIXE ="ic_launcher";
public static EnumSet<Thumbnail> ICON_RESOLUTIONS = EnumSet.allOf(Thumbnail.class);
private String destinationPath;
private String iconPath;
private File destinationPathFile;
private File iconPathFile;




    public IconResizer(File destinationPath, File iconPath) {
        destinationPathFile = destinationPath;
        iconPathFile = iconPath;
    }

    public boolean processResizing() throws IOException {
          JavaImageResizer resizer = new JavaImageResizer();
        BufferedImage icon = ImageIO.read(iconPathFile);
        BufferedImage newBufferedImage = new BufferedImage(icon.getWidth(),
                icon.getHeight(), BufferedImage.TYPE_INT_ARGB);

        newBufferedImage.createGraphics().drawImage(icon, 0, 0, Color.lightGray, null);


        for (Thumbnail resolution : ICON_RESOLUTIONS) {
            resizer.ResizeAndroidIcon(destinationPathFile, newBufferedImage,resolution);
        }
      return true;
    }

}
