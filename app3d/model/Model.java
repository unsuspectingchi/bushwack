package app3d.model;

import java.util.ArrayList;

import app3d.App3d;
import app3d.utilities.Loader;

public class Model {

  public final String MAP_URL = "app3d/resources/map.txt";

  public final Camera camera;

  public int[][] map = Loader.load2dIntegerArray(MAP_URL);
  public final int mapHeight = map.length;
  public final int mapWidth = map[0].length;
  private final int NUM_RIVERS = 3;
  private final int WALL_TEX_NUM = 1;
  private final int CITY_TEX_NUM = 2;
  private ArrayList<Bush> bushes = new ArrayList<>();
  private ArrayList<Bush> bushesToBeRemoved = new ArrayList<>();
  private ArrayList<Wall> walls = new ArrayList<>();

  public Model(App3d app) {
    System.out.println("Constructing Model");
    camera = new Camera(app, 2, 40, 1, 0, 0, -0.66);

    int cityX = (int) Math.random() * mapWidth;
    int cityY = (int) Math.random() * mapHeight;
    City lostCity = new City(cityX, cityY);
    map[cityX][cityY] = CITY_TEX_NUM;

    for (int i = 0; i < NUM_RIVERS; ++i) {
      int riverX = (int) Math.random() * mapWidth;
      int riverY = (int) Math.random() * mapHeight;
      walls.add(new Wall(riverX, riverY));
      map[riverX][riverY] = WALL_TEX_NUM;
    }

  }

  public void mine(int x, int y) {
    if (bushes.size() < 1) return;
    for (Bush bush : bushes) {
      if (bush.getX() == x && bush.getY() == y) {
        int durability = bush.mine();
        if (durability < 1) {
          bushesToBeRemoved.add(bush);
          bush.destroy();
        } else {
          map[x][y] = durability + 5;
        }
      }
    }
    for (Bush bush : bushesToBeRemoved) {
      map[bush.getX()][bush.getY()] = 0;
      bushes.remove(bush);
    }
    bushesToBeRemoved.clear();
  }

}
