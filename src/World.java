import java.awt.*;
public class World {
    int width;
    int height;
    Player player;
    double playerVelocity;
    double cowVelocity;
    Ground ground;
    DataStructure<NPC> purpleCows;
    DataStructure<Obstacle> everything;
    boolean isScrolling = false;

    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.player = new Player(this);
        this.ground = new Ground();
        this.playerVelocity = 200;
        this.cowVelocity = -100;

        purpleCows = new DataStructure<NPC>();
        purpleCows.append(new NPC(380,460, this));

        everything = new DataStructure<Obstacle>();

        everything.append(new Brick(100, 628));
        everything.append(new Brick(400, 628));


        everything.append(new Block(300, 500));
        everything.append(new Block(340, 500));
        everything.append(new Block(380, 500));

        everything.append(new Brick(1400, 328));
        everything.append(new Brick(1200, 528));
        everything.append(new Brick(1300, 528));
        everything.append(new Pipe(1600, Main.HEIGHT - 100 - 100));
        everything.append(new Block(2000, 500));
        everything.append(new QuestionCube(2200, 500));
        everything.append(new QuestionCube(2150, 400));
    }
    public void draw(Graphics g) {
        player.draw(g);
        ground.draw(g);
        purpleCows.drawAll(g);
        everything.drawAll(g);
    }

    public void update(double time) {
        player.update(this, time);
        ground.update(this, time);
        purpleCows.updateAll(this, time);
        everything.updateAll(this, time);
    }
}
