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

        everything.append(new QuestionCube(280, 518));
        everything.append(new Brick(530, 518));
        everything.append(new Brick(650, 518));
        everything.append(new Brick(770, 518));
        everything.append(new QuestionCube(650, 368));

        everything.append(new Pipe(1100, Main.HEIGHT - 100 - 100, 70, 100));
        everything.append(new Pipe(1400, Main.HEIGHT - 100 - 130, 70, 130));
        everything.append(new Pipe(1700, Main.HEIGHT - 100 - 160, 70, 160));
        everything.append(new Pipe(2100, Main.HEIGHT - 100 - 150, 70, 150));

        everything.append(new Block(2600, 628));
        everything.append(new Block(2640, 628));
        everything.append(new Block(2640, 588));
        everything.append(new Block(2680, 628));
        everything.append(new Block(2680, 588));
        everything.append(new Block(2680, 548));
        everything.append(new Block(2820, 628));
        everything.append(new Block(2820, 588));
        everything.append(new Block(2820, 548));
        everything.append(new Block(2860, 628));
        everything.append(new Block(2860, 588));
        everything.append(new Block(2900, 628));

        everything.append(new Pipe(3400, Main.HEIGHT - 100 - 130, 70, 130));
        everything.append(new Pipe(3600, Main.HEIGHT - 100 - 130, 70, 130));

        everything.append(new Brick(4000, 628));
        everything.append(new Brick(4040, 588));
        //everything.append(new Brick(4080, 548));
        everything.append(new Brick(4120, 508));
        //everything.append(new Brick(4160, 468));
        //everything.append(new Brick(4200, 428));
        everything.append(new Brick(4240, 388));
        everything.append(new Brick(4280, 348));
        everything.append(new Brick(4320, 308));
        //everything.append(new Brick(4360, 268));

        everything.append(new Brick(4440, 268));
        everything.append(new Brick(4600, 268));
        everything.append(new Brick(4760, 268));
        everything.append(new Brick(4920, 268));
        everything.append(new Brick(5080, 268));
        everything.append(new Brick(5240, 268));

        everything.append(new Brick(5420, 208));
        everything.append(new Brick(5600, 60));
        everything.append(new Pipe(5500, Main.HEIGHT - 100 - 140, 70, 140));

        everything.append(new Pipe(6000, Main.HEIGHT - 100 - 100, 70, 100));
        everything.append(new Pipe(6200, Main.HEIGHT - 100 - 180, 70, 180));
        everything.append(new Pipe(6400, Main.HEIGHT - 100 - 260, 70, 260));
        everything.append(new Pipe(6600, Main.HEIGHT - 100 - 340, 70, 340));

        everything.append(new QuestionCube(6700, 128));

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < i; j++){
                everything.append(new Block(6800 + (i * 40), 628 - (j * 40)));
            }
        }
        for (int i = 0; i < 8; i++){
            everything.append(new Block(6800 + (9 * 40), 628 - (i * 40)));
        }







        /*everything.append(new Brick(100, 628));
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
        everything.append(new QuestionCube(2150, 400));*/
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
