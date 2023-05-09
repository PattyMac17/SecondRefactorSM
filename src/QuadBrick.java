import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class QuadBrick extends Obstacle implements Drawable{
    int sideWidth;

    public QuadBrick(int x, int y, int sideWidth) {
        super(x, y);
        this.sideWidth = sideWidth;
        leftWall = new Rectangle2D.Double(position.x, position.y + wallLength,
                wallLength, sideLength - wallLength);
        rightWall = new Rectangle2D.Double(position.x + sideLength - wallLength,
                position.y + wallLength, wallLength, sideLength - wallLength);
        topWall = new Rectangle2D.Double(position.x, position.y, sideWidth, wallLength);
        bottomWall = new Rectangle2D.Double(position.x + wallLength, position.y + sideLength - wallLength,
                sideWidth - (2 * wallLength), wallLength);
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage brick = null;
        try {
            brick = ImageIO.read(getClass().getResourceAsStream("brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 4; i++){
            g.drawImage(brick, (int)position.x + sideLength*i, (int)position.y,sideLength,sideLength, null);
        }
    }
}
