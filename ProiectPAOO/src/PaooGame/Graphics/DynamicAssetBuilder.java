package PaooGame.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
public class DynamicAssetBuilder {
    private final int width;
    private final int height;
    private final List<BufferedImage> layers;

    public DynamicAssetBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
    }

    public void addLayer(String filepath) throws IOException {
        BufferedImage image = ImageLoader.LoadImage(filepath);
        if(image.getWidth() != width || image.getHeight() != height){
            throw new IllegalArgumentException("Layer image dimensions must match!");
        }
        layers.add(image);
    }

    public void buildAsset(String outputFilePath, String outputName) throws IOException {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) result.getGraphics();

        if (layers.isEmpty()) {
            System.err.println("Warning: No layers were added. The result will be empty.");
        }

        for (BufferedImage layer : layers) {
            if (layer == null) {
                System.err.println("Warning: Encountered a null layer. Skipping...");
                continue;
            }

            System.out.println("Drawing layer: " + layer.toString());
            g.drawImage(layer, 0, 0, null);
        }

        //Due to weird limitations and trying to use relative paths,
        //the path where this writes to is in the out/production area instead of the actual compilable project
        ImageLoader.SaveImagePNG(result,outputFilePath, outputName);
    }


}
