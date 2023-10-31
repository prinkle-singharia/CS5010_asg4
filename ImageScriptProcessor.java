import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageScriptProcessor {
  private Map<String, BufferedImage> imageMap;

  public ImageScriptProcessor() {
    this.imageMap = new HashMap<>();
  }

  public void executeCommand(String command) {
    String[] parts = command.split(" ");
    try {
      switch (parts[0]) {
        case "load":
          handleLoad(parts);
          break;
        case "save":
          handleSave(parts);
          break;
        case "value-component":
          handleValueComponent(parts);
          break;
        case "intensity-component":
          handleIntensityComponent(parts);
          break;
        case "luma-component":
          handleLumaComponent(parts);
          break;
        case "brighten":
          handleBrighten(parts);
          break;
        case "vertical-flip":
          handleVerticalFlip(parts);
          break;
        case "horizontal-flip":
          handleHorizontalFlip(parts);
          break;
        case "rgb-split":
          handleRgbSplit(parts);
          break;
        case "rgb-combine":
          handleRgbCombine(parts);
          break;
        case "blur":
          handleBlur(parts);
          break;
        case "sharpen":
          handleSharpen(parts);
          break;
        case "sepia":
          handleSepia(parts);
          break;
        default:
          System.out.println("Unknown command: " + parts[0]);
      }
    } catch (Exception e) {
      System.out.println("Error executing command: " + e.getMessage());
    }
  }

  private void handleLoad(String[] parts) throws IOException {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for load.");
    String path = parts[1];
    String imageName = parts[2];
    BufferedImage image = ImageUtil.readImage(path);
    imageMap.put(imageName, image);
  }

  private void handleSave(String[] parts) throws IOException {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for save.");
    String path = parts[1];
    String imageName = parts[2];
    BufferedImage image = imageMap.get(imageName);
    if (image == null) throw new IllegalArgumentException("Image not found: " + imageName);
    ImageUtil.saveImage(image, path);
  }

  private void handleValueComponent(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for value-component.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.extractValue(image);
    imageMap.put(destImageName, result);
  }

  private void handleIntensityComponent(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for intensity-component.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.extractIntensity(image);
    imageMap.put(destImageName, result);
  }

  private void handleLumaComponent(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for luma-component.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.extractLuma(image);
    imageMap.put(destImageName, result);
  }
  private void handleBrighten(String[] parts) {
    if (parts.length != 4) throw new IllegalArgumentException("Invalid number of arguments for brighten.");
    String imageName = parts[2];
    String destImageName = parts[3];
    int increment = Integer.parseInt(parts[1]);
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.adjustBrightness(image, increment);
    imageMap.put(destImageName, result);
  }

  // handle horizontal-flip command
  private void handleHorizontalFlip(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for horizontal-flip.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.flipImage(image, true);
    imageMap.put(destImageName, result);
  }

  // handle vertical-flip command
  private void handleVerticalFlip(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for vertical-flip.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.flipImage(image, false);
    imageMap.put(destImageName, result);
  }

  // handle rgb-split command
  private void handleRgbSplit(String[] parts) {
    if (parts.length != 5) throw new IllegalArgumentException("Invalid number of arguments for rgb-split.");
    String imageName = parts[1];
    BufferedImage image = getImage(imageName);
    BufferedImage[] rgbImages = ImageProcessor.splitRGB(image);
    imageMap.put(parts[2], rgbImages[0]); // Red
    imageMap.put(parts[3], rgbImages[1]); // Green
    imageMap.put(parts[4], rgbImages[2]); // Blue
  }

  // handle rgb-combine command
  private void handleRgbCombine(String[] parts) {
    if (parts.length != 5) throw new IllegalArgumentException("Invalid number of arguments for rgb-combine.");
    BufferedImage redImage = getImage(parts[2]);
    BufferedImage greenImage = getImage(parts[3]);
    BufferedImage blueImage = getImage(parts[4]);
    BufferedImage result = ImageProcessor.combineRGB(redImage, greenImage, blueImage);
    imageMap.put(parts[1], result);
  }

  // handle blur command
  private void handleBlur(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for blur.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.blurImage(image);
    imageMap.put(destImageName, result);
  }

  // handle sharpen command
  private void handleSharpen(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for sharpen.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.sharpenImage(image);
    imageMap.put(destImageName, result);
  }

  // handle sepia command
  private void handleSepia(String[] parts) {
    if (parts.length != 3) throw new IllegalArgumentException("Invalid number of arguments for sepia.");
    String imageName = parts[1];
    String destImageName = parts[2];
    BufferedImage image = getImage(imageName);
    BufferedImage result = ImageProcessor.applySepia(image);
    imageMap.put(destImageName, result);
  }

  // Utility method to get image from the map
  private BufferedImage getImage(String imageName) {
    BufferedImage image = imageMap.get(imageName);
    if (image == null) throw new IllegalArgumentException("Image not found: " + imageName);
    return image;
  }
  // Method to run a script file
  public void runScript(String scriptPath) {
    try (Scanner scanner = new Scanner(new FileInputStream(scriptPath))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (!line.startsWith("#") && !line.isEmpty()) {
          executeCommand(line);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("Script file not found: " + scriptPath);
    }
  }

  public static void main(String[] args) {
    ImageScriptProcessor processor = new ImageScriptProcessor();
  }

}
