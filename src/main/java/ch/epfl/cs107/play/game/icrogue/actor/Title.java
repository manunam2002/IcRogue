package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;

public class Title extends ICRogueActor{

    private String title;

    private TextGraphics message;

    public Title(Area owner,String title) {
        super(owner, Orientation.UP, new DiscreteCoordinates(3,4));
        message = new TextGraphics(title,1.8f, Color.BLACK,Color.GRAY,.04f,
                true,false,new Vector(3,4));
    }

    @Override
    public void draw(Canvas canvas) {
        message.draw(canvas);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        27.12.2022
 */

