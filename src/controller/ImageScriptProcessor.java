package controller;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import model.HandleBlueComponent;
import model.HandleBrighten;
import model.HandleGreenComponent;
import model.HandleHorizontalFlip;
import model.HandleIntensityComponent;
import model.HandleLumaComponent;
import model.HandleRedComponent;
import model.HandleRgbCombine;
import model.HandleRgbSplit;
import model.HandleSave;
import model.HandleSharpen;
import model.HandleValueComponent;
import model.HandleVerticalFlip;
import model.HandleCommand;
import model.HandleLoad;
import model.HandleBlur;
import model.HandleSepia;

public class ImageScriptProcessor {
  private Map<String, BufferedImage> imageMap;

  public ImageScriptProcessor() {
    this.imageMap = new HashMap<>();
  }

  public void executeCommand(String command) {
    String[] parts = command.split("\\s+");
    try {
      HandleCommand handleCommand = null;
      switch (parts[0]) {
        case "load":
          handleCommand = new HandleLoad();
          break;
        case "save":
          handleCommand = new HandleSave();
          break;
        case "value-component":
          handleCommand = new HandleValueComponent();
          break;
        case "intensity-component":
          handleCommand = new HandleIntensityComponent();
          break;
        case "luma-component":
          handleCommand = new HandleLumaComponent();
          break;
        case "red-component":
          handleCommand = new HandleRedComponent();
          break;
        case "green-component":
          handleCommand = new HandleGreenComponent();
          break;
        case "blue-component":
          handleCommand = new HandleBlueComponent();
          break;
        case "brighten":
          handleCommand = new HandleBrighten();
          break;
        case "vertical-flip":
          handleCommand = new HandleVerticalFlip();
          break;
        case "horizontal-flip":
          handleCommand = new HandleHorizontalFlip();
          break;
        case "rgb-split":
          handleCommand = new HandleRgbSplit();
          break;
        case "rgb-combine":
          handleCommand = new HandleRgbCombine();
          break;
        case "blur":
          handleCommand = new HandleBlur();
          break;
        case "sharpen":
          handleCommand = new HandleSharpen();
          break;
        case "sepia":
          handleCommand = new HandleSepia();
          break;
        default:
          System.out.println("Unknown command: " + parts[0]);
      }
      handleCommand.doCommand(parts, imageMap);
    } catch (Exception e) {
      System.out.println("Error executing command: " + e.getMessage());
    }
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

}
