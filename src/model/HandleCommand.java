package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public interface HandleCommand {

  public void doCommand(String[] parts, Map<String, BufferedImage> imageMap) throws IOException;

}
