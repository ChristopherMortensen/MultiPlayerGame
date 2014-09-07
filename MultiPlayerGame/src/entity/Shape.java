package entity;

import java.util.ArrayList;

/**
 *
 * @author Christopher
 */
public class Shape {

    //== Fields
    private ArrayList<Point> coordinates;
    private Point center;
    private float[] color;

    //== Constructor
    public Shape() {
        this.coordinates = new ArrayList<>();
        this.center = new Point(0, 0);
        this.color = new float[3];
    }

    //== Methods
    //== Setters and Gettters
    public ArrayList<Point> getCoordinateList() {
        return this.coordinates;
    }

    public void setCenter(Point newPoint) {
        this.center = newPoint;
    }

    public Point getCenter() {
        return this.center;
    }

    public void setColor(float red, float green, float blue) {
        this.color[0] = red;
        this.color[1] = green;
        this.color[2] = blue;
    }

    public float[] getColor() {
        return this.color;
    }

    //== Creation
    public void createRectangle(double xStart, double yStart, int xLength, int yLength) {
        Point p;
        coordinates.add(p = new Point(xStart, yStart));                     // p1
        coordinates.add(p = new Point(xStart + xLength, yStart));           // p2
        coordinates.add(p = new Point(xStart + xLength, yStart + yLength)); // p3
        coordinates.add(p = new Point(xStart, yStart + yLength));           // p4

        double centerX = (((xStart + xLength) - (xStart)) / 2) + xStart;
        double centerY = (((yStart + yLength) - (yStart)) / 2) + yStart;
        center.setX(centerX);
        center.setY(centerY);
    }

    public void createSquare(double xStart, double yStart, int sideLength) {
        Point p;
        coordinates.add(p = new Point(xStart, yStart));                           // p1
        coordinates.add(p = new Point(xStart + sideLength, yStart));              // p2
        coordinates.add(p = new Point(xStart + sideLength, yStart + sideLength)); // p3
        coordinates.add(p = new Point(xStart, yStart + sideLength));              // p4

        double centerX = (((xStart + sideLength) - (xStart)) / 2) + xStart;
        double centerY = (((yStart + sideLength) - (yStart)) / 2) + yStart;
        center.setX(centerX);
        center.setY(centerY);
    }

    public boolean pointTouches(Point p) {

        if (p.getX() > coordinates.get(0).getX() && p.getY() > coordinates.get(0).getY()
                && p.getX() < coordinates.get(1).getX() && p.getY() > coordinates.get(1).getY()
                && p.getX() < coordinates.get(2).getX() && p.getY() < coordinates.get(2).getY()
                && p.getX() > coordinates.get(3).getX() && p.getY() < coordinates.get(3).getY())
            return true;
        return false;
    }

}
