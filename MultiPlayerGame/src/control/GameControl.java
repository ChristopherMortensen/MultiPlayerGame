package control;

import entity.Entity;
import entity.Shape;
import network.Server_Client;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Christopher
 */
public class GameControl {

    //== Fields
    private GraphicsControl graphicsControl;
    private Server_Client server_client;
    private ArrayList<Entity> entities;
    private boolean obstacleAwaiting;
    private ArrayList<Integer> entityCenters;
    

    //== Constructor
    public GameControl(String networkType) {
        graphicsControl = new GraphicsControl();
        server_client = new Server_Client(networkType);
        entities = new ArrayList<>();
        obstacleAwaiting = false;
        entityCenters = new ArrayList<>();
    }

    public GameControl(String networkType, String serverIP) {
        graphicsControl = new GraphicsControl();
        server_client = new Server_Client(networkType, serverIP);
        entities = new ArrayList<>();
        obstacleAwaiting = false;
    }

    //== Methods
    public void setObstacleAwaiting(boolean awaiting) {
        this.obstacleAwaiting = awaiting;
    }

    public void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //== Method to move obstacles one to more pixels to the left
    public void moveObstacles(double pixels) {

        for (Entity obstacle : entities) {
            if (obstacle.getFigure().getType().equalsIgnoreCase("obstacle")) {
                obstacle.updateObstacle(pixels);
            }
        }
        if (obstacleAwaiting && server_client.getNetworkType().equalsIgnoreCase("server")) {
            server_client.transmitObstacleSpawn();
            this.addObstacle();
            this.obstacleAwaiting = false;
        }
    }

    public void playerTick1() {
        entities.get(0).tick();
        entities.get(0).getFigure().updatePlayer();

    }

    public void playerTick2() {
        entities.get(1).tick();
        entities.get(1).getFigure().updatePlayer();
    }

    //== Methods
    //== Own input
    public void checkInput1() {
        if (Mouse.isButtonDown(0) || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
//            Point mouse = new Point(Mouse.getX(), Mouse.getY());
//            System.out.println("MOUSE DOWN @ X: " + mouse.getX() + " Y: " + mouse.getY());

//                    System.out.println("jep");
            entities.get(0).setJumping(true);
            entities.get(0).setFalling(false);
            entities.get(0).setVelocityY(0.8f); //0.8

            //send a datatransmission to Client
            server_client.transmitJump();
        }
    }

    //== Eventually on a separate thread. "Enemy's input"
    public void checkInput2() {
        String inputSignal = "";
        inputSignal = server_client.checkInputSignal();
        if (!inputSignal.isEmpty()) { 
            switch (inputSignal) {
                case "space":
                    entities.get(1).setJumping(true);
                    entities.get(1).setFalling(false);
                    entities.get(1).setVelocityY(0.8f);
                    break;
                case "o":
                    this.addObstacle();
                    this.obstacleAwaiting = false;
                    break;
            }
        }
    }

    //== Pseudocode for checking for collision.
    /* When the player is moving, whenever an obstacle is touching the vertical line at 650x, 
     then all figures should check their shapes, if the players shape is touching the other. 
     if it is, the player loses, if not, then the player can proceed playing. 
     */
    public void checkCollision() {
        for (int i = 2; i < entities.size(); i++) {
            for (Shape shape : entities.get(i).getFigure().getShapes()) {
                if (shape.pointTouches(entities.get(0).getFigure().getCenter())
                        || shape.pointTouches(entities.get(1).getFigure().getCenter())) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameControl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    for (int j = 2; j < entities.size(); j++) {
                        entities.remove(j);
                    }

                }
            }
        }
    }

    public void draw() {
        graphicsControl.draw(entities);
    }

    public void addObstacle() {
        if (entities.size() == 5) // 4
        {
            entities.remove(2);
        }
        entities.add(new Entity("obstacle"));
    }

    public void addPlayer1() {
        entities.add(new Entity("player"));
    }

    public void addPlayer2() {
        entities.add(new Entity("player"));
    }

    public void setupServerOrClient() {
        server_client.startRunnning();

    }

    public void closeCrap() {
        server_client.closeCrap();
    }
    
    public void calibrateScreens(){
        
    }
}
