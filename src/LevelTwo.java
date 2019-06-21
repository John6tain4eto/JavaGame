import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelTwo {
    private int finalScore;

    public  LevelTwo(int score){
        this.finalScore = score;
    }

    public void start()
    {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Stage theStage = new Stage();
        //set Stage boundaries to visible bounds of the main screen
        theStage.setX(primaryScreenBounds.getMinX());
        theStage.setY(primaryScreenBounds.getMinY());
        theStage.setWidth(primaryScreenBounds.getWidth());
        theStage.setHeight(primaryScreenBounds.getHeight());
        theStage.setFullScreen(true);

        theStage.setTitle( "What Really Smurfs Do" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        Canvas canvas = new Canvas( primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight()+50);
        root.getChildren().add( canvas );

        final ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                        
                    }
                });

        theScene.setOnKeyReleased(

                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Image background = new Image("background.png");

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        final Sprite smurf = new Sprite();
        smurf.setImage("4.png");
        smurf.setPosition(0,  primaryScreenBounds.getHeight()-100);
        final ArrayList<Sprite> items = new ArrayList<Sprite>();

        for (int i = 0; i < 15; i++)
        {
            Sprite itemIter = new Sprite();
            itemIter.setImage("vodka.png");
            double px = (primaryScreenBounds.getMaxX()-200) * Math.random() + 50;
            double py = (primaryScreenBounds.getMaxY()-200) * Math.random() + 50;
            itemIter.setPosition(px,py);
            items.add( itemIter );
        }

        final LongValue lastNanoTime = new LongValue( System.nanoTime() );



        new AnimationTimer()
        {

            public void handle(long currentNanoTime)
            {

                int score =0;
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / (1000000000.0);
                lastNanoTime.value = currentNanoTime;

                //game logic
                smurf.setVelocity(0,0);
                if (input.contains("LEFT"))
                    smurf.addVelocity(-200,0);
                if (input.contains("RIGHT"))
                    smurf.addVelocity(200,0);
                if (input.contains("UP"))
                    smurf.addVelocity(0,-200);
                if (input.contains("DOWN"))
                    smurf.addVelocity(0,200);

              /*  final Bounds bounds = canvas.getBoundsInLocal();
                final boolean atRightBorder = smurf.getBoundary() >= (bounds.getMaxX() - smurf());
                final boolean atLeftBorder = smurf.getLayoutX() <= (bounds.getMinX() + smurf.getRadius());
                final boolean atBottomBorder = smurf.getLayoutY() >= (bounds.getMaxY() - smurf.getRadius());
                final boolean atTopBorder = smurf.getLayoutY() <= (bounds.getMinY() + smurf.getRadius());

                if (atRightBorder || atLeftBorder) {
                    deltaX *= -1;
                }
                if (atBottomBorder || atTopBorder) {
                    deltaY *= -1;
                }*/
                smurf.update(elapsedTime);

                // collision detection

                Iterator<Sprite> itemIterIter = items.iterator();
                while ( itemIterIter.hasNext() )
                {
                    Sprite itemIter = itemIterIter.next();
                    if ( smurf.intersects(itemIter) )
                    {
                        itemIterIter.remove();
                        smurf.addScore(1000);
                        if(smurf.getScore()==15000){
                            LevelThree secondLevel = new LevelThree(finalScore + smurf.getScore());
                            secondLevel.start();

                            //System.out.println("da");
                            //System.exit(1);
                        }

                    }

                }

                // render
                gc.clearRect(0,0,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());
                gc.drawImage(background,0,0,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight()+500);
                smurf.render( gc );

                for (Sprite itemIter : items )
                    itemIter.render( gc );
                String pointsText = "Score: " + (smurf.getScore());
                gc.fillText( pointsText, primaryScreenBounds.getWidth()-150, 36 );
                gc.strokeText( pointsText, primaryScreenBounds.getWidth()-150, 36 );

            }
        }.start();

        theStage.show();
    }
}
