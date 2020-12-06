package app3d;

import java.awt.Color;
import javax.swing.JFrame;

import app3d.controller.Controller;
import app3d.model.Model;
import app3d.view.View;

public class App3d extends JFrame implements Runnable {

  private static final long serialVersionUID = 1L;
  public final int WIDTH = 800;
  public final int HEIGHT = 600;
  public final String TITLE = "Maze";
  public final int UPDATES_PER_SECOND = 60;

  public final Controller controller = new Controller();
  public final Model model = new Model(this);
  public final View view = new View(this);
  private final Thread thread = new Thread(this);
  public final double updatesPerNanosecond = ((double) UPDATES_PER_SECOND) / 1000000000;
  private boolean isRunning;

  public App3d() {
    System.out.println("Constructing App3d");
    addKeyListener(controller);
    setSize(WIDTH, HEIGHT);
    setResizable(false);
    setTitle(TITLE);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLocationRelativeTo(null);
    setVisible(true);
    start();
  }

  private synchronized void start() {
    System.out.println("Starting App3d");
    isRunning = true;
    thread.start();
  }

  public synchronized void stop() {
    System.out.println("Stopping App3d");
    isRunning = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    System.out.println("Running App3d");
    long timePrior = System.nanoTime();
    double numUpdates = 0;
    requestFocus();
    while (isRunning) {
      long timeNow = System.nanoTime();
      numUpdates = numUpdates + ((timeNow - timePrior) * updatesPerNanosecond);
      timePrior = timeNow;
      while (numUpdates >= 1) // Make sure update is only happening 60 times a second
      {
        view.screen.update();
        model.camera.update();
        numUpdates--;
      }
      view.render();
    }
  }

}
