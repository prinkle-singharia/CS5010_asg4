package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class HandleBrighten implements HandleCommand {

  @Override
  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException {
    if (parts.length != 4) throw new IllegalArgumentException("Invalid number of arguments for brighten.");
    String imageName = parts[2];
    String destImageName = parts[3];
    int increment = Integer.parseInt(parts[1]);
    BufferedImage image = ImageProcessor.getImage(imageName, imageMap);
    BufferedImage result = ImageProcessor.adjustBrightness(image, increment);
    imageMap.put(destImageName, result);
  }
}
