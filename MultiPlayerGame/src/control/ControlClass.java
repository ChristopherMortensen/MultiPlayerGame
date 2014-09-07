package control;


import entity.Entity;
import entity.Figure;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Christopher
 */
public class ControlClass {
    //== Fields
    GameControl gameControl;
    
    
    
    //== Constructor
    public ControlClass(String networkType){
        gameControl = new GameControl(networkType);    
    }
    
    public ControlClass(String networkType, String serverIP){
        gameControl = new GameControl(networkType, serverIP);
    }
    
    //== Methods
    public void run(){
        gameControl.addPlayer1();
        gameControl.addPlayer2();
        
        gameControl.setupServerOrClient();
        
        ObstacleSpawner os = new ObstacleSpawner(gameControl);
        Thread t1 = new Thread(os);
        
        t1.start();
        gameControl.checkInput2();
        try {
            Display.setDisplayMode(new DisplayMode(1600, 900));
            Display.create();

        } catch (LWJGLException ex) {
            System.exit(0);
        }
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 1600, 0, 900, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        // As long as there is not pressed on the closebutton
        while (!Display.isCloseRequested()) {
            
            // Clears the frame
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            //---------------------- START -----------------------------------
            GL11.glBegin(GL11.GL_QUADS);
            //================================================================
            //================================================================
            
            //== UserInput Check
            gameControl.checkInput1();
            gameControl.checkInput2();
            //== Collision Check
            gameControl.checkCollision();
            //== UpdateObjects
            gameControl.moveObstacles(0.4);
            //== UpdatesPlayer
            gameControl.playerTick1();
            gameControl.playerTick2();
            //== Drawobjects
            gameControl.draw();
            //== Pause
//            gameControl.pause(5); //== For finetuning
            
            
            
            
            //================================================================
            //================================================================
            GL11.glEnd();
            //----------------------- END ------------------------------------
            Display.update();

        }
        gameControl.closeCrap();
        os.stop();
        Display.destroy();
    }
    
    
    
}
