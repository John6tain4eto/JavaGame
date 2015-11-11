import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Score {

    private int score;

    public Score(int finalScore) {
        this.score = finalScore;
    }

    public void start() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Stage theStage = new Stage();
        //set Stage boundaries to visible bounds of the main screen
        theStage.setX(primaryScreenBounds.getMinX());
        theStage.setY(primaryScreenBounds.getMinY());
        theStage.setWidth(primaryScreenBounds.getWidth());
        theStage.setHeight(primaryScreenBounds.getHeight());
        theStage.setFullScreen(true);

        theStage.setTitle("What Really Smurfs Do");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        Canvas canvas = new Canvas(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight() + 50);
        root.getChildren().add(canvas);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Font theFont = Font.font("Montana", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                Image background = new Image("SmurfLend.jpg");

                // render
                gc.clearRect(0, 0, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
                gc.drawImage(background, 0, 0, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
                gc.fillText("Score: " + score, (primaryScreenBounds.getWidth() - 130) / 2, primaryScreenBounds.getHeight() / 3);
                gc.strokeText("Score: " + score, (primaryScreenBounds.getWidth() - 130) / 2, primaryScreenBounds.getHeight() / 3);


            }
        }.start();

        theStage.show();
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
