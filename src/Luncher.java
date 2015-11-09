import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class Luncher extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage)
    {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

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
        smurf.setImage("2.png");
        smurf.setPosition(0,  primaryScreenBounds.getHeight());
//domryhinmg
        final ArrayList<Sprite> items = new ArrayList<Sprite>();

        for (int i = 0; i < 15; i++)
        {
            Sprite moneybag = new Sprite();
            moneybag.setImage("2m.png");
            double px = primaryScreenBounds.getMaxX() * Math.random() + 50;
            double py = primaryScreenBounds.getMaxY() * Math.random() + 50;
            moneybag.setPosition(px,py);
            items.add( moneybag );
        }

        final LongValue lastNanoTime = new LongValue( System.nanoTime() );

        final IntValue score = new IntValue(0);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {


                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic

                smurf.setVelocity(0,0);
                if (input.contains("LEFT"))
                    smurf.addVelocity(-90,0);
                if (input.contains("RIGHT"))
                    smurf.addVelocity(90,0);
                smurf.setImage("3.png");
                smurf.setImage("2.png");
                System.out.println( );
                if (input.contains("UP"))
                    smurf.addVelocity(0,-90);
                if (input.contains("DOWN"))
                    smurf.addVelocity(0,90);

                smurf.update(elapsedTime);

                // collision detection

                Iterator<Sprite> moneybagIter = items.iterator();
                while ( moneybagIter.hasNext() )
                {
                    Sprite moneybag = moneybagIter.next();
                    if ( smurf.intersects(moneybag) )
                    {
                        moneybagIter.remove();
                        score.value++;
                    }
                }

                // render
                gc.clearRect(0,0,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());
                gc.drawImage(background,0,0,primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight()+500);
                smurf.render( gc );

                for (Sprite moneybag : items )
                    moneybag.render( gc );

                String pointsText = "Score: " + (100 * score.value);
                gc.fillText( pointsText, 850, 36 );
                gc.strokeText( pointsText, 850, 36 );

            }
        }.start();

        theStage.show();
    }
}