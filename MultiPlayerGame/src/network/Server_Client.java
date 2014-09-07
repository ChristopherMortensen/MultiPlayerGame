package network;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server_Client {

    //== Fields
    //== Streams and server-related objects
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private String networkType;
    //== Only used by client
    private String serverIP;

    //== Only String Constructor --> Server
    public Server_Client(String networkType) {
        try {
            this.networkType = networkType;
            server = new ServerSocket(6789, 100);
        } catch (IOException ioException) {
            System.out.println("Error in creating the ServerSocket");
            ioException.printStackTrace();
        }

    }

    //== String Param Constructor --> Client
    public Server_Client(String networkType, String serverIP) {
        this.networkType = networkType;
        this.serverIP = serverIP;
    }

    //== Methods
    public void startRunnning() {
        try {
            waitForConnection();
            setupStreams();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            if(networkType.equalsIgnoreCase("server"))
            System.out.println("Problems obtaining the incoming socket");
            else
                System.out.println("Problems reaching the server");
        }
    }

    private void waitForConnection() throws IOException {
        if (this.networkType.equalsIgnoreCase("server")) {
            connection = server.accept();
            System.out.println("Problems obtaining the incoming socket");
        } else {
            connection = new Socket(InetAddress.getByName(serverIP), 6789);
        }
    }

    //== Get stream to send and receive data
    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams are now setup!");
    }

    public String checkInputSignal() {
        String signal = "";
        try {
            signal = (String) input.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("class not found...");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return signal;
    }

    public void closeCrap() {
        System.out.println("\n Closing connections... \n");

        try {
            output.close();
            input.close();
            connection.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public String getNetworkType(){
        return this.networkType;
    }
    
    //== Transmissions to send
    //== Space = jump
    public void transmitJump() {
        try {
            output.writeObject("space");
        } catch (IOException ioException) {
            System.out.println("Error in sending a jump-transmission");
        }
    }

    //== 'o' = obstacle spawn
    public void transmitObstacleSpawn() {
        try {
            output.writeObject("o");
        } catch (IOException ioException) {
            System.out.println("Error in sending an obstacle-transmission");
        }
    }

    //== 'x' = game over
    public void transmitGameOver() {
        try {
            output.writeObject("x");
        } catch (IOException ioException) {
            System.out.println("Error in sending an Game Over-transmission");
        }
    }
    
    public void transmitEntityCenters(ArrayList<Integer> entityCenters){
        String transmission = "";
        for(Integer integer : entityCenters)
            transmission.concat("," + integer);
        try{//== This substring-thing is because of the first comma concatenated in the for-loop
            output.writeObject(transmission.substring(1, transmission.length()+1));
        }catch(IOException ioException){
            System.out.println("Error in sending an EntityCenters-transmission");
        }
    }
}
