package control;

import network.ClientCalibrater_Runner;
import network.ServerCalibrater_Runner;
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
    public ControlClass(String networkType) {
        gameControl = new GameControl(networkType);
    }

    public ControlClass(String networkType, String serverIP) {
        gameControl = new GameControl(networkType, serverIP);
    }

    //== Methods
    public void run() {
        if(gameControl.getNetworkType().equalsIgnoreCase("server"))
        gameControl.addPlayer1();
//        gameControl.addPlayer2();

//        gameControl.setupServerOrClient();
        gameControl.startObstacleSpawnerRunner();
        gameControl.startConnectorRunner();

        try {
            Display.setDisplayMode(new DisplayMode(1600, 900));
            Display.setInitialBackground(0.2f, 0.4f, 0.5f);
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
            if(gameControl.getNetworkType().equalsIgnoreCase("server"))
            gameControl.checkInput1();
//            gameControl.checkInput2();
            //== Collision Check
            gameControl.checkCollision();
            //== UpdateObjects
            if (gameControl.getNetworkType().equalsIgnoreCase("server")) {
                gameControl.moveObstacles(0.4);
                //== UpdatesPlayer
                gameControl.playerTick1();
                if (gameControl.isTwoPlayer()) {
                    gameControl.playerTick2();
                }
            }
            gameControl.checkConnection();
            //== Only for the client
            if(gameControl.getNetworkType().equalsIgnoreCase("client"))
                gameControl.updateEntities();
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
        Display.destroy();
        System.exit(0);
    }

}
