package app3d.view;

import java.awt.Color;
import java.util.ArrayList;

import app3d.model.Camera;

public class Screen {

  private Camera camera;
  private int[][] map;
  private View view;
  private int width;
  private int height;
  public final ArrayList<Texture> textures = Texture.textures;

  public Screen(View view) {
    System.out.println("Constructing Screen");
    this.view = view;
    this.camera = view.app.model.camera;
    this.map = view.app.model.map;
    this.width = view.app.WIDTH;
    this.height = view.app.HEIGHT;
  }

  public void update() {
    for (int n = 0; n < view.pixels.length / 2; n++) {
      if (view.pixels[n] != Color.DARK_GRAY.getRGB()) {
        view.pixels[n] = Color.DARK_GRAY.getRGB();
      }
    }
    for (int i = view.pixels.length / 2; i < view.pixels.length; i++) {
      if (view.pixels[i] != Color.gray.getRGB()) {
        view.pixels[i] = Color.gray.getRGB();
      }
    }

    for (int x = 0; x < width; x = x + 1) {
      double cameraX = 2 * x / (double) (width) - 1;
      double rayDirX = camera.xDir + camera.xPlane * cameraX;
      double rayDirY = camera.yDir + camera.yPlane * cameraX;
      // Map position
      int mapX = (int) camera.xPos;
      int mapY = (int) camera.yPos;
      // length of ray from current position to next x or y-side
      double sideDistX;
      double sideDistY;
      // Length of ray from one side to next in map
      double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
      double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
      double perpWallDist;
      int stepX, stepY; // Direction to go in x and y
      boolean hit = false; // was a wall hit
      int side = 0; // was the wall vertical or horizontal
      // Figure out the step direction and initial distance to a side
      if (rayDirX < 0) {
        stepX = -1;
        sideDistX = (camera.xPos - mapX) * deltaDistX;
      } else {
        stepX = 1;
        sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
      }
      if (rayDirY < 0) {
        stepY = -1;
        sideDistY = (camera.yPos - mapY) * deltaDistY;
      } else {
        stepY = 1;
        sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
      }
      // Loop to find where the ray hits a wall
      while (!hit) {
        // Jump to next square
        if (sideDistX < sideDistY) {
          sideDistX += deltaDistX;
          mapX += stepX;
          side = 0;
        } else {
          sideDistY += deltaDistY;
          mapY += stepY;
          side = 1;
        }
        // Check if ray has hit a wall
        if (mapX >= 0 && mapX < 12 && mapY >= 0 && mapY <= 12 && map[mapX][mapY] > 0) {
          hit = true;
        }
      }
      // Calculate distance to the point of impact
      if (side == 0) {
        perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
      } else {
        perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
      }
      // Now calculate the height of the wall based on the distance from the camera
      int lineHeight;
      if (perpWallDist > 0) {
        lineHeight = Math.abs((int) (height / perpWallDist));
      } else {
        lineHeight = height;
      }
      // calculate lowest and highest pixel to fill in current stripe
      int drawStart = -lineHeight / 2 + height / 2;
      if (drawStart < 0) {
        drawStart = 0;
      }
      int drawEnd = lineHeight / 2 + height / 2;
      if (drawEnd >= height) {
        drawEnd = height - 1;
      }
      // add a texture
      int texNum = map[mapX][mapY] - 1;
      double wallX; // Exact position of where wall was hit
      if (side == 1) { // it's a y-axis wall
        wallX = (camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX);
      } else { // it's an x-axis wall
        wallX = (camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY);
      }
      wallX -= Math.floor(wallX);
      int texX = (int) (wallX * (textures.get(texNum).size)); // x coordinate on the texture
      if (side == 0 && rayDirX > 0) {
        texX = textures.get(texNum).size - texX - 1;
      }
      if (side == 1 && rayDirY < 0) {
        texX = textures.get(texNum).size - texX - 1;
      }
      for (int y = drawStart; y < drawEnd; y++) {
        int texY = (((y * 2 - height + lineHeight) << 6) / lineHeight) / 2; // y coordinate on the texture
        int color;
        if (side == 0) {
          color = textures.get(texNum).pixels[texX + (texY * textures.get(texNum).size)];
        } else {
          // make y-sides darker
          color = (textures.get(texNum).pixels[texX + (texY * textures.get(texNum).size)] >> 1) & 8355711;
        }
        view.pixels[x + y * (width)] = color;
      }
    }
  }

}
