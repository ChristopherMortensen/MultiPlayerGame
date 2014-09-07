package control;

import entity.Entity;
import entity.Figure;
import entity.Point;
import entity.Shape;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Christopher
 */
public class GraphicsControl {
    
    //== Fields
    
    
    //== Constructor
    public GraphicsControl(){
        
    }
    
    //== Methods
    
    //== Draw Method to draw all the figures in the Figure-Array Field
    public void draw(ArrayList<Entity> entities){
        for(Entity entity : entities){
            for(Shape shape : entity.getFigure().getShapes()){
                GL11.glColor3f(shape.getColor()[0], shape.getColor()[1], shape.getColor()[2]); // set colour statement
                for (Point point : shape.getCoordinateList()) {
                    GL11.glVertex2i((int)point.getX(), (int)point.getY()); // draw statement
                }
            }
        }
    }
    
    
    
}
