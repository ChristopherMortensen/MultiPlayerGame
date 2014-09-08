package control;

import entity.Entity;
import entity.Point;
import entity.Shape;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.Client;
import network.ClientCalibrater_Runner;
import network.Connector_Runner;
import network.Server;
import network.ServerCalibrater_Runner;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Christopher
 */
public class GameControl {

    //== Fields
    private GraphicsControl graphicsControl;
//    private Server_Client server_client;
    private ArrayList<Entity> entities;
    private boolean obstacleAwaiting;
    private ArrayList<double[]> entityCenters;

    //== flags to show whether an output or input is awaiting
    private boolean outputAwaiting;
    private boolean inputAwaiting;
    private String output;
    private String networkType;

    private Client client;
    private Server server;
    private boolean serverConnectedToServer;
    private boolean twoPlayer = false;

    private ObstacleSpawner_Runner os = null;
    private ServerCalibrater_Runner sc = null;
    private ClientCalibrater_Runner cc = null;
    private Connector_Runner cr = null;
    private Thread t1 = null;
    private Thread t2 = null;
    private Thread t3 = null;
    private Thread t4 = null;

    //== Constructor
    public GameControl(String networkType) {
        graphicsControl = new GraphicsControl();
//        server_client = new Server_Client(networkType);
        this.networkType = networkType;
        entities = new ArrayList<>();
        obstacleAwaiting = false;
        entityCenters = new ArrayList<>();

        outputAwaiting = false;
        inputAwaiting = false;
        output = "";

        server = new Server();
        serverConnectedToServer = false;
    }

    public GameControl(String networkType, String serverIP) {
        graphicsControl = new GraphicsControl();
//        server_client = new Server_Client(networkType, serverIP);
        this.networkType = networkType;
        entities = new ArrayList<>();
        obstacleAwaiting = false;

        outputAwaiting = false;
        inputAwaiting = false;
        output = "";

        client = new Client(serverIP);
        serverConnectedToServer = false;
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

        for (Entity obstacle : this.getEntities()) {
            if (obstacle.getFigure().getType().equalsIgnoreCase("obstacle")) {
                obstacle.updateObstacle(pixels);
            }
        }
        if (obstacleAwaiting/* && server_client.getNetworkType().equalsIgnoreCase("server")*/) {
//            server_client.transmitObstacleSpawn();
            this.addObstacle();
            this.obstacleAwaiting = false;
        }
    }

    public void playerTick1() {
        this.getEntities().get(0).tick();
        this.getEntities().get(0).getFigure().updatePlayer();

    }

    public void playerTick2() {
        this.getEntities().get(1).tick();
        this.getEntities().get(1).getFigure().updatePlayer();
    }

    //== Methods
    //== Own input
    public void checkInput1() {
        if (Mouse.isButtonDown(0) || Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
//            Point mouse = new Point(Mouse.getX(), Mouse.getY());
//            System.out.println("MOUSE DOWN @ X: " + mouse.getX() + " Y: " + mouse.getY());

//                    System.out.println("jep");
            this.getEntities().get(0).setJumping(true);
            this.getEntities().get(0).setFalling(false);
            this.getEntities().get(0).setVelocityY(0.8f); //0.8

            //send a datatransmission to Client
            this.outputAwaiting = true;
//            server_client.transmitJump();
        }
    }

    //== Eventually on a separate thread. "Enemy's input"
    public void checkInput2(String input) {
        String inputSignal = input;
//        String inputSignal = "";
//        inputSignal = server_client.checkInputSignal();
        if (!inputSignal.isEmpty()) {
            switch (inputSignal) {
                case "space":
                    this.getEntities().get(1).setJumping(true);
                    this.getEntities().get(1).setFalling(false);
                    this.getEntities().get(1).setVelocityY(0.8f);
                    break;
                case "o":
                    this.addObstacle();
                    this.obstacleAwaiting = false;
                    break;
                default:
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
        int extraPlayer = 0;
        if (this.serverConnectedToServer) {
            extraPlayer++;
        }
        for (int i = 1 + extraPlayer; i < this.getEntities().size(); i++) {
            for (Shape shape : this.getEntities().get(i).getFigure().getShapes()) {
                if (shape.pointTouches(this.getEntities().get(0).getFigure().getCenter())
                        || shape.pointTouches(this.getEntities().get(1).getFigure().getCenter())) { //<--- Notice this runs even on 1p.

                    for (int j = 1 + extraPlayer; j < this.getEntities().size() - 1; j++) {
                        this.getEntities().remove(j);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GameControl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        }
    }

    public void draw() {
        graphicsControl.draw(this.getEntities());
    }

    public void addObstacle() {
        if (this.getEntities().size() == 5) // 4
        {
            this.getEntities().remove(2);
        }
        this.getEntities().add(new Entity("obstacle"));
    }

    public void addPlayer1() {
        this.getEntities().add(new Entity("player"));
    }

    public void addPlayer2() {
        this.getEntities().add(new Entity("player"));
    }

    public void checkConnection() {
        if (this.serverConnectedToServer) {
            setUpThreads();
            this.serverConnectedToServer = false;
            this.twoPlayer = true;
        }
    }
    
    public boolean isTwoPlayer(){
        return this.twoPlayer;
    }

    public void startConnectorRunner() {
        cr = new Connector_Runner(this, server, client);
        t4 = new Thread(cr);
        t4.start();
    }

    public void startObstacleSpawnerRunner() {
        if (this.networkType.equalsIgnoreCase("server")) {
            os = new ObstacleSpawner_Runner(this);
            t1 = new Thread(os);
            t1.start();
        }

    }

    private void setUpThreads() {
        if (this.networkType.equalsIgnoreCase("server")) {
            addPlayer2();
            sc = new ServerCalibrater_Runner(this);
            t2 = new Thread(sc);
            t2.start();
        } else {
            cc = new ClientCalibrater_Runner(this);
            t3 = new Thread(cc);
            t3.start();
        }
    }

    public void setServerConnectedToServer(boolean connected) {
        this.serverConnectedToServer = connected;
    }

    public boolean getServerConnectedToServer() {
        return this.serverConnectedToServer;
    }

    public void setupServerOrClient() {
        if (networkType.equalsIgnoreCase("server")) {
            server.startRunnning();
        } else {
            client.startRunnning();
        }
    }

    public void closeCrap() {
        if (networkType.equalsIgnoreCase("server")) {
            if (os != null) {
                os.stop();
            }
            if (sc != null) {
                sc.stop();
            }
            if (server != null) {
                server.closeCrap();
            }
        } else {
            if (cc != null) {
                cc.stop();
            }
            if (client != null) {
                client.closeCrap();
            }
        }
    }

    public void updateEntityCenters() {
        entityCenters.clear();
        for (Entity entity : this.getEntities()) {
            double[] center = new double[2];
            center[0] = entity.getFigure().getCenter().getX();
            center[1] = entity.getFigure().getCenter().getY();
            entityCenters.add(center);
        }
    }

    public String getEntitiesToTransmit() {
        //== Creating and adding to the ArrayList<String>
        String entitiesToTransmit = "";
        for (int i = 0; i < this.getEntities().size(); i++) {
            String tempEntity = "";
            tempEntity += this.getEntities().get(i).getFigure().getType();
            tempEntity += "," + this.getEntities().get(i).getFigure().getCenter().getX();
            tempEntity += "," + this.getEntities().get(i).getFigure().getCenter().getY();
            if (i != this.getEntities().size() - 1) {
                tempEntity += ":";
            }
            entitiesToTransmit += tempEntity;
        }
        return entitiesToTransmit;
    }

    public ArrayList<double[]> getEntityCenters() {
        return this.entityCenters;
    }

    public synchronized ArrayList<Entity> getEntities() {
        return this.entities;
    }

    public void calibrateRecievedEntityTypes(String entitiesReceived) {
        this.getEntities().clear();
        System.out.println(entitiesReceived);
        String entitiesObtained = entitiesReceived;
        String[] separatedEntities = entitiesObtained.split(":");

        for (String entityString : separatedEntities) {
            Entity tempEntity = new Entity(entityString.split(",")[0]);
            tempEntity.getFigure().setCenter(Double.parseDouble(entityString.split(",")[1]), Double.parseDouble(entityString.split(",")[2]));
            this.getEntities().add(tempEntity);
        }
        
//        //== Annoying but important check. Actually i don't think it matters here.
//        if(entities.get(entities.size()-1).getFigure().getType().equalsIgnoreCase("player"))
//            entities.add(1, entities.remove(entities.size()-1));
    }

    public void calibrateEntityTypes(ArrayList<Entity> hostsEntities) {

        if (!hostsEntities.isEmpty()) {
            this.getEntities().clear();
            for (Entity entity : hostsEntities) {
                this.getEntities().add(entity);
            }
//            for (int i = 2; i < entities.size(); i++) {
//                entities.remove(i);
//            }
//            for (int j = 2; j < hostsEntities.size(); j++) {
//                entities.add(hostsEntities.get(j));
//            }
        }
    }

    public void calibrateEntityCenters(ArrayList<Point> inputCenters) {
        if (!inputCenters.isEmpty()) {
            for (int i = 0; i < inputCenters.size(); i++) {
                this.getEntities().get(i).getFigure().setCenter(inputCenters.get(i).getX(), inputCenters.get(i).getY());
            }

        }

    }

    //== Method for the client to update its entities. Should be called in the control-class run-loop
    public void updateEntities() {
        
        for (Entity entity : this.getEntities()) {
            entity.update();
        }
    }

    public void setOutputAwaiting(boolean awaiting) {
        this.outputAwaiting = awaiting;
    }

    public boolean getOuputAwaiting() {
        return this.outputAwaiting;
    }

    public void setInputAwaiting(boolean awaiting) {
        this.inputAwaiting = awaiting;
    }

    public boolean getInputAwaiting() {
        return this.inputAwaiting;
    }

    public String getNetworkType() {
        return this.networkType;
    }

    public Server getServer() {
        return this.server;
    }

    public Client getClient() {
        return this.client;
    }
}
