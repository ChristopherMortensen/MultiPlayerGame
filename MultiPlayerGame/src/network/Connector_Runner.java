package network;

import control.GameControl;
import control.ObstacleSpawner_Runner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector_Runner implements Runnable {

    //== Fields

    private GameControl gameControl;
    private Server server;
    private Client client;

    //== Constructor

    public Connector_Runner(GameControl gameControl, Server server, Client client) {
        this.gameControl = gameControl;
        this.server = server;
        this.client = client;
    }

    //== Methods
    @Override
    public void run() {
        if (gameControl.getNetworkType().equalsIgnoreCase("server")) {
            server.startRunnning();
        } else {
            client.startRunnning();
        }
        gameControl.setServerConnectedToServer(true);
    }
}
