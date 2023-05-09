import javax.xml.crypto.Data;
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
    public void adjustXVelocity(){
        Node<E> node = this.end;
        E elem = node.element;
        double signOf = Math.signum(elem.velocity.x);
        if(signOf > 0){
            elem.setVelocity(new Pair(0, elem.velocity.y));
        }
        else{
            elem.setVelocity(new Pair(-300, elem.velocity.y));
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            signOf = Math.signum(elem.velocity.x);
            if(signOf > 0){
                elem.setVelocity(new Pair(0, elem.velocity.y));
            }
            else{
                elem.setVelocity(new Pair(-300, elem.velocity.y));
            }
        }
    }
    public void revertXVelocity(){
        Node<E> node = this.end;
        E elem = node.element;
        double signOf = Math.signum(elem.velocity.x);
        if(signOf > 0){
            elem.setVelocity(new Pair(100, elem.velocity.y));
        }
        else{
            elem.setVelocity(new Pair(-100, elem.velocity.y));
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            signOf = Math.signum(elem.velocity.x);
            if(signOf > 0){
                elem.setVelocity(new Pair(100, elem.velocity.y));
            }
            else{
                elem.setVelocity(new Pair(-100, elem.velocity.y));
            }
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
    public void NPCCheck(DataStructure<NPC> npcs){
        NPCLeftCheck(npcs);
        NPCRightCheck(npcs);
        NPCGravity(npcs);
    }
    public void NPCLeftCheck(DataStructure<NPC> npcs) {
        Node<NPC> node = npcs.end;
        NPC elem = node.element;
        NPC_LC(elem);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            NPC_LC(elem);
        }
    }
    public void NPC_LC(NPC unit){
        Node<E> node = this.end;
        E elem = node.element;
        if(unit.leftWall.intersects(elem.rightWall)){
            unit.velocity.flipX();
            unit.bounceCount++;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(unit.leftWall.intersects(elem.rightWall)){
                unit.velocity.flipX();
                unit.bounceCount++;
            }
        }
    }
    public void NPCRightCheck(DataStructure<NPC> npcs){
        Node<NPC> node = npcs.end;
        NPC elem = node.element;
        NPC_RC(elem);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            NPC_RC(elem);
        }
    }
    public boolean NPC_RC(NPC unit){
        Node<E> node = this.end;
        E elem = node.element;
        if(unit.rightWall.intersects(elem.leftWall)){
            unit.velocity.flipX();
            unit.bounceCount++;
            return true;
        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(unit.rightWall.intersects(elem.leftWall)){
                unit.velocity.flipX();
                unit.bounceCount++;
                return true;
            }
        }
        return false;
    }
    public void NPCGravity(DataStructure<NPC> npcs){
        Node<NPC> node = npcs.end;
        NPC elem = node.element;
        NPC_GC(elem);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            NPC_GC(elem);
        }
    }
    public void NPC_GC(NPC unit){
        Node<E> node = this.end;
        E elem = node.element;
        if(unit.bottomWall.intersects(elem.topWall) && unit.velocity.y > 0){
            unit.velocity = new Pair(unit.velocity.x,0);

        }
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            if(unit.bottomWall.intersects(elem.topWall) && unit.velocity.y > 0){
                unit.velocity = new Pair(unit.velocity.x,0);

            }
        }
    }
    /*public void fullStop(){
        Node<E> node = this.end;
        E elem = node.element;
        cease(elem);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            cease(elem);
        }
    }
    public void cease(E element){
        if(element.velocity.x != 0){
            element.storedSpeed = element.velocity;
        }
        element.velocity = new Pair(0, 0);
    }
    public void release(){
        Node<E> node = this.end;
        E elem = node.element;
        resume(elem);
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            resume(elem);
        }
    }
    public void resume(E element){
        element.velocity = element.storedSpeed;
    }*/
    /*public void adjustVelocity(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(Math.signum(elem.velocity.x)> 0){
            elem.setVelocity(new Pair(elem.velocity.x * 2, elem.velocity.y));
        }
        else{
            elem.setVelocity(new Pair(elem.velocity.x * 0.5, elem.velocity.y));
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if(Math.signum(elem.velocity.x)> 0){
                elem.setVelocity(new Pair(elem.velocity.x * 2, elem.velocity.y));
            }
            else{
                elem.setVelocity(new Pair(elem.velocity.x * 0.5, elem.velocity.y));
            }
        }
    }
    public void revertVelocity(World w){
        Node<E> node = this.end;
        E elem = node.element;
        if(Math.signum(elem.velocity.x)> 0){
            elem.setVelocity(new Pair(elem.velocity.x * 0.5, elem.velocity.y));
        }
        else{
            elem.setVelocity(new Pair(elem.velocity.x * 2, elem.velocity.y));
        }
        while(node.prev != null){
            node = node.prev;
            elem = node.element;
            if(Math.signum(elem.velocity.x)> 0){
                elem.setVelocity(new Pair(elem.velocity.x * 0.5, elem.velocity.y));
            }
            else{
                elem.setVelocity(new Pair(elem.velocity.x * 2, elem.velocity.y));
            }
        }
    }*/
}
