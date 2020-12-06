package app3d.view;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import app3d.App3d;

public class View {

  private final BufferedImage image;
  public final int[] pixels;
  public final Screen screen;
  public App3d app;

  public View(App3d app) {
    System.out.println("Constructing View");
    this.app = app;
    image = new BufferedImage(app.WIDTH, app.HEIGHT, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    screen = new Screen(this);
  }

  public void render() {
    BufferStrategy bs = app.getBufferStrategy();
    if (bs == null) {
      app.createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    bs.show();
  }

}
