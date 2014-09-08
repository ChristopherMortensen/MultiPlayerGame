package network;

import entity.Entity;
import entity.Point;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

    //== Fields
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private String serverIP;

    //== Constructor
    public Client(String serverIP) {
        this.serverIP = serverIP;
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
        connection = new Socket(InetAddress.getByName(serverIP), 6789);
        System.out.println("Connectected to server");
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

    public ArrayList<Point> checkForEntityCenters() {
        String signal = "";
        try {
            signal = (String) input.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("class not found...");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        ArrayList<Point> centers = new ArrayList<>();

        boolean containsNotPlayer = !signal.contains("player") && !signal.isEmpty();

        if (containsNotPlayer) {
            String[] pairs = signal.split(",");
            for (String pair : pairs) {
                String[] points = pair.split("|");
                Point point = new Point(Double.parseDouble(points[0]), Double.parseDouble(points[1]));
                centers.add(point);
            }
        }

        return centers;
    }

    public ArrayList<Entity> checkForEntityTypes() {
        String signal = "";
        try {
            signal = (String) input.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Class not found...");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        ArrayList<Entity> entities = new ArrayList<>();

        boolean containsPlayer = signal.contains("player") && !signal.isEmpty();

        if (containsPlayer) {
            String[] types = signal.split(",");
            for (String type : types) {
                entities.add(new Entity(type));
            }
        }

        return entities;
    }

    public String checkForEntitiesTransmitted() {
        String transmission = "";
        try {
            transmission = (String) input.readObject();
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("Class not found...");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return transmission;
    }
}
