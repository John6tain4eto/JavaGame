import javafx.geometry.Point3D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Sprite {
    private Image image;
    private double positionX;
    private double positionY;
    private double prevPosX;
    private double prevPosY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private int score;
    private int steps;

    public Sprite() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        score = 0;
        steps = 0;

    }

    public void addScore(int value) {
        score += value;
    }

    public int getScore() {

        return score;
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        this.setImage(i);
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        String file = "1.png";
        this.steps = (this.steps) % 4 + 1;
        file = file.replace(file.substring(0, 1), String.valueOf(this.steps));
        this.setImage(file);
        if (Screen.getPrimary().getVisualBounds().contains(this.getBoundary())) {
            velocityX += x;
            velocityY += y;
            this.saveLastPossiton();
        } else {
            positionX = prevPosX;
            positionY = prevPosY;
        }
    }

    private void saveLastPossiton() {
        prevPosX = positionX;
        prevPosY = positionY;
    }

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;

    }

    public void render(GraphicsContext gc) {
        gc.save();
        if (velocityX < 0) {
            ImageView iv = new ImageView(image);
            iv.setScaleX(-1);
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            image = iv.snapshot(params, null);
        }


        gc.drawImage(image, positionX, positionY, width, height);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public String toString() {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }
}