package entity;

/**
 *
 * @author Christopher
 */
public class Point {
    //== Fields
    private double x;
    private double y;
    
    //== Constructor
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    //== Methods
    public void setX(double value){
        this.x = value;
    }
    public void setY(double value){
        this.y = value;
    }
    
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

}
