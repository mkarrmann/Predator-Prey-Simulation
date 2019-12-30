///////////////////////////////////////////////////////////////////////////////////////////////////
//
// Title: Predator-Prey Simulation
// Description: Defines a GUI to simulate predators (tigers) chasing prey (deer), while the prey
// run away. User has ability to add, remove, and drag-and-drop either tigers or deer.
// Author: Matthew Karrmann
// Email: mattkarrmann@gamil.com
//
// Note: This is an extension of class project for UW-Madison CS 200. The original project served
// as an introduction to GUIs, and included adding the ability to add, remove, and drag-and-drop
// both tigers and deer. All file names, relevant method headers, and some starter code was
// provided. Additionally, all credit for images used goes to the UW-Madison Computer Science
// Department. All code related to the animals moving on their own was developed independently by
// myself.
//
///////////////////////////////////////////////////////////////////////////////////////////////////


import java.util.ArrayList;

/**
 * Extends PApplet. Defines the single instance of this applet which will be run.
 * 
 * @author Mouna Kacem, Matthew Karrmann
 *
 */
public class JunglePark extends PApplet {
  private PImage backgroundImage; // PImage object that represents the background image
  // ArrayList storing the current graphical objects(animals present in the Jungle Park and buttons)
  protected ArrayList<ParkGUI> listGUI;

  protected final int WIDTH = 800;
  protected final int HEIGHT = 632;
  public int frameCounter;
  public int deersEaten;

  /**
   * CallBack method Defines initial environment properties such as screen size and to load
   * background images and fonts as the program starts Initializes the backgroundImage and listGUI
   * instance fields.
   */
  @Override
  public void setup() {
    this.getSurface().setTitle("Jungle Park"); // Displays text in the title of the display
                                               // window
    this.textAlign(PApplet.CENTER, PApplet.CENTER); // Sets the current alignment for drawing
                                                    // text
                                                    // to CENTER
    this.imageMode(PApplet.CENTER); // Sets the location from which images are drawn to CENTER
    this.rectMode(PApplet.CORNERS); // Sets the location from which rectangles are drawn.
    // rectMode(CORNERS) interprets the first two parameters of rect() method as the location of
    // one
    // corner, and the third and fourth parameters as the location of the opposite corner.
    // rect() method draws a rectangle to the display window
    this.focused = true; // Confirms that our Processing program is "focused," meaning that
    // it is active and will accept mouse or keyboard input.
    backgroundImage = this.loadImage("images/background.png"); // load the background image

    listGUI = new ArrayList<ParkGUI>(); // create the listGUI ArrayList that would store all the
    // graphic objects (animals and buttons) that would be drawn on the display window

    listGUI.add(new AddAnimalButton("Tiger", 43, 16, this));
    listGUI.add(new AddAnimalButton("Deer", 129, 16, this));
    listGUI.add(new ClearButton(215, 16, this));
    frameCounter = 0;
    deersEaten = 0;
  }

  /**
   * Sets the size of the application display window
   */
  @Override
  public void settings() {
    size(WIDTH, HEIGHT); // sets the size of the display window to 800 x 632 pixels
  }

  /**
   * Prints information related to current status in console.
   * 
   */
  private void printInfo() {
    // Counts number of deer and tigers currently alive and, if info is to be displayed this frame,
    // determines how long the longest living deer has been alive:
    int deerCount = 0;
    int tigerCount = 0;
    int maxTimeAlive = 0;
    for (ParkGUI graphicalObject : listGUI) {
      if (graphicalObject instanceof Deer) {
        ++deerCount;
        if (frameCount % 30 == 0 && ((Deer) graphicalObject).timeAlive > maxTimeAlive) {
          maxTimeAlive = ((Deer) graphicalObject).timeAlive;
        }
      }
    }
    for (ParkGUI graphicalObject : listGUI) {
      if (graphicalObject instanceof Tiger) {
        ++tigerCount;
      }
    }

    // Every 30 frames, print info in console
    if (frameCount % 30 == 0) {
      System.out.println();
      System.out.println("---------------");
      System.out.println("Time: " + (frameCounter) / 60);
      System.out.println("Deer count: " + deerCount);
      System.out.println("Tiger count: " + tigerCount);
      System.out.println("Max time alive: " + maxTimeAlive / 60);
      System.out.println("Deers eaten: " + deersEaten);
      System.out.println("---------------");
      System.out.println();
    }
  }

  /**
   * Callback method called in an infinite loop. It draws the Jungle Park's window display
   */
  @Override
  public void draw() {
    // Set the color used for the background of the Processing window
    this.background(245, 255, 250); // Set the mint cream color background
    this.image(backgroundImage, this.width / 2, this.height / 2); // draw the background image
                                                                  // at
    // the center of the display window
    // traverse the tigers array and draw each stored tiger
    for (int i = 0; i < listGUI.size(); i++)
      listGUI.get(i).draw();
    ++frameCounter;
    this.printInfo();
    // ALTERNATIVE DEMONSTRATION: Add a set number of tigers and deer at the launch of application
    // or whenever all have been removed.
    // if (deerCount == 0) {
    // for (int i = 0; i < 75; ++i) {
    // listGUI.add(new Deer(this));
    // }
    // }
    // if (tigerCount == 0) {
    // for (int j = 0; j < 10; ++j) {
    // listGUI.add(new Tiger(this));
    // }
    // }
  }



  /**
   * Callback method called each time the user presses the mouse
   */
  @Override
  public void mousePressed() {
    // traverse listGUI and call mousePressed() of the first graphical object which the mouse is
    // over
    for (int i = 0; i < listGUI.size(); i++)
      if (listGUI.get(i).isMouseOver()) {
        listGUI.get(i).mousePressed();
        break;
      }
  }

  /**
   * Callback method called each time the mouse is released
   */
  @Override
  public void mouseReleased() {
    // traverse listGUI and call mouseReleased() method defined for every graphic object
    for (int i = 0; i < listGUI.size(); i++)
      listGUI.get(i).mouseReleased();
  }

  /**
   * Callback method called each time the user presses a key
   */
  @Override
  public void keyPressed() {
    switch (Character.toUpperCase(this.key)) {
      case 'T': // add new tiger to the Jungle Park
        listGUI.add(new Tiger(this));
        break;
      case 'D': // add new deer to the Jungle Park
        listGUI.add(new Deer(this));
        break;
      case 'R': // remove an animal from the Jungle Park if the mouse is over it
        // traverse the listGUI list and consider only animal objects to be removed if any
        for (int i = 0; i < listGUI.size(); i++) {
          if (listGUI.get(i) instanceof Animal && listGUI.get(i).isMouseOver()) {
            listGUI.remove(i);
            break; // remove the first animal which the mouse is over it while the r-key
                   // is pressed
          }
        }
    }
  }

  /**
   * Removes all the animals from the park
   */
  public void clear() {
    for (int i = 0; i < listGUI.size(); i++) {
      if (listGUI.get(i) instanceof Animal) {
        listGUI.remove(i);
        i--;
      }
    }
  }



  /**
   * This main method starts the application
   * 
   * @param args
   */
  public static void main(String[] args) {
    // starts the application (calls PApplet main() method with the name
    // of the PApplet class to run as parameter)
    PApplet.main("JunglePark");
  }
}

