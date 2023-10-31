import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class ImageUtil {

  public static BufferedImage readPPM(String filename) {
    try (Scanner sc = new Scanner(new FileInputStream(filename))) {
      StringBuilder builder = new StringBuilder();
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s + System.lineSeparator());
        }
      }

      Scanner sc2 = new Scanner(builder.toString());
      if (!sc2.next().equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
        return null;
      }

      int width = sc2.nextInt();
      int height = sc2.nextInt();
      int maxValue = sc2.nextInt();

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int r = sc2.nextInt() * 255 / maxValue;
          int g = sc2.nextInt() * 255 / maxValue;
          int b = sc2.nextInt() * 255 / maxValue;
          image.setRGB(x, y, (r << 16) | (g << 8) | b);
        }
      }
      return image;
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return null;
    }
  }

  public static BufferedImage readImage(String filename) throws IOException {
    if (filename.endsWith(".ppm")) {
      return readPPM(filename);
    } else {
      return ImageIO.read(new File(filename));
    }
  }

  public static void saveImage(BufferedImage image, String filename) throws IOException {
    if (filename.endsWith(".ppm")) {
      writePPM(image, filename);
    } else {
      String formatName = filename.substring(filename.lastIndexOf('.') + 1);
      ImageIO.write(image, formatName, new File(filename));
    }
  }

  private static void writePPM(BufferedImage image, String filename) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
      bw.write("P3\n");
      bw.write(image.getWidth() + " " + image.getHeight() + "\n");
      bw.write("255\n");

      for (int y = 0; y < image.getHeight(); y++) {
        for (int x = 0; x < image.getWidth(); x++) {
          int color = image.getRGB(x, y);
          int r = (color >> 16) & 0xff;
          int g = (color >> 8) & 0xff;
          int b = color & 0xff;
          bw.write(r + " " + g + " " + b + "\n");
        }
      }
    }
  }

  // Demo main (you can use this for quick testing)
  public static void main(String[] args) {
    String filename;
    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "sample.ppm";
    }
    try {
      BufferedImage image = ImageUtil.readImage(filename);
      // Perform some operations on the image if needed
      // Save it back or to a new file
      // ImageUtil.saveImage(image, "newfilename.ppm");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


