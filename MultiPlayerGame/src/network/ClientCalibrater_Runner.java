package network;

import control.GameControl;

public class ClientCalibrater_Runner implements Runnable{
    //== Fileds
    GameControl gameControl;
    Client client;
    boolean running;
    
    //== Constructor
    public ClientCalibrater_Runner(GameControl gameControl){
        this.gameControl = gameControl;
        this.client = gameControl.getClient();
        running = true;
    }
    
    @Override 
    public void run(){
        while(running){
//            gameControl.calibrateEntityTypes(client.checkForEntityTypes());
//            gameControl.calibrateEntityCenters(client.checkForEntityCenters());
            gameControl.calibrateRecievedEntityTypes(client.checkForEntitiesTransmitted());
        }
    }
    
    public void stop(){
        running = false;
    }

}
