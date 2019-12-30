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

public class JungleParkTests extends JunglePark {

    private static JunglePark park; // PApplet object that represents the display
                                    // window of this program

    /**
     * This method checks whether isClose() called by a Deer returns true if a tiger is within its
     * scanRange area and false if called with another tiger as input parameter located outside the
     * scanRange area
     * 
     * @return true when test verifies correct functionality, and false otherwise.
     */
    public static boolean test1isCloseMethod() {
        boolean passed = true;

        // This is an example. You can define your own test scenario for this method
        // Create a deer and two tigers
        Deer d = new Deer(park);
        Tiger t1 = new Tiger(park);
        Tiger t2 = new Tiger(park);
        // Set deer at position(200,200)
        d.setPositionX(200);
        d.setPositionY(200);
        // Set first tiger at position(400,200)
        t1.setPositionX(400); // tiger is 200px away from deer
        t1.setPositionY(200);
        // Set second tiger at position(300,200)
        t2.setPositionX(300); // tiger is 100px away from deer
        t2.setPositionY(200);
        if (d.isClose(t1, 175)) { // bug! isClose() should return false here
            System.out.println("Deer's isClose is returning true when it should return false.");
            passed = false;
        }
        if (!d.isClose(t2, 175)) { // bug! isClose() should return true here
            System.out.println("Deer's isClose is returning false when it should return true.");
            passed = false;
        }

        /////////////////////////////////////
        park.listGUI.clear(); // clear all the content of listGUI to get ready for a next scenario

        return passed;
    }

    /**
     * This method checks whether isClose() called by a Tiger returns false if another tiger is
     * located outside its scanRange area
     * 
     * @return true when test verifies correct functionality, and false otherwise.
     */
    public static boolean test2isCloseMethod() {
        boolean passed = true;
        // Create two tigers
        Tiger t1 = new Tiger(park);
        Tiger t2 = new Tiger(park);
        // Set t1 at position(50, 50)
        t1.setPositionX(150);
        t1.setPositionY(150);
        // Set t2 at position(275, 275)
        t2.setPositionX(300); // t2 is 150px away from t1
        t2.setPositionY(150);
        if (t1.isClose(t2, 100)) { // bug! isClose() should return false here
            System.out.println("isClose method is returning true when it should return false.");
            passed = false;
        }
        /////////////////////////////////////
        park.listGUI.clear(); // clear all the content of listGUI to get ready for a next scenario

        return passed;
    }

    /**
     * This method checks whether the deer detects a Tiger present at its proximity
     * 
     * @return true when test verifies correct functionality, and false otherwise.
     */
    public static boolean test1DeerScanForThreatMethod() {
        boolean passed = true;
        // Create Deer
        Deer deer = new Deer(park);
        // Create Tiger
        Tiger tiger = new Tiger(park);
        // Set deer position(100, 100)
        deer.setPositionX(100);
        deer.setPositionY(100);
        // Set tiger position(175, 100)
        tiger.setPositionX(175); // tiger is 75px away from deer
        tiger.setPositionY(100);
        if (!deer.scanForThreat(175)) { // bug! scanForThreat(175) should return false here
            System.out
                .println("scanForThreat method is returning false when it should return true.");
            passed = false;
        }
        /////////////////////////////////////
        park.listGUI.clear(); // clear all the content of listGUI to get ready for a next scenario

        return passed;
    }

    /**
     * This method checks whether your scanForThreat() method returns false if no Tiger is present
     * within a specific range distance from it
     * 
     * @return true when test verifies correct functionality, and false otherwise.
     */
    public static boolean test2DeerScanForThreatMethod() {
        boolean passed = true;
        // Create Deer
        Deer deer = new Deer(park);
        // Create Tiger
        Tiger tiger = new Tiger(park);
        // Set deer position(100, 150)
        deer.setPositionX(100);
        deer.setPositionY(150);
        // Set tiger position(300, 150)
        tiger.setPositionX(300); // tiger is 200px away from deer
        tiger.setPositionY(150);
        if (deer.scanForThreat(175)) { // bug! scanForThreat(175) should return false here
            System.out
                .println("scanForThreat method is returning true when it should return false.");
            passed = false;
        }
        /////////////////////////////////////
        park.listGUI.clear(); // clear all the content of listGUI to get ready for a next scenario

        return passed;
    }

    /**
     * This method checks whether the tiger hops on the deer provided to the hop() method as input
     * argument. (1) The tiger should take the position of the deer. (2) The unfortunate deer should
     * be removed from the JunglePark listGUI. (3) The eatenDeerCount should be incremented.
     * 
     * @return true when test verifies correct functionality, and false otherwise.
     */
    public static boolean testTigerHopMethod() {
        boolean passed = true;
        // This is an example. You may develop different scenarios to assess further the correctness
        // of
        // your hop() method
        // Create one deer and one tiger
        Deer d = new Deer(park);
        Tiger t = new Tiger(park);
        // Set the deer at position(250,250)
        d.setPositionX(250);
        d.setPositionY(250);
        // Set the tiger at position(300,300) tiger is 70.71px away from deer d1
        t.setPositionX(300);
        t.setPositionY(300);
        // add the tiger and the deer to the JunglePark (i.e. to listGUI)
        park.listGUI.add(d);
        park.listGUI.add(t);
        t.hop(d); // tiger hops on the deer
        if (t.getPositionX() != d.getPositionX() && t.getPositionY() != d.getPositionY()) {
            // tiger should move to the position of the deer
            System.out.println("Tiger did not move correctly when hopping.");
            passed = false;
        }
        if (park.listGUI.contains(d)) {
            // deer should be removed from the park
            System.out.println("Deer was not removed after being hopped on.");
            passed = false;
        }
        if (t.getDeerEatenCount() != 1) {
            // deerEatenCount should be incremented. It was 0
            System.out
                .println("deerEatenCount should be incremented after the tiger hopped on a deer.");
            passed = false;
        }

        /////////////////////////////////////
        park.listGUI.clear(); // clear all the content of listGUI to get ready for a next scenario

        return passed;
    }

    /**
     * runs JungleParkTests program as a PApplet client
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Call PApplet.main(String className) to start this program as a PApplet client application
        PApplet.main("JungleParkTests");
    }

    /**
     * This is a callback method automatically called only one time when the PApplet application
     * starts as a result of calling PApplet.main("PAppletClassName"); Defines the initial environment
     * properties of this class/program As setup() is run only one time when this program starts, all
     * your test methods should be called in this method
     */
    @Override
    public void setup() {
        super.setup(); // calls the setup() method defined
        park = this; // set the park to the current instance of Jungle

        // TODO Call your test methods here
        System.out.println("test1isCloseMethod(): " + test1isCloseMethod());
        System.out.println("testTigerHopMethod(): " + testTigerHopMethod());
        System.out.println("test2isCloseMethod(): " + test2isCloseMethod());
        System.out.println("test1DeerScanForThreatMethod(): " + test1DeerScanForThreatMethod());
        System.out.println("test2DeerScanForThreatMethod(): " + test2DeerScanForThreatMethod());

        // close PApplet display window (No need for the graphic mode for these tests)
        park.exit();

    }



}
