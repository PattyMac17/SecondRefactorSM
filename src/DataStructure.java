import java.awt.*;
public class DataStructure<E extends Obstacle> {
    Node<E> end;
    public DataStructure(){
        end = null;
    }
    public void append(E toAppend){
        Node<E> toAdd = new Node<E>(toAppend);
        toAdd.prev = end;
        end = toAdd;
    }
    public E peek(){
        return end.element;
    }
    public E pop(){
        E toReturn = end.element;
        end = end.prev;
        return toReturn;
    }
    public void drawAll( Graphics g){
        Node<E> elem = this.end;
        elem.element.draw(g);
        while(elem.prev != null){
            elem = elem.prev;
            elem.element.draw(g);
        }
    }
    public void updateAll(World w, double time){
        Node<E> node = this.end;
        E elem = node.element;
        elem.update(w, time);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            elem.update(w, time);
        }
    }
    public void setVelocity(Pair a){
        Node<E> node = this.end;
        E elem = node.element;
        elem.setVelocity(a);
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            elem.setVelocity(a);
        }
    }
    public void collisionCheck(World w){
        rightCheck(w);
        leftCheck(w);
        jumpStopCheck(w);
        bottomCheck(w);
    }
    public void rightCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.rightWall)){
            w.player.rightCollision = true;
            w.player.velocity = new Pair(0, w.player.velocity.y);
        }
        else{
            w.player.rightCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.rightWall)){
                w.player.rightCollision = true;
                w.player.velocity = new Pair(0, w.player.velocity.y);
            }
            else{
                w.player.rightCollision = false;
            }
        }
    }
    public void leftCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.leftWall)){
            w.player.leftCollision = true;
            w.player.velocity = new Pair(0, w.player.velocity.y);
            w.ground.setVelocity(new Pair(0, 0));
            this.setVelocity(new Pair(0, 0));
        }
        else{
            w.player.leftCollision = false;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.leftWall)){
                w.player.leftCollision = true;
                w.player.velocity = new Pair(0, w.player.velocity.y);
                w.ground.setVelocity(new Pair(0, 0));
                this.setVelocity(new Pair(0, 0));
            }
            else{
                w.player.leftCollision = false;
            }
        }
    }
    public boolean jumpStartCheck(World w){
        boolean contact = false;
        Node<E> node = this.end;
        E elem = node.element;
        if (w.player.hitBox.intersects(elem.topWall)){
            contact = true;
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if (w.player.hitBox.intersects(elem.topWall)){
                contact = true;
            }
        }
        return contact;
    }
    public void jumpStopCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(w.player.hitBox.intersects(elem.topWall) && w.player.velocity.y > 0){
            w.player.velocity = new Pair(w.player.velocity.x,0);

        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(w.player.hitBox.intersects(elem.topWall) && w.player.velocity.y > 0){
                w.player.velocity = new Pair(w.player.velocity.x,0);

            }
        }
    }
    public void bottomCheck(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if (w.player.hitBox.intersects(elem.bottomWall)){
            w.player.velocity = new Pair(w.player.velocity.x, -w.player.velocity.y);
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if (w.player.hitBox.intersects(elem.bottomWall)){
                w.player.velocity = new Pair(w.player.velocity.x, -w.player.velocity.y);
            }
        }
    }
}
