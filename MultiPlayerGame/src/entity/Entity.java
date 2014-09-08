package entity;

/**
 *
 * @author Christopher
 */
public class Entity {

    //== Fields
    private Figure figure;

    //                                                      >> Insert some sort of physics fields <<
    private boolean falling;
    private boolean jumping;
    private float velocityY;
    private float gravity;
    private float maxSpeed;

    //== Constructor
    public Entity(String type) {
        figure = new Figure(type);
        falling = true;
        jumping = false;
        velocityY = 0;
        gravity = 0.0028f;
        maxSpeed = 2f;
    }

    //== Methods
    public Figure getFigure() {
        return this.figure;
    }

    public void updateObstacle(double x) {
        this.figure.updateObstacle(x);
    }
    
    public void update(){
        switch(this.figure.getType()){
            case "obstacle":
                    updateObstacle(0);
                    break;
                case "player":
                    this.getFigure().updatePlayer();
                    break;
                default:
                    break;
        }
    }

    
    public void tick() {

        

        if (jumping) {
            velocityY -= gravity;
            figure.getCenter().setY(figure.getCenter().getY() + velocityY);
            if (velocityY < 0.005) {
//                System.out.println("jump -> fall");
                jumping = false;
                falling = true;
            }

        }

        if (falling) {  //== Implement Jumping  
            velocityY += gravity;
            figure.getCenter().setY(figure.getCenter().getY() - velocityY);
        }
        if (velocityY > maxSpeed)
            velocityY = maxSpeed;
//        System.out.println("Velocity: " + velocityY + " Jumping: " + jumping + " Falling: " + falling);
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isFalling() {
        return falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public float getVelocityY() {
        return velocityY;
    }

    @Override
    public String toString(){
        return 
                "type: " + this.figure.getType() +
                "\tCenter-X: " + this.figure.getCenter().getX() +
                "\tCenter-Y: " + this.figure.getCenter().getY();
    }
}
