package control;

import entity.Entity;
import entity.Point;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Christopher
 */
public class UserInput {
    //== Fields
    
    //== Constructor
    public UserInput(){
        
    }
    
    //== Methods
    public void checkInput(Entity player)
    {
        if (Mouse.isButtonDown(0))
        {
            Point mouse = new Point(Mouse.getX(), Mouse.getY());
            System.out.println("MOUSE DOWN @ X: " + mouse.getX() + " Y: " + mouse.getY());
            
        }
    }

}
