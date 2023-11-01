package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class HandleRgbCombine implements HandleCommand {

  @Override
  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException {
    if (parts.length != 5) throw new IllegalArgumentException("Invalid number of arguments for rgb-combine.");
    BufferedImage redImage = ImageProcessor.getImage(parts[2], imageMap);
    BufferedImage greenImage = ImageProcessor.getImage(parts[3], imageMap);
    BufferedImage blueImage = ImageProcessor.getImage(parts[4], imageMap);
    BufferedImage result = ImageProcessor.combineRGB(redImage, greenImage, blueImage);
    imageMap.put(parts[1], result);
  }
}
