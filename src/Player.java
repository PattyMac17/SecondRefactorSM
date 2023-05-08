import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Player implements Drawable{
    Rectangle2D hitBox;
    Pair position;
    Pair velocity;
    Pair acceleration;

    int playerHeight;
    int playerWidth;
    Color testColor;

    boolean atMiddle = false;
    boolean atStart = false;
    boolean ableToJump = false;
    boolean leftCollision = false;
    boolean rightCollision = false;
    public Player(){
        //set player height and width
        this.playerHeight = 50;
        this.playerWidth = 30;

        //set initial position
        this.position = new Pair(50, Main.HEIGHT - 99 - this.playerHeight);
        this.velocity = new Pair(0, 0);
        this.acceleration = new Pair(0, 0);

        //create hitBox
        this.hitBox = new Rectangle2D.Double(position.x, position.y, playerWidth, playerHeight);

        //set color for test rectangle
        this.testColor = Color.CYAN;
    }
    @Override
    public void update(World w, double time){
        position = position.add(velocity.times(time));
        hitBox.setRect(position.x,position.y, playerWidth,playerHeight);
        velocity = velocity.add(acceleration.times(time));

        midCheck();
        wallCheck();
        jumpStop(w);
        jumpStart(w);

        w.everything.collisionCheck(w);

    }
    public void midCheck(){
        Rectangle2D midline = new Rectangle2D.Double(Main.WIDTH/2 + playerWidth, 0, Main.WIDTH/2 -
                playerWidth, Main.HEIGHT);
        if(this.hitBox.intersects(midline)){
            atMiddle = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            atMiddle = false;
        }
    }
    public void wallCheck(){
        Rectangle2D start = new Rectangle2D.Double(-10, 0, 10, Main.HEIGHT);
        if(this.hitBox.intersects(start)){
            atStart = true;
            velocity = new Pair(0, velocity.y);
        }
        else{
            atStart = false;
        }
    }
    @Override
    public void draw(Graphics g){
        g.setColor(testColor);
        g.fillRect((int)this.position.x, (int)this.position.y, playerWidth, playerHeight);
    }
    public void setVelocity(Pair v){
        velocity = v;
    }

    public void setAcceleration(Pair acceleration) {
        this.acceleration = acceleration;
    }
    public void jumpStop(World w){
        if(hitBox.intersects(w.ground.groundLevel) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);
        }
    }
    public <E extends Obstacle> void DSJumpStopCheck(DataStructure<E> DS, World w){
        Node<E> endNode = DS.end;
        if(hitBox.intersects(endNode.element.topWall) && velocity.y > 0){
            velocity = new Pair(velocity.x,0);

        }
        while (endNode.prev != null){
            endNode = endNode.prev;
            E elem = endNode.element;
            if(hitBox.intersects(elem.topWall) && velocity.y > 0){
                velocity = new Pair(velocity.x,0);

            }
        }
    }
    public <E extends Obstacle> void jumpStart( World w){
        if(hitBox.intersects(w.ground.groundLevel) || w.everything.jumpStartCheck(w)){
            ableToJump = true;
        }
        else{
            ableToJump = false;
        }
    }
}
