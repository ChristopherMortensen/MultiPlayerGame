package network;

import control.GameControl;

public class ServerCalibrater_Runner implements Runnable {

    //== Fields

    private GameControl gameControl;
    private Server server;
    private boolean running;

    //== Constructor
    public ServerCalibrater_Runner(GameControl gameControl) {
        this.gameControl = gameControl;
        this.server = gameControl.getServer();
        running = true;
    }

    //== Methods
    public void run() {
        while (running) {
//            this.server.transmitEntityTypes(this.gameControl.getEntities());
//            this.gameControl.updateEntityCenters();
//            this.server.transmitEntityCenters(this.gameControl.getEntityCenters());
            this.server.transmitEntitiesToTransmit(gameControl.getEntitiesToTransmit());
        }
    }

    public void stop() {
        this.running = false;
    }
}
