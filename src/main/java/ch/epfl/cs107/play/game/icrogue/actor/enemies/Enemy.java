package ch.epfl.cs107.play.game.icrogue.actor.enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Enemy extends ICRogueActor {
    private boolean isAlive;

    private Sprite sprite;

    public boolean isAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;
    }

    public Enemy(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates);
        isAlive = true;
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        11.12.2022
 */

