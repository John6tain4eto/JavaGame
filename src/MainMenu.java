import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;


public  class MainMenu extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getX() > (primaryScreenBounds.getWidth() -150) / 2&&mouseEvent.getY() >(primaryScreenBounds.getHeight() /3)-15
                    &&mouseEvent.getY() <(primaryScreenBounds.getHeight() /3)+9) {
                LevelOne one = new LevelOne();
                one.start();

            }


        }
    };
    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage theStage = new Stage();
        //set Stage boundaries to visible bounds of the main screen
        theStage.setX(primaryScreenBounds.getMinX());
        theStage.setY(primaryScreenBounds.getMinY());
        theStage.setWidth(primaryScreenBounds.getWidth());
        theStage.setHeight(primaryScreenBounds.getHeight());
         theStage.setFullScreen(true);


        theStage.setTitle("What Smurfs Really Do");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        Canvas canvas = new Canvas( primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight()+100);
        root.getChildren().add(canvas);
        theScene.setOnMouseClicked(mouseHandler);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Font theFont = Font.font("Montana", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);


        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                Image background = new Image("SmurfLend.jpg");
                Image logo = new Image("Logo.png");
                // render
                gc.clearRect(0, 0, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
                gc.drawImage(background, 0, 0, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
                gc.drawImage(logo, (primaryScreenBounds.getWidth() - 50) / 4, primaryScreenBounds.getHeight() / 8, 689, 91);
                gc.fillText("Play", (primaryScreenBounds.getWidth() - 130) / 2, primaryScreenBounds.getHeight() / 3);
                gc.strokeText("Play", (primaryScreenBounds.getWidth() - 130) / 2, primaryScreenBounds.getHeight() / 3);


            }
        }.start();

        theStage.show();
    }
}