package network;

import entity.Entity;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    //== Fields
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    //== Constructor
    public Server() {
        try {
            server = new ServerSocket(6789, 100);
        } catch (IOException ioException) {
            System.out.println("Error in creating the ServerSocket");
            ioException.printStackTrace();
        }
    }

    //== Methods
    public void startRunnning() {
        try {
            waitForConnection();
            setupStreams();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("Problems reaching the server");
        }
    }

    private void waitForConnection() throws IOException {
        connection = server.accept();
        System.out.println("Connection obtained from Client");
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
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
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

    public void transmitEntityCenters(ArrayList<double[]> entityCenters) {
        String transmission = "";
        for (double[] center : entityCenters) {
            transmission += ("," + center[0] + "|" + center[1]);
        }
        try {//== This substring-thing is because of the first comma and last punctuation concatenated in the for-loop 
            output.writeObject(transmission.substring(1, transmission.length()));
        } catch (IOException ioException) {
            System.out.println("Error in sending an EntityCenters-transmission");
            ioException.printStackTrace();
        }
    }

    public void transmitEntitiesToTransmit(String wholeString) {
        try {
            output.writeObject(wholeString);
        } catch (IOException ioException) {
            System.out.println("Error in sending an EntityCenters-transmission");
            ioException.printStackTrace();
        }
    }

    public void transmitEntityTypes(ArrayList<Entity> hostsEntities) {
        String transmission = "";
        for (Entity entity : hostsEntities) {
            transmission += ("," + entity.getFigure().getType());
        }
        try {
            output.writeObject(transmission.substring(1, transmission.length()));
        } catch (IOException ioException) {
            System.out.println("Error in sending an EntityTypes-transmission");
            ioException.printStackTrace();
        }
    }
}
