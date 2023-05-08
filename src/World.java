import java.awt.*;
public class World {
    int width;
    int height;
    Player player;
    int playerVelocity;
    Ground ground;
    DataStructure<Obstacle> everything;

    public World(int width, int height){
        this.width = width;
        this.height = height;
        this.player = new Player();
        this.ground = new Ground();
        this.playerVelocity = 500;

        everything = new DataStructure<Obstacle>();

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
        everything.drawAll(g);
    }

    public void update(double time) {
        player.update(this, time);
        ground.update(this, time);
        everything.updateAll(this, time);
    }
}
