package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class HandleRgbSplit implements HandleCommand {

  @Override
  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException {
    if (parts.length != 5) throw new IllegalArgumentException("Invalid number of arguments for rgb-split.");
    String imageName = parts[1];
    BufferedImage image = ImageProcessor.getImage(imageName, imageMap);
    BufferedImage[] rgbImages = ImageProcessor.splitRGB(image);
    imageMap.put(parts[2], rgbImages[0]); // Red
    imageMap.put(parts[3], rgbImages[1]); // Green
    imageMap.put(parts[4], rgbImages[2]); // Blue
  }
}
