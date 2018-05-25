package sample;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

/**
 * Created by bagrram on 25/05/2018.
 */
public class JavaImageResizer {
    private static int screnshotsCounter=0;
    public static boolean resizeUsingJavaAlgo(List<String> sources, int width, int height) throws IOException {
        File dest = new File(".//screnshots//");
        for (String source : sources) {
            try {
                BufferedImage sourceImage = ImageIO.read(new URL(source));
                double ratio = calculateRatio(sourceImage.getWidth(), sourceImage.getHeight());
                Image scaled = sourceImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
                BufferedImage bufferedScaled = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = bufferedScaled.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(scaled, 0, 0, width, height, null);
                dest.createNewFile();
                writeJpeg(bufferedScaled, dest.getCanonicalPath(), 1.0f);
            }
            catch (IOException ex){
                continue;
            }



        }
        return true;
    }
    private static double calculateRatio(double width,double height){
        double ratio = width /height;
        if (width < 1) {
            width = (int) (height * ratio + 0.4);
        } else if (height < 1) {
            height = (int) (width /ratio + 0.4);
        }
        return ratio;
    }

    /**
     * Write a JPEG file setting the compression quality.
     *
     * @param image a BufferedImage to be saved
     * @param destFile destination file (absolute or relative path)
     * @param quality a float between 0 and 1, where 1 means uncompressed.
     * @throws IOException in case of problems writing the file
     */
    private static void writeJpeg(BufferedImage image, String destFile, float quality)
        throws IOException {
        ImageWriter writer = null;
        FileImageOutputStream output = null;
        try {
            writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            output = new FileImageOutputStream(new File(destFile+"screen"+String.valueOf(screnshotsCounter++)+".jpg"));
            writer.setOutput(output);
            IIOImage iioImage = new IIOImage(image, null, null);
            writer.write(null, iioImage, param);
            System.out.println("done for "+screnshotsCounter);
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (writer != null) {
                writer.dispose();
            }
            if (output != null) {
                output.close();
            }
        }
    }
}
