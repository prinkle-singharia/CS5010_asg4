import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class ImageProcessor {

  public static BufferedImage extractComponent(BufferedImage original, char component) {
    BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        int rgb = original.getRGB(x, y);
        int newRgb;
        switch (component) {
          case 'R':
            newRgb = (rgb & 0xFF0000);
            break;
          case 'G':
            newRgb = (rgb & 0x00FF00);
            break;
          case 'B':
            newRgb = (rgb & 0x0000FF);
            break;
          default:
            throw new IllegalArgumentException("Invalid component: " + component);
        }
        result.setRGB(x, y, newRgb);
      }
    }
    return result;
  }

  public static BufferedImage flipImage(BufferedImage original, boolean horizontal) {
    int width = original.getWidth();
    int height = original.getHeight();
    BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int newX = horizontal ? width - 1 - x : x;
        int newY = horizontal ? y : height - 1 - y;
        flipped.setRGB(newX, newY, original.getRGB(x, y));
      }
    }
    return flipped;
  }

  public static BufferedImage adjustBrightness(BufferedImage original, int increment) {
    BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        Color color = new Color(original.getRGB(x, y));
        int r = Math.min(Math.max(color.getRed() + increment, 0), 255);
        int g = Math.min(Math.max(color.getGreen() + increment, 0), 255);
        int b = Math.min(Math.max(color.getBlue() + increment, 0), 255);
        result.setRGB(x, y, new Color(r, g, b).getRGB());
      }
    }
    return result;
  }

  public static BufferedImage[] splitRGB(BufferedImage original) {
    BufferedImage redChannel = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    BufferedImage greenChannel = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    BufferedImage blueChannel = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        int rgb = original.getRGB(x, y);
        redChannel.setRGB(x, y, rgb & 0xFF0000);
        greenChannel.setRGB(x, y, rgb & 0x00FF00);
        blueChannel.setRGB(x, y, rgb & 0x0000FF);
      }
    }
    return new BufferedImage[]{redChannel, greenChannel, blueChannel};
  }

  public static BufferedImage combineRGB(BufferedImage red, BufferedImage green, BufferedImage blue) {
    int width = red.getWidth();
    int height = red.getHeight();
    BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int r = (red.getRGB(x, y) >> 16) & 0xFF;
        int g = (green.getRGB(x, y) >> 8) & 0xFF;
        int b = blue.getRGB(x, y) & 0xFF;
        combined.setRGB(x, y, (r << 16) | (g << 8) | b);
      }
    }
    return combined;
  }

  public static BufferedImage applySepia(BufferedImage original) {
    BufferedImage sepia = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        Color color = new Color(original.getRGB(x, y));
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int newRed = Math.min((int)(0.393 * r + 0.769 * g + 0.189 * b), 255);
        int newGreen = Math.min((int)(0.349 * r + 0.686 * g + 0.168 * b), 255);
        int newBlue = Math.min((int)(0.272 * r + 0.534 * g + 0.131 * b), 255);
        sepia.setRGB(x, y, new Color(newRed, newGreen, newBlue).getRGB());
      }
    }
    return sepia;
  }

  public static BufferedImage applyFilter(BufferedImage original, float[] matrix, int matrixSize) {
    BufferedImage filtered = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    Kernel kernel = new Kernel(matrixSize, matrixSize, matrix);
    ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    convolve.filter(original, filtered);
    return filtered;
  }

  public static BufferedImage blurImage(BufferedImage original) {
    float[] blurMatrix = {
            1 / 16f, 1 / 8f, 1 / 16f,
            1 / 8f, 1 / 4f, 1 / 8f,
            1 / 16f, 1 / 8f, 1 / 16f
    };
    return applyFilter(original, blurMatrix, 3);
  }

  public static BufferedImage sharpenImage(BufferedImage original) {
    float[] sharpenMatrix = {
            -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f,
            -1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f,
            -1 / 8f, 1 / 4f, 1 , 1 / 4f, -1 / 8f,
            -1 / 8f, 1 / 4f, 1 / 4f, 1 / 4f, -1 / 8f,
            -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f,
    };
    return applyFilter(original, sharpenMatrix, 3);
  }

  public static BufferedImage extractValue(BufferedImage original) {
    BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        Color color = new Color(original.getRGB(x, y));
        int maxComponent = Math.max(color.getRed(), Math.max(color.getGreen(), color.getBlue()));
        result.setRGB(x, y, new Color(maxComponent, maxComponent, maxComponent).getRGB());
      }
    }
    return result;
  }

  public static BufferedImage extractIntensity(BufferedImage original) {
    BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        Color color = new Color(original.getRGB(x, y));
        int avgComponent = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
        result.setRGB(x, y, new Color(avgComponent, avgComponent, avgComponent).getRGB());
      }
    }
    return result;
  }

  public static BufferedImage extractLuma(BufferedImage original) {
    BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < original.getWidth(); x++) {
      for (int y = 0; y < original.getHeight(); y++) {
        Color color = new Color(original.getRGB(x, y));
        int luma = (int)(0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
        result.setRGB(x, y, new Color(luma, luma, luma).getRGB());
      }
    }
    return result;
  }


}
