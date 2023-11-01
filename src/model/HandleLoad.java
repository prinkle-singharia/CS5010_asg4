package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import view.ImageUtil;

public class HandleLoad implements HandleCommand {

  @Override
  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException {
    if (parts.length != 3)
      throw new IllegalArgumentException("Invalid number of arguments for load.");
    String path = parts[1];
    String imageName = parts[2];
    BufferedImage image = ImageUtil.readImage(path);
    imageMap.put(imageName, image);
  }
}
