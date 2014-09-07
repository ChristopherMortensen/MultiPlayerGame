package control;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
*
* @author Christopher
*/
public class ObstacleSpawner implements Runnable{

//== Fields
GameControl gameControl;
boolean running;
    
    //== Constructor
    public ObstacleSpawner(GameControl gameControl){
        this.gameControl = gameControl;
        running = true;
    }
    
    @Override
    public void run() {
        while(running){
            try {
                gameControl.setObstacleAwaiting(true);
//                gameControl.addObstacle();
                Thread.sleep(1700); // 1700
            } catch (InterruptedException ex) {
                Logger.getLogger(ObstacleSpawner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stop(){
        this.running = false;
    }

}
