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
 * Defines the properties of and represents a Tiger in the JunglePark application
 * 
 * @author Matthew Karrmann
 *
 */
public class Tiger extends Animal {
  private static final int SCAN_RANGE = 10; // range dimension for scanning the neighborhood for
                                            // food
  private static final String IMAGE_FILE_NAME = "images/tiger.png";
  private static int nextID = 1; // class variable that represents the identifier of the next
                                 // tiger
                                 // to be created
  // Tiger's identification fields
  private static final String TYPE = "TGR"; // A String that represents the tiger type
  private final int id; // Tiger's id: positive number that represents the order of the tiger
  private int deerEatenCount; // Number of Deers that the current tiger has eaten so far
  private static final double SPEED = 1.5; // number of pixels tiger moves per frame
  // arbitrary exponent distance to deer is taken to in objective function
  private static final double EXPONENT = -2.0;

  /**
   * Creates a new Tiger object positioned at a random position of the display window
   * 
   * @param processing PApplet object that represents the display window
   */
  public Tiger(JunglePark processing) {
    // Set Tiger drawing parameters
    super(processing, IMAGE_FILE_NAME);

    // Set Tiger identification fields
    id = nextID;
    this.label = TYPE + id; // String that identifies the current tiger
    nextID++;
    deerEatenCount = 0;
  }

  /**
   * Returns deerEatenCount, which keeps track of the number of deers eaten by given Tiger.
   * 
   * @return
   */
  public int getDeerEatenCount() {
    return deerEatenCount;
  }

  /**
   * Checks if item is food (Deer) within range of Tiger
   * 
   * @param scanRange       range within which Tiger will consider item food
   * @param graphicalObject item to be checked whether or not is food
   * @return
   */
  public boolean checkForFood(int scanRange, ParkGUI graphicalObject) {
    boolean isFood = false;
    // Item is considered food iff it is a Deer within its scan range
    if (graphicalObject instanceof Deer && isClose((Deer) graphicalObject, SCAN_RANGE)) {
      isFood = true;
    }
    return isFood;
  }

  /**
   * Computes partial derivative with respect to x of objective function. The objective function is
   * defined as $\sum d^a(this, deer)$, where the sum is taken over the set of deer and a is the
   * arbitrary exponent, and thus the partial derivative with respect to x is equal to summing
   * this.xComponent(deer, a) over the set of deer.
   * 
   * @return x partial derivative of objective function
   */
  public double xDerivative() {
    // Sum over the xComponent corresponding to each Deer:
    double sum = 0;
    for (ParkGUI graphicalObject : processing.listGUI) {
      if (graphicalObject instanceof Deer) {
        sum += this.xComponent((Deer) graphicalObject, EXPONENT);
      }
    }
    return sum;
  }

  /**
   * Computes partial derivative with respect to y of objective function. The objective function is
   * defined as $\sum d^a(this, deer)$, where the sum is taken over the set of deer and a is the
   * arbitrary exponent, and thus the partial derivative with respect to y is equal to summing
   * this.yComponent(deer, a) over the set of deer.
   * 
   * @return y partial derivative of objective function
   */
  public double yDerivative() {
    // Sum over the yComponent corresponding to each Deer:
    double sum = 0;
    for (ParkGUI graphicalObject : processing.listGUI) {
      if (graphicalObject instanceof Deer) {
        sum += this.yComponent((Deer) graphicalObject, EXPONENT);
      }
    }
    return sum;
  }

  /**
   * Tiger's behavior in the Jungle Park Scans for food at the neighborhood of the current tiger. If
   * the Tiger founds any deer at its proximity, it hops on it, and eats it. Additionally, moves
   * Tiger to chase Deer according to objective function.
   */
  @Override
  public void action() {
    super.action();
    /*
     * Beginning from lowest index of listGUI, checks whether item is food. If food is found, Tiger
     * hops to food. Loop does not break, and the Tiger continues to search through items for food.
     */
    for (int i = 0; i < processing.listGUI.size(); ++i) {
      if (checkForFood(SCAN_RANGE, processing.listGUI.get(i))) {
        hop((Deer) processing.listGUI.get(i));
      }
    }
    // Compute and save partial derivatives:
    double xDerivative = this.xDerivative();
    double yDerivative = this.yDerivative();
    // Norm of gradient:
    double gradientNorm = Math.sqrt(Math.pow(xDerivative, 2) + Math.pow(yDerivative, 2));
    // If deer are present, move tiger in direction of gradient (gradient ascent) and magnitude
    // SPEED:
    if (gradientNorm > 0) {
      this.changePosition((SPEED * xDerivative / gradientNorm),
          (SPEED * yDerivative / gradientNorm));
    }
    super.action(); // Returns Tiger to other side of screen if they leave it
    // Displays number of deers eaten if Tiger has eaten a Tiger
    if (deerEatenCount > 0) {
      displayDeerEatenCount(); // display deerEatenCount
    }
  }

  /**
   * Moves tiger to location of given deer, and removes deer from scene.
   * 
   * @param food Deer Tiger is to eat
   */
  public void hop(Deer food) {
    // Sets Tiger location to location of food:
    this.setPositionX(food.getPositionX());
    this.setPositionY(food.getPositionY());
    processing.listGUI.remove(food); // Removes food from scene
    ++this.deerEatenCount; // Increments number of deer eaten by Tiger
    ++processing.deersEaten; // Increments total number of deer eaten
  }

  /**
   * Displays the number of eaten deers if any on the top of the tiger image
   */
  public void displayDeerEatenCount() {
    this.processing.fill(0); // specify font color: black
    // display deerEatenCount on the top of the Tiger's image
    this.processing.text(deerEatenCount, this.getPositionX(),
        this.getPositionY() - this.image.height / 2 - 4);
  }
}
