package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class HandleSharpen implements HandleCommand {

  @Override
  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for sharpen.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = ImageProcessor.getImage(imageName, imageMap);
    BufferedImage result = ImageProcessor.sharpenImage(image);
    imageMap.put(destImageName, result);
  }
}
