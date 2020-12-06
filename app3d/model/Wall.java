package app3d.model;
import app3d.model.Sound;

public class Wall {

  private int xPos;
  private int yPos;
  //private int durability;

  public Wall(int xPos, int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
    //durability = 5;
  }

  public int getX() {
    return xPos;
  }

  public int getY() {
    return yPos;
  }

  // public int mine() {
  //   return --durability;
  // }

  // public void destroy() {
  //   String audioFilePath = "app3d/resources/bushDestroy.wav";
  //   Sound player = new Sound();
  //   player.play(audioFilePath);
  // }
}
