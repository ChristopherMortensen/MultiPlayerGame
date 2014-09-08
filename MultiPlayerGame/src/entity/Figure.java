package entity;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Christopher
 */
public class Figure {
    //== Fields
    private ArrayList<Shape> shapes;
    private String type;
    private Random random;
    
    private int upperLength;
    private int lowerLength;
    
    private Point center;
    
    //== Constructor
    public Figure(String type){
        shapes = new ArrayList<>();
        this.type = type;
        random = new Random();
        
        upperLength = 0;
        lowerLength = 0;
        
        center = new Point(0,0);
        
        initializeFigure();
    }
    
    //== Methods
    private void initializeFigure(){
        switch(type){
            case "obstacle":
                createObstacle();
                break;
            case "player":
                createPlayer();
                break;
        }
    }
    
    //== Getters and Setters
    public ArrayList<Shape> getShapes(){
        return this.shapes;
    }
    
    public String getType(){
        return this.type;
    }
    
    //== Method to create a new Obstacle. All shapes in the figure, is dependent on the centerX and centerY-coords. 
    private void createObstacle(){
        shapes.clear();
        int obstacle = 600; //== Both shapes' length
        this.upperLength = random.nextInt(obstacle)+1;
        this.lowerLength = obstacle-upperLength;
        
        //== The center is calculated out from the random column-height and spawns from the most right point
        center.setX(1700);
        center.setY(this.lowerLength + 160);
        
        //== Upper Long Shape
        Shape upperLongShape = new Shape();
        upperLongShape.createRectangle(center.getX() - 50, center.getY() + 160, 100, this.upperLength);
        upperLongShape.setColor(0.2f, 0.2f, 0.2f);
        shapes.add(upperLongShape);
        //== Upper Small Block
        Shape upperSmallBlock = new Shape();
        upperSmallBlock.createRectangle(center.getX() - 60, center.getY() + 150, 120, 20);
        upperSmallBlock.setColor(0.25f, 0.25f, 0.25f);
        shapes.add(upperSmallBlock);
        //== Lower Long Shape
        Shape lowerLongShape = new Shape();
        lowerLongShape.createRectangle(center.getX() - 50, center.getY() - 160 - this.lowerLength, 100, this.lowerLength);
        lowerLongShape.setColor(0.2f, 0.2f, 0.2f);
        shapes.add(lowerLongShape);
        //== Lower Small Block
        Shape lowerSmallBlock = new Shape();
        lowerSmallBlock.createRectangle(center.getX() - 60, center.getY() - 160, 120, 20);
        lowerSmallBlock.setColor(0.25f, 0.25f, 0.25f);
        shapes.add(lowerSmallBlock);
        
    }
    
    //== Made to update already existing Obstacles, when they move across the screen
    //== NOTICE -> IF X == 0, THEN IT WILL ONLY UPDATE THE COORDINATES OF THIS SHAPE. 
    public void updateObstacle(double x){
        shapes.clear();
        
        if(x != 0)
        center.setX(center.getX() - x);
        
        //== Upper Long Shape
        Shape upperLongShape = new Shape();
        upperLongShape.createRectangle(center.getX() - 50, center.getY() + 160, 100, this.upperLength);
        upperLongShape.setColor(0.1f, 0.25f, 0.1f);
        shapes.add(upperLongShape);
        //== Upper Small Block
        Shape upperSmallBlock = new Shape();
        upperSmallBlock.createRectangle(center.getX() - 60, center.getY() + 150, 120, 20);
        upperSmallBlock.setColor(0.1f, 0.3f, 0.1f);
        shapes.add(upperSmallBlock);
        //== Lower Long Shape
        Shape lowerLongShape = new Shape();
        lowerLongShape.createRectangle(center.getX() - 50, center.getY() - 160 - this.lowerLength, 100, this.lowerLength);
        lowerLongShape.setColor(0.1f, 0.25f, 0.1f);
        shapes.add(lowerLongShape);
        //== Lower Small Block
        Shape lowerSmallBlock = new Shape();
        lowerSmallBlock.createRectangle(center.getX() - 60, center.getY() - 160, 120, 20);
        lowerSmallBlock.setColor(0.1f, 0.3f, 0.1f);
        shapes.add(lowerSmallBlock);
    }
    
    public void createPlayer(){
        shapes.clear();
        center.setX(650);
        center.setY(700);
        
        //== Body
        Shape block = new Shape();
        block.createRectangle(center.getX() - 20, center.getY() - 20, 40, 30);
        block.setColor(0.25f, 0.25f, 0.1f);
        
        shapes.add(block);
        
        //== Beak
        Shape beak = new Shape();
        beak.createRectangle(center.getX(), center.getY() - 15, 25, 10);
        beak.setColor(0.50f, 0.05f, 0.05f);
        shapes.add(beak);
        //== Eyes
        Shape eye = new Shape();
        eye.createRectangle(center.getX() + 5, center.getY() - 5, 15, 10);
        eye.setColor(1f, 1f, 1f);
        shapes.add(eye);
        //== Wing
        Shape wing = new Shape();
        wing.createRectangle(center.getX() - 25, center.getY() - 15, 15, 10);
        wing.setColor(0.8f, 0.8f, 0.8f);
        shapes.add(wing);
        //== Pupil
        Shape pupil = new Shape();
        pupil.createRectangle(center.getX() + 7, center.getY() - 3, 4, 4);
        pupil.setColor(0.1f, 0.1f, 0.1f);
        shapes.add(pupil);
    }
    
    public void updatePlayer(){
        shapes.clear();
        
        //== Body
         Shape block = new Shape();
        block.createRectangle(center.getX() - 20, center.getY() - 20, 40, 30);
        block.setColor(0.5f, 0.5f, 0.05f);
        
        shapes.add(block);
        
        //== Beak
        Shape beak = new Shape();
        beak.createRectangle(center.getX(), center.getY() - 15, 25, 10);
        beak.setColor(0.50f, 0.05f, 0.05f);
        shapes.add(beak);
        //== Eyes
        Shape eye = new Shape();
        eye.createRectangle(center.getX() + 5, center.getY() - 5, 15, 10);
        eye.setColor(1f, 1f, 1f);
        shapes.add(eye);
        //== Wing
        Shape wing = new Shape();
        wing.createRectangle(center.getX() - 25, center.getY() - 15, 15, 10);
        wing.setColor(0.8f, 0.8f, 0.8f);
        shapes.add(wing);
        //== Pupil
        Shape pupil = new Shape();
        pupil.createRectangle(center.getX() + 9, center.getY() - 3, 5, 5);
        pupil.setColor(0.1f, 0.1f, 0.1f);
        shapes.add(pupil);
    }
    
//    public void setCenterX(double x){
//        this.center.setX(x);
//    }
//    public void setCenterY(double y){
//        this.center.setY(y);
//    }
//    
//    public double getCenterX(){
//        return this.center.getX();
//    }
//    public double getCenterY(){
    
    public Point getCenter(){
        return this.center;
    }
    
    public void setCenter(double x, double y){
        this.center.setX(x);
        this.center.setY(y);
    }

}
