import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer implements Runnable {

    private static final int NEW_WIDTH = 300;

    private final File[] files;
    private final String dstFolder;

    public ImageResizer(File[] files, String dstFolder) {
        this.files = files;
        this.dstFolder = dstFolder;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        resize();
        System.out.println("Thread name: " + Thread.currentThread().getName() + "\n\t duration - " + (System.currentTimeMillis() - start) + " ms");
    }

    private void resize() {
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }

                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) NEW_WIDTH)
                );
                BufferedImage newImage = new BufferedImage(
                        NEW_WIDTH, newHeight, BufferedImage.TYPE_INT_RGB
                );

                int widthStep = image.getWidth() / NEW_WIDTH;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < NEW_WIDTH; x++)
                {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
