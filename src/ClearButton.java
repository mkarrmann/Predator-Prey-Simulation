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
 * Extends the Button class to the define a Clear Park button which removes all animals
 * 
 * @author Matthew Karrmann
 *
 */
public class ClearButton extends Button {
    /**
     * Constructor of button given coordinates and instance of JunglePark
     * 
     * @param x          float, x-coordinate of button to be constructed
     * @param y          float, y-coordinate of button to be constructed
     * @param processing JunglePark, instance of JunglePark button to be added to
     */
    public ClearButton(float x, float y, JunglePark park) {
        super(x, y, park);
        this.label = "Clear Park";
    }

    /**
     * Overrides mousePressed() method from ParkGUI interface. Calls clear()
     * method from JunglePark class, removing all Animals.
    */
    @Override
    public void mousePressed() {
        if (isMouseOver()) {
            processing.clear();
        }
    }
}
