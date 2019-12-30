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
 * Extends the Button class to define buttons to add animals. Specifically, "add tiger" and "add
 * deer" buttons are defined.
 * 
 * @author Matthew Karrmann
 *
 */
public class AddAnimalButton extends Button {

  private String type; // type of the animal to add

  /**
   * Constructor of button given coordinates and instance of JunglePark
   * 
   * @param x          float, x-coordinate of button to be constructed
   * @param y          float, y-coordinate of button to be constructed
   * @param processing JunglePark, instance of JunglePark button to be added to
   */
  public AddAnimalButton(String type, float x, float y, JunglePark park) {
    super(x, y, park);
    this.type = type.toLowerCase();
    this.label = "Add " + type;
  }

  /**
   * Adds corresponding animal each time button is pressed.
   */
  @Override
  public void mousePressed() {
    // Button is clicked if mouse is pressed while mouse is over the button
    if (isMouseOver()) {
      // Add corresponding animal to PApplet
      switch (type) {
        case "tiger":
          processing.listGUI.add(new Tiger(processing));
          break;
        case "deer":
          processing.listGUI.add(new Deer(processing));
          break;
      }
    }
  }
}
