package network;

import java.util.ArrayList;
import java.util.Random;

public class TestDoubleArrayArrayList {
    public static void main(String[] args) {
        ArrayList<double[]> centers = new ArrayList<>();
        Random random = new Random();
        
        for(int i = 0; i < 20; i++){
            double[] center = new double[2];
            center[0] = random.nextDouble();
            center[1] = random.nextDouble();
            centers.add(center);
        }
        
        String transmission = "";
        for(double[] center : centers){
            transmission += ("," + center[0] + "|" + center[1]);
        }
        System.out.println(transmission);
        
        System.out.println(transmission.substring(1, transmission.length()));
        
        boolean containsPlayer = transmission.contains("player");
        System.out.println("Contains \"player\"? --> " + containsPlayer);
        System.out.println("Adding player... transmission += \"player\"");
        transmission += "player";
        containsPlayer = transmission.contains("player");
        System.out.println("Contains \"player\" now? --> " + containsPlayer);
    }

}
