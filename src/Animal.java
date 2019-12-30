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

import java.util.Random;

/**
 * This class represents an animal in the Jungle Park application. It implements the interface
 * ParkGUI
 * 
 * @author Mouna Kacem and Matt Karrmann
 */
public class Animal implements ParkGUI {

  private static Random randGen = new Random(); // Generator of random numbers
  protected String label; // represents the animal's identifier
  // Fields defined to draw the animal in the application display window
  protected JunglePark processing; // PApplet object that represents the display window
  protected PImage image; // animal's image

  private float[] position; // animal's position in the display window
                            // Usage: position[0: x-coordinate, or 1: y-coordinate]
  private boolean isDragging; // indicates whether the animal is being dragged or not


  /**
   * Creates a new Animal object positioned at a given position of the display window
   * 
   * @param processing    PApplet object that represents the display window
   * @param positionX     x-coordinate of the animal's image in the display window
   * @param positionY     y-coordinate of the animal's image in the display window
   * @param imageFileName filename of the animal image
   */
  public Animal(JunglePark processing, float positionX, float positionY, String imageFileName) {

    // Set Animal drawing parameters
    this.processing = processing; // set the PApplet Object where the animal will be drawn
    this.position = new float[] {positionX, positionY}; // sets the position of the animal
                                                        // object
    this.image = processing.loadImage(imageFileName);
    isDragging = false; // initially the animal is not dragging
  }

  /**
   * Creates a new Animal object positioned at a random position of the display window
   * 
   * @param processing    PApplet object that represents the display window
   * @param imageFileName filename of the animal image
   */
  public Animal(JunglePark processing, String imageFileName) {
    this(processing, (float) randGen.nextInt(processing.width),
        Math.max((float) randGen.nextInt(processing.height), 100), imageFileName);
  }

  /**
   * Draws the animal to the display window. It sets also its position to the mouse position if the
   * tiger is being dragged (i.e. if its isDragging field is set to true).
   */
  @Override
  public void draw() {
    // if the tiger is dragging, set its position to the mouse position with respect to the display
    // window (processing) dimension
    if (this.isDragging) {
      if (this.processing.mouseX < 0) {// mouse moves to left of screen
        this.position[0] = 0; // set x position to far left position
      } // mouse moves to right of the screen
      else if (this.processing.mouseX > this.processing.width) {
        this.position[0] = this.processing.width; // set x position to far right position
      } else {
        this.position[0] = this.processing.mouseX; // set x position to x position of mouse
      }

      if (this.processing.mouseY < 0) {// mouse moves beneath the screen
        this.position[1] = 0; // set y position to bottom position
      } else if (this.processing.mouseY > this.processing.height) {// mouse moves above the screen
        this.position[1] = this.processing.height; // set position to top position
      } else {
        this.position[1] = this.processing.mouseY; // set y position to y position of mouse
      }
    }

    // draw the animals at its current position
    this.processing.image(this.image, this.position[0], position[1]);
    // display label
    displayLabel();
    this.action();
  }


  /**
   * display's the animal label on the application window screen
   */
  private void displayLabel() {
    this.processing.fill(0); // specify font color: black
    // display label text
    this.processing.text(label, this.position[0], this.position[1] + this.image.height / 2 + 4);
  }

  /**
   * Checks if the mouse is over the given tiger object
   * 
   * @param tiger reference to a given Tiger object
   * @return true if the mouse is over the given tiger object, false otherwise
   */
  @Override
  public boolean isMouseOver() {
    int animalWidth = image.width; // image width
    int animalHeight = image.height; // image height

    // checks if the mouse is over the animal
    if (processing.mouseX > position[0] - animalWidth / 2
        && processing.mouseX < position[0] + animalWidth / 2
        && processing.mouseY > position[1] - animalHeight / 2
        && processing.mouseY < position[1] + animalHeight / 2) {
      return true;
    }
    return false;
  }

  /**
   * Sets object to dragging if mouse is pressed while it is over it
   */
  @Override
  public void mousePressed() {
    if (isMouseOver())
      isDragging = true;
  }

  /**
   * Turns off dragging of object when dragging when mouse is released
   */
  @Override
  public void mouseReleased() {
    isDragging = false;
  }

  /**
   * Getter for label
   * 
   * @return the label that represents the animal's identifier
   */
  public String getLabel() {
    return label;
  }


  /**
   * Getter for image
   * 
   * @return the image of type PImage of the animal object
   */
  public PImage getImage() {
    return image;
  }


  /**
   * Getter for x position
   * 
   * @return the X coordinate of the animal position
   */
  public float getPositionX() {
    return position[0];
  }

  /**
   * Getter for y position
   * 
   * @return the Y coordinate of the animal position
   */
  public float getPositionY() {
    return position[1];
  }


  /**
   * Setter for x position
   * 
   * @param position the XPosition to set
   */
  public void setPositionX(float position) {
    this.position[0] = position;
  }

  /**
   * Setter for y position
   * 
   * @param position the YPosition to set
   */
  public void setPositionY(float position) {
    this.position[1] = position;
  }

  /**
   * Change position by given amount in each direction
   * 
   * @param deltaX change in x position
   * @param deltaY change in y position
   */
  public void changePosition(double deltaX, double deltaY) {
    this.position[0] += deltaX;
    this.position[1] += deltaY;
  }

  /**
   * Determines if animal is being dragged
   * 
   * @return true if the animal is being dragged, false otherwise
   */
  public boolean isDragging() {
    return isDragging;
  }

  /**
   * Computes the distance squared between the current animal and another one. See article for
   * explanation:
   * 
   * https://blog.demofox.org/2017/10/01/calculating-the-distance-between-points-in-wrap-around-toroidal-space/
   * 
   * @param otherAnimal other animal
   * @return distance between the current animal and otherAnimal
   */
  public double distanceSquared(Animal otherAnimal) {
    // Computes standard x distance
    double xDistance = Math.abs(this.getPositionX() - otherAnimal.getPositionX());
    // Adjust distance if necessary:
    if (xDistance > processing.WIDTH / 2) {
      xDistance = processing.WIDTH - xDistance;
    }
    // Computes standard y distance
    double yDistance = Math.abs(this.getPositionY() - otherAnimal.getPositionY());
    // Adjust distance if necessary
    if (yDistance > processing.HEIGHT / 2) {
      yDistance = processing.HEIGHT - yDistance;
    }
    // Returns squared distance:
    return Math.pow(xDistance, 2) + Math.pow(yDistance, 2);
  }

  /**
   * Computes the signed distance in the x direction. Used to compute the partial derivative with
   * respect to x of the objective function. See article for explanation:
   * 
   * https://blog.demofox.org/2017/10/01/calculating-the-distance-between-points-in-wrap-around-toroidal-space/
   * 
   * @param otherAnimal other animal computing distance with respect to
   * @param exp         exponent distance function is taken to in objective function
   * @return signed distance in x direction
   */
  public double xComponent(Animal otherAnimal, double exp) {
    // Computes standard signed x distance
    double xDistanceSigned = this.getPositionX() - otherAnimal.getPositionX();
    if (Math.abs(xDistanceSigned) > processing.WIDTH / 2) { // If distance must be adjusted
      // Adjust appropriately:
      if (xDistanceSigned < 0) {
        xDistanceSigned += processing.WIDTH;
      } else {
        xDistanceSigned -= processing.WIDTH;
      }
    }
    return exp * Math.pow(this.distanceSquared(otherAnimal), exp / 2 - 1) * xDistanceSigned;
  }

  /**
   * Computes the signed distance in the y direction. Used to compute the partial derivative with
   * respect to y of the objective function. See article for explanation:
   * 
   * https://blog.demofox.org/2017/10/01/calculating-the-distance-between-points-in-wrap-around-toroidal-space/
   * 
   * @param otherAnimal other animal computing distance with respect to
   * @param exp         exponent distance function is taken to in objective function
   * @return signed distance in y direction
   */
  public double yComponent(Animal otherAnimal, double exp) {
    // Computes standard signed y distance
    double yDistanceSigned = this.getPositionY() - otherAnimal.getPositionY();
    if (Math.abs(yDistanceSigned) > processing.HEIGHT / 2) { // If distance must be adjusted
      // Adjust appropriately:
      if (yDistanceSigned < 0) {
        yDistanceSigned += processing.HEIGHT;
      } else {
        yDistanceSigned -= processing.HEIGHT;
      }
    }
    return exp * Math.pow(this.distanceSquared(otherAnimal), exp / 2 - 1) * yDistanceSigned;
  }

  /**
   * Defines the behavior of the current animal in the jungle park
   */
  public void action() {
    if (getPositionX() < 0) {
      setPositionX(processing.WIDTH + getPositionX());
    } else {
      setPositionX(getPositionX() % processing.WIDTH);
    }
    if (getPositionY() < 0) {
      setPositionY(processing.HEIGHT + getPositionY());
    } else {
      setPositionY(getPositionY() % processing.HEIGHT);
    }
  }

  /**
   * Determines if other animal is within given range
   * 
   * @param otherAnimal other animal to see if it is close
   * @param range       range within which animals are considered close
   * @return TRUE if otherAnimal is located within range distance around the current animal and
   *         FALSE otherwise.
   */
  public boolean isClose(Animal otherAnimal, int range) {
    if (Math.sqrt(this.distanceSquared(otherAnimal)) <= range) {
      return true;
    } else {
      return false;
    }
  }
}
