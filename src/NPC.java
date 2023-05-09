import java.awt.*;

public class NPC extends Obstacle implements Drawable{
    int bounceCount;
    Color color;
    boolean engaged = false;

    public NPC(int x, int y, World w) {
        super(x, y);
        color = Color.YELLOW;

        acceleration = new Pair(acceleration.x, 700);

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect((int)this.position.x, (int)this.position.y, sideLength, sideLength);
    }

    @Override
    public void update(World w, double time) {
        super.update(w, time);
        jumpStop(w);
        scrollAdjust(w);
        System.out.println(bounceCount%2);
    }

    @Override
    public void setVelocity(Pair a) {
        super.setVelocity(a);
    }
    public void jumpStop(World w){
        if(bottomWall.intersects(w.ground.groundLevel)){
            velocity = new Pair(velocity.x,0);
        }
    }
    public void scrollAdjust(World w){
        if(engaged){
            System.out.println(!leftCollision(w.everything)&& !rightCheck(w.everything));
            if(w.isScrolling){
                if(bounceCount%2 ==0){
                    velocity = new Pair(-300, velocity.y);
                }
                else{
                    velocity = new Pair(-100, velocity.y);
                }
            }
            else{
                if(bounceCount%2 ==0){
                    velocity = new Pair(-100, velocity.y);
                }
                else{
                    velocity = new Pair(100, velocity.y);
                }
            }
        }
    }
    public <E extends Obstacle> boolean leftCollision(DataStructure<E> DS){
        Node<E> node = DS.end;
        E elem = node.element;
        if(this.leftWall.intersects(elem.rightWall)){
            return true;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(this.leftWall.intersects(elem.rightWall)){
                return true;
            }
        }
        return false;
    }
    public <E extends Obstacle> boolean rightCheck(DataStructure<E> DS){
        Node<E> node = DS.end;
        E elem = node.element;
        if(this.rightWall.intersects(elem.leftWall)){
            return true;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(this.rightWall.intersects(elem.leftWall)){
                return true;
            }
        }
        return false;
    }
}
