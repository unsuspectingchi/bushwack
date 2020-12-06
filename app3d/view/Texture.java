package app3d.view;

import java.util.ArrayList;
import app3d.utilities.Loader;

public class Texture {

  public static ArrayList<Texture> textures = new ArrayList<Texture>();

  static {
    textures.add(new Texture("app3d/resources/wall.png", 64)); //wall 1
    textures.add(new Texture("app3d/resources/leaves.png", 64)); //foliage 2
    textures.add(new Texture("app3d/resources/leafbreak5.png", 64)); //bushcrack5 3
    textures.add(new Texture("app3d/resources/leafbreak4.png", 64)); //bushcrack4 4
    textures.add(new Texture("app3d/resources/leafbreak3.png", 64)); //bushcrack3 5
    textures.add(new Texture("app3d/resources/leafbreak2.png", 64)); //bushcrack2 6
    textures.add(new Texture("app3d/resources/leafbreak1.png", 64)); //bushcrack1 7
    textures.add(new Texture("app3d/resources/rainbowGem.png", 64)); //win
  }

  public final int[] pixels;
  public final int size;

  public Texture(String url, int size) {
    System.out.println("Constructing Texture from " + url);
    this.size = size;
    pixels = new int[size * size];
    Loader.loadAsBufferedImage(url).getRGB(0, 0, size, size, pixels, 0, size);
  }

}
