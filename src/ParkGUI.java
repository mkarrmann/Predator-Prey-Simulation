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
 * Defines common methods of ParkGUI objects (animals or buttons).
 * @author Matthew Karrmann
 *
 */
public interface ParkGUI {
    public void draw(); // draws a ParkGUI object (either an animal or a button) to the display
                        // window

    public void mousePressed(); // called each time the mouse is Pressed

    public void mouseReleased(); // called each time the mouse is Pressed

    public boolean isMouseOver(); // checks whether the mouse is over a ParkGUI object

}
