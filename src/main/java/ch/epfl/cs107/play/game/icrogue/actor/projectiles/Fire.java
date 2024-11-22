package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Fire extends Projectile{

    private Sprite[] sprites = Sprite.extractSprites("zelda/fire",4,1f,1f,this,16,16);

    private Animation animation = new Animation(4,sprites,true);

    private ICRogueFireInteractionHandler handler = new ICRogueFireInteractionHandler();

    public Fire(Area owner, Orientation orientation, DiscreteCoordinates coordinates) {
        super(owner, orientation, coordinates, 5, 1);
        setSprite(new Sprite("zelda/fire", 1f, 1f, this,
                new RegionOfInterest(0, 0, 16, 16), new
                Vector(0, 0)));
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler,isCellInteraction);
    }

    // defines interactions with turrets : a turret dies when is hit by a fire
    private class ICRogueFireInteractionHandler extends ICRogueProjectileInteractionHandler{

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if (isCellInteraction){
                turret.die();
            }
        }

        @Override
        public void interactWith(DarkLord darkLord, boolean isCellinteraction) {
            if (isCellinteraction){
                consume();
                darkLord.damage(getDAMAGE());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        animation.draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        animation.update(deltaTime);
        super.update(deltaTime);
    }
}

/*
 *	Author:      Manu Cristini
 *	Date:        04.12.2022
 */

