package app3d.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Loader {

  public static int[][] load2dIntegerArray(String url) {
    Scanner scanner = null;
    try {
      scanner = new Scanner(new BufferedReader(new FileReader(url)));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(0);
    }
    ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      ArrayList<Integer> row = new ArrayList<>();
      for (int i = 0; i < line.length(); ++i) {
        row.add(Integer.parseInt(String.valueOf(line.charAt(i) == ' ' ? '0' : line.charAt(i))));
      }
      rows.add(row);
    }
    return convertTo2dArray(rows);
  }

  public static BufferedImage loadAsBufferedImage(String url) {
    try {
      return ImageIO.read(new File(url));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static int[][] convertTo2dArray(ArrayList<ArrayList<Integer>> arrayList) {
    int[][] ra = new int[arrayList.size()][arrayList.get(0).size()];
    for (int row = 0; row < arrayList.size(); ++row) {
      for (int col = 0; col < arrayList.get(0).size(); ++col) {
        ra[row][col] = arrayList.get(row).get(col).intValue();
      }
    }
    return ra;
  }

}
