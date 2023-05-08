import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class Main extends JPanel implements KeyListener{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 1000;
    Rectangle2D midline;

    World world;



    class Runner implements Runnable{
        public void run() {
            while(true){
                world.update(1.0 / (double)FPS);
                repaint();
                try{
                    Thread.sleep(1000/FPS);
                }
                catch(InterruptedException e){}
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W && world.player.ableToJump){
            world.player.setAcceleration(new Pair(world.player.acceleration.x, 700));
            world.player.setVelocity(new Pair(world.player.velocity.x, -400));
        }
        if(e.getKeyCode() == KeyEvent.VK_A && !world.player.atStart && !world.player.rightCollision){
            Pair a = new Pair(-world.playerVelocity, world.player.velocity.y);
            world.player.setVelocity(a);
        }
        if (e.getKeyCode() == KeyEvent.VK_D && !world.player.leftCollision){
            Pair a = new Pair(world.playerVelocity, world.player.velocity.y);
            if (!world.player.atMiddle){
                world.player.setVelocity(a);
            }
            else{
                a = new Pair(-world.playerVelocity, 0);
                world.ground.setVelocity(a);
                world.everything.setVelocity(a);
            }
        }
    }
    public <E extends Obstacle> void moveDS(DataStructure<E> DS, Pair a){
        Node<E> node = DS.end;
        E elem = node.element;
        elem.setVelocity(a);
        while (node.prev != null){
            node = node.prev;
            elem = node.element;
            elem.setVelocity(a);
        }
    }

    public void keyReleased(KeyEvent e) {
        if( (e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_D)){
            Pair a = new Pair(0, world.player.velocity.y);
            world.player.setVelocity(a);
            world.ground.setVelocity(new Pair(0, 0));
            world.everything.setVelocity(new Pair (0, 0));
        }
    }


    public void keyTyped(KeyEvent e) {

    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public Main(){
        world = new World(WIDTH, HEIGHT);
        midline = new Rectangle2D.Double(WIDTH/2 + world.player.playerWidth, 0, WIDTH/2 -
                world.player.playerWidth, HEIGHT);
        System.out.println(world.player.hitBox.getBounds2D());
        System.out.println(world.player.hitBox.contains(midline));
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Super Mammoth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage background = null;
        try {
            background = ImageIO.read(getClass().getResourceAsStream("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(background, (int)world.ground.position.x,0,10000,885, null);

        world.draw(g);

    }
}