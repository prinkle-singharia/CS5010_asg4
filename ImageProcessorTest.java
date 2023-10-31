import static org.junit.Assert.*;
import java.awt.image.BufferedImage;

public class ImageProcessorTest {

  public static void main(String[] args) {
    try {
      // Load the image
      BufferedImage img = ImageUtil.readImage("manhattan-small.png");

      // Test brightening
      BufferedImage brightenedImage = ImageProcessor.adjustBrightness(img, 30);
      ImageUtil.saveImage(brightenedImage, "res/manhattan-small_brightened.png");

      // Test blurring
      BufferedImage blurredImage = ImageProcessor.blurImage(img);
      ImageUtil.saveImage(blurredImage, "res/manhattan-small_blurred.png");

      // Test horizontal flipping
      BufferedImage flippedImage = ImageProcessor.flipImage(img, true);
      ImageUtil.saveImage(flippedImage, "res/manhattan-small_flipped.png");

      // Test individual RGB component extraction
      BufferedImage redComponent = ImageProcessor.extractComponent(img, 'R');
      BufferedImage greenComponent = ImageProcessor.extractComponent(img, 'G');
      BufferedImage blueComponent = ImageProcessor.extractComponent(img, 'B');
      ImageUtil.saveImage(redComponent, "res/manhattan-small_red.png");
      ImageUtil.saveImage(greenComponent, "res/manhattan-small_green.png");
      ImageUtil.saveImage(blueComponent, "res/manhattan-small_blue.png");

      // Test value, intensity, luma
      BufferedImage valueImage = ImageProcessor.extractValue(img);
      BufferedImage intensityImage = ImageProcessor.extractIntensity(img);
      BufferedImage lumaImage = ImageProcessor.extractLuma(img);
      ImageUtil.saveImage(valueImage, "res/manhattan-small_value.png");
      ImageUtil.saveImage(intensityImage, "res/manhattan-small_intensity.png");
      ImageUtil.saveImage(lumaImage, "res/manhattan-small_luma.png");

      // Test vertical flipping
      BufferedImage verticalFlip = ImageProcessor.flipImage(img, false);
      ImageUtil.saveImage(verticalFlip, "res/manhattan-small_vertical_flip.png");

      // Test darkening
      BufferedImage darkenedImage = ImageProcessor.adjustBrightness(img, -30);
      ImageUtil.saveImage(darkenedImage, "res/manhattan-small_darkened.png");

      // Test RGB splitting and combining
      BufferedImage[] rgbImages = ImageProcessor.splitRGB(img);
      BufferedImage combinedImage = ImageProcessor.combineRGB(rgbImages[0], rgbImages[1], rgbImages[2]);
      ImageUtil.saveImage(combinedImage, "res/manhattan-small_combined.png");

      // Test sharpening
      BufferedImage sharpenedImage = ImageProcessor.sharpenImage(img);
      ImageUtil.saveImage(sharpenedImage, "res/manhattan-small_sharpened.png");

      // Test sepia
      BufferedImage sepiaImage = ImageProcessor.applySepia(img);
      ImageUtil.saveImage(sepiaImage, "res/manhattan-small_sepia.png");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}