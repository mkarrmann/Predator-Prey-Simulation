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

/**
 * Defines the properties of and represents a Deer in the JunglePark application
 * 
 * @author Matthew Karrmann
 *
 */

public class Deer extends Animal {
  private static final int SCAN_RANGE = 175; // scan range area to check for a threat in the
                                             // neighborhood
  private static final String IMAGE_FILE_NAME = "images/deer.png";
  private static int nextID = 1; // class variable that represents the identifier of the next deer
                                 // to be created

  private static final String TYPE = "DR"; // A String that represents the deer type
  private final int id; // Deer's id:positive number that represents the order of the deer
  private static final double SPEED = 2; // number of pixels deer moves per frame
  // arbitrary exponent distance to tiger is taken to in objective function:
  private static final double EXPONENT = -2.0;
  public Integer timeAlive = 0; // how long given deer has been alive for

  /**
   * Constructor that creates a new Deer object positioned at a random position of the display
   * window
   * 
   * @param processing
   */
  public Deer(JunglePark processing) {
    // Set Tiger drawing parameters
    super(processing, IMAGE_FILE_NAME);

    // Set Deer identification fields
    id = nextID;
    this.label = TYPE + id; // String that identifies the current deer
    nextID++;
  }

  /**
   * Checks if there is a threat (a Tiger for instance) at the neighborhood
   * 
   * @param scanRange an integer that represents the range of the area to be scanned around the
   *                  animal
   * @return true if threat is found within range, false otherwise
   */
  public boolean scanForThreat(int scanRange) {
    boolean threatFound = false;
    // Scan through each element of instance of listGUI
    for (ParkGUI graphicalObject : processing.listGUI) {
      // Threat is found if object is a Tiger and isClose returns true
      if (graphicalObject instanceof Tiger && isClose((Animal) graphicalObject, SCAN_RANGE)) {
        threatFound = true;
        break;
      }
    }
    return threatFound;
  }


  /**
   * Computes partial derivative with respect to x of objective function. The objective function is
   * defined as $\sum d^a(this, tiger)$, where the sum is taken over the set of tigers and a is the
   * arbitrary exponent, and thus the partial derivative with respect to x is equal to summing
   * this.xComponent(tiger, a) over the set of tiger.
   * 
   * @return x partial derivative of objective function
   */
  public double xDerivative() {
    // Sum over the xComponent corresponding to each Tiger:
    double sum = 0;
    for (ParkGUI graphicalObject : processing.listGUI) {
      if (graphicalObject instanceof Tiger) {
        sum += this.xComponent((Tiger) graphicalObject, EXPONENT);
      }
    }
    return sum;
  }

  /**
   * Computes partial derivative with respect to y of objective function. The objective function is
   * defined as $\sum d^a(this, tiger)$, where the sum is taken over the set of tigers and a is the
   * arbitrary exponent, and thus the partial derivative with respect to y is equal to summing
   * this.yComponent(tiger, a) over the set of tiger.
   * 
   * @return y partial derivative of objective function
   */
  public double yDerivative() {
    // Sum over the yComponent corresponding to each Tiger:
    double sum = 0;
    for (ParkGUI graphicalObject : processing.listGUI) {
      if (graphicalObject instanceof Tiger) {
        sum += this.yComponent((Tiger) graphicalObject, EXPONENT);
      }
    }
    return sum;
  }

  /**
   * Defines the behavior of a Deer object in the Jungle park
   */
  @Override
  public void action() {
    // Compute and save partial derivatives:
    double xDerivative = this.xDerivative();
    double yDerivative = this.yDerivative();
    // Norm of gradient:
    double gradientNorm = Math.sqrt(Math.pow(xDerivative, 2) + Math.pow(yDerivative, 2));
    // If tigers are present, move deer in direction of negative gradient (gradient descent) and
    // magnitude SPEED and increment time alive
    if (gradientNorm > 0) {
      this.changePosition(-(SPEED * xDerivative / gradientNorm),
          -(SPEED * yDerivative / gradientNorm));
      ++timeAlive; // Increment time alive each frame
    }

    super.action(); // Returns deer to other side of screen if they leave it
    // Display time alive above deer:
    this.processing.text(timeAlive / 60, this.getPositionX(),
        this.getPositionY() - this.image.height / 2 - 4);
  }
}
