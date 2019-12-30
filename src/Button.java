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

// This a super class for any Button that can be added to a PApplet application
// This class implements the ParkGUI interface
// TODO You MUST comment well this code
// TODO ADD File header, Javadoc class header, Javadoc method header to every method, and in-line
// commenting

/**
 * Super class for buttons that can be added to a PApplet applet application. Implements the ParkGUI
 * interface.
 * 
 * @author Mouna Kacem and Matthew Karrmann
 *
 */
public class Button implements ParkGUI {
    private static final int WIDTH = 85; // Width of the Button
    private static final int HEIGHT = 32; // Height of the Button
    protected JunglePark processing; // PApplet object where the button will be displayed
    private float[] position; // array storing x and y positions of the Button with respect to
                              // the display window
    protected String label; // text/label that represents the button

    /**
     * Constructor of button given coordinates and instance of JunglePark
     * @param x float, x-coordinate of button to be constructed
     * @param y float, y-coordinate of button to be constructed
     * @param processing JunglePark, instance of JunglePark button to be added to
     */
    public Button(float x, float y, JunglePark processing) {
        this.processing = processing;
        this.position = new float[2]; // Defines array to save button position
        this.position[0] = x; // Saves x-coordinate in first element of array
        this.position[1] = y; // Saves y-coordinate in second element of array
        this.label = "Button"; // Defines generic button label
    }

    /**
     * Overrides draw() method from ParkGUI interface. Defines appearance of button
     * under various conditions.
     */
    @Override
    public void draw() {
        this.processing.stroke(0);// set line value to black
        if (isMouseOver())
            processing.fill(100); // set the fill color to dark gray if the mouse is over the button
        else
            processing.fill(200); // set the fill color to light gray otherwise

        // draw the button (rectangle with a centered text)
        processing.rect(position[0] - WIDTH / 2.0f, position[1] - HEIGHT / 2.0f,
            position[0] + WIDTH / 2.0f, position[1] + HEIGHT / 2.0f);
        processing.fill(0); // set the fill color to black
        processing.text(label, position[0], position[1]); // display the text of the current button
    }

    /**
     * Overrides mousePressed() method from ParkGUI interface. Prints message that
     * button was pressed if mouse is pressed while the mouse is over the button.
    */
    @Override
    public void mousePressed() {
        if (isMouseOver())
            System.out.println("A button was pressed.");
    }

    /**
     * Overrides mouseReleased() method from ParkGUI interface. Does nothing.
     */
    @Override
    public void mouseReleased() {}

    /**
     * Overrides isMouseOver() method from ParkGUI interface. Determines whether
     * mouse is over button given the size of the button.
     */
    @Override
    public boolean isMouseOver() {
        /*
         * Note that position of button is determined by its center, and recall that x and y
         * coordinates are stored in position[0] and position[1], respectively.
         */
        if (this.processing.mouseX > this.position[0] - WIDTH / 2
            && this.processing.mouseX < this.position[0] + WIDTH / 2
            && this.processing.mouseY > this.position[1] - HEIGHT / 2
            && this.processing.mouseY < this.position[1] + HEIGHT / 2)
            return true;
        return false;
    }
}
