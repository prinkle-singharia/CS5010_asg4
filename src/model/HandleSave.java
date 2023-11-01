package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import view.ImageUtil;

public class HandleSave implements HandleCommand {

  @Override
  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for save.");
    String path = parts[1];
    String imageName = parts[2];
    BufferedImage image = imageMap.get(imageName);
    if (image == null) throw new IllegalArgumentException("Image not found: " + imageName);
    ImageUtil.saveImage(image, path);
  }
}
